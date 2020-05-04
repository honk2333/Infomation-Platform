package com.itheima.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.itheima.domain.*;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.domain.Category;
import com.itheima.domain.Product;
import com.itheima.utils.DataSourceUtils;

import cn.itcast.jdbc.TxQueryRunner;

public class ProductDao {
	public List<Product> getHotProductList(){
        QueryRunner qr=new TxQueryRunner();
        String sql="select * from product where is_hot=? limit ?,?";
        List<Product>list= new ArrayList<>();
        try {
            list = qr.query(sql,new BeanListHandler<Product>(Product.class),1,0,9);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Product> getNewProductList(){
        QueryRunner qr=new TxQueryRunner();
        String sql="select * from product order by pdate DESC limit ?,?";
        List<Product>list= new ArrayList<>();
        try {
            list = qr.query(sql,new BeanListHandler<Product>(Product.class),0,9);
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }
        return list;
    }

    public int getTotalCode(String cid) {
        QueryRunner qr=new TxQueryRunner();
        String sql="select count(*) from product where cid=?";
        Number row=0;
        try {
            row=(Number) qr.query(sql,new ScalarHandler(),cid);
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }
        return row.intValue();
    }

    public List<Product> getProductListByCid(String cid, int aPageCount,int currentPage) {
        QueryRunner qr=new TxQueryRunner();
        String sql="select * from product where cid=? limit ?,?";
        try {
            return qr.query(sql,new BeanListHandler<Product>(Product.class),
                    cid,(currentPage-1)*aPageCount,aPageCount);
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }
    }

    public Product getProductInfo(String pid) {
        QueryRunner qr=new TxQueryRunner();
        String sql="select * from product where pid=?";
        try {
            return qr.query(sql,new BeanHandler<Product>(Product.class),pid);
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }
    }

    public int submitOrder(Orders orders) {
        QueryRunner qr=new TxQueryRunner();
        String sql="insert into orders values(?,?,?,?,?,?,?,?)";
        Object[] params={orders.getOid(),orders.getOrdertime(),orders.getTotal(),
                        orders.getState(),orders.getAddress(),orders.getName(),
                        orders.getTelephone(),orders.getUser().getUid()};
        try {
            return qr.update(sql,params);
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }

    }

    public void submitOrderItem(OrderItem orderItem) {
        QueryRunner qr=new TxQueryRunner();
        String sql="insert into orderitem values(?,?,?,?,?)";
        Object[] params={orderItem.getItemid(),orderItem.getCount(),orderItem.getSubtotal(),
                         orderItem.getProduct().getPid(),orderItem.getOrders().getOid()};
        try {
             qr.update(sql,params);
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }
    }

    public int confirmOrder(Orders orders) {
        QueryRunner qr=new TxQueryRunner();
        String sql="update orders set address=?,name=?,telephone=? where oid=?";
        Object[] params={orders.getAddress(),orders.getName(),orders.getTelephone(),
                         orders.getOid()};
        try {
            return qr.update(sql,params);
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }
    }

    ////根据用户，查出该用户的所有订单
    public List<Orders> getUserOrderList(User user) {
        QueryRunner qr=new TxQueryRunner();
        String sql="select * from orders where uid=?";
        List<Orders> ordersList=new ArrayList<>();
        try {
            ordersList=qr.query(sql,new BeanListHandler<Orders>(Orders.class),user.getUid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersList;
    }

    public List<Map<String, Object>>getEachOrderItem(Orders orders,String uid) {
        QueryRunner qr=new TxQueryRunner();

        String sql="select o.count,o.subtotal,p.pid,p.pname,p.pimage,p.shop_price from orderitem as o,product as p where o.oid=? ANd p.pid=o.pid";
        List<Map<String, Object>> mapList=new ArrayList<>();
        try {
            mapList = qr.query(sql, new MapListHandler(), orders.getOid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mapList;
    }
	//获得热门商品
	public List<Product> findHotProductList() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where is_hot=? limit ?,?";
		return runner.query(sql, new BeanListHandler<Product>(Product.class), 1,0,9);
	}
	//获得最新商品
	public List<Product> findNewProductList() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product order by pdate desc limit ?,?";
		return runner.query(sql, new BeanListHandler<Product>(Product.class),0,9);
	}

	public List<Category> findAllCategory() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category";
		return runner.query(sql, new BeanListHandler<Category>(Category.class));
	}

	public int getCount(String cid) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select count(*) from product where cid=?";//返回值为Long型
		Long query=(Long) runner.query(sql, new ScalarHandler(),cid);//返回值为Object型，强转为Long型		
		return query.intValue();//返回值为Long型强转为int型
	}

	public List<Product> findProductByPage(String cid, int index, int currentCount) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where cid=? limit ?,?";
		List<Product> list = runner.query(sql, new BeanListHandler<Product>(Product.class), cid,index,currentCount);
		return list;
	}

	public Product findProductByPid(String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid=?";
		return runner.query(sql, new BeanHandler<Product>(Product.class), pid);
	}

}
