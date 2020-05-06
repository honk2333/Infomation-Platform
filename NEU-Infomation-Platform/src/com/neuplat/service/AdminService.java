package com.neuplat.service;

import com.neuplat.dao.AdminDao;
import com.neuplat.domain.Category;
import com.neuplat.domain.Order;
import com.neuplat.domain.OrderItem;
import com.neuplat.domain.Product;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminService {
    AdminDao dao=new AdminDao();
    //查找分类列表
    public List<Category> findCategoryList() {
        return dao.findCategoryList();
    }

    public Category findCategoryByCid(String cid) {
        return dao.findCategoryByCid(cid);
    }

    public boolean modify(Category category) {
        int row=dao.modify(category);
        return row>0?true:false;
    }

    public void addCategory(Category category) {
        dao.addCategory(category);
    }

    public void deleteCategoryByCid(String cid) {
        dao.deleteCategoryByCid(cid);
    }

    public List<Product> findProductList() {
        return dao.findProductList();
    }

    public boolean addProduct(Product product) {
        int row=dao.addProduct(product);
        return row>0?true:false;
    }

    public Product findProductByPid(String pid) {
        return dao.findProductByPid(pid);
    }

    public boolean modifyProduct(Product product) {
        int row=dao.modifyProduct(product);
        return row>0?true:false;
    }

    public void deleteProductByPid(String pid) {
        dao.deleteProductByPid(pid);
    }

    public List<Order> findOrderList() {
        return dao.ordersList();
    }

    public List<OrderItem> findOrderItemByOid(String oid) {
        List<Map<String, Object>> mapList = dao.findOrderItemByOid(oid);
        List<OrderItem>orderItemList=new ArrayList<>();
        for(Map<String,Object> map:mapList) {
            OrderItem orderItem = new OrderItem();
            Product product = new Product();
            try {
                BeanUtils.populate(product,map);
                BeanUtils.populate(orderItem,map);
            } catch (Exception e) {
                e.printStackTrace();
            }
            orderItem.setProduct(product);
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }
}
