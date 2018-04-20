<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>留言板详情</title>

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
						<li class="active">留言板详情</li>
					</ul>

				</div>
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="col-md-12">
						<form class="form-horizontal" id="mainForm" name="mainForm" action="${ctx}/faq/save" method="post">
							<input type="hidden" id="id" name="id" value="${faq.id }" />

							<!-- 列表页面检索条件 -->
							<input type="hidden" id="pageNo" name="pageNo" value="${pageNo }" />
							<input type="hidden" id="pageSize" name="pageSize" value="${pageSize }" />
							<input type="hidden" id="queryOrderBy" name="queryOrderBy" value="${queryOrderBy }" />
							<input type="hidden" id="queryOrderType" name="queryOrderType" value="${queryOrderType }" />
							<input type="hidden" id="query_all_like" name="query_all_like" value="${query_all_like }" />

							<div class="form-group">
								<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><font color="red">*</font>&nbsp;标题：
								</label>
								<div class="col-sm-10 controls">
									<div class="col-md-6 ">
										<input type="text" readonly="readonly" class="col-sm-12" id="title" name="title" value="${faq.title}"
											placeholder="标题" reg="." data-rel="tooltip" data-original-title="必填项！" tip="必填项！" data-placement="bottom">
									</div>
									<div class="col-md-6">&nbsp;</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label no-padding-right" for="form-field-1">姓名：</label>
								<div class="col-sm-10 controls">
									<div class="col-md-6 ">
										<input type="text" readonly="readonly" class="col-sm-12" id="anonymous" name="anonymous" value="${faq.anonymous}"
											placeholder="用户姓名" data-placement="bottom">
									</div>
									<div class="col-md-6">&nbsp;</div>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> <font color="red">*</font>&nbsp;邮箱：
								</label>
								<div class="col-sm-10 controls">
									<div class="col-md-6">
										<input type="text" readonly="readonly" class="col-sm-12" id="email" name="email" value="${faq.email}" placeholder="用户邮箱">

									</div>
									<div class="col-md-6">&nbsp;</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 联系电话： </label>
								<div class="col-sm-10 controls">
									<div class="col-md-6">
										<input type="text" readonly="readonly" class="col-sm-12" id="phone" name="phone" value="${faq.phone}">
									</div>
									<div class="col-md-6">&nbsp;</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 内容： </label>
								<div class="col-sm-10 controls">
									<div class="col-md-12">
										<textarea type="text" id="content" readonly="readonly" name="content" class="col-md-6" data-placement="bottom" rows="7">${faq.content }</textarea>
									</div>
									<div class="col-md-6">&nbsp;</div>
								</div>
							</div>
							<c:if test="${faq.answerContent!=null }">
							<div class="form-group">
								<label class="col-sm-2 control-label no-padding-right" for="form-field-1">&nbsp;回复的留言：
								</label>
								<div class="col-sm-10 controls">
									<div class="col-md-12">
										<textarea type="text" readonly="readonly" id="answerContent" name="answerContent" class="col-md-6" data-placement="bottom" rows="7">${faq.answerContent }</textarea>
										<!-- <h6 class="col-md-12" style="text-align: left;">您还可以输入<span id="word">200</span>个字符。</h6> -->
									</div>
									<div class="col-md-6">&nbsp;</div>
								</div>
						</div>
						</c:if>
							<c:if test="${message==1}">
						<div class="form-group">
								<label class="col-sm-2 control-label no-padding-right" for="form-field-1">&nbsp;回复留言：
								</label>
								<div class="col-sm-10 controls">
									<div class="col-md-12">
										<textarea type="text" id="answerContent" name="answerContent" class="col-md-6" data-placement="bottom" rows="7"></textarea>
										<h6 class="col-md-12" style="text-align: left;">您还可以输入<span id="word">200</span>个字符。</h6>
									</div>
									<div class="col-md-6">&nbsp;</div>
								</div>
						</div>
						<tags:submitAndBack submitButton="submit" submitOnclick="" backOnclick="javascript:backList();" />
						</c:if>
						<c:if test="${message==0}">
							<tags:back backOnclick="backList();" />
						</c:if>
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
		$(function(){
			var textLength = 200;
		  	$("#answerContent").keyup(function(){
		   		var len = $(this).val().length;
		   		if(len > (textLength-1)){
		    		$(this).val($(this).val().substring(0,textLength));
		   		}
		   		var num = textLength - len;
		   		if(num>=0)
		   		$("#word").text(num);
		  	});
		 });
		var isExtendsValidate = true; //如果要试用扩展表单验证的话，该属性一定要申明
		function extendsValidate() { //函数名称，固定写法

			return true;
		}

		jQuery(document).ready(function() {
			//启用tooltip提示信息
			$('[data-rel=tooltip]').tooltip();
		});

		function backList() {
			//方式2实现返回
			$("form")
					.find(
							"input[reg],input[rel],select[reg],select[rel],textarea[reg],textarea[rel]")
					.each(function() {
						$(this).removeAttr("reg");
						$(this).removeAttr("rel");
					});
			$("#mainForm").attr("action", "${ctx}/faq/list");
			$("#mainForm").submit();
		}
	</script>


</body>
</html>