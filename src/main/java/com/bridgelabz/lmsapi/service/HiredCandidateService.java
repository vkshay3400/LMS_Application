package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.dto.HiredCandidateDto;
import com.bridgelabz.lmsapi.model.HiredCandidateDao;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface HiredCandidateService {

    void getHiredCandidate(MultipartFile filePath);

    void saveCandidateDetails(List<List<XSSFCell>> candidateList) throws IOException;

    List getList();

    HiredCandidateDao findById(long id);

    String sendMail(HiredCandidateDto hiredCandidateDto);

    HiredCandidateDao getOnboardStatus(HiredCandidateDto hiredCandidateDto, String choice);

    String sendJobOffer(HiredCandidateDto hiredCandidateDto);

}
