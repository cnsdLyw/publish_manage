<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>  
<!DOCTYPE html>
<%
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>机构管理</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" 	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<%@include file="../../common/meta.html"%>
    <script src="${ctx }/resources/script/js/validate/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/js/bootbox.js" type="text/javascript"></script>
<style type="text/css">
</style>
<script type="text/javascript">
   
</script>
</head>
<body class="no-skin" >
	<%@include file="../../common/top.jsp"%>
	<div class="main-container" id="main-container">
		<%@include file="../../common/left.jsp"%>
		<div class="main-content">
			<div class="main-content-inner">
				
				<!--所在位置 -->
				<div class="breadcrumbs" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="${ctx}/index">首页</a>
						</li>
						<li></i> <a href="#">系统管理</a></li>
						<li class="active">机构管理</li>
					</ul>
				</div>
				
					<div class="col-md-12">
						<div class="row">
							<div class="col-sm-4" style="height:500px;">
								<div class="widget-box widget-color-blue2">
									<div class="widget-header">
										<h4 class="widget-title lighter smaller">机构分类</h4>
									</div>

									<div class="widget-body" id="test">
										<div class="widget-main padding-8">
										<iframe src="${ctx}/organization/leftTree?orgCode=ORG_CLASS" name="treeLoad" id="treeLoad" scrolling="yes" frameborder="0" height="725px" width="100%"></iframe>
										</div>
									</div>
								</div>
							</div>
							<div class="col-sm-8" style="padding-left: 0;">
								<div class="widget-box widget-color-blue2">
									<div class="widget-header">
										<h4 class="widget-title lighter smaller">机构内容</h4>
									</div>

									<div class="widget-body">
										<div class="widget-main padding-8">
										<iframe src="${ctx }/organization/editOrganization?orgCode=${orgCode}" id="main_content_organization" name="main_content_organization" scrolling="yes" frameborder="0" height="725px" width="100%"></iframe>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div><!-- end col-md-12 -->
				</div><!--end  main-content-inner -->
			</div><!-- end page-content -->

		<%@include file="../../common/footer.jsp"%>
	</div><!-- end main-container -->
	<%@include file="../../common/javascript.html"%>

	
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		jQuery(function($) {
			
			/*表格全选操作加载*/
			var active_class = 'active';
			$('#dynamic-table > thead > tr > th input[type=checkbox]').eq(0).on(
					'click',
					function() {
						var th_checked = this.checked;//checkbox inside "TH" table header

						$(this).closest('table').find('tbody > tr').each(
								function() {
									var row = this;
									if (th_checked)
										$(row).addClass(active_class).find(
												'input[type=checkbox]').eq(0)
												.prop('checked', true);
									else
										$(row).removeClass(active_class).find(
												'input[type=checkbox]').eq(0)
												.prop('checked', false);
						});
			});

			$('#dynamic-table').on('click', 'td input[type=checkbox]',
					function() {
						var $row = $(this).closest('tr');
						if (this.checked)
							$row.addClass(active_class);
						else
							$row.removeClass(active_class);
			});
			
			

		});//end jQuery
	
	</script>

</body>
</html>