package cn.wh.service;

import cn.wh.dao.UsersDao;
import cn.wh.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersDao usersDao;
    @Override
    public int regist(Users user) {
        return usersDao.regist(user);
    }

    @Override
    public Users login(Users user) {
        return usersDao.login(user);
    }

    @Override
    public int isexist(String name) {
        return usersDao.isexist(name);
    }
}
