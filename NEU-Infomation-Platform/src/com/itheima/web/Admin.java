package com.itheima.web;

import cn.itcast.commons.CommonUtils;
import com.google.gson.Gson;
import com.itheima.domain.Category;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;
import com.itheima.domain.Product;
import com.itheima.service.AdminService;
import com.itheima.utils.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;
//import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Admin extends BaseServlet {
    AdminService service=new AdminService();

    //查找分类列表
    public void findCategoryList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        List<Category> categoryList= service.findCategoryList();
        request.setAttribute("categoryList",categoryList);
        request.getRequestDispatcher("/admin/category/list.jsp").forward(request,response);
    }

    //查找信息
    public void findCategoryByCid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String cid = request.getParameter("cid");
        Category category=service.findCategoryByCid(cid);
        request.setAttribute("category",category);

        request.getRequestDispatcher("/admin/category/edit.jsp").forward(request,response);
    }

    //修改信息
    public void modify(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        Map<String, String[]> parameterMap = request.getParameterMap();
        Category category=new Category();
        try {
            BeanUtils.populate(category,parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(category.getCid()+"="+category.getCname());
        boolean isModify=service.modify(category);
//        String msg="修改失败";
//        if(isModify){
//            msg="修改成功";
//        }
        request.getRequestDispatcher("/admin?method=findCategoryList").forward(request,response);
    }


    public void addCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String cname = request.getParameter("cname");
        String cid=CommonUtils.uuid();

        Category category=new Category();
        category.setCid(cid);
        category.setCname(cname);

        service.addCategory(category);
        request.getRequestDispatcher("/admin?method=findCategoryList").forward(request,response);
        //response.sendRedirect(request.getContextPath()+"/admin/category/list.jsp");
    }

    public void deleteCategoryByCid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String cid = request.getParameter("cid");

        service.deleteCategoryByCid(cid);
        request.getRequestDispatcher("/admin?method=findCategoryList").forward(request,response);
    }

    //查找商品列表，并显示
    public void findProductList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        List<Product> productList=service.findProductList();
        request.setAttribute("productList",productList);
        request.getRequestDispatcher("/admin/product/list.jsp").forward(request,response);
    }

    public void findAllCategoryByAjax(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        List<Category> categoryList = service.findCategoryList();
        Gson gson=new Gson();
        String toJson = gson.toJson(categoryList);
        response.getWriter().print(toJson);
    }

    public void findProductByPid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String pid = request.getParameter("pid");

        Product product=service.findProductByPid(pid);

        request.setAttribute("product",product);

        request.getRequestDispatcher("/admin/product/edit.jsp").forward(request,response);
    }

    //根据pid删除商品
    public void deleteProductByPid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String pid = request.getParameter("pid");
        service.deleteProductByPid(pid);
        response.sendRedirect(request.getContextPath()+"/admin?method=findProductList");
    }

    //查找订单列表，并显示
    public void findOrderList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        List<Orders> ordersList=new ArrayList<>();
        ordersList=service.findOrderList();

        request.setAttribute("ordersList",ordersList);
        request.getRequestDispatcher("/admin/order/list.jsp").forward(request,response);
    }

    //查找一个订单的详细信息，可能有多个订单项
    public void findOrderItemByOid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String oid = request.getParameter("oid");
        //只需查出OrderItem的集合，需要两张表进行查
        List<OrderItem> orderItems = new ArrayList<>();

        orderItems = service.findOrderItemByOid(oid);

        Gson gson=new Gson();
        String json = gson.toJson(orderItems);

        response.getWriter().print(json);
    }
}
















