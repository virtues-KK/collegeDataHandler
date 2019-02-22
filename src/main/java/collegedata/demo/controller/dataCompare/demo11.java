//package collegedata.demo.controller.dataCompare;
//
//import collegedata.demo.entity.ESPlan;
//import collegedata.demo.entity.EnrollStudentPlan;
//import collegedata.demo.entity.ScienceAndArt;
//import collegedata.demo.repository.EnrollStudentPlanRepo;
//import com.csvreader.CsvReader;
//import com.csvreader.CsvWriter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * author:panle
// * Date:2018/12/8
// * Time:14:18
// */
//@Component
//public class demo11 {
//
//    final static String FileName = "C:\\Users\\sunwukong\\Desktop\\招生专业数据 - 副本 (3)";
//
//    @Autowired
//    private EnrollStudentPlanRepo enrollStudentPlanRepo;
//
//    void test() throws Exception {
//        List<EnrollStudentPlan> list = enrollStudentPlanRepo.findAll();
//        List<ESPlan> esPlans = new ArrayList<>();
//        //1.把DB里面的数据转换成标准格式的list
//        list.forEach(enrollStudentPlan -> {
//            String batch = enrollStudentPlan.getBatch();
//            batch = getString(batch);
//            String course = "0";
//            if (enrollStudentPlan.getScienceArt().equals(ScienceAndArt.ART)) {
//                course = "1";
//            }
//            String major = enrollStudentPlan.getName();
//            Matcher matcher = Pattern.compile("^.+?(?=\\(.*?\\))+|.+").matcher(major);
//            if (matcher.find()) {
//                major = matcher.group();
//            }
//            ESPlan esPlan = ESPlan.builder().CollegeName(enrollStudentPlan.getCollege().getName())
//                    .Batch(batch)
//                    .Major(major)
//                    .EnrollStudentPlanId(enrollStudentPlan.getId().toString())
//                    .Course(course)
//                    .ProvinceName(enrollStudentPlan.getProvince().getName())
//                    .build();
//            esPlans.add(esPlan);
//        });
//        System.out.println(esPlans.size());
//        //2.读出csv文件里面的资料,collegename,batch,course,major相等,其他的补全
//        File[] files = new File(FileName).listFiles();
//        List<ESPlan> CSVesPlan = new ArrayList<>();
//        for (int i = 0; i < files.length; i++) {
//            CsvReader reader = new CsvReader(files[i].getPath(), ',', Charset.forName("utf-8"));
//            Matcher matcher = Pattern.compile("out-(.{2,3}).csv").matcher(files[i].getName());
//            if (matcher.find()) {
////                System.out.println(matcher.group(1));
//            }
//            reader.readHeaders();
//            while (reader.readRecord()) {
//                ESPlan esPlan = ESPlan.builder().ProvinceName(matcher.group(1))
//                        .Batch(reader.get("Batch"))
//                        .Course(reader.get("Course"))
//                        .Major(reader.get("Major"))
//                        .Mean(reader.get("Mean"))
//                        .Stand(reader.get("Stand"))
//                        .CollegeName(reader.get("CollegeName"))
//                        .build();
//                CSVesPlan.add(esPlan);
//            }
////            System.out.println(CSVesPlan.size());
//            //3.比较两个list,然后得到最终的list,写入csv
//            CSVesPlan.forEach(esPlan_CSV -> esPlans.forEach(esPlan_DB -> {
//                if (esPlan_CSV.getBatch().equals(esPlan_DB.getBatch())
//                        && esPlan_CSV.getCollegeName().equals(esPlan_DB.getCollegeName())
//                        && esPlan_CSV.getCourse().equals(esPlan_DB.getCourse())
//                        && esPlan_CSV.getMajor().equals(esPlan_DB.getMajor())
//                        && esPlan_CSV.getProvinceName().equals(esPlan_DB.getProvinceName())
//                ) {
//                    esPlan_CSV.setEnrollStudentPlanId(esPlan_DB.getEnrollStudentPlanId());
//                    esPlan_CSV.setProvinceName(esPlan_DB.getProvinceName());
//                }
//            }));
//            //4.直接写CSVPlan就可以了
//            CsvWriter writer = new CsvWriter(files[i].getPath(), ',', Charset.forName("utf-8"));
//            String[] str = {"CollegeName", "ProvinceName", "Batch", "Course", "Major", "EnrollStudentPlanId", "Mean", "Stand"};
//            writer.writeRecord(str);
//            CSVesPlan.forEach(esPlan -> {
//                String[] strings = {
//                        esPlan.getCollegeName(),
//                        esPlan.getProvinceName(),
//                        esPlan.getBatch(),
//                        esPlan.getCourse(),
//                        esPlan.getMajor(),
//                        esPlan.getEnrollStudentPlanId(),
//                        esPlan.getMean(),
//                        esPlan.getStand(),
//                };
//                try {
//                    writer.writeRecord(strings);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                writer.flush();
//            });
//
//
//        }
//
//    }
//
//    static String getString(String batch) {
//        switch (batch) {
//            case "文科 专科批":
//            case "理科 专科批":
//            case "文科 高职专科":
//            case "理科 高职专科":
//            case "文科 高职高专":
//            case "理科 高职高专":
//            case "文科":
//            case "理科":
//                batch = "4";
//
//                break;
//            case "文科 本一批":
//            case "理科 本一批":
//            case "文科 本科A段":
//            case "理科 本科A段":
//                batch = "1";
//
//                break;
//            case "文科 本二批":
//            case "理科 本二批":
//            case "文科 本科B段":
//            case "理科 本科B段":
//            case "文科 二批C":
//            case "理科 二批C":
//                batch = "2";
//
//                break;
//            case "文科 本三批":
//            case "理科 本三批":
//                batch = "3";
//                break;
//        }
//        return batch;
//    }
//}