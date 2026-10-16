package com.codeintelligence.customeraccount.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "CustomerAccount")
public class CustomerXmlRequest {
    @JacksonXmlProperty(localName = "CustomerId") private String customerId;
    @JacksonXmlProperty(localName = "FirstName") private String firstName;
    @JacksonXmlProperty(localName = "LastName") private String lastName;
    @JacksonXmlProperty(localName = "Email") private String email;
    @JacksonXmlProperty(localName = "CountryCode") private String countryCode;
    @JacksonXmlProperty(localName = "AccountType") private String accountType;

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
