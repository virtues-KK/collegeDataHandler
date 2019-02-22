package collegedata.demo.controller.dataCompare;

import collegedata.demo.entity.CompareData;
import collegedata.demo.entity.SourceData;
import collegedata.demo.repository.CompareDataRepo;
import collegedata.demo.repository.SourceDataRepo;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * author:panle
 * Date:2018/12/3
 * Time:11:08
 */
@Slf4j
@Component
class demo2 {
    final static String sourceFilePath = "C:\\Users\\sunwukong\\Desktop\\元数据";
    final static String compareFilePath = "C:\\Users\\sunwukong\\Desktop\\比较数据";
    final static char separator = ',';

    @Autowired
    private SourceDataRepo sourceDataRepo;

    @Autowired
    private CompareDataRepo compareDataRepo;

    void read() throws Exception {
        File sourceFile = new File(sourceFilePath);
        File[] sourceFileList = sourceFile.listFiles();
        assert sourceFileList != null;

        File compareFile = new File(compareFilePath);
        File[] compareFileList = compareFile.listFiles();
        System.out.println(compareFileList[0].listFiles()[2].getName());

        //拿出所有元数据中的位次形成数据新建集合
        List<SourceData> sourceData = new ArrayList<>();
        for (int i = 0; i < sourceFileList.length; i++) {
            CsvReader sourceReader = new CsvReader(sourceFileList[i].getPath(), separator, Charset.forName("UTF-8"));
            sourceReader.readHeaders();
            while (sourceReader.readRecord()) {
                SourceData sourceData1 = SourceData.builder().CollegeName(sourceReader.get("CollegeName")).LowSort_2015(sourceReader.get("LowSort_2015"))
                        .LowSort_2016(sourceReader.get("LowSort_2016")).LowSort_2017(sourceReader.get("LowSort_2017")).Province(sourceReader.get("ProvinceName")).Course(sourceReader.get("Course")).build();
                sourceData.add(sourceData1);
            }
        }
        System.out.println(sourceData.size());
        //拿出所有的比较数据的位次形成数据新建集合
        List<CompareData> compareData = new ArrayList<>();
        for (int i = 0; i < compareFileList.length; i++) {
            CsvReader compareReader = new CsvReader(compareFileList[i].listFiles()[2].getPath(), separator, Charset.forName("UTF-8"));
            compareReader.readHeaders();
            while (compareReader.readRecord()) {
                CompareData compareData1 = CompareData.builder().CollegeName(compareReader.get("CollegeName")).LowSort_2015(compareReader.get("LowSort_2015"))
                        .LowSort_2016(compareReader.get("LowSort_2016")).LowSort_2017(compareReader.get("LowSort_2017")).Province(compareReader.get("ProvinceName"))
                        .Course(compareReader.get("Course")).MajorName(compareReader.get("MajorName")).build();
                compareData.add(compareData1);
            }
        }
        System.out.println(compareData.size());
        //根据collegeName和最低位次来比较,比较数据与元数据的同一条数据的最低位次去做比较
        List<CompareData> endList = new ArrayList<>();
        sourceData.forEach(sourceData1 -> {
            String CollegeName = sourceData1.getCollegeName();
            String Course = sourceData1.getCourse();
            String LowSort_2015 = sourceData1.getLowSort_2015();
            String LowSort_2016 = sourceData1.getLowSort_2016();
            String LowSort_2017 = sourceData1.getLowSort_2017();
            String province = sourceData1.getProvince();
            compareData.forEach(compareData1 -> {
                String CollegeName1 = compareData1.getCollegeName();
                String Course1 = compareData1.getCourse();
                String LowSort_2015_1 = compareData1.getLowSort_2015();
                String LowSort_2016_1 = compareData1.getLowSort_2016();
                String LowSort_2017_1 = compareData1.getLowSort_2017();
                String province_1 = compareData1.getProvince();
                if (CollegeName.equals(CollegeName1) && Course.equals(Course1) && province.equals(province_1)) {
                    //比较15年到17年的最低位次,元数据较低的正常,元数据高的把id
                    if (Integer.valueOf(LowSort_2015) < Integer.valueOf(LowSort_2015_1) && Integer.valueOf(LowSort_2015) != 0 && Integer.valueOf(LowSort_2015_1) != 0) {
                        compareData1.setId1("15");
                        compareData1.setTo_LowSort_2015(LowSort_2015);
                        endList.add(compareData1);
                    } else if (Integer.valueOf(LowSort_2016) < Integer.valueOf(LowSort_2016_1) && Integer.valueOf(LowSort_2016) != 0 && Integer.valueOf(LowSort_2016_1) != 0) {
                        compareData1.setId2("16");
                        compareData1.setTo_LowSort_2016(sourceData1.getLowSort_2016());
                        endList.add(compareData1);
                    } else if (Integer.valueOf(LowSort_2017) < Integer.valueOf(LowSort_2017_1) && Integer.valueOf(LowSort_2017) != 0 && Integer.valueOf(LowSort_2017_1) != 0) {
                        compareData1.setId3("17");
                        compareData1.setTo_LowSort_2017(sourceData1.getLowSort_2017());
                        endList.add(compareData1);
                    }
                }
            });
        });
        System.out.println(endList.size());


        //再把结果写入csv
        String FilePath = "C:\\Users\\sunwukong\\Desktop\\最终结果\\endList.csv";
        CsvWriter writer = new CsvWriter(FilePath, separator, Charset.forName("UTF-8"));
        String[] Headers = {
                "id1",
                "id2",
                "id3",
                "CollegeName",
                "Province",
                "LowSort_2014",
                "14年对比资料",
                "LowSort_2015",
                "15年对比资料",
                "LowSort_2016",
                "16年对比资料",
                "LowSort_2017",
                "17年对比资料",
                "LowSort_2018",
                "18年对比资料",
                "Course",
                "MajorName"
        };
        writer.writeRecord(Headers);
        endList.forEach(compareData1 -> {
            String[] recond = {compareData1.getId1(), compareData1.getId2(), compareData1.getId3(), compareData1.getCollegeName(), compareData1.getProvince(), compareData1.getLowSort_2014(),
                    compareData1.getTo_LowSort_2014(), compareData1.getLowSort_2015(), compareData1.getTo_LowSort_2015(), compareData1.getLowSort_2016(), compareData1.getTo_LowSort_2016(),
                    compareData1.getLowSort_2017(), compareData1.getTo_LowSort_2017(), compareData1.getLowSort_2018(), compareData1.getTo_LowSort_2018(), compareData1.getCourse(), compareData1.getMajorName()};
            try {
                writer.writeRecord(recond);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }
}
