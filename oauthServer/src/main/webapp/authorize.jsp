<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String httpsPath= "https://"+request.getServerName()+":8443"+path+"/";
%>
<!DOCTYPE html>
<html lang='en'>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<!-- bootstrap css -->
	<link  rel="stylesheet" style="text/css" href="<%=basePath%>css/bootstrap-theme.min.css"/>
	<link  rel="stylesheet" style="text/css" href="<%=basePath%>css/bootstrap.min.css"/>
	<!-- insert js  -->
	<script type="text/javascript" src='<%=basePath%>js/angular.min.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/sockjs.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/jquery.min.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/bootstrap.min.js'></script>
	<script type="text/javascript" src='<%=basePath%>js/stomp.js'></script>
	<script type='text/javascript' src='<%=basePath%>js/echarts/echarts.js'></script>
	
	<style>
		
		#body{
			background:lightgray;
		}
		#client_area{
			background:skyblue;
			width:90%;
			margin-left:5%;
			margin-bottom:8px;
			flaot:left;
			height:180px;
		}
		
		#client_profile{
			width:20%;
			float:left;
			height:170px;
			margin-top:5px;
			background:lightgreen;
		}
		#client_info{
			width:70%;
			float:left;
			height:170px;
			margin-top:5px;
			background:lightgray;
		}
		
		#client_name{
			font-size:26px;
			font-weight:bold;
			letter-spacing:6px;
			text-indent:1em;
		}
		#client_description{
			font-size:18px;
			letter-spacing:3px;
			text-indent:2em;
		}
		
		#scopes{
			border:1px solid red;
			width:90%;
			height:200px;
			margin-left:5%;
			float:left;
		}
		#authorize_btns{
			border:1px solid blue;
			margin-top:4px;
			width:90%;
			height:40px;
			margin-left:5%;
			float:left;
			
		}
		
		#confirm{
			width:140px;
			height:30px;
			border:1px solid red;
			float:right;
			letter-spacing: 8px;
			margin-right:30px;
			text-align:center;
			line-height:28px;
			margin-top:4px;
		}
		
		#cancel{
			width:140px;
			height:30px;
			border:1px solid red;
			float:right;
			letter-spacing: 8px;
			margin-right:40px;
			text-align:center;
			line-height:28px;
			margin-top:4px;
		}
		
		#error_description{
			width:90%;
			height:44px;
			border:1px solid lime;
			margin-top:4px;
			margin-left:5%;
			float:left;
		}
	</style>
	
</head>
<body>
	<div id='header'>
		<div id='service_icon'></div>
	</div>
	<div id='center'>
		<div id='client_area'>
			<div id='client_profile'></div>
			<div id='client_info'>
				<div id='client_name'><span>春雨醫生</span></div>
				<div id='client_description'><span>您的健康護理</span></div>
			</div>
		</div>
		<div id='scopes'>
			<ul >
				<li><img /><span>獲取個人信息，好友關係</span></li>
				<li><img /><span>分享內容到你的微博</span></li>
				<li><img /><span>獲得你的評論</span></li>
			</ul>
		</div>
		<div id='authorize_btns'>
			<div id='cancel'><span id='cancel_authorize'>取消</span></div>
			<div id='confirm'><span id='do_authorize'>授權</span></div>
			
		</div>
		<div id='error_description'></div>
	</div>
	<div id='comment'>
	</div>
	
	<script>
		$(function(){
			alert('測試中');
			
			$('#confirm').on({
				'mouseenter':function(){
					$(this).css('background','skyblue');
					
				},
				'mouseleave':function(){
					$(this).css('background','white');
				},
				'click':function(){
					alert('用戶同意!');
					
					alert('url: <%=httpsPath%>authorize.jsp')

					$.ajax({
						url:<%=%>
						
					})
				}
				
			})
			
		})
	</script>
</body>
</html>