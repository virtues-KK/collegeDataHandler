package collegedata.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @time:7/19
 * @author:潘乐
 * @description:省份的基本类
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Cacheable
public class Province {

    /**
     * 省编号
     */
    @Id
    private Long id;

    /**
     * 省名
     */
    private String name;

}
