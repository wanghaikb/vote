package cn.wh.service;

import cn.wh.pojo.Users;

public interface UsersService {
    public int regist(Users user);
    public  Users login(Users user);
    public int isexist(String name);
}
