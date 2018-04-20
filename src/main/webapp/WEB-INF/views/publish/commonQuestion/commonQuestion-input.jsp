<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>常见问题编辑</title>

<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<%@include file="../../common/meta.html"%>
<link href="${ctx}/resources/script/js/easyvalidate/css/validate.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="${ctx}/resources/zTree_v3/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
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
						<li class="active">常见问题编辑</li>
					</ul>

				</div>
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="col-md-12">
						<form class="form-horizontal validate-form" id="mainForm" name="mainForm"role="form" action="${ctx}/commonQuestion/save" method="post" command="commonQuestion">
							<div class="form-group">
								<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><font color="red">*</font>&nbsp;问题描述：
								</label>
								<div class="col-sm-10 controls">
									<div class="col-md-6 ">
										<input type="text" class="col-sm-12" id="question" name="question" placeholder="请输入字符小于200的问题描述！" data-original-title="" reg="." tip="请输入字符小于200的问题！" data-placement="bottom">
									</div>
									<div class="col-md-6">&nbsp;</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><font color="red">*</font>&nbsp;问题解答：
								</label>
								<div class="col-sm-10 controls">
									<div class="col-md-6 ">
									 	<textarea rows="8" cols="8" class="col-md-12 col-sm-12" style=padding-top:3px;padding-bottom:3px;" id="content" name="content" placeholder=""
										reg="." data-original-title="请输入问题解答！" tip="请输入问题解答！"></textarea>
									</div>
									<div class="col-md-6">&nbsp;</div>
								</div>
							</div>
							<div class="clearfix form-actions"style="background-color:#FFF;border-top:0px solid #E5E5E5;margin-left: 30%;">
								<div style="margin-top: 30px;">
									<button class="btn btn-info" id="submitButton" type="submit">
										<i class="ace-icon fa fa-check bigger-110"></i> 提交
									</button>
	
									&nbsp; &nbsp; &nbsp;
									<button class="btn" type="button"
										onclick="javascript:backList();">
										<i class="ace-icon fa fa-undo bigger-110"></i> 返回
									</button>
								</div>
							</div>
	                      
						</form>
					</div>
				</div>
			</div>
		</div>
		<%@include file="../../common/footer.jsp"%>
	</div>
	<%@include file="../../common/javascript.html"%>
</body>
	<script type="text/javascript" src="${ctx}/resources/script/js/easyvalidate/js/easy_validator.pack.js"></script>
	<script type="text/javascript" src="${ctx}/resources/script/js/easyvalidate/js/jquery.bgiframe.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/script/js/common.js"></script>
	<script type="text/javascript" src="${ctx}/resources/zTree_v3/js/jquery.ztree.core-3.5.min.js"></script>
	<script src="${ctx}/resources/script/js/nav.js" type="text/javascript"></script>
	
<script type="text/javascript">


	function backList(){
		history.back() ;
	}	
</script>
</html>