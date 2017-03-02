<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<!-- 
	 * 				client_id:  must exists
	 * 				redirect_uri: 	if all pass , redirect user to redirectView finally.
	 * 				scope: 	(optional)
	 * 				state: (recommended)  
	 * 
	 * 				custom-defined:(user_id,username,password)

 -->
	<form action='${basePath}/oauthServer/ssa/authorize' method='post' enctype="application/x-www-form-urlencoded">
		<label>response_type</label><input readonly="readonly" name='response_type' value='code'/>
		<label>client_id</label><input name='client_id' /><br />
		<label>redirect_uri</label><input name='redirect_uri'/><br />
		<label>scope</label><input name='scope'/><br />
		<label>state</label><input name='state'/><br />
		<label>user_id</label><input name='user_id'/><br />
		<label>username</label><input name='username'/><br />
		<label>password</label><input name='password'/><br />
		<input type='submit' name='提交'/>
	</form>
</body>
</html>