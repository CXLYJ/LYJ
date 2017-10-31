package com.java.web;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.java.dao.UserDao;
import com.java.model.User;
import com.java.util.DateUtil;
import com.java.util.DbUtil;
import com.java.util.PropertiesUtil;

public class UserServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DbUtil dbUtil=new DbUtil();
	UserDao userDao=new UserDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action=request.getParameter("action");
		if("preSave".equals(action)){
			userPreSave(request,response);
		}else if("save".equals(action)){
			userSave(request,response);
		}
	}
	
	private void userPreSave(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
			request.setAttribute("mainPage", "user/userSave.jsp");
			request.getRequestDispatcher("mainTemp.jsp").forward(request, response);		
	}
	
	private void userSave(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		FileItemFactory factory=new DiskFileItemFactory();
		ServletFileUpload upload=new ServletFileUpload(factory);
		List<FileItem> items=null;
		try {
			items=upload.parseRequest(request);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<FileItem> itr=items.iterator();
		
		HttpSession session=request.getSession();
		
		User user=(User)session.getAttribute("currentUser");
		boolean imageChange=false;
		while(itr.hasNext()){
			FileItem item=(FileItem)itr.next();
			if(item.isFormField()){
				String fieldName=item.getFieldName();
				if("nickName".equals(fieldName)){
					user.setNickName(item.getString("utf-8"));
				}
				if("mood".equals(fieldName)){
					user.setMood(item.getString("utf-8"));
				}
			}else if(!"".equals(item.getName())){
				try{
					imageChange=true;
					String imageName=DateUtil.getCurrentDateStr();
					user.setImageName(imageName+"."+item.getName().split("\\.")[1]);
					String filePath=PropertiesUtil.getValue("imagePath")+imageName+"."+item.getName().split("\\.")[1];
					item.write(new File(filePath));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		
		if(!imageChange){
			user.setImageName(user.getImageName().replaceFirst(PropertiesUtil.getValue("imageFile"), ""));
		}
		
		Connection con=null;
		try {
			con=dbUtil.getCon();
			int saveNums=userDao.userUpdate(con, user);
			if(saveNums>0){
				user.setImageName(PropertiesUtil.getValue("imageFile")+user.getImageName());
				session.setAttribute("currentUser", user);
				request.getRequestDispatcher("main?all=true").forward(request, response);
			}else{
				request.setAttribute("currentUser", user);
				request.setAttribute("error", "±£¥Ê ß∞‹£°");
				request.setAttribute("mainPage", "user/userSave.jsp");
				request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
