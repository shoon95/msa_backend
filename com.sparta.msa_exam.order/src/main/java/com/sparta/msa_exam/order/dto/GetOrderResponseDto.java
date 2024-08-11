package com.sparta.msa_exam.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderResponseDto {
    private Long orderId;
    private List<Long> productIds = new ArrayList<>();
}
