package com.sparta.msa_exam.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProductToOrderRequestDto {
    private Long productId;
}
