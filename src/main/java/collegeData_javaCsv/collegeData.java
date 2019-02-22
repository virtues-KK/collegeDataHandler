//package collegeData_javaCsv;
//
//import com.csvreader.CsvReader;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
///**
// * author:panle
// * Date:2018/11/12
// * Time:15:58
// * collegeList.add(College.builder().CollegeName(CollegeName).Batch(Batch).Course(Course).Mean(Mean).Stand(Stand).build());
// */
//public class collegeData {
//    final static String FILE_NAME ="A:\\learn-project\\大学数据处理\\src\\main\\resources\\hubei-out.csv";
//    final static String writer_File_name = "A:\\learn-project\\大学数据处理\\src\\main\\resources\\new_hubei.csv";
//
//    public static void dataService() throws Exception {
//        CsvReader csvReader = new CsvReader(FILE_NAME,',', Charset.forName("utf-8"));
//        //读取数据\去重
//        List<College> collegeList = new ArrayList<>();
//        csvReader.readHeaders();
//        while (csvReader.readRecord()){
//            String CollegeName = csvReader.get("CollegeName");
//            String Batch = csvReader.get("Batch");
//            String Course = csvReader.get("Course");
//            String Mean = csvReader.get("Mean");
//            String Stand = csvReader.get("Stand");
//            //System.out.println(CollegeName);
//            collegeList.add(College.builder().CollegeName(CollegeName).Batch(Batch).Course(Course).Mean(Mean).Stand(Stand).build());
//        }
//        List<College> list = collegeList.stream().distinct().collect(Collectors.toList());
//        System.out.println(list.size());
//        //拿去重之后的数据和数据库中的数据对比
//    }
//
//
//
//
//
//
//
//    public static void main(String[] args) throws Exception {
//        dataService();
//
//    }
//
//
//
//
//
//
//}
//
///**
// * College entity
// */
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//class College{
//    private Long id;
//    private String CollegeName ;
//    private String Batch;
//    private String Course;
//    private String Mean;
//    private String Stand;
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof College)) return false;
//        College college = (College) o;
//        return Objects.equals(getCollegeName(), college.getCollegeName());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getCollegeName());
//    }
//}
