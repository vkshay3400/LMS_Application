package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.model.CandidateDao;
import com.bridgelabz.lmsapi.model.FellowshipDao;
import com.bridgelabz.lmsapi.repository.FellowshipCandidateRepository;
import com.bridgelabz.lmsapi.repository.HiredCandidateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FellowshipCandidateServiceImpl implements FellowshipCandidateService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HiredCandidateRepository hiredCandidateRepository;

    @Autowired
    private FellowshipCandidateRepository fellowshipCandidateRepository;

    // Method to save data in fellowship database table
    @Override
    public void getUpdateDetails() {
        List<CandidateDao> list = hiredCandidateRepository.findAll();
        for (CandidateDao candidate : list) {
            if (candidate.getStatus().matches("Accept")) {
                FellowshipDao fellowshipDao = modelMapper
                        .map(candidate, FellowshipDao.class);
                fellowshipCandidateRepository.save(fellowshipDao);
            }
        }
    }

    // Method to get count from fellowship database table
    @Override
    public int getFellowshipCount() {
        List list = fellowshipCandidateRepository.findAll();
        return list.size();

    }
}
