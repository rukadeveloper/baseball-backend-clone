package com.baseball.comics.baseball_comics.layers.service;

import com.baseball.comics.baseball_comics.layers.Repository.ProductRepository;
import com.baseball.comics.baseball_comics.layers.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> get() {
        return productRepository.findAll();
    }
}
