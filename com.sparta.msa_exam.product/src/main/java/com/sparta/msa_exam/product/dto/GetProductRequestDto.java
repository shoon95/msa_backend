package com.sparta.msa_exam.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetProductRequestDto {
    private List<Long> productIds = new ArrayList<>();
}
