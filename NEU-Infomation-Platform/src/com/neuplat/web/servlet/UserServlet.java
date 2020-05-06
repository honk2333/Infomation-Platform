package com.neuplat.web.servlet;

import cn.itcast.commons.CommonUtils;

import com.neuplat.domain.PageBean;
import com.neuplat.domain.Product;
import com.neuplat.domain.User;
import com.neuplat.service.ProductService;
import com.neuplat.service.UserService;
import com.neuplat.utils.MailUtils;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserServlet extends BaseServlet {


//    //注册
//    public void register(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        Map<String, String[]> parameterMap = request.getParameterMap();
//        User user = CommonUtils.toBean(parameterMap, User.class);
//        //uid
//        user.setUid(CommonUtils.uuid());
//        //telephone手机
//        user.setTelephone(null);
//        //code激活码
//        user.setCode(CommonUtils.uuid());
//        //state状态，刚注册未激活state就为0
//        user.setState(0);
//
//        UserService service = new UserService();
//        boolean isRegister = service.register(user);
//
//        String message = "欢迎您注册，请点击进行激活<a href='" +
//                "http://localhost:8080/heimaShop/user?method=active&active=" +
//                user.getCode() + "'>点击激活</a>";
//        if (isRegister) {
//            //发送邮箱
//            try {
//                MailUtils.sendMail(user.getEmail(), message);
//            } catch (MessagingException e) {
//                throw new RuntimeException(e);
//            }
//
//            //成功跳转
//            response.sendRedirect(request.getContextPath() + "/registerSuccess.jsp");
//
//        } else {
//            response.sendRedirect(request.getContextPath() + "/registerFail.jsp");
//        }
//    }
//
//    //确认名字是否已存在
//    public void checkUsername(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String username = request.getParameter("username");
//        UserService service=new UserService();
//        boolean isExist=service.checkUsername(username);
//        String json="{\"isExist\":"+isExist+"}";
//
//        response.getWriter().print(json);
//    }
//
//    //激活
//    public void active(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String active = request.getParameter("active");
//        System.out.println(active);
//        UserService service=new UserService();
//        boolean isActive=service.active(active);
//
//        if(isActive){
//            response.sendRedirect(request.getContextPath()+"/login.jsp");
//        }else{
//            request.setAttribute("msg","激活失败");
//            response.sendRedirect(request.getContextPath()+"/registerFail.jsp");
//        }
//    }

    //登陆
    public void userLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        UserService service=new UserService();
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String autoLogin = request.getParameter("autoLogin");
        if(autoLogin!=null){
            Cookie userCookie=new Cookie("username",username);

            Cookie passCookie=new Cookie("password",password);

            userCookie.setMaxAge(60*60*24*10);
            passCookie.setMaxAge(60*60*24*10);

            response.addCookie(userCookie);
            response.addCookie(passCookie);
        }

        User user=service.userLogin(username,password);
        if(user!=null&&user.getState()==1){
            request.getSession().setAttribute("user",user);
            response.sendRedirect(request.getContextPath()+"/product?method=index");
        }else if(user!=null&&user.getState()!=1) {
        	request.setAttribute("msg", "请先去邮箱激活");
        	response.sendRedirect(request.getContextPath()+"/login.jsp");
        }
        else {
        	request.setAttribute("msg", "用户不存在或密码不匹配");
            response.sendRedirect(request.getContextPath()+"/login.jsp");
        }

    }

    //退出
    public void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user!=null){
            session.removeAttribute("user");

            //把cookie中保存到的用户和密码删除
            Cookie userCookie=new Cookie("username","");
            Cookie passCookie=new Cookie("password","");

            userCookie.setMaxAge(0);
            passCookie.setMaxAge(0);
            response.addCookie(userCookie);
            response.addCookie(passCookie);
        }
        response.sendRedirect(request.getContextPath()+"/product?method=index");
    }
    
	// 根据用户的id获得用户的信息
	public void contactUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获得uid
		String uid = request.getParameter("uid");

		UserService service = new UserService();
		User user = service.findUserByuid(uid);

		request.setAttribute("user", user);
		
		request.getRequestDispatcher("/contact.jsp").forward(request, response);
	}
}











