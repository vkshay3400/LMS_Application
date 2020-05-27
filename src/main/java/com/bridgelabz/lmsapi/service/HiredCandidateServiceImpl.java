package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.dto.HiredCandidateDto;
import com.bridgelabz.lmsapi.exception.LMSException;
import com.bridgelabz.lmsapi.model.HiredCandidateDao;
import com.bridgelabz.lmsapi.model.FellowshipDao;
import com.bridgelabz.lmsapi.repository.FellowshipCandidateRepository;
import com.bridgelabz.lmsapi.repository.HiredCandidateRepository;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class HiredCandidateServiceImpl implements HiredCandidateService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private HiredCandidateRepository hiredCandidateRepository;

    @Autowired
    private FellowshipCandidateRepository fellowshipCandidateRepository;

    private String Accept, Reject, Pending;
    HiredCandidateDto hiredCandidateDTO = new HiredCandidateDto();

    // Method to get details from excel sheet
    @Override
    public void getHiredCandidate(MultipartFile filePath) {
        List candidateList = new ArrayList();
        boolean flag = true;
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
                if (!flag) {
                    while (cellIterators.hasNext()) {
                        XSSFCell cell = (XSSFCell) cellIterators.next();
                        data.add(cell);
                        candidateList.add(data);
                    }
                }
                flag = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveCandidateDetails(candidateList);
    }

    // Method to save details in database
    @Override
    public void saveCandidateDetails(List<List<XSSFCell>> candidateList) {
        XSSFCell cell;

        for (List<XSSFCell> list : candidateList) {
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

            HiredCandidateDao hiredCandidateDao = modelMapper.map(hiredCandidateDTO, HiredCandidateDao.class);
            if (hiredCandidateDao.equals(null))
                throw new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, "Data not found");
            hiredCandidateRepository.save(hiredCandidateDao);
        }
    }

    // Method to get list of hired candidates
    @Override
    public List getList() {
        List<HiredCandidateDao> list = hiredCandidateRepository.findAll();
        if (list.equals(null))
            throw new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, "Data not found");
        return list;
    }

    // Method to get profile of hired candidate
    @Override
    public HiredCandidateDao findById(long id) {
        return hiredCandidateRepository.findById(id)
                .orElseThrow(() -> new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, "Data not found"));
    }

    // Method to send mail on the user's mail
    @Override
    public String sendMail(HiredCandidateDto hiredCandidateDto) {
        hiredCandidateDto.setEmail(hiredCandidateDto.getEmail());
        hiredCandidateDto.setFirstName(hiredCandidateDto.getFirstName());
        hiredCandidateDto.setLastName(hiredCandidateDto.getLastName());

        try {
            HiredCandidateDao hiredCandidateDao = hiredCandidateRepository.findByEmail(hiredCandidateDto.getEmail())
                    .orElseThrow(() -> new LMSException(LMSException.exceptionType.USER_NOT_FOUND, "User not found"));
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(hiredCandidateDto.getEmail());
            mail.setFrom("${gmail.username}");
            mail.setSubject("Regarding your choice for joining ");
            mail.setText("Hello " + hiredCandidateDto.getFirstName() + " please select your choice: Accept or Reject to " +
                    "join the fellowship program and click on the link and put your choice " +
                    "Link: http://localhost:8080/hired/onboardstatus/choice?= {Your choice} ");

            javaMailSender.send(mail);
            return new String("Mail sent successfully");
        } catch (Exception e) {
            return new String("Mail Exception");
        }
    }

    // Method to change onboard status
    @Override
    public HiredCandidateDao getOnboardStatus(HiredCandidateDto hiredCandidateDto, String choice) {
        HiredCandidateDao hiredCandidateDao = hiredCandidateRepository.findById(hiredCandidateDto.getId())
                .orElseThrow(() -> new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, "Data not found"));
        switch (choice) {
            case "Accept":
                hiredCandidateDao.setStatus("Accept");
                hiredCandidateRepository.save(hiredCandidateDao);
                break;
            case "Reject":
                hiredCandidateDao.setStatus("Reject");
                break;
            default:
                hiredCandidateDao.setStatus("Pending");
                break;
        }
        return hiredCandidateRepository.save(hiredCandidateDao);
    }

    // Method to send job offer
    @Override
    public String sendJobOffer(HiredCandidateDto hiredCandidateDto) {
        HiredCandidateDao hiredCandidateDao = hiredCandidateRepository.findById(hiredCandidateDto.getId())
                .orElseThrow(() -> new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, "Data not found"));
        if (hiredCandidateDao.getStatus().matches("Accept")) {
            hiredCandidateDao = hiredCandidateRepository.findByEmail(hiredCandidateDto.getEmail())
                    .orElseThrow(() -> new LMSException(LMSException.exceptionType.USER_NOT_FOUND, "User not found"));
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(hiredCandidateDto.getEmail());
            mail.setFrom("${gmail.username}");
            mail.setSubject("Regarding your job offer");
            mail.setText("Hello " + hiredCandidateDto.getFirstName() + " Congratulations...!! " +
                    "You are selected for the fellowship program... ");
            javaMailSender.send(mail);

            // To save data in fellowship database table
            FellowshipDao fellowshipDao = new FellowshipDao();
            modelMapper.map(hiredCandidateDao, fellowshipDao);
            if (fellowshipDao.equals(null))
                throw new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, "Data not found");
            fellowshipCandidateRepository.save(fellowshipDao);
            return new String("Mail sent successfully");
        }
        return new String("Candidate Status is not Accept");
    }
}
