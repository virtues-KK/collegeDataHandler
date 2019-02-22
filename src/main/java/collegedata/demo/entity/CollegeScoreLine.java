package collegedata.demo.entity;

import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 大学分数线
 * @author zxy
 * @date 2018-09-10 15:28
 */
@Entity
@Data
public class CollegeScoreLine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 大学名称
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private College college;

    /**
     * 省名称
     */
    @ManyToOne
    @JoinColumn
    private Province province;

    /**
     * 年份
     */
    @Column(nullable = false)
    private Integer year;

    /**
     * 文理科标识
     */
    @Column(nullable = false)
    @Enumerated
    private ScienceAndArt scienceArt;

    /**
     * 批次名称
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private EnrollBatch enrollBatch;

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

    /**
     * 线差
     */
    private Integer scoreLineDiff;

}
