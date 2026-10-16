package com.codeintelligence.customeraccount.repository;

import com.codeintelligence.customeraccount.entity.CustomerAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAccountRepository extends JpaRepository<CustomerAccountEntity, String> { }
