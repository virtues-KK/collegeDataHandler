package collegedata.demo.entity;

import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 大学专业招生分数线
 * @author zxy
 * @date 2018-09-10 15:28
 */
@Entity
@Data
@Cacheable
public class EnrollMajorScoreLine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 大学
     */
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(nullable = false)
//    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
//    private College college;

    /**
     * 招生计划
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
    private EnrollStudentPlan enrollStudentPlan;

    /**
     * 专业方向名称
     */
    @Column(nullable = false,length = 500)
    private String name;

    /**
     * 招生代码
     * 这个代码不是指通用的专业国家编码
     * 而是指某学校某专业的招生代码
     */
//    private String majorCode;

    /**
     * 省
     */
//    @ManyToOne
//    @JoinColumn
//    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
//    private Province province;

    /**
     * 年份
     */
    @Column(nullable = false)
    private Integer year;
//
//    /**
//     * 文理科标识
//     */
//    @Enumerated
//    private ScienceAndArt scienceArt;

    /**
     * 批次名称
     */
//    private String batch;

    /**
     * 最高分
     */
    private Integer maxScore;

    /**
     * 最低分
     */
    private Integer minScore;

    /**
     * 最低位次
     */
    private Integer minRank;

    /**
     * 平均分
     */
    private Double averageScore;

    /**
     * 录取人数
     */
    private Integer enrollCount;

    private Integer scoreLineDiff;

}
