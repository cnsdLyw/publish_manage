<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>机构编辑</title>

<meta name="description" content="overview &amp; stats" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<%@include file="../../common/meta.html"%>
    <script src="${ctx }/resources/script/js/area/jsOrganization.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/js/area/jsAddress.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/js/jquery.form.js" type="text/javascript"></script>
	<script src="${ctx }/resources/script/js/validate/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/js/validate/myValidate.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/js/bootbox.min.js" type="text/javascript"></script>
<style type="text/css">
.file {
    position: relative;
    display: inline-block;
    background: #46b8da;
    border: 1px solid #99D3F5;
    border-radius: 4px;
    padding: 4px 12px;
    overflow: hidden;
    color: #fff;
    text-decoration: none;
    text-indent: 0;
    line-height: 20px;
}
.file input {
    position: absolute;
    font-size: 100px;
    right: 0;
    top: 0;
    opacity: 0;
}
.file:hover {
    background: #46b8da;
    border-color: #78C3F3;
    color: #fff;
    text-decoration: none;
}
</style>
<script type="text/javascript">
		$(document).ready(function(){
			setValidateForm();
			var a = "${type}";
			if(a=="1"){
				document.getElementById("ISBN").style.display="";
			}
			var x = Math.round(window.screen.width*0.8333333333);
			var y = Math.round(x/2);
			$("#imageUrl").attr("placeholder","推荐分辨率为"+x+"*"+y+"的封面！");
			$("#imageUrl").attr("data-original-title","推荐分辨率为"+x+"*"+y+"的封面！");
			$("#imageUrl").attr("tip","推荐分辨率为"+x+"*"+y+"的封面！");
		});
		function setUrl(url){
	    	document.getElementById("imageUrl").value=url;
	    }
		
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
					/*var orgCode = document.getElementById("orgCode").value;
					var options = {//验证ftp是否可用
							async: false, 
							contentType: "application/x-www-form-urlencoded; charset=utf-8", 
							url: "${ctx}/organization/saveOrganization.html"
							success: function(data) {
								parent.location.href="${ctx}/organization/list";
							},error:function(data){
								parent.location.href="${ctx}/organization/list";
							}
							};
					$("#mainForm").ajaxSubmit(options);*/
					$("#orgType").removeAttr("disabled"); 
					form.submit();
				}
			});
		};
		
		function select(value){
			if(value==1){
				document.getElementById("ISBN").style.display="";
			}else{
				document.getElementById("ISBN").style.display="none";
			}
		}
		
		function upload(){
			
		}
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
						<li class="active">设置机构FTP</li>
					</ul>
					
				</div>
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="col-md-12">
						<form:form id="mainForm" class="form-horizontal templatemo-create-account templatemo-container validate-form"
							 commandName="organization" action="${ctx}/organization/saveFtp.html">
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 机构名称： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >
									            	<input type="text" class="col-sm-10" value="${organization.orgName }" readonly="readonly"/>
									        </div>	
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 机构代码： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
												<input type="text" class="col-sm-10" id="orgCode" name="orgCode" value="${organization.orgCode }" readonly="readonly"/>
									        </div>	
										</div>
									</div>
									
									<!-- 
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 登录帐号： </label>
											<div class="col-sm-10 controls">
												<div class="col-md-6" >		          	
										            	<input type="text" class="col-sm-10" id="loginName" name="loginName" 
																value="${organization.loginName }" placeholder="登录帐号">
										        </div>	
											</div>
										</div>
										
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 帐号密码： </label>
											<div class="col-sm-10 controls">
												<div class="col-md-6" >		          	
										            	<input type="passWord" class="col-sm-10" id="passWord" name="passWord"  
															value="${organization.passWord }" placeholder="帐号密码">
										        </div>	
											</div>
										</div>
									 -->	
									 	<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> <span class="red">*</span>ftp地址： </label>
											<div class="col-sm-10 controls">
												<div class="col-md-6" >		          	
										            	<input type="text" class="col-sm-10" id="ftpAddress" name="ftpAddress" value="${organization.ftpAddress }" placeholder="ftp地址"
										            	isFtp="true" data-rule-minlength='1' placeholder='例：www.xxx.com' value="${organization.orgWebsit }"/>
										        </div>	
											</div>
										</div>
										
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> ftp用户名： </label>
											<div class="col-sm-10 controls">
												<div class="col-md-6" >		          	
										            	<input type="text" class="col-sm-10" id="ftpName" name="ftpName" value="${organization.ftpName }" placeholder="ftp用户名"
										            	chrnum="true" data-rule-required='true' data-rule-minlength='6' data-rule-maxlength='20' placeholder="请输入6-30的ftp用户名"/>
										        </div>	
											</div>
										</div>
										
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> <span class="red">*</span>ftp密码： </label>
											<div class="col-sm-10 controls">
												<div class="col-md-6" >		          	
										            	<input type="text" class="col-sm-10" id="ftpPassWord" name="ftpPassWord" value="${organization.ftpPassWord }" placeholder="ftp密码"
										            	data-rule-required='true' data-rule-minlength='6' data-rule-maxlength='20'  placeholder="请输入6-30字符的机构代码"/>
										        </div>	
											</div>
										</div>
										
									<div class="clearfix">
										<div class="col-md-offset-3 col-md-9">
											<button class="btn btn-info" type="submit">
												<i class="ace-icon fa fa-check bigger-110"></i>
												提交
											</button>

											&nbsp; &nbsp; &nbsp;
											<button class="btn" type="button" onclick="javascript:history.back();">
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
				else $row.removeClassss(active_class);
			});

			});
			//organizationInit('firstOrgName', 'secondOrgName', '${organization.firstOrgName}','${organization.secondOrgName}');
			//addressInit('province', 'city', 'county','${organization.province}','${organization.city}','${organization.county}');
			
		</script>


</body>
</html>