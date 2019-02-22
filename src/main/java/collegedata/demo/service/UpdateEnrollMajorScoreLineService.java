package collegedata.demo.service;

import collegedata.demo.bean.MajorScoreLine;
import collegedata.demo.entity.EnrollMajorScoreLine;
import collegedata.demo.entity.ScienceAndArt;
import collegedata.demo.repository.EnrollMajorScoreLineRepo;
import collegedata.demo.repository.EnrollStudentPlanRepo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:panLe
 * Date:2018/12/21
 * Time:10:30
 */
@Service
@Transactional
public class UpdateEnrollMajorScoreLineService {

    private static final String path = "A:\\provinceControllerLine.csv";
    private static final String headers[] = {"id", "batch_name", "science_art", "score_line", "province_id", "enroll_batch_id"};

    @Autowired
    private EnrollStudentPlanRepo enrollStudentPlanRepo;

    @Autowired
    private EnrollMajorScoreLineRepo enrollMajorScoreLineRepo;

    /**
     * create condition bean with enrollMajorScoreLine
     */
    public List<EnrollMajorScoreLine> getEMSLine() {
        List<MajorScoreLine> majorScoreLines = new ArrayList<>();
        List<EnrollMajorScoreLine> all = enrollMajorScoreLineRepo.findAll();
        all.forEach(enrollMajorScoreLine -> {
            String course = "0";
            if (enrollMajorScoreLine.getEnrollStudentPlan().getScienceArt().equals(ScienceAndArt.ART)) {
                course = "1";
            }
            MajorScoreLine majorScoreLine =
                    MajorScoreLine.builder()
                            .majorName(enrollMajorScoreLine.getName())
                            .collegeId(enrollMajorScoreLine.getEnrollStudentPlan().getCollege().getId())
                            .year(enrollMajorScoreLine.getYear())
                            .course(course)
                            .provinceId(enrollMajorScoreLine.getEnrollStudentPlan().getProvince().getId())
                            .enrollBatchId(enrollMajorScoreLine.getEnrollStudentPlan().getEnrollBatch().getId())
                            .scoreDiff(null)
                            .build();
            majorScoreLines.add(majorScoreLine);
        });
        return all;
    }

    /**
     * compare with provinceControllerScoreLine calculate scoreLineDiff
     * ‪A:\provinceControllerLine.csv
     */
    public void calculateDiff(List<EnrollMajorScoreLine> all) throws IOException {
        CSVFormat formator = CSVFormat.DEFAULT.withHeader(headers).withSkipHeaderRecord();
        FileReader fileReader = new FileReader(path);
        CSVParser csvParser = new CSVParser(fileReader, formator);
        // build new hashMap<String,integer>
        Map<String, Integer> provinceControllerLine = new HashMap<>();
        csvParser.getRecords().forEach(records -> {
            String score_line = records.get("score_line");
            String province_id = records.get("province_id");
            String enroll_batch_id = records.get("enroll_batch_id");
            String science_art = records.get("science_art");
            String key = String.join(" ", science_art, province_id, enroll_batch_id);
            Integer value = Integer.valueOf(score_line);
            provinceControllerLine.put(key, value);
        });

        // according to science_art , province_id , enroll_batch_id
        all.forEach(enrollMajorScoreLine -> {
            String course = "0";
            if (enrollMajorScoreLine.getEnrollStudentPlan().getScienceArt().equals(ScienceAndArt.ART)) {
                course = "1";
            }
            String key = String.join(" ", course, enrollMajorScoreLine.getEnrollStudentPlan().getProvince().getId().toString(),
                    enrollMajorScoreLine.getEnrollStudentPlan().getEnrollBatch().getId().toString());
            Integer value = enrollMajorScoreLine.getMinScore();
            Integer score = provinceControllerLine.get(key);
            if (score != null) {
                enrollMajorScoreLine.setScoreLineDiff(value - score);
            }
            enrollMajorScoreLineRepo.save(enrollMajorScoreLine);
        });

    }


}
