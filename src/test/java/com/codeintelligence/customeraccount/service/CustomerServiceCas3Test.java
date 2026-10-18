package com.codeintelligence.customeraccount.service;

import com.codeintelligence.customeraccount.entity.CustomerAccountEntity;
import com.codeintelligence.customeraccount.mapper.CustomerJpaMapper;
import com.codeintelligence.customeraccount.mapper.CustomerXmlMapper;
import com.codeintelligence.customeraccount.repository.CustomerAccountRepository;
import com.codeintelligence.customeraccount.xml.CustomerXmlRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceCas3Test {
    private final CustomerAccountRepository repository = mock(CustomerAccountRepository.class);
    private final CustomerService service = new CustomerService(repository, new CustomerXmlMapper(), new CustomerJpaMapper());
    private CustomerXmlRequest request() {
        CustomerXmlRequest r = new CustomerXmlRequest();
        r.setCustomerId("C-200"); r.setFirstName("Demo"); r.setLastName("User");
        r.setEmail("demo@example.com"); r.setCountryCode("IN"); r.setAccountType("STANDARD");
        return r;
    }
    private void allowSave() { when(repository.save(any(CustomerAccountEntity.class))).thenAnswer(inv -> inv.getArgument(0)); }

    @Test void createRejectsMalformedEmails() {
        for (String email : new String[]{"a..b@example.com", "a@example", "a@.example.com", "a@example.c", "a b@example.com"}) {
            CustomerXmlRequest r = request(); r.setEmail(email);
            assertEquals("Invalid email", assertThrows(IllegalArgumentException.class, () -> service.create(r)).getMessage());
        }
        verify(repository, never()).save(any(CustomerAccountEntity.class));
    }
    @Test void createAcceptsValidEmailAndNormalizesCase() {
        allowSave(); CustomerXmlRequest r = request(); r.setEmail("  FIRST.LAST+TAG@EXAMPLE.COM  ");
        assertEquals("first.last+tag@example.com", service.create(r).getEmail());
    }
    @Test void replaceRejectsMalformedEmail() {
        when(repository.existsById("C-200")).thenReturn(true);
        CustomerXmlRequest r = request(); r.setEmail("bad..email@example.com");
        assertThrows(IllegalArgumentException.class, () -> service.replace("C-200", r));
        verify(repository, never()).save(any(CustomerAccountEntity.class));
    }
}
