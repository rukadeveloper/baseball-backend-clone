package com.baseball.comics.baseball_comics.layers.controller;

import com.baseball.comics.baseball_comics.layers.dto.common.ApiResponseDTO;
import com.baseball.comics.baseball_comics.layers.dto.common.MessageType;
import com.baseball.comics.baseball_comics.layers.entity.Product;
import com.baseball.comics.baseball_comics.layers.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/api/products")
    @CrossOrigin(origins = "http://localhost:3000")
    public ApiResponseDTO<List<Product>> getProduct() {
        List<Product> products = productService.get();
        return ApiResponseDTO.success(MessageType.RETRIEVE, products);
    }
}
