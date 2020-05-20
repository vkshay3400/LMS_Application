package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.dto.HiredCandidateDTO;
import com.bridgelabz.lmsapi.model.DAOCandidate;
import com.bridgelabz.lmsapi.repository.HiredCandidateRepository;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class HiredCandidateServiceImpl implements HiredCandidateService {

    HiredCandidateDTO hiredCandidateDTO = new HiredCandidateDTO();

    @Autowired
    private HiredCandidateRepository hiredCandidateRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Method to get hired candidate list from excel sheet
    @Override
    public List getHiredCandidate(String filePath) {
        List candidateList = new ArrayList();
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {

            // Create Workbook instance holding reference to .xlsx fileInputStream
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            // Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            // Iterate through each rows one by one
            Iterator rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                XSSFRow row = (XSSFRow) rowIterator.next();
                // For each row, iterate through all the columns
                Iterator cellIterators = row.cellIterator();
                List data = new ArrayList();
                while (cellIterators.hasNext()) {
                    XSSFCell cell = (XSSFCell) cellIterators.next();
                    data.add(cell);
                }
                candidateList.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidateList;
    }

    // Method to save details in database
    @Override
    public void saveCandidateDetails(List candidateList) throws IOException{
        XSSFCell cell;
        for (int index1 = 1; index1 < candidateList.size(); index1++) {
            int index2 = 0;
            List list = (List) candidateList.get(index1);
            cell = (XSSFCell) list.get(index2++);
            hiredCandidateDTO.setId((long) cell.getNumericCellValue());
            cell = (XSSFCell) list.get(index2++);
            hiredCandidateDTO.setFirstName(cell.getStringCellValue());
            cell = (XSSFCell) list.get(index2++);
            hiredCandidateDTO.setMiddleName(cell.getStringCellValue());
            cell = (XSSFCell) list.get(index2++);
            hiredCandidateDTO.setLastName(cell.getStringCellValue());
            cell = (XSSFCell) list.get(index2++);
            hiredCandidateDTO.setEmail(cell.getStringCellValue());
            cell = (XSSFCell) list.get(index2++);
            hiredCandidateDTO.setHiredCity(cell.getStringCellValue());
            cell = (XSSFCell) list.get(index2++);
            hiredCandidateDTO.setDegree(cell.getStringCellValue());
            cell = (XSSFCell) list.get(index2++);
            hiredCandidateDTO.setHiredDate(cell.getDateCellValue());
            cell = (XSSFCell) list.get(index2++);
            hiredCandidateDTO.setMobileNumber((long) cell.getNumericCellValue());
            cell = (XSSFCell) list.get(index2++);
            hiredCandidateDTO.setPermanentPincode((long) cell.getNumericCellValue());
            cell = (XSSFCell) list.get(index2++);
            hiredCandidateDTO.setHiredLab(cell.getStringCellValue());
            cell = (XSSFCell) list.get(index2++);
            hiredCandidateDTO.setAttitude(cell.getStringCellValue());
            cell = (XSSFCell) list.get(index2++);
            hiredCandidateDTO.setCommunicationRemark(cell.getStringCellValue());
            cell = (XSSFCell) list.get(index2++);
            hiredCandidateDTO.setKnowledgeRemark(cell.getStringCellValue());
            cell = (XSSFCell) list.get(index2++);
            hiredCandidateDTO.setAggregateRemark(cell.getStringCellValue());
            cell = (XSSFCell) list.get(index2++);
            hiredCandidateDTO.setStatus(cell.getStringCellValue());
            cell = (XSSFCell) list.get(index2++);
            hiredCandidateDTO.setCreatorStamp(cell.getDateCellValue());
            cell = (XSSFCell) list.get(index2++);
            hiredCandidateDTO.setCreatorUser(cell.getStringCellValue());

            DAOCandidate daoCandidate = modelMapper.map(hiredCandidateDTO, DAOCandidate.class);
            hiredCandidateRepository.save(daoCandidate);
        }
    }

    // Method to get list of hired candidates
    @Override
    public List getList() {
        return hiredCandidateRepository.findAll();
    }

    // Method to get profile of hired candidate
//    @Override
//    public Optional<DAOCandidate> findByFirstName(String name) {
////        return hiredCandidateRepository.findByFirstName(name);
//    }
}
