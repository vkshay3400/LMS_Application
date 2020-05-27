package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.dto.CandidateQualificationDto;
import com.bridgelabz.lmsapi.exception.LMSException;
import com.bridgelabz.lmsapi.model.CandidateQualificationDao;
import com.bridgelabz.lmsapi.repository.CandidateQualificationRepository;
import com.bridgelabz.lmsapi.repository.FellowshipCandidateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CandidateQualificationServiceImpl implements CandidateQualificationService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CandidateQualificationRepository candidateQualificationRepository;

    @Autowired
    private FellowshipCandidateRepository fellowshipCandidateRepository;

    // Method to update education details
    @Override
    public void saveEducationDetails(CandidateQualificationDto candidateQualificationDto) {
        fellowshipCandidateRepository.findById(candidateQualificationDto.getCandidateId())
                .orElseThrow(() -> new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, "Data not found"));
        CandidateQualificationDao candidateQualificationDao = modelMapper
                .map(candidateQualificationDto, CandidateQualificationDao.class);
        candidateQualificationDao.setCreatorStamp(LocalDateTime.now());
        candidateQualificationRepository.save(candidateQualificationDao);
    }
}
