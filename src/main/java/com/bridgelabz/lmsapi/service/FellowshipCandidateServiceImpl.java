package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.dto.PersonalDetailsDto;
import com.bridgelabz.lmsapi.exception.LMSException;
import com.bridgelabz.lmsapi.model.HiredCandidateDao;
import com.bridgelabz.lmsapi.model.FellowshipDao;
import com.bridgelabz.lmsapi.repository.FellowshipCandidateRepository;
import com.bridgelabz.lmsapi.repository.HiredCandidateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class FellowshipCandidateServiceImpl implements FellowshipCandidateService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HiredCandidateRepository hiredCandidateRepository;

    @Autowired
    private FellowshipCandidateRepository fellowshipCandidateRepository;

    @Value("${upload.path}")
    private String path;

    // Method to save data in fellowship database table
    @Override
    public void getDetails() {
        try {
            List<HiredCandidateDao> list = hiredCandidateRepository.findAll();
            for (HiredCandidateDao candidate : list) {
                if (candidate.getStatus().matches("Accept")) {
                    FellowshipDao fellowshipDao = modelMapper
                            .map(candidate, FellowshipDao.class);
                    fellowshipCandidateRepository.save(fellowshipDao);
                }
            }
        } catch (LMSException e) {
            throw new LMSException(LMSException.exceptionType.INVALID_ID, e.getMessage());
        }
    }

    // Method to get count from fellowship database table
    @Override
    public int getFellowshipCount() {
        try {
            List list = fellowshipCandidateRepository.findAll();
            return list.size();
        } catch (LMSException e) {
            throw new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, e.getMessage());
        }
    }

    // Method to update profile
    @Override
    public void getUpdateDetails(PersonalDetailsDto personalDetailsDto, long id) {
        try {
            fellowshipCandidateRepository.findById(id).map(fellowshipDao -> {
                fellowshipDao.setBirthDate(personalDetailsDto.getBirthDate());
                fellowshipDao.setIsBirthDateVerified(personalDetailsDto.getIsBirthDateVerified());
                fellowshipDao.setParentName(personalDetailsDto.getParentName());
                fellowshipDao.setParentOccupation(personalDetailsDto.getParentOccupation());
                fellowshipDao.setParentMobileNumber(personalDetailsDto.getParentMobileNumber());
                fellowshipDao.setParentAnnualSalary(personalDetailsDto.getParentAnnualSalary());
                fellowshipDao.setLocalAddress(personalDetailsDto.getLocalAddress());
                fellowshipDao.setPermanentAddress(personalDetailsDto.getPermanentAddress());
                fellowshipDao.setJoiningDate(personalDetailsDto.getJoiningDate());
                fellowshipDao.setRemark(personalDetailsDto.getRemark());
                return fellowshipDao;
            }).map(fellowshipCandidateRepository::save);
        } catch (LMSException e) {
            throw new LMSException(LMSException.exceptionType.INVALID_ID, e.getMessage());
        }
    }

    // Method to upload documents
    @Override
    public void upload(MultipartFile file, long id) throws LMSException, IOException {
        fellowshipCandidateRepository.findById(id)
                .orElseThrow(() -> new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, "Data not found"));
        if (file.isEmpty())
            throw new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, "Failed to store empty file");
        File convertFile = new File(path + id + "-" + file.getOriginalFilename());
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        fout.close();
    }

}
