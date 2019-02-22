package collegedata.demo.controller.dataCompare;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class collegeScoreDiffTest {

    @Autowired
    private collegeScoreDiff collegeScoreDiff;

    @Autowired
    private collegeScoreLineUpdate collegeScoreLineUpdate;

    @Test
    public void Test() throws IOException {
        //collegeScoreLineUpdate.updateDiff();
        collegeScoreDiff.mapToUpdate();
    }

    @Test
    public void Test1() throws IOException {
        collegeScoreLineUpdate.updateDiff();
        //collegeScoreDiff.updateCollegeScoreLineData();

    }

}