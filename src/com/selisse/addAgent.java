/**
 * 
 */
package com.selisse;

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
import org.json.JSONArray;

import com.selisse.db.common;

/**
 * @author Administrator
 *
 */
public class addAgent extends HttpServlet {
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
		request.setCharacterEncoding("utf-8");
		PrintWriter outer = response.getWriter();
		String name = "";
		String type = "";
		String mobile = "";
		String balance = "";
		String cert = "";
		String agent_no = "";
		String weixin_no = "";
		String wangwang_no = "";
		String join_time = "";
		String img_path = "";
		try{  
            if(ServletFileUpload.isMultipartContent(request)){  
	            DiskFileItemFactory dff = new DiskFileItemFactory();//创建该对象  
	            dff.setRepository(common.tmpDir);// 指定上传文件的临时目录  
	            dff.setSizeThreshold(1024000);//指定在内存中缓存数据大小,单位为byte  
	            ServletFileUpload sfu = new ServletFileUpload(dff);//创建该对象  
	            sfu.setFileSizeMax(5000000);// 指定单个上传文件的最大尺寸  
	            sfu.setSizeMax(10000000);//指定一次上传多个文件的 总尺寸  
	            //FileItemIterator fii = sfu.getItemIterator(request);//解析request 请求,并返回FileItemIterator集合  
	            List<FileItem> list = sfu.parseRequest(request);// 解析
	            for (FileItem ff : list) {
	            	if (ff.isFormField()) {
	            		String ds = ff.getString("UTF-8");//处理中文
	            		if(ff.getFieldName().equals("name")){
	            			name=ds;
	            		}else if(ff.getFieldName().equals("mobile")){
	            			mobile=ds;
	            		}else if(ff.getFieldName().equals("cert")){
	            			cert=ds;
	            		}else if(ff.getFieldName().equals("balance")){
	            			balance=ds;
	            		}else if(ff.getFieldName().equals("agent_no")){
	            			agent_no=ds;
	            		}else if(ff.getFieldName().equals("weixin_no")){
	            			weixin_no=ds;
	            		}else if(ff.getFieldName().equals("wangwang_no")){
	            			wangwang_no=ds;
	            		}else if(ff.getFieldName().equals("join_time")){
	            			join_time=ds;
	            		}else if(ff.getFieldName().equals("type")){
	            			type=ds;
	            		}
            		}
	            	else{
	            		String ss = ff.getName();
	            		if(!ff.getName().equals("")){
		            		long dateStr = Calendar.getInstance().getTimeInMillis();
		            		String extend = ff.getName().substring(ff.getName().lastIndexOf("."),ff.getName().length());
		            		img_path = "/upload/" + dateStr + "_water" + extend;
		            		//ss = ss.substring(ss.lastIndexOf("\\") + 1);//解析文件名
		            		String saveFileName = common.saveDir + "/" + dateStr + extend;
		            		String waterSaveFileName = common.saveDir + "/" + dateStr + "_water" + extend;
		            		FileUtils.copyInputStreamToFile( //直接使用commons.io.FileUtils
		            		ff.getInputStream(),
		            		new File(saveFileName));
		            		// 打水印
		            		WaterMarkUtils.targetZoomOut(WaterMarkUtils.pressText("微信号：" + weixin_no, saveFileName,
		            				"雅黑", "#dddddd", 28, new Float(0.7), true),waterSaveFileName);
	            		}
	            	}
	            }
  
            response.getWriter().println("成功了,棒棒哒!!!");//终于成功了,还不到你的上传文件中看看,你要的东西都到齐了吗
    		boolean isSuccess = common.addAgent(name, type, mobile, balance, cert, agent_no,weixin_no,wangwang_no,join_time,img_path);
    		outer.write((isSuccess ? "成功了,棒棒哒 返回码:000000" : "上传失败 返回码:999999"));
        }  
        }catch(Exception e){  
        	e.printStackTrace(); 
        	outer.write(e.getMessage());
        }  
	}

	// ���ʵ��
	public void destroy() {
		super.destroy();
		System.out.println("����destroy()�����������������ʵ��Ĺ���");
	}
}
