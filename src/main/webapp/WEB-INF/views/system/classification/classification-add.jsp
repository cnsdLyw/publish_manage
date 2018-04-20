<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>分类管理</title>

<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<%@include file="../../common/meta.html"%>
<link href="${ctx}/resources/script/js/easyvalidate/css/validate_platform.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.col-md-6 {
	padding-left:100px;
	padding-top:10px;
}
.col-md-12 {
	width:70%;
}
</style>

<script type="text/javascript">
</script>

</head>

<body class="no-skin">
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<!-- #section:basics/content.breadcrumbs -->
				<!-- /section:basics/content.breadcrumbs -->
				<div class="col-xs-12" class="form-horizontal">
				   <div class="row">
					<div class="col-xs-12">
						<form id="mainForm" class="form-horizontal" method="POST" action="${ctx}/classification/saveClass" commandName="classification">
							<input type="hidden" id="classKey" name="classKey" value="${classification.classKey }" />
							<input type="hidden" id="parentCode" name="parentCode" value="${classification.parentCode }" />
					
							<div class="form-group">
								<label class="col-md-2 col-xs-2 col-sm-2 col-lg-2 control-label no-padding-right" for="form-field-1"><font color="red">*</font>&nbsp;分类名称：
								</label>
								<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10">
									<input type="text" class="col-xs-8 col-md-8 col-sm-8 col-lg-8" id="className" name="className" value="${classification.className}" placeholder="请输入分类名称"
										reg="." data-rel="tooltip" data-original-title="必填项！" tip="必填项！" data-placement="bottom">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 col-xs-2 col-sm-2 col-lg-2 control-label no-padding-right" for="form-field-1"> <font color="red">*</font>&nbsp;分类码：
								</label>
								<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10">
									<c:if test="${classification.classCode!=null }">
										<input type="text" class="col-xs-8 col-md-8 col-sm-8 col-lg-8" id="classCode" name="classCode" value="${classification.classCode}" readonly="readonly">
									</c:if>
									<c:if test="${classification.classCode==null }">
										<input type="text" class="col-xs-8 col-md-8 col-sm-8 col-lg-8" id="classCode" name="classCode" value="${classification.classCode}" 
										reg="." data-rel="tooltip" data-original-title="必填项！" tip="必填项！" placeholder="请输入分类码">
									</c:if>
									
								
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 col-xs-2 col-sm-2 col-lg-2 control-label no-padding-right" for="form-field-1"><font color="red">*</font>&nbsp;备注：
								</label>
								<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10">
									   <textarea class="col-xs-8 col-md-8 col-sm-8 col-lg-8" type="text" rows="3" id="remark" name="remark"
										data-rule-maxlength='50' placeholder="请输入备注">${classification.remark}</textarea>
								</div>
							</div>
							<div style="padding-left:100px;text-align:center;">
								<tags:submitAndBack submitButton="submit" submitOnclick="" backOnclick="" />
							</div>
						</form>
					</div>
				</div>
			</div>
			</div>
		</div>
	</div>
	<%@include file="../../common/javascript.html"%>
	<script type="text/javascript" src="${ctx}/resources/script/js/easyvalidate/js/easy_validator.pack_platform.js"></script>
	<script type="text/javascript" src="${ctx}/resources/script/js/easyvalidate/js/jquery.bgiframe.min.js"></script>
	<script type="text/javascript">
	var returnValue=true;
	var isExtendsValidate = true; 
	function extendsValidate() {
		 var check="${check}";
		 if(check=="0"){
		 	return true;
		 }
		 $.ajax({
		      type : "get",  
		      url : "${ctx}/classification/isClassCodeExist",  
		      dataType:"json",  
		      data:{classCode:$("#classCode").val()},
		      async: false,
		      success : function(isExist) {
		      	if(isExist){
		      		window.parent.showMessage("分类码已存在！");
					if(isExist){
						returnValue=isExist;
					}
		      	}
		      	if(!isExist){
		      		returnValue = isExist;
		      	}
		      }  
		  });
		 if(returnValue){
			 return false;
		 }else{
			 return true;
		 }
	}


	
	<c:if test="${param.message==1 }">
		window.parent.setTreeDate("${classification.classKey}","${classification.classCode}");
	</c:if>

	</script>


</body>
</html>