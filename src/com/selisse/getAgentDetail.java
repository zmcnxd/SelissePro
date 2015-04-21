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

import net.sf.json.JSONObject;

import org.json.JSONArray;

import com.selisse.db.common;

/**
 * @author Administrator
 *
 */
public class getAgentDetail extends HttpServlet {
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		System.out.println("����init()�������������г�ʼ������");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String agentID = request.getParameter("agentID");
		String openid = request.getParameter("openid");
		String type = request.getParameter("type");
		String value = "";
		JSONObject agentDetail = new JSONObject();
		if(null != type && type.equals("openid")){
			value = openid;
		}
		else{
			type = "agentID";
			value = agentID;
		}

		agentDetail = common.getAgentDetail(type,value); 
		out.write(agentDetail.toString());
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	// ���ʵ��
	public void destroy() {
		super.destroy();
		System.out.println("����destroy()�����������������ʵ��Ĺ���");
	}
}
