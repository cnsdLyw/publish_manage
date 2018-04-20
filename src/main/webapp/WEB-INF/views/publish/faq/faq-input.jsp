<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>留言板编辑</title>

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
						<li class="active">留言板编辑</li>
					</ul>

				</div>
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="col-md-12">
						<form id="mainForm" class="form-horizontal" method="POST" action="${ctx}/faq/save">
							<input type="hidden" id="id" name="id" value="${faq.id }" />
							<!-- 列表页面检索条件 -->
							<input type="hidden" id="pageNo" name="pageNo" value="${pageNo }" />
							<input type="hidden" id="pageSize" name="pageSize" value="${pageSize }" />
							<input type="hidden" id="queryOrderBy" name="queryOrderBy" value="${queryOrderBy }" />
							<input type="hidden" id="queryOrderType" name="queryOrderType" value="${queryOrderType }" />
							<input type="hidden" id="query_all_like" name="query_all_like" value="${query_all_like }" />
							
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right" for="form-field-1"><font color="red">*</font>&nbsp;姓名：
								</label>
								<div class="col-md-6 ">
									<input type="text" class="col-md-12" id="anonymous" name="anonymous" value="${faq.anonymous}"
										placeholder="用户姓名" reg="." data-rel="tooltip" data-original-title="必填项！" tip="必填项！" data-placement="bottom">
								</div>
								<span name="easyTip"></span>
							</div>


							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right" for="form-field-1"> <font color="red">*</font>&nbsp;邮箱：
								</label>
								<div class="col-md-6 ">
									<input type="text" class="col-md-12" id="email" name="email" value="${faq.email}"
										reg="^[a-zA-Z0-9\._-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$" data-rel="tooltip" data-original-title="必填项！"
										tip="请输入正确的邮箱格式！" data-placement="bottom">
								</div>
								<span name="easyTip"></span>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right" for="form-field-1"> 联系电话： </label>
								<div class="col-md-6">
									<input type="text" class="col-md-12" id="phone" name="phone" value="${faq.phone}"
										reg="^$|(^((\d{11})|(\d{7,8})|(\d{4}|\d{3})-(\d{7,8}))$)" data-rel="tooltip" data-original-title="不是必填项"
										tip="请输入正确的联系电话！(支持手机和座机)" data-placement="bottom">
								</div>
								<span name="easyTip"></span>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right" for="form-field-1"> 通讯地址： </label>
								<div class="col-md-6">
									<input type="text" class="col-md-12" id="address" name="address" value="${faq.address}" placeholder="联系地址">
								</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right" for="form-field-1"> 内容： </label>
								<div class="col-md-6">
									<!-- 编辑框区域 -->
									<script id="editor" name="content" type="text/plain" style="width: 100%; height: 400px;"></script>
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
		var isExtendsValidate = true; //如果要试用扩展表单验证的话，该属性一定要申明
		function extendsValidate() { //函数名称，固定写法

			//return true;
		}

		jQuery(document).ready(function() {
			//启用tooltip提示信息
			$('[data-rel=tooltip]').tooltip();
		});

		var ue = UE.getEditor('editor');
		function getContent() {
			alert(UE.getEditor('editor').getContent());
		}
		function setContent() {
			UE.getEditor('editor').setContent('${faq.content }');
		}

		setTimeout("setContent()", 1000);

		function backList() {
			//方式2实现返回
			$("form").find("input[reg],input[rel],select[reg],select[rel],textarea[reg],textarea[rel]").each(function(){
				$(this).removeAttr("reg");
				$(this).removeAttr("rel");
			});
			$("#mainForm").attr("action", "${ctx}/faq/list");
			$("#mainForm").submit();
		}
	</script>


</body>
</html>