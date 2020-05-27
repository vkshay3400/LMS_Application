package com.bridgelabz.lmsapi.repository;

import com.bridgelabz.lmsapi.model.FellowshipDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FellowshipCandidateRepository extends JpaRepository<FellowshipDao, Long> {

}
