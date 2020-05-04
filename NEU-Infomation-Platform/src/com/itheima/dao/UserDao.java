package com.itheima.dao;

import com.itheima.domain.User;
import com.itheima.utils.DataSourceUtils;
import com.itheima.utils.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

public class UserDao {
    public int register(User user) {
        QueryRunner qr=new TxQueryRunner();
        String sql="insert into user values(?,?,?,?,?,?,?,?,?,?)";
        Object[] param={user.getUid(),user.getUsername(),user.getPassword(),
                        user.getName(), user.getEmail(),
                        user.getTelephone(),user.getBirthday(),
                        user.getSex(),user.getState(),user.getCode()};
        int row=0;
        try {
            row=qr.update(sql,param);
        } catch (SQLException e) {
            new RuntimeException(e);
        }
        return row;
    }

    public int active(String active) {
        QueryRunner qr=new TxQueryRunner();
        String sql="update user set state=? where code=?";
        int row=0;
        try {
            row = qr.update(sql, 1, active);
        } catch (SQLException e) {
            new RuntimeException(e);
        }
        return row;
    }

    public Long checkUsername(String username) {
        QueryRunner qr=new TxQueryRunner();
        String sql="select count(*) from user where username=?";
        Long row=0L;
        try {
            row=(Long)qr.query(sql,new ScalarHandler(), username);
        } catch (SQLException e) {
            new RuntimeException(e);
        }
        return row;
    }

    public User userLogin(String username, String password) {
        QueryRunner qr=new TxQueryRunner();
        String sql="select * from user where username=? ANd password=? AND state=1";
        User user=new User();
        try {
            user= qr.query(sql,new BeanHandler<User>(User.class),username,password);
        } catch (SQLException e) {
            new RuntimeException(e);
        }
        return user;
    }
}
