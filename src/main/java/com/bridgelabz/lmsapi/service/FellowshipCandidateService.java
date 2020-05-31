package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.dto.BankDetailsDto;
import com.bridgelabz.lmsapi.dto.CandidateQualificationDto;
import com.bridgelabz.lmsapi.dto.PersonalDetailsDto;
import com.bridgelabz.lmsapi.exception.LMSException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FellowshipCandidateService {

    boolean getDetails();

    int getFellowshipCount();

    boolean getUpdateDetails(PersonalDetailsDto personalDetailsDto, long id);

    void upload(MultipartFile file, long id) throws LMSException, IOException;

    File convertMultiPartToFile(MultipartFile file) throws IOException;

    String uploadFile(MultipartFile file, long id);

    boolean saveBankDetails(BankDetailsDto bankDetailsDto);

    boolean saveEducationDetails(CandidateQualificationDto candidateQualificationDto);

}

