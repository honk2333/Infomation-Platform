package com.neuplat.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.neuplat.domain.Cart;
import com.neuplat.domain.CartItem;
import com.neuplat.domain.Category;
import com.neuplat.domain.Order;
import com.neuplat.domain.OrderItem;
import com.neuplat.domain.PageBean;
import com.neuplat.domain.Product;
import com.neuplat.domain.User;
import com.neuplat.service.ProductService;
import com.neuplat.utils.CommonsUtils;
import com.neuplat.utils.JedisPoolUtils;

import redis.clients.jedis.Jedis;

public class ProductServlet extends BaseServlet {

//	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		
//		//���������ĸ�������method
//		String methodName=request.getParameter("method");
//		
//		
//		
//		
//		
//		if("categoryList".equals(methodName)){
//			categoryList(request, response);
//		}else if("index".equals(methodName)){
//			index(request, response);
//		}else if("productInfo".equals(methodName)){
//			productInfo(request, response);
//		}else if("productList".equals(methodName)){
//			productList(request, response);
//		}
//	}
//
//	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doGet(request, response);
//	}

	// 模块中的功能按照方法区分

	// 显示商品类别的方法
	public void categoryList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService service = new ProductService();

		// 先从缓存中查询categoryList 如果有直接使用 没有在从数据库中查询 存到缓存中
		// 1、获得jedis对象 连接redis数据库
		Jedis jedis = JedisPoolUtils.getJedis();
		String categoryListJson = jedis.get("categoryListJson");
		// 2、判断categoryListJson是否为空
		if (categoryListJson == null) {
			System.out.println("缓存没有数据 查询数据库");
			// 准备分类数据
			List<Category> categoryList = service.findAllCategory();
			Gson gson = new Gson();
			categoryListJson = gson.toJson(categoryList);
			jedis.set("categoryListJson", categoryListJson);
		}

		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(categoryListJson);
	}

	// 显示首页的功能
	public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductService service = new ProductService();
		// 准备热门商品---List<Product>
		List<Product> hotProductList = service.findHotProductList();

		// 准备最新商品---List<Product>
		List<Product> newProductList = service.findNewProductList();

		// 准备分类数据
		// List<Category> categoryList = service.findAllCategory();

		// request.setAttribute("categoryList", categoryList);
		request.setAttribute("hotProductList", hotProductList);
		request.setAttribute("newProductList", newProductList);

		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	// 显示商品详细信息的功能
	public void productInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获得当前页
		String currentPage = request.getParameter("currentPage");
		// 获得商品类别
		String cid = request.getParameter("cid");

		// 获得要查询的商品的pid
		String pid = request.getParameter("pid");

		ProductService service = new ProductService();
		Product product = service.findProductByPid(pid);

		request.setAttribute("product", product);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("cid", cid);

		// 先获得客户端携带cookie---获得名字是pids的cookie
		String pids = pid;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("pids".equals(cookie.getName())) {// 找到名字是pids的cookie
					pids = cookie.getValue();// 获得名为pids的cookie存储的pid串
					// 若获得的pid串为:1-3-2 ，且本次访问商品pid是8----->则重新拼接的pid串为:8-1-3-2
					// 若获得的pid串为:1-3-2 ，且本次访问商品pid是3----->则重新拼接的pid串为:3-1-2
					// 若获得的pid串为:1-3-2 ，且本次访问商品pid是2----->则重新拼接的pid串为:2-1-3
					// 1.将获得的名为pids的cookie存储的pid串拆成一个数组
					String[] split = pids.split("-");// {3,1,2}
					List<String> asList = Arrays.asList(split);// 把split数组转成List集合以便于操作，[3,1,2]
					LinkedList<String> list = new LinkedList<String>(asList);// 转成LinkedList集合，[3,1,2]
					// 2.判断集合中是否存在当前pid
					if (list.contains(pid)) {
						// 包含当前查看商品的pid
						list.remove(pid);// 先把包含的当前查看商品的pid删掉
						list.addFirst(pid);// 再将其放在最前面，使得当前访问访商品显示在浏览记录的最前面。
					} else {
						// 不包含当前查看商品的pid 直接将该pid放到头上
						list.addFirst(pid);
					}
					// 3.重新拼接pid串
					// 将[3,1,2]转成3-1-2字符串
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < list.size() && i < 7; i++) {// 限制显示的浏览历史个数为7个
						sb.append(list.get(i));
						sb.append("-");// 3-1-2-
					}
					// 去掉3-1-2-后面多的-
					pids = sb.substring(0, sb.length() - 1);// 长度减一即可去掉最后一个字符
				}
			}
		}

		// 4.将pid串重新写回cookie
		Cookie cookie_pids = new Cookie("pids", pids);
		response.addCookie(cookie_pids);

		request.getRequestDispatcher("/product_info.jsp").forward(request, response);

	}

	// 根据商品的类别获得商品的列表
	public void productList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获得cid
		String cid = request.getParameter("cid");

		String currentPageStr = request.getParameter("currentPage");
		if (currentPageStr == null)
			currentPageStr = "1";
		int currentPage = Integer.parseInt(currentPageStr);
		int currentCount = 12;

		ProductService service = new ProductService();
		PageBean pageBean = service.findProductListByCid(cid, currentPage, currentCount);

		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);

		// 定义一个记录历史商品信息的集合
		List<Product> historyProductList = new ArrayList<Product>();

		// 在转发之前获得客户端携带名字叫pids的cookie
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("pids".equals(cookie.getName())) {
					String pids = cookie.getValue();// 3-2-1
					String[] split = pids.split("-");
					for (String pid : split) {
						Product pro = service.findProductByPid(pid);
						historyProductList.add(pro);
					}
				}
			}
		}

		// 将历史记录的集合放到域中
		request.setAttribute("historyProductList", historyProductList);
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}

	// 将商品添加到购物车
	public void addProductToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService service = new ProductService();
		HttpSession session = request.getSession();

		// 获得要放到购物车的商品的pid
		String pid = request.getParameter("pid");
		// 获得该商品的购买数量
		int buyNum = Integer.parseInt(request.getParameter("buyNum"));

		// 获得product对象
		Product product = service.findProductByPid(pid);
		// 计算小计
		double subtotal = product.getMarket_price() * buyNum;
		// 封装CartItem
		CartItem item = new CartItem();
		item.setProduct(product);
		item.setBuyNum(buyNum);
		item.setSubtotal(subtotal);

		// 获得购物车-----先判断是否在session中已经存在购物车
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();// 如果session中没有cart就创建一个cart
		}
		// 将购物项放到购物车中----key是pid
		// 先判断购物车中是否已包含此购物项了 即判断key是否已经存在,,key是当前商品的pid
		// 如果购物车中已经存在该商品----将现在买的数量与原有的数量进行相加操作
		Map<String, CartItem> cartItems = cart.getCartItems();
		double newSubtotal = 0.0;
		if (cartItems.containsKey(pid)) {
			// 取出原有的商品的购买数量
			CartItem cartItem = cartItems.get(pid);
			int oldbuyNum = cartItem.getBuyNum();
			oldbuyNum += buyNum;
			cartItem.setBuyNum(oldbuyNum);
			cart.setCartItems(cartItems);
			// 修改小计
			double oldSubtotal = cartItem.getSubtotal();
			// 新买的商品的小计
			newSubtotal = buyNum * product.getMarket_price();
			cartItem.setSubtotal(oldSubtotal + newSubtotal);
		} else {
			// 如果购物车中不存在该商品
			cart.getCartItems().put(pid, item);
			newSubtotal = buyNum * product.getMarket_price();
		}
		// 计算总计
		double total = cart.getTotal() + newSubtotal;
		cart.setTotal(total);
		// 将购物车再次放回到session中
		session.setAttribute("cart", cart);

		// 直接跳转到购物车页
		// request.getRequestDispatcher("/cart.jsp").forward(request, response);
		// 这里用转发，当刷新购物车页面时相当于又添加了一遍商品，不合适，应该用重定向
		response.sendRedirect(request.getContextPath() + "/star.jsp");
	}
	
	public void addProductToMine(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService service = new ProductService();
		HttpSession session = request.getSession();

		// 获得要放到购物车的商品的pid
		String pid = request.getParameter("pid");
		// 获得该商品的购买数量
		int buyNum = 1;

		// 获得product对象
		Product product = service.findProductByPid(pid);
		// 计算小计
		double subtotal = product.getMarket_price() * buyNum;
		// 封装CartItem
		CartItem item = new CartItem();
		item.setProduct(product);
		item.setBuyNum(buyNum);
		item.setSubtotal(subtotal);

		// 获得购物车-----先判断是否在session中已经存在购物车
		Cart cart = (Cart) session.getAttribute("mine");
		if (cart == null) {
			cart = new Cart();// 如果session中没有cart就创建一个cart
		}
		// 将购物项放到购物车中----key是pid
		// 先判断购物车中是否已包含此购物项了 即判断key是否已经存在,,key是当前商品的pid
		// 如果购物车中已经存在该商品----将现在买的数量与原有的数量进行相加操作
		Map<String, CartItem> cartItems = cart.getCartItems();
		double newSubtotal = 0.0;
		if (cartItems.containsKey(pid)) {
			// 取出原有的商品的购买数量
			CartItem cartItem = cartItems.get(pid);
			int oldbuyNum = cartItem.getBuyNum();
			oldbuyNum += buyNum;
			cartItem.setBuyNum(oldbuyNum);
			cart.setCartItems(cartItems);
			// 修改小计
			double oldSubtotal = cartItem.getSubtotal();
			// 新买的商品的小计
			newSubtotal = buyNum * product.getMarket_price();
			cartItem.setSubtotal(oldSubtotal + newSubtotal);
		} else {
			// 如果购物车中不存在该商品
			cart.getCartItems().put(pid, item);
			newSubtotal = buyNum * product.getMarket_price();
		}
		// 计算总计
		double total = cart.getTotal() + newSubtotal;
		cart.setTotal(total);
		// 将购物车再次放回到session中
		session.setAttribute("mine", cart);

		// 直接跳转到购物车页
		// request.getRequestDispatcher("/cart.jsp").forward(request, response);
		// 这里用转发，当刷新购物车页面时相当于又添加了一遍商品，不合适，应该用重定向
		response.sendRedirect(request.getContextPath() + "/cart.jsp");
	}
	// 删除单一商品
	public void delProFromCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获得要删除的item的pid
		String pid = request.getParameter("pid");
		// 删除session中的购物车中的购物项中的item
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart != null) {
			Map<String, CartItem> cartItems = cart.getCartItems();
			// 需要修改总价
			cart.setTotal(cart.getTotal() - cartItems.get(pid).getSubtotal());
			// 删除该项
			cartItems.remove(pid);
			cart.setCartItems(cartItems);// 放不放都行
		}
		session.setAttribute("cart", cart);
		// 跳转回购物车页
		response.sendRedirect(request.getContextPath() + "/star.jsp");
	}

	// 清空购物车
	public void clearCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("cart");// 直接从session移除购物车对象
		// 跳转回购物车页
		response.sendRedirect(request.getContextPath() + "/star.jsp");
	}

	// 确认支付
	public void confirmOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.更新orders表，加上收货地址，电话和收件人
		ProductService service = new ProductService();
		Map<String, String[]> parameterMap = request.getParameterMap();
		Order orders = new Order();
		try {
			BeanUtils.populate(orders, parameterMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean isConfirmOrder = service.confirmOrder(orders);

		// 2.进行支付操作
		// 获得 支付必须基本数据
		String orderid = orders.getOid();
//        String money = orders.getTotal()+"";
		String money = "0.01";
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
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
		String hmac = com.neuplat.utils.PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid,
				p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);

		String url = "https://www.yeepay.com/app-merchant-proxy/node?pd_FrpId=" + pd_FrpId + "&p0_Cmd=" + p0_Cmd
				+ "&p1_MerId=" + p1_MerId + "&p2_Order=" + p2_Order + "&p3_Amt=" + p3_Amt + "&p4_Cur=" + p4_Cur
				+ "&p5_Pid=" + p5_Pid + "&p6_Pcat=" + p6_Pcat + "&p7_Pdesc=" + p7_Pdesc + "&p8_Url=" + p8_Url
				+ "&p9_SAF=" + p9_SAF + "&pa_MP=" + pa_MP + "&pr_NeedResponse=" + pr_NeedResponse + "&hmac=" + hmac;

		// 重定向到第三方支付平台
		response.sendRedirect(url);
	}

	// 查询订单信息，已购买过的产品
	public void findOrderList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		// 用session中获得用户
		User user = (User) session.getAttribute("user");
		// 如果没登陆
		if (user == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		// 根据用户，查出该用户的所有订单
		ProductService service = new ProductService();
		List<Order> ordersList = service.getUserOrderList(user);

		// 根据用户和订单查出订单项
		request.setAttribute("ordersList", ordersList);
		request.getRequestDispatcher("/order_list.jsp").forward(request, response);

	}

	// 提交购物车的订单
	public void submitOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductService service = new ProductService();
		HttpSession session = request.getSession();
		// 获得用户名
		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}
		Cart cart = (Cart) session.getAttribute("mine");
		// 创建订单对象
		Order orders = new Order();

		orders.setOid(CommonsUtils.getUUID());
		orders.setState(0);// 表示还没提交
		orders.setOrdertime(new Date());

		orders.setTotal(cart.getTotal());// 总价
		orders.setUser(user);
		orders.setName(null);
		orders.setTelephone(null);
		orders.setAddress(null);

		// 获得订单项
		Map<String, CartItem> cartItems = cart.getCartItems();
		// 创建集合
		List<OrderItem> orderItemList = new ArrayList<>();
		for (Map.Entry<String, CartItem> entry : cartItems.entrySet()) {
			OrderItem orderItem = new OrderItem();
			CartItem cartItem = entry.getValue();

			orderItem.setCount(cartItem.getBuyNum());
			orderItem.setItemid(CommonsUtils.getUUID());
			orderItem.setOrder(orders);
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setSubtotal(cartItem.getSubtotal());

			// 放到集合中
			orderItemList.add(orderItem);
		}
		orders.setOrdersList(orderItemList);
		// 把封装好的order传到service
		service.submitOrder(orders);

		// 将提单放到request域中
		request.setAttribute("orders", orders);

		System.out.println("=========提交==========");

		request.getRequestDispatcher("/order_info.jsp").forward(request, response);
	}

	public int getCurrent(HttpServletRequest request) {
		String currentStr = request.getParameter("currentPage");
		if (currentStr == null) {
			return 1;
		}
		return Integer.parseInt(currentStr);
	}

	public String getCookie(HttpServletRequest request, String pids) {
		/*
		 * 实现的功能，数字是商品的id 3-1-2-4 如果本次访问时2 2-3-1-4 如果本次访问时4 4-2-3-1
		 */

		// 得到cookie
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if ("pids".equals(cookie.getName())) {
				// 得到原来Cookie中的值
				String pidVaule = cookie.getValue();
				// 进行分片
				String[] split = pidVaule.split("-");

				List<String> asList = Arrays.asList(split);
				LinkedList<String> list = new LinkedList<>(asList);
				// 查看是否包括当前返回的商品
				if (list.contains(pids)) {
					// 如果存在，则删除
					list.remove(pids);
				}
				// 再添加到第一个，说明是刚访问过
				list.addFirst(pids);
				// 拼接pids的值
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < list.size() && i <= 7; i++) {
					sb.append(list.get(i));
					if (i + 1 != list.size()) {
						sb.append("-");
					}
				}
				pids = sb.toString();
			}
		}
		return pids;
	}

}