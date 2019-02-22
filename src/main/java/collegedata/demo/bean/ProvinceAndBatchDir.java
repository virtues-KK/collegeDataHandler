package collegedata.demo.bean;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProvinceAndBatchDir {
    private String provinceId;
    private String provinceName;
    private String batchId;
    private String enrollBatchId;
    private String scoreLineDiff;
    private String course;
    private String collegeName;
    private String year;
}