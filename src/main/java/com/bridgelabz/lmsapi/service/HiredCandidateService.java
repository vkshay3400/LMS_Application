package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.model.CandidateDao;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface HiredCandidateService {

    List getHiredCandidate(MultipartFile filePath);

    void saveCandidateDetails(List<List<XSSFCell>> candidateList) throws IOException;

    List getList();

    CandidateDao findById(long id);

}
