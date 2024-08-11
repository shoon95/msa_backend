package com.sparta.msa_exam.order.exception;

public class ProductDoesNotExistException extends RuntimeException{

    public ProductDoesNotExistException(String message) {
        super(message);
    }
}
