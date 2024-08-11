package com.sparta.msa_exam.product.service;

import com.sparta.msa_exam.product.domain.Product;
import com.sparta.msa_exam.product.dto.AddProductRequestDto;
import com.sparta.msa_exam.product.dto.AddProductResponseDto;
import com.sparta.msa_exam.product.dto.ProductResponseDto;
import com.sparta.msa_exam.product.dto.ResponseDto;
import com.sparta.msa_exam.product.exception.ProductDoesNotExistException;
import com.sparta.msa_exam.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ResponseDto<AddProductResponseDto> addProduct(AddProductRequestDto addProductRequestDto) {

        Product product = Product.builder()
                .name(addProductRequestDto.getName())
                .supplyPrice(addProductRequestDto.getSupplyPrice())
                .build();

        productRepository.save(product);

        return ResponseDto.success("Product added successfully", new AddProductResponseDto(product.getId()));
    }


    @Transactional(readOnly = true)
    public ResponseDto<List<ProductResponseDto>> getProducts(List<Long> productIds) {
        List<Product> allProductsById = productRepository.findAllById(productIds);
        if (allProductsById.isEmpty()) {
            throw new ProductDoesNotExistException("product does not exist");
        } else {
            List<ProductResponseDto> productResponseDtos = allProductsById.stream()
                    .map(product -> new ProductResponseDto(product.getId(), product.getName(), product.getSupplyPrice()))
                    .collect(Collectors.toList());
            return ResponseDto.success("Successfully retrieved product", productResponseDtos);
        }
    }

    @Transactional(readOnly = true)
    public ResponseDto<ProductResponseDto> getProduct(Long productId) {
        Optional<Product> productById = productRepository.findById(productId);
        if (productById.isEmpty()) {
            throw new ProductDoesNotExistException("product does not exist");
        } else {
            ProductResponseDto productResponseDto = new ProductResponseDto(productById.get().getId(), productById.get().getName(), productById.get().getSupplyPrice());
            return ResponseDto.success("Successfully retrieved product", productResponseDto);
        }
    }
}
