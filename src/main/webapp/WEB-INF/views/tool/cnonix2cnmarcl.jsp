<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>CNONIX转换CNMARC</title>

<meta name="description" content="overview &amp; stats" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<%@include file="/common/meta.html"%>

<style type="text/css">
h4, .h4, h5, .h5, h6, .h6 {
margin-top: 1px;
margin-bottom: 1px;
}

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
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<%@include file="../common/left.jsp"%>
		<div class="main-content">
			<div class="main-content-inner">
				<!-- #section:basics/content.breadcrumbs -->
				<div class="breadcrumbs" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="${ctx}/index">首页</a>
						</li>
						<li> <a href="#">相关工具</a>
						</li>
						<li class="active">CNONIX转换CNMARC</li>
					</ul>
					<!-- /.breadcrumb -->
					<!-- #section:basics/content.searchbox -->
					<!-- 
					<div class="nav-search" id="nav-search">
						<form class="form-search">
							<span class="input-icon"> <input type="text"
								placeholder="Search ..." class="nav-search-input"
								id="nav-search-input" autocomplete="off" /> <i
								class="ace-icon fa fa-search nav-search-icon"></i>
							</span>
						</form>
					</div>
					 -->
					<!-- /.nav-search -->

					<!-- /section:basics/content.searchbox -->
				</div>

				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<!-- #section:settings.box -->
					<!-- /section:settings.box -->
					<!-- 
					<div class="col-md-2">
						<div class="widget-box widget-color-blue2">
							<div class="widget-header">
								<h4 class="widget-title lighter smaller">素材分类(模拟)</h4>
							</div>
	
							<div class="widget-body">
								<div class="widget-main padding-8">
									<ul id="tree1" class="tree tree-selectable" role="tree"><li class="tree-branch hide" data-template="treebranch" role="treeitem" aria-expanded="false">				<div class="tree-branch-header">					<span class="tree-branch-name">						<i class="icon-folder ace-icon tree-plus"></i>						<span class="tree-label"></span>					</span>				</div>				<ul class="tree-branch-children" role="group"></ul>				<div class="tree-loader hide" role="alert"><div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div></div>						</li><li class="tree-item hide" data-template="treeitem" role="treeitem">				<span class="tree-item-name">				  <i class="icon-item ace-icon fa fa-times"></i>				  <span class="tree-label"></span>				</span>			</li><li class="tree-branch" role="treeitem" aria-expanded="false">				<div class="tree-branch-header">					<span class="tree-branch-name">						<i class="icon-folder ace-icon tree-plus"></i>						<span class="tree-label">For Sale</span>					</span>				</div>				<ul class="tree-branch-children" role="group"></ul>				<div class="tree-loader hide" role="alert"><div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div></div>						</li><li class="tree-branch" role="treeitem" aria-expanded="false">				<div class="tree-branch-header">					<span class="tree-branch-name">						<i class="icon-folder ace-icon tree-plus"></i>						<span class="tree-label">Vehicles</span>					</span>				</div>				<ul class="tree-branch-children" role="group"></ul>				<div class="tree-loader hide" role="alert"><div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div></div>						</li><li class="tree-branch" role="treeitem" aria-expanded="false">				<div class="tree-branch-header">					<span class="tree-branch-name">						<i class="icon-folder ace-icon tree-plus"></i>						<span class="tree-label">Rentals</span>					</span>				</div>				<ul class="tree-branch-children" role="group"></ul>				<div class="tree-loader hide" role="alert"><div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div></div>						</li><li class="tree-branch" role="treeitem" aria-expanded="false">				<div class="tree-branch-header">					<span class="tree-branch-name">						<i class="icon-folder ace-icon tree-plus"></i>						<span class="tree-label">Real Estate</span>					</span>				</div>				<ul class="tree-branch-children" role="group"></ul>				<div class="tree-loader hide" role="alert"><div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div></div>						</li><li class="tree-branch" role="treeitem" aria-expanded="false">				<div class="tree-branch-header">					<span class="tree-branch-name">						<i class="icon-folder ace-icon tree-plus"></i>						<span class="tree-label">Pets</span>					</span>				</div>				<ul class="tree-branch-children" role="group"></ul>				<div class="tree-loader hide" role="alert"><div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div></div>						</li><li class="tree-item" role="treeitem">				<span class="tree-item-name">				  <i class="icon-item ace-icon fa fa-times"></i>				  <span class="tree-label">Tickets</span>				</span>			</li><li class="tree-item" role="treeitem">				<span class="tree-item-name">				  <i class="icon-item ace-icon fa fa-times"></i>				  <span class="tree-label">Services</span>				</span>			</li><li class="tree-item" role="treeitem">				<span class="tree-item-name">				  <i class="icon-item ace-icon fa fa-times"></i>				  <span class="tree-label">Personals</span>				</span>			</li></ul>
								</div>
							</div>
						</div>
					</div>
					 -->
					<div class="col-md-12">
						<form class="form-horizontal" role="form">
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 选择CNONIX： </label>
										<div class="col-sm-10">
											<label class="ace-file-input"> <input type="file"
													id="id-input-file-2"> <span
													class="ace-file-container" data-title="选择"> <span
														class="ace-file-name" data-title="没有文件 ..."> <i
															class=" ace-icon fa fa-upload"></i>
													</span>
												</span> <a class="remove" href="#"><i
														class=" ace-icon fa fa-times"></i></a>
												</label>
										</div>
										
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 选择保存路径： </label>
										<div class="col-sm-10">
											<label class="ace-file-input"> <input type="file"
													id="id-input-file-2"> <span
													class="ace-file-container" data-title="选择"> <span
														class="ace-file-name" > <i
															class=" ace-icon fa fa-upload"></i>
													</span>
												</span> <a class="remove" href="#"><i
														class=" ace-icon fa fa-times"></i></a>
												</label>
										</div>
										
									</div>
									
									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											<button class="btn btn-info" type="button">
												<i class="ace-icon fa fa-check bigger-110"></i>
												转换
											</button>
											&nbsp; &nbsp; &nbsp;
											&nbsp; &nbsp; &nbsp;
											<button class="btn" type="button" onclick="javascript:history.back();">
												<i class="ace-icon fa fa-undo bigger-110"></i>
												取消
											</button>
										</div>
									</div>
									
								</form>
					</div>
					<!-- end col-md-12 -->

				</div>
				<!-- end page-content -->
				<!-- /.page-content -->
			</div>
			<!-- end main-content-inner -->
		</div>
		<!-- main-content -->
		<!-- /.main-content -->

		<%@include file="/common/footer.jsp"%>
	</div>
	<%@include file="/common/javascript.html"%>
	<script type="text/javascript">
			jQuery(function($) {
		//And for the first simple table, which doesn't have TableTools or dataTables
				//select/deselect all rows according to table header checkbox
			var active_class = 'active';
			$('#dynamic-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
				var th_checked = this.checked;//checkbox inside "TH" table header
				
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
				});
			});
			
			//select/deselect a row when the checkbox is checked/unchecked
			$('#dynamic-table').on('click', 'td input[type=checkbox]' , function(){
				var $row = $(this).closest('tr');
				if(this.checked) $row.addClass(active_class);
				else $row.removeClass(active_class);
			});
			


			});
		</script>


</body>
</html>
