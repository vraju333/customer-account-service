package com.codeintelligence.customeraccount.controller;

import com.codeintelligence.customeraccount.model.Customer;
import com.codeintelligence.customeraccount.service.CustomerService;
import com.codeintelligence.customeraccount.xml.CustomerXmlRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService service;
    public CustomerController(CustomerService service) { this.service = service; }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> create(@RequestBody CustomerXmlRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer get(@PathVariable String customerId) { return service.get(customerId); }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Customer> getAll() { return service.getAll(); }

    @PutMapping(value = "/{customerId}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer replace(@PathVariable String customerId, @RequestBody CustomerXmlRequest request) { return service.replace(customerId, request); }

    @PatchMapping(value = "/{customerId}/account-type", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer updateAccountType(@PathVariable String customerId, @RequestParam String accountType) { return service.updateAccountType(customerId, accountType); }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String customerId) { service.delete(customerId); }
}
