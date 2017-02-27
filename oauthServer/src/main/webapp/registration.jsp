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
		*{
			margin:auto 0px;
			padding:0px;
		}

		#list{border:1px solid blue;}
		#list table{ width:96%;border:1px solid blue;} 
		#list table tr{line-height:26px;}
		#list th{font-size: 14px;}
		#list th span{float:left;margin-left:50px;}
		#list td{font-size:14px;font-weight: bold;}
		
		#registration{border:1px solid lime;}
		#registration table{width:96%;}
		#registration table tr{line-height:28px;}
		#registration th{font-size:14px;}
		#registration th span{float:left; margin-left:50px;letter-spacing: 4px;}
		#registration td{font-size:14px;font-weight: bold;}
		
	</style>
	
</head>
<body ng-app='app'>
	<b>應用註冊頁面</b>
	
	<div id='list'>
	<span style='margin:20px 0px;'>已註冊應用:</span>
		<table  >
			<colgroup>
				<col width='30%;'/>
				<col width='70%;'/>
			</colgroup>
			<c:forEach items="${appList}" var='app' >
			<tr><th><span>名稱</span></th><td><input name='name'/>${app.name}</td></tr>
			<tr><th><span>描述</span></th><td>${app.description }</td></tr>
			<tr><th><span>應用ID</span></th><td>${app.client_id }</td></tr>
			<tr><th><span>應用密文</span></th><td><input name='client_secrect'>${app.client_secrect}</td></tr>
			<tr><th><span>接受驗證的URI</span></th><td><input name='receive_authz_code_uri'/>${app.receive_authz_code_uri}</td></tr>
			<tr><th><span>接受TOKEN的URI</span></th><td><input name='receive_token_uri'/>${app.receive_token_uri}</td></tr>
			<!-- <tr><th><span>REDIRECT_URI</span></th><td><input name='redirection_uri'/>${app.redirection_uri}</td></tr>  -->
			<tr><th><span>IS_CLIENT_AUTHZ_OPEN</span></th><td>${app.is_server_auth_enabled }</td></tr>
			<tr><th><span>IS_CLIENT_AUTHZ_OPEN</span></th><td>${app.is_client_auth_enabled}</td></tr>
			<tr><th><span>可用狀態</span></th><td>${app.del_flag}</td></tr>
			</c:forEach>
			</table>
			
	</div>
	<div id='registration' >
		<span>註冊新第三方應用</span>
		<!-- <form action='<%=basePath %>registration/add' method="post">  -->
			<table id='registration'>	
				<colgroup>
					<col width='30%'/>
					<col width='70%'/>
				</colgroup>
				<tr><th><span>名稱</span></th><td><input name='name'/></td></tr>
				<tr><th><span>描述</span></th><td><input name='description'/></td></tr>
				<tr><th><span>應用ID</span></th><td><input name='client_id'/></td></tr>
				<tr><th><span>應用密文</span></th><td><input type='password' name='client_secrect'/></td></tr>
				<tr><th><span>第三方應用接收驗證票據的URI</span></th><td><input name='redirection_uri'/></td></tr>
				<tr><th><span>第三方應用接收token的URI</span></th><td><input name='redirection_uri'/></td></tr>

				<tr><th><input type='submit' value='提交'/></td></tr>
			</table>
		<!-- </form>  -->
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
		
		
		var app=angular.module('app',[]);
		
		app.
		
		//add client
		
		//update client
		
		//enable client
		
		//disable client
		
		//open client-auth-flow
		
		//close client-auth-flow
		
		//open server-auth-flow
		
		//close server-auth-flow
		
		
	</script>
	
</body>
</html>