package com.java.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.dao.DiaryDao;
import com.java.dao.DiaryTypeDao;
import com.java.model.DiaryType;
import com.java.util.DbUtil;
import com.java.util.StringUtil;

public class DiaryTypeServlet extends HttpServlet {
	
	DbUtil dbUtil=new DbUtil();
	DiaryTypeDao diaryTypeDao=new DiaryTypeDao();
	DiaryDao diaryDao=new DiaryDao();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		if("list".equals(action)){
			diaryTypeList(request, response);
		}else if("preSave".equals(action)){
			diaryTypePreSave(request, response);
		}else if("save".equals(action)){
			diaryTypeSave(request,response);
		}else if("delete".equals(action)){
			diaryTypeDelete(request, response);
		}
	}
	
	private void diaryTypeList (HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con=null;
		try {
			con=dbUtil.getCon();
			List<DiaryType> diaryTypeList =diaryTypeDao.diaryTypeList(con);
			request.setAttribute("diaryTypeList", diaryTypeList);
			request.setAttribute("mainPage", "diaryType/diaryTypeList.jsp");
			request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//显示要修改的日志和要添加的日志
	private void diaryTypePreSave(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String diaryTypeId=request.getParameter("diaryTypeId");
		if(StringUtil.isNotEmpty(diaryTypeId)){
			Connection con=null;
			try {
				con=dbUtil.getCon();
				DiaryType diaryType=diaryTypeDao.diaryTypeShow(con, diaryTypeId);
				request.setAttribute("diaryType", diaryType);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}finally{
				try {
					dbUtil.closeCon(con);
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
		request.setAttribute("mainPage", "diaryType/diaryTypeSave.jsp");
		request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
	}
	
	private void diaryTypeSave(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String diaryTypeId=request.getParameter("diaryTypeId");
		String typeName=request.getParameter("typeName");
		DiaryType diaryType=new DiaryType(typeName);
		if(StringUtil.isNotEmpty(diaryTypeId)){
			diaryType.setDiaryTypeId(Integer.parseInt(diaryTypeId));
		}
		Connection con=null;
		try{
			con=dbUtil.getCon();
			int saveNum=0;
			if(StringUtil.isNotEmpty(diaryTypeId)){
				saveNum=diaryTypeDao.diaryTypeUpdate(con, diaryType);
			}else{
				saveNum=diaryTypeDao.diaryTypeAdd(con, diaryType);
			}
			if(saveNum>0){
				request.getRequestDispatcher("diaryType?action=list").forward(request, response);
			}else{
				request.setAttribute("diaryType", diaryType);
				request.setAttribute("error", "保存失败！");
				request.setAttribute("mainPage", "diaryType/diaryTypeSave.jsp");
				request.getRequestDispatcher("mainTemp.jsp").forward(request, response);
			}
		}catch(Exception e){
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
	
	//删除日志
	private void diaryTypeDelete(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		String diaryTypeId=request.getParameter("diaryTypeId");
		Connection con=null;
		try {
			con=dbUtil.getCon();
			if(diaryDao.existDiaryWithTypeId(con, diaryTypeId)){
				request.setAttribute("error","日志类别下有日志，不能删除该类别！");
			}else{
				diaryTypeDao.diaryTypeDelete(con, diaryTypeId);
			}
			request.getRequestDispatcher("diaryType?action=list").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
}
