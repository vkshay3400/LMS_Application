package com.bridgelabz.lmsapi.service;

import java.io.IOException;
import java.util.List;

public interface HiredCandidateService {
    List getHiredCandidate(String filepath) throws IOException;
}
