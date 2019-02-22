package collegedata.demo.controller.dataCompare;

import collegedata.demo.bean.ProvinceAndBatchDir;
import collegedata.demo.entity.CollegeScoreLine;
import collegedata.demo.entity.ScienceAndArt;
import collegedata.demo.repository.CollegeScoreLineRepo;
import collegedata.demo.repository.ProvinceRepo;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * author:panle
 * Date:2018/12/20
 * Time:9:49
 * 填充大学分数线的线差
 */
@Component
@Transactional
public class collegeScoreDiff {

    final static String testCSVPath = "A:\\test.csv";
    final static String localDataPath = "A:\\localData";
    private final static String NEW_LINE_SEPARATOR = "\n";
    private static String[] testHeaders = {"province_id", "batch_id", "enrollbatch"};
    private static String[] localDataHeaders = {"ProvinceName", "CollegeName", "Year", "BatchName",
            "Batch", "Course", "MaxScore", "MinScore", "AvgScore", "EnterNum", "LowSort",
            "LineDiff", "MapBatch", "Count", "StanCount", "RelativeLowSort", "Prorata",
            "StanLowSort", "AbsLowSort"
    };

    @Autowired
    private ProvinceRepo provinceRepo;

    @Autowired
    private CollegeScoreLineRepo collegeScoreLineRepo;

    public void mapToUpdate() throws IOException {
        List<CollegeScoreLine> collegeScoreLines = collegeScoreLineRepo.findAll();
        HashMap<String, Integer> collegeScoreLineMap = new HashMap<>();
        List<ProvinceAndBatchDir> provinceAndBatchDirs = this.turnLocalData();
        provinceAndBatchDirs.forEach(provinceAndBatchDir -> {
            String collegeName = provinceAndBatchDir.getCollegeName();
            String year = provinceAndBatchDir.getYear();
            String enrollbatchId = provinceAndBatchDir.getEnrollBatchId();
            String provinceName = provinceAndBatchDir.getProvinceName();
            String course = provinceAndBatchDir.getCourse();
            String key = String.join(" ", collegeName, year, enrollbatchId, provinceName, course);
            String diff = provinceAndBatchDir.getScoreLineDiff();
            if (diff.equals("LineDiff")) {
                return;
            }
            Integer value = Integer.valueOf(diff);
            collegeScoreLineMap.put(key, value);
        });
        System.out.println(collegeScoreLineMap.size());
        collegeScoreLines.forEach(collegeScoreLine -> {
            String collegeName = collegeScoreLine.getCollege().getName();
            Integer year = collegeScoreLine.getYear();
            Long enrollbatchId = collegeScoreLine.getEnrollBatch().getId();
            String provinceName = collegeScoreLine.getProvince().getName();
            String course = "1";
            if (collegeScoreLine.getScienceArt().equals(ScienceAndArt.SCIENCE)) {
                course = "0";
            }
            Integer scoreDiff = collegeScoreLine.getScoreLineDiff();
            String key = String.join(" ", collegeName, year.toString(), enrollbatchId.toString(), provinceName, course);

            Integer diff = collegeScoreLineMap.get(key);
            collegeScoreLine.setScoreLineDiff(diff);
            collegeScoreLineRepo.save(collegeScoreLine);
        });
    }


