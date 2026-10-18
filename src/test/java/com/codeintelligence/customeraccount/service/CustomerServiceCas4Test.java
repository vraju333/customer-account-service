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

class CustomerServiceCas4Test {
    private final CustomerAccountRepository repository = mock(CustomerAccountRepository.class);
    private final CustomerService service = new CustomerService(repository, new CustomerXmlMapper(), new CustomerJpaMapper());
    private CustomerXmlRequest request() {
        CustomerXmlRequest r = new CustomerXmlRequest();
        r.setCustomerId("C-200"); r.setFirstName("Demo"); r.setLastName("User");
        r.setEmail("demo@example.com"); r.setCountryCode("IN"); r.setAccountType("STANDARD");
        return r;
    }
    private void allowSave() { when(repository.save(any(CustomerAccountEntity.class))).thenAnswer(inv -> inv.getArgument(0)); }

    @Test void createAcceptsEnterpriseAndNormalizesCase() {
        allowSave(); CustomerXmlRequest r = request(); r.setAccountType(" enterprise ");
        assertEquals("ENTERPRISE", service.create(r).getAccountType());
    }
    @Test void createRejectsUnsupportedAccountType() {
        CustomerXmlRequest r = request(); r.setAccountType("GOLD");
        assertThrows(IllegalArgumentException.class, () -> service.create(r));
        verify(repository, never()).save(any(CustomerAccountEntity.class));
    }
    @Test void replaceAcceptsEnterprise() {
        when(repository.existsById("C-200")).thenReturn(true); allowSave();
        CustomerXmlRequest r = request(); r.setAccountType("ENTERPRISE");
        assertEquals("ENTERPRISE", service.replace("C-200", r).getAccountType());
    }
    @Test void updateAccountTypeAcceptsEnterprise() {
        CustomerAccountEntity e = new CustomerAccountEntity();
        e.setCustomerId("C-200"); e.setFirstName("Demo"); e.setLastName("User");
        e.setEmail("demo@example.com"); e.setCountryCode("IN"); e.setAccountType("STANDARD");
        when(repository.findById("C-200")).thenReturn(java.util.Optional.of(e)); allowSave();
        assertEquals("ENTERPRISE", service.updateAccountType("C-200", " enterprise ").getAccountType());
    }
}
