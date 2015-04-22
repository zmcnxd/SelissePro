/**
 * 
 */
package com.selisse.user;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;

import com.selisse.db.common;

/**
 * @author Administrator
 *
 */
public class addOrder extends HttpServlet {
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
		PrintWriter outer = response.getWriter();			
		String name = request.getParameter("name");
		String agentID = request.getParameter("agentID");
		String reciver = request.getParameter("reciver");
		String address = request.getParameter("address");
		String express = request.getParameter("express");
		String mobile = request.getParameter("mobile");
		String amount = request.getParameter("amount");
		String products = request.getParameter("products");
		String samples = request.getParameter("samples");
		String wuliao = request.getParameter("wuliao");
		String charges = request.getParameter("charges");
		
		boolean isSuccess = common.addOrder(name,agentID,reciver,address,mobile,amount,products,samples,wuliao,charges,express);
		try{
			// 分析订单，更新库存
			JSONObject jObject = JSONObject.fromObject(products);
			Iterator it = jObject.keys();
			while (it.hasNext()) {  
	            String key = (String) it.next();  
	            String value = jObject.getString(key);  
	            common.updateProductAmount(key,value);
	        } 
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		         
		
		outer.write((isSuccess ? "000000" : "999999"));
	}

	// ���ʵ��
	public void destroy() {
		super.destroy();
		System.out.println("����destroy()�����������������ʵ��Ĺ���");
	}
}
