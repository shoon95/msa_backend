package com.sparta.msa_exam.product.exception;

public class ProductDoesNotExistException extends RuntimeException{
    public ProductDoesNotExistException(String message) {
        super(message);
    }
}
