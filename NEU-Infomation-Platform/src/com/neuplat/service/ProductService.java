package com.neuplat.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.neuplat.dao.ProductDao;
import com.neuplat.domain.Category;
import com.neuplat.domain.Order;
import com.neuplat.domain.OrderItem;
import com.neuplat.domain.PageBean;
import com.neuplat.domain.Product;
import com.neuplat.domain.User;
import com.neuplat.utils.DataSourceUtils;

public class ProductService {

	// 获得热门商品
	public List<Product> findHotProductList() {

		ProductDao dao = new ProductDao();
		List<Product> hotProductList = null;
		try {
			hotProductList = dao.findHotProductList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hotProductList;

	}

	// 获得最新商品
	public List<Product> findNewProductList() {
		ProductDao dao = new ProductDao();
		List<Product> newProductList = null;
		try {
			newProductList = dao.findNewProductList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newProductList;
	}

	public List<Category> findAllCategory() {
		ProductDao dao = new ProductDao();
		List<Category> categoryList = null;
		try {
			categoryList = dao.findAllCategory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoryList;
	}

	public PageBean findProductListByCid(String cid, int currentPage, int currentCount) {

		ProductDao dao = new ProductDao();

		// 封装一个PageBean 返回web层
		PageBean<Product> pageBean = new PageBean<Product>();

		// 1、封装当前页
		pageBean.setCurrentPage(currentPage);
		// 2、封装每页显示的条数
		pageBean.setCurrentCount(currentCount);
		// 3、封装总条数
		int totalCount = 0;
		try {
			totalCount = dao.getCount(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageBean.setTotalCount(totalCount);
		// 4、封装总页数
		int totalPage = (int) Math.ceil(1.0 * totalCount / currentCount);
		pageBean.setTotalPage(totalPage);

		// 5、当前页显示的数据
		// select * from product where cid=? limit ?,?
		// 当前页与起始索引index的关系
		int index = (currentPage - 1) * currentCount;
		List<Product> list = null;
		try {
			list = dao.findProductByPage(cid, index, currentCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageBean.setList(list);

		return pageBean;
	}

	public Product findProductByPid(String pid) {
		ProductDao dao = new ProductDao();
		Product product = null;
		try {
			product = dao.findProductByPid(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

	public void submitOrder(Order order) {
		ProductDao dao = new ProductDao();
		// 存入到数据库
		// 存入到订单表
//	        try {
//	            DataSourceUtils.startTransaction();
//		
//	            int row=dao.submitOrder(order);
//		
//	            List<OrderItem> orderItemList = order.getOrdersList();
//	            for(OrderItem orderItem:orderItemList){
//	                dao.submitOrderItem(orderItem);
//	            }
//		
//	        } catch (SQLException e) {
//	            try {
//	                DataSourceUtils.rollback();
//	            } catch (SQLException e1) {
//	                e1.printStackTrace();
//	            }
//	            e.printStackTrace();
//	        }finally {
//	            try {
//	                DataSourceUtils.commitAndRelease();
//	            } catch (SQLException e) {
//	                e.printStackTrace();
//	            }
//	        }
		int row = dao.submitOrder(order);

		List<OrderItem> orderItemList = order.getOrdersList();
		for (OrderItem orderItem : orderItemList) {
			dao.submitOrderItem(orderItem);
		}

	}

	public boolean confirmOrder(Order orders) {
		ProductDao dao = new ProductDao();
		int row = dao.confirmOrder(orders);
		return row > 0 ? true : false;
	}

	// 根据用户，查出该用户的所有订单
	public List<Order> getUserOrderList(User user) {
		ProductDao dao = new ProductDao();
		// 1.先查出用户的所有订单
		List<Order> ordersList = new ArrayList<>();
		ordersList = dao.getUserOrderList(user);

		// 查出每个订单的所有订单项

		if (ordersList != null) {
			// 取出每个订单信息
			for (Order order : ordersList) {// 一个订单
				List<Map<String, Object>> mapList = dao.getEachOrderItem(order, user.getUid());
				// 取出list中的每个map
				List<OrderItem> ordersItemList = new ArrayList<>();
				for (Map<String, Object> map : mapList) {// 一个订单项
					Product product = new Product();
					OrderItem orderItem = new OrderItem();

					orderItem.setCount((Integer) map.get("count"));
					orderItem.setSubtotal((Double) map.get("subtotal"));
					product.setPname((String) map.get("pname"));
					product.setPimage((String) map.get("pimage"));
					product.setShop_price((Double) map.get("shop_price"));
					product.setPid((String) map.get("pid"));
					// 把product放到orderItem中
					orderItem.setProduct(product);

					// 把orderItem放到ordersItemList中
					ordersItemList.add(orderItem);
				}
				// 把orderItemList放到order中
				order.setOrdersList(ordersItemList);
			}

		}
		return ordersList;
	}
}
