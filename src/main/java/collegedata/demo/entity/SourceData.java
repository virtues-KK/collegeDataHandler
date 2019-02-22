package collegedata.demo.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;

/**
 * author:panle
 * Date:2018/12/3
 * Time:14:08
 */
@Entity
@Data
@Builder
public class SourceData {

    @Id
    private String id1;

    private String id2;

    private String id3;

    private String CollegeName;

    private String Province;

    private String LowSort_2014 = "0";

    private String LowSort_2015;

    private String LowSort_2016;

    private String LowSort_2017;

    private String LowSort_2018 = "0";

    private String Course;
}
