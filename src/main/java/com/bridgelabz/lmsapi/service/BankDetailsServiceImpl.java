package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.dto.BankDetailsDto;
import com.bridgelabz.lmsapi.exception.LMSException;
import com.bridgelabz.lmsapi.model.BankDetailsDao;
import com.bridgelabz.lmsapi.repository.BankDetailsRepository;
import com.bridgelabz.lmsapi.repository.FellowshipCandidateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BankDetailsServiceImpl implements BankDetailsService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BankDetailsRepository bankDetailsRepository;

    @Autowired
    private FellowshipCandidateRepository fellowshipCandidateRepository;

    // Method to update bank details
    @Override
    public void saveBankDetails(BankDetailsDto bankDetailsDto) {
        fellowshipCandidateRepository.findById(bankDetailsDto.getCandidateId())
                .orElseThrow(() -> new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, "Data not found"));
        BankDetailsDao bankDetailsDao = modelMapper
                .map(bankDetailsDto, BankDetailsDao.class);
        bankDetailsDao.setCreatorStamp(LocalDateTime.now());
        bankDetailsRepository.save(bankDetailsDao);
    }
}
