package com.codeintelligence.customeraccount.mapper;

import com.codeintelligence.customeraccount.model.Customer;
import com.codeintelligence.customeraccount.xml.CustomerXmlRequest;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class CustomerXmlMapper {
    public Customer toModel(CustomerXmlRequest source) {
        //addspace
        Customer target = new Customer();
        target.setCustomerId(trim(source.getCustomerId()));
        target.setFirstName(trim(source.getFirstName()));
        target.setLastName(trim(source.getLastName()));
        target.setEmail(lower(source.getEmail()));
        target.setCountryCode(upper(source.getCountryCode()));
        target.setAccountType(upper(source.getAccountType()));
        return target;
    }

    private String trim(String value) { return value == null ? null : value.trim(); }
    private String lower(String value) { return value == null ? null : value.trim().toLowerCase(Locale.ROOT); }
    private String upper(String value) { return value == null ? null : value.trim().toUpperCase(Locale.ROOT); }
}
