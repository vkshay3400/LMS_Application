package com.bridgelabz.lmsapi.repository;

import com.bridgelabz.lmsapi.model.CandidateDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HiredCandidateRepository extends JpaRepository<CandidateDao, Long> {
}
