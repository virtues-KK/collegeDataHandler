package collegedata.demo.controller.dataCompare;

import collegedata.demo.entity.College;
import collegedata.demo.repository.CollegeRepo;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * author:panle
 * Date:2018/12/5
 * Time:16:01
 */
@Service
public class demo5 {
    final static String sourceFilePath = "C:\\Users\\sunwukong\\Desktop\\比较数据 - 副本 - 副本 (2)";
    final static char separator = ',';

    @Autowired
    private CollegeRepo collegeRepo;

    void demo5() throws Exception {
        File[] sourceListFiles = new File(sourceFilePath).listFiles();
        assert sourceListFiles != null;
        for (int i = 0; i < sourceListFiles.length; i++) {
            CsvReader sourceReader = new CsvReader(Objects.requireNonNull(sourceListFiles[i].listFiles())[2].getPath(), separator, Charset.forName("UTF-8"));
            List<sourceData> sourceDataList = new ArrayList<>();
            sourceReader.readHeaders();
            while (sourceReader.readRecord()) {
                Long id = -1L;
                String collegeName =sourceReader.get("CollegeName");
                if (collegeName != null) {
                    Optional<College> optional = collegeRepo.findByName(collegeName);
                    if (optional.isPresent()) {
                        id = optional.get().getId();
                    }
                }
                sourceData Data = sourceData.builder().id(Long.toString(id)).ProvinceName(sourceReader.get("ProvinceName"))
                        .CollegeName(sourceReader.get("CollegeName"))
                        .Batch(sourceReader.get("Batch"))
                        .MajorName(sourceReader.get("MajorName"))
                        .Course(sourceReader.get("Course"))
                        .EnterNum_2014(sourceReader.get("EnterNum_2014"))
                        .EnterNum_2015(sourceReader.get("EnterNum_2015"))
                        .EnterNum_2016(sourceReader.get("EnterNum_2016"))
                        .EnterNum_2017(sourceReader.get("EnterNum_2017"))
                        .MinSort_2014(sourceReader.get("MinSort_2014"))
                        .MinSort_2015(sourceReader.get("MinSort_2015"))
                        .MinSort_2016(sourceReader.get("MinSort_2016"))
                        .MinSort_2017(sourceReader.get("MinSort_2017"))
                        .LowSort_2014(sourceReader.get("LowSort_2014"))
                        .LowSort_2015(sourceReader.get("LowSort_2015"))
                        .LowSort_2016(sourceReader.get("LowSort_2016"))
                        .LowSort_2017(sourceReader.get("LowSort_2017"))
                        .Count_2014(sourceReader.get("Count_2014"))
                        .Count_2015(sourceReader.get("Count_2015"))
                        .Count_2016(sourceReader.get("Count_2016"))
                        .Count_2017(sourceReader.get("Count_2017"))
                        .StanCount_2014(sourceReader.get("StanCount_2014"))
                        .StanCount_2015(sourceReader.get("StanCount_2015"))
                        .StanCount_2016(sourceReader.get("StanCount_2016"))
                        .StanCount_2017(sourceReader.get("StanCount_2017"))
                        .RelativeLowSort_2014(sourceReader.get("RelativeLowSort_2014"))
                        .RelativeLowSort_2015(sourceReader.get("RelativeLowSort_2015"))
                        .RelativeLowSort_2016(sourceReader.get("RelativeLowSort_2016"))
                        .RelativeLowSort_2017(sourceReader.get("RelativeLowSort_2017"))
                        .StanLowSort_2014(sourceReader.get("StanLowSort_2014"))
                        .StanLowSort_2015(sourceReader.get("StanLowSort_2015"))
                        .StanLowSort_2016(sourceReader.get("StanLowSort_2016"))
                        .StanLowSort_2017(sourceReader.get("StanLowSort_2017"))
                        .AbsLowSort_2014(sourceReader.get("AbsLowSort_2014"))
                        .AbsLowSort_2015(sourceReader.get("AbsLowSort_2015"))
                        .AbsLowSort_2016(sourceReader.get("AbsLowSort_2016"))
                        .AbsLowSort_2017(sourceReader.get("AbsLowSort_2017")).build();
                sourceDataList.add(Data);
            }
            //重新写入csv
            CsvWriter csvWriter = new CsvWriter(sourceListFiles[i].listFiles()[2].getPath(), separator, Charset.forName("UTF-8"));
            //为数组追加一个字段
            csvWriter.writeRecord(sourceReader.getHeaders());
            List<String[]> list =  sourceDataList.stream().map(sourceData -> {
                Field[] fileds = sourceData.getClass().getDeclaredFields();
                String[] strs = new String[fileds.length];
                csvWirter(sourceData, fileds, strs);
                return strs;
            }).collect(Collectors.toList());

            list.forEach(strings ->{
                try {
                    csvWriter.writeRecord(strings);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }
    }

    static void csvWirter(sourceData sourceData, Field[] fileds, String[] strs) {
        for (int i1 = 0; i1 < fileds.length; i1++) {
            Field field = fileds[i1];
            field.setAccessible(true);
            try {
                strs[i1] = (String) field.get(sourceData);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}