<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>资源文件管理</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />


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
	background: url(${ctx}/resources/images/mail2757b6.png) -48px 0
		no-repeat;
}

.Rr {
	background: url(${ctx}/resources/images/mail2757b6.png) -48px -16px
		no-repeat;
}
</style>
</head>

<body class="no-skin">
	<%@include file="../common/top.jsp"%>

	<div class="main-container" id="main-container">
		<%@include file="../common/left.jsp"%>
		<div class="main-content">
			<div class="main-content-inner">
				<form action="${ctx }/filescenter/list" method="post" id="queryForm">
					<!--所在位置 -->
					<div class="breadcrumbs" id="breadcrumbs">
						<ul class="breadcrumb">
							<li><i class="ace-icon fa fa-home home-icon"></i> <a
								href="/">首页</a></li>
							<li></i> <a href="#">系统管理</a></li>
							<li class="active">资源文件管理</li>
						</ul>
					</div>
					<c:if test="${not empty filescenter}">
						<div id="warning-block" class="alert alert-success">
							<!--style="display: none;"  alert-success  alert-warning  <strong>无法提交！</strong>-->
							<a href="#" class="close" data-dismiss="alert">&times;</a>
							${filescenter}！
						</div>
					</c:if>

					<div class="col-md-12">
						<!-- 操作按钮start -->
						<div class="page-header">
							<div>
								<button class="btn btn-sm btn-primary " type="button"
									onclick="deleteManyfilescenter();">删除</button>
								&nbsp;&nbsp;&nbsp;
								<button class="btn btn-sm btn-primary " type="button">
									导出</button>
								&nbsp;&nbsp;&nbsp;
							</div>
						</div>
						<!-- 操作按钮end -->


						<!-- 隐藏的检索条件 -->
						<input type="hidden" id="pageNo" name="pageNo" value="${pageNo}" />
						<input type="hidden" id="id" name="id" "/> <input type="hidden"
							id="pageNo" name="pageSize" value="${pageSize }" /> <input
							type="hidden" id="queryOrderBy" name="queryOrderBy"
							value="${queryOrderBy }" /> <input type="hidden"
							id="queryOrderType" name="queryOrderType"
							value="${queryOrderType }" />

						<!-- 表单检索row  start-->
						<div class="row">
							<%--
							<div class="col-md-2">
								<input type="text" name="queryTitle" id="queryTitle"
									class="form-control" placeholder="发送者" value="${queryTitle }"
									data-rel="tooltip" title="发送者"> 
							</div>
							&nbsp;&nbsp;&nbsp;
							 --%>
							<div class="col-md-2">
								<div class="input-group">
								<input class="form-control date-picker" id="id-date-picker-1" type="text" title="开始时间" placeholder="开始时间" data-date-format="yyyy-mm-dd">
								<span class="input-group-addon">
									<i class="fa fa-calendar bigger-110"></i>
								</span>
								</div>
							</div>
							&nbsp;&nbsp;&nbsp;
							<div class="col-md-2">
								<div class="input-group">
								<input class="form-control date-picker" id="id-date-picker-1" type="text" title="结束时间" placeholder="结束时间" data-date-format="yyyy-mm-dd">
								<span class="input-group-addon">
									<i class="fa fa-calendar bigger-110"></i>
								</span>
								</div>
							</div>
							
							<button class="btn btn-sm btn-primary "
								style="margin-bottom: 7px;" type="submit">
								<i class="ace-icon glyphicon glyphicon-search"></i> 搜 索
							</button>
						</div>
						<!-- 表单检索row  end-->


						<!-- 列表row  start-->
						<div class="row">
							<div class="col-xs-12">
								<div>
									<div id="dynamic-table_wrapper"
										class="dataTables_wrapper form-inline no-footer">

										<table id="dynamic-table"
											class="table table-striped table-bordered table-hover dataTable no-footer DTTT_selectable"
											role="grid" aria-describedby="dynamic-table_info">
											<thead>
												<tr role="row">
													<th class="center sorting_disabled" rowspan="1" colspan="1"
														aria-label=""><label class="pos-rel"> <input
															type="checkbox" class="ace"> <span class="lbl"></span>
													</label></th>
													<th class="sorting" tabindex="0" id="title">文件名称</th>
													<th class="sorting" tabindex="0">文件类型</th>
													<th class="sorting" tabindex="0">文件大小</th>
													<th>操作</th>
												</tr>
											</thead>

											<tbody>
												<c:forEach items="${pageContent.content}" var="filescenter">
													<tr role="row" class="odd">
														<td class="center"><label class="pos-rel"> <input
																type="checkbox" id="objectid" name="objectid"
																value="${filescenter.id}" class="ace"> <span
																class="lbl"></span>
														</label></td>
														<td><a href="${ctx}/filescenter/detail?id=${filescenter.id}">${filescenter.name}</a>
														</td>
														<td>
															${filescenter.type}
														</td>
														<td>
															${filescenter.size}
														</td>
														<td>
															<a class="red"
																href="${ctx}/filescenter/detail?id=${filescenter.id}"
																title="查看详情"> <i
																	class="ace-icon fa fa-book bigger-130"></i>
															</a> &nbsp;&nbsp;&nbsp; 
															
															<a class="red"
																href="javascript:deleteOnefilescenter(${filescenter.id})"
																title="点击删除"> <i
																	class="ace-icon fa fa-trash-o bigger-130"></i>
															</a>
														</td>
													</tr>
												</c:forEach>


											</tbody>
										</table>

										<!-- 分页 begin -->
										<tags:page dataPage="${pageContent}" paginationSize="2" />
										<!-- 分页 end -->


									</div>
								</div>


							</div>
						</div>
						<!-- 列表row  end-->


					</div>
					<!-- end col-md-12 -->

				</form>
			</div>
			<!--end  main-content-inner -->
		</div>
		<!-- end page-content -->

		<%@include file="../common/footer.jsp"%>

	</div>
	<!-- end main-container -->


	<%@include file="../common/javascript.html"%>
	<script src="${ctx}/resources/script/js/table.js"></script>

	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		jQuery(function($) {

			/*日期选择控件加载*/
			$('.date-picker').datepicker({
				autoclose : true,
				todayHighlight : true
			})
			//show datepicker when clicking on the icon
			.next().on(ace.click_event, function() {
				$(this).prev().focus();
			});
			
			/*表格全选操作加载*/
			var active_class = 'active';
			$('#dynamic-table > thead > tr > th input[type=checkbox]')
					.eq(0)
					.on(
							'click',
							function() {
								var th_checked = this.checked;//checkbox inside "TH" table header

								$(this)
										.closest('table')
										.find('tbody > tr')
										.each(
												function() {
													var row = this;
													if (th_checked)
														$(row)
																.addClass(
																		active_class)
																.find(
																		'input[type=checkbox]')
																.eq(0)
																.prop(
																		'checked',
																		true);
													else
														$(row)
																.removeClass(
																		active_class)
																.find(
																		'input[type=checkbox]')
																.eq(0)
																.prop(
																		'checked',
																		false);
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
			var queryOrderBy = "${queryOrderBy}";
			var queryOrderType = "${queryOrderType}";
			$("th[id='" + queryOrderBy + "']").attr("class",
					"sorting_" + queryOrderType);

		});//end jQuery

		//删除单个
		function deleteOnefilescenter(id) {
			if (confirm("您确定要删除当前记录？")) {
				queryForm.action = "${ctx}/filescenter/delete?objectid=" + id;
				queryForm.submit();
			}
		}

		//删除多个
		function deleteManyfilescenter() {
			if (!isBoxChecked(queryForm.objectid)) {
				alert("请至少选择一条进行删除！");
				return false;
			}

			if (!confirm("确定要删除该记录吗？")) {
				return false;
			}
			queryForm.action = "${ctx}/filescenter/delete";
			queryForm.submit();
		}

		function updatefilescenter(id) {
			queryForm.action = "${ctx}/filescenter/input";
			$("#id").val(id);
			queryForm.submit();
		}

		//操作提示信息js
		function hideBlock() {
			$("#warning-block").hide();
		}

		setTimeout("javascript:hideBlock();", 2000);
	</script>

</body>
</html>
