package com.sparta.msa_exam.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.msa_exam.order.domain.Order;
import com.sparta.msa_exam.order.domain.OrderProduct;
import com.sparta.msa_exam.order.dto.*;
import com.sparta.msa_exam.order.exception.GetProductException;
import com.sparta.msa_exam.order.exception.OrderDoesNotExistException;
import com.sparta.msa_exam.order.exception.ProductDoesNotExistException;
import com.sparta.msa_exam.order.feign.client.ProductFeignController;
import com.sparta.msa_exam.order.repository.OrderProductRepository;
import com.sparta.msa_exam.order.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {


    private final ObjectMapper objectMapper;
    private final ProductFeignController productFeignController;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    @CircuitBreaker(name = "product-service")
    public String getProducts(List<Long> productIds) {
        // productIds가 있는지 검증
        try {
            return productFeignController.getProducts(productIds);
        } catch (Exception ex){
            throw new GetProductException(ex.getMessage());
        }
    }

    @CircuitBreaker(name = "product-service")
    public String getProduct(Long productId) {
        // productIds가 있는지 검증
        try {
            return productFeignController.getProduct(productId);
        } catch (Exception ex){
            throw new GetProductException("Product does not exist");
        }
    }

    @Transactional
    public ResponseDto<CreateOrderResponseDto> createOrder(String name, List<Long> productIds, String productJson) throws JsonProcessingException {

        ResponseDto<List<ProductResponseDto>> productResponseDto = objectMapper.readValue(productJson, new TypeReference<ResponseDto<List<ProductResponseDto>>>() {});
        List<ProductResponseDto> productList = productResponseDto.getData();

        // Order 생성
        Order order = Order.builder()
                .name(name)
                .build();

        // order-product 생성
        for (Long productId : productIds) {
            OrderProduct orderProduct = OrderProduct.builder()
                    .productId(productId)
                    .build();
            order.addOrderProduct(orderProduct);
            orderProductRepository.save(orderProduct);
        }

        orderRepository.save(order);


        CreateOrderResponseDto createOrderResponseDto = new CreateOrderResponseDto(name, productList.stream().map(product -> product.getName()).collect(Collectors.toList()));
        return ResponseDto.success("Order created successfully", createOrderResponseDto);
    }


    @Transactional
    public ResponseDto<AddProductToOrderResponseDto> addProductToOrder(Long orderId, String productJson) throws JsonProcessingException {
        ResponseDto<ProductResponseDto> productResponseDto = objectMapper.readValue(productJson, new TypeReference<ResponseDto<ProductResponseDto>>() {});
        ProductResponseDto product = productResponseDto.getData();

        // Order 생성
        Order orderById = orderRepository.findById(orderId).get();
        OrderProduct orderProduct = OrderProduct.builder()
                .productId(product.getId())
                .build();
        orderById.addOrderProduct(orderProduct);
        orderProductRepository.save(orderProduct);
        orderRepository.save(orderById);

        AddProductToOrderResponseDto createOrderResponseDto = new AddProductToOrderResponseDto(orderById.getName(), product.getName());

        return ResponseDto.success("Product added to order successfully", createOrderResponseDto);
    }

    public ResponseDto<GetOrderResponseDto> getOrder(Long orderId) {
        Order orderById = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderDoesNotExistException("Order does not exist"));

        GetOrderResponseDto getOrderResponseDto = new GetOrderResponseDto(orderById.getId(), orderById.getProductIds().stream().map(product -> product.getId()).collect(Collectors.toList()));
        return ResponseDto.success("Successfully retrieved order", getOrderResponseDto);
    }
}
