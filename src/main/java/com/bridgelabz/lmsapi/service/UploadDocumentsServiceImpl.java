package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.exception.LMSException;
import com.bridgelabz.lmsapi.repository.FellowshipCandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class UploadDocumentsServiceImpl implements UploadDocumentsService {

    @Autowired
    FellowshipCandidateRepository fellowshipCandidateRepository;

    @Value("${upload.path}")
    private String path;

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
