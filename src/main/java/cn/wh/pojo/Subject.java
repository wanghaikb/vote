package cn.wh.pojo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.EAN;

import java.util.List;

@Getter
@Setter
public class Subject {
    private int sid;
    private String title;
    private String type;
    private int options;
    private int votes;

}
