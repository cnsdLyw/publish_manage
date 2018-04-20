<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<head>
	<title>Create Account</title>
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="${ctx }/resources/script/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
	<link href="${ctx }/resources/script/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css">
	<link href="${ctx }/resources/script/bootstrap/css/templatemo_style.css" rel="stylesheet" type="text/css">
	
	<script src="${ctx }/resources/script/js/jquery.1.9.1.min.js"  type="text/javascript" ></script>
	<script src="${ctx }/resources/script/bootstrap/js/bootstrap.min.js"  type="text/javascript" ></script>
	<script src="${ctx }/resources/script/js/validate/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/js/validate/additional-methods.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/js/bootbox.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/js/validate/myValidate.js" type="text/javascript"></script>
    <script type="text/javascript">
   		  $(document).ready(function(){
				setValidateForm();
			});
			
			// 绑定校验格式
			function setValidateForm(selector) {
				$(".validate-form").validate({
					/*onfocusout: function(element){
				        $(element).valid();
				    },*/
					errorElement : "span",
					errorClass : "help-block has-error",
					errorPlacement : function(e, t) {
						return t.parents(".controls").first().append(e);
					},
					highlight : function(e) {
						return $(e).closest('.form-group').removeClass("has-error has-success")
								.addClass('has-error');
					},
					success : function(e) {
						return e.closest(".form-group").removeClass("has-error");
					},
					submitHandler : function(form) {
						if($("#agree").is(":checked")==false){
							bootbox.alert("您还没有同意服务条款和隐私政策!", function() {});
							$("#warning-block").show();
							return false;
						}else{
							$.ajax({//验证登录名
							      type : "get",  
							      url : "${ctx}/user/isLoginNameExist/?loginName="+$("#loginName").val(),  
							      dataType:"json",  
							      success : function(isExist) {
							      	if(!isExist){
							      		$.ajax({//验证邮箱
										      type : "get",  
										      url : "${ctx}/user/isEmailExist/?email="+$("#email").val(),  
										      dataType:"json",  
										      success : function(isExist) {
										      	if(!isExist){
										      		form.submit();
										      	}else{
										      		bootbox.alert("邮箱已存在，请重新输入!", function() {});
										      		return false;
										      	}
										      }  
										  });
							      	}else{
							      		bootbox.alert("用户名已存在，请重新输入!", function() {});
							      		return false;
							      	}
							      }  
							  });
						}
						//form.submit();
					}
				});
			};
			
			
    </script>
</head>
<body class="templatemo-bg-gray">
	<div id="warning-block" class="alert alert-warning" style="display: none;"><!--  alert-success  alert-warning -->
  		 <a href="#" class="close" data-dismiss="alert">&times;</a>
  		 <strong>无法提交！</strong>您还没有同意服务条款和隐私政策！
	</div>
	<h1 class="margin-bottom-15">Create Account</h1> <!-- <input type="button" value="SSS" onclick="test()">  -->
	<div class="container">
		<div class="col-md-12">			
			<form class="form-horizontal templatemo-create-account templatemo-container validate-form"  
				action="${pageContext.request.contextPath}/security/add" method="post">
				<div class="form-inner">
			        <div class="form-group">
			          <div class="col-md-6 controls" style="height:70px;">		          	
			            <label for="username" class="control-label">邮箱   </label>	
			            	<input type="email" class="form-control" id="email" name="email" data-rule-email='true' data-rule-required='true' data-rule-minlength='6' data-rule-maxlength='30' placeholder="email">
			                     		            		            
			          </div>
			          <div class="col-md-6">
			          	&nbsp;
			          </div>   
			        </div>			
			        <div class="form-group">
			          <div class="col-md-6 controls" style="height:70px;">		          	
			            <label for="username" class="control-label">登录名</label>
			            <input type="text" class="form-control" id="loginName" name="loginName" chrnum="true" data-rule-required='true' data-rule-minlength='6' data-rule-maxlength='20' placeholder="loginName" />		            		            		            
			          </div>
			          <div class="col-md-6">
			          	&nbsp;
			          </div>             
			        </div>
			        <div class="form-group">
			          <div class="col-md-6 controls" style="height:70px;">	
			            <label for="password" class="control-label">密码</label>
			            <input type="password" class="form-control" id="passWord" name="passWord" chrnum="true" data-rule-required='true' data-rule-minlength='6' data-rule-maxlength='20' placeholder="password" />
			          </div>
			          <div class="col-md-6 controls" style="height:70px;">	
			            <label for="password" class="control-label">在输入一次</label>
			            <input type="password" class="form-control" id="password_confirm" name="password_confirm" data-rule-equalto='#passWord' chrnum="true" data-rule-required='true' data-rule-minlength='6' data-rule-maxlength='20' placeholder="password" />
			          </div>
			        </div>
			        <div class="form-group">
			          <div class="col-md-12">
			            <label>
			            	<input type="checkbox" id="agree" name="agree" value="1">我同意 <a href="javascript:;" data-toggle="modal" data-target="#templatemo_modal">服务条款</a> 
			            	和<a href="javascript:;" data-toggle="modal" data-target="#PrivacyPolicy_modal">隐私政策。</a>
			            </label>
			          </div>
			        </div>
			        <div class="form-group">
			          <div class="col-md-12">
			            <input type="submit" value="Create account" class="btn btn-info">
			            <a href="${ctx }/authority/login" class="pull-right">Login</a>
			          </div>
			        </div>	
				</div>				    	
		      </form>		      
		</div>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="templatemo_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title" id="myModalLabel">服务条款</h4>
	      </div>
	      <div class="modal-body">
	      	<p>This form is provided by <a rel="nofollow" href="#">Free HTML5 Templates</a> that can be used for your websites. Cras mattis consectetur purus sit amet fermentum. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Morbi leo risus, porta ac consectetur ac, vestibulum at eros.</p>
	        <p>Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Vivamus sagittis lacus vel augue laoreet rutrum faucibus dolor auctor. Aenean lacinia bibendum nulla sed consectetur. Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Donec sed odio dui. Donec ullamcorper nulla non metus auctor fringilla. Cras mattis consectetur purus sit amet fermentum. Cras justo odio, dapibus ac facilisis in, egestas eget quam.</p>
	        <p>Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Vivamus sagittis lacus vel augue laoreet rutrum faucibus dolor auctor.</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="PrivacyPolicy_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title" id="myModalLabel">隐私政策</h4>
	      </div>
	      <div class="modal-body">
	      	<p>This form is provided by <a rel="nofollow" href="#">Free HTML5 Templates</a> that can be used for your websites. Cras mattis consectetur purus sit amet fermentum. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Morbi leo risus, porta ac consectetur ac, vestibulum at eros.</p>
	        <p>Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Vivamus sagittis lacus vel augue laoreet rutrum faucibus dolor auctor. Aenean lacinia bibendum nulla sed consectetur. Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Donec sed odio dui. Donec ullamcorper nulla non metus auctor fringilla. Cras mattis consectetur purus sit amet fermentum. Cras justo odio, dapibus ac facilisis in, egestas eget quam.</p>
	        <p>Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Vivamus sagittis lacus vel augue laoreet rutrum faucibus dolor auctor.</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	      </div>
	    </div>
	  </div>
	</div>
	
</body>
</html>