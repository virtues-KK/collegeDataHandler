//package collegedata.demo.controller.dataCompare;
//
//import collegedata.demo.entity.EnrollStudentPlan;
//import collegedata.demo.entity.EnrollStudentPlan_1;
//import collegedata.demo.entity.ScienceAndArt;
//import collegedata.demo.repository.EnrollStudentPlanRepo;
//import com.csvreader.CsvReader;
//import com.csvreader.CsvWriter;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * author:panle
// * Date:2018/12/7
// * Time:11:40
// */
//@Component
//public class demo7 {
//
//    final static String FileName = "C:\\Users\\sunwukong\\Desktop\\招生专业数据 - 副本";
//    @Autowired
//    private EnrollStudentPlanRepo enrollStudentPlanRepo;
//
//
//    void test() throws Exception {
//        File[] files = new File(FileName).listFiles();
//        for (int i = 0; i < files.length; i++) {
//            CsvReader reader = new CsvReader(files[i].getPath(),',', Charset.forName("utf-8"));
//            //int t =files[i].getPath().length()-4;
//            //String name = files[i].getPath().substring(t-2,t);
//            Matcher matcher = Pattern.compile("out-(.{2,3}).csv").matcher(files[i].getName());
//            if (matcher.find()){
//                System.out.println(matcher.group(1));
//            }
//            //System.out.println(name);
//            List<EnrollStudentPlan_1> list = new ArrayList<>();
//            while(reader.readRecord()){
//                String CollegeName = reader.get("CollegeName");
//                String Batch = reader.get("Batch");
//                String Course = reader.get("Course");
//                String Major = reader.get("Major");
//                String Mean = reader.get("Mean");
//                String Stand = reader.get("Stand");
//                if (Course.equals("0")){
//                    Optional<EnrollStudentPlan> enrollStudentPlan =
//                            enrollStudentPlanRepo
//                                    .findByProvince_NameAndBatchAndNameAndScienceArtAndCollege_Name
//                                            (matcher.group(1),Batch,Major, ScienceAndArt.SCIENCE,CollegeName);
//                    if (enrollStudentPlan.isPresent()){
//                        //enrollStudentPlan.get().setMean(Mean);
//                        //enrollStudentPlan.get().setStand(Stand);
//                        EnrollStudentPlan_1 enrollStudentPlan_1 = new EnrollStudentPlan_1();
//                        BeanUtils.copyProperties(enrollStudentPlan.get(),enrollStudentPlan_1);
//                        list.add(enrollStudentPlan_1);
//                    }
//                }else {
//                    Optional<EnrollStudentPlan> enrollStudentPlan =enrollStudentPlanRepo.findByProvince_NameAndBatchAndNameAndScienceArtAndCollege_Name(matcher.group(1),Batch,Major,ScienceAndArt.ART,CollegeName);
//                    if (enrollStudentPlan.isPresent()){
//                        EnrollStudentPlan_1 enrollStudentPlan_1 = new EnrollStudentPlan_1();
//                        BeanUtils.copyProperties(enrollStudentPlan.get(),enrollStudentPlan_1);
//                        list.add(enrollStudentPlan_1);
//                    }
//                }
//            }
//            //写入
//            CsvWriter writer = new CsvWriter(files[i].getPath(),',', Charset.forName("utf-8"));
//            String[] str = {"CollegeName","Batch","Course","Major","EnrollStudentPlanId","Mean","Stand"};
//            writer.writeRecord(str);
//            list.forEach(enrollStudentPlan -> {
//                String[] strings = {enrollStudentPlan.getCollege().getName(),enrollStudentPlan.getBatch()
//                        ,enrollStudentPlan.getName(),enrollStudentPlan.getId().toString(),enrollStudentPlan.getMean(),enrollStudentPlan.getStand()};
//                try {
//                    writer.writeRecord(strings);
//                    writer.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//            list.clear();
//        }
//    }
//
//
//
//
//}
