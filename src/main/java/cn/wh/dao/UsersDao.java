package cn.wh.dao;

import cn.wh.pojo.Users;

public interface UsersDao {
    public int regist(Users user);
    public  Users login(Users user);
    public int isexist(String name);
}
