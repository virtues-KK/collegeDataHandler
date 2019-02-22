package collegedata.demo.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 招生批次
 * @author zxy
 * @date 2018-10-25 10:42
 */
@Data
@Entity
@Cacheable
public class EnrollBatch {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

}
