package com.example.CodeExamples.Util;

import com.example.CodeExamples.DTOs.ProductResponseDto;
import com.example.CodeExamples.Models.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Converter {

    public ProductResponseDto convertToDto(Product product){
        return ProductResponseDto.builder().description(product.getDescription()).name(product.getName()).build();
    }


    public List<ProductResponseDto> convertListToDtoList(List<Product> products){
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
