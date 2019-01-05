package cn.wh.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Users {
    private int uid;
    private String username;
    private String password;
    private int isAdmin;
}
