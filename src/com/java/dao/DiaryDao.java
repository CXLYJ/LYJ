package com.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.java.model.Diary;
import com.java.model.PageBean;
import com.java.util.DateUtil;
import com.java.util.StringUtil;

public class DiaryDao {
		//设置集合查询出页数标题和日期并遍历类别和日期下的日记
	public List<Diary> diaryList(Connection con,PageBean pageBean,Diary s_diary)throws Exception{
		List<Diary> diaryList=new ArrayList<Diary>();
		StringBuffer sb=new StringBuffer("select * from t_diary t1,t_diaryType t2 where t1.typeId=t2.diaryTypeId ");
		//模糊查询     搜索功能
		if(StringUtil.isNotEmpty(s_diary.getTitle())){
			sb.append(" and t1.title like '%"+s_diary.getTitle()+"%'");
		}
		//类别查询
		if(s_diary.getTypeId()!=-1){
			sb.append(" and t1.typeId="+s_diary.getTypeId());
		}
		//日期查询
		if(StringUtil.isNotEmpty(s_diary.getReleaseDateStr())){
			sb.append(" and DATE_FORMAT(t1.releaseDate,'%Y年%m月')='"+s_diary.getReleaseDateStr()+"'");
		}
		sb.append(" order by t1.releaseDate desc");
		if(pageBean!=null){
			sb.append(" limit "+pageBean.getStart()+","+pageBean.getPageSize());
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			Diary diary=new Diary();
			diary.setDiaryId(rs.getInt("diaryId"));  //把从数据库中得到diaryId设置到diary.setDiaryId中
			diary.setTitle(rs.getString("title"));
			diary.setContent(rs.getString("content"));
			diary.setReleaseDate(DateUtil.formatString(rs.getString("releaseDate"), "yyyy-MM-dd HH:mm:ss"));
			diaryList.add(diary);
		}
		return diaryList;
	}
	//设置集合遍历日记id下的日记类别id的总记录数并返回结果
	public int diaryCount(Connection con,Diary s_diary)throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from t_diary t1,t_diaryType t2 where t1.typeId=t2.diaryTypeId ");
		if(StringUtil.isNotEmpty(s_diary.getTitle())){
			sb.append(" and t1.title like '%"+s_diary.getTitle()+"%'");
		}
		if(s_diary.getTypeId()!=-1){
			sb.append(" and t1.typeId="+s_diary.getTypeId());
		}
		if(StringUtil.isNotEmpty(s_diary.getReleaseDateStr())){
			sb.append(" and DATE_FORMAT(t1.releaseDate,'%Y年%m月')='"+s_diary.getReleaseDateStr()+"'");
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
	//查询出日期和日期类别下的每条记录数
	public List<Diary> diaryCountList(Connection con)throws Exception{
		List<Diary> diaryCountList=new ArrayList<>();
		String sql="SELECT DATE_FORMAT(releaseDate,'%Y年%m月') as releaseDateStr ,COUNT(*) AS diaryCount  FROM t_diary GROUP BY DATE_FORMAT(releaseDate,'%Y年%m月') ORDER BY DATE_FORMAT(releaseDate,'%Y年%m月') DESC;";
		PreparedStatement pstmt=con.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()){
			Diary diary=new Diary();
			diary.setReleaseDateStr(rs.getString("releaseDateStr"));
			diary.setDiaryCount(rs.getInt("diaryCount"));
			diaryCountList.add(diary);
		}
		return diaryCountList;
	}
		
	public Diary diaryShow(Connection con,String diaryId)throws Exception{
		String sql="select * from t_diary t1,t_diaryType t2 where t1.typeId=t2.diaryTypeId and t1.diaryId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, diaryId);
		ResultSet rs=pstmt.executeQuery();
		Diary diary=new Diary();
		if(rs.next()){
			diary.setDiaryId(rs.getInt("diaryId"));
			diary.setTitle(rs.getString("title"));
			diary.setContent(rs.getString("content"));
			diary.setTypeId(rs.getInt("typeId"));
			diary.setTypeName(rs.getString("typeName"));
			diary.setReleaseDate(DateUtil.formatString(rs.getString("releaseDate"),"yyyy-MM-dd HH:mm:ss"));
		}
		return diary;
	}
	//添加
	public int diaryAdd(Connection con,Diary diary)throws Exception{
		String sql="insert into t_diary values(null,?,?,?,now())";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, diary.getTitle());
		pstmt.setString(2, diary.getContent());
		pstmt.setInt(3, diary.getTypeId());
		return pstmt.executeUpdate();
	}
	
	//删除
	public int diaryDelete(Connection con,String diaryId)throws Exception{
		String sql="delete from t_diary where diaryId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, diaryId);
		return pstmt.executeUpdate();
	}
	
	//修改
	public int diaryUpdate(Connection con,Diary diary)throws Exception{
		String sql="update t_diary set title=?,content=?,typeId=? where diaryId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, diary.getTitle());
		pstmt.setString(2, diary.getContent());
		pstmt.setInt(3, diary.getTypeId());
		pstmt.setInt(4, diary.getDiaryId());
		return pstmt.executeUpdate();
	}
	
	//如果日志类别有日志则不能删除该日志
	public boolean existDiaryWithTypeId(Connection con,String typeId)throws Exception{
		String sql="select * from t_diary where typeId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, typeId);
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return true;
		}else{
			return false;
		}
	}
}
