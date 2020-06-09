package com.bridgelabz.lmsapi.service;

import com.bridgelabz.lmsapi.configuration.ApplicationConfig;
import com.bridgelabz.lmsapi.dto.HiredCandidateDto;
import com.bridgelabz.lmsapi.dto.MailDto;
import com.bridgelabz.lmsapi.exception.LMSException;
import com.bridgelabz.lmsapi.model.FellowshipDao;
import com.bridgelabz.lmsapi.model.HiredCandidateDao;
import com.bridgelabz.lmsapi.repository.FellowshipCandidateRepository;
import com.bridgelabz.lmsapi.repository.HiredCandidateRepository;
import com.bridgelabz.lmsapi.util.RabbitMq;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class HiredCandidateServiceImpl implements HiredCandidateService {

    @Autowired
    private RabbitMq rabbitMq;

    @Autowired
    private MailDto mailDto;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    HiredCandidateDto hiredCandidateDTO;

    @Autowired
    private HiredCandidateRepository hiredCandidateRepository;

    @Autowired
    private FellowshipCandidateRepository fellowshipCandidateRepository;

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
     * Method to send mail on the user's mail for choice
     *
     * @param hiredCandidateDto
     * @return
     */
    @Override
    public String sendMail(HiredCandidateDto hiredCandidateDto) throws MessagingException {
        mailDto.setTo(hiredCandidateDto.getEmail());
        mailDto.setSubject("Regarding your choice for joining");
        mailDto.setFrom("${gmail.username}");
        final Context ctx = new Context();
        ctx.setVariable("name", hiredCandidateDto.getFirstName());
        ctx.setVariable("acceptLink", "http://localhost:8080/hired/onboardstatus?email="
                + hiredCandidateDto.getEmail() + "" + "&choice=Accept");
        ctx.setVariable("rejectLink", "http://localhost:8080/hired/onboardstatus?email="
                + hiredCandidateDto.getEmail() + "" + "&choice=Reject");
        String html = templateEngine.process("Button", ctx);
        rabbitMq.sendMail(html,mailDto);
        return new String(ApplicationConfig.getMessageAccessor().getMessage("104"));
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
     * Method to change onboard status
     *
     * @param email
     * @param choice
     * @return
     */
    @Override
    public HiredCandidateDao getOnboardStatus(String email, String choice) {
        HiredCandidateDao hiredCandidateDao = hiredCandidateRepository.findByEmail(email)
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
            mailDto.setTo(hiredCandidateDto.getEmail());
            mailDto.setBody("Hello " + hiredCandidateDto.getFirstName() + " Congratulations...!! " +
                    "You are selected for the fellowship program... ");
            mailDto.setSubject("Regarding your job offer");
            mailDto.setFrom("${gmail.username}");
            rabbitMq.sendMail(mailDto);

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
