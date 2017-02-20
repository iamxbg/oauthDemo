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
<body >
	<b>OAuthClient</b>
	
	<button id='openFunction'>
		開通OAuthClient應用的功能
	</button>
	
	<script type="text/javascript">
	
	
		
	
		$(function(){
			$('#openFunction').on('click',function(){
				if(confirm('需要使用用戶在OAuthServer中的測量信息,是否繼續?')){
						$.ajax({
							url:'<%=basePath%>oauth/toAuthzView',
							type:'get',
							dataType:'json',
							success:function(data){
								
								for(var d in data)
									console.log(d+' --- '+data[d]);
								
								var form=document.createElement('form');
									
								var clientId=document.createElement('input');
									clientId.type='hidden';
									clientId.name='client_id';
									clientId.value=data.client_id;
									
								var redirectUri=document.createElement('input');
									redirectUri.type='hidden';
									redirectUri.name='redirectUri';
									redirectUri.value=data.redirect_uri;
								
								var state=document.createElement('input');
									state.type='hidden';
									state.name='state';
									state.value=data.state;
									
								var scope=document.createElement('input');
									scope.type='hidden';
									scope.name='scope';
									scope.value=data.scope;
									
									
								var responseType=document.createElement('input');
									responseType.type='hidden';
									responseType.name='response_type';
									responseType.value=data.response_type;
	
								alert('#'+data.authz_endpoint)
									
								form.appendChild(clientId);
								form.appendChild(redirectUri);
								form.appendChild(state);
								form.appendChild(scope);
								form.appendChild(responseType);
								
								form.action=data.authz_endpoint;
								
								document.body.appendChild(form);
								
								
								
								form.submit();
								

							}
							
						})
				}else{
					
				}
				
			})
			
		})
	</script>
</body>
</html>