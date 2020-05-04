package com.itheima.domain;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<String,CartItem> cartItemMap=new HashMap<>();
    private double TotalPrice;

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }

    public Map<String, CartItem> getCartItemMap() {
        return cartItemMap;
    }

    public void setCartItemMap(Map<String, CartItem> cartItemMap) {
        this.cartItemMap = cartItemMap;
    }
}
