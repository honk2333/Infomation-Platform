package com.itheima.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.itheima.dao.ProductDao;
import com.itheima.domain.Category;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Orders;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.domain.User;

public class ProductService {
	ProductDao dao=new ProductDao();
    public List<Product> getHotProductList() {
        return dao.getHotProductList();
    }

    public List<Product> getNewProductList(){
        return dao.getNewProductList();
    }

//    public int getTotalCode(String cid) {
//        return dao.getTotalCode(cid);
//    }

    public PageBean<Product> getProductListByCid(String cid, int aPageCount,int currentPage) {
        PageBean<Product> list=new PageBean<>();
        //当前页
        list.setCurrentPage(currentPage);
        //每业数量
        list.setAPageCount(aPageCount);
        //总记录数
        int row=dao.getTotalCode(cid);
        list.setTotalCount(row);

        //每页数据
        List<Product> productList=new ArrayList<>();
        productList=dao.getProductListByCid(cid,aPageCount,currentPage);
        list.setListPage(productList);

        return list;
    }

    public Product getProductInfo(String pid) {
        return dao.getProductInfo(pid);
    }

    public void submitOrder(Orders orders) {
        //存入到数据库
        //存入到订单表
//        try {
//            DataSourceUtils.startTransaction();
//
//            int row=dao.submitOrder(orders);
//
//            List<OrderItem> orderItemList = orders.getOrdersList();
//            for(OrderItem orderItem:orderItemList){
//                dao.submitOrderItem(orderItem);
//            }
//
//        } catch (SQLException e) {
//            try {
//                DataSourceUtils.rollback();
//            } catch (SQLException e1) {
//                e1.printStackTrace();
//            }
//            e.printStackTrace();
//        }finally {
//            try {
//                DataSourceUtils.commitAndRelease();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
            int row=dao.submitOrder(orders);

            List<OrderItem> orderItemList = orders.getOrdersList();
            for(OrderItem orderItem:orderItemList){
                dao.submitOrderItem(orderItem);
            }

    }

    public boolean confirmOrder(Orders orders) {
        int row=dao.confirmOrder(orders);
        return row>0?true:false;
    }

    ////根据用户，查出该用户的所有订单
    public List<Orders> getUserOrderList(User user) {
        //1.先查出用户的所有订单
        List<Orders> ordersList=new ArrayList<>();
        ordersList=dao.getUserOrderList(user);

        //查出每个订单的所有订单项

        if(ordersList!=null){
            //取出每个订单信息
            for(Orders orders:ordersList){//一个订单
            	List<Map<String, Object>> mapList = dao.getEachOrderItem(orders, user.getUid());
                //取出list中的每个map
                List<OrderItem>ordersItemList=new ArrayList<>();
                for(Map<String, Object> map:mapList){//一个订单项
                    Product product=new Product();
                    OrderItem orderItem=new OrderItem();

                    orderItem.setCount((Integer) map.get("count"));
                    orderItem.setSubtotal((Double) map.get("subtotal"));
                    product.setPname((String) map.get("pname"));
                    product.setPimage((String) map.get("pimage"));
                    product.setShop_price((Double) map.get("shop_price"));
                    product.setPid((String) map.get("pid"));
                    //把product放到orderItem中
                    orderItem.setProduct(product);

                    //把orderItem放到ordersItemList中
                    ordersItemList.add(orderItem);
                }
                //把ordersItemList放到order中
                orders.setOrdersList(ordersItemList);
            }

        }
        return ordersList;
    }
	//获得热门商品
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

	//获得最新商品
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

	public PageBean findProductListByCid(String cid,int currentPage,int currentCount) {
		
		ProductDao dao = new ProductDao();
		
		//封装一个PageBean 返回web层
		PageBean<Product> pageBean = new PageBean<Product>();
		
		//1、封装当前页
		pageBean.setCurrentPage(currentPage);
		//2、封装每页显示的条数
		pageBean.setAPageCount(currentCount);
		//3、封装总条数
		int totalCount = 0;
		try {
			totalCount = dao.getCount(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageBean.setTotalCount(totalCount);
		//4、封装总页数
		int totalPage = (int) Math.ceil(1.0*totalCount/currentCount);
		pageBean.setTotalPage(totalPage);
		
		//5、当前页显示的数据
		// select * from product where cid=? limit ?,?
		// 当前页与起始索引index的关系
		int index = (currentPage-1)*currentCount;
		List<Product> list = null;
		try {
			list = dao.findProductByPage(cid,index,currentCount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageBean.setListPage(list);
		
		
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

}
