<?xml version="1.0" encoding="utf-8" ?>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Insert title here</title>
</head>
<body>
	
	<div>ssaAuthView</div>
	
	
	<form action="<%=basePath%>ssa/authorize" enctype="application/x-www-form-urlencoded" method="post" >
		<div>
			<ul>
				<li>
					<div>tel</div>
					<div><input name='account' type='text'/></div>
				</li>
				<li>
					<div>password</div>
					<div><input name='password' type='password'/></div>
				</li>
				<li>
					<div><input type='submit' value='submit'/></div>
				</li>
				<li>
					<input type="hidden" name='client_id' value="${client_id }"/>
					<input type="hidden" name='service_id' value='${service_id }'/>
					<input type="hidden" name='user_id' value='${user_id}'/>
					<input type="hidden" name='state' value='${state }'/>
					<input type="hidden" name='redirect_uri' value='${redirect_uri}'/>
				</li>
				<li>
					<div>error:</div>
					<div>${error_description}</div>
				</li>
			</ul>
		</div>
		
	</form>
	
	<div>${requestScope.client_id}</div>

</body>
</html>