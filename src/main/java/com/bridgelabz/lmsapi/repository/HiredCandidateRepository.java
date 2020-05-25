package com.bridgelabz.lmsapi.repository;

import com.bridgelabz.lmsapi.model.CandidateDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HiredCandidateRepository extends JpaRepository<CandidateDao, Long> {
    Optional<CandidateDao> findByEmail(String email);
}
