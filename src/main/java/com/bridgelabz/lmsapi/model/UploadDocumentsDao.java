package com.bridgelabz.lmsapi.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Table(name = "upload_documents")
@Entity(name = "upload_documents")
public class UploadDocumentsDao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long candidateId;
    private String documentType;
    private String documentPath;
    private String status;
    private LocalDateTime creatorStamp;
    private long creatorUser;

    @ManyToOne(fetch = FetchType.LAZY,optional=false)
    @JoinColumn(name = "candidateId", referencedColumnName = "id", insertable=false, updatable=false)
    private FellowshipDao fellowshipDao;

    public LocalDateTime getCreatorStamp() {
        return creatorStamp;
    }

    public void setCreatorStamp(LocalDateTime creatorStamp) {
        this.creatorStamp = LocalDateTime.now();
    }

    public long getCreatorUser() {
        return creatorUser;
    }

    public void setCreatorUser(long creatorUser) {
        this.creatorUser = this.candidateId;
    }

}
