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
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;

import com.selisse.db.common;

/**
 * @author Administrator
 *
 */
public class updateAgentInfo extends HttpServlet {
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
		String agentID = request.getParameter("agentID");
		String type = request.getParameter("type");
		String value = request.getParameter("value");
		String jsonp=request.getParameter("jsonpcallback");
		if(!type.equals("address")){
			boolean isSuccess = common.updateAgentInfo(agentID,type,value);
			if(StringUtils.isNotEmpty(jsonp)){
				outer.write(jsonp+"({'result':'"+(isSuccess ? "000000" : "999999")+"'})");
			}else{
				outer.write((isSuccess ? "000000" : "999999"));
			}
		}else{
			String names = request.getParameter("names");
			String mobiles = request.getParameter("mobiles");
			String addrs = request.getParameter("addrs");
			boolean isSuccess = common.updateAddress(agentID,names,mobiles,addrs);
			if(StringUtils.isNotEmpty(jsonp)){
				outer.write(jsonp+"({'result':'"+(isSuccess ? "000000" : "999999")+"'})");
			}else{
				outer.write((isSuccess ? "000000" : "999999"));
			}
		}
		
	}

	// ���ʵ��
	public void destroy() {
		super.destroy();
		System.out.println("����destroy()�����������������ʵ��Ĺ���");
	}
}
