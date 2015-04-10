/**
 * 
 */
package com.selisse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.selisse.db.common;

/**
 * @author Administrator
 *
 */
public class getBrandIntroduction extends HttpServlet {
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		System.out.println("我是init()方法！用来进行初始化工作");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		// 获得品牌介绍
		List brands = common.getBrandIntroduction();
		JSONArray brandsArr = new JSONArray(brands);
		out.write(brandsArr.toString());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	// 销毁实例
	public void destroy() {
		super.destroy();
		System.out.println("我是destroy()方法！用来进行销毁实例的工作");
	}
}
