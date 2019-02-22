package collegedata.demo.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文理科
 * @author zxy
 * @date 2018-09-11 16:57
 */
@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ScienceAndArt {

    /**
     * 理
     */
    SCIENCE(0,"理科"),
    /**
     * 文
     */
    ART(1,"文科");
    /**
     * 不限
     */
//    UNLIMITED(2,"不限");

    private int id;

    private String name;

    /**
     * 将id 转换成枚举
     */
    @JsonCreator
    public static ScienceAndArt fromId(String value){
        if (value ==null){
            return null;
        }
        final int id = Integer.valueOf(value);
        for (ScienceAndArt scienceAndArt : values()) {
            if (scienceAndArt.id == id)
                return scienceAndArt;
        }
        return null;
    }

    public static ScienceAndArt fromId(int value){
        return fromId(String.valueOf(value));
    }

}