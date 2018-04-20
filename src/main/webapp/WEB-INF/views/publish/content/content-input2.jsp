<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>行业信息</title>

<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<%@include file="../../common/meta.html"%>
<link href="${ctx}/resources/script/js/easyvalidate/css/validate_platform.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.file {
    position: relative;
    display: inline-block;
    background: ;
    border: 1px solid #99D3F5;
    border-radius: 4px;
    padding: 4px 12px;
    overflow: hidden;
    color: #1E88C7;
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
    background: #337ab7;
    border-color: #78C3F3;
    color: #004974;
    text-decoration: none;
}
</style>

<script>
	function change(input){
		var fileName = input.value;
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
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="${ctx}/index">首页</a></li>
						<li><a href="javascript:void(0);">信息发布管理</a></li>
						<li class="active">行业信息</li>
					</ul>

				</div>
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="col-md-12">
						<form id="mainForm" class="form-horizontal" method="POST" action="${ctx}/content/saveHref" enctype="multipart/form-data" onsubmit="return getContent()">
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
									<input type="text" class="col-md-12" id="title" name="title" value="${manuscript.title}" placeholder="请输入字符长度为2-60的标题名"
										reg="^.{2,60}$" data-rel="tooltip" data-original-title="请输入字符长度为2-60的标题名！" tip="请输入字符长度为2-60的标题名！" data-placement="bottom">
								</div>
								<span name="easyTip"></span>
							</div>


							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right" for="form-field-1">作者： </label> </label>
								<div class="col-md-6">
									<input type="text" class="col-md-12" id="author" name="author" value="${manuscript.author}" 
									data-rel="tooltip" placeholder="作者" reg="^.{0,200}$"   data-original-title="请输入200字符以内的作者名！" tip="请输入200字符以内的作者名！" data-placement="bottom">
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label no-padding-right" for="form-field-1">新闻分类： </label>
									<div class="col-md-6" style="height:50px;">		          	
							            <select class="col-xs-5 col-md-5" id="newType" name="newType" >
								            <c:forEach var="clzss" items="${classes}">
								            	<option value="${clzss.id}" <c:if test="${manuscript.newType==clzss.id}">selected="selected"</c:if>>${clzss.name }</option>
										    </c:forEach>
									    </select>
									 </div>
									</div>
									
							
							
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right" for="form-field-1"> 内容： </label>
								<div class="col-md-6">
									<!-- 编辑框区域 -->
									<!-- controller.jsp控制上传路径 -->
									<script id="editor" name="content" type="text/plain" style="width: 100%; height: 400px;"></script>
								</div>
							</div>

							<tags:submitAndBack submitButton="submit" submitOnclick="" backOnclick="" />

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
	     //增加内容最大值校验
		var con = $("#edui1_wordcount").text();
		if(con=='字数超出最大允许值，服务器可能拒绝保存！'){
		   alert("内容字数超出最大允许值！");
		   return false;
		}
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
				 return true;
			 }
		}
	}

		jQuery(document).ready(function() {
			//启用tooltip提示信息
			$('[data-rel=tooltip]').tooltip();
			/* var url = document.getElementById("imageUrl").value;
			if(url!=''){
				document.getElementById("imageUrl").style.display="";
				document.getElementById("titles").style.display="";
			} */
		});

		var ue = UE.getEditor('editor');
		function getContent() {
			if(UE.getEditor('editor').getContent()==""){
				alert("请输入新闻详情！");
				return false;
			}
			return true;
		}
		function setContent() {
			var content = '${manuscript.content}';
			UE.getEditor('editor').setContent(content);
		}

		setTimeout("setContent()", 1000);

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
			
			$("#mainForm").attr("action", "${ctx}/content/list/${nodeID}");
			$("#mainForm").submit();

		}

	</script>


</body>
</html>