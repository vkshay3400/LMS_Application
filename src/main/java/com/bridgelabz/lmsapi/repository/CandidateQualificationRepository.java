package com.bridgelabz.lmsapi.repository;

import com.bridgelabz.lmsapi.model.CandidateQualificationDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateQualificationRepository extends JpaRepository<CandidateQualificationDao, Long> {

}
