/**
 * 
 */
package com.selisse;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.selisse.db.common;

/**
 * @author Administrator
 *
 */
public class editCommonInfo extends HttpServlet {
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		System.out.println("我是init()方法！用来进行初始化工作");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		doGet(request, response);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String content = request.getParameter("content");
		String id = request.getParameter("id");
		boolean isSuccess = common.updateCommonInfo(id,content);
		out.write((isSuccess ? "000000" : "999999"));
	}

	// 销毁实例
	public void destroy() {
		super.destroy();
		System.out.println("我是destroy()方法！用来进行销毁实例的工作");
	}
}
