<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>供应商</title>

<meta name="description" content="overview &amp; stats" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<%@include file="../common/meta.html"%>
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
							url: "${ctx}/supplier/saveOrganization.html"
							success: function(data) {
								parent.location.href="${ctx}/supplier/list";
							},error:function(data){
								parent.location.href="${ctx}/supplier/list";
							}
							};
					$("#mainForm").ajaxSubmit(options);*/
						//验证邮箱
						//-----------------------
		      		$.ajax({//验证邮箱
					      type : "get",  
					   	  url : "${ctx}/supplier/isEmailExistWithId/?id=${supplier.id}&email="+$("#businessContactsEmail").val(),  
					      dataType:"json",  
					      success : function(isExist) {
					      	if(!isExist){
					      		$("#orgType").removeAttr("disabled"); 
								form.submit();
					      	}else{
					      		bootbox.alert("邮箱已存在，请重新输入!", function() {});
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
	 <%@include file="../common/top.jsp"%>
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<%@include file="../common/left.jsp"%>
		<div class="main-content">
			<div class="main-content-inner">
				<!-- #section:basics/content.breadcrumbs -->
				<div class="breadcrumbs" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="${ctx}/index">首页</a>
						</li>
						<li> <a href="javascript:void(0);">供应商系统</a>
						</li>
						<li class="active">供应商</li>
					</ul>
					
				</div>
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="col-md-12">
						<form id="mainForm" method="post" class="form-horizontal templatemo-create-account templatemo-container validate-form"
							 commandName="supplier" enctype="multipart/form-data"  action="${ctx}/supplier/saveSupplier">
							<input type="hidden" id="id" name="id" value="${supplier.id}"/>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> 公司名称： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >
									            	<input type="text" class="col-sm-10" id="companyName" name="companyName" value="${supplier.companyName }" stringCheck="true"
															data-rule-required='true' data-rule-minlength='6' data-rule-maxlength='30' placeholder="请输入6-30字符的公司名称">
									        </div>	
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> 主管人： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
												<input type="text" class="col-sm-10" id="supervisor" name="supervisor" value="${supplier.supervisor }" isChinese="true" 
													 data-rule-required='true' data-rule-minlength='2' data-rule-maxlength='20' placeholder="主管人">
									        </div>	
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span>主管人电话： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
									            	<input type="text" class="col-sm-10" id="supervisorPhone" name="supervisorPhone" value="${supplier.supervisorPhone}" isPhone="true" data-rule-required='true'
															data-rule-minlength='6' data-rule-maxlength='30' placeholder="请输入格式正确的电话或手机，例：9558800">
									        </div>	
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> 业务联系人： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >
									            	<input type="text" class="col-sm-10" id="businessContacts" name="businessContacts" value="${supplier.businessContacts }" isChinese="true"
															data-rule-required='true' data-rule-minlength='2' data-rule-maxlength='20' placeholder="请输入20字符以内的企业联系人">
									        </div>	
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span>业务联系人电话： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
									            	<input type="text" class="col-sm-10" id="businessContactsPhone" name="businessContactsPhone" value="${supplier.businessContactsPhone}" isPhone="true" data-rule-required='true'
															data-rule-minlength='6' data-rule-maxlength='30' placeholder="请输入格式正确的电话或手机，例：9558800">
									        </div>	
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> 业务联系人邮箱： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
									            	<input type="text" class="col-sm-10" id="businessContactsEmail" name="businessContactsEmail" value="${supplier.businessContactsEmail}" data-rule-email='true'
															data-rule-required='true' data-rule-minlength='6' data-rule-maxlength='30' placeholder="例：xxx@163.com">
									        </div>	
										</div>
									</div>
									
								  <div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 行业分类： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
							            		<select class=" form-control" id="industryClassification" name="industryClassification">
							            			<option value="">-选择行业分类-</option>
								            		<c:forEach var="clzss" items="${classList}">
								            			<option value="${clzss.classCode}" <c:if test="${supplier.industryClassification== clzss.classCode}">selected='selected'</c:if>>${clzss.className }</option>
										        	</c:forEach>
									        	</select>
									        </div>	
									    </div>
									        
									</div>
										
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1">地区： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-2" style="height:50px;">		          	
								            	<select class="form-control col-md-12" id="province" name="province">
											    </select>
									        </div>
									        <div class="col-md-2" style="height:50px;">		          	
												<select class="form-control col-md-12" id="city" name="city">
											    </select>
									        </div>
									        <div class="col-md-2" style="height:50px;">		          	
												<select class="form-control col-md-12" id="county" name="county">
									    		</select>
									        </div>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> <span class="red">*</span>供应商地址： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
									            	<input type="text" class="col-sm-10" id="address" name="address" value="${supplier.address }" 
															data-rule-required='true' data-rule-minlength='6' data-rule-maxlength='30' placeholder="请输入6-30字符的机构地址,例：xxx区xx路x号">
									        </div>	
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> <span class="red">*</span>邮编：</label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
									            	<input type="text" class="col-sm-10" id="postalCode" name="postalCode" value="${supplier.postalCode}" isPostalcode="true"
															data-rule-required='true' data-rule-minlength='6' data-rule-maxlength='30'placeholder="请输入格式正确的邮编，例：100000">
									        </div>	
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1">公司网址： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
								            	<input type="text" class="col-sm-10" id="website" name="website" isWebsit="true" 
                          						data-rule-minlength='1' placeholder='例：www.xxx.com' value="${supplier.website }">
									        </div>	
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 公司电话： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
									            	<input type="text" class="col-sm-10" id="telephone" name="telephone" value="${supplier.telephone}" isPhone="true"
															data-rule-minlength='6' data-rule-maxlength='30' placeholder="联系电话 固话">
									        </div>	
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 公司传真： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
									            	<input type="text" class="col-sm-10" id="fax" name="fax" value="${supplier.fax}" isPhone="true"
															data-rule-minlength='6' data-rule-maxlength='30' placeholder="公司传真">
									        </div>	
										</div>
									</div>
									
								 <!-- 相关附件上传 -->
			                     <div class="form-group">
			                     	<label class="col-sm-2 control-label no-padding-right" for="form-field-1">主管人身份证正面： </label>
									<div class="col-sm-10 controls">
										<div class="col-md-6" style="height:30px;margin-top:5px;">	
											<c:choose>  
												 <c:when test="${supplier.idCardObverse!=null}">
													<div id="idCardObverseDiv1" style="display: block">
														<a target='_blank' href='${ctx}${supplier.idCardObverse}'>${supplier.idCardObverseName }</a>
														<a class="red" href="javascript:void(0);" onclick="showUploadDiv('idCardObverse');">
															<i class="ace-icon fa fa-cloud-upload bigger-130"></i>
															重新上传
														</a>
														<input type="hidden" id="idCardObverse" name="idCardObverse" value="${supplier.idCardObverse}"/>
														<input type="hidden" id="idCardObverseName" name="idCardObverseName" value="${supplier.idCardObverseName}"/>
													</div>
													<div id="idCardObverseDiv2" style="display: none">
														<input type="file" id="idCardObverseFile" name="idCardObverseFile"/>
													</div>
												 </c:when>
											   <c:otherwise>
													<input type="file" id="idCardObverseFile" name="idCardObverseFile"/>
											   </c:otherwise>  
											</c:choose>  
										</div>
									</div>
								 </div>
								 <div class="form-group">
			                     	<label class="col-sm-2 control-label no-padding-right" for="form-field-1">主管人身份证反面： </label>
									<div class="col-sm-10 controls">
										<div class="col-md-6" style="height:30px;margin-top:5px;">	
											  <c:choose>  
												 <c:when test="${supplier.idCardReverse!=null}">
													<div id="idCardReverseDiv1" style="display: block">
														<a target='_blank' href='${ctx}${supplier.idCardReverse}'>${supplier.idCardReverseName }</a>
														<a class="red" href="javascript:void(0);" onclick="showUploadDiv('idCardReverse');">
															<i class="ace-icon fa fa-cloud-upload bigger-130"></i>
															重新上传
														</a>
														<input type="hidden" id="idCardReverse" name="idCardReverse" value="${supplier.idCardReverse}"/>
														<input type="hidden" id="idCardReverseName" name="idCardReverseName" value="${supplier.idCardReverseName}"/>
													</div>
													<div id="idCardReverseDiv2" style="display: none">
														<input type="file" id="idCardReverseFile" name="idCardReverseFile"/>
													</div>
												 </c:when>
											   <c:otherwise>
													<input type="file" id="idCardReverseFile" name="idCardReverseFile"/>
											   </c:otherwise>  
											</c:choose>  
										</div>
									</div>
								 </div>
								  <div class="form-group">
			                     	<label class="col-sm-2 control-label no-padding-right" for="form-field-1">企业营业执照副本： </label>
									<div class="col-sm-10 controls">
										<div class="col-md-6" style="height:30px;margin-top:5px;">	
											<c:choose>  
											     <c:when test="${supplier.businessLicenceCopy!=null}">
											     	<div id="businessLicenceCopyDiv1" style="display: block">
												     	<a target='_blank' href='${ctx}${supplier.businessLicenceCopy}'>${supplier.businessLicenceCopyName }</a>
												     	<a class="red" href="javascript:void(0);" onclick="showUploadDiv('businessLicenceCopy');">
															<i class="ace-icon fa fa-cloud-upload bigger-130"></i>
															重新上传
														</a>
												     	<input type="hidden" id="businessLicenceCopy" name="businessLicenceCopy" value="${supplier.businessLicenceCopy}"/>
												     	<input type="hidden" id="businessLicenceCopyName" name="businessLicenceCopyName" value="${supplier.businessLicenceCopyName}"/>
											     	</div>
											     	<div id="businessLicenceCopyDiv2" style="display: none">
											     		<input type="file" id="businessLicenceCopyFile" name="businessLicenceCopyFile"/>
											     	</div>
											     </c:when>
											   <c:otherwise>
											   		<input type="file" id="businessLicenceCopyFile" name="businessLicenceCopyFile"/>
											   </c:otherwise>  
											</c:choose>  
										</div>
									</div>
								 </div>
								  <div class="form-group">
			                     	<label class="col-sm-2 control-label no-padding-right" for="form-field-1">授权书： </label>
									<div class="col-sm-10 controls">
										<div class="col-md-6" style="height:30px;margin-top:5px;">	
											<c:choose>  
												 <c:when test="${supplier.certificateAuthorization!=null}">
													<div id="certificateAuthorizationDiv1" style="display: block">
														<a target='_blank' href='${ctx}${supplier.certificateAuthorization}'>${supplier.certificateAuthorizationName }</a>
														<a class="red" href="javascript:void(0);" onclick="showUploadDiv('certificateAuthorization');">
															<i class="ace-icon fa fa-cloud-upload bigger-130"></i>
															重新上传
														</a>
														<input type="hidden" id="certificateAuthorization" name="certificateAuthorization" value="${supplier.certificateAuthorization}"/>
														<input type="hidden" id="certificateAuthorizationName" name="certificateAuthorizationName" value="${supplier.certificateAuthorizationName}"/>
													</div>
													<div id="certificateAuthorizationDiv2" style="display: none">
														<input type="file" id="certificateAuthorizationFile" name="certificateAuthorizationFile"/>
													</div>
												 </c:when>
											   <c:otherwise>
													<input type="file" id="certificateAuthorizationFile" name="certificateAuthorizationFile"/>
											   </c:otherwise>  
											</c:choose>  
										</div>
									</div>
								 </div>
					 
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1">供应商简介： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:150px;margin-bottom:50px;width:60%;">		          	
									            <textarea style="width:100%;" rows="8"  placeholder="请输入1-200个字符的内容" data-rule-minlength='1' data-rule-maxlength='600' id="remark" name="remark">${supplier.remark}</textarea>
									        	<h6 class="col-md-12" style="text-align: left;">您还可以输入<span id="word">200</span>个字符。</h6>
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
							</form>
					</div>
				</div>
			</div>
		</div>
		<%@include file="../common/footer.jsp"%>
	</div>
<%@include file="../common/javascript.html"%>
<script src="${ctx }/resources/script/bootstrap/js/ace/ace.js"  type="text/javascript" ></script>
<script src="${ctx }/resources/script/bootstrap/js/ace/elements.fileinput.js"  type="text/javascript" ></script>
<script src="${ctx }/resources/script/bootstrap/js/ace-elements.js"  type="text/javascript" ></script>
	<script type="text/javascript">
		addressInit('province', 'city', 'county','${supplier.province}','${supplier.city}','${supplier.county}');
		$(function(){
			$('#idCardObverseFile,#idCardReverseFile,#businessLicenceCopyFile,#certificateAuthorizationFile').ace_file_input({
				no_file:'还未选择文件 ...',
				btn_choose:'选择',
				btn_change:'修改',
				droppable:false,
				onchange:null,
				thumbnail:false //| true | large
				//whitelist:'gif|png|jpg|jpeg'
				//blacklist:'exe|php'
				//onchange:''
				//
			});
			var textLength = 200;
		  	$("#remark").keyup(function(){
		   		var len = $(this).val().length;
		   		if(len > (textLength-1)){
		    		$(this).val($(this).val().substring(0,textLength));
		   		}
		   		var num = textLength - len;
		   		if(num>=0)
		   		$("#word").text(num);
		  	});
		 });	
		 
		 function showUploadDiv(fileStr){
		 	$("#"+fileStr+"Div1").css("display","none");
		 	$("#"+fileStr+"Div2").css("display","block");
		 }
	</script>


</body>
</html>