package com.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.java.model.User;
import com.java.util.MD5Util;
import com.java.util.PropertiesUtil;

public class UserDao {
	public User login(Connection con,User user)throws Exception{
		User resultUser=null;
		String sql="select*from t_user where userName=? and password=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, MD5Util.EncoderPwdByMd5(user.getPassword()));
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			resultUser=new User();
			resultUser.setUserId(rs.getInt("userId"));
			resultUser.setUserName(rs.getString("userName"));
			resultUser.setPassword(rs.getString("password"));
			resultUser.setNickName(rs.getString("nickName"));
			resultUser.setImageName(PropertiesUtil.getValue("imageFile")+rs.getString("imageName"));
			resultUser.setMood(rs.getString("mood"));
		}
		return resultUser;
	}
	
	public int userUpdate(Connection con,User user)throws Exception{
		String sql="update t_user set nickName=?,imageName=?,mood=? where userId=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, user.getNickName());
		pstmt.setString(2, user.getImageName());
		pstmt.setString(3, user.getMood());
		pstmt.setInt(4, user.getUserId());
		return pstmt.executeUpdate();
	}
}
