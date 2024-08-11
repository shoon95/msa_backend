package com.sparta.msa_exam.order.dto;

import com.sparta.msa_exam.order.domain.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderResponseDto {

    private String orderName;
    private List<String> productName = new ArrayList<>();
}
