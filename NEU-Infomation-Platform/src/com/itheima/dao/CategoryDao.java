package com.itheima.dao;

import com.itheima.domain.Category;
import com.itheima.utils.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    public List<Category> getCategory() {
        QueryRunner qr=new TxQueryRunner();
        String sql="select * from category";
        List<Category> list=new ArrayList<>();
        try {
            list=qr.query(sql,new BeanListHandler<Category>(Category.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
