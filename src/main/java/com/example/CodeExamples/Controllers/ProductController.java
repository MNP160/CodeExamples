package com.example.CodeExamples.Controllers;

import com.example.CodeExamples.DTOs.ProductCreateDto;
import com.example.CodeExamples.DTOs.ProductResponseDto;
import com.example.CodeExamples.Models.Product;
import com.example.CodeExamples.Repositories.ProductRepository;
import com.example.CodeExamples.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api")
@CrossOrigin(origins = "http://localhost:8081")
public class ProductController {


    ProductService _productService;

    @Autowired
    public ProductController(ProductService productService) {
        this._productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(@RequestParam(required = false) String filters) {
        try {
            List<ProductResponseDto> products = new ArrayList<>();

            if (filters == null)
                products = _productService.getProducts();
            else {
                products = _productService.getProductsByFilters(filters);
            }
            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Exception is " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") long id) {
        Optional<ProductResponseDto> productDto = _productService.getProductById(id);
        return productDto.map(productResponseDto ->
                        new ResponseEntity<>(productResponseDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductCreateDto product) {
        try {
            ProductResponseDto createdProduct = _productService.createProduct(product);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") long id, @RequestBody ProductCreateDto product) {

        Optional<ProductResponseDto> productResponse = _productService.updateProduct(id, product);
        if (productResponse.isPresent()) {
            return new ResponseEntity<>(productResponse.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {

        boolean result = _productService.deleteProductById(id);
        if (result)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @DeleteMapping("/products")
    public ResponseEntity<HttpStatus> deleteAllProducts() {

        boolean result = _productService.deleteAllProducts();
        if (result)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
