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
	<b>用戶簽名同意頁面</b>
		
		<div>
			<div><span style='font-weight:border;'>使用用戶名和密碼驗證的情況</span></div>
			<div>
				<form action='<%=basePath%>oauth/tag'>
					<table>
						<tr><td>用戶名</td><td><input name='username'/></td></tr>
						<tr><td>密碼</td><td><input type='password' name='password'/></td></tr>
						<tr> <input type='checkbox'/> <a >用戶協議</a></tr>
						<tr><td><input type='submit' value='提交'/></td></tr>
					</table>
				</form>
			</div>
			
		</div>
</body>
</html>