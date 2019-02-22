package collegedata.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author:panle
 * Date:2018/12/8
 * Time:14:22
 * 最终结果标准招生计划类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ESPlan {

    private String CollegeName;

    private String Batch;

    private String Course;

    private String EnrollStudentPlanId;

    private String Major;

    private String Mean;

    private String Stand;

    private String ProvinceName;

}
