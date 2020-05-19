package com.bridgelabz.lmsapi.repository;

import com.bridgelabz.lmsapi.model.DAOCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HiredCandidateRepository extends JpaRepository<DAOCandidate, Integer> {
    @Query("select u from user u where u.first_name = ?1")
    DAOCandidate findByFirst_name(String first_name);
}
