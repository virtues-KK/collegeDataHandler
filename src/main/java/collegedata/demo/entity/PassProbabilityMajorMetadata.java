package collegedata.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 专业录取概率
 * @author zxy
 * @date 2018-12-10 18:08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PassProbabilityMajorMetadata {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long batchId;

    @Enumerated(EnumType.ORDINAL)
    private ScienceAndArt scienceArt;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Province province;

//    @ManyToOne(optional = false)
//    @JoinColumn(unique = true,nullable = false)
//    private EnrollStudentPlan enrollStudentPlan;

    private Integer max;

    private Integer min;

    private Integer stand;

    private Integer mean;


}
