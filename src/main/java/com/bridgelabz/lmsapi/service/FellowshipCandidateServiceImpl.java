package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.dto.BankDetailsDto;
import com.bridgelabz.lmsapi.dto.CandidateQualificationDto;
import com.bridgelabz.lmsapi.dto.PersonalDetailsDto;
import com.bridgelabz.lmsapi.dto.UploadDocumentsDto;
import com.bridgelabz.lmsapi.exception.LMSException;
import com.bridgelabz.lmsapi.model.*;
import com.bridgelabz.lmsapi.repository.*;
import com.cloudinary.Cloudinary;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FellowshipCandidateServiceImpl implements FellowshipCandidateService {

    @Autowired
    private Cloudinary cloudinaryConfig;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HiredCandidateRepository hiredCandidateRepository;

    @Autowired
    private BankDetailsRepository bankDetailsRepository;

    @Autowired
    private CandidateQualificationRepository candidateQualificationRepository;

    @Autowired
    private FellowshipCandidateRepository fellowshipCandidateRepository;

    @Autowired
    private UploadDocumentsRepository uploadDocumentsRepository;

    @Value("${upload.path}")
    private String path;

    /**
     * Method to save data in fellowship database table
     *
     * @return
     */
    @Override
    public boolean getDetails() {
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
        return false;
    }

    /**
     * Method to get count from fellowship database table
     *
     * @return
     */
    @Override
    public int getFellowshipCount() {
        try {
            List list = fellowshipCandidateRepository.findAll();
            return list.size();
        } catch (LMSException e) {
            throw new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Method to update profile
     *
     * @param personalDetailsDto
     * @param id
     * @return
     */
    @Override
    public boolean getUpdateDetails(PersonalDetailsDto personalDetailsDto, long id) {
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
        return false;
    }

    /**
     * Method to update bank details
     *
     * @param bankDetailsDto
     * @return
     */
    @Override
    public boolean saveBankDetails(BankDetailsDto bankDetailsDto) {
        fellowshipCandidateRepository.findById(bankDetailsDto.getCandidateId())
                .orElseThrow(() -> new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, "Data not found"));
        BankDetailsDao bankDetailsDao = modelMapper
                .map(bankDetailsDto, BankDetailsDao.class);
        bankDetailsRepository.save(bankDetailsDao);
        return true;
    }

    /**
     * Method to update education details
     *
     * @param candidateQualificationDto
     * @return
     */
    @Override
    public boolean saveEducationDetails(CandidateQualificationDto candidateQualificationDto) {
        fellowshipCandidateRepository.findById(candidateQualificationDto.getCandidateId())
                .orElseThrow(() -> new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, "Data not found"));
        CandidateQualificationDao candidateQualificationDao = modelMapper
                .map(candidateQualificationDto, CandidateQualificationDao.class);
        candidateQualificationRepository.save(candidateQualificationDao);
        return true;
    }

    /**
     * Method to upload documents in system
     *
     * @param file
     * @param id
     * @return
     * @throws LMSException
     * @throws IOException
     */
    @Override
    public boolean upload(MultipartFile file, long id) throws LMSException, IOException {
        fellowshipCandidateRepository.findById(id)
                .orElseThrow(() -> new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, "Data not found"));
        if (file.isEmpty())
            throw new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, "Failed to store empty file");
        File convertFile = new File(path + id + "-" + file.getOriginalFilename());
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());

        fout.close();
        return false;
    }

    /**
     * Method to upload documents
     *
     * @param file
     * @param updateDocumentDto
     * @return
     */
    @Override
    public String uploadFile(MultipartFile file, String updateDocumentDto) {
        try {
            UploadDocumentsDto uploadDocumentsDto = new ObjectMapper().readValue(updateDocumentDto, UploadDocumentsDto.class);
            fellowshipCandidateRepository.findById(uploadDocumentsDto.getId())
                    .orElseThrow(() -> new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, "Data not found"));
            if (file.isEmpty())
                throw new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, "Failed to store empty file");
            Map<Object, Object> parameters = new HashMap<>();
            parameters.put("public_id", "CandidateDocuments/" + uploadDocumentsDto.getId() + "/" + file.getOriginalFilename());
            File uploadedFile = convertMultiPartToFile(file);
            Map uploadResult = cloudinaryConfig.uploader().upload(uploadedFile, parameters);
            String url = uploadResult.get("url").toString();
            uploadDocumentsDto.setDocumentPath(url);
            UploadDocumentsDao uploadDocumentsDao = modelMapper
                    .map(uploadDocumentsDto, UploadDocumentsDao.class);
            uploadDocumentsRepository.save(uploadDocumentsDao);
            return url;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to convert multipart to file
     *
     * @param file
     * @return
     * @throws IOException
     */
    @Override
    public File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertFile);
        fos.write(file.getBytes());
        fos.close();
        return convertFile;
    }

}
