package collegedata.demo.service;

import collegedata.demo.entity.College;
import collegedata.demo.repository.CollegeRepo;
import collegedata.demo.repository.ProvinceRepo;
import com.csvreader.CsvReader;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author:panle
 * Date:2018/12/25
 * Time:19:29
 * handler csv data and turn batch to enrollBatch
 * create bean with some
 */
@Service
public class csvService {
    static final String sourthPath = "A:\\generated";
    static final String comparePath = "A:\\test.csv";
    static final String[] sourthPathHead = {"CollegeName", "Batch", "Course", "Mean", "Stand"};
    static final String[] comparePathHead = {"province_id", "batch_id", "enrollbatch"};
    private static final String LINE_SEPARATOR = "\n";
    private static final String[] File_Head = {"id", "collegeId", "enrollCollegeName"};
    static final String writePath = "A:\\用中文也没问题.csv";

    private final ProvinceRepo provinceRepo;
    private final CollegeRepo collegeRepo;

    @Autowired
    public csvService(ProvinceRepo provinceRepo, CollegeRepo collegeRepo) {
        this.provinceRepo = provinceRepo;
        this.collegeRepo = collegeRepo;
    }

    public List<List<CSVData>> trunEnrollBatch() throws Exception {
        File southFile = new File(sourthPath);
        File[] files = southFile.listFiles();
        assert files != null;
        List<List<CSVData>> csvList = new ArrayList<>();
        List<CSVData> lists = new ArrayList<>();
        for (File file : files) {
            CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader(sourthPathHead).withSkipHeaderRecord();
            // turn lists.batch to enrollBatch
            Map<String, String> compareMap = new HashMap<>();
            CSVFormat csvFormat_c = CSVFormat.DEFAULT.withHeader(comparePathHead).withSkipHeaderRecord();
            FileReader southFileReader_c = new FileReader(comparePath);
            CSVParser csvParser_c = new CSVParser(southFileReader_c, csvFormat_c);
            csvParser_c.forEach(strings -> {
                String provinceId = strings.get("province_id");
                String batch_id = strings.get("batch_id");
                String enrollbatch = strings.get("enrollbatch");
                CSVData csvData = CSVData.builder().provinceId(provinceId).batch(batch_id).enrollBatch(enrollbatch).build();
                compareMap.put(provinceId + batch_id, enrollbatch);
            });

            //turn provinceName to provinceId
            String name = file.getName();// out-provinceName
            String provinceName = name.substring(4);
            Matcher matcher = Pattern.compile("^[\\u4e00-\\u9fa5]+").matcher(provinceName);
            if (matcher.find()) {
                provinceName = matcher.group();
            }
            String PId = provinceRepo.findByName(provinceName).getId().toString();

            FileReader southFileReader = new FileReader(file);
            CSVParser csvParser = new CSVParser(southFileReader, csvFormat);
            csvParser.getRecords().forEach(strings -> {
                String collegeName = strings.get("CollegeName");
                String batch = strings.get("Batch");
                String course = strings.get("Course");
                String enrollBatch = compareMap.get(PId + batch);
                if (enrollBatch != null) {
                    CSVData csvData = CSVData.builder().collegeName(collegeName).batch(enrollBatch).course(course).provinceId(PId).build();
                    lists.add(csvData);
                    //System.out.println(csvData.getCollegeName());
                }
            });
            List<CSVData> list_new = new ArrayList<>(lists);
            csvList.add(list_new);
//            lists.forEach(csvData -> {
//                System.out.println(csvData.getCollegeName()+"-------------------------------------------------------------");
//            });
            lists.clear();
        }
        System.out.println(csvList.size());
        return csvList;
    }

