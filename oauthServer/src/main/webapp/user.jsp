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
	<div><b>用戶管理介面</b></div>
	<div id='userList' style='border:1px solid red;'>
		
		<div>用戶列表</div>
		<c:forEach items="${userList }" var='u'>
		<table>		
				<tr><td>真實姓名:</td><td>${u.name}</td></tr>
				<tr><td>用戶名:</td><td>${u.username }</td></tr>
				<tr><td>密碼:</td><td>${u.password}</td></tr>
				<tr><td><button class='del' id="${u.id}">刪除</button></td></tr>
		</table>
		</c:forEach>
	</div>
	<div style='border:1px solid blue'>
		<div>新建用戶:</div>
		<form action='<%=basePath%>user/add' method='post'>
		<table>
			<tr><td>真實姓名:</td><td><input name='name' /></td></tr>
			<tr><td>用戶名:</td><td><input name='username'/></td></tr>
			<tr><td>密碼:</td><td><input name='password' type='password'/></td></tr>
			<tr><td><input type='submit' value='提交'/></td></tr>
		</table>
	
		</form>
	</div>
	
	<script type="text/javascript">
	
	$(function(){
		$('#userList').on('click','.del',function(){
			var id=$(this).attr('id');
			
			var form=document.createElement('form');
				form.action='<%=basePath%>user/del/'+id;
				form.method='post';
				document.body.appendChild(form);
				form.submit();
			
		})
		
	})

	</script>
</body>
</html>