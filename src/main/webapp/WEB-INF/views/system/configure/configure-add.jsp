<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>配置编辑</title>

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
						<form id="mainForm" class="form-horizontal" method="POST" action="${ctx}/sysConfirure/save?typeid=${typeid}">
							<input type="hidden" id="id" name="id" value="${sysConfigure.id }" />
							<!-- 列表页面检索条件 -->
							<input type="hidden" id="pageNo" name="pageNo" value="${pageNo }" />
							<input type="hidden" id="pageSize" name="pageSize" value="${pageSize }" />
							<input type="hidden" id="queryOrderBy" name="queryOrderBy" value="${queryOrderBy }" />
							<input type="hidden" id="queryOrderType" name="queryOrderType" value="${queryOrderType }" />
							<input type="hidden" id="query_all_like" name="query_all_like" value="${query_all_like }" />
					
							<div class="form-group">
								<label class="col-md-2 col-xs-2 col-sm-2 col-lg-2 control-label no-padding-right" for="form-field-1"><font color="red">*</font>&nbsp;配置名称：
								</label>
								<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10">
									<input type="text" class="col-xs-8 col-md-8 col-sm-8 col-lg-8" id="configureName" name="configureName" value="${sysConfigure.configureName}" placeholder="请输入配置名称"
										reg="." data-rel="tooltip" data-original-title="必填项！" tip="必填项！" data-placement="bottom">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 col-xs-2 col-sm-2 col-lg-2 control-label no-padding-right" for="form-field-1"> <font color="red">*</font>&nbsp;配置编码：
								</label>
								<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10">
									<input type="text" class="col-xs-8 col-md-8 col-sm-8 col-lg-8" id="configureCode" name="configureCode" value="${sysConfigure.configureCode}" 
									reg="." data-rel="tooltip" data-original-title="必填项！" tip="必填项！" placeholder="请输入配置编码">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 col-xs-2 col-sm-2 col-lg-2 control-label no-padding-right" for="form-field-1"> <font color="red">*</font>&nbsp;配置数值： 
								</label>
								<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10">
									<input type="text" class="col-xs-8 col-md-8 col-sm-8 col-lg-8" id="configureValue" name="configureValue" value="${sysConfigure.configureValue}" 
									reg="." data-rel="tooltip" data-original-title="必填项！" tip="必填项！" placeholder="请输入配置值">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 col-xs-2 col-sm-2 col-lg-2 control-label no-padding-right" for="form-field-1"><font color="red">*</font>&nbsp;配置备注：
								</label>
								<div class="col-xs-10 col-md-10 col-sm-10 col-lg-10">
									   <textarea class="col-xs-8 col-md-8 col-sm-8 col-lg-8" type="text" rows="3" id="remark" name="remark"
										data-rule-maxlength='50' placeholder="请输入配置备注">${sysConfigure.remark}</textarea>
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

	<script src="${ctx }/resources/script/ueditor/ueditor.config.js" type="text/javascript"></script>
	<script src="${ctx }/resources/script/ueditor/ueditor.all.js" type="text/javascript"></script>
	<script src="${ctx }/resources/script/ueditor/lang/zh-cn/zh-cn.js" type="text/javascript"></script>
	<script type="text/javascript">
	var returnValue=true;
	var isExtendsValidate = true; //如果要试用扩展表单验证的话，该属性一定要申明
	function extendsValidate() { //函数名称，固定写法
		if($("#id").val()){
			 $.ajax({//验证登录名
			      type : "get",  
			      url : "${ctx}/sysConfirure/isSysConfigureNameExistWithId",  
			      dataType:"json",  
			      data:{id:$("#id").val(),configureName:$("#configureName").val()},
			      async: false,
			      success : function(isExist) {
			      	if(isExist){
			      		bootbox.setDefaults("locale", "zh_CN");
						bootbox.dialog({
							message : "<span class='bigger-110'>配置名称已存在！</span>",
							buttons : {
								"click" : {
									"label" : "确定",
									"className" : "btn-sm btn-primary"
								}
							}
						});
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
		}else{
			$.ajax({//验证登录名
			      type : "get",  
			      url : "${ctx}/sysConfirure/isSysConfigureNameExist",  
			      dataType:"json",  
			      data:{configureName:$("#configureName").val()},
			      async: false,
			      success : function(isExist) {
			      	if(isExist){
			      		bootbox.setDefaults("locale", "zh_CN");
						bootbox.dialog({
							message : "<span class='bigger-110'>配置名称已存在！</span>",
							buttons : {
								"click" : {
									"label" : "确定",
									"className" : "btn-sm btn-primary"
								}
							}
						});
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
	}

		
		/*function backList() {
			//方式1实现返回
// 			var path="${ctx}/content/list/${nodeID}";
// 			var serializeHidden = $("#mainForm").find(":hidden").serializeArray();
// 			var param ="1=1";
// 			$.each(serializeHidden, function() {
// 				param=param+"&"+this.name+"="+this.value;
				
// 			});
// 			window.location.href=path+"?"+param;

			//方式2实现返回
			$("form").find("input[reg],input[rel],select[reg],select[rel],textarea[reg],textarea[rel]").each(function(){
				$(this).removeAttr("reg");
				$(this).removeAttr("rel");
			});
			
			$("#mainForm").attr("action", "${ctx}/content/list/${nodeID}");
			$("#mainForm").submit();

		}*/

	</script>


</body>
</html>