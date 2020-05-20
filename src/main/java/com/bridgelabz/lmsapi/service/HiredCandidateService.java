package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.model.DAOCandidate;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface HiredCandidateService {
    List getHiredCandidate(String filepath) throws IOException;

    void saveCandidateDetails(List candidateList) throws IOException;

    List getList();

    //    public DAOCandidate findByFirstName(String name);
//    Optional<DAOCandidate> findByFirstName(String name);
}
