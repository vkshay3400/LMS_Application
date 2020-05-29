package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.exception.LMSException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadDocumentsService {

    void upload(MultipartFile file, long id) throws LMSException, IOException;

}
