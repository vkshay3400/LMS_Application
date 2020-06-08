package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.configuration.ApplicationConfig;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class HiredCandidateServiceImpl implements HiredCandidateService {

    @Autowired
    private ModelMapper modelMapper;

//    @Autowired
//    private SpringTemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private HiredCandidateRepository hiredCandidateRepository;

    @Autowired
    private FellowshipCandidateRepository fellowshipCandidateRepository;

    @Autowired
    HiredCandidateDto hiredCandidateDTO;

    @Value("${choice.accept}")
    private String accept;

    @Value("${choice.reject}")
    private String reject;

    @Value("${choice.pending}")
    private String pending;

    /**
     * Method to get details from excel sheet
     *
     * @param filePath
     * @return
     */
    @Override
    public boolean getHiredCandidate(MultipartFile filePath) {
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
        return flag;
    }

    /**
     * Method to save details in database
     *
     * @param candidateList
     */
    @Override
    public void saveCandidateDetails(List<List<XSSFCell>> candidateList) {
        XSSFCell cell;

        for (List<XSSFCell> list : candidateList) {
            int index = 0;
            cell = list.get(index++);
            hiredCandidateDTO.setId((long) cell.getNumericCellValue());
            cell = list.get(index++);
            hiredCandidateDTO.setFirstName(cell.getStringCellValue());
            cell = list.get(index++);
            hiredCandidateDTO.setMiddleName(cell.getStringCellValue());
            cell = list.get(index++);
            hiredCandidateDTO.setLastName(cell.getStringCellValue());
            cell = list.get(index++);
            hiredCandidateDTO.setEmail(cell.getStringCellValue());
            cell = list.get(index++);
            hiredCandidateDTO.setHiredCity(cell.getStringCellValue());
            cell = list.get(index++);
            hiredCandidateDTO.setDegree(cell.getStringCellValue());
            cell = list.get(index++);
            hiredCandidateDTO.setHiredDate(cell.getDateCellValue());
            cell = list.get(index++);
            hiredCandidateDTO.setMobileNumber((long) cell.getNumericCellValue());
            cell = list.get(index++);
            hiredCandidateDTO.setPermanentPincode((long) cell.getNumericCellValue());
            cell = list.get(index++);
            hiredCandidateDTO.setHiredLab(cell.getStringCellValue());
            cell = list.get(index++);
            hiredCandidateDTO.setAttitude(cell.getStringCellValue());
            cell = list.get(index++);
            hiredCandidateDTO.setCommunicationRemark(cell.getStringCellValue());
            cell = list.get(index++);
            hiredCandidateDTO.setKnowledgeRemark(cell.getStringCellValue());
            cell = list.get(index++);
            hiredCandidateDTO.setAggregateRemark(cell.getStringCellValue());
            cell = list.get(index++);
            hiredCandidateDTO.setStatus(cell.getStringCellValue());

            HiredCandidateDao hiredCandidateDao = modelMapper.map(hiredCandidateDTO, HiredCandidateDao.class);
            if (hiredCandidateDao.equals(null))
                throw new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, "Data not found");
            hiredCandidateRepository.save(hiredCandidateDao);
        }
    }

    /**
     * Method to get list of hired candidates
     *
     * @return
     */
    @Override
    public List getList() {
        List<HiredCandidateDao> list = hiredCandidateRepository.findAll();
        if (list.equals(null))
            throw new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, "Data not found");
        return list;
    }

    /**
     * Method to get profile of hired candidate
     *
     * @param id
     * @return
     */
    @Override
    public HiredCandidateDao findById(long id) {
        return hiredCandidateRepository.findById(id)
                .orElseThrow(() -> new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, "Data not found"));
    }

    /**
     * Method to send mail on the user's mail
     *
     * @param hiredCandidateDto
     * @return
     */
    @Override
    public String sendMail(HiredCandidateDto hiredCandidateDto) {
        hiredCandidateDto.setEmail(hiredCandidateDto.getEmail());
        hiredCandidateDto.setFirstName(hiredCandidateDto.getFirstName());
        hiredCandidateDto.setLastName(hiredCandidateDto.getLastName());

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
        return new String(ApplicationConfig.getMessageAccessor().getMessage("104"));
    }

    /**
     * Method to change onboard status
     *
     * @param hiredCandidateDto
     * @param choice
     * @return
     */
    @Override
    public HiredCandidateDao getOnboardStatus(HiredCandidateDto hiredCandidateDto, String choice) {
        HiredCandidateDao hiredCandidateDao = hiredCandidateRepository.findById(hiredCandidateDto.getId())
                .orElseThrow(() -> new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, "Data not found"));
        switch (choice) {
            case "accept":
                hiredCandidateDao.setStatus(accept);
                hiredCandidateRepository.save(hiredCandidateDao);
                break;
            case "reject":
                hiredCandidateDao.setStatus(reject);
                break;
            default:
                hiredCandidateDao.setStatus(pending);
                break;
        }
        return hiredCandidateRepository.save(hiredCandidateDao);
    }

    /**
     * Method to send job offer
     *
     * @param hiredCandidateDto
     * @return
     */
    @Override
    public String sendJobOffer(HiredCandidateDto hiredCandidateDto) {
        HiredCandidateDao hiredCandidateDao = hiredCandidateRepository.findById(hiredCandidateDto.getId())
                .orElseThrow(() -> new LMSException(LMSException.exceptionType.DATA_NOT_FOUND, "Data not found"));
        if (hiredCandidateDao.getStatus().matches(accept)) {
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
            return new String(ApplicationConfig.getMessageAccessor().getMessage("104"));
        }
        return new String(ApplicationConfig.getMessageAccessor().getMessage("114"));
    }

}
