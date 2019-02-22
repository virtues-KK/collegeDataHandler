package collegedata.demo.controller.dataCompare;

import collegedata.demo.entity.CollegeScoreLine;
import collegedata.demo.repository.CollegeScoreLineRepo;
import collegedata.demo.service.AsyncTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

/**
 * author:panle
 * Date:2018/12/20
 * Time:21:17
 */
@Component
@Service
public class collegeScoreLineUpdate {
    private final CollegeScoreLineRepo collegeScoreLineRepo;

    @Resource
    private AsyncTaskService asyncTaskService;

    @Autowired
    public collegeScoreLineUpdate(CollegeScoreLineRepo collegeScoreLineRepo) {
        this.collegeScoreLineRepo = collegeScoreLineRepo;
    }

    @Transactional
    public void updateDiff() throws IOException {
    }
}
