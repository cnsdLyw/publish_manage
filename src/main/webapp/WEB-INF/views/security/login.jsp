<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.litc.common.util.Constant"%>
<c:set var="login_role" value="${sessionScope.loginRole }" />

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<head>
	<title>登陆</title>
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<script src="${ctx }/resources/script/js/jquery.1.9.1.min.js" type="text/javascript"></script>
	<script src="${ctx }/resources/script/bootstrap/js/bootstrap.min.js"  type="text/javascript" ></script>
	<script src="${ctx }/resources/script/bootstrap/js/bootbox.min.js" type="text/javascript"></script>
	
	<link href="${ctx }/resources/script/bootstrap/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx }/resources/script/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx }/resources/script/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx }/resources/script/bootstrap/css/templatemo_style.css" rel="stylesheet" type="text/css"/>
	<style type="text/css">
		html #body{
			//background-image:url('${ctx}/resources/images/login/6.jpg');
		}
	</style>
	<script type="text/javascript">
	function hideBlock(){
		$("#warning-block").hide();
	}
	<c:if test="${successCode==1||successCode==2}">
		setTimeout("javascript:hideBlock();", 3500)
	</c:if>
	</script>
</head>
<body id="body" class="templatemo-bg-gray"  >
	<c:if test="${errorCode==1 }">
		<div id="warning-block" class="alert alert-warning"><!--  alert-success  alert-warning -->
	  		 <a href="#" class="close" data-dismiss="alert">&times;</a>
	  		 <strong>登录失败！</strong>用户名或密码错误！ 
		</div>
	</c:if>
	
	<c:if test="${errorCode==2 }">
		<div id="warning-block" class="alert alert-warning"><!--  alert-success  alert-warning -->
	  		 <a href="#" class="close" data-dismiss="alert">&times;</a>
	  		 <strong>登录失败！</strong>登录用户没有角色权限设置！ 
		</div>
	</c:if>
	
	<c:if test="${errorCode==3 }">
		<div id="warning-block" class="alert alert-warning"><!--  alert-success  alert-warning -->
	  		 <a href="#" class="close" data-dismiss="alert">&times;</a>
	  		 <strong>登录失败！</strong>登录用户机构信息不正确！ 
		</div>
	</c:if>
	
	<c:if test="${errorCode==4 }">
		<div id="warning-block" class="alert alert-warning"><!--  alert-success  alert-warning -->
	  		 <a href="#" class="close" data-dismiss="alert">&times;</a>
	  		 <strong>登录失败！</strong>登录用户已暂停服务！ 
		</div>
	</c:if>
	
	<c:if test="${successCode==1 }">
		<div id="warning-block" class="alert alert-success"><!--  alert-success  alert-warning -->
	  		 <a href="#" class="close" data-dismiss="alert">&times;</a>
	  		 <strong>注册成功！</strong>暂时没有权限无法登录！ 
		</div>
	</c:if>
	
	<c:if test="${successCode==2 }">
		<div id="warning-block" class="alert alert-success"><!--  alert-success  alert-warning -->
	  		 <a href="#" class="close" data-dismiss="alert">&times;</a>
	  		 <!-- <strong>注册成功！</strong> -->退出登录！ 
		</div>
	</c:if>
	
	<div class="container" >
		<div class="col-md-12" style="top:100px;">
			<h1 class="margin-bottom-15" >欢&nbsp;迎&nbsp;访&nbsp;问</h1>
			<form id="loginForm" name="loginForm" class="form-horizontal templatemo-container templatemo-login-form-1 margin-bottom-30" role="form" 
				action="<c:url value="/j_spring_security_check" />" method="post">				
		        <div class="form-group">
		          <div class="col-xs-12">		            
		            <div class="control-wrapper">
		            	<label for="username" class="control-label fa-label"><i class="fa fa-user fa-medium"></i></label>
		            	<input type="text" class="form-control" id="j_username" name="j_username" placeholder="用户名"
		            		value="${not empty param.login_error ? sessionScope['SPRING_SECURITY_LAST_USERNAME'] : '' }" >
		            </div>		            	            
		          </div>              
		        </div>
		        <div class="form-group">
		          <div class="col-md-12">
		          	<div class="control-wrapper">
		            	<label for="password" class="control-label fa-label"><i class="fa fa-lock fa-medium"></i></label>
		            	<input type="password" class="form-control" id="j_password" name="j_password"placeholder="密码">
		            </div>
		          </div>
		        </div>
		        
		        <div class="form-group">
		          <div class="col-md-12">
		          	<div class="control-wrapper">
		          		<input type="submit" value="Log in" class="btn btn-info">
	          			<a href="forgot-password.html" class="text-right pull-right">Forgot password?</a>
		          	</div>
		          </div>
		        </div>
			        <div class="form-group">
			        	<div class="col-md-12">
			        		<label>Login with: </label>
			        		<div class="inline-block">
			        			<a href="#"><i class="fa fa-facebook-square login-with"></i></a>
				        		<a href="#"><i class="fa fa-twitter-square login-with"></i></a>
				        		<a href="#"><i class="fa fa-google-plus-square login-with"></i></a>
				        		<a href="#"><i class="fa fa-tumblr-square login-with"></i></a>
				        		<a href="#"><i class="fa fa-github-square login-with"></i></a>
			        		</div>		        		
			        	</div>
			        </div>
		      </form>
		       <div class="text-center">
		      	<a href="${ctx }/security/register" class="templatemo-create-new">创建新账户 <i class="fa fa-arrow-circle-o-right"></i></a>
		      	&nbsp;&nbsp;&nbsp;&nbsp;
		      	<a href="${ctx }/regSupplier" class="templatemo-create-new">供应商注册<i class="fa fa-arrow-circle-o-right"></i></a>
		      </div>
		</div>
	</div>
	
	<script src="${ctx}/resources/script/js/jquery-1.7.2.min.js" type="text/javascript"></script>
	<script src="${ctx}/resources/script/js/jquery.cookie.js" type="text/javascript"></script>
	<script src="${ctx }/resources/script/js/jquery.md5.js" type="text/javascript"></script>
	<script type="text/javascript">
			$(document).ready(function(){
			    var n = $.cookie('loginName');  
			    var p = $.cookie('passWord'); 
			    //if(n!=null) $('#j_username').val(n);  
			    //if(p!=null) $('#j_password').val(p);  
			    if(p!=null){  
			        $('#cookiePwd').attr('checked', true);  
			    }else{  
			    	$("#j_password").val("");
			        $('#cookiePwd').attr('checked', false);  
			    }  
			      
			    $('#cookiePwd').click(function(){  
			        if($(this).attr('checked')){  
			            $('#cookieInfo').attr('checked', true);  
			        }  
			    });  
			   delCookie("Cookie");
				
				// 表单校验
				$('#loginForm').submit(function(){
					/** 实现记住密码功能 */  
				    var $n = $('#j_username');  
				    var $p = $('#j_password');  
				   // alert("00");
				    var n = $n.val();  
				    var p = $p.val();  
				    
				    $.cookie("names",n);
				   // alert("11");
				    
					//if($('#cookiePwd').attr('checked')){  
					if($('#cookiePwd').is(':checked')) {
				        $.cookie('username', n, {expires:7});  
				        $.cookie('password', p, {expires:7});  
				    }else{  
				        $.cookie('username', null);  
				        $.cookie('password', null);  
				    }  
					
				    $('#j_username').val(n); 
				    //前台进行md5加密
				    $('#j_password').val($.md5(p)); 
				    $('#password').val($.md5(p));   //密码加密  
				    
                   
				    var p1 = document.forms[0].j_password;  
				    var p2 = document.forms[0].password;  
				    p2.value = $.md5(p1.value);  
				    
				});
			});
			//删除cookies
			function delCookie(name)
			{
			var exp = new Date();
			exp.setTime(exp.getTime() - 1);
			var cval=getCookie(name);
			if(cval!=null)
			document.cookie= name + "="+cval+";expires="+exp.toGMTString();
			}
			//获取Cookie
function getCookie(name)
    {    
        var offset,cookieValue;
        var search=name+"=";
        if(document.cookie.length>0)
        {
            offset=document.cookie.indexOf(search);
            if(offset!=-1)
            {
                offset += search.length;   
                end = document.cookie.indexOf(";", offset);   
                if (end == -1) 
                    end = document.cookie.length;   
                cookieValue=unescape(document.cookie.substring(offset, end));
            }
        }
        return cookieValue;
    } 
		</script>
</body>
</html>