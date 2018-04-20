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
						<li> <a href="#">数据交换</a>
						</li>
						<li class="active">数据交换测试</li>
					</ul>
				</div>

				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="col-md-12">
						<form class="form-horizontal" role="form" action="/message/SendMessage" method="post">
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 发送信息： </label>
										<div class="col-sm-10">
										<textarea type="text" id="form-field-Send" class="col-md-12" name="sendtext"></textarea>
										</div>										
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 选择文件路径： </label>
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
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 接收信息： </label>
										<div class="col-sm-10">
										<textarea type="text" id="form-field-Receive" class="col-md-12"></textarea>	
										</div>
										
									</div>
									
									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											<button class="btn btn-info" type="submit">
												<i class="ace-icon fa fa-check bigger-110"></i>
												发送
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
