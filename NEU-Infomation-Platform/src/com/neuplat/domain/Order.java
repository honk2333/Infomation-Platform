package com.neuplat.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
	//该订单中有多少订单项
	private List<OrderItem> orderItems=new ArrayList<OrderItem>();
	private String oid;//该订单的订单号
	private Date ordertime;//下单时间
	private double total;//该订单的总金额
	private int state;//订单的支付状态
	private String address;//收货地址
	private String name;//收货人
	private String telephone;//收货人的联系方式
	private User user;//该订单属于哪个用户
	
	
	

	public List<OrderItem> getOrdersList() {
        return orderItems;
    }

    public void setOrdersList(List<OrderItem> ordersList) {
        this.orderItems = ordersList;
    }
	
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
