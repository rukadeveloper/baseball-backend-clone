package com.baseball.comics.baseball_comics.layers.service;

import com.baseball.comics.baseball_comics.layers.Exception.common.CommonException;
import com.baseball.comics.baseball_comics.layers.Repository.GameRepository;
import com.baseball.comics.baseball_comics.layers.dto.GameEntity;
import com.baseball.comics.baseball_comics.layers.dto.GameResultDTO;
import com.baseball.comics.baseball_comics.layers.enums.common.CommonError;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BaseballCrawlerService {
    private final GameRepository gameRepository;

    public Elements crawlingTeamInfo() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // 브라우저 창 없이 실행
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);

        try {
            LocalDate start = LocalDate.of(2025,4,1);
            LocalDate end = LocalDate.of(2025,6,10);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            int days = (int) (end.toEpochDay() - start.toEpochDay() + 1);
            String[] dateArray = new String[days];

            for(int i = 0; i < days;i++) {
                dateArray[i] = start.plusDays(i).format(formatter);
            }

            Elements allTables = new Elements();

            for(int j = 0; j < dateArray.length; j++) {
                driver.get("https://m.sports.naver.com/kbaseball/schedule/index?date=" + dateArray[j]);

                WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".MatchBox_match_item__3_D0Q")));

                String html = driver.getPageSource();

                Document doc = Jsoup.parse(html);

                Elements tables = doc.select(".MatchBox_match_item__3_D0Q");

                allTables.addAll(tables);
            }

            return allTables;

        } catch(Exception e) {
            throw new CommonException(CommonError.CRAWLING_ERROR);
        } finally {
            driver.quit();
        }
    }

    @Transactional
    @Scheduled(cron = " 0 0 2 * * *")
    public List<GameResultDTO> selectTeamInfo() {
        Elements baseballInfo = crawlingTeamInfo();
        List<String> homeTeamNames = extractHomeTeamName(baseballInfo);
        List<GameEntity> existTeams = findByHomeTeamName(homeTeamNames);
        teamGroupToEntity(baseballInfo, existTeams);

        return existTeams.stream().map(GameResultDTO::new).toList();
    }

    private List<GameEntity> findByHomeTeamName(List<String> homeTeamNames) {
        return gameRepository.findByHomeTeamIn(homeTeamNames);
    }

    private List<String> extractHomeTeamName(Elements baseballInfo) {
        return baseballInfo.stream()
                .flatMap(baseball -> new GameEntity()
                        .crawledToGameEntity(baseball).stream()
                        .map(GameEntity::getHomeTeam))
                .toList();
    }

    private void processTeam(GameEntity newGameEntity, List<GameEntity> existGames) {
        existGames.stream().filter(existing -> existing.getHomeTeam().equals(newGameEntity.getHomeTeam()))
                .findFirst().ifPresentOrElse(existingGame -> existingGame.updateGame(newGameEntity), () -> existGames.add(newGameEntity));
    }

    private void teamGroupToEntity(Elements baseballInfo, List<GameEntity> existTeams) {
        baseballInfo.forEach(baseballTeam -> {
            List<GameEntity> newGameEntities= new GameEntity().crawledToGameEntity(baseballTeam);
            for (GameEntity entity : newGameEntities) {
                processTeam(entity, existTeams);
            }
        });
        saveTeams(existTeams);
    }

    private void saveTeams(List<GameEntity> existTeams) {
        Optional.ofNullable(existTeams).filter(teams -> !teams.isEmpty())
                .ifPresentOrElse(gameRepository::saveAll, () -> { throw new CommonException(CommonError.CRAWLING_ERROR); });
    }

}
