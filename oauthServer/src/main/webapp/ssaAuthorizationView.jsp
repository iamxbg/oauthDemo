<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html >
<html >
<head>


<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<title>Insert title here</title>

	<style>
		*{
			margin:0px auto; padding:0px;
		}
		
		#app_infos{
			width:100%;height:190px;
			background:skyblue;
			margin-top:30px;
		}
		
				#app_profile{
					border:3px solid red;
					width:80px;
					height:80px;
					top:20px;
				}
				
				#app_name{
					border:1px solid black;
					width:180px;
					text-align:center;
					line-height:30px;
					margin-top:10px;
					letter-spacing:6px;
					font-size:20px;
					font-weight:bold;
				}
		#scopes{
			width:100%;height:200px;
			background:lightgreen;
		}
			#tip{
				margin-left:100px;
				line-height:70px;
			}
			
			#scopes ul{
				margin-left:220px;
			}
			
			#actions{
				width:100%;height:120px;
				background:red;
			}
		
			#aggree{
				width:200px;
				height:60px;
				border:1px solid black;
				background:green;
				text-align:center;
				line-height:60px;
				margin-top:10px;
				letter-spacing:4px;
			}
		
	</style>
	
</head>
<body>
	
	<div>
		<div id='app_infos'>
			<div id='app_profile'>
				<IMG alt="" src="" />
			</div>
			<div id='app_name'>
				<SPAN>春雨医生</SPAN>
			</div>
		</div>

		<div id='scopes'>
			<span id='tip'>使用XXX登陆后该应用将获得以下权限:</span>
			<ul>
				<li><span>获得你的公开资料</span></li>
				<li><span>使用您的医疗健康数据</span></li>
			</ul>
		</div>

		<div id='actions'>
			<div id='aggree'>
				<span>授权并登陆</span>
			</div>
		</div>
	</div>
	 
	 
	
</body>
</html>