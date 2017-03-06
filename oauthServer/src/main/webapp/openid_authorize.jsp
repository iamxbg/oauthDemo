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
	
	<style>
		ul{
			list-style:none;
		}
		li lable{
			width:140px;
		}
		.label{
			width:140px;
			font-weight:bold;
			}
			
		#loginBox{
			border:1px solid blue;
			width:400px;
			
		}
	</style>
	
</head>
<body>
	<div>
		<center>
		<div id='loginBox' >
		
			<form method='POST' action='<%=basePath%>openid/authorize'>
				<ul>
					<li><div class='label'>&nbsp;用&nbsp;戶&nbsp;名</div><input name='username'/></li>
					<li><div class='label'>&nbsp;密&nbsp;&nbsp;碼</div><input type='password' name='password'/></li>
					<li>
						<div ><span>授權模式</span></div>
						
						<select id='auth_type' name='auth_type'>
							<option value='forever'>永久統一</option>
							<option value='onetime'>僅一次</option>
							<option value='no'>不同意</option>
						</select>
						
					</li>
					<li>
						<input  id='submit'  type='submit' value='登陸'/>
					</li>
				</ul>
			</form>
		
		</div>
		</center>
	</div>
	
	<script type="text/javascript">
		$(function(){
			
			$('#auth_type').on('change',function(){
				//alert($(this).val())
				if($(this).val()=='no'){
					$('#submit').attr('disabled','disabled');
				}else{
					$('#submit').removeAttr('disabled');
				}
			})
			
			
		})
	</script>
</body>
</html>