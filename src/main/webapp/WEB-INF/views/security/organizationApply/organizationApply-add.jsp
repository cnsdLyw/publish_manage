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
    <script src="${ctx }/resources/script/js/area/jsOrganization.js" type="text/javascript"></script>
	<script src="${ctx }/resources/script/js/area/PinYin.js" type="text/javascript"></script>
	<script src="${ctx }/resources/script/js/statusBarExt.js" type="text/javascript"></script>
<style type="text/css">
</style>
<script type="text/javascript">
		$(document).ready(function(){
				select(1);
				setValidateForm();
				
		});
		var ctxPath ="${ctx}";
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
           					$.ajax({//验证机构代码
								type : "get",  
								url : "${ctx}/organization/isOrgCodeExist/?orgCode="+$("#orgCode").val(),  
								dataType:"json",  
								success : function(isExist) {
									if(!isExist){
										Show("正在审核任务!");
										$("#province").removeAttr("disabled"); 
										$("#city").removeAttr("disabled"); 
										$("#county").removeAttr("disabled"); 
										$("#orgType").removeAttr("disabled"); 
										$("#firstOrgName").removeAttr("disabled"); 
										$("#secondOrgName").removeAttr("disabled"); 
									 	//下拉框等取消disabled属性
	   									qxDisabled();
									 	form.submit();
									 }else{
									 	bootbox.setDefaults("locale","zh_CN"); 
									 	bootbox.alert("机构代码已存在，请重新输入!", function() {});
									 	return false;
									 }	
								}  
							});
						 
				}
			});
		};
		
		function select(value){
			 if(value==1){ 
				/* document.getElementById("pass").style.display=""; */
				var name = document.getElementById("orgName").value;
				var code = document.getElementById("orgCode").value;
				var loginName = codefans_net_CC2PY(name);
				var host  = window.location.host;
				document.getElementById("loginName").value=loginName;
				document.getElementById("passWord").value=loginName+"111111";
				document.getElementById("ftpName").value=loginName;
				document.getElementById("ftpPassWord").value=loginName+"111111";
				document.getElementById("ftpAddress").value="ftp://"+host+"/"+loginName; 
			 } else{
				document.getElementById("loginName").value="";
				document.getElementById("passWord").value="";
				document.getElementById("ftpName").value="";
				document.getElementById("ftpPassWord").value="";
				document.getElementById("ftpAddress").value=""; 
			 }  
		}
		function disabledForm(){
		  $("input").attr('readonly', true);
		  $("textarea").attr('readonly', true);
		  $(':radio').attr('disabled', true);
		  $(':checkbox').attr('disabled', true);
		  $(':button').attr('disabled', true);
		  $('a').removeAttr('onclick');
		  $('select').attr('disabled', true);
		
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
						<li class="active">机构审核</li>
					</ul>
					
				</div>
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="col-md-12">
						<form:form class="form-horizontal templatemo-create-account templatemo-container validate-form" method="POST"
							commandName="organizationApply" action="${ctx}/organizationApply/saveOrganizationApply.html">
								<input type="hidden" id="id" name="id" value="${organizationApply.id }">
								<input type="hidden" id="beforeCode" name="beforeCode" value="${organizationApply.beforeCode}">
								<input type="hidden" id="orgCode" name="orgCode" value="${organizationApply.orgCode}">
								
									<c:if test="${organizationApply.upperCode!=null }">
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="form-field-1">上级机构： </label>
											<div class="col-sm-10 controls">
												<div class="col-md-6">
										            	<input type="text" class="col-sm-10" value="${orgName }" readonly="readonly">
										        </div>	<div class="col-md-6">&nbsp; </div> 
											</div>
										</div>
				                    </c:if>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> <span class="red">*</span> 机构名称： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >
									            	<input type="text" class="col-sm-10" id="orgName" name="orgName" value="${organizationApply.orgName }" stringCheck="true" readonly="readonly"
															data-rule-required='true' data-rule-minlength='2' data-rule-maxlength='30' placeholder="机构名称">
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> 机构封面： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-5">		<%--  --%>
										        <c:choose>  
												     <c:when test="${organizationApply.url==null||organizationApply.url==''}">
														<a href="${ctx}/resources/images/defaultImages.jpg" target="_blank" style="float:left;width:250px;margin-top: 8px;font-size: 14px;">预览</a> 
												     </c:when>
												   <c:otherwise>
														<a href="${fileHomeUrl}/${organizationApply.url}" target="_blank" style="float:left;width:250px;margin-top: 8px;font-size: 14px;">预览</a> 
												   </c:otherwise>  
												</c:choose> 
												 <input id="url" name="url" type="hidden" class="col-sm-10" data-rel="tooltip" data-original-title="请选择图片，必选项！" tip="请选择图片！" data-placement="bottom" value="${organizationApply.url}"  style="float:left;width:250px;">  
												
									        </div>	
											<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									<input type="hidden"id="orgType" name="orgType" value="${organizationApply.orgType}">
									<%-- <div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> <span class="red">*</span> 机构分类： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
							            		<select class="col-xs-5 col-md-5" id="orgType" name="orgType" disabled="disabled"  style="height:33.8px;">
							            			<option value="0">-选择所属分类-</option>
								            		<c:forEach var="clzss" items="${classes}">
								            			<option value="${clzss.id}" <c:if test="${organizationApply.orgType==clzss.id}">selected="selected"</c:if>>${clzss.name }</option>
										        	</c:forEach>
									        	</select>
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div> --%>
									<div class="form-group">
										<label class='col-sm-2 control-label no-padding-right' for="form-field-1"><span class="red">*</span> 机构类型：</label> 
										<div class="col-sm-10 controls">
											<div class="col-md-2" style="float:left;">		          	
								            	<input readonly="readonly" class="col-md-12" style="height:33.8px;" id="firstOrgName" name="firstOrgName" value="${organizationApply.firstOrgName}">
									        </div>
									        <div class="col-md-2" style="float:left;">		          	
												<input readonly="readonly" class="col-md-12"  style="height:33.8px;" id="secondOrgName" name="secondOrgName" value="${organizationApply.secondOrgName}">
									        </div>
								        </div>
									</div>
									<c:if test="${organizationApply.isbn!=null&& organizationApply.isbn!=''}">
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> isbn前缀码： </label>
											<div class="col-sm-10 controls">
												<div class="col-md-6"  style="height:33.8px;">		          	
									            	<input type="text" class="col-sm-10" id="isbn" name="isbn" value="${organizationApply.isbn }" readonly="readonly" data-rule-maxlength='13'>
										        </div>	<div class="col-md-6">&nbsp; </div> 
											</div>
										</div>
									</c:if>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> <span class="red">*</span> 地区： </label>
										<div class="col-sm-10 controls">
										    <div class="col-md-2" style="float:left;">		          	
								            	<input readonly="readonly" class="col-md-12"  style="height:33.8px;" id="province" name="province" value="${organizationApply.province}">
									        </div>
									        <div class="col-md-2" style="float:left;">		          	
												<input readonly="readonly" class="col-md-12" style="height:33.8px;"  id="city" name="city" value="${organizationApply.city}">
									        </div>
											<!-- <div class="col-md-2" >		          	
								            	<select class="col-md-12" id="province" name="province" disabled="disabled">
											    </select>
									        </div>
									        <div class="col-md-2" >		          	
												<select class="col-md-12" id="city" name="city" disabled="disabled">
											    </select>
									        </div> -->
									        <div class="col-md-2" style="height:33.8px;display:none;">		          	
												<select class="col-md-12" id="county" name="county" disabled="disabled">
									    		</select>
									        </div><div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> <span class="red">*</span> 机构地址： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
								            	<input type="text" class="col-sm-10" id="orgAddress" name="orgAddress" value="${organizationApply.orgAddress }"  readonly="readonly"
														data-rule-minlength='6' data-rule-maxlength='30' placeholder="机构地址">
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 邮编： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
									            	<input type="text" class="col-sm-10" id="postalcode" name="postalcode" value="${organizationApply.postalcode}" isPostalcode="true" readonly="readonly"
															data-rule-minlength='2' data-rule-maxlength='30' placeholder="邮编">
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> <span class="red">*</span> 企业联系人： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >
									            	<input type="text" class="col-sm-10" id="orgContacter" name="orgContacter" value="${organizationApply.orgContacter }" isChineseOrEn="true" readonly="readonly"
															data-rule-required='true' data-rule-minlength='2' data-rule-maxlength='30' placeholder="联系人姓名">
									         		 
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<%-- <div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 联系电话： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
									            	<input type="text" class="col-sm-10" id="telephone" name="telephone" value="${organizationApply.telephone}" isPhone="true" readonly="readonly"
															data-rule-minlength='2' data-rule-maxlength='30' placeholder="联系电话 固话">
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div> --%>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> <span class="red">*</span> 联系人电话： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
									            	<input type="text" class="col-sm-10" id="orgContactPhone" name="orgContactPhone" value="${organizationApply.orgContactPhone}" isPhone="true" readonly="readonly"
															data-rule-minlength='2' data-rule-maxlength='30' placeholder="联系人电话 手机">
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1">企业网址： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
								            	<input type="text" class="col-sm-10" id="orgWebsit" name="orgWebsit" value="${organizationApply.orgWebsit }" readonly="readonly">
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
				                     
				                     <div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> 经济类型： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
								            	<input type="text" class="col-sm-10" id="orgEconomic" name="orgEconomic" value="${organizationApply.orgEconomic }" readonly="readonly">
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> <span class="red">*</span> 企业邮箱： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
									            	<input type="text" class="col-sm-10" id="orgContactEmail" name="orgContactEmail" value="${organizationApply.orgContactEmail}" data-rule-email='true' readonly="readonly"
															data-rule-required='true' data-rule-minlength='2' data-rule-maxlength='30' placeholder="联系人邮箱">
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 机构简介： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:70px;margin-bottom:20px;width:70%;">		          	
									            <textarea readonly="readonly" class="col-sm-10" rows="5" id="orgContent" name="orgContent">${organizationApply.orgContent}</textarea>
									        </div><div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<%-- <div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 机构代码设置： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
								            	<c:choose>
						            				<c:when test="${organizationApply.orgCode==null||organizationApply.orgCode==''}">
														<input type="text" class="col-sm-10" id="orgCode" name="orgCode" value="${organizationApply.orgCode }" chrnum="true"
															 data-rule-required='true' data-rule-minlength='6' data-rule-maxlength='30' placeholder="机构代码">
													</c:when>
						              				<c:otherwise>
														<input type="hidden" class="col-sm-10" id="orgCode" name="orgCode" value="${organizationApply.orgCode }" readonly="readonly">
													</c:otherwise>
												</c:choose>      
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>--%>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 状态设置： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >
												<select class="col-xs-5 col-md-5" name="orgStatus" id="orgStatus" onchange="select(this.value);">
													<c:forEach  items="${statuses}" var="stat">
														<option value="${stat.statusId }" <c:if test="${stat.statusId==organizationApply.orgStatus }">selected</c:if>>${stat.statusName }</option>
													</c:forEach>
												</select>	
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									<div id="pass" style="display:none;">
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 登录帐号： </label>
											<div class="col-sm-10 controls">
												<div class="col-md-6" >		          	
										            	<input type="text" class="col-sm-10" id="loginName" name="loginName" readonly="readonly"
																data-rule-minlength='2' data-rule-maxlength='30' placeholder="登录帐号">
										        </div>	<div class="col-md-6">&nbsp; </div> 
											</div>
										</div>
										
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 帐号密码： </label>
											<div class="col-sm-10 controls">
												<div class="col-md-6" >		          	
										            	<input type="passWord" class="col-sm-10" id="passWord" name="passWord" readonly="readonly"
																 placeholder="帐号密码">
										        </div>	<div class="col-md-6">&nbsp; </div> 
											</div>
										</div>
										
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> ftp用户名： </label>
											<div class="col-sm-10 controls">
												<div class="col-md-6" >		          	
										            	<input type="text" class="col-sm-10" id="ftpName" name="ftpName" readonly="readonly"
															 placeholder="ftp用户名">
										        </div>	<div class="col-md-6">&nbsp; </div> 
											</div>
										</div>
										
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> ftp地址： </label>
											<div class="col-sm-10 controls">
												<div class="col-md-6" >		          	
										            	<input type="text" class="col-sm-10" id="ftpAddress" name="ftpAddress" readonly="readonly"
															 placeholder="ftp地址">
										        </div>	<div class="col-md-6">&nbsp; </div> 
											</div>
										</div>
										
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> ftp密码： </label>
											<div class="col-sm-10 controls">
												<div class="col-md-6" >		          	
										            	<input type="passWord" class="col-sm-10" id="ftpPassWord" name="ftpPassWord" readonly="readonly"
																 placeholder="ftp密码">
										        </div>	<div class="col-md-6">&nbsp; </div> 
											</div>
										</div>
										
										<!-- <div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> ftp地址： </label>
											<div class="col-sm-10 controls">
												<div class="col-md-6" >		          	
										            	<input type="text" class="col-sm-10" id="ftpAddress" name="ftpAddress" readonly="readonly"
															 placeholder="ftp地址">
										        </div>	<div class="col-md-6">&nbsp; </div> 
											</div>
										</div> -->
									</div>
									<%-- <input type="hidden" class="col-sm-10" id="loginName" name="loginName"value="${organizationApply.loginName }">
									<input type="hidden" class="col-sm-10" id="passWord" name="passWord" value="${organizationApply.passWord }">
									<input type="hidden" class="col-sm-10" id="ftpName" name="ftpName" value="${organizationApply.ftpName }">
									<input type="hidden" class="col-sm-10" id="ftpPassWord" name="ftpPassWord" value="${organizationApply.ftpPassWord }"> --%>
									<%-- <input type="hidden" class="col-sm-10" id="ftpAddress" name="ftpAddress" value="${ftpAddress}"> --%>
									<input type="hidden" class="col-sm-10" id="upperCode" name="upperCode" value="${organizationApply.upperCode}">
									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											<button class="btn btn-info" type="submit">
												<i class="ace-icon fa fa-check bigger-110"></i>
												提交
											</button>

											&nbsp; &nbsp; &nbsp;
											<button class="btn" type="button" onclick="javascript:location.href='${ctx}/organizationApply/list'">
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
			//organizationInit('firstOrgName', 'secondOrgName', '${organizationApply.firstOrgName}','${organizationApply.secondOrgName}');
			//addressInit('province', 'city', 'county','${organizationApply.province}','${organizationApply.city}','${organizationApply.county}');
		</script>


</body>
</html>