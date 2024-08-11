package com.sparta.msa_exam.product.controller;

import com.sparta.msa_exam.product.domain.Product;
import com.sparta.msa_exam.product.dto.*;
import com.sparta.msa_exam.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ResponseDto<AddProductResponseDto>> addProduct(@RequestBody AddProductRequestDto addProductRequestDto) {

        ResponseDto<AddProductResponseDto> addProductResponse = productService.addProduct(addProductRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(addProductResponse);
    }

//    @GetMapping
//    public ResponseEntity<ResponseDto<List<ProductResponseDto>>> getAllProducts() {
//        ResponseDto<List<ProductResponseDto>> allProductsResponse = productService.getAllProducts();
//        return ResponseEntity.status(HttpStatus.OK).body(allProductsResponse);
//    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<ProductResponseDto>>> getProducts(@RequestParam List<Long> productIds) {
        ResponseDto<List<ProductResponseDto>> productsResponseDto = productService.getProducts(productIds);
        return ResponseEntity.status(HttpStatus.OK).body(productsResponseDto);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ResponseDto<ProductResponseDto>> getProduct(@PathVariable Long productId) {
        ResponseDto<ProductResponseDto> productsResponseDto = productService.getProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(productsResponseDto);
    }
}
