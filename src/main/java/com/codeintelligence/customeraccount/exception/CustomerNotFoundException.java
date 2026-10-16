package com.codeintelligence.customeraccount.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String customerId) { super("Customer not found: " + customerId); }
}
