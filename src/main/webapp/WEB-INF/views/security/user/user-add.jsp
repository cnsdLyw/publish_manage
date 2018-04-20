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
    <script src="${ctx }/resources/script/bootstrap/js/bootbox.min.js" type="text/javascript"></script>
<style type="text/css">
</style>
<script type="text/javascript">
		$(document).ready(function(){
				setValidateForm();
		});
		
		function check(){
			$('#orgCode').removeAttr("disabled");
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
					if($("#id").val()){
						 $.ajax({//验证邮箱
						      type : "get",  
						      url : "${ctx}/user/isEmailExistWithId/?id="+$("#id").val()+"&email="+$("#email").val(),  
						      dataType:"json",  
						      success : function(isExist) {
						      	if(!isExist){
						      	    $("#orgCode").removeAttr("disabled");
						      		form.submit();
						      	}else{
						      		bootbox.alert("邮箱已存在，请重新输入!", function() {});
						      		return false;
						      	}
						      }  
						  });
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
										      		$("#orgCode").removeAttr("disabled");
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
				}
			});
		};
		
		function checkAll(name){
			$("input[type=checkbox][name="+name+"]").each(function() {
		        $(this).prop("checked", true);
			});
		}
		
		function unCheckAll(name){
			$("input[type=checkbox][name="+name+"]").each(function() {
		        $(this).prop("checked", false);
			});
		}
		
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
						<li class="active">用户编辑</li>
					</ul>
					
				</div>
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="col-md-12">
						<form:form id="FormID" class="form-horizontal templatemo-create-account templatemo-container validate-form" method="POST"
							commandName="user" action="${ctx}/user/saveUser.html" onsubmit="return check()">
							<input type="hidden" id="id" name="id" value="${user.id }"/>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 登录名： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">
												<c:choose>
						            				<c:when test="${user.id==null||user.id==''||user.id=='0'||user.id==0}">
														<input type="text" class="col-sm-10" id="loginName" name="loginName" value="${user.loginName }" chrnum="true" onchange="checkUserName()"
															 data-rule-required='true' data-rule-minlength='6' data-rule-maxlength='30' placeholder="请输入字符长度为6-30的用户登录名">
													</c:when>
						              				<c:otherwise>
														<input type="text" class="col-sm-10" id="loginName" name="loginName" value="${user.loginName }" readonly="readonly">
													</c:otherwise>
												</c:choose>        	
									         		 <font color="red">*</font>
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group" >
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 所属机构： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">
													<c:if test="${roleStr==4 }">
														<select class="col-xs-5 col-md-5" id="orgCode" name="orgCode"  disabled="disabled">
									            			<!--  <option value="0">-选择所属机构-</option>-->
										            		<c:forEach var="organization" items="${organizations}">
										            			<option  value="${organization.orgCode}" <c:if test="${organization.orgCode==sessionScope.loginOrgCode}">selected="selected"</c:if>>${organization.orgName}</option>
												        	</c:forEach>
											        	</select>
													</c:if>
													<c:if test="${roleStr!=4 }">       	
									            		<select class="col-xs-5 col-md-5" id="orgCode" name="orgCode"  disabled="disabled">
									            			<!--  <option value="0">-选择所属机构-</option>-->
										            		<c:forEach var="organization" items="${organizations}">
										            			<option  value="${organization.orgCode}" <c:if test="${organization.orgCode==sessionScope.loginOrgCode}">selected="selected"</c:if>>${organization.orgName}</option>
												        	</c:forEach>
											        	</select>
											        </c:if>
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<c:if test="${user.id==null||user.id==''||user.id=='0'||user.id==0}"><!-- 新建用户才可以输入密码 -->
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 密码： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									           <input type="password" class="col-sm-10" id="passWord" name="passWord" chrpwd="true"  onchange="checkUserName()"
									           		autocomplete="off" data-rule-required='true' data-rule-minlength='8' data-rule-maxlength='20' placeholder="至少包含字母、数字、特殊字符中两种及以上组合,8-20个字符"/>
									         		 <font color="red">*</font>
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 再输入一次： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									           <input type="password" class="col-sm-10" id="password_confirm" name="password_confirm" data-rule-equalto='#passWord' chrnum="true" data-rule-required='true' data-rule-minlength='6' data-rule-maxlength='20' placeholder="请再次输入密码"/>
									         		 <font color="red">*</font>
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									</c:if>
									 
									 <div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 设置角色： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6">
								            	<label class='pos-rel col-sm-12'>
													<input name="checkAllc" type="button" onClick="javascript:checkAll('roleIds')" value="全选">&nbsp;&nbsp;
													<input name="unCheckAlcl" type="button" onClick="javascript:unCheckAll('roleIds')" value="取消全选">
												</label><br> <br>  	
									        	  <!--
									        	<c:forEach var="role" items="${roles}">
									        		<label class="pos-rel col-sm-4">
					                                	<input name="roleIds" type="checkbox" value="${role.id}" title="${role.remark }"
					                                	<c:if test="${role.status==2 }">checked="checked"</c:if> >
					                            		 ${role.roleName } &nbsp;&nbsp;
					                              	</label>
									        	</c:forEach>
									        	-->
									        	
									        	<c:forEach var="role" items="${roles}">
									        		<label class="pos-rel col-sm-6 col-sm-6">
										        		<input name="roleIds" type="checkbox" value="${role.id}" title="${role.remark }"
										        			<c:forEach var="roleUse" items="${useRoles}">
																<c:if test="${role.id==roleUse.id }">checked="checked"</c:if>	
										        			</c:forEach>
										        		>${role.roleName }&nbsp;&nbsp;
										        	</label>
									        	</c:forEach>
									        	
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 姓名： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">
									            	<input type="text" class="col-sm-10" id="name" name="name" value="${user.name }" isChinese="true"
															data-rule-required='true' data-rule-minlength='2' data-rule-maxlength='30' placeholder="请输入字符长度为2-30的用户姓名">
									         		 <font color="red">*</font>
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 性别： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									            	<select class="col-sm-4" id="sex" name="sex" title="性别" >
														<option value="男" <c:if test="${user.sex=='男' }"> selected</c:if> >男</option>
														<option value="女" <c:if test="${user.sex=='女' }"> selected</c:if> >女</option>
													</select>
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 邮箱： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									            	<input type="text" class="col-sm-10" id="email" name="email" value="${user.email}" data-rule-email='true'
															data-rule-required='true' data-rule-minlength='2' data-rule-maxlength='30' placeholder="请输入字符长度为2-30的用户邮箱">
									         		 <font color="red">*</font>
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 联系电话： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									            	<input type="text" class="col-sm-10" id="phone" name="phone" value="${user.phone}" isPhone="true"
															data-rule-minlength='2' data-rule-maxlength='30' placeholder="请输入字符长度为2-30的联系电话 手机">
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 通讯地址： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									            	<input type="text" class="col-sm-10" id="address" name="address" value="${user.address}"
															data-rule-minlength='2' data-rule-maxlength='100' placeholder="请输入字符长度为2-100的联系地址">
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
											<button class="btn" type="button" onclick="javascript:backList();">
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
		function backList(){
			window.location.href= "${ctx}/user/list.html";
	    }		
		</script>


</body>
</html>