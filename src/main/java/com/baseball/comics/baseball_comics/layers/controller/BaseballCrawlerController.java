package com.baseball.comics.baseball_comics.layers.controller;

import com.baseball.comics.baseball_comics.layers.dto.GameResultDTO;
import com.baseball.comics.baseball_comics.layers.dto.common.ApiResponseDTO;
import com.baseball.comics.baseball_comics.layers.dto.common.MessageType;
import com.baseball.comics.baseball_comics.layers.service.BaseballCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class BaseballCrawlerController {
    @Autowired
    private final BaseballCrawlerService baseballCrawlerService;

    public BaseballCrawlerController(BaseballCrawlerService baseballCrawlerService) {
        this.baseballCrawlerService = baseballCrawlerService;
    }


    @GetMapping("/baseball/crawl")
    @CrossOrigin (origins = "http://localhost:3000")
    public ApiResponseDTO<List<GameResultDTO>> getCrawl() {
        List<GameResultDTO> result = baseballCrawlerService.selectTeamInfo();
        return ApiResponseDTO.success(MessageType.RETRIEVE, result);
    }
}