    /**
     * create enrollCollege bean
     * map<provinceId+collegeName,collegeId></>
     */
    @Transactional
    public void getEnrollCollege() throws Exception {
        List<List<CSVData>> lists = this.turnFinalData();
        lists.forEach(list -> {
            System.out.println(list.size());
            list.forEach(CSVData -> {
                //System.out.println(CSVData.getCollegeName()+"-------------------------------------------------------------------------------------------");
                System.out.println(list.size());
            });
        });
        System.out.println(lists.size() + "上个方法传递的结果集大小");
        List<College> all = collegeRepo.findAll();
        List<ResultData> resultDatas = new ArrayList<>();
        AtomicInteger i = new AtomicInteger();
        lists.forEach(lists_csv -> {
            lists_csv.forEach(csvData -> {
                String collegeName_csv = csvData.getCollegeName();
                String enrollBatch = csvData.getEnrollBatch();
                all.forEach(college -> {
                    String collegegName = college.getName();
                    //System.out.println(collegegName);
                    if (collegeName_csv.startsWith(collegegName)) {
                        resultDatas.add(ResultData.builder().name(collegeName_csv).collegeId(college.getId().toString()).build());
                    }
                });
            });
        });
        System.out.println(resultDatas.size());
        try {
            CSVFormat csvFormat = CSVFormat.DEFAULT.withRecordSeparator(LINE_SEPARATOR);
            FileWriter fileWriter = new FileWriter(writePath);
            CSVPrinter csvPrinter = new CSVPrinter(fileWriter, csvFormat);
            csvPrinter.printRecord(File_Head);
            resultDatas.forEach(resultData -> {
                try {
                    csvPrinter.printRecord(null, resultData.getCollegeId(), resultData.getName());
                    csvPrinter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 转化原始final数据
     * 在最原始的数据中转换所有大学provinceName,collegeName,batch,转换provinceId和enrollBatch
     */
    static final String finalPath = "A:\\final(3)";

    public List<List<CSVData>> turnFinalData() throws Exception {
        File[] files = new File(finalPath).listFiles();
        List<List<CSVData>> lists = new ArrayList<>();
        for (File file : files) {
            String name = file.getName();//hunan
            List<CSVData> finalList = this.fengzhuangMethod(name);
            lists.add(finalList);
            System.out.println("各省的list大小" + lists.size());
        }
        return lists;
    }


    /**
     * 封装方法,避免循环里面太恶心了
     * A:\final(3)\anhui\data.csv
     */
    public List<CSVData> fengzhuangMethod(String name) throws Exception {
        CsvReader csvReader = new CsvReader("A:\\final(3)\\" + name + "\\data.csv", ',', Charset.forName("UTF-8"));
        CsvReader csvReader_compare = new CsvReader(comparePath, ',', Charset.forName("UTF-8"));
        csvReader_compare.readHeaders();
        csvReader.readHeaders();
        List<CSVData> finalList = new ArrayList<>();
        Map<String, String> map_data = new HashMap<>();
        Map<String, String> map_csv = new HashMap<>();
//        System.out.println(csvReader.getColumnCount()+"data数据行数");
//        System.out.println(csvReader_compare.getColumnCount()+"compare数据行数");
        while (csvReader_compare.readRecord()) {
            String PId = csvReader_compare.get("province_id");
            String Batch = csvReader_compare.get("batch_id");
            String enrollbatch = csvReader_compare.get("enrollbatch");
            map_csv.put(PId + Batch, enrollbatch);
        }
        while (csvReader.readRecord()) {
            String provinceName = csvReader.get("ProvinceName");
            String collegeName = csvReader.get("CollegeName");
            //System.out.println(collegeName);
            String batch = csvReader.get("Batch");
            String course = csvReader.get("Course");
            //trun provinceId
            String provinceId = provinceRepo.findByName(provinceName).getId().toString();
            //String provinceId = "110000";
            if (map_csv.get(provinceId + batch) != null) {
                finalList.add(CSVData.builder().collegeName(collegeName).provinceId(provinceId).enrollBatch(map_csv.get(provinceId + batch)).build());
            }
            //System.out.println(map_data.size());
        }
        csvReader.close();
        //trun batch to enrollBatch
        csvReader_compare.close();
        // TODO:这里筛选的会少一点
        System.out.println(finalList.size());
        return finalList;

    }


}

@Data
@Builder
class CSVData {
    private String collegeName;
    private String batch;
    private String enrollBatch;
    private String course;
    private String provinceId;
}

@Data
@Builder
class ResultData {
    private String collegeId;
    private String name;

}
/**
 * //TODO: 看看为什么
 * i.getAndIncrement();
 * CSVData.forEach(csvData -> {
 * String collegeName = csvData.getCollegeName();
 * map.put(collegeName, "value");
 * });
 * all.forEach(college -> {
 * String collegeName = college.getName();
 * String collegeId = college.getId().toString();
 * //                if (map.get(collegeName) != null) {
 * //                    map_end.put(collegeName, collegeId);
 * //                } else {
 * //拿出所有的key做比较
 * Iterator<String> iterator = map.keySet().iterator();
 * while (iterator.hasNext()) {
 * String key = iterator.next();
 * if (key.startsWith(collegeName)) {
 * map_end.put(collegeName, collegeId);
 * }
 * }
 * });
 */
