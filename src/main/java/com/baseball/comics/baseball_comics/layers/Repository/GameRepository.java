package com.baseball.comics.baseball_comics.layers.Repository;

import com.baseball.comics.baseball_comics.layers.dto.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<GameEntity, Long> {

    List<GameEntity> findByHomeTeamIn(List<String> homeTeamNames);

    GameEntity findByHomeTeam(String homeTeam);
}
