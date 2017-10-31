<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>个人日记本主页</title>
<link href="${pageContext.request.contextPath}/style/diary.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/bootstrap/js/jQuery.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/ckeditor/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/javascript/index.js"></script>
<style type="text/css">
	  body {
        padding-top: 60px;
        padding-bottom: 40px;
      }
      .brand{
      	margin-top: 20px;	
      	padding-top: 20px;
      }
      .active{
      	padding-top: 20px;
      }
      
.stages{ 
	margin:0px auto; 
	padding:0px; 
	border:1px solid #c4c4c4; 
	height:30px; 
	width:998px; 
	line-height:30px; 
	vertical-align:middle; 
	text-align:left; 
	overflow:hidden;
	margin-top: 80px;
}
.stages_title{ 
	width:96px; 
	text-align:center; 
	border-right:1px solid #c4c4c4; 
	float:left;
}
.stages_con{ 
	width:900px; 
	float:left;
}
</style>
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top"> 			<!-- //加黑 ，固定在顶部 -->
      <div class="navbar-inner">				<!-- //包含内部东西 -->
        <div class="container">					<!-- //包含容器里面放东西 -->
          <a class="brand" href="main?all=true">屌丝的日记本</a>		<!-- 标题  -->
          <div class="nav-collapse collapse"> 				<!-- //触发展开导航 -->
            <ul class="nav">									<!-- //导航标签 -->
              <li class="active"><a href="main?all=true"><i class="icon-home"></i>&nbsp;主页</a></li>
              <li class="active"><a href="diary?action=preSave"><i class="icon-pencil"></i>&nbsp;写日记</a></li>
              <li class="active"><a href="diaryType?action=list"><i class="icon-book"></i>&nbsp;日记分类管理</a></li>
              <li class="active"><a href="user?action=preSave"><i class="icon-user"></i>&nbsp;个人中心</a></li>
            </ul>
         <form name="myForm" class="navbar-form pull-right" method="post" action="main?all=true">
	  		<input class="span2" id="s_title" name="s_title"  type="text" style="margin-top:5px;height:30px;" placeholder="往事如烟...">
	 		<button type="submit" class="btn" onkeydown="if(event.keyCode==13) myForm.submit()"><i class="icon-search"></i>&nbsp;搜索日志</button>
		</form>
          </div>
      </div>
   </div>
</div>
     
   	<div class="stages">
		<div class="stages_title">友情分享</div>
			<div class="stages_con">
			<marquee direction="left" onMouseOver="this.stop()" onMouseOut="this.start()" ><a href="main?action=true"><font color="green">欢迎来到LYJ日记本世界</font></a></marquee>
		</div>
	</div>
     
<div class="container">
   	<div class="row-fluid">
   	
   		<div class="span9">
   			<jsp:include page="${mainPage }"></jsp:include>
   		</div>
   		
   		<div class="span3">
   			<div class="data_list">
				<div class="data_list_title">
					<img src="${pageContext.request.contextPath}/images/user_icon.png"/>
					个人中心
				</div>
				<div class="user_image">
					<img  src="${currentUser.imageName }">
				</div>
				<div class="nickName">${currentUser.nickName }</div>
				<div class="userSign">(${currentUser.mood })</div>
			</div>
			
			
			<div class="data_list">
				<div class="data_list_title">
					<img src="${pageContext.request.contextPath}/images/byType_icon.png"/>
					按日志类别
				</div>
				<div class="datas">
					<ul>
						<c:forEach var="diaryTypeCount" items="${diaryTypeCountList }">
							<li><span><a href="main?s_typeId=${diaryTypeCount.diaryTypeId }"><font color="#0000FF">${diaryTypeCount.typeName } [ ${diaryTypeCount.diaryCount } ]</font></a></span></li>
						</c:forEach>
					</ul>
				</div>
			</div>
			
			
			<div class="data_list">
				<div class="data_list_title">
					<img src="${pageContext.request.contextPath}/images/byDate_icon.png"/>
					按日志日期
				</div>
				<div class="datas">
					<ul>
						<c:forEach var="diaryCount" items="${diaryCountList }">
							<li><span><a href="main?s_releaseDateStr=${diaryCount.releaseDateStr }"><font color="#00ff00">${diaryCount.releaseDateStr } [ ${diaryCount.diaryCount } ]</font></a></span></li>
						</c:forEach>
					</ul>
				</div>
			</div>
			
	
			<div class="data_list">
				<div class="data_list_title">
					<img src=" ${pageContext.request.contextPath}/images/list_icon.png"/>
					友情链接
				</div>
				<div class="datas">
					<ul>
					
							<li><span><a href="http://www.easyicon.net/" target="_blank">小图标下载</a></span></li>
						
							<li><span><a href="http://tieba.baidu.com/f?kw=java" target="_blank">Java贴吧</a></span></li>
						
							<li><span><a href="http://www.uugai.com/" target="_blank">免费logo在线制作</a></span></li>
						
					</ul>
				</div>
			</div>
			
		
		  </div>  
   	 </div>	
</div>
</body>
</html>