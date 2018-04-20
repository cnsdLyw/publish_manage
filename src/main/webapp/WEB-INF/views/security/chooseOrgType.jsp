<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
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
		<%@include file="../common/meta.html"%>
<script type="text/javascript">
</script>		
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
					<%@include file="../common/setColor.html"  %>
					<BR/><BR/><BR/><BR/><BR/><BR/>
					<div class="row text-center">
						<h1>欢迎使用复合出版数据传递管理${loginName}系统</h1>
					</div>
						<form id="mainForm" class="form-horizontal templatemo-create-account templatemo-container"
							 action="${ctx}/security/saveOrgType">
							 <input type="hidden" id="loginName" name="loginName" value="${loginName}"/>
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<div class="row" controls>
									<div class="col-xs-12 col-sm-12  center form-group"">
										请选择需要登录的机构类别：
										<select class="right" id="orgType" name="orgType" data-rule-required='true' style="height:40px;width:200px">
						            		<c:forEach var="clzss" items="${classes}">
						            			<option value="${clzss.id}">${clzss.name }</option>
								        	</c:forEach>
							        	</select>
									</div>			
								</div><!-- /.row -->
							</div><!-- /.col -->
							<BR/><BR/><BR/>
							<div class="col-xs-12" >
								<div class="row">
								<div class="col-xs-12 col-sm-12  center">
									<button class="btn btn-info" type="submit">
										<i class="ace-icon fa fa-check bigger-110"></i>
										提交
									</button>

									&nbsp; &nbsp; &nbsp;
									<button class="btn" type="button" onclick="javascript:logout()">
										<i class="ace-icon fa fa-undo bigger-110"></i>
										取消
									</button>
								</div>
								</div>
							</div>
						</form>
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
		<%@include file="../common/footer.jsp"  %>
			
		</div><!-- /.main-container -->

	<%@include file="../common/javascript.html"  %>
	</body>
	<script type="text/javascript">
		function logout(){
			window.location.href="${ctx}/logout"; 
		}
			jQuery(function($) {
				/*bootbox.setDefaults("locale", "zh_CN");
				bootbox.dialog({
					message : "<span class='bigger-110'>${errorMessage}</span>",
					closeButton: false,
					buttons : {
						"click" : {
							"label" : "确定",
							"className" : "btn-sm btn-primary",
							"callback" : function() {
								
							}
						}
					}
				});*/
			
			});
		</script>
</html>
