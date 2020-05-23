package com.bridgelabz.lmsapi.repository;

import com.bridgelabz.lmsapi.model.CandidateDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HiredCandidateRepository extends JpaRepository<CandidateDao, Integer> {
    @Query("select h from hired_candidate h where h.firstName = ?1")
    Optional<CandidateDao> findByFirstName(String firstName);
}
