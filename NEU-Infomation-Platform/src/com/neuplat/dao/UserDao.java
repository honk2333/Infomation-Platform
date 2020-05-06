package com.neuplat.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.neuplat.domain.Product;
import com.neuplat.domain.User;
import com.neuplat.utils.DataSourceUtils;

import cn.itcast.jdbc.TxQueryRunner;

public class UserDao {

	public int regist(User user) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		int update = runner.update(sql, user.getUid(),user.getUsername(),user.getPassword(),
				user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),
				user.getSex(),user.getState(),user.getCode());
		return update;
	}

	//激活
	public void active(String activeCode) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update user set state=? where code=?";
		runner.update(sql, 1,activeCode);
	}

	//校验用户名是否存在
	public Long checkUsername(String username) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from user where username=?";
		Long query = (Long) runner.query(sql, new ScalarHandler(), username);
		return query;
	}

	public User userLogin(String username, String password) {
        QueryRunner qr=new TxQueryRunner();
        String sql="select * from user where username=? ANd password=? ";
        /*AND state=1*/
        User user=new User();
        try {
            user= qr.query(sql,new BeanHandler<User>(User.class),username,password);
        } catch (SQLException e) {
            new RuntimeException(e);
        }
        return user;
    }

	public User findUserByuid(String uid) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where uid=?";
		return runner.query(sql, new BeanHandler<User>(User.class), uid);
	}
}
