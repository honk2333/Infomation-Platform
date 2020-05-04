package com.itheima.service;

import com.itheima.dao.CategoryDao;
import com.itheima.domain.Category;

import java.util.List;

public class CategoryService {
    CategoryDao dao=new CategoryDao();
    public List<Category> getCategory() {
        return dao.getCategory();
    }
}
