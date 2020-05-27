package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.dto.FellowshipDto;
import com.bridgelabz.lmsapi.dto.PersonalDetailsDto;
import com.bridgelabz.lmsapi.model.FellowshipDao;

import java.util.Optional;

public interface FellowshipCandidateService {
    void getDetails();

    int getFellowshipCount();

    void getUpdateDetails(PersonalDetailsDto personalDetailsDto, long id);
//    void getUpdateDetails(FellowshipDto fellowshipDto, Optional<FellowshipDao> id);
//    void getUpdateDetails(FellowshipDto fellowshipDto, long id);
}

