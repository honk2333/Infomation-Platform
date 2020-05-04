package com.itheima.service;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;

public class UserService {
    UserDao dao=new UserDao();
    public boolean register(User user) {

        int row=dao.register(user);
        return row>0?true:false;
    }

    public boolean active(String active) {
        int row=dao.active(active);
        return row>0?true:false;
    }

    public boolean checkUsername(String username) {
        Long row=dao.checkUsername(username);
        return row>0?true:false;
    }

    public User userLogin(String username, String password) {
        return dao.userLogin(username,password);
    }
}
