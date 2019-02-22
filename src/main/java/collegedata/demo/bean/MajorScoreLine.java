package collegedata.demo.bean;

import lombok.Builder;
import lombok.Data;

/**
 * author:panle
 * Date:2018/12/21
 * Time:10:26
 */
@Data
@Builder
public class MajorScoreLine {
    private  Long collegeId;
    private Integer year;
    private String course;
    private String majorName;
    private Long provinceId;
    private Integer scoreDiff;
    private Long enrollBatchId;
}
