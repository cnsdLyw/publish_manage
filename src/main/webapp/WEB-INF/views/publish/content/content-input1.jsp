<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>友情链接</title>

<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<%@include file="../../common/meta.html"%>
<link href="${ctx}/resources/script/js/easyvalidate/css/validate_platform.css" rel="stylesheet" type="text/css" />
<style type="text/css">
</style>

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
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="${ctx}/index">首页</a></li>
						<li><a href="javascript:void(0);">信息发布管理</a></li>
						<li class="active">友情链接</li>
					</ul>

				</div>
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="col-md-12">
						<form id="mainForm" class="form-horizontal" method="POST" action="${ctx}/content/saveHref">
							<input type="hidden" id="id" name="id" value="${manuscript.id }" />
							<input type="hidden" id="nodeID" name="nodeID" value="${nodeID }" />
							<input type="hidden" id="masterID" name="masterID" value="${nodeID}" />
							<input type="hidden" id="operate" name="operate" value="${operate }"/>
							<!-- 列表页面检索条件 -->
							<input type="hidden" id="pageNo" name="pageNo" value="${pageNo }" />
							<input type="hidden" id="pageSize" name="pageSize" value="${pageSize }" />
							<input type="hidden" id="queryOrderBy" name="queryOrderBy" value="${queryOrderBy }" />
							<input type="hidden" id="queryOrderType" name="queryOrderType" value="${queryOrderType }" />
							<input type="hidden" id="query_all_like" name="query_all_like" value="${query_all_like }" />
					
							
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right" for="form-field-1"><font color="red">*</font>&nbsp;标题：
								</label>
								<div class="col-md-6 ">
									<input type="text" class="col-md-12" id="title" name="title" value="${manuscript.title}" placeholder="请输入字符数为2-60的标题名称"
										reg="^.{2,60}$" data-rel="tooltip" data-original-title="请输入字符数为2-60的标题名称！" tip="请输入字符数为2-60的标题名称！" data-placement="bottom">
								</div>
								<span name="easyTip"></span>
							</div>


							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right" for="form-field-1"> <font color="red">*</font>&nbsp;链接： </label> </label>
								<div class="col-md-6">
									<input type="text" class="col-md-12" id="url" name="url" value="${manuscript.url}" 
									reg="([a-z0-9][a-z0-9\-]*?\.(?:com|cn|net|org|gov|info|la|cc|co)(?:\.(?:cn|jp))?)$" data-rel="tooltip" data-original-title="请输入正确域名" tip="请输入正确地址" placeholder="请输入正确地址">
								</div>
							</div>




							<tags:submitAndBack submitButton="submit" submitOnclick="" backOnclick="javascript:backList();" />

						</form>
					</div>
				</div>
			</div>
		</div>
		<%@include file="../../common/footer.jsp"%>
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
			      type : "POST",  
			      url : "${ctx}/content/isContentNameExistWithId?nodeId=${nodeID}",  
			      dataType:"json",  
			      data:{id:$("#id").val(),title:$("#title").val()},
			      async: false,
			      success : function(isExist) {
			      	if(isExist){
			      		bootbox.setDefaults("locale", "zh_CN");
						bootbox.dialog({
							message : "<span class='bigger-110'>链接新闻标题已存在！</span>",
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
			  	//判断url
			     var url = $("#url").val();
			     if(!url.startsWith("http:")){
			     	$("#url").val("http://"+url)
			     }
				 return true;
			 }
		}else{
			$.ajax({//验证登录名
			      type : "POST",  
			      url : "${ctx}/content/isContentNameExist?nodeId=${nodeID}",  
			      dataType:"json",  
			      data:{title:$("#title").val()},
			      async: false,
			      success : function(isExist) {
			      	if(isExist){
			      		bootbox.setDefaults("locale", "zh_CN");
						bootbox.dialog({
							message : "<span class='bigger-110'>链接标题已存在！</span>",
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
			     //判断url
			     var url = $("#url").val();
			     if(!url.startsWith("http:")){
			     	$("#url").val("http://"+url)
			     }
				 return true;
			 }
		}
	}


		jQuery(document).ready(function() {
			//启用tooltip提示信息
			$('[data-rel=tooltip]').tooltip();
		});
		
		/* var ue = UE.getEditor('editor');
		function getContent() {
			alert(UE.getEditor('editor').getContent());
		}
		function setContent() {
			var content = '${manuscript.content}';
			UE.getEditor('editor').setContent(content);
		}
		setTimeout("setContent()", 1000);
 */

			//删除附件
		function deleteAttach(id, name, path, size, attlist) {
			var pathStr = $("#" + path).val();
			if (pathStr != '') {
				bootbox.setDefaults("locale", "zh_CN");
				bootbox.confirm("确定要删除附件吗？", function(result) {
					if (result) {//确认
						document.getElementById(attlist).innerHTML = '';
						document.getElementById(id).value = '';
						document.getElementById(name).value = '';
						document.getElementById(path).value = '';
						document.getElementById(size).value = '';
					}
				});
			}
		}
		
		
		
		function backList() {
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
			/* $("#mainForm").attr("action", "${ctx}/content/list/${nodeID}");
			$("#mainForm").submit(); */
			//history.back();
			
			window.location.href="${ctx}/content/list/${nodeID}";

		}

	</script>


</body>
</html>