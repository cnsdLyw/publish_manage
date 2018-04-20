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
	<script src="${ctx }/resources/script/js/validate/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/js/validate/myValidate.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/js/area/jsAddress.js" type="text/javascript"></script>
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
						 //form.submit();
					<c:choose>
           				<c:when test="${organization.orgCode==null||organization.orgCode==''}">
           					$.ajax({//验证机构代码
								type : "get",  
								url : "${ctx}/organization/isOrgCodeExist/?orgCode="+$("#orgCode").val(),  
								dataType:"json",  
								success : function(isExist) {
									if(!isExist){
									 	form.submit();
									 }else{
									 	bootbox.alert("机构代码已存在，请重新输入!", function() {});
									 	return false;
									 }	
								}  
							});
						</c:when>
             			<c:otherwise>
             				form.submit();
						</c:otherwise>
					</c:choose>     	 
						 
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
						<li class="active">机构编辑</li>
					</ul>
					
				</div>
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="col-md-12">
						<form:form class="form-horizontal templatemo-create-account templatemo-container validate-form" method="POST"
							commandName="organization" action="${ctx}/organization/saveOrganization.html" enctype="multipart/form-data">
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> 机构名称： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">
									            	<input type="text" class="col-sm-10" id="orgName" name="orgName" value="${organization.orgName }" stringCheck="true"
															data-rule-required='true' data-rule-minlength='2' data-rule-maxlength='30' placeholder="机构名称">
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> 机构代码： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									         	<c:choose>
						            				<c:when test="${organization.orgCode==null||organization.orgCode==''}">
														<input type="text" class="col-sm-10" id="orgCode" name="orgCode" value="${organization.orgCode }" chrnum="true"
															 data-rule-required='true' data-rule-minlength='6' data-rule-maxlength='30' placeholder="机构代码">
													</c:when>
						              				<c:otherwise>
														<input type="text" class="col-sm-10" id="orgCode" name="orgCode" value="${organization.orgCode }" readonly="readonly">
													</c:otherwise>
												</c:choose>     
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> 机构封面： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-5">		<%--  --%>
												<input  data-rule-required='true' placeholder="推荐分辨率为${x}*${y}的封面" id="imageUrl" class="col-sm-10" reg="." readonly="readonly" data-rel="tooltip" data-original-title="请选择首页大图，必选项！" tip="请选择首页大图！" data-placement="bottom" value="${organization.url}"  style="float:left;width:250px;"><input type="file" name="file" id="file" onchange="this.previousSibling.value=this.value;" style="float:left;width:70px;height:34px;" value="点击上传" accept="image/gif,image/jpeg,image/jpg,image/png,image/bmp">          	
									        </div><div class="col-md-1">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 地区： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-2" style="height:50px;">		          	
								            	<select class="col-md-12" id="province" name="province">
											    </select>
									        </div>
									        <div class="col-md-2" style="height:50px;">		          	
												<select class="col-md-12" id="city" name="city">
											    </select>
									        </div>
									        <div class="col-md-2" style="height:50px;">		          	
												<select class="col-md-12" id="county" name="county">
									    		</select>
									        </div><div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 机构地址： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									            	<input type="text" class="col-sm-10" id="orgAddress" name="orgAddress" value="${organization.orgAddress }" stringCheck="true"
															data-rule-minlength='6' data-rule-maxlength='30' placeholder="机构地址">
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<%-- <div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> 机构分类： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
							            		<select class="col-xs-5 col-md-5" id="orgType" name="orgType" data-rule-required='true'>
							            			<option value="0">-选择所属分类-</option>
								            		<c:forEach var="clzss" items="${classes}">
								            			<option value="${clzss.id}" <c:if test="${organization.orgType==clzss.id}">selected="selected"</c:if>>${clzss.name }</option>
										        	</c:forEach>
									        	</select>
									        </div>	
									        </div>
									        <div class="col-md-6">&nbsp; </div> 
									</div> --%>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><font color="red">*</font>机构分类：</label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">	
							            		<select class="col-xs-5 col-md-5" id="orgType" name="orgType" data-rule-required='true'>
							            			<option value="">-选择机构所属分类-</option>
								            		<c:forEach var="clzss" items="${classes}">
								            			<option value="${clzss.id}" <c:if test="${organization.orgType==clzss.id}">selected="selected"</c:if>>${clzss.name }</option>
										        	</c:forEach>
									        	</select>
										</div>
				                        <div class='col-md-6 ccontrols'>
				                        </div>
				                        </div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 邮编： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									            	<input type="text" class="col-sm-10" id="postalcode" name="postalcode" value="${organization.postalcode}" isPostalcode="true"
															data-rule-minlength='2' data-rule-maxlength='30' placeholder="邮编">
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 联系电话： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									            	<input type="text" class="col-sm-10" id="telephone" name="telephone" value="${organization.telephone}" isPhone="true"
															data-rule-minlength='2' data-rule-maxlength='30' placeholder="联系电话 固话">
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> 联系人： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">
									            	<input type="text" class="col-sm-10" id="orgContacter" name="orgContacter" value="${organization.orgContacter }" isChinese="true"
															data-rule-required='true' data-rule-minlength='2' data-rule-maxlength='30' placeholder="联系人姓名">
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> 联系人邮箱： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									            	<input type="text" class="col-sm-10" id="orgContactEmail" name="orgContactEmail" value="${organization.orgContactEmail}" data-rule-email='true'
															data-rule-required='true' data-rule-minlength='2' data-rule-maxlength='30' placeholder="联系人邮箱">
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> 联系人电话： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									            	<input type="text" class="col-sm-10" id="orgContactPhone" name="orgContactPhone" value="${organization.orgContactPhone}" isPhone="true" data-rule-required='true'
															data-rule-minlength='2' data-rule-maxlength='30' placeholder="联系人电话 手机">
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> 机构简介： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:70px;margin-bottom:20px;width:70%;">		          	
									            <textarea class="col-sm-10" rows="5" data-rule-required='true' placeholder="请输入1-600个字符的内容" data-rule-minlength='1' data-rule-maxlength='600' id="orgContent" name="orgContent">${organization.orgContent}</textarea>
									        </div><div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="clearfix form-actions">
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
			
			addressInit('province', 'city', 'county','${organization.province}','${organization.city}','${organization.county}');
			
		</script>


</body>
</html>