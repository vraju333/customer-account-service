package com.codeintelligence.customeraccount.mapper;

import com.codeintelligence.customeraccount.entity.CustomerAccountEntity;
import com.codeintelligence.customeraccount.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerJpaMapper {
    public CustomerAccountEntity toEntity(Customer source) {
        CustomerAccountEntity target = new CustomerAccountEntity();
        target.setCustomerId(source.getCustomerId());
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setEmail(source.getEmail());
        if(null != source.getCountryCode()) {
            target.setCountryCode(source.getCountryCode());
        }
        target.setAccountType(source.getAccountType());
        return target;
    }

    public Customer toModel(CustomerAccountEntity source) {
        Customer target = new Customer();
        target.setCustomerId(source.getCustomerId());
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setEmail(source.getEmail());
        target.setCountryCode(source.getCountryCode());
        target.setAccountType(source.getAccountType());
        return target;
    }
}
