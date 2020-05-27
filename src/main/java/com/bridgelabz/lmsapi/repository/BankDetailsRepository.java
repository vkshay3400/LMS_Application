package com.bridgelabz.lmsapi.repository;

import com.bridgelabz.lmsapi.model.BankDetailsDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankDetailsRepository extends JpaRepository<BankDetailsDao, Long> {

}
