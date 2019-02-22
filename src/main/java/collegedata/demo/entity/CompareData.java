package collegedata.demo.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * author:panle
 * Date:2018/12/3
 * Time:14:14
 */
@Data
@Builder
@Entity
public class CompareData {

    @Id
    private String id1;

    private String id2;

    private String id3;

    private String CollegeName;

    private String Province;

    private String LowSort_2014;

    private String LowSort_2015;

    private String LowSort_2016;

    private String LowSort_2017;

    private String LowSort_2018;

    private String To_LowSort_2014;

    private String To_LowSort_2015;

    private String To_LowSort_2016;

    private String To_LowSort_2017;

    private String To_LowSort_2018;

    private String Course;

    private String MajorName;
}
