package collegedata.demo.service;

import collegedata.demo.repository.CollegeRepo;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * author:panle
 * Date:2018/12/28
 * Time:17:07
 */
@Service
@Slf4j
public class EnrollCollegeService {

    static final String enrollCollgePath = "A:\\2018年招生大学.csv";
    static final String new_enrollCollegePath = "A:\\new_2018年招生大学.csv";
    static final String[] head = {"collegeId","enrollCollegeId","enrollCollegeName"};
    static final String enrollCollegeProvincePath = "A:\\2018招生省份对应表.csv";
    static final String enrollCollegeMajor = "A:\\2018年招生专业.csv";

    @Autowired
    private CollegeRepo collegeRepo;

    public void addCollegeId() throws Exception{

        CsvReader csvReader = new CsvReader(enrollCollgePath,',', Charset.forName("GBK"));
        csvReader.readHeaders();
        CsvWriter csvWriter = new CsvWriter(new_enrollCollegePath,',',Charset.forName("GBK"));
        csvWriter.writeRecord(head);
        Map<String,String> map = new HashMap<>();
        collegeRepo.findAll().forEach(college -> {
            map.put(college.getName(),college.getId().toString());
        });

        while (csvReader.readRecord()) {
            String enrollCollegeId = csvReader.get("EnrollCollegeId");
            String enrollCollegeName = csvReader.get("EnrollCollegeName");
            String[] strings;
            String collegeId = map.get(enrollCollegeName);
            if (collegeId != null) {
                //have right collegeName , write in table
                strings = new String[]{collegeId, enrollCollegeId, enrollCollegeName};
                try {
                    csvWriter.writeRecord(strings);
                    csvWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // have no collegeName , find something like
                Iterator<String> iterator = map.keySet().iterator();
                int i = 0 ;
                while (iterator.hasNext()) {
                    String collegeName = iterator.next();
                    if (enrollCollegeName.startsWith(collegeName) || collegeName.startsWith(enrollCollegeName) || collegeName.endsWith(enrollCollegeName) || enrollCollegeName.endsWith(collegeName)) {
                        strings = new String[]{map.get(collegeName), enrollCollegeId, enrollCollegeName};
                        try {
                            csvWriter.writeRecord(strings);
                            csvWriter.flush();
                            i = 1;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (i == 0){
                    strings = new String[]{"-10000", enrollCollegeId, enrollCollegeName};
                    try {
                        csvWriter.writeRecord(strings);
                        csvWriter.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public void getProvinceName() throws Exception{
        CsvReader csvReader= new CsvReader(enrollCollegeProvincePath,',',Charset.forName("UTF-8"));
        CsvReader csvReader_major = new CsvReader(enrollCollegeMajor,',',Charset.forName("GBK"));
        CsvReader csvReader_college = new CsvReader(new_enrollCollegePath,',',Charset.forName("GBK"));
        csvReader_major.readHeaders();
        csvReader.readHeaders();
        Map<String,String> provinceNames = new HashMap<>();
        Map<String,String> college_province = new HashMap<>();
        Map<String,String> map = new HashMap<>();
        while (csvReader.readRecord()){
            String provinceId = csvReader.get("ProvinceId");
            String provinceName = csvReader.get("ProvinceName");
            provinceNames.put(provinceId,provinceName);
        }
        log.info(provinceNames.size()+"招生省份对应表");

        while (csvReader_major.readRecord()){
            String provinceId = csvReader_major.get("ProvinceId");
            String collegeCode = csvReader_major.get("CollegeCode");
            map.put(collegeCode,provinceId);
        }
        log.info(map.size()+"大学数量");
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()){
            String collegeCode = iterator.next();
            String pId = map.get(collegeCode);
            if (provinceNames.get(pId) != null){
                college_province.put(collegeCode,provinceNames.get(pId));
            }

        }


        log.info(college_province.size()+"省份大学对应表");
        //get all collegeId and provinceId

        CsvWriter csvWriter = new CsvWriter("A:\\2018带省份招生大学表.csv",',',Charset.forName("GBK"));
        csvWriter.writeRecord(new String[]{"collegeId", "enrollCollegeId", "enrollCollegeName", "provinceName"});
        csvReader_college.readHeaders();
        while (csvReader_college.readRecord()){
            String enrollCollegeId = csvReader_college.get("enrollCollegeId");
            String collegeId = csvReader_college.get("collegeId");
            String enrollCollegeName = csvReader_college.get("enrollCollegeName");
            if (college_province.get(enrollCollegeId) != null){
                csvWriter.writeRecord(new String[]{collegeId,enrollCollegeId,enrollCollegeName,college_province.get(enrollCollegeId)});
                csvWriter.flush();
            }
        }










    }











}
@Data
@Builder
class EnrollCollege{
    private String collegeId;
    private String enrollCollegeId;
    private String enrollCollegeName;
}
@Data
@Builder
class CP{
    private String collegeId;
    private String collegeName;
    private String provinceId;
    private String provinceName;
}
