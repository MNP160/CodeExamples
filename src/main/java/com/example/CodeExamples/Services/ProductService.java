package com.example.CodeExamples.Services;

import com.example.CodeExamples.DTOs.ProductCreateDto;
import com.example.CodeExamples.DTOs.ProductResponseDto;
import com.example.CodeExamples.Models.Product;
import com.example.CodeExamples.Repositories.ProductRepository;
import com.example.CodeExamples.Util.Converter;
import com.example.CodeExamples.Util.FilterConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {


     private ProductRepository _productRepository;
     private Converter _converter;

    @Autowired
    public void ProductService(ProductRepository productRepository, Converter converter){
        this._productRepository = productRepository;
        this._converter=converter;
    }


    public List<ProductResponseDto> getProducts(){
       return _productRepository.findAll().stream().map(x->_converter.convertToDto(x)).collect(Collectors.toList());
    }

    public Optional<ProductResponseDto> getProductById(Long id){
        return _productRepository.findById(id).map(x->_converter.convertToDto(x));
    }

    public ProductResponseDto createProduct(ProductCreateDto productCreate){
        Product created = _productRepository.save(Product.
                builder().description(productCreate.getDescription()).name(productCreate.getName()).price(productCreate.getPrice()).build());
        return _converter.convertToDto(created);
    }
    public List<ProductResponseDto> getProductsByFilters(String filters){  //lets say filters follow pattern ?filters=productPrice:5.11,productName:name1
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        List<String> productFilters = List.of(filters.split("="));
        productFilters = List.of(productFilters.get(1).split(","));
        for (int i =0 ; i <productFilters.size(); i++){
            if (productFilters.get(i).equals(FilterConstants.PRODUCT_PRICE)){
                productResponseDtos.addAll(_converter.
                        convertListToDtoList(_productRepository.
                                findByPrice(
                                        Double.valueOf(
                                                productFilters.get(i).split(":")[1]))));
            }else if (productFilters.get(i).equals(FilterConstants.PRODUCT_NAME)){
                productResponseDtos.addAll(_converter.
                        convertListToDtoList(_productRepository.
                                findByNameContaining(
                                                productFilters.get(i).split(":")[1])));
            }
        }
        return productResponseDtos;
    }

    public Optional<ProductResponseDto> updateProduct(long id, ProductCreateDto productCreateDto){

        Optional<Product> existingProduct = _productRepository.findById(id);
        if (existingProduct.isPresent()){
                Product updatedProduct = existingProduct.get();
                updatedProduct.setName(productCreateDto.getName());
                updatedProduct.setDescription(productCreateDto.getDescription());
                updatedProduct.setPrice(productCreateDto.getPrice());
                return Optional.of(_converter.convertToDto(_productRepository.save(updatedProduct)));

        }else {
            return Optional.empty();
        }

    }

    public boolean deleteProductById(long id){
    try {
        _productRepository.deleteById(id);
        return true;
    }catch (Exception ex){
        System.out.println("Exception is " +  ex.getMessage());
        return false;
    }

    }

    public boolean deleteAllProducts(){
        try {
            _productRepository.deleteAll();
            return true;
        }catch (Exception ex){
            System.out.println("Exception is " +  ex.getMessage());
            return false;
        }
    }





}
