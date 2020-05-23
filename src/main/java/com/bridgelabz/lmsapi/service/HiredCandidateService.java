package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.model.CandidateDao;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface HiredCandidateService {

    void getHiredCandidate(MultipartFile filepath) throws IOException;
    List getList();
    Optional<CandidateDao> findById(Long id);

}
