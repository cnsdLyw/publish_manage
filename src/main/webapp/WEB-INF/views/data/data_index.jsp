<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>  
<!DOCTYPE html>
<%
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>21包数据字典</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" 	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<%@include file="../common/meta.html"%>
    <script src="${ctx }/resources/script/bootstrap/js/bootbox.js" type="text/javascript"></script>
<style type="text/css">
</style>
<script type="text/javascript">
	var temlTableLi = "jc_book_li";
	function setTable(tableLi,tableGroupName,tableName,tableText,tableComment){
		if(temlTableLi!=""){
			$("ul.nav nav-list > li.active open").removeClass("active open");
			$("ul>li.active").removeClass("active");
		}
		$("#"+tableLi).addClass("active");
		$("#"+tableLi).parent().parent().addClass("active");
		$("#tableGroupName").html(tableGroupName);
		$("#tableText").html(tableText);
		$("#tableName").html("："+tableName);
		$("#tableCommentContent").html("<i class='ace-icon fa fa-angle-double-right'></i>&nbsp;&nbsp;"+tableComment);
		temlTableLi = tableLi;
		
		//获取表结构
		$.ajax( {  
	        type : "get",  
	        url : "${ctx}/data/getTable/?tableName="+tableName,  
	        dataType:"json",  
	        success : function(data) {  
		        var content="";
		        var statusStr="";
		        $.each(data, function (i, item) {
					statusStr+="<tr>"+
						"<td><a href=\"javascript:void(0);\">"+item.columnName+"</a></td>"+
						"<td>"+item.comment+"</td>"+
						"<td>"+item.columnNameType+"</td>"+
						"<td>"+(item.isNull=='YES'?"空":"非空")+"</td>"+
						"<td>"+item.columnKey+"</td>"+
						"<td class=\"hidden-480\">"+item.defaultValue+"</td>"+
						"<td class=\"hidden-480\">"+item.columnExtra+"</td>"+
					"</tr>";	        	
		        });
		        
		        $("#columnsContent").html(statusStr);
		        
		         
	        }  
	    });
	}
</script>
</head>
<body class="no-skin">
	<div id="navbar" class="navbar navbar-default">
			<div class="navbar-container" id="navbar-container">
				<!-- #section:basics/sidebar.mobile.toggle -->
				<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
					<span class="sr-only">Toggle sidebar</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<!-- /section:basics/sidebar.mobile.toggle -->
				<div class="navbar-header pull-left">
					<!-- #section:basics/navbar.layout.brand -->
					<a href="${ctx }/index" class="navbar-brand">
						<small>复合出版数据传递系统—数据字典
						</small>
					</a>
				</div>
			</div><!-- /.navbar-container -->
		</div>
</body>
	<div class="main-container" id="main-container">
		<%@include file="left.jsp"%>
		<div class="main-content">
			<div class="main-content-inner">
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
					</script>

					<ul class="breadcrumb">
						<li>
							<i class="ace-icon fa fa-home home-icon"></i>
							<a href="javascript:void(0);">21包数据字典</a>
						</li>
						<li>
							<a href="javascript:void(0);" id="tableGroupName">Tables</a>
						</li>
						<li class="active" id="tableText">Simple &amp; Dynamic</li>
					</ul><!-- /.breadcrumb -->

					<!-- #section:basics/content.searchbox -->
					<!-- /section:basics/content.searchbox -->
				</div>
				<div class="page-content">
						<div class="page-header">
							<h1>
								Tables<font size="4px" id="tableName"></font>
								<small id="tableCommentContent">
								</small>
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<div class="row">
									<div class="col-xs-12">
										<table id="simple-table" class="table table-striped table-bordered table-hover">
											<thead>
												<tr>
													<th>字段名</th>
													<th>中文名称</th>
													<th>数据类型（精度范围）</th>
													<th>空/非空</th>
													<th>约束条件</th>
													<th class="hidden-480">默认值</th>
													<th class="hidden-480">自动递增</th>
												</tr>
											</thead>

											<tbody id="columnsContent">
											</tbody>
										</table>
									</div><!-- /.span -->
								</div><!-- /.row -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
			</div><!--end  main-content-inner -->
		</div><!-- end page-content -->

		<%@include file="../common/footer.jsp"%>
	</div><!-- end main-container -->
	<%@include file="../common/javascript.html"%>
	<script src="${ctx}/resources/script/js/mysort.js"></script>
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		jQuery(function($) {
			
			
			

		});//end jQuery
	</script>
	
</body>
</html>
