package com.baseball.comics.baseball_comics.layers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameResultDTO {

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

    public GameResultDTO(GameEntity gameEntity) {
        this.date = gameEntity.getDate();
        this.status = gameEntity.getStatus();
        this.homeTeam = gameEntity.getHomeTeam();
        this.awayTeam = gameEntity.getAwayTeam();
        this.homeScore = gameEntity.getHomeScore();
        this.awayScore = gameEntity.getAwayScore();
        this.homePitcher = gameEntity.getHomePitcher();
        this.awayPitcher = gameEntity.getAwayPitcher();
        this.thumbnail = gameEntity.getThumbnail();
        this.homeImage = gameEntity.getHomeImage();
        this.awayImage = gameEntity.getAwayImage();
    }
}
