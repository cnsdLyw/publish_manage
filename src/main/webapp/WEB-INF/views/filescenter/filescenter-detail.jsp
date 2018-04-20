<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>资源文件详情</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />


<%@include file="../common/meta.html"%>
<style type="text/css">

/*状态为已接收 未接收的图标控制*/
.cir {
	width: 18px;
	height: 16px;
	overflow: hidden;
	cursor: pointer;
}

.Ru {
	background: url(/images/mail2757b6.png) -48px 0 no-repeat;
}

.Rr {
	background: url(/images/mail2757b6.png) -48px -16px no-repeat;
}
</style>
</head>

<body class="no-skin">
	<%@include file="../common/top.jsp"%>

	<div class="main-container" id="main-container">
		<%@include file="../common/left.jsp"%>
		<div class="main-content">
			<div class="main-content-inner">
				<!--所在位置 -->
				<div class="breadcrumbs" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="${ctx}/index">首页</a></li>
						<li></i> <a href="#">系统管理</a></li>
						<li class="active">资源文件详情</li>
					</ul>
				</div>
				
				</br>

				<form action="#" method="post" class="form-horizontal">
					<div class="col-md-12">
						<div class="form-group">
							<label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 文件名称： </label>
							<div class="col-sm-11">
								<label class="col-sm-12" style="padding-top: 7px;"> ${filescenter.name} </label>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-1 control-label no-padding-right" for="form-field-1">文件大小： </label>
							<div class="col-sm-11">
								<label class="col-sm-12" style="padding-top: 7px;"> ${filescenter.size} </label>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-1 control-label no-padding-right" for="form-field-1">文件类型： </label>
							<div class="col-sm-10">
								<label class="col-sm-12" style="padding-top: 7px;"> 
									 ${filescenter.type}
								</label>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-1 control-label no-padding-right" for="form-field-1">附件： </label>
							<div class="col-sm-10">
								<label class="col-sm-12" style="padding-top: 7px;" id="otherList"> 
										<a href="${rctx}/${filescenter.path}" title="点击下载" target="_blank"
											title="${filescenter.name}">${filescenter.name}</a>&nbsp;&nbsp;&nbsp;
								</label>
							</div>
						</div>


						<tags:back backOnclick="" />


						<div class="vspace-6-sm"></div>
					</div>
					<!-- end col-md-12-->
				</form>

			</div>
			<!--end  main-content-inner -->
		</div>
		<!-- end main-content -->

		<%@include file="../common/footer.jsp"%>

	</div>
	<!-- end main-container -->

	<%@include file="../common/footer.jsp"%>

	</div>
	<!-- end main-container -->


	<%@include file="../common/javascript.html"%>

	<!-- inline scripts related to this page -->
	<script type="text/javascript">

		
	</script>

</body>
</html>
