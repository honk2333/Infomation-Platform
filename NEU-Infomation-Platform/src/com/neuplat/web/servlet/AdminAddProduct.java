package com.neuplat.web.servlet;

import com.neuplat.domain.Cart;
import com.neuplat.domain.CartItem;
import com.neuplat.domain.Category;
import com.neuplat.domain.Product;
import com.neuplat.service.AdminService;
import com.neuplat.service.ProductService;
import com.neuplat.utils.CommonsUtils;

import org.apache.commons.beanutils.BeanUtils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AdminAddProduct")
public class AdminAddProduct extends HttpServlet {
	AdminService service = new AdminService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		Product product = new Product();
		try {
			// 创建磁盘文件项
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 文件上传核心
			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			fileUpload.setHeaderEncoding("utf-8");
			// 解析
			List<FileItem> list = fileUpload.parseRequest(request);

			// 遍历表单项
			for (FileItem fileItem : list) {
				// 判断是否为普通表单项

				if (fileItem.isFormField()) {
					String fieldName = fileItem.getFieldName();
					String fileValue = fileItem.getString("UTF-8");
					map.put(fieldName, fileValue);
				} else {
					// 文件表单项
					String name = CommonsUtils.getUUID();
					InputStream is = fileItem.getInputStream();
					// 获得本地存储的位置
					String filePath = this.getServletContext().getRealPath("upload");
					System.out.println(filePath);
					OutputStream os = new FileOutputStream(filePath + "/" + name);
					IOUtils.copy(is, os);
					String path = "upload/" + name;
					map.put("pimage", path);
				}
			}
			BeanUtils.populate(product, map);
//          private String pid;
			product.setPid(CommonsUtils.getUUID());
//          private Date pdate;
			product.setPdate(new Date());
//          private int pflag;
			product.setPflag(0);
//          private Category category;
			Category category = new Category();
			category.setCid(map.get("cid").toString());
			product.setCategory(category);
			boolean isAdd = service.addProduct(product);
			if (isAdd) {
				request.setAttribute("product", product);
				request.getRequestDispatcher("/addsuccess.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
