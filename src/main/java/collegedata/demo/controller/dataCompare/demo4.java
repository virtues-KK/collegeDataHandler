package collegedata.demo.controller.dataCompare;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author:panle
 * Date:2018/12/5
 * Time:10:16
 * 删除不符合要求的数据,转大学id;
 */
public class demo4 {

    final static String sourceFilePath = "C:\\Users\\sunwukong\\Desktop\\比较数据 - 副本";
    final static String deleteFilePath = "C:\\Users\\sunwukong\\Desktop\\最终结果 - 副本";
    final static char separator = ',';

    void deleteOthers() throws Exception {
        File[] deleteListFilesiles = new File(deleteFilePath).listFiles();
        File[] sourceListFilesiles = new File(sourceFilePath).listFiles();

        //获取每一条元数据记录,转成Java对象
        for (int i = 0; i < sourceListFilesiles.length; i++) {
            CsvReader sourceReader = new CsvReader(sourceListFilesiles[i].listFiles()[2].getPath(), separator, Charset.forName("UTF-8"));
            List<sourceData> sourceDataList = new ArrayList<>();
            sourceReader.readHeaders();
            while (sourceReader.readRecord()) {
                sourceData Data = sourceData.builder().ProvinceName(sourceReader.get("ProvinceName"))
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

            //读取每一条要修改的记录
            assert deleteListFilesiles != null;
            List<deleteData> deleteDataList = new ArrayList<>();
            CsvReader deleteReader = new CsvReader(deleteListFilesiles[0].getPath(), separator, Charset.forName("UTF-8"));
            deleteReader.readHeaders();
            while (deleteReader.readRecord()) {
                String province = deleteReader.get("Province");
                String majorName = deleteReader.get("MajorName");
                String course = deleteReader.get("Course");
                String collegeName = deleteReader.get("CollegeName");
                String id1 = deleteReader.get("id1");
                String id2 = deleteReader.get("id2");
                String id3 = deleteReader.get("id3");
                deleteData data = deleteData.builder().province(province).majorName(majorName).course(course).collegeName(collegeName)
                        .id1(id1).id2(id2).id3(id3).build();
                deleteDataList.add(data);
            }
            //比较数据,修改数据,写入数据
            deleteDataList.forEach(deleteData -> {
                sourceDataList.forEach(sourceData -> {
                    if (deleteData.getCollegeName().equals(sourceData.getCollegeName()) &&
                            deleteData.getCourse().equals(sourceData.getCourse()) &&
                            deleteData.getMajorName().equals(sourceData.getMajorName()) &&
                            deleteData.getProvince().equals(sourceData.getProvinceName())
                    ) {
                        if (deleteData.getId1().equals("15")) {
                            sourceData.setLowSort_2015("-1");
                        } else if (deleteData.getId2().equals("16")) {
                            sourceData.setLowSort_2016("-1");
                        } else if (deleteData.getId3().equals("17")) {
                            sourceData.setLowSort_2017("-1");
                        }
                    }
                });
            });
            //重新写入csv
            CsvWriter csvWriter = new CsvWriter(sourceListFilesiles[i].listFiles()[2].getPath(), separator, Charset.forName("UTF-8"));
            //为数组追加一个字段
            String[] headers = sourceReader.getHeaders();
            String[] Headers = new String[headers.length +1];
            System.arraycopy(headers,0,Headers,1, headers.length);
            Headers[0] = "CollegeId";
            csvWriter.writeRecord(Headers);
            List<String[]> list =  sourceDataList.stream().map(sourceData -> {
                Field[] fileds = sourceData.getClass().getDeclaredFields();
                String[] strs = new String[fileds.length];
                demo5.csvWirter(sourceData, fileds, strs);
                System.out.println(strs[1]);
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

}

@Data
@Builder
class sourceData {
    private String id;
    private String ProvinceName;
    private String CollegeName;
    private String Batch;
    private String MajorName;
    private String Course;
    private String EnterNum_2014;
    private String EnterNum_2015;
    private String EnterNum_2016;
    private String EnterNum_2017;
    private String MinSort_2014;
    private String MinSort_2015;
    private String MinSort_2016;
    private String MinSort_2017;
    private String LowSort_2014;
    private String LowSort_2015;
    private String LowSort_2016;
    private String LowSort_2017;
    private String Count_2014;
    private String Count_2015;
    private String Count_2016;
    private String Count_2017;
    private String StanCount_2014;
    private String StanCount_2015;
    private String StanCount_2016;
    private String StanCount_2017;
    private String RelativeLowSort_2014;
    private String RelativeLowSort_2015;
    private String RelativeLowSort_2016;
    private String RelativeLowSort_2017;
    private String StanLowSort_2014;
    private String StanLowSort_2015;
    private String StanLowSort_2016;
    private String StanLowSort_2017;
    private String AbsLowSort_2014;
    private String AbsLowSort_2015;
    private String AbsLowSort_2016;
    private String AbsLowSort_2017;
}

@Data
@Builder
class deleteData {
    private String province;
    private String majorName;
    private String course;
    private String collegeName;
    private String id1;
    private String id2;
    private String id3;

}
