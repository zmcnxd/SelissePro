/**
 * 
 */
package com.selisse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.selisse.db.common;

/**
 * @author Administrator
 *
 */
public class delOrder extends HttpServlet {
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		System.out.println("����init()�������������г�ʼ������");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		doGet(request, response);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String orderID = request.getParameter("orderID");
		boolean isSuccess = common.delOrder(orderID);
		
		try{
			// 分析订单，更新(增加)库存
			String sql = "select * from orders where ID =" + orderID + "";
			String products = JSONObject.fromObject(common.executeQuery(sql).get(0)).getString("products");
			
			JSONObject jObject = JSONObject.fromObject(products);
			Iterator it = jObject.keys();
			while (it.hasNext()) {  
	            String key = (String) it.next();  
	            String value = jObject.getString(key);  
	            common.addProductAmount(key,value);
	        } 
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		
		out.write((isSuccess ? "000000" : "999999"));
	}

	// ���ʵ��
	public void destroy() {
		super.destroy();
		System.out.println("����destroy()�����������������ʵ��Ĺ���");
	}
}
