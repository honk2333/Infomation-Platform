package com.neuplat.domain;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	//该购物车中存储的n个购物项Map的key是pid，用来找到对应商品的购物项，Map的value是一个购物项，一个购物项包含一个商品的对象和购买商品的数量以及商品金额小计
		private Map<String,CartItem> cartItems=new HashMap<String,CartItem>(); 
		//购物车中所有购物项商品价格总计
		private double total;
		public Map<String, CartItem> getCartItems() {
			return cartItems;
		}
		public void setCartItems(Map<String, CartItem> cartItems) {
			this.cartItems = cartItems;
		}
		public double getTotal() {
			return total;
		}
		public void setTotal(double total) {
			this.total = total;
		}

}
