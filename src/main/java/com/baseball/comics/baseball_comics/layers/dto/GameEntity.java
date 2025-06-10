package com.baseball.comics.baseball_comics.layers.dto;

import jakarta.persistence.*;
import lombok.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="t_team")
public class GameEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;
    private String status;
    private String homeTeam;
    private String awayTeam;
    private String homeScore;
    private String awayScore;
    private String homePitcher;
    private String awayPitcher;
    private String thumbnail;
    private String homeImage;
    private String awayImage;

    public List<GameEntity> crawledToGameEntity(Element baseballInfo) {
        List<GameEntity> entities = new ArrayList<>();
        Elements dataElements = baseballInfo.select(".MatchBox_match_item__3_D0Q");
        for(Element dataElement : dataElements) {
            GameEntity entity = new GameEntity();
            entity.date = getTextSafe(dataElement.selectFirst(".MatchBox_time__nIEfd")).split("간")[1];
            entity.status = getTextSafe(dataElement.selectFirst(".MatchBox_status__2pbzi"));
            entity.awayTeam = getTextSafe(dataElement.selectFirst("span.MatchBoxHeadToHeadArea_team__40JQL"));
            entity.homeTeam = getTextSafe(dataElement.selectFirst(".MatchBox_match_area__39dEr span.MatchBoxHeadToHeadArea_team__40JQL:last-child"));
            entity.awayScore = getTextSafe(dataElement.selectFirst(".MatchBox_match_area__39dEr .MatchBoxHeadToHeadArea_score__e2D7k:first-child"));
            entity.homeScore = getTextSafe(dataElement.selectFirst(".MatchBox_match_area__39dEr .MatchBoxHeadToHeadArea_score__e2D7k:last-child"));
            entity.homePitcher = "폰세";
            entity.awayPitcher = "정우주";
            entity.thumbnail = "";
            entity.homeImage = "";
            entity.awayImage = "";
            entities.add(entity);
        }

        return entities;
    }

    private String getTextSafe(Element element) {
        return (element != null && !element.text().isEmpty()) ? element.text() : "정보 확인 중입니다..";
    }

    public void updateGame(GameEntity newGameEntity) {
        this.date = newGameEntity.date;
        this.status = newGameEntity.status;
        this.awayTeam = newGameEntity.awayTeam;
        this.homeTeam = newGameEntity.homeTeam;
        this.awayScore = newGameEntity.awayScore;
        this.homeScore = newGameEntity.homeScore;
        this.awayPitcher = newGameEntity.awayPitcher;
        this.homePitcher = newGameEntity.homePitcher;
        this.thumbnail = newGameEntity.thumbnail;
        this.awayImage = newGameEntity.awayImage;
        this.homeImage = newGameEntity.homeImage;
    }
}
