package collegedata.demo.controller;

import collegedata.demo.entity.EnrollMajorScoreLine;
import collegedata.demo.service.UpdateEnrollMajorScoreLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * author:panle
 * Date:2018/12/21
 * Time:11:10
 */
@Component
public class UpdateEnrollMajorScoreLine {

    @Autowired
    private UpdateEnrollMajorScoreLineService updateEnrollMajorScoreLineService;

    public void updateMajorScoreLine() throws IOException {
        updateEnrollMajorScoreLineService.calculateDiff(updateEnrollMajorScoreLineService.getEMSLine());

    }

}
