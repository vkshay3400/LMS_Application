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

@Service
public class HiredCandidateServiceImpl implements HiredCandidateService {

    HiredCandidateDTO hiredCandidateDTO = new HiredCandidateDTO();

    @Autowired
    private HiredCandidateRepository hiredCandidateRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List getHiredCandidate(String filePath) throws IOException {
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
                //For each row, iterate through all the columns
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

    // To save details in database
    public void saveCandidateDetails(List candidateList) {
        XSSFCell cell;
        for (int i = 1; i < candidateList.size(); i++) {
            int j = 0;
            List list = (List) candidateList.get(i);
            cell = (XSSFCell) list.get(j++);
            hiredCandidateDTO.setId((long) cell.getNumericCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidateDTO.setFirst_name(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidateDTO.setMiddle_name(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidateDTO.setLast_name(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidateDTO.setEmail(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidateDTO.setHired_city(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidateDTO.setDegree(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidateDTO.setHired_date(cell.getDateCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidateDTO.setMobile_number((long) cell.getNumericCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidateDTO.setPermanent_pincode((long) cell.getNumericCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidateDTO.setHired_lab(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidateDTO.setAttitude(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidateDTO.setCommunication_remark(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidateDTO.setKnowledge_remark(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidateDTO.setAggregate_remark(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidateDTO.setStatus(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidateDTO.setCreator_stamp(cell.getDateCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidateDTO.setCreator_user(cell.getStringCellValue());

            DAOCandidate daoCandidate = modelMapper.map(hiredCandidateDTO, DAOCandidate.class);
            hiredCandidateRepository.save(daoCandidate);
        }
    }

    // To get list of hired candidate
    public List getList() {
        return hiredCandidateRepository.findAll();
    }

    // To find hired candidate by name
    public DAOCandidate findByFirst_Name(String first_name) {
        DAOCandidate daoCandidate = hiredCandidateRepository.findByFirst_name(first_name);
        return daoCandidate;
    }
}