    /**
     * hashMap update collegeScoreLine diff
     *
     * @throws IOException
     */
    public void updateCollegeScoreLineData() throws IOException {
        List<CollegeScoreLine> collegeScoreLines = collegeScoreLineRepo.findAll();
        HashMap<String, Integer> collegeScoreLineMap = new HashMap<>();
        trunToMap(collegeScoreLines, collegeScoreLineMap);
        System.out.println(collegeScoreLineMap.size());

        List<ProvinceAndBatchDir> provinceAndBatchDirs = this.turnLocalData();
//        HashMap<String, Integer> localDataMap = new HashMap<>();

        provinceAndBatchDirs.forEach(provinceAndBatchDir -> {
            String collegeName = provinceAndBatchDir.getCollegeName();
            String year = provinceAndBatchDir.getYear();
            String enrollbatchId = provinceAndBatchDir.getEnrollBatchId();
            String provinceName = provinceAndBatchDir.getProvinceName();
            String course = provinceAndBatchDir.getCourse();
            String key = String.join(" ", collegeName, year, enrollbatchId, provinceName, course);
            String diff = provinceAndBatchDir.getScoreLineDiff();
            if (diff.equals("LineDiff")) {
                return;
            }
            Integer value = Integer.valueOf(diff);
            //localDataMap.put(key,value);
            // 包含key的话,给key,添加value
            if (collegeScoreLineMap.containsKey(key)) {
                collegeScoreLineMap.put(key, value);
                System.out.println(collegeScoreLineMap.get(key) + "-------------------------------------------------");
                String[] elements = key.split(" ");

                collegeScoreLines.forEach(collegeScoreLine -> {
                    String collegeName_1 = collegeScoreLine.getCollege().getName();
                    Integer year_1 = collegeScoreLine.getYear();
                    Long enrollbatchId_1 = collegeScoreLine.getEnrollBatch().getId();
                    String provinceName_1 = collegeScoreLine.getProvince().getName();
                    String course_1 = "1";
                    if (collegeScoreLine.getScienceArt().equals(ScienceAndArt.SCIENCE)) {
                        course_1 = "0";
                    }
                    if (elements[0].equals(collegeName_1)
                            && elements[1].equals(year_1.toString())
                            && elements[2].equals(enrollbatchId_1)
                            && elements[3].equals(provinceName_1)
                            && elements[4].equals(course_1)) {
                        collegeScoreLine.setScoreLineDiff(value);
                    }
                    collegeScoreLineRepo.save(collegeScoreLine);
                });
            }
        });
    }

    public void trunToMap(List<CollegeScoreLine> collegeScoreLines, HashMap<String, Integer> collegeScoreLineMap) {
        collegeScoreLines.forEach(collegeScoreLine -> {
            String collegeName = collegeScoreLine.getCollege().getName();
            Integer year = collegeScoreLine.getYear();
            Long enrollbatchId = collegeScoreLine.getEnrollBatch().getId();
            String provinceName = collegeScoreLine.getProvince().getName();
            String course = "1";
            if (collegeScoreLine.getScienceArt().equals(ScienceAndArt.SCIENCE)) {
                course = "0";
            }
            Integer scoreDiff = collegeScoreLine.getScoreLineDiff();
            String key = String.join(" ", collegeName, year.toString(), enrollbatchId.toString(), provinceName, course);
            collegeScoreLineMap.put(key, scoreDiff);
        });
    }

    /**
     * 更新大学分数线中的线差
     */
    public void updateCollegeScoreLine() throws IOException {
        List<CollegeScoreLine> collegeScoreLines = collegeScoreLineRepo.findAll();
        List<ProvinceAndBatchDir> provinceAndBatchDirs = this.turnLocalData();
        System.out.println(collegeScoreLines.size());
        System.out.println(provinceAndBatchDirs.size());
        collegeScoreLines.forEach(collegeScoreLine -> {
            String collegeName = collegeScoreLine.getCollege().getName();
            Integer year = collegeScoreLine.getYear();
            Long enrollbatchId = collegeScoreLine.getEnrollBatch().getId();
            String provinceName = collegeScoreLine.getProvince().getName();
            String course = "1";
            if (collegeScoreLine.getScienceArt().equals(ScienceAndArt.SCIENCE)) {
                course = "0";
            }
            String finalCourse = course;
            provinceAndBatchDirs.forEach(provinceAndBatchDir -> {
                String diffe = provinceAndBatchDir.getScoreLineDiff();
                if (diffe.equals("LineDiff")) {
                    return;
                }
                Integer diff = Integer.valueOf(diffe);
                if (collegeName.equals(provinceAndBatchDir.getCollegeName())
                        && Integer.valueOf(provinceAndBatchDir.getYear()).equals(year)
                        && enrollbatchId.equals(Long.parseLong(provinceAndBatchDir.getBatchId()))
                        && provinceName.equals(provinceAndBatchDir.getProvinceName())
                        && finalCourse.equals(provinceAndBatchDir.getCourse())
                ) {
                    collegeScoreLine.setScoreLineDiff(diff);
                    collegeScoreLineRepo.save(collegeScoreLine);
                }
            });
        });
        //collegeScoreLineRepo.saveAll(collegeScoreLines);
    }


