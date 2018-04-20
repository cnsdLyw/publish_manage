<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>  
<!DOCTYPE html>
<%
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>数据交换历史消息</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" 	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<%@include file="../common/meta.html"%>
    <script src="${ctx }/resources/script/js/validate/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/js/bootbox.js" type="text/javascript"></script>
<style type="text/css">
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
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="${ctx}/index">首页</a>
						</li>
						<li></i> <a href="#">系统管理</a></li>
						<li class="active">数据交换历史消息</li>
					</ul>
				</div>
				
					<div class="col-md-12">
					<form action="${ctx }/jmxRest/getHistroyMessage" method="post" id="queryForm">
						<input type="hidden" id="orderType" name="orderType" value="${orderType }" />
						<input type="hidden" id="sortType" name="sortType" value="${sortType }" />
						<input type="hidden" id="name" name="name" value="${name }" />
					    <input type="hidden" id="pageNo" name="pageNo" />
						<!-- 列表row  start-->
						<div class="row">
							<div class="col-xs-12">
								<div>
									<div id="dynamic-table_wrapper"class="dataTables_wrapper form-inline no-footer">
									<input type="hidden" id="pageNo" name="pageNo" />
										<table id="dynamic-table"
											class="table table-striped table-bordered table-hover dataTable no-footer DTTT_selectable"
											user="grid" aria-describedby="dynamic-table_info">
											<thead>
												<tr role="row">
												<th class="sorting" tabindex="0" id="title" onclick="javascript:sort(this,'queryForm','sender','asc');">发送者</th>
												<th class="sorting" tabindex="0" id="title" onclick="javascript:sort(this,'queryForm','receiver','asc');">接收者</th>
												<th class="sorting" tabindex="0" id="title" onclick="javascript:sort(this,'queryForm','sendtime','asc');">发送时间</th>
												<th>消息文本</th>
											</tr>
											</thead>
										<tbody>
							<c:forEach items="${pageContent.content}" var="message">
								<tr role="row" class="odd">
								   <td width="30%;">${message.sender.orgName}</td>
								   <td width="30%;">${name }</td>
								   <td width="30%;"><fmt:formatDate value="${message.sendtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								   <td width="10%;"><a href="${ctx}/jmxRest/pre?id=${message.id}" target="_blank">预览</a></td>
								</tr>
							</c:forEach>


						</tbody>
										</table>
										<!-- 分页 begin -->
										<tags:page dataPage="${pageContent}" paginationSize="2" />
										<!--  -->
										<!-- 分页 end -->
									</div>
								</div>
							</div>
						</div><!-- 列表row  end-->
						<!-- 隐藏的检索条件 -->
						<input type="hidden" id="pageNo" name="pageNo"/>
						<input type="hidden" id="id" name="id" />
						<input type="hidden" id="pageNo" name="pageSize" value="${pageSize }" />
						<input type="hidden" id="queryOrderBy" name="queryOrderBy" value="${queryOrderBy }" />
						<input type="hidden" id="queryOrderType" name="queryOrderType" value="${queryOrderType }" />
						<input type="hidden" id="nodeID" name="nodeID" value="${nodeID}"/>
						<!-- end col-md-12 -->
						<ul>
						
					</ul>
						</form>
					</div><!-- end col-md-12 -->
				</div><!--end  main-content-inner -->
			</div><!-- end page-content -->

		<%@include file="../common/footer.jsp"%>
	</div><!-- end main-container -->
	<%@include file="../common/javascript.html"%>
	<script src="${ctx}/resources/script/js/table.js"></script>
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
