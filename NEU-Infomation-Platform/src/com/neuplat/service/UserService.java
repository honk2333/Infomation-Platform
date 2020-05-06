package com.neuplat.service;

import java.sql.SQLException;

import com.neuplat.dao.ProductDao;
import com.neuplat.dao.UserDao;
import com.neuplat.domain.PageBean;
import com.neuplat.domain.Product;
import com.neuplat.domain.User;

public class UserService {

	public boolean regist(User user) {
		
		UserDao dao = new UserDao();
		int row = 0;
		try {
			row = dao.regist(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return row>0?true:false;
	}

	//激活
	public void active(String activeCode) {
		UserDao dao = new UserDao();
		try {
			dao.active(activeCode);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	
	//校验用户名是否存在
		public boolean checkUsername(String username) {
			UserDao dao=new UserDao();
			Long isExist=0L;
			try {
				isExist=dao.checkUsername(username);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//返回值是查到的数据条数，为Long型
			return isExist>0?true:false;//如果isExist>0说明用户已存在返回true用户名不能使用，如果isExist=0说明用户不存在返回false
		}
		

	    public User userLogin(String username, String password) {
	    	UserDao dao=new UserDao();
	        return dao.userLogin(username,password);
	    }

		public User findUserByuid(String uid) {
			// TODO Auto-generated method stub
			UserDao dao = new UserDao();
			User user = null;
			try {
				user = dao.findUserByuid(uid);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return user;
		}

}
