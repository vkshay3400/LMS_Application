package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.dto.HiredCandidate;
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

    HiredCandidate hiredCandidate = new HiredCandidate();

    @Autowired
    private HiredCandidateRepository hiredCandidateRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List getHiredCandidate(String filePath) throws IOException {
        List candidateList = new ArrayList();
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {

            //Create Workbook instance holding reference to .xlsx fileInputStream
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
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

    public void saveCandidateDetails(List candidateList) {
        XSSFCell cell;
        for (int i = 1; i < candidateList.size(); i++) {
            int j = 0;
            List list = (List) candidateList.get(i);
            cell = (XSSFCell) list.get(j++);
            hiredCandidate.setId((long) cell.getNumericCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidate.setFirst_name(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidate.setMiddle_name(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidate.setLast_name(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidate.setEmail(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidate.setHired_city(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidate.setDegree(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidate.setHired_date(cell.getDateCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidate.setMobile_number((long) cell.getNumericCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidate.setPermanent_pincode((long) cell.getNumericCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidate.setHired_lab(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidate.setAttitude(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidate.setCommunication_remark(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidate.setKnowledge_remark(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidate.setAggregate_remark(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidate.setStatus(cell.getStringCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidate.setCreator_stamp(cell.getDateCellValue());
            cell = (XSSFCell) list.get(j++);
            hiredCandidate.setCreator_user(cell.getStringCellValue());

            DAOCandidate daoCandidate = modelMapper.map(hiredCandidate, DAOCandidate.class);
            hiredCandidateRepository.save(daoCandidate);
        }
    }

    public List getList() {
        return hiredCandidateRepository.findAll();
    }

    public DAOCandidate findByFirst_Name(String first_name) {
        DAOCandidate daoCandidate = hiredCandidateRepository.findByFirst_name(first_name);
        return daoCandidate;
    }

}
