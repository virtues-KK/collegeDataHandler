package collegedata.demo.controller;

import collegedata.demo.entity.College;
import collegedata.demo.repository.CollegeRepo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * author:panle
 * Date:2018/11/8
 * Time:14:49
 * 处理贵州大学数据
 *
 * ProvinceName varchar(255) DEFAULT NULL,
 * CollegeName
 * Batch
 * Course
 * LineDiff_2015
 * LineDiff_2016
 * LineDiff_2017
 * LineDiff_2018
 * EnterNum_2015
 * EnterNum_2016
 * EnterNum_2017
 * EnterNum_2018
 * MinSort_2015
 * MinSort_2016
 * MinSort_2017
 * MinSort_2018
 * LowSort_2015
 * LowSort_2016
 * LowSort_2017
 * LowSort_2018
 * Count_2015
 * Count_2016
 * Count_2017
 * Count_2018
 * StanCount_2015
 * StanCount_2016
 * StanCount_2017
 * StanCount_2018
 * RelativeLowSort_2015
 * RelativeLowSort_2016
 * RelativeLowSort_2017
 * RelativeLowSort_2018
 * StanLowSort_2015
 * StanLowSort_2016
 * StanLowSort_2017
 * StanLowSort_2018
 * AbsLowSort_2015
 * AbsLowSort_2016
 * AbsLowSort_2017
 * AbsLowSort_2018
 *
 */

@RestController
@RequestMapping("college")
public class guizhou {
    @Autowired
    private CollegeRepo collegeRepo;

    private static String[] FILE_HEADER = {"CollegeName","Batch","Course","Mean","Stand"};
//    String[] province = {"out-云南.csv","out-北京.csv","out-吉林.csv","out-四川.csv","out-天津.csv","out-安徽.csv","out-山东.csv","out-山西.csv",
//            "out-广东.csv","out-广西.csv","out-新疆.csv","out-河北.csv","out-河南.csv","out-湖北.csv","out-湖南.csv","out-甘肃.csv","out-福建.csv"
//            ,"out-贵州.csv","out-辽宁.csv","out-重庆.csv","out-陕西.csv","out-黑龙江.csv"};
    String[] province = {"out-黑龙江.csv"};
    @GetMapping("1")
    public void resolve(){
        for (int i =0;i <= 21;i++){
            String FILE_NAME ="A:\\learn-project\\大学数据处理\\src\\main\\resources\\" + province[i];
            String writer_File_name = "A:\\learn-project\\大学数据处理\\src\\main\\resources\\" + province[i]+"result";
            this.dataProcessing(FILE_NAME,writer_File_name);
        }
    }
    //大学数据处理
    public void dataProcessing(String FILE_NAME,String writer_File_name){
        //读取现有数据库中所有的数据
        List<College> baseCollegeList = collegeRepo.findAll();
        //读取去重之后的所有贵州省的数据,设置跳过表头
        CSVFormat format = CSVFormat.DEFAULT.withHeader(FILE_HEADER).withSkipHeaderRecord();
        try{
            Reader in = new FileReader(FILE_NAME);
            Iterable<CSVRecord> records = format.parse(in);
            String CollegeName;
            String Batch;
            String Course;
            String Mean;
            String Stand;
            List<guizhouCollege> guizhouColleges = new ArrayList<>();
            List<guizhouCollege> collegeList = new ArrayList<>();
            for (CSVRecord record : records) {
                CollegeName = record.get("CollegeName");
                Batch = record.get("Batch");
                Course = record.get("Course");
                Mean = record.get("Mean");
                Stand = record.get("Stand");
                collegeList.add(guizhouCollege.builder().CollegeName(CollegeName).Batch(Batch).Course(Course).Mean(Mean).Stand(Stand).build());
            }
            List<guizhouCollege> list = collegeList.stream().distinct().collect(Collectors.toList());


            for (guizhouCollege guizhouCollege : list) {
                //在读取csv的循环中判断是否存在baseCollege的数据,
                Optional<College> optionalCollege =collegeRepo.findByName(guizhouCollege.getCollegeName());
                if (optionalCollege.isPresent()){
                    College college = optionalCollege.get();
                    guizhouColleges.add(guizhouCollege.builder().id(college.getId()).CollegeName(college.getName()).Batch(guizhouCollege.getBatch()).Course(guizhouCollege.getCourse()).
                            Mean(guizhouCollege.getMean()).Stand(guizhouCollege.getStand()).build());
                }else {
                    guizhouColleges.add(guizhouCollege.builder().id(-1L).CollegeName(guizhouCollege.getCollegeName()).Batch(guizhouCollege.getBatch()).Course(guizhouCollege.getCourse()).build());
                }
            }

            //写数据
            //CSV文件分隔符
             String NEW_LINE_SEPARATOR="\r\n";
            //初始化csvformat
            CSVFormat formator = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
            Writer out = new FileWriter(writer_File_name);
            CSVPrinter printer = new CSVPrinter(out,formator);
            printer.printRecord("id","CollegeName","Batch","Course","Mean","Stand");
            guizhouColleges.forEach(guizhouCollege->{
                try {
                    printer.printRecord(guizhouCollege.getId(),guizhouCollege.getCollegeName(),guizhouCollege.getBatch(),guizhouCollege.getCourse(),guizhouCollege.getMean(),guizhouCollege.getStand());
                    printer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("写完了");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

/**
 * guizhou entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class guizhouCollege{
    private Long id;
    private String CollegeName ;
    private String Batch;
    private String Course;
    private String Mean;
    private String Stand;
}
