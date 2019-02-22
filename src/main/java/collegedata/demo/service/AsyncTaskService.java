package collegedata.demo.service;

import collegedata.demo.bean.ProvinceAndBatchDir;
import collegedata.demo.controller.dataCompare.collegeScoreDiff;
import collegedata.demo.entity.CollegeScoreLine;
import collegedata.demo.entity.ScienceAndArt;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Future;

/**
 * author:panle
 * Date:2018/12/20
 * Time:21:36
 */
@Service
public class AsyncTaskService {
    Random random = new Random();// 默认构造方法

    @Transactional
    public List<CollegeScoreLine> updatecollegeScoreLine(List<CollegeScoreLine> collegeScoreLineList) throws IOException {
        HashMap<String, Integer> collegeScoreLineMap = new HashMap<>();
        new collegeScoreDiff().trunToMap(collegeScoreLineList, collegeScoreLineMap);
        List<ProvinceAndBatchDir> provinceAndBatchDirs = new collegeScoreDiff().turnLocalData();

        provinceAndBatchDirs.forEach(provinceAndBatchDir -> {
            String collegeName = provinceAndBatchDir.getCollegeName();
            String year = provinceAndBatchDir.getYear();
            String enrollbatchId = provinceAndBatchDir.getEnrollBatchId();
            String provinceName = provinceAndBatchDir.getProvinceName();
            String course = provinceAndBatchDir.getCourse();
            String key = String.join(" ", collegeName, year, enrollbatchId, provinceName, course);
            String diff = provinceAndBatchDir.getScoreLineDiff();
            if (diff.equals("LineDiff")) {
                return;
            }
            Integer value = Integer.valueOf(diff);
            //localDataMap.put(key,value);
            // 包含key的话,给key,添加value
            if (collegeScoreLineMap.containsKey(key)) {
                collegeScoreLineMap.put(key, value);
                System.out.println(collegeScoreLineMap.get(key) + "-------------------------------------------------");
                String[] elements = key.split(" ");

                collegeScoreLineList.forEach(collegeScoreLine -> {
                    String collegeName_1 = collegeScoreLine.getCollege().getName();
                    Integer year_1 = collegeScoreLine.getYear();
                    Long enrollbatchId_1 = collegeScoreLine.getEnrollBatch().getId();
                    String provinceName_1 = collegeScoreLine.getProvince().getName();
                    String course_1 = "1";
                    if (collegeScoreLine.getScienceArt().equals(ScienceAndArt.SCIENCE)) {
                        course_1 = "0";
                    }
                    if (elements[0].equals(collegeName_1)
                            && elements[1].equals(year_1.toString())
                            && elements[2].equals(enrollbatchId_1)
                            && elements[3].equals(provinceName_1)
                            && elements[4].equals(course_1)) {
                        collegeScoreLine.setScoreLineDiff(value);
                    }
                });
            }
        });
        return collegeScoreLineList;
    }
    @Async
    public Future<String> asyncInvokeReturnFuture(int i) throws InterruptedException {
        System.out.println("input is " + i);
        Thread.sleep(1000 * random.nextInt(i));

        // Future接收返回值，这里是String类型，可以指明其他类型
        Future<String> future = new AsyncResult<String>("success:" + i);

        return future;
    }


}
