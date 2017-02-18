<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
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
	
	
</head>
<body>
	<div style='border:1px solid red;'>
		<table>
			<c:forEach items="${userList }" var='u'>
				<tr><td>username</td><td>${u.username }</td></tr>
				<tr><td>real_name</td><td>${u.real_name }</td></tr>
			</c:forEach>
		</table>
	</div>
	<div style='border:1px solid blue'>
		<form action='<%=basePath%>user/add' method='post'>
		<table>
			<tr><td>username</td><td><input name='username' /></td></tr>
			<tr><td>real_name</td><td><input name='real_name'/></td></tr>
			<tr><td>password</td><td><input name='password' type='password'/></td></tr>
			<tr><input type='submit' value='提交'/></tr>
		</table>
	
		</form>
	</div>
</body>
</html>