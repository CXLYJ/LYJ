<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>

<style type="text/css">
body, ul, li, ol,form,img,a{margin:0; padding:0; border:0; list-style:none;}
	/*banner*/
.banner{
 	width:870px;
 	height:300px;
 	margin:13px auto 15px auto;
 	position:relative;
 	overflow: hidden;
 	padding-top:10px;
 }
 .banner .banner_pic .pic{display:none;}
 .banner .banner_pic .current{display:block;}
 .banner ol{
 	position:absolute;
 	left:400px;
 	bottom:26px;
 }
 .banner ol .but{
 	float:left;
 	width:10px;
 	height:20px;
 	border:1px solid #FF7101;
 	margin-right:12px;
 	text-align:center;
 	line-height:22px;
 	background:#fff;
 	color:#FF7101;
 	font-size:14px;
 	font-weight:bold;
 }
 .banner ol li{cursor:pointer;}
 .banner ol .current{
 	color:#fff;
 	background:#FF7101;
 	float:left;
 	width:10px;
 	height:20px;
 	border:1px solid #FF7101;
 	margin-right:12px;
 	text-align:center;
 	line-height:22px;
 	font-size:14px;
 	font-weight:bold;
 }
/*banner*/
</style>

<!--nav end-->
<!--banner begin-->
		<div class="banner">
			<div class="banner_pic" id="banner_pic">
				<div class="current"><a href="main?all=true"><img src="images/jss01.jpg" alt="" /></a></div>
				<div class="pic"><a href="diary?action=preSave"><img src="images/jss02.jpg" alt="" /></a></div>
				<div class="pic"><a href="diaryType?action=list"><img src="images/js03.jpg" alt="" /></a></div>
				<div class="pic"><a href="user?action=preSave"><img src="images/jss04.jpg" alt="" /></a></div>
			</div>
			<ol id="button">
				<li class="current">1</li>
				<li class="but">2</li>
				<li class="but">3</li>
				<li class="but">4</li>
			</ol>
		</div>
<!--banner end-->
<!--stages begin-->

<font color="red" ><strong>北京时间：</strong></font><font color="#FF00FF"></font><div id="time" style="color: #0000FF; margin-bottom: 20px; text-indent: 6em;">
	<script >setInterval("time.innerHTML=new Date().toLocaleString()+'&nbsp;&nbsp;&nbsp;星期'+'日一二三四五六'.charAt(new Date().getDay());",1000)</script>
	</div>
	
<div class="data_list">
		<div class="data_list_title">
		<img src="${pageContext.request.contextPath}/images/list_icon.png"/>
		日记列表</div>
		<div class="diary_datas">
			<ul>
				<c:forEach var="diary" items="${diaryList }">
					<li>『<fmt:formatDate value="${diary.releaseDate }" type="date" pattern="yyyy-MM-dd"/>』<span>&nbsp;<a href="diary?action=show&diaryId=${diary.diaryId }"><font color="#FF00FF">${diary.title }</font></a></span></li>
				</c:forEach>
			</ul>
		</div>
		<div class="pagination pagination-centered">
			<ul>
				<li>${pageCode }<br /></li>
	
				<li><p align="center" style="padding-top: 15px;"> LYJ 版权所有  </p></li>
			</ul>
		</div>
</div>
