package com.itheima.Interceptor;

import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.web.UserServlet;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AutoLoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }
    @Override
    public void destroy() { }
    @Override

    //自动登陆
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        //先判断用户是否已经登陆
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user==null){
            Cookie[] cookies = request.getCookies();
            String userValue="";
            String passValue="";
            if(cookies!=null){
                for(Cookie cookie:cookies){
                    if("username".equals(cookie.getName())){
                        userValue = cookie.getValue();
                    }
                    if("password".equals(cookie.getName())){
                        passValue = cookie.getValue();
                    }
                }

                if(userValue!="" && passValue!=""){
                    UserService service=new UserService();
                    user=service.userLogin(userValue,passValue);
                    if(user!=null) {
                        session.setAttribute("user", user);
                        response.sendRedirect(request.getContextPath() + "/product?method=index");
                    }
                    return;
                }
            }

        }
        filterChain.doFilter(servletRequest, servletResponse);
    }


}
