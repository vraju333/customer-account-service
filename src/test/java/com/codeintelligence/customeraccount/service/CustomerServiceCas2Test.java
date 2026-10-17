
package com.codeintelligence.customeraccount.service;

import com.codeintelligence.customeraccount.entity.CustomerAccountEntity;
import com.codeintelligence.customeraccount.mapper.CustomerJpaMapper;
import com.codeintelligence.customeraccount.mapper.CustomerXmlMapper;
import com.codeintelligence.customeraccount.model.Customer;
import com.codeintelligence.customeraccount.repository.CustomerAccountRepository;
import com.codeintelligence.customeraccount.xml.CustomerXmlRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceCas2Test {

    private final CustomerAccountRepository repository =
            mock(CustomerAccountRepository.class);

    private final CustomerService service =
            new CustomerService(
                    repository,
                    new CustomerXmlMapper(),
                    new CustomerJpaMapper());

    private CustomerXmlRequest request(String code) {
        CustomerXmlRequest request = new CustomerXmlRequest();
        request.setCustomerId("C-100");
        request.setFirstName("Test");
        request.setLastName("Customer");
        request.setEmail("test@example.com");
        request.setCountryCode(code);
        request.setAccountType("STANDARD");
        return request;
    }

    private void allowSave() {
        when(repository.save(any(CustomerAccountEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void createAcceptsTwoLettersAndNormalizesLowercase() {
        allowSave();

        Customer customer = service.create(request(" in "));

        assertEquals("IN", customer.getCountryCode());
        verify(repository).save(any(CustomerAccountEntity.class));
    }

    @Test
    void createRejectsOneAndThreeLetterCodes() {
        for (String code :
                new String[]{"I", "IND", "USA", "123", "U1"}) {

            IllegalArgumentException error =
                    assertThrows(IllegalArgumentException.class,
                            () -> service.create(request(code)));

            assertEquals(
                    "countryCode must contain exactly 2 letters",
                    error.getMessage());
        }

        verify(repository, never())
                .save(any(CustomerAccountEntity.class));
    }

    @Test
    void replaceAcceptsLowercaseAndStoresUppercase() {
        when(repository.existsById("C-100")).thenReturn(true);
        allowSave();

        Customer customer =
                service.replace("C-100", request("us"));

        assertEquals("US", customer.getCountryCode());
    }

    @Test
    void replaceRejectsThreeLettersWithoutSaving() {
        when(repository.existsById("C-100")).thenReturn(true);

        IllegalArgumentException error =
                assertThrows(IllegalArgumentException.class,
                        () -> service.replace(
                                "C-100", request("USA")));

        assertEquals(
                "countryCode must contain exactly 2 letters",
                error.getMessage());

        verify(repository, never())
                .save(any(CustomerAccountEntity.class));
    }

    @Test
    void accountTypeUpdateRevalidatesExistingCountryCode() {
        CustomerAccountEntity existing =
                new CustomerAccountEntity();

        existing.setCustomerId("C-100");
        existing.setFirstName("Test");
        existing.setLastName("Customer");
        existing.setEmail("test@example.com");
        existing.setCountryCode("USA");
        existing.setAccountType("STANDARD");

        when(repository.findById("C-100"))
                .thenReturn(java.util.Optional.of(existing));

        assertThrows(IllegalArgumentException.class,
                () -> service.updateAccountType(
                        "C-100", "PREMIUM"));

        verify(repository, never())
                .save(any(CustomerAccountEntity.class));
    }
}
