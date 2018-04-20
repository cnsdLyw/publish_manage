<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>权限编辑</title>

<meta name="description" content="overview &amp; stats" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<%@include file="../../common/meta.html"%>
	<script src="${ctx }/resources/script/js/validate/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/js/validate/myValidate.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/js/jquery.md5.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/js/bootbox.min.js" type="text/javascript"></script>
<style type="text/css">
</style>
<script type="text/javascript">
		$(document).ready(function(){
				setValidateForm();
		});
		
		// 绑定校验格式
		function setValidateForm(selector) {
			$(".validate-form").validate({
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
					$.ajax({//验证密码
						      type : "get",  
						      url : "${ctx}/user/checkPassword/?id="+$("#id").val()+"&oldPassWord="+$.md5($("#oldPassWord").val()),  
						      dataType:"json",  
						      success : function(isRight) {
						      	if(isRight){
						      		//校验原密码和新密码是否相同
									var oldpwd = $("#oldPassWord").val();
									var newpwd = $("#passWord").val();
									if(oldpwd!=""&&newpwd!=""){
									    if(oldpwd==newpwd){
									      bootbox.alert("新密码和原密码相同!", function() {});
									      return false;
									    }
									}
						      		form.submit();
						      	}else{
						      		bootbox.alert("原始密码不正确!", function() {});
						      		return false;
						      	}
						      }  
					});
					
					
					
				}
			});
		};
		
</script>
</head>

<body class="no-skin">
	<%@include file="../../common/top.jsp"%>
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<%@include file="../../common/left.jsp"%>
		<div class="main-content">
			<div class="main-content-inner">
				<!-- #section:basics/content.breadcrumbs -->
				<div class="breadcrumbs" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="${ctx}/index">首页</a>
						</li>
						<li> <a href="javascript:void(0);">系统管理</a>
						</li>
						<li class="active">修改用户登录密码</li>
					</ul>
					
				</div>
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="col-md-12">
						<form:form class="form-horizontal templatemo-create-account templatemo-container validate-form" method="POST"
							commandName="user" action="${ctx}/user/saveUserPassword.html">
							<input type="hidden" id="id" name="id" value="${user.id }"/>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 登录名： </label>
										<div class="col-sm-10">
											<div class="col-md-6">
												<input type="text" class="col-sm-10" id="name" name="name" value="${user.loginName }" readonly="readonly">
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 姓名： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">
									            <input type="text" class="col-sm-10" id="name" name="name" value="${user.name }" readonly="readonly">
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 姓别： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									            	<select class="col-sm-4" id="sex" name="sex" title="性别" disabled="disabled">
														<option value="男" <c:if test="${user.sex=='男' }"> selected</c:if> >男</option>
														<option value="女" <c:if test="${user.sex=='女' }"> selected</c:if> >女</option>
													</select>
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 原始密码： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									           <input type="password" class="col-sm-10" id="oldPassWord" name="oldPassWord"  data-rule-required='true' data-rule-maxlength='20' placeholder="原始密码"/>
									         		 <font color="red">*</font>
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 密码： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									           <input type="password" class="col-sm-10" id="passWord" name="passWord" chrpwd="true" onchange="checkUserName()" data-rule-required='true' data-rule-minlength='8' data-rule-maxlength='20' placeholder="至少包含字母、数字、特殊字符中两种及以上组合,8-20个字符"/>
									         		 <font color="red">*</font>
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 再输入一次： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									           <input type="password" class="col-sm-10" id="password_confirm" name="password_confirm" data-rule-equalto='#passWord' data-rule-required='true' data-rule-minlength='6' data-rule-maxlength='20' placeholder="再输入一次确认密码"/>
									         		 <font color="red">*</font>
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									 
									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											<button class="btn btn-info" type="submit">
												<i class="ace-icon fa fa-check bigger-110"></i>
												提交
											</button>

											&nbsp; &nbsp; &nbsp;
											<button class="btn" type="button" onclick="javascript:location.href='${ctx}/user/list'">
												<i class="ace-icon fa fa-undo bigger-110"></i>
												返回
											</button>
										</div>
									</div>
									
								</form:form>
					</div>
				</div>
			</div>
		</div>
		<%@include file="../../common/footer.jsp"%>
	</div>
<%@include file="../../common/javascript.html"%>
	<script type="text/javascript">
			jQuery(function($) {
		//And for the first simple table, which doesn't have TableTools or dataTables
				//select/deselect all rows according to table header checkbox
			var active_class = 'active';
			$('#dynamic-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
				var th_checked = this.checked;//checkbox inside "TH" table header
				
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
				});
			});
			
			//select/deselect a row when the checkbox is checked/unchecked
			$('#dynamic-table').on('click', 'td input[type=checkbox]' , function(){
				var $row = $(this).closest('tr');
				if(this.checked) $row.addClass(active_class);
				else $row.removeClass(active_class);
			});
			


			});
			
			function checkUserName(){
				var loginName = $("#loginName").val();
				var pwd = $("#passWord").val();
				if(loginName!=""&&pwd!=""){
				  if(loginName==pwd){
				    alert("密码不能与用户名相同！");
				    $("#passWord").val("");
				  }
				}
			
			}
		</script>


</body>
</html>