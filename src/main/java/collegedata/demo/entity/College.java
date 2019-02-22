package collegedata.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Cacheable
public class College {

    @Id
    private Long id;

    private String name;

    private String city;             //大学所属城市

    private String code;                       //代码

    //private Integer sequence;             //大学排名

    private String collegeInitials;//大学拼音首字母

    @ManyToOne(fetch = FetchType.LAZY)
    private Province province;


}

