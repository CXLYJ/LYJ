package com.java.web;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.java.dao.DiaryDao;
import com.java.dao.DiaryTypeDao;
import com.java.model.Diary;
import com.java.model.PageBean;
import com.java.util.DbUtil;
import com.java.util.PropertiesUtil;
import com.java.util.StringUtil;

public class MainServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DbUtil dbUtil=new DbUtil();
	DiaryDao diaryDao=new DiaryDao();
	DiaryTypeDao diaryTypeDao=new DiaryTypeDao();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session=request.getSession();	//����session��request����
		String s_typeId=request.getParameter("s_typeId");		//�õ�ǰ̨s_typeId���Ե�ֵ
		String s_releaseDateStr=request.getParameter("s_releaseDateStr");
		String s_title=request.getParameter("s_title");
		String all=request.getParameter("all");
		String page=request.getParameter("page");
		Diary diary=new Diary();
		if("true".equals(all)){			
			if(StringUtil.isNotEmpty(s_title)){  	//���all����true�Ļ���s_title��ֵ���ý�ȥ
				diary.setTitle(s_title);
			}
			session.removeAttribute("s_releaseDateStr"); //�Ƴ�ֵ
			session.removeAttribute("s_typeId");		//�Ƴ�ֵ
			session.setAttribute("s_title", s_title);  //��ǰ̨�õ�s_titleֵ���õ���̨s_title������
		}else{
			if(StringUtil.isNotEmpty(s_typeId)){
				diary.setTypeId(Integer.parseInt(s_typeId));
				session.setAttribute("s_typeId", s_typeId);
				session.removeAttribute("s_releaseDateStr");
				session.removeAttribute("s_title");
			}
			if(StringUtil.isNotEmpty(s_releaseDateStr)){
				s_releaseDateStr=new String(s_releaseDateStr.getBytes("ISO-8859-1"),"UTF-8");
				diary.setReleaseDateStr(s_releaseDateStr);
				session.setAttribute("s_releaseDateStr", s_releaseDateStr);
				session.removeAttribute("s_typeId");
				session.removeAttribute("s_title");
			}
			
			//��ҳ��ʾ
			if(StringUtil.isEmpty(s_typeId)){
				Object o=session.getAttribute("s_typeId");
				if(o!=null){
					diary.setTypeId(Integer.parseInt((String)o));
				}
			}
			if(StringUtil.isEmpty(s_releaseDateStr)){
				Object o=session.getAttribute("s_releaseDateStr");
				if(o!=null){
					diary.setReleaseDateStr((String)o);
				}
			}
			if(StringUtil.isEmpty(s_title)){
				Object o=session.getAttribute("s_title");
				if(o!=null){
					diary.setTitle((String)o);
				}
			}
		}
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		Connection con=null;
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(PropertiesUtil.getValue("pageSize")));
		try {
			con=dbUtil.getCon();
			List<Diary> diaryList=diaryDao.diaryList(con,pageBean,diary);
			int total=diaryDao.diaryCount(con,diary);
			String pageCode=this.genPagation(total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
			request.setAttribute("pageCode", pageCode);
			request.setAttribute("diaryList", diaryList);
			session.setAttribute("diaryTypeCountList", diaryTypeDao.diaryTypeCountList(con));
			session.setAttribute("diaryCountList", diaryDao.diaryCountList(con));
			request.setAttribute("mainPage", "diary/diaryList.jsp");  //ͨ��JSP��mainPage��EI���ʽ���õ�diary/diaryList.jsp����ʾ
			request.getRequestDispatcher("mainTemp.jsp").forward(request, response);	//ʹ��diary/diaryList.jspҳ����Ҳ��ʾmainTemp.jspҳ��
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
	//���÷�ҳ
	private String genPagation(int totalNum,int currentPage,int pageSize){
		int totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		StringBuffer pageCode=new StringBuffer();
		pageCode.append("<li><a href='main?page=1'>��ҳ</a></li>");
		if(currentPage==1){
			pageCode.append("<li class='disabled'><a href='#'>��һҳ</a></li>");
		}else{
			pageCode.append("<li><a href='main?page="+(currentPage-1)+"'>��һҳ</a></li>");
		}
		for(int i=currentPage-2;i<=currentPage+2;i++){
			if(i<1||i>totalPage){
				continue;
			}
			if(i==currentPage){
				pageCode.append("<li class='active'><a href='#'>"+i+"</a></li>");
			}else{
				pageCode.append("<li><a href='main?page="+i+"'>"+i+"</a></li>");
			}
		}
		if(currentPage==totalPage){
			pageCode.append("<li class='disabled'><a href='#'>��һҳ</a></li>");
		}else{
			pageCode.append("<li><a href='main?page="+(currentPage+1)+"'>��һҳ</a></li>");
		}
		pageCode.append("<li><a href='main?page="+totalPage+"'>βҳ</a></li>");
		return pageCode.toString();
	}
	
	

}
