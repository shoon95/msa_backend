package com.sparta.msa_exam.order.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="product-service")
public interface ProductFeignController {

    @GetMapping("/products")
    public String getProducts(@RequestParam List<Long> productIds);

    @GetMapping("/products/{productId}")
    public String getProduct(@PathVariable Long productId);
}
