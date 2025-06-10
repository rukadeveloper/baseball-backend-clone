package com.baseball.comics.baseball_comics.layers.Repository;

import com.baseball.comics.baseball_comics.layers.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();
}
