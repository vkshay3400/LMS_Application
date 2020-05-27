package com.bridgelabz.lmsapi.repository;

import com.bridgelabz.lmsapi.model.HiredCandidateDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HiredCandidateRepository extends JpaRepository<HiredCandidateDao, Long> {
    Optional<HiredCandidateDao> findByEmail(String email);
}
