
package com.codeintelligence.customeraccount.service;

import com.codeintelligence.customeraccount.exception.CustomerNotFoundException;
import com.codeintelligence.customeraccount.mapper.CustomerJpaMapper;
import com.codeintelligence.customeraccount.mapper.CustomerXmlMapper;
import com.codeintelligence.customeraccount.model.Customer;
import com.codeintelligence.customeraccount.repository.CustomerAccountRepository;
import com.codeintelligence.customeraccount.xml.CustomerXmlRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class CustomerService {

    private static final Pattern EMAIL =
            Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    // CAS-2: Country code must contain exactly two letters.
    private static final Pattern COUNTRY =
            Pattern.compile("^[A-Z]{2}$");

    private static final Set<String> ACCOUNT_TYPES =
            Set.of("STANDARD", "PREMIUM", "BUSINESS");

    private final CustomerAccountRepository repository;
    private final CustomerXmlMapper xmlMapper;
    private final CustomerJpaMapper jpaMapper;

    public CustomerService(
            CustomerAccountRepository repository,
            CustomerXmlMapper xmlMapper,
            CustomerJpaMapper jpaMapper) {
        this.repository = repository;
        this.xmlMapper = xmlMapper;
        this.jpaMapper = jpaMapper;
    }

    @Transactional
    public Customer create(CustomerXmlRequest request) {
        Customer customer = xmlMapper.toModel(request);
        validate(customer);

        if (repository.existsById(customer.getCustomerId()))
            throw new IllegalArgumentException(
                    "Customer already exists: " + customer.getCustomerId());

        return jpaMapper.toModel(
                repository.save(jpaMapper.toEntity(customer)));
    }

    @Transactional(readOnly = true)
    public Customer get(String customerId) {
        return repository.findById(customerId)
                .map(jpaMapper::toModel)
                .orElseThrow(() ->
                        new CustomerNotFoundException(customerId));
    }

    @Transactional(readOnly = true)
    public List<Customer> getAll() {
        return repository.findAll()
                .stream()
                .map(jpaMapper::toModel)
                .toList();
    }

    @Transactional
    public Customer replace(String customerId, CustomerXmlRequest request) {
        if (!repository.existsById(customerId))
            throw new CustomerNotFoundException(customerId);

        Customer customer = xmlMapper.toModel(request);

        if (customer.getCustomerId() == null ||
                customer.getCustomerId().isBlank())
            customer.setCustomerId(customerId);

        if (!customerId.equals(customer.getCustomerId()))
            throw new IllegalArgumentException(
                    "Path customerId must match XML CustomerId");

        validate(customer);

        return jpaMapper.toModel(
                repository.save(jpaMapper.toEntity(customer)));
    }

    @Transactional
    public Customer updateAccountType(
            String customerId, String accountType) {

        Customer customer = get(customerId);
        customer.setAccountType(
                accountType == null
                        ? null
                        : accountType.trim().toUpperCase());

        validate(customer);

        return jpaMapper.toModel(
                repository.save(jpaMapper.toEntity(customer)));
    }

    @Transactional
    public void delete(String customerId) {
        if (!repository.existsById(customerId))
            throw new CustomerNotFoundException(customerId);

        repository.deleteById(customerId);
    }

    private void validate(Customer c) {
        require(c.getCustomerId(), "customerId");
        require(c.getFirstName(), "firstName");
        require(c.getLastName(), "lastName");
        require(c.getEmail(), "email");
        require(c.getCountryCode(), "countryCode");
        require(c.getAccountType(), "accountType");

        if (!EMAIL.matcher(c.getEmail()).matches())
            throw new IllegalArgumentException("Invalid email");

        // CAS-2: Reject codes with fewer or more than two letters.
        if (!COUNTRY.matcher(c.getCountryCode()).matches())
            throw new IllegalArgumentException(
                    "countryCode must contain exactly 2 letters");

        if (!ACCOUNT_TYPES.contains(c.getAccountType()))
            throw new IllegalArgumentException(
                    "accountType must be STANDARD, PREMIUM, or BUSINESS");
    }

    private void require(String value, String field) {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException(
                    field + " is required");
    }
}
