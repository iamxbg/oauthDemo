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
		
		.registed{
			margin-top:10px;
			margin-left:5px;
		}
		
		.registed .label{ }
		.registed .val{}
	</style>
	
</head>
<body>
	<b>應用註冊頁面</b>
	
	<div id='list'>
	<div style='margin:20px 0px;'>已註冊應用:</div>
			<c:forEach items="${appList}" var='app' >
			<div>
			<table class='registed' style='border:1px solid red;'>
				<tr><td >應用名稱:</td><td >${app.name}</td></tr>
				<tr><td >應用描述:</td><td >${app.description }</td></tr>
				<tr><td >應用類型:</td><td >${app.response_type }</td></tr>
				<tr><td >應用ID:</td><td >${app.client_id }</td></tr>
				<tr><td >應用密碼:</td><td >${app.client_secrect}</td></tr>
				<tr><td >重定向地址:</td><td >${app.redirection_uri}</td></tr>
			</table>
			<button class='del' id='${app.id}'>刪除</button>
			</div>
			</c:forEach>
	</div>
	<div id='add' >
		<div>註冊新應用:</div>
		<form action='<%=basePath %>registration/add' method="post">
			<table>	
				<tr><td>應用名稱:</td><td><input name='name'/></td></tr>
				<tr><td>應用描述:</td><td><input name='description'/></td></tr>
				<tr><td>驗證方式:</td><td><select name='response_type'><option value='code'>授權碼</option><option value='token'>令牌</option></select></td></tr>
				<tr><td>應用ID:</td><td><input name='client_id'/></td></tr>
				<tr><td>應用密碼:</td><td><input type='password' name='client_secrect'/></td></tr>
				<tr><td>重定向地址:</td><td><input name='redirection_uri'/></td></tr>
				<tr><td><input type='submit' value='提交'/></td></tr>
			</table>
		</form>
	</div>
	
	
	<script type="text/javascript">
		$(function(){
			
			alert('run5')
			$('#list').on('click','.del',function(){
				
				var id=$(this).attr('id');
				alert('id:'+id)
				var form=document.createElement('form');
					form.enctype='multipart/form-data'
					form.method='post';
					form.action='<%=basePath%>registration/del/'+id;
					form.id='form'
					form.name='form'
					
					// must be append to body first!
					
					//document.body.append(form);
					document.body.appendChild(form);
					
					form.submit();
				
			})
			
		})
	</script>
	
</body>
</html>