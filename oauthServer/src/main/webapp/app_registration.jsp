<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
		#list{
			border:1px solid blue;
		}
		#add{border:1px solid green;}
	</style>
	
</head>
<body>
	<b>Registration</b>
	
	<div id='list'>
		<table>
		<c:forEach items="appList" var='app'>
			<tr><td>ID</td><td>${app.id}</td></tr>
			<tr><td>CLIENT_ID</td><td>${app.client_id }</td></tr>
			<tr><td>CLIENT_SECRECT</td>${app.client_secrect}<td></td></tr>
			<tr><td>REDIRECTION_URI</td><td>${app.redirection_uri}</td></tr>
			<tr><td>AUTHORIZATION_URI</td><td>${app.authorization_uri }</td></tr>
			<tr><td>TOKEN_URI</td><td>${app.token_uri}</td></tr>
		</c:forEach>
			
		</table>
	</div>
	<div id='add'>
		<table>
			
		</table>
	</div>
	
</body>
</html>