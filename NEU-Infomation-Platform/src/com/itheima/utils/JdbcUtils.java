package com.itheima.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcUtils {
    //配置文件的默认配置，要求你必须给出配置文件
    private static ComboPooledDataSource dataSource;

    static {
        try {
            dataSource = JdbcUtils.getComboPooledDataSource();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public static ComboPooledDataSource getComboPooledDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource1=new ComboPooledDataSource();
        dataSource1.setDriverClass("com.mysql.jdbc.Driver");
        dataSource1.setJdbcUrl("jdbc:mysql://localhost:3306/heimashop");
        dataSource1.setUser("root");
        dataSource1.setPassword("root");
        dataSource1.setAcquireIncrement(5);
        dataSource1.setMaxPoolSize(50);
        dataSource1.setMinPoolSize(10);
        dataSource1.setInitialPoolSize(20);
        return dataSource1;
    }

    //事务专用连接
    private static ThreadLocal<Connection> tl=new ThreadLocal<>();

    //使用连接池返回一个连接对象
    public static Connection getConnection() throws SQLException {
        Connection con=tl.get();
        //当con不等于null，说明已经调用或beginTransaction()，表示开启了事务！
        if(con!=null){
            return con;
        }
        return dataSource.getConnection();
    }

    //返回连接池对象
    public static DataSource getDataSource(){
        return dataSource;
    }

    public static void beginTransaction() throws SQLException {
        Connection con=tl.get();
        if(con!=null){//避免重复提交
            throw new SQLException("已经开启了事务");
        }
        //给con赋值
        con=getConnection();
        con.setAutoCommit(false);

        //当前的线程保存起来
        tl.set(con);
    }

    //提交事务
    public static void commitTransaction() throws SQLException {
        Connection con=tl.get();//获得当前的Connection
        if(con==null){
            throw new SQLException("还没有开启事务");
        }

        con.commit();
        con.close();//关闭，还给连接池

        //从事务中移除
        tl.remove();
    }

    public static void rollbackTransaction() throws SQLException {
        Connection con=tl.get();

        if(con==null){
            throw new SQLException("还没有开启事务,不能回滚");
        }

        con.rollback();//回滚
        con.close();//还给连接池

        tl.remove();//从事务中移除

    }

    public static void releaseConnection(Connection connection) throws SQLException {
        Connection con=tl.get();
        // 如果con == null，说明现在没有事务，那么connection一定不是事务专用的！
        if(con==null){
            connection.close();
        }

        // 如果con != null，说明有事务，那么需要判断参数连接是否与con相等，
        // 若不等，说明参数连接不是事务专用连接
        if(con!=connection){
            connection.close();
        }
    }

}
