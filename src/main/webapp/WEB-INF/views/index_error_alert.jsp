<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="common/taglibs.jsp"%>
<%@page import="com.litc.common.util.Constant"%>
<c:set var="login_role" value="${sessionScope.loginRole }" />

<%
String loginame=(String) session.getAttribute("loginname"); 
%>
<!DOCTYPE html>
<html lang="zh-CN"> 
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>复合出版数据传递系统</title>
		<%@include file="./common/meta.html"%>
	</head>

	<body class="no-skin">

		<!-- /section:basics/navbar.layout -->
		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>
				
			<div class="main-content">
			<div class="main-content-inner">
				<!-- #section:basics/content.breadcrumbs -->
				<div class="breadcrumbs" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">首页</a>
						</li>
					</ul>
					<!-- /.breadcrumb -->
					<!-- #section:basics/content.searchbox -->
					
					<!-- 
					<div class="nav-search" id="nav-search">
						<form class="form-search">
							<span class="input-icon"> <input type="text"
								placeholder="Search ..." class="nav-search-input"
								id="nav-search-input" autocomplete="off" /> <i
								class="ace-icon fa fa-search nav-search-icon"></i> </span>
						</form>
					</div>
					 -->
					<!-- /.nav-search -->

					<!-- /section:basics/content.searchbox -->
				</div>
				
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<!-- #section:settings.box -->
					<%@include file="./common/setColor.html"  %>
					<BR/><BR/><BR/><BR/><BR/><BR/>
					<div class="row text-center">
						<h1>欢迎使用复合出版数据传递管理系统</h1>
					</div>
						<c:if test="${'4'!=login_role}">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<div class="row">
									<div class="col-xs-12 col-sm-6 widget-container-col">
										<div class="widget-box">
											<div class="widget-header">
												<h4 class="widget-title"><i class="menu-icon fa fa-envelope"></i> 消息</h4>
												<!-- #section:custom/widget-box.toolbar -->
												<div class="widget-toolbar">
													<a href="#" data-action="fullscreen" class="orange2" title="全屏">
														<i class="ace-icon fa fa-expand"></i>
													</a>
													<a href="${ctx}/message/list?type=2" title="点击查看更多">
														<i class="ace-icon fa fa-plus"></i>
													</a>
													<a href="#" data-action="collapse" title="隐藏">
														<i class="ace-icon fa fa-chevron-up"></i>
													</a>
													<a href="#" data-action="close" title="关闭">
														<i class="ace-icon fa fa-times"></i>
													</a>
												</div>

												<!-- /section:custom/widget-box.toolbar -->
											</div>

											<div class="widget-body">
												<div class="widget-main no-padding">
													<table class="table table-bordered table-striped">
														<thead class="thin-border-bottom">
															<tr>
																<th><i class="ace-icon fa fa-caret-right blue"></i>发送者</th>
																<th><i class="ace-icon fa fa-caret-right blue"></i>消息类型</th>
																<th class="hidden-480"><i class="ace-icon fa fa-caret-right blue"></i>发送时间</th>
															</tr>
														</thead>
														<tbody>
														</tbody>
													</table>
												</div>
											</div>
										</div>

										<!-- /section:custom/widget-box -->
									</div>

									<div class="col-xs-12 col-sm-6 widget-container-col">
												<!-- #section:custom/widget-box -->
												<div class="widget-box mainIndexDiv">
													<div class="widget-header">
														<h4 class="widget-title"><i class="menu-icon fa fa-book fa-4"></i> 书目</h4>
														<!-- #section:custom/widget-box.toolbar -->
														<div class="widget-toolbar">
															<a href="#" data-action="fullscreen" class="orange2" title="全屏">
																<i class="ace-icon fa fa-expand"></i>
															</a>
															<a href="${ctx}/companybook/list" title="点击查看更多">
																<i class="ace-icon fa fa-plus"></i>
															</a>
															<a href="#" data-action="collapse" title="隐藏">
																<i class="ace-icon fa fa-chevron-up"></i>
															</a>
															<a href="#" data-action="close" title="关闭">
																<i class="ace-icon fa fa-times"></i>
															</a>
														</div>
													</div>
													<div class="widget-body">
														<div class="widget-main no-padding">
															<table class="table table-bordered table-striped">
																<thead class="thin-border-bottom">
																	<tr>
																		<th><i class="ace-icon fa fa-caret-right blue"></i>书名</th>
																		<th><i class="ace-icon fa fa-caret-right blue"></i>ISBN</th>
																		<th class="hidden-480"><i class="ace-icon fa fa-caret-right blue"></i>作者</th>
																	</tr>
																</thead>
		
																<tbody>
																</tbody>
															</table>
														</div>
													</div>
												</div>
												<!-- /section:custom/widget-box -->
									</div><!-- /.span -->
								</div><!-- /.row -->
							</div><!-- /.col -->
							</c:if>
							<c:if test="${'1'==login_role||'4'==login_role}">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<div class="row">
									<div class="col-xs-12 col-sm-6 widget-container-col">
										<!-- #section:custom/widget-box -->
												<div class="widget-box mainIndexDiv">
													<div class="widget-header">
														<h4 class="widget-title"><i class="menu-icon fa fa-pencil-square-o"></i> 加工任务</h4>
														<!-- #section:custom/widget-box.toolbar -->
														<div class="widget-toolbar">
															<a href="#" data-action="fullscreen" class="orange2" title="全屏">
																<i class="ace-icon fa fa-expand"></i>
															</a>
															<a href="${ctx}/processTask/list" title="点击查看更多">
																<i class="ace-icon fa fa-plus"></i>
															</a>
															<a href="#" data-action="collapse" title="隐藏">
																<i class="ace-icon fa fa-chevron-up"></i>
															</a>
															<a href="#" data-action="close" title="关闭">
																<i class="ace-icon fa fa-times"></i>
															</a>
														</div>
													</div>
													<div class="widget-body">
														<div class="widget-main no-padding">
															<table class="table table-bordered table-striped">
																<thead class="thin-border-bottom">
																	<tr>
																		<th><i class="ace-icon fa fa-caret-right blue"></i>任务名称</th>
																		<th><i class="ace-icon fa fa-caret-right blue"></i>图书名称</th>
																		<th class="hidden-480"><i class="ace-icon fa fa-caret-right blue"></i>状态</th>
																	</tr>
																</thead>
		
																<tbody>
																</tbody>
															</table>
														</div>
													</div>
												</div>
										
										
									</div><!-- /.span -->
									<div class="col-xs-12 col-sm-6 widget-container-col">
									</div>
								</div>
							</div>
							</c:if>
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
		<%@include file="./common/footer.jsp"  %>
			
		</div><!-- /.main-container -->

	<%@include file="./common/javascript.html"  %>
	</body>
	<script type="text/javascript">
			jQuery(function($) {
				bootbox.setDefaults("locale", "zh_CN");
				bootbox.dialog({
					message : "<span class='bigger-110'>${errorMessage}</span>",
					closeButton: false,
					buttons : {
						"click" : {
							"label" : "确定",
							"className" : "btn-sm btn-primary",
							"callback" : function() {
								if('${flag}'=='1'){
									window.location.href="${ctx}/security/setOrgType?loginName=${loginName}"; 
								}else{
									window.location.href="${ctx}/logout"; 
								}
							}
						}
					}
				});
			
			});
		</script>
</html>
