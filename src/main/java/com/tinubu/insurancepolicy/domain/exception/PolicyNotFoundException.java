package com.tinubu.insurancepolicy.domain.exception;

public class PolicyNotFoundException extends RuntimeException{
    public PolicyNotFoundException(String message) {
        super(message);
    }

    public PolicyNotFoundException(Integer id) {
        super("Insurance policy not found with ID : " + id);
    }
}
