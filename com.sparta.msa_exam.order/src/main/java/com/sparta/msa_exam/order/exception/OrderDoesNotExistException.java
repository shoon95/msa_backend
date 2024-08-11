package com.sparta.msa_exam.order.exception;

public class OrderDoesNotExistException extends RuntimeException{
    public OrderDoesNotExistException(String message) {
        super(message);
    }
}
