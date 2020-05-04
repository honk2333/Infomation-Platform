package com.itheima.dao;

import com.itheima.domain.Category;
import com.itheima.domain.Orders;
import com.itheima.domain.Product;
import com.itheima.utils.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminDao {
    public List<Category> findCategoryList() {
        QueryRunner qr=new TxQueryRunner();
        String sql="select * from category";
        List<Category> categoryList=new ArrayList<>();
        try {
            categoryList=qr.query(sql,new BeanListHandler<>(Category.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    public Category findCategoryByCid(String cid) {
        QueryRunner qr=new TxQueryRunner();
        String sql="select * from category where cid=?";
        Category category=new Category();
        try {
            category =qr.query(sql,new BeanHandler<>(Category.class),cid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    public int modify(Category category) {
        QueryRunner qr=new TxQueryRunner();
        String sql="update category set cname=? where cid=?";
        int row=0;
        try {
            row=qr.update(sql,category.getCname(),category.getCid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return row;
    }

    public void addCategory(Category category) {
        QueryRunner qr=new TxQueryRunner();
        String sql="insert into category Values(?,?)";
        try {
            qr.update(sql,category.getCid(),category.getCname());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCategoryByCid(String cid) {
        QueryRunner qr=new TxQueryRunner();
        String sql="delete from category where cid=?";
        try {
            qr.update(sql,cid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> findProductList() {
        QueryRunner qr=new TxQueryRunner();
        String sql="select * from product";
        List<Product> productList=new ArrayList<>();
        try {
            productList=qr.query(sql,new BeanListHandler<>(Product.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public int addProduct(Product product) {
        QueryRunner qr=new TxQueryRunner();
        String sql="insert into product VALUES(?,?,?,?,?,?,?,?,?,?)";
        Object[] params={product.getPid(),product.getPname(),product.getMarket_price(),
                        product.getShop_price(),product.getPimage(),product.getPdate(),
                        product.getIs_hot(),product.getPdesc(),product.getPflag(),
                        product.getCategory().getCid()};
        int row=0;
        try {
            row=qr.update(sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return row;
    }

    public Product findProductByPid(String pid) {
        QueryRunner qr=new TxQueryRunner();
        String sql="select * from product where pid=?";
        Product product=new Product();
        try {
            product=qr.query(sql,new BeanHandler<>(Product.class),pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public int modifyProduct(Product product) {
        QueryRunner qr=new TxQueryRunner();
        String sql="update product set pname=?,market_price=?,shop_price=?," +
                "pimage=?,pdate=?,is_hot=?,pdesc=?,pflag=?,cid=? where pid=?";
        Object[] params={product.getPname(),product.getMarket_price(),
                product.getShop_price(),product.getPimage(),product.getPdate(),
                product.getIs_hot(),product.getPdesc(),product.getPflag(),
                product.getCategory().getCid(),product.getPid()};
        int row=0;
        try {
            row=qr.update(sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return row;
    }

    public void deleteProductByPid(String pid) {
        QueryRunner qr=new TxQueryRunner();
        String sql="delete from product where pid=?";
        try {
            qr.update(sql,pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Orders> ordersList() {
        QueryRunner qr=new TxQueryRunner();
        String sql="select * from orders";
        List<Orders> ordersList=new ArrayList<>();
        try {
            ordersList=qr.query(sql,new BeanListHandler<>(Orders.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersList;
    }

    public List<Map<String, Object>> findOrderItemByOid(String oid) {
        QueryRunner qr=new TxQueryRunner();
        String sql="select p.pname,p.pimage,p.shop_price,oi.count,oi.subtotal" +
                " from product p,orderitem oi where oi.oid=? AND p.pid=oi.pid";
        List<Map<String, Object>>mapList=new ArrayList<>();
        try {
            mapList = qr.query(sql, new MapListHandler(), oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mapList;
    }
}














