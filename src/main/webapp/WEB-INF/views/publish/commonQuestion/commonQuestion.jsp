<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>常见问题管理</title>
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
				<form action="${ctx }/commonQuestion/list" method="post" id="queryForm" name="queryForm">

					<!--所在位置 -->
					<div class="breadcrumbs" id="breadcrumbs">
						<ul class="breadcrumb">
							<li><i class="ace-icon fa fa-home home-icon"></i> <a href="${ctx}/index">首页</a></li>
							<li></i> <a href="#">信息发布</a></li>
							<li class="active">常见问题管理</li>
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
								<button class="btn btn-sm btn-primary " type="button" style="background:#2196f3"
									onclick="addQuestion();">新增</button>
								<button class="btn btn-sm btn-primary " type="button" style="background:#2196f3"
									onclick="deleteMany();">删除</button>
								<div class="col-md-4 col-sm-2 pull-right">
									<button class="btn btn-sm btn-primary  pull-right" style="margin-bottom: 7px;" type="submit">
										<i class="ace-icon glyphicon glyphicon-search"></i> 搜 索
									</button>
									<input type="text" name="keyWord" id="keyWord" class="col-md-5 col-sm-2 pull-right" placeholder="常见问题"
										value="${keyWord}"  title="常见问题" data-rel="tooltip" >
									
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
													<th class="sorting" tabindex="0" id="question" onclick="javascript:sort(this,'queryForm','question','asc');">常见问题</th>
													<th class="sorting" tabindex="0" id="content" onclick="javascript:sort(this,'queryForm','content','asc');">问题解答</th>
													<th class="sorting" tabindex="0" id="time" onclick="javascript:sort(this,'queryForm','time','asc');">创建时间</th>
													<th>操作</th>
												</tr>
											</thead>

											<tbody>
												<c:forEach items="${pageContent.content}" var="content">
													<tr role="row" class="odd">
														<td class="center"><label class="pos-rel"> <input type="checkbox" id="objectid"
																	name="objectid" value="${content.id}" class="ace"> <span class="lbl"></span>
														</label></td>
														<td><a href="${ctx }/commonQuestion/edit/${content.id}&type=1">${content.question}</a></td>
														<td>
															<c:choose>  
													         <c:when test="${fn:length(content.content) > 60}">  
													             <c:out value="${fn:substring(content.content, 0, 60)}..." />  
													         </c:when>  
													        <c:otherwise>  
													           <c:out value="${content.content}" />  
													         </c:otherwise>  
													    	 </c:choose> 
														</td>
														<td>
															<fmt:formatDate value="${content.time}"
															pattern="yyyy-MM-dd HH:mm:ss" /></td>
														</td>
														<td>
														    <a  class="green" href="${ctx }/commonQuestion/edit/${content.id}&type=1" title="查看"
															><i
																class="ace-icon fa fa-book bigger-130"></i></a>&nbsp;&nbsp;
															<a  class="green" href="${ctx }/commonQuestion/edit/${content.id}&type=0"  title="修改"
															><i
																class="ace-icon fa fa-pencil bigger-130"></i></a>&nbsp;&nbsp;
															<a  class="red" href="javascript:deleteOne('${content.id}')"  title="删除"
															><i class="ace-icon fa fa-trash-o bigger-130"></i></a>
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
						<input type="hidden" id="id" name="id" value="${id }" />
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

		//删除一条记录
	function deleteOne(id) {
		bootbox.setDefaults("locale", "zh_CN");
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
					queryForm.action = "${ctx}/commonQuestion/delete/"+ id;
					queryForm.submit();
				} else {
					// alert('点击取消按钮了');  
				}
			},
		});
	}
	//删除多个
	function deleteMany() {
		if (!isBoxChecked(queryForm.objectid)) {
			bootbox.setDefaults("locale", "zh_CN");
			bootbox.dialog({
				message : "<span class='bigger-110'>请至少选择一条进行删除！</span>",
				buttons : {
					confirm : {
						label : '确认',
						className : 'btn btn-primary'
					}
				}
			});
			return false;
		}

		bootbox.confirm({
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
					queryForm.action = "${ctx}/commonQuestion/deletes";
					queryForm.submit();
				} else {
					// alert('点击取消按钮了');  
				}
			},
		});

	}
	 function addQuestion(){
	  window.location="${ctx }/commonQuestion/add" ;
     }

		//操作提示信息js
		function hideBlock() {
			$("#warning-block").hide();
		}

		setTimeout("javascript:hideBlock();", 2000);
	</script>

</body>
</html>
