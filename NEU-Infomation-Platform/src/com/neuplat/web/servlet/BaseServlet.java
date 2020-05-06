package com.neuplat.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@SuppressWarnings("all")
public class BaseServlet extends HttpServlet {
    //ͨ��������ʶ�Ӧ��method����
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		
		try {
			//1.��������method����
			String methodName = req.getParameter("method");
			//2. this����ǰ�����ʵĶ��󣬻�õ�ǰ�����ʶ�����ֽ������
			Class clazz = this.getClass();  //productServlet.class------userServlet.class
			//3.��õ�ǰ�ֽ�������е�ָ������
			Method method=clazz.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			//4.ִ����Ӧ���ܵķ���
			method.invoke(this,req,resp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
