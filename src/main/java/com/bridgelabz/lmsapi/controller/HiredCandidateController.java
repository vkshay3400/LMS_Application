package com.bridgelabz.lmsapi.controller;

import com.bridgelabz.lmsapi.model.DAOCandidate;
import com.bridgelabz.lmsapi.service.HiredCandidateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/hired")
public class HiredCandidateController {

    @Autowired
    private HiredCandidateServiceImpl service;

    @PostMapping("/import-hired-list")
    public String importList() throws IOException {
        String filePath = "./src/main/resources/HiredListOfCandidate.xlsx";
        List getList = service.getHiredCandidate(filePath);
        service.saveCandidateDetails(getList);
        return "Imported Successfully";
    }

    @GetMapping("/all-hired-candidates")
    public List getAllCandidatesDetails() {
        return service.getList();
    }

    @GetMapping(value="/{name}")
    public DAOCandidate getCandidateDetails(@PathVariable String name){
        return service.findByFirst_Name(name);
    }
}
