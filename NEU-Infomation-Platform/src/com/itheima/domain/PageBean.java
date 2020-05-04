package com.itheima.domain;

import java.util.List;

public class PageBean<T> {
    //当前页
    private int currentPage;
    //总页数
    private int totalPage;
    //总条数
    private int totalCount;
    //每页记录数
    private int aPageCount;
    //当前页的记录数
    private List<T> listPage;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        int i = totalCount / aPageCount;
        return totalCount % aPageCount==0?i:i+1;
    }
    
    public void setTotalPage(int totalPage) {
    	this.totalPage=totalPage;
    }

    public int getTotalCode() {
        return totalCount;
    }

    public void setTotalCount(int totalCode) {
        this.totalCount = totalCode;
    }

    public int getAPageCount() {
        return aPageCount;
    }

    public void setAPageCount(int aPageCount) {
        this.aPageCount = aPageCount;
    }

    public List<T> getListPage() {
        return listPage;
    }

    public void setListPage(List<T> listPage) {
        this.listPage = listPage;
    }
}