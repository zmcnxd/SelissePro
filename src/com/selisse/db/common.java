/**
 * 
 */
package com.selisse.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.BooleanUtils;

/**
 * @author Administrator
 * 
 */
public class common {
	//private static String rootPath = "C:/Program Files/Apache Software Foundation/Tomcat 6.0/webapps/Selisse/";
	private static String rootPath = (0 != (new File("F:/workspace/SelissePro/WebContent/db/selisse.mdb")).length()) ? "F:/workspace/SelissePro/WebContent/" : "C:/Program Files/Apache Software Foundation/Tomcat 6.0/webapps/Selisse/";
	private static String dbPath = rootPath + "db/selisse.mdb";
	
	// Access�����
	private static String accessdriver = "sun.jdbc.odbc.JdbcOdbcDriver";
	// �����ַ�
	private static String accessURL = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ="
			+ dbPath;
	public static File tmpDir = new File(rootPath + "upload/");//初始化上传文件的临时存放目录  
	public static File saveDir = new File(rootPath + "upload/");//初始化上传文件后的保存目录  
    
	// 查询
	public static List executeQuery(String sql){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			List resultList = resultSetToList(rs);
			conn.close();
			return resultList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 执行删除动作
	 * @param sql
	 * @return
	 */
	private static boolean executeDelete(String sql){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private static boolean executeUpdate(String sql){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement();			
			stmt.executeUpdate(sql);
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private static boolean executeInsert(String sql){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * ��ȡ��Ʒ�����б�
	 * 
	 * @return
	 */
	public static List getProductSort() {
		String sql = "select * from productSort";
		return executeQuery(sql);
	}

	/**
	 * �����ز�Ʒ
	 * 
	 * @param sortID
	 * @return
	 */
	public static List getProduct(String sortID,String type) {
		String sql = "select * from product order by [order]";
		if (StringUtils.isNotEmpty(sortID)) {
			sql = "select * from product" + (StringUtils.isNotEmpty(sortID) ? (" where sortID ='" + sortID) : "")
					 + (StringUtils.isNotEmpty(type) ? (" where type ='" + type) : "")+ "' order by [order]";
		}
		return executeQuery(sql);
	}

	/**
	 * ��ȡ�������ŷ���
	 * 
	 * @return
	 */
	public static List getNewsType() {
		String sql = "select * from newsType";
		return executeQuery(sql);
	}

	/**
	 * ��ȡ�������
	 * 
	 * @param typeID
	 * @return
	 */
	public static List getNews(String typeID) {
		String sql = "select * from news order by [date] desc";
		if (StringUtils.isNotEmpty(typeID)) {
			sql = "select * from news where typeID ='" + typeID + "' order by [date] desc";
		}
		return executeQuery(sql);
	}
	
	public static List getAgents(){
		String sql = "select * from agents";
		return executeQuery(sql);
	}
	
	/**
	 * ��������
	 * @param newsID id
	 * @param title ����
	 * @param content ���� 
	 * @return
	 */
	public static boolean updateNews(String newsID,String title,String content,String date,String typeID){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement();
			String sql = "update news set title='" + title + "',content='" + content + "',[date]='" + (StringUtils.isEmpty(date) ? getNowDate() : date) + "',typeID='"+typeID+"' where ID=" + newsID;
			stmt.executeUpdate(sql);
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean updateOrder(String orderID,String expressNo,String status,String charges){
		String sql = "update orders set express_no='" + expressNo + "',status='" + status + "',charges='"+charges+"' where ID=" + orderID;
		return executeUpdate(sql);
	}
	
	public static boolean updateProduct(String productID,String product_type,String name,String imgSrc,String content,String order,String guige,String zong_price,String a_price,String amount,String last_in_time,String last_update_time){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement();
			String sql = "";
			if(!imgSrc.equals("")){
				sql = "update product set content='" + content + "',[name]='"+name + "',[type]='"+product_type +"',imgSrc='"+imgSrc+"',[order]='" + order+"',guige='"+guige+"',zongPrice='"+zong_price+"',aPrice='"+zong_price+"',amount='"+amount+"',last_in_time'"+(StringUtils.isEmpty(last_in_time) ? getNowDate() : last_in_time)+"',last_update_time='"+(StringUtils.isEmpty(last_update_time) ? getNowDate() : last_update_time) + "' where ID=" + productID;
			}else{
				sql = "update product set content='" + content + "',[name]='"+name + "',[type]='"+product_type + "',[order]='" + order+"',guige='"+guige+"',zongPrice='"+zong_price+"',aPrice='"+a_price+"',amount='"+amount+"',last_in_time='"+(StringUtils.isEmpty(last_in_time) ? getNowDate() : last_in_time)+"',last_update_time='"+(StringUtils.isEmpty(last_update_time) ? getNowDate() : last_update_time) + "' where ID=" + productID;
			}
			
			stmt.executeUpdate(sql);
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean addProduct(String name,String product_type,String imgSrc,String content,String order,String guige,String zong_price,String a_price,String amount,String last_in_time,String last_update_time){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO [product] ([name],[type],[imgSrc],[content],[order],[guige],[zongPrice],[aPrice],[amount],[last_in_time],[last_update_time]) VALUES('" + name + "','" + product_type + "','" + imgSrc + "','"+content+"','"+order+"','"+guige+"','"+zong_price+"','"+a_price+"','"+amount+"','"+(StringUtils.isEmpty(last_in_time) ? getNowDate() : last_in_time)+"','"+(StringUtils.isEmpty(last_update_time) ? getNowDate() : last_update_time)+"')";
			stmt.execute(sql);
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * ���¹�����Ϣ
	 * @param id
	 * @param content
	 * @return
	 */
	public static boolean updateCommonInfo(String id,String content) {
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement();
			String sql = "update commonInfo set content='" + content + "' where ID=" + id;
			stmt.executeUpdate(sql);
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * ��ȡ������ϸ
	 * 
	 * @param newsID
	 * @return
	 */
	public static JSONObject getNewsDetail(String newsID) {
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String sql = "select * from news where ID =" + newsID + "";
			ResultSet rs = stmt.executeQuery(sql);
			List resultList = resultSetToList(rs);
			conn.close();
			JSONObject jObject = JSONObject.fromObject(resultList.get(0));
			return jObject;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取代理详细信息
	 * @param type
	 * @param value
	 * @return
	 */
	public static JSONObject getAgentDetail(String type,String value) {
		String sql = "";
		if(type.equals("openid")){
			sql = "select * from agents where openid='"+value+"'";
		}else{
			sql = "select * from agents where ID =" + value + "";
		}

		List resultList = executeQuery(sql);
		JSONObject jObject = JSONObject.fromObject(resultList.get(0));
		return jObject;
	}
	
	/**
	 * 获取订单详细信息
	 * @param orderID
	 * @return
	 */
	public static JSONObject getOrderDetail(String orderID){
		String sql = "select * from orders where ID =" + orderID + "";
		return JSONObject.fromObject(executeQuery(sql).get(0));
	}
	
	/**
	 * 获取产品详细
	 * @param productID
	 * @return
	 */
	public static JSONObject getProductDetail(String productID){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String sql = "select * from product where ID =" + productID + "";
			ResultSet rs = stmt.executeQuery(sql);
			List resultList = resultSetToList(rs);
			conn.close();
			JSONObject jObject = JSONObject.fromObject(resultList.get(0));
			return jObject;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡƷ�ƽ���
	 * 
	 * @return
	 */
	public static List getBrandIntroduction() {
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String sql = "select * from brandIntro";
			ResultSet rs = stmt.executeQuery(sql);
			List resultList = resultSetToList(rs);
			conn.close();
			return resultList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ����Ʒ�ƽ�����Ŀ������
	 * @param id
	 * @param content
	 * @return
	 */
	public static boolean updateBrandInfo(String id,String content){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement();
			String sql = "update brandIntro set content='" + content + "' where ID=" + id;
			stmt.executeUpdate(sql);
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * ��ȡ������Ϣ
	 * 
	 * @return
	 */
	public static List getCommonInfo() {
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String sql = "select * from commonInfo";
			ResultSet rs = stmt.executeQuery(sql);
			List resultList = resultSetToList(rs);
			conn.close();
			return resultList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �������
	 * @param title ����
	 * @param content ����
	 * @return �Ƿ�ɹ�
	 */
	public static boolean addNews(String title,String content,String date,String typeID){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO [news] ([title],[content],[date],[typeID]) VALUES('" + title + "','" + content + "','" + (StringUtils.isEmpty(date) ? getNowDate() : date) + "','"+typeID+"')";
			stmt.execute(sql);
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean addAgent(String name,String type, String mobile,String balance, String cert,String agent_no,String weixin_no,
			String wangwang_no,String join_time,String img_path){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO [agents] ([name],[type],[mobile],[balance],[cert],[agent_no],[weixin_no],"+"" +
					"[wangwang_no],[join_time],[img_path]) VALUES('" + name + "','" + mobile +  "','" + type + "','" + balance + "','" + cert + 
					"','" + agent_no + "','" + weixin_no + "','" + wangwang_no + "','" + join_time + "','" + img_path + "')";
			stmt.execute(sql);
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean updateAgent(String name,String type, String mobile, String balance, String cert,String agent_no,String weixin_no,
			String wangwang_no,String join_time,String img_path,String agentID){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement();
			String sql = "";
			if(!img_path.equals("")){
				sql = "update agents set name='" + name + "',mobile='" + mobile +  "',type='" + type + "',balance='" + balance + "',cert='" + cert + "',agent_no='" + agent_no + "',weixin_no='" + weixin_no + "',wangwang_no='" + wangwang_no + "',join_time='" + join_time + "',img_path='" + img_path + "' where ID=" + agentID;
			}
			else{
				sql = "update agents set name='" + name + "',mobile='" + mobile +  "',type='" + type + "',balance='" + balance + "',cert='" + cert + "',agent_no='" + agent_no + "',weixin_no='" + weixin_no + "',wangwang_no='" + wangwang_no + "',join_time='" + join_time + "' where ID=" + agentID;
			}
			stmt.executeUpdate(sql);
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean deleteNews(String id){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement();
			String sql = "delete from news where ID=" + id;
			stmt.execute(sql);
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 删除订单
	 * @param orderID
	 * @return
	 */
	public static boolean delOrder(String orderID){
		String sql = "delete from orders where ID=" + orderID;
		return executeDelete(sql);
	}
	
	public static boolean deleteProduct(String id){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement();
			String sql = "delete from product where ID=" + id;
			stmt.execute(sql);
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean deleteAgent(String id){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement();
			String sql = "delete from agents where ID=" + id;
			stmt.execute(sql);
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static List resultSetToList(ResultSet rs)
			throws java.sql.SQLException {
		if (rs == null)
			return Collections.EMPTY_LIST;
		ResultSetMetaData md = rs.getMetaData(); // �õ����(rs)�Ľṹ��Ϣ�������ֶ����ֶ����
		int columnCount = md.getColumnCount(); // ���ش� ResultSet �����е�����
		List list = new ArrayList();
		Map rowData = new HashMap();
		while (rs.next()) {
			rowData = new HashMap(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				rowData.put(md.getColumnName(i), rs.getObject(i));
			}
			list.add(rowData);
			System.out.println("list:" + list.toString());
		}
		return list;
	}
	
	/**
	 * ��ʽ����ǰʱ��
	 * @return
	 */
	public static String getNowDate(){
		//SimpleDateFormat partern = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");// 12小时制
		SimpleDateFormat partern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制
		Date now = new Date();
		return partern.format(now);
	}
	
	public static List queryAgentInfo(String keyword) {
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String sql = "select * from agents where mobile = '"+keyword+"' or weixin_no = '"+keyword+"' or agent_no = '"+keyword+"'";
			ResultSet rs = stmt.executeQuery(sql);
			List resultList = resultSetToList(rs);
			conn.close();
			return resultList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean addIndexPic(String pic_href,String img_path){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO [indexPic] ([pic_href],[img_path]) VALUES('" + pic_href + "','" + img_path + "')";
			stmt.execute(sql);
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean addIndexBottomPic(String pic_href,String img_path){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO [indexBottomPic] ([pic_href],[img_path]) VALUES('" + pic_href + "','" + img_path + "')";
			stmt.execute(sql);
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static List getIndexPics(){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String sql = "select * from indexPic";
			ResultSet rs = stmt.executeQuery(sql);
			List resultList = resultSetToList(rs);
			conn.close();
			return resultList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static List getIndexBottomPics(){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String sql = "select * from indexBottomPic";
			ResultSet rs = stmt.executeQuery(sql);
			List resultList = resultSetToList(rs);
			conn.close();
			return resultList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean deleteIndexPic(String id){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement();
			String sql = "delete from indexPic where ID=" + id;
			stmt.execute(sql);
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean deleteIndexBottomPic(String id){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement();
			String sql = "delete from indexBottomPic where ID=" + id;
			stmt.execute(sql);
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean updateIndexPic(String pic_href,String img_path,String id){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement();
			String sql = "";
			if(!img_path.equals("")){
				sql = "update indexPic set pic_href='" + pic_href + "',img_path='" + img_path + "' where ID=" + id;
			}
			else{
				sql = "update indexPic set pic_href='" + pic_href + "' where ID=" + id;
			}
			stmt.executeUpdate(sql);
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean updateIndexBottomPic(String pic_href,String img_path,String id){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement();
			String sql = "";
			if(!img_path.equals("")){
				sql = "update indexBottomPic set pic_href='" + pic_href + "',img_path='" + img_path + "' where ID=" + id;
			}
			else{
				sql = "update indexBottomPic set pic_href='" + pic_href + "' where ID=" + id;
			}
			stmt.executeUpdate(sql);
			conn.close();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static JSONObject getIndexPicDetail(String id){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String sql = "select * from indexPic where ID =" + id + "";
			ResultSet rs = stmt.executeQuery(sql);
			List resultList = resultSetToList(rs);
			conn.close();
			JSONObject jObject = JSONObject.fromObject(resultList.get(0));
			return jObject;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static JSONObject getIndexBottomPicDetail(String id){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String sql = "select * from indexBottomPic where ID =" + id + "";
			ResultSet rs = stmt.executeQuery(sql);
			List resultList = resultSetToList(rs);
			conn.close();
			JSONObject jObject = JSONObject.fromObject(resultList.get(0));
			return jObject;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	// 获取余额
	public static String getBalance(String openID){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String sql = "select * from agents where openID ='" + openID + "'";
			ResultSet rs = stmt.executeQuery(sql);
			List resultList = resultSetToList(rs);
			conn.close();
			JSONObject jObject = JSONObject.fromObject(resultList.get(0));
			return jObject.getString("balance");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	// 绑定代理
	public static String bindAgent(String openID, String weixinNo){
		Connection conn = null;
		try {
			Class.forName(accessdriver);
			Properties properties = new Properties() ;  
	        properties.setProperty("charSet", "gbk") ;
			conn = DriverManager.getConnection(accessURL,properties);
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String sql = "select * from agents where weixin_no ='" + weixinNo + "'";
			ResultSet rs = stmt.executeQuery(sql);
			List resultList = resultSetToList(rs);
			String result = "false";
			if(resultList.size() > 0){
				String usql = "update agents set openID='" + openID + "' where weixin_no='" + weixinNo + "'";
				stmt.executeUpdate(usql);
				result = "success";
			}
			conn.close();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 判断用户是否已经绑定过
	 * @param openID
	 * @param weixinNo
	 * @return
	 */
	public static String isbindAgent(String openID){
		String sql = "select * from agents where openID='" + openID + "'";
		List result = executeQuery(sql);
		if(result.size() > 0){
			return "1";
		}
		return "0";
	}
	
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @param type
	 * @return
	 */
	public static String userLogin(String username,String password,String type){
		String sql = "select * from agents where name = '" + username + "' and password='" + password + "' and type='" + type + "'";
		List result = executeQuery(sql);
		if(result.size() > 0){
			JSONObject jObject = JSONObject.fromObject(result.get(0));
			return jObject.getString("ID");
		}
		return "";
	}
	
	/**
	 * 修改密码
	 * @param old
	 * @param newP
	 * @param confirm
	 * @return
	 */
	public static boolean changePassword(String userid,String old,String newP){
		String sql = "update agents set password='" + newP + "' where ID=" + userid;
		return executeUpdate(sql);
	}
	
	/**
	 * 更新用户余额
	 * @param agentID
	 * @param balance
	 * @return
	 */
	public static boolean updateAgentInfo(String agentID,String type,String value){
		String sql = "";
		if(type.equals("balance")){
			sql = "update agents set balance='" + value + "' where ID=" + agentID + "";
		}
		
		return executeUpdate(sql);
	}
	
	/**
	 * 更新用户的收货地址
	 * @param userID
	 * @param names
	 * @param mobiles
	 * @param addrs
	 * @return
	 */
	public static boolean updateAddress(String userID,String names,String mobiles,String addrs){
		String[] nameArr = names.split("\\|");
		String[] mobileArr = mobiles.split("\\|");
		String[] addrArr = addrs.split("\\|");
		String sql = "delete from address where userID=" + userID;
		boolean delResult = executeDelete(sql);
		if(delResult){
			for(int index=0;index<nameArr.length;index++){
				String insSql = "INSERT INTO address(userID,name,mobile,addr) values("+userID+",'"+nameArr[index]+"','"+mobileArr[index]+"','"+addrArr[index]+"')";
				if(!executeInsert(insSql)){
					return false;
				}
			}
		}
		else{
			return false;
		}
		
		return true;
		
	}
	
	/**
	 * 添加订单
	 * @param name
	 * @param reciver
	 * @param address
	 * @param mobile
	 * @param amount
	 * @param products
	 * @param samples
	 * @param wuliao
	 * @param charges
	 * @return
	 */
	public static boolean addOrder(String name,String reciver,String address,String mobile,String amount,String products,String samples,String wuliao,String charges,String express){
		String sql = "insert into orders([name],[time],[reciver],[address],[mobile],[amount],[products],[samples],[wuliao],[express],[charges]) values('"+name+"','" + getNowDate() +"','"+reciver+"','"+address+"','"+mobile+"','"+amount+"','"+products+"','"+samples+"','"+wuliao+"','"+express+"','"+charges+"')";
		return executeInsert(sql);
	}
	
	/**
	 * 获取所有订单
	 * @return
	 */
	public static List getOrders(String userName){
		String sql = (StringUtils.isEmpty(userName) ? "select * from orders" : "select * from orders where name = '" + userName + "' order by time desc");
		return executeQuery(sql);
	}
	
	// 减少库存
	public static void updateProductAmount(String id,String val){
		String sql = "update product set amount = amount - " + val + " where id="+id;
		executeUpdate(sql);
	}
	
	// 增加库存
	public static void addProductAmount(String id,String val){
		String sql = "update product set amount = amount + " + val + " where id="+id;
		executeUpdate(sql);
	}
	
	/**
	 * 查询用户的收货地址
	 */
	public static List getAgentAddress(String userID){
		String sql = "select * from address where userID=" + userID;
		return executeQuery(sql);
	}
}
