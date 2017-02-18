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
			<c:forEach items="${appList}" var='app' >
			<table style='border:1px solid red;'>
				<tr><td>APP_NAME</td><td>${app.app_name}</td></tr>
				<tr><td>CLIENT_ID</td><td>${app.client_id }</td></tr>
				<tr><td>CLIENT_SECRECT</td><td>${app.client_secrect}</td></tr>
				<tr><td>REDIRECTION_ENDPOINT</td><td>${app.redirection_endpoint}</td></tr>
				<tr><td>AUTHORIZATION_ENDPOINT</td><td>${app.authorization_endpoint }</td></tr>
				<tr><td>TOKEN_ENDPOINT</td><td>${app.token_endpoint}</td></tr>
			</table>
			</c:forEach>
	</div>
	<div id='add'>
	<form action='<%=basePath %>admin/doRegistration' method="post">
		<table>	
			<tr><td>APP名稱</td><td><input name='app_name'/></td></tr>
			<tr><td>Client_id</td><td><input name='client_id'/></td></tr>
			<tr><td>Client_secrect</td><td><input type='password' name='client_secrect'/></td></tr>
			<tr><td>REDIRECTION_ENDPOINT</td><td><input name='redirection_endpoint'/></td></tr>
			<tr><td><input type='submit' value='提交'/></td></tr>
		</table>
		</form>
	</div>
	
</body>
</html>