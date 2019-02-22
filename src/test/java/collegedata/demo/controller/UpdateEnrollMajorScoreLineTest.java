package collegedata.demo.controller;

import collegedata.demo.service.UpdateEnrollMajorScoreLineService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateEnrollMajorScoreLineTest {

    @Autowired
    private UpdateEnrollMajorScoreLine updateEnrollMajorScoreLine;

    @Autowired
    private UpdateEnrollMajorScoreLineService updateEnrollMajorScoreLineService;

    @Test
    public void test() throws IOException {
        updateEnrollMajorScoreLine.updateMajorScoreLine();
        //updateEnrollMajorScoreLineService.getEMSLine();
    }

}