package collegedata.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 招生信息
 * @author zxy
 * @date 2018-09-10 16:44
 */
@Data
@Cacheable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrollStudentPlan_1 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 招生代码
     */
    private String code;

    /**
     * 专业名称
     */
    private String name;

    /**
     * 招生批次
     */
    private String batch;

    /**
     * 计划数量
     */
    private Integer planCount;

    /**
     * 学年
     */
    private Integer yearOfStudy;

    @Enumerated
    private ScienceAndArt scienceArt;

    @ManyToOne
    @JoinColumn
    private Province province;

    /**
     * 学费
     */
    private String price;

    /**
     * 年份
     */
    private Integer year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private College college;

    private String Mean;
    private String Stand;
    private String CollegeName;
    private String Course;


}