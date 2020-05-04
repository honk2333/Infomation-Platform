package com.itheima.web;

import cn.itcast.commons.CommonUtils;
import com.google.gson.Gson;
import com.itheima.domain.*;
import com.itheima.service.CategoryService;
import com.itheima.service.ProductService;
import com.itheima.utils.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ProductServlet extends BaseServlet {

    //查找商品的详细信息
    public void findProductInfo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductService service=new ProductService();
        //得到商品id
        String pid = request.getParameter("pid");
        //查出该商品的详细信息
        Product product=service.getProductInfo(pid);
        //得到浏览记录的商品的pid
        String cookiePidValue=getCookie(request,pid);
        Cookie cookie1=new Cookie("pids",cookiePidValue);
        //把Cookie发回到客户端
        response.addCookie(cookie1);

        request.setAttribute("product",product);

        request.getRequestDispatcher("/product_info.jsp").forward(request,response);
    }

    //分类管理
    public void categoryListByCid(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PageBean<Product> list=new PageBean<>();
        ProductService service=new ProductService();
        //当前的分类cid
        String cid = request.getParameter("cid");
        //当前页
        int current=getCurrent(request);
        //每页显示的数量
        int aPageCount=12;

        list=service.getProductListByCid(cid,aPageCount,current);

        //获得浏览的历史记录，先获得cookie
        Cookie[] cookies = request.getCookies();
        //创建一个一个用于保存浏览的历史记录的list集合
        List<Product>productList=new ArrayList<>();
        for(Cookie cookie:cookies){
            //如果有之前的访问记录
            if("pids".equals(cookie.getName())){
                //获得值3-2-1-5，这样的值每个都是一个商品的id，可进行查找
                String value = cookie.getValue();
                String[] split = value.split("-");
                for(String pid: split){
                    productList.add(service.getProductInfo(pid));
                }
            }
        }
        request.setAttribute("productList",productList);

        request.setAttribute("cid",cid);

        request.setAttribute("pageInfo",list);

        request.getRequestDispatcher("/product_list.jsp").forward(request,response);

    }

    //分类列表
    public void categoryList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CategoryService service=new CategoryService();
        //拿到列表
        List<Category> list=service.getCategory();;
        //json转换工具
        Gson gson=new Gson();
        String categoryList = gson.toJson(list);
        response.getWriter().print(categoryList);
    }

    //主页信息显示，从数据查找最热和最新商品
    public void index(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProductService service=new ProductService();
        CategoryService categoryService=new CategoryService();
        //获得最新商品
        List<Product> hotProductList=new ArrayList<>();
        hotProductList=service.getHotProductList();

        //获得最热商品
        List<Product> newProductList=new ArrayList<>();
        newProductList=service.getNewProductList();

        //获得列表
        List<Category>categoryList=new ArrayList<>();
        categoryList=categoryService.getCategory();

        request.setAttribute("hotProductList",hotProductList);
        request.setAttribute("newProductList",newProductList);
        //把首页列表放到session中
        //request.getSession().setAttribute("categoryList",categoryList);

        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }

    //购物车的显示，在增加一个商品到购物车之后，执行，然后跳转到购物车
    public void addCartItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        ProductService service=new ProductService();

        //商品id
        String pid = request.getParameter("pid");
        //获得购买数量
        String buyNum=request.getParameter("buyNum");
        //得到商品对象
        Product product = service.getProductInfo(pid);

        //创建CartItem购物项
        CartItem cartItem=new CartItem();
        cartItem.setProduct(product);
        cartItem.setBuyNum(Integer.parseInt(buyNum));
        cartItem.setTotalPrice(Integer.parseInt(buyNum)*product.getShop_price());

        //获得放在session中的cart
        Cart cart = (Cart) session.getAttribute("cart");
        //如果为空，则创建
        if(cart==null){
            cart=new Cart();
        }
        //创建map
        Map<String, CartItem> itemMap = cart.getCartItemMap();

        //如果不包括这个pid
        if(!itemMap.containsKey(pid) ){
            System.out.println("===================2");
            //把该购物项放到map中
            itemMap.put(pid,cartItem);
            //放到cart中
            double oldPrice=cart.getTotalPrice();
            cart.setTotalPrice(oldPrice + cartItem.getTotalPrice());
            cart.setCartItemMap(itemMap);

        }else {
            System.out.println("===================3");
            //如果包括这个pid,修改数量和价格
            CartItem cartItem1 = itemMap.get(pid);
            //把原来的加上现在的
            cartItem1.setBuyNum(cartItem1.getBuyNum()+Integer.parseInt(buyNum));
            cartItem1.setTotalPrice(cartItem1.getTotalPrice()+cartItem.getTotalPrice());
            //放到map中
            itemMap.put(pid,cartItem1);
            //放到cart中
            cart.setCartItemMap(itemMap);
            //原来的加上刚买的，cartItem.getTotalPrice()为增加的量
            double oldPrice=cart.getTotalPrice();
            cart.setTotalPrice(oldPrice+cartItem.getTotalPrice());
        }

        //将更新后的cart放到session
        session.setAttribute("cart",cart);
        System.out.println(request.getContextPath()+"/cart.jsp");
        response.sendRedirect(request.getContextPath()+"/cart.jsp");
    }

    //从购物车中删除单个购物项
    public void deleteCartItem(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        HttpSession session = request.getSession();

        String pid = request.getParameter("pid");
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart==null){
            return;
        }

        //获得map
        Map<String, CartItem> itemMap = cart.getCartItemMap();
        if(itemMap.containsKey(pid)){
            CartItem cartItem = itemMap.get(pid);
            //总价减去移除的
            cart.setTotalPrice(cart.getTotalPrice()-cartItem.getTotalPrice());
            //移除选中的
            itemMap.remove(pid);

            cart.setCartItemMap(itemMap);
        }
        session.setAttribute("cart",cart);
        request.getRequestDispatcher("/cart.jsp").forward(request,response);
    }

    //清空购物车
    public void clearCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if(cart!=null){
            session.removeAttribute("cart");
        }
        response.sendRedirect(request.getContextPath()+"/cart.jsp");

    }

    //提交购物车的订单
    public void submitOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        ProductService service=new ProductService();
        HttpSession session = request.getSession();
        //获得用户名
        User user = (User) session.getAttribute("user");
        if(user==null){
            response.sendRedirect(request.getContextPath()+"/login.jsp");
            return;
        }
        Cart cart = (Cart) session.getAttribute("cart");
        //创建订单对象
        Orders orders=new Orders();

        orders.setOid(CommonUtils.uuid());
        orders.setState(0);//表示还没提交
        orders.setOrdertime(new Date());

        orders.setTotal(cart.getTotalPrice());//总价
        orders.setUser(user);
        orders.setName(null);
        orders.setTelephone(null);
        orders.setAddress(null);

        //获得订单项
        Map<String, CartItem> cartItemMap = cart.getCartItemMap();
        //创建集合
        List<OrderItem> orderItemList=new ArrayList<>();
        for(Map.Entry<String,CartItem> entry:cartItemMap.entrySet()){
            OrderItem orderItem=new OrderItem();
            CartItem cartItem = entry.getValue();

            orderItem.setCount(cartItem.getBuyNum());
            orderItem.setItemid(CommonUtils.uuid());
            orderItem.setOrders(orders);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setSubtotal(cartItem.getTotalPrice());

            //放到集合中
            orderItemList.add(orderItem);
        }
        orders.setOrdersList(orderItemList);
        //把封装好的order传到service
        service.submitOrder(orders);

        //将提单放到request域中
        request.setAttribute("orders",orders);

        System.out.println("=========提交==========");

        request.getRequestDispatcher("/order_info.jsp").forward(request,response);
    }


    //确认支付
    public void confirmOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        //1.更新orders表，加上收货地址，电话和收件人
        ProductService service=new ProductService();
        Map<String, String[]> parameterMap = request.getParameterMap();
        Orders orders=new Orders();
        try {
            BeanUtils.populate(orders,parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean isConfirmOrder=service.confirmOrder(orders);

        //2.进行支付操作
        // 获得 支付必须基本数据
        String orderid = orders.getOid();
//        String money = orders.getTotal()+"";
        String money ="0.01";
                // 银行
        String pd_FrpId = request.getParameter("pd_FrpId");

        // 发给支付公司需要哪些数据
        String p0_Cmd = "Buy";
        String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
        String p2_Order = orderid;
        String p3_Amt = money;
        String p4_Cur = "CNY";
        String p5_Pid = "";
        String p6_Pcat = "";
        String p7_Pdesc = "";
        // 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
        // 第三方支付可以访问网址
        String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("callback");
        String p9_SAF = "";
        String pa_MP = "";
        String pr_NeedResponse = "1";
        // 加密hmac 需要密钥
        String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
                "keyValue");
        String hmac = com.itheima.utils.PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
                p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
                pd_FrpId, pr_NeedResponse, keyValue);


        String url = "https://www.yeepay.com/app-merchant-proxy/node?pd_FrpId="+pd_FrpId+
                "&p0_Cmd="+p0_Cmd+
                "&p1_MerId="+p1_MerId+
                "&p2_Order="+p2_Order+
                "&p3_Amt="+p3_Amt+
                "&p4_Cur="+p4_Cur+
                "&p5_Pid="+p5_Pid+
                "&p6_Pcat="+p6_Pcat+
                "&p7_Pdesc="+p7_Pdesc+
                "&p8_Url="+p8_Url+
                "&p9_SAF="+p9_SAF+
                "&pa_MP="+pa_MP+
                "&pr_NeedResponse="+pr_NeedResponse+
                "&hmac="+hmac;

        //重定向到第三方支付平台
        response.sendRedirect(url);
    }

    //查询订单信息，已购买过的产品
    public void findOrderList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        HttpSession session = request.getSession();
        //用session中获得用户
        User user= (User) session.getAttribute("user");
        //如果没登陆
        if(user==null){
            response.sendRedirect("login.jsp");
            return;
        }

        //根据用户，查出该用户的所有订单
        ProductService service=new ProductService();
        List<Orders>ordersList=service.getUserOrderList(user);

        //根据用户和订单查出订单项
        request.setAttribute("ordersList",ordersList);
        request.getRequestDispatcher("/order_list.jsp").forward(request,response);

    }



    public int getCurrent(HttpServletRequest request){
        String currentStr=request.getParameter("currentPage");
        if(currentStr == null ){
            return 1;
        }
        return Integer.parseInt(currentStr);
    }

    public String getCookie(HttpServletRequest request,String pids){
        /*
         * 实现的功能，数字是商品的id
         * 3-1-2-4
         * 如果本次访问时2
         * 2-3-1-4
         * 如果本次访问时4
         * 4-2-3-1
         * */

        //得到cookie
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies){
            if("pids".equals(cookie.getName())){
                //得到原来Cookie中的值
                String pidVaule=cookie.getValue();
                //进行分片
                String[] split=pidVaule.split("-");

                List<String> asList=Arrays.asList(split);
                LinkedList<String> list=new LinkedList<>(asList);
                //查看是否包括当前返回的商品
                if(list.contains(pids)){
                    //如果存在，则删除
                    list.remove(pids);
                }
                //再添加到第一个，说明是刚访问过
                list.addFirst(pids);
                //拼接pids的值
                StringBuffer sb=new StringBuffer();
                for(int i=0;i<list.size()&&i<=7;i++){
                    sb.append(list.get(i));
                    if(i+1!=list.size()){
                        sb.append("-");
                    }
                }
                pids=sb.toString();
            }
        }
        return pids;
    }

}