    /**
     * 转换本地字典表中的provinceId -> provinceName  add to list<provinceAndBatchDir></>
     */
    public List<ProvinceAndBatchDir> turnTestData() throws IOException {

        List<ProvinceAndBatchDir> provinceAndBatchDirs = new ArrayList<>();

        CSVFormat formator = CSVFormat.DEFAULT.withHeader(testHeaders);
        FileReader fileReader = new FileReader(testCSVPath);
        CSVParser csvParser = new CSVParser(fileReader, formator);
        csvParser.getRecords().forEach(Strings -> {
            String provinceId = Strings.get("province_id");
            if (provinceId.equals("province_id")) {
                return;
            }
            Long province_id = Long.parseLong(provinceId);
            String enrollBatchId = Strings.get("enrollbatch");
            String batch_id = Strings.get("batch_id");
            String provinceName = provinceRepo.findById(province_id).get().getName();
            ProvinceAndBatchDir provinceAndBatchDir = ProvinceAndBatchDir.builder()
                    .enrollBatchId(enrollBatchId)
                    .provinceId(provinceId)
                    .provinceName(provinceName)
                    .batchId(batch_id)
                    .build();
            provinceAndBatchDirs.add(provinceAndBatchDir);
        });
        //System.out.println(provinceAndBatchDirs.size());
        return provinceAndBatchDirs;
    }

    /**
     * 转换本地data数据的batch -> enrollBatchId to List<ProvinceAndBatchDir></>
     * TODO:这里因为collegeName不同有可能会损失一些资料
     */
    public List<ProvinceAndBatchDir> turnLocalData() throws IOException {

        List<ProvinceAndBatchDir> provinceAndBatchDirs_local = new ArrayList<>();

        File[] files = new File(localDataPath).listFiles();
        assert files != null;
        for (int i = 0; i < files.length; i++) {
            String path = Objects.requireNonNull(files[i].listFiles())[1].getPath();
            CSVFormat formator = CSVFormat.DEFAULT.withHeader(localDataHeaders);
            FileReader fileReader = new FileReader(path);
            CSVParser csvParser = new CSVParser(fileReader, formator);
            List<CSVRecord> lists = csvParser.getRecords();
            lists.forEach(Strings -> {
//                if (Strings.get("ProvinceName").equals("ProvinceName")) {
//                    return;
//                }
                // builder list
                ProvinceAndBatchDir provinceAndBatchDir = ProvinceAndBatchDir
                        .builder()
                        .provinceName(Strings.get("ProvinceName"))
                        .batchId(Strings.get("Batch"))
                        .collegeName(Strings.get("CollegeName"))
                        .year(Strings.get("Year"))
                        .course(Strings.get("Course"))
                        .scoreLineDiff(Strings.get("LineDiff"))
                        .build();
                provinceAndBatchDirs_local.add(provinceAndBatchDir);
            });
            // compare with list test data
            List<ProvinceAndBatchDir> provinceAndBatchDirs_testData = this.turnTestData();
            provinceAndBatchDirs_testData.forEach(provinceAndBatchDir_testData -> {
                provinceAndBatchDirs_local.forEach(provinceAndBatchDir_local -> {
                    if (provinceAndBatchDir_testData.getProvinceName().equals(provinceAndBatchDir_local.getProvinceName()) &&
                            provinceAndBatchDir_local.getBatchId().equals(provinceAndBatchDir_testData.getBatchId())
                    ) {
                        provinceAndBatchDir_local.setProvinceId(provinceAndBatchDir_testData.getProvinceId());
                        provinceAndBatchDir_local.setEnrollBatchId(provinceAndBatchDir_testData.getEnrollBatchId());
                    }
                });
            });
        }
        System.out.println(provinceAndBatchDirs_local.size());
        return provinceAndBatchDirs_local;
    }


    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException IOE) {
        return ResponseEntity.status(5000000).body("读文件出问题了");
    }
}

