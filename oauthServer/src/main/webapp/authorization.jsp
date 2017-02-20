<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	<b></b>
		
		<!-- Use username and password to validate -->
		<div>
			<div>
				<form method='post' action='<%=basePath%>oauth/authorize'>
					<table>
						<tr><td>用戶名</td><td><input name='username'/></td></tr>
						<tr><td>密碼</td><td><input type='password' name='password'/></td></tr>
						<tr> 
							<td>
							<label>是否同意OAuthClient使用你的信息</label>
							<input type='checkbox'/>
						 	<a >用戶協議</a>
						 	</td>
						 </tr>
						<tr><td><input type='submit' value='提交'/></td></tr>
						
					</table>
					<input name='response_type' value='code'/>
					<input  name='client_id' value='${requestScope.client_id}'/>
					<input  name='client_secrect' value='${requestScope.client_secrect}'/>
				</form> 
			</div>
			
		</div>
</body>
</html>