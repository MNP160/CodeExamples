package com.example.CodeExamples.DTOs;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDto {


    private String name;

    private String description;

    private Double price;

}
