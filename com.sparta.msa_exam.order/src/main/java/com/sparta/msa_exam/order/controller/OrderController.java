package com.sparta.msa_exam.order.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.msa_exam.order.dto.*;
import com.sparta.msa_exam.order.feign.client.ProductFeignController;
import com.sparta.msa_exam.order.service.OrderService;
import jakarta.servlet.http.HttpServlet;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ResponseDto<CreateOrderResponseDto>> createOrder(@RequestBody CreateOrderRequestDto createOrderRequestDto) throws JsonProcessingException {

        // product app 에서 product 정보 받아오기
        String productJson = orderService.getProducts(createOrderRequestDto.getProductIds());

        ResponseDto<CreateOrderResponseDto> createOrderResponseDto = orderService.createOrder(createOrderRequestDto.getName(), createOrderRequestDto.getProductIds(), productJson);
        return ResponseEntity.status(HttpStatus.CREATED).body(createOrderResponseDto);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<ResponseDto<AddProductToOrderResponseDto>> addProductToOrder(@PathVariable Long orderId,
                                                                                       @RequestBody AddProductToOrderRequestDto addProductToOrderRequestDto) throws JsonProcessingException {

        String productJson = orderService.getProduct(addProductToOrderRequestDto.getProductId());

        ResponseDto<AddProductToOrderResponseDto> addProductToOrderResponseDto = orderService.addProductToOrder(orderId, productJson);
        return ResponseEntity.status(HttpStatus.CREATED).body(addProductToOrderResponseDto);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseDto<GetOrderResponseDto>> getOrder(@PathVariable Long orderId) {
        ResponseDto<GetOrderResponseDto> orderResponseDto = orderService.getOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);
    }
}
