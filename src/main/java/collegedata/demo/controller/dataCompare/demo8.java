//package collegedata.demo.controller.dataCompare;
//
//import collegedata.demo.entity.College;
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
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * author:panle
// * Date:2018/12/7
// * Time:14:38
// */
//@Component
//public class demo8 {
//
//    final static String FileName = "C:\\Users\\sunwukong\\Desktop\\招生专业数据 - 副本";
//
//    @Autowired
//    private EnrollStudentPlanRepo enrollStudentPlanRepo;
//
//    void test() throws Exception {
//        List<EnrollStudentPlan> list = enrollStudentPlanRepo.findAll();
//        File[] files = new File(FileName).listFiles();
//        for (int i = 0; i < files.length; i++) {
//            int finalI = i;
//            System.out.println(finalI + "...................................................................");
//            List<EnrollStudentPlan_1> list_1 = new ArrayList<>();
//
//            try {
//                CsvReader reader = new CsvReader(files[finalI].getPath(), ',', Charset.forName("utf-8"));
//                Matcher matcher = Pattern.compile("out-(.{2,3}).csv").matcher(files[finalI].getName());
//                if (matcher.find()) {
//                    // System.out.println(matcher.group(1));
//                }
//                reader.readHeaders();
//                while (reader.readRecord()) {
//                    String CollegeName = reader.get("CollegeName");
//                    String Batch = reader.get("Batch");
//                    String Course = reader.get("Course");
//                    String Major = reader.get("Major");
//                    String Mean = reader.get("Mean");
//                    String Stand = reader.get("Stand");
//                    for (int j = 0; j < list.size(); j++) {
//                        String collegeName = list.get(j).getCollege().getName();
//                        String batch = list.get(j).getBatch();
//                        String provinceName = list.get(j).getProvince().getName();
//                        String major = list.get(j).getName();
//                        batch = demo10.getString(batch);
//                        Matcher matcher1 = Pattern.compile("^.+?(?=\\(.*?\\))+|.+").matcher(major);
//                        if (matcher1.find()) {
//                            major = matcher1.group();
//                        }
//                        //matcher1.group();
//                        if (collegeName.equals(CollegeName)
//                                && Major.equals(major)
//                                && batch.equals(Batch)
//                                && provinceName.equals(matcher.group(1))) {
//                            //再判断course的值
//                            if ((Course.equals("0") && list.get(j).getScienceArt().equals(ScienceAndArt.SCIENCE)) || (Course.equals("1") && list.get(j).getScienceArt().equals(ScienceAndArt.ART))) {
//                                EnrollStudentPlan_1 enrollStudentPlan_1 = new EnrollStudentPlan_1();
//                                BeanUtils.copyProperties(list.get(j), enrollStudentPlan_1);
//                                enrollStudentPlan_1.setBatch(batch);
//                                enrollStudentPlan_1.setMean(Mean);
//                                enrollStudentPlan_1.setStand(Stand);
//                                list_1.add(enrollStudentPlan_1);
//                                break;
//                            }
//                        } else {
//                            //不能匹配上就需要把原来的值放回,专业id设为-1
//                            EnrollStudentPlan_1 enrollStudentPlan_1 = EnrollStudentPlan_1.builder().college(College.builder().name(CollegeName).build())
//                                    .batch(Batch).Course(Course).name(Major).id(-1L).Mean(Mean).Stand(Stand).build();
//                            list_1.add(enrollStudentPlan_1);
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            System.out.println(list_1.size());
//
//            //写入数据
//            CsvWriter writer = new CsvWriter(files[i].getPath(), ',', Charset.forName("utf-8"));
//            String[] str = {"CollegeName", "Batch", "Course", "Major", "EnrollStudentPlanId", "Mean", "Stand"};
//            writer.writeRecord(str);
//            list_1.stream().map(enrollStudentPlan_1 -> new String[]{enrollStudentPlan_1.getCollege().getName(),
//                    enrollStudentPlan_1.getBatch(),
//                    enrollStudentPlan_1.getCourse(),
//                    enrollStudentPlan_1.getName(),
//                    enrollStudentPlan_1.getId().toString(),
//                    enrollStudentPlan_1.getMean(),
//                    enrollStudentPlan_1.getStand()}).forEach(strings -> {
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
//}
