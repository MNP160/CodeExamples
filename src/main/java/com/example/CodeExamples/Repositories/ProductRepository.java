package com.example.CodeExamples.Repositories;

import com.example.CodeExamples.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository  extends JpaRepository<Product, Long> {

    List<Product> findByNameContaining(String name);

    List<Product> findByPrice(Double price);
}
