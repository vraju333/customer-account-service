package com.codeintelligence.customeraccount.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CUSTOMER_ACCOUNT")
public class CustomerAccountEntity {
    @Id @Column(name = "CUSTOMER_ID", length = 50) private String customerId;
    @Column(name = "FIRST_NAME", nullable = false, length = 100) private String firstName;
    @Column(name = "LAST_NAME", nullable = false, length = 100) private String lastName;
    @Column(name = "EMAIL", nullable = false, length = 200) private String email;
    @Column(name = "COUNTRY_CODE", nullable = false, length = 3) private String countryCode;
    @Column(name = "ACCOUNT_TYPE", nullable = false, length = 30) private String accountType;

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }
    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }
}
