package com.bridgelabz.lmsapi.repository;

import com.bridgelabz.lmsapi.model.UploadDocumentsDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadDocumentsRepository extends JpaRepository<UploadDocumentsDao, Long> {


}
