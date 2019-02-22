package collegedata.demo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class csvServiceTest {

    @Autowired
    private csvService csvService;

    @Autowired
    private EnrollCollegeService enrollCollegeService;

    @Test
    public void test() throws Exception {
        csvService.getEnrollCollege();
        //csvService.trunEnrollBatch();

    }

    @Test
    public void test1() throws Exception {
        csvService.turnFinalData();
    }

    @Test
    public void test2() throws Exception {
        csvService.fengzhuangMethod("beijing");
    }

    @Test
    public void test3() throws Exception {
        new EnrollCollegeService().getProvinceName();
    }

    @Test
    public void test4() throws Exception {
        enrollCollegeService.addCollegeId();
    }

}