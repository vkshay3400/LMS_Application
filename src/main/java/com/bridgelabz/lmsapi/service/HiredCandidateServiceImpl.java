package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.dto.HiredCandidateDto;
import com.bridgelabz.lmsapi.model.CandidateDao;
import com.bridgelabz.lmsapi.repository.HiredCandidateRepository;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class HiredCandidateServiceImpl implements HiredCandidateService {

    HiredCandidateDto hiredCandidateDTO = new HiredCandidateDto();

    @Autowired
    private HiredCandidateRepository hiredCandidateRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Method to get details from excel sheet
    @Override
    public List getHiredCandidate(MultipartFile filePath) {
        List candidateList = new ArrayList();
        try (InputStream fileInputStream = filePath.getInputStream()) {
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
                    candidateList.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return candidateList;
    }

    // Method to save details in database
    @Override
    public void saveCandidateDetails(List<List<XSSFCell>> candidateList) throws IOException {
        XSSFCell cell;
        boolean flag = true;
        for (List<XSSFCell> list : candidateList ){
            if (!flag) {
                int index = 0;
                cell = (XSSFCell) list.get(index++);
                hiredCandidateDTO.setId((long) cell.getNumericCellValue());
                cell = (XSSFCell) list.get(index++);
                hiredCandidateDTO.setFirstName(cell.getStringCellValue());
                cell = (XSSFCell) list.get(index++);
                hiredCandidateDTO.setMiddleName(cell.getStringCellValue());
                cell = (XSSFCell) list.get(index++);
                hiredCandidateDTO.setLastName(cell.getStringCellValue());
                cell = (XSSFCell) list.get(index++);
                hiredCandidateDTO.setEmail(cell.getStringCellValue());
                cell = (XSSFCell) list.get(index++);
                hiredCandidateDTO.setHiredCity(cell.getStringCellValue());
                cell = (XSSFCell) list.get(index++);
                hiredCandidateDTO.setDegree(cell.getStringCellValue());
                cell = (XSSFCell) list.get(index++);
                hiredCandidateDTO.setHiredDate(cell.getDateCellValue());
                cell = (XSSFCell) list.get(index++);
                hiredCandidateDTO.setMobileNumber((long) cell.getNumericCellValue());
                cell = (XSSFCell) list.get(index++);
                hiredCandidateDTO.setPermanentPincode((long) cell.getNumericCellValue());
                cell = (XSSFCell) list.get(index++);
                hiredCandidateDTO.setHiredLab(cell.getStringCellValue());
                cell = (XSSFCell) list.get(index++);
                hiredCandidateDTO.setAttitude(cell.getStringCellValue());
                cell = (XSSFCell) list.get(index++);
                hiredCandidateDTO.setCommunicationRemark(cell.getStringCellValue());
                cell = (XSSFCell) list.get(index++);
                hiredCandidateDTO.setKnowledgeRemark(cell.getStringCellValue());
                cell = (XSSFCell) list.get(index++);
                hiredCandidateDTO.setAggregateRemark(cell.getStringCellValue());
                cell = (XSSFCell) list.get(index++);
                hiredCandidateDTO.setStatus(cell.getStringCellValue());
                cell = (XSSFCell) list.get(index++);
                hiredCandidateDTO.setCreatorStamp(cell.getDateCellValue());
                cell = (XSSFCell) list.get(index++);
                hiredCandidateDTO.setCreatorUser(cell.getStringCellValue());

                CandidateDao candidateDao = modelMapper.map(hiredCandidateDTO, CandidateDao.class);
                hiredCandidateRepository.save(candidateDao);
            }
            flag = false;
        }
    }

    // Method to get list of hired candidates
    @Override
    public List getList() {
        return hiredCandidateRepository.findAll();
    }

    // Method to get profile of hired candidate
    @Override
    public Optional<CandidateDao> findById(Long id) {
        return hiredCandidateRepository.findById(Math.toIntExact(id));
    }

}
