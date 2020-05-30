package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.dto.PersonalDetailsDto;
import com.bridgelabz.lmsapi.exception.LMSException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FellowshipCandidateService {

    void getDetails();

    int getFellowshipCount();

    void getUpdateDetails(PersonalDetailsDto personalDetailsDto, long id);

    void upload(MultipartFile file, long id) throws LMSException, IOException;

}

