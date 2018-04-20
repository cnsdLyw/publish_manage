<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>  
<!DOCTYPE html>
<%
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>系统配置</title>
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
<body class="no-skin">
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
						<li class="active">配置管理</li>
					</ul>
				</div>
				<!-- Modal -->
				<div class="modal fade" id="templatemo_modal" tabindex="-1" user="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				        <h4 class="modal-title" id="myModalLabel">查看用户详细信息</h4>
				      </div>
				      <div class="modal-body" id="detailContent">
				      	
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-info" data-dismiss="modal">关闭</button>
				      </div>
				    </div>
				  </div>
				</div>
				
					<div class="col-md-12">
						<div class="row">
							<div class="col-sm-4" style="height:500px;">
								<div class="widget-box widget-color-blue2">
									<div class="widget-header">
										<h4 class="widget-title lighter smaller">系统配置分类</h4>
									</div>

									<div class="widget-body" id="test">
										<div class="widget-main padding-8">
											<ul id="ressubTree"  class="ztree"></ul>
										</div>
										<div class="col-sm-8 col-md-9 col-lg-10" id="metaContent">
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

	<div id="rMenu" class="menuContent" style="display:none;">
		<ul class="submenu can-scroll ace-scroll scroll-disabled" style="" id="rmul">
			<li class="hover" id="m_add" onclick="addTreeNode();">增加</li>
			<li class="hover" id="m_del" onclick="removeTreeNode();">删除</li>
		</ul>
	</div> 
	<div id="menuContent" class="menuContent" style="display:none;">
		<ul id="ClassTree" class="ztree" style="margin-top:0; min-width:160px;"></ul>
	</div>

</body>
</html>
<%@include file="../../common/javascript.html"%>
<script type="text/javascript" src="${ctx}/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.ztree.exedit-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/js/myValidate.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript" src="${ctx}/js/configuration.js"></script>
<script type="text/javascript" src="${ctx}/js/select2.js"></script>