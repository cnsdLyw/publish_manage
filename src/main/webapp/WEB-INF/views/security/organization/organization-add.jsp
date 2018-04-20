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
						<li class="active">机构编辑</li>
					</ul>
					
				</div>
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="col-md-12">
						<form:form id="mainForm" class="form-horizontal templatemo-create-account templatemo-container validate-form"
							 commandName="organization" enctype="multipart/form-data"  action="${ctx}/organization/saveOrganization.html">
							<input type="hidden" id="upperCode" name="upperCode" value="${organization.upperCode}"/>
									<c:if test="${organization.upperCode!=null}">
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="form-field-1">上级机构： </label>
											<div class="col-sm-10 controls">
												<div class="col-md-6" >
										            	<input readonly="readonly" type="text" class="col-sm-10" value="${orgName }" >
										        </div>	 
											</div>
										</div>
				                    </c:if>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> 机构名称： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >
									            	<input type="text" class="col-sm-10" id="orgName" name="orgName" value="${organization.orgName }" stringCheck="true"
															data-rule-required='true' data-rule-minlength='2' data-rule-maxlength='30' placeholder="请输入2-30字符的机构名称">
									        </div>	
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> 机构代码： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
												<input type="text" class="col-sm-10" id="orgCode" name="orgCode" value="${organization.orgCode }" chrnum="true"
													 data-rule-required='true' data-rule-minlength='6' data-rule-maxlength='30' placeholder="请输入6-30字符的机构代码">
									        </div>	
										</div>
									</div>
									
									<%-- <div class="form-group">
										<label class='control-label col-md-2 no-padding-right'><font color="red">*</font>&nbsp;机构封面</label>
										<div class='col-md-6' >
				                          <input class='form-control' id="imageUrl" data-rule-required='true' readonly="readonly" placeholder='推荐上传图片分辨率${x }*${y}' style="float:left;height:34px;width:77.7%;">
				                          <a href="" class="file" style="float:left;height:34px;"><input type="file" name="file" id="file" onchange="setUrl(this.value);" accept="image/*">选择文件</a>
				                        </div>
										 <div class='col-md-4 ccontrols'>
				                         </div>
									</div> --%>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> 机构封面： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6">		
											<input class='form-control' id="imageUrl" name="url" data-rule-required='true' readonly="readonly"  style="float:left;height:34px;width:65%;" value="${organization.url}">
                          					<a href="" class="file" style="float:left;width:16%;height:33.8px;"><input type="file" name="file" id="file" onchange="setUrl(this.value);" accept="image/gif,image/jpeg,image/jpg,image/png,image/bmp">选择文件</a>
												<%-- <input  data-rule-required='true' placeholder="推荐分辨率为${x}*${y}的封面" id="imageUrl" class="col-sm-10" reg="."  data-rel="tooltip" data-original-title="请选择首页大图，必选项！" tip="请选择首页大图！" data-placement="bottom" value="${organization.url}"  style="float:left;width:250px;"><input type="file" name="file" id="file" onchange="this.previousSibling.value=this.value;" style="float:left;width:70px;" value="点击上传" accept="image/*"> --%>          	
									        <c:if test="${organization.url!=null&&organization.url!='' }">
									        	<br/><br/><a href="${fileHomeUrl}/${organization.url}" target="_blank">预览</a>
									        </c:if>
									        </div> 
										</div>
									</div>
									
									
									
									<%-- <div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> 机构分类： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
							            		<select class="col-xs-5 col-md-5" id="orgType" name="orgType" data-rule-required='true'>
							            			<option value="0">-选择所属分类-</option>
								            		<c:forEach var="clzss" items="${classes}">
								            			<option value="${clzss.id}" <c:if test="${organization.orgType==clzss.id}">selected="selected"</c:if>>${clzss.name }</option>
										        	</c:forEach>
									        	</select>
									        </div>	
									        </div>
									        
									</div> --%>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><font color="red">*</font>机构分类：</label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >	
							            		<select disabled="disabled" class="col-xs-5 col-md-5" id="orgType" name="orgType" data-rule-required='true' onchange="select(this.value);" style="height:33.8px;">
							            			<option value="">-选择机构所属分类-</option>
								            		<c:forEach var="clzss" items="${classes}">
								            			<option value="${clzss.id}" <c:if test="${organization.orgType==clzss.id}">selected="selected"</c:if>>${clzss.name }</option>
										        	</c:forEach>
									        	</select>
										</div>
				                        </div>
									</div>
									
										
									<!-- 	<div class="form-group">
										<label class='col-sm-2 control-label no-padding-right'>机构类型：</label> 
										<div class="col-sm-10 controls">
											<div class="col-md-2" style="height:50px;">		          	
							            	<select class="col-md-12" id="firstOrgName" name="firstOrgName" disabled="disabled">
										    </select>
								        </div>
								        <div class="col-md-2" style="height:50px;">			          	
											<select class="col-md-12" id="secondOrgName" name="secondOrgName" disabled="disabled">
										    </select>
								        </div>
								        </div>
									</div> -->
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> <span class="red">*</span>机构类型： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-2" style="float:left;">		          	
								            	<input readonly="readonly" class="col-md-12" style="height:33.8px;" id="firstOrgName" name="firstOrgName" value="${organization.firstOrgName}">
									        </div>
									        <div class="col-md-2" style="float:left;">		          	
												<input readonly="readonly" class="col-md-12" style="height:33.8px;" id="secondOrgName" name="secondOrgName" value="${organization.secondOrgName}">
									        </div>
									        
										</div>
									</div>
									<div class="form-group"  style="display:none;" id="ISBN">
											<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span>isbn前缀码： </label>
											<div class="col-sm-10 controls">
												<div class="col-md-6" >		          	
									            	<input type="text" class="col-sm-10" id="isbn" name="isbn" data-rule-required='true' data-rule-maxlength='13'value="${organization.isbn }">
										        </div>	
											</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> <span class="red">*</span>地区： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-2" style="float:left;">		          	
									            	<input readonly="readonly" class="col-md-12" style="height:33.8px;"id="province" name="province" value="${organization.province}">
										        </div>
										        <div class="col-md-2" style="float:left;">		          	
													<input readonly="readonly" class="col-md-12" style="height:33.8px;" id="city" name="city" value="${organization.city}">
										     </div>
									        <div class="col-md-2" style="display:none;">		          	
												<select class="col-md-12" id="county" name="county">
									    		</select>
									        </div>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> <span class="red">*</span>机构地址： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
									            	<input type="text" class="col-sm-10" id="orgAddress" name="orgAddress" value="${organization.orgAddress }" 
															data-rule-minlength='6' data-rule-maxlength='30' placeholder="请输入6-30字符的机构地址,例：xxx区xx路x号">
									        </div>	
										</div>
									</div>
									<!-- <div class="form-group" id="add">
										<label class='control-label col-md-2 no-padding-right'><font color="red">*</font>&nbsp;机构类型</label> 
										<div class="col-md-3" style="height:50px;">		          	
							            	<select class="form-control col-md-12" data-rule-required='true' id="firstOrgName" name="firstOrgName">
										    </select>
								        </div>
								        <div class="col-md-3" style="height:50px;">		          	
											<select class="form-control col-md-12" id="secondOrgName" data-rule-required='true' name="secondOrgName">
										    </select>
								        </div>
									</div> -->
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
										
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> ftp用户名： </label>
											<div class="col-sm-10 controls">
												<div class="col-md-6" >		          	
										            	<input type="text" class="col-sm-10" id="ftpName" name="ftpName" 
															value="${organization.ftpName }" placeholder="ftp用户名">
										        </div>	
											</div>
										</div>
										
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> ftp密码： </label>
											<div class="col-sm-10 controls">
												<div class="col-md-6" >		          	
										            	<input type="passWord" class="col-sm-10" id="ftpPassWord" name="ftpPassWord" 
																value="${organization.ftpPassWord }" placeholder="ftp密码">
										        </div>	
											</div>
										</div>
										
										<div class="form-group">
											<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> ftp地址： </label>
											<div class="col-sm-10 controls">
												<div class="col-md-6" >		          	
										            	<input type="text" class="col-sm-10" id="ftpAddress" name="ftpAddress" 
															value="${organization.ftpAddress }" placeholder="ftp地址">
										        </div>	
											</div>
										</div>
									 -->	
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 邮编：</label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
									            	<input type="text" class="col-sm-10" id="postalcode" name="postalcode" value="${organization.postalcode}" isPostalcode="true"
															data-rule-minlength='6' data-rule-maxlength='30'placeholder="请输入格式正确的邮编，例：100000">
									        </div>	
										</div>
									</div>
									<%-- <div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 联系电话： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
									            	<input type="text" class="col-sm-10" id="telephone" name="telephone" value="${organization.telephone}" isPhone="true"
															data-rule-minlength='2' data-rule-maxlength='30' placeholder="联系电话 固话">
									        </div>	
										</div>
									</div> --%>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> 企业联系人： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >
									            	<input type="text" class="col-sm-10" id="orgContacter" name="orgContacter" value="${organization.orgContacter }" isChineseOrEn="true"
															data-rule-required='true' data-rule-minlength='2' data-rule-maxlength='12' placeholder="请输入12字符以内的企业联系人">
									        </div>	
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> 联系人电话： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
									            	<input type="text" class="col-sm-10" id="orgContactPhone" name="orgContactPhone" value="${organization.orgContactPhone}" isPhone="true" data-rule-required='true'
															data-rule-minlength='2' data-rule-maxlength='30' placeholder="请输入格式正确的电话或手机，例：9558800">
									        </div>	
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1">企业网址： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
								            	<input type="text" class="col-sm-10" id="orgWebsit" name="orgWebsit" isWebsit="true" 
                          						data-rule-minlength='1' placeholder='例：www.xxx.com' value="${organization.orgWebsit }">
									        </div>	
										</div>
									</div>
				                     
				                     <div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span>经济类型： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
								            	<input type="text" class="col-sm-10" id="orgEconomic" name="orgEconomic"data-rule-required='true' is3Num="true" 
                          						placeholder='请输入3位数字，例：100代表内资，请参照GB/T12402-2000' value="${organization.orgEconomic }">
									        </div>	
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> 企业邮箱： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
									            	<input type="text" class="col-sm-10" id="orgContactEmail" name="orgContactEmail" value="${organization.orgContactEmail}" data-rule-email='true'
															data-rule-required='true' data-rule-minlength='2' data-rule-maxlength='30' placeholder="例：xxx@163.com">
									        </div>	
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1">机构简介： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:70px;margin-bottom:50px;width:60%;">		          	
									            <textarea class="col-sm-10" rows="5"  placeholder="请输入1-600个字符的内容" data-rule-minlength='1' data-rule-maxlength='600' id="orgContent" name="orgContent">${organization.orgContent}</textarea>
									        </div>
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
				else $row.removeClassss(active_class);
			});

			});
			//organizationInit('firstOrgName', 'secondOrgName', '${organization.firstOrgName}','${organization.secondOrgName}');
			//addressInit('province', 'city', 'county','${organization.province}','${organization.city}','${organization.county}');
			
		</script>


</body>
</html>