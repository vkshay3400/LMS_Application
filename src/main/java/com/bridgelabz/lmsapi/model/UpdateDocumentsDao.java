package com.bridgelabz.lmsapi.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "upload_documents")
@Entity(name = "upload_documents")
public class UpdateDocumentsDao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long candidateId;
    private String documentType;
    private String documentPath;
    private String status;
    private Date creatorStamp;
    private String creatorUser;

    @ManyToOne(fetch = FetchType.LAZY,optional=false)
    @JoinColumn(name = "candidateId", referencedColumnName = "id", insertable=false, updatable=false)
    private FellowshipDao fellowshipDao;

}
