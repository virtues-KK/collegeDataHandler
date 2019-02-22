package collegedata.demo.controller.dataCompare;

import collegedata.demo.entity.CompareData;
import collegedata.demo.entity.SourceData;
import collegedata.demo.repository.CompareDataRepo;
import collegedata.demo.repository.SourceDataRepo;
import com.csvreader.CsvReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.Charset;

/**
 * author:panle
 * Date:2018/12/3
 * Time:11:08
 */
@Slf4j
@Component
class demo1 {
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
        //System.out.println(sourceFileList[1].getPath());

        File compareFile = new File(compareFilePath);
        File[] compareFileList = compareFile.listFiles();

        //拿出所有元数据中的位次形成数据存入数据库
        for (int i = 0; i < sourceFileList.length; i++) {
            CsvReader sourceReader = new CsvReader(sourceFileList[i].getPath(), separator, Charset.forName("UTF-8"));
            sourceReader.readHeaders();
            while (sourceReader.readRecord()) {
                SourceData sourceData1 = SourceData.builder().CollegeName(sourceReader.get("CollegeName")).LowSort_2015(sourceReader.get("LowSort_2015"))
                        .LowSort_2016(sourceReader.get("LowSort_2016")).LowSort_2017(sourceReader.get("LowSort_2017")).Province(sourceReader.get("ProvinceName")).build();
                sourceDataRepo.save(sourceData1);
                //System.out.println(sourceData1.getCollegeName());
            }
        }
        //拿出所有的比较数据的位次形成数据存入数据库
        for (int i = 0; i< compareFileList.length; i++){
            CsvReader compareReader = new CsvReader(compareFileList[i].getPath(),separator,Charset.forName("UTF-8"));
            compareReader.readHeaders();
            while (compareReader.readRecord()){
                CompareData compareData = CompareData.builder().CollegeName(compareReader.get("CollegeName")).LowSort_2015(compareReader.get("LowSort_2015"))
                        .LowSort_2016(compareReader.get("LowSort_2016")).LowSort_2017(compareReader.get("LowSort_2017")).Province(compareReader.get("ProvinceName")).build();
                compareDataRepo.save(compareData);
            }
        }
        //根据collegeName和最低位次来比较,比较数据与元数据的同一条数据的最低位次去做比较


    }


}
