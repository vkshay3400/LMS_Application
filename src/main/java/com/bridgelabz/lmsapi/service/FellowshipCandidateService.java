package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.dto.PersonalDetailsDto;

public interface FellowshipCandidateService {
    void getDetails();

    int getFellowshipCount();

    void getUpdateDetails(PersonalDetailsDto personalDetailsDto, long id);

}

