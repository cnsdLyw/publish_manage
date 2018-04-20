<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>留言板管理</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<%@include file="../../common/meta.html"%>
<style type="text/css">
</style>
</head>

<body class="no-skin">
	<%@include file="../../common/top.jsp"%>

	<div class="main-container" id="main-container">
		<%@include file="../../common/left.jsp"%>
		<div class="main-content">
			<div class="main-content-inner">
				<form action="${ctx }/faq/list" method="post" id="queryForm" name="queryForm">

					<!--所在位置 -->
					<div class="breadcrumbs" id="breadcrumbs">
						<ul class="breadcrumb">
							<li><i class="ace-icon fa fa-home home-icon"></i> <a href="${ctx}/index">首页</a></li>
							<li></i> <a href="#">信息发布</a></li>
							<li class="active">留言板管理</li>
						</ul>
					</div>
					
					<c:if test="${not empty message}">
						<div id="warning-block" class="alert alert-success">
							<!--style="display: none;"  alert-success  alert-warning  <strong>无法提交！</strong>-->
							<a href="#" class="close" data-dismiss="alert">&times;</a> ${message}！
						</div>
					</c:if>

					<div class="col-md-12">
						<!-- 操作按钮start -->
						<div class="page-header">
							<div>
								<security:authorize ifAnyGranted="ROLER_CONTENT_PUBLIC_FAQ_DELETE">
									<button class="btn btn-sm btn-primary " type="button" onclick="deleteManyFaq();">删除</button>
									&nbsp;&nbsp;&nbsp;
								</security:authorize>
									<div class="col-md-4 col-sm-2 pull-right">
										<button class="btn btn-sm btn-primary  pull-right" style="margin-bottom: 7px;" type="submit">
											<i class="ace-icon glyphicon glyphicon-search"></i> 搜 索
										</button>
										<input type="text" name="query_all_like" id="query_all_like" class="col-md-5 col-sm-2 pull-right" placeholder="姓名|电话|邮箱"
											value="${query_all_like }" data-rel="tooltip" title="姓名|电话|邮箱">
									
								</div>
							</div>
						</div>
						<!-- 操作按钮end -->

						<!-- 列表row  start-->
						<div class="row">
							<div class="col-xs-12">
								<div>
									<div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">

										<table id="dynamic-table"
											class="table table-striped table-bordered table-hover dataTable no-footer DTTT_selectable" role="grid"
											aria-describedby="dynamic-table_info">
											<thead>
												<tr role="row">
													<th class="center sorting_disabled" rowspan="1" colspan="1" aria-label=""><label class="pos-rel">
															<input type="checkbox" class="ace"> <span class="lbl"></span>
													</label></th>
													<th class="sorting" tabindex="0" id="title" onclick="javascript:sort(this,'queryForm','title','asc');">标题</th>
													<th class="sorting" tabindex="0" id="anonymous" onclick="javascript:sort(this,'queryForm','anonymous','asc');">姓名</th>
													<th class="sorting" tabindex="0" id="isbn" onclick="javascript:sort(this,'queryForm','phone','asc');">电话</th>
													<th class="sorting" tabindex="0" id="author" onclick="javascript:sort(this,'queryForm','email','asc');">邮箱</th>
													<th class="sorting" tabindex="0" id="auditStatus" onclick="javascript:sort(this,'queryForm','auditStatus','asc');">留言板状态</th>
													<th>操作</th>
												</tr>
											</thead>

											<tbody>
												<c:forEach items="${pageContent.content}" var="faq">
													<tr role="row" class="odd">
														<td class="center"><label class="pos-rel"> <input type="checkbox" id="objectid"
																	name="objectid" value="${faq.id}" class="ace"> <span class="lbl"></span>
														</label></td>
														<td><a href="${ctx}/faq/detail?id=${faq.id}&message=0">${faq.title}</a></td>
														<td>${faq.anonymous}</td>
														<td>${faq.phone}</td>
														<td class="hidden-480">${faq.email}</td>
														<td>
															<c:if test="${faq.auditStatus==0 }"><span class="label label-sm label-info arrowed-in">未回复 </span></c:if>
												        	<c:if test="${faq.auditStatus==1 }"><span class="label label-sm label-success">已回复</span></c:if>
														</td>
														<td><a class="green" href="${ctx}/faq/detail?id=${faq.id}&message=0" title="查看详情"> <i
																class="ace-icon fa fa-book bigger-130"></i></a> &nbsp;&nbsp;&nbsp; 
															<security:authorize ifAnyGranted="ROLER_CONTENT_PUBLIC_FAQ_EDIT">  
																  <c:if test="${faq.auditStatus==1 or faq.auditStatus==2 }">
																	<a class="red" href="${ctx }/faq/detail?id=${faq.id}&message=1" title="留言已经处理，不可再次处理！" onclick="return false;"> <i
																			class="ace-icon fa fa-check bigger-130"></i>
																	</a>
																  </c:if>
																  <c:if test="${faq.auditStatus!=1 and  faq.auditStatus!=2}">
																	<a class="green" href="${ctx }/faq/detail?id=${faq.id}&message=1" title="留言处理"> <i
																			class="ace-icon fa fa-check bigger-130"></i>
																	</a>
																  </c:if> &nbsp;&nbsp;&nbsp;
															</security:authorize>
														  
																<security:authorize ifAnyGranted="ROLER_CONTENT_PUBLIC_FAQ_DELETE">
																	<a class="red" href="javascript:deleteOneFaq(${faq.id})" title="点击删除"> <i
																		class="ace-icon fa fa-trash-o bigger-130"></i></a>&nbsp;&nbsp;&nbsp; 
																</security:authorize>
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
					<ul>
						<!-- 隐藏的检索条件 -->
						<input type="hidden" id="pageNo" name="pageNo" value="${pageNo}" />
						<input type="hidden" id="id" name="id" />
						<input type="hidden" id="pageNo" name="pageSize" value="${pageSize }" />
						<input type="hidden" id="queryOrderBy" name="queryOrderBy" value="${queryOrderBy }" />
						<input type="hidden" id="queryOrderType" name="queryOrderType" value="${queryOrderType }" />

					</ul>
				</form>
			</div>


			<!--end  main-content-inner -->
		</div>
		<!-- end page-content -->

		<%@include file="../../common/footer.jsp"%>

	</div>
	<!-- end main-container -->


	<%@include file="../../common/javascript.html"%>
	<script src="${ctx}/resources/script/js/table.js"></script>

	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		jQuery(function($) {

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
		function deleteOneFaq(id) {
			bootbox.setDefaults("locale", "zh_CN");
			//bootbox.js控制确认和取消的位置
			// options = mergeDialogOptions("confirm", ["confirm","cancel"], ["message", "callback"], arguments);
			//button.className = "btn-default";
			//button.className = "btn-primary";	
			bootbox
					.confirm({
						buttons : {
							confirm : {
								label : '确认',
								className : 'btn btn-primary'
							},
							cancel : {
								label : '取消',
								className : 'btn btn-default'
							}
						},
						message : '确定要删除该记录吗？',
						callback : function(result) {
							if (result) {
								queryForm.action = "${ctx}/faq/delete?objectid="
										+ id;
								queryForm.submit();
							} else {
								// alert('点击取消按钮了');  
							}
						},
					//title: "提示信息",  
					});
			// 			bootbox.confirm("确定要删除该记录吗？", function(result) {
			// 				if (result) {//确认
			// 					queryForm.action = "${ctx}/faq/delete?objectid="
			// 							+ id;
			// 					queryForm.submit();
			// 				}
			// 			});

		}

		//删除多个
		function deleteManyFaq() {
			if (!isBoxChecked(queryForm.objectid)) {
				bootbox.setDefaults("locale", "zh_CN");
				bootbox.dialog({
					message : "<span class='bigger-110'>请至少选择一条进行删除！</span>",
					buttons : {
						"click" : {
							"label" : "确定",
							"className" : "btn-sm btn-primary"
						}
					}
				});
				return false;
			}

			bootbox.setDefaults("locale", "zh_CN");
			bootbox.confirm("确定要删除该记录吗？", function(result) {
				if (result) {//确认
					queryForm.action = "${ctx}/faq/delete";
					queryForm.submit();
				}
			});
		}

		function updateFaq(id) {
			queryForm.action = "${ctx}/faq/input";
			// 			alert(id);
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
