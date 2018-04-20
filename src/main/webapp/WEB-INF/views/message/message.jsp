<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>消息管理</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<%@include file="../common/meta.html"%>
<style type="text/css">
/*状态为已接收 未接收的图标控制*/
#one,#two{
	display: inline;
}

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
				<form action="${ctx }/message/list" method="post" id="queryForm">
					<!--所在位置 -->
					<div class="breadcrumbs" id="breadcrumbs">
						<ul class="breadcrumb">
							<li><i class="ace-icon fa fa-home home-icon"></i> 
							<a href="${ctx}/index">首页</a></li>
							<li></i> <a href="#">消息中心</a></li>
							<li class="active">消息管理</li>
						</ul>
					</div>
					<c:if test="${not empty message}">
						<div id="warning-block" class="alert alert-success">
							<!--style="display: none;"  alert-success  alert-warning  <strong>无法提交！</strong>-->
							<a href="#" class="close" data-dismiss="alert">&times;</a>
							${message}！
						</div>
					</c:if>

					<div class="col-md-12">
						<!-- 操作按钮start -->
						<div class="page-header">
						<div>
							<div id="one" style="float:left;width:60%;">
								<button class="btn btn-sm btn-primary " type="button" onclick = "exportXML()">导出</button>
								&nbsp;&nbsp;&nbsp;
								<c:if test="${login_role=='1'||login_role=='2'||login_role=='3'||login_role=='5'}">
									<c:if test="${type=='1'}">
										<security:authorize ifAnyGranted="	ROLER_MESSAGE_SEND_MANAGER">
											<button class="btn btn-sm btn-primary " type="button" onclick="deleteManyMessage();">删除</button>&nbsp;&nbsp;&nbsp;
										</security:authorize>
									</c:if>
									<c:if test="${type=='2'}">
										<security:authorize ifAnyGranted="ROLER_MESSAGE_RECEIVE_MANAGER">
											<button class="btn btn-sm btn-primary " type="button" onclick="deleteManyMessage();">删除</button>&nbsp;&nbsp;&nbsp;
										</security:authorize>
									</c:if>
								</c:if>
								<c:if test="${login_role=='4'}">
									<security:authorize ifAnyGranted="ROLER_MESSAGE_MANAGE_MANAGER">
										<button class="btn btn-sm btn-primary " type="button" onclick="deleteManyMessage();">删除</button>&nbsp;&nbsp;&nbsp;
									</security:authorize>
								</c:if>
								
								
							</div>
							
							<!-- 表单检索row  start-->
						<div id="two">
							<%--
							<div class="col-md-2">
								<input type="text" name="queryTitle" id="queryTitle"
									class="form-control" placeholder="发送者" value="${queryTitle }"
									data-rel="tooltip" title="发送者"> 
							</div>
							&nbsp;&nbsp;&nbsp;
							 --%>
							<div class="col-md-2 col-sm-2 col-xs-2">
								<div class="input-group">
								<input class="form-control date-picker" id="startTime" name="startTime" value="${startTime}" onchange="validateTime()" type="text" title="开始时间" placeholder="开始时间" data-date-format="yyyy-mm-dd" >
								<span class="input-group-addon">
									<i class="fa fa-calendar bigger-110"></i>
								</span>
								</div>
							</div>
							&nbsp;&nbsp;&nbsp;
							<div class="col-md-2 col-sm-2 col-xs-2"">
								<div class="input-group">
								<input class="form-control date-picker" id="endTime" name="endTime" value="${endTime}" onchange="validateTime()" type="text" title="结束时间" placeholder="结束时间" data-date-format="yyyy-mm-dd" >
								<span class="input-group-addon">
									<i class="fa fa-calendar bigger-110"></i>
								</span>
								</div>
							</div>
							
							<button class="btn btn-sm btn-primary " onclick="return validateTime()"
								style="margin-bottom: 7px;margin-top:-20px;" type="submit">
								<i class="ace-icon glyphicon glyphicon-search"></i> 搜 索
							</button>
						</div>
						<!-- 表单检索row  end-->
						</div>
						</div>
						<!-- 操作按钮end -->
						
						

						<!-- 隐藏的检索条件 -->
						<input type="hidden" id="pageNo" name="pageNo" value="${pageNo}" />
						<input type="hidden" id="id" name="id"/> 
						<input type="hidden" id="type" name="type" value="${type }"/> 
						<input type="hidden" id="pageNo" name="pageSize" value="${pageSize }" /> 
						<input type="hidden" id="queryOrderBy" name="queryOrderBy" value="${queryOrderBy }" /> 
						<input type="hidden" id="queryOrderType" name="queryOrderType" value="${queryOrderType }" />

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
													<c:if test="${type==1 }">
													<th class="sorting" id="sender" tabindex="0" onclick="javascript:sort(this,'queryForm','sender','asc');">接收者</th>
													</c:if>
													<c:if test="${type==2 }">
													<th class="sorting" id="sender" tabindex="0" onclick="javascript:sort(this,'queryForm','sender','asc');">发送者</th>
													</c:if>
													<th class="sorting" id="type" tabindex="0" onclick="javascript:sort(this,'queryForm','type','asc');">消息类型</th>
													<c:if test="${type==1 }">
													<th class="sorting" id="sendtime" tabindex="0" onclick="javascript:sort(this,'queryForm','sendtime','asc');">发送时间</th>
													<!-- <th class="sorting" id="responseStatus" tabinx="0" onclick="javascript:sort(this,'queryForm','responseStatus','asc')">消息状态</th> -->
													</c:if>
													<c:if test="${type==2 }">
													<th class="sorting" id="receivetime" tabindex="0" onclick="javascript:sort(this,'queryForm','sendtime','asc');">发送时间</th>
													</c:if>
													<th>操作</th>
												</tr>
											</thead>

											<tbody>
												<c:forEach items="${pageContent.content}" var="message">
													<tr role="row" class="odd">
														<td class="center"><label class="pos-rel"> <input
																type="checkbox" id="objectid" name="objectid"
																value="${message.id}" class="ace"> <span
																class="lbl"></span>
														</label></td>
														<c:if test="${type==1}">
														<td><a href="${ctx}/message/detail?id=${message.id}">${message.receiver}</a>
														</c:if>
														<c:if test="${type==2 }">
														<td><a href="${ctx}/message/detail?id=${message.id}">${message.sender.orgName}</a>
														</td>
														</c:if>
														<td>
															<c:if test="${message.type==1}">发送</c:if>
															<c:if test="${message.type==2}">接收</c:if>
														</td>
														<td>
															<fmt:formatDate  value='${message.sendtime}' type='both' pattern='yyyy-MM-dd HH:mm:ss' />
														</td>
														<%-- <c:if test="${type==1 }">
														<td>
														    <c:if test="${message.responseStatus==0}">接收失败</c:if>
														    <c:if test="${message.responseStatus==1}">接收成功</c:if>
														    <!-- 
														    	<c:if test="${message.responseStatus==1}"><a href="${ctx}/messageResponse/findMessageResponseById?messageId=${message.id}">接收成功</a></c:if>
														    -->
														</td>
														</c:if> --%>
														<td>
															<a class="green"
																href="${ctx}/message/detail?id=${message.id}"
																title="查看详情"> <i
																	class="ace-icon fa fa-book bigger-130"></i>
															</a> &nbsp;&nbsp;&nbsp; 
															
															<c:if test="${login_role=='1'||login_role=='2'||login_role=='3'||login_role=='5'}">
																<c:if test="${type=='1'}">
																	<security:authorize ifAnyGranted="ROLER_MESSAGE_SEND_MANAGER">
																		<a class="red" href="javascript:deleteOneMessage('${message.id}')"
																			title="点击删除"> <i class="ace-icon fa fa-trash-o bigger-130"></i>
																		</a>
																	</security:authorize>
																</c:if>
																<c:if test="${type=='2'}">
																	<security:authorize ifAnyGranted="ROLER_MESSAGE_RECEIVE_MANAGER">
																		<a class="red" href="javascript:deleteOneMessage('${message.id}')"
																			title="点击删除"> <i class="ace-icon fa fa-trash-o bigger-130"></i>
																		</a>
																	</security:authorize>
																</c:if>
															</c:if>
															<c:if test="${login_role=='4'}">
																<security:authorize ifAnyGranted="ROLER_MESSAGE_MANAGE_MANAGER">
																	<a class="red" href="javascript:deleteOneMessage('${message.id}')"
																		title="点击删除"> <i class="ace-icon fa fa-trash-o bigger-130"></i>
																	</a>
																</security:authorize>
															</c:if>
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
					
					
					$("#datepicker").on('chick', function (){
						var startTime = $("#startTime").val();
						var endTime = $("#endTime").val();
						startTime = new Date(startTime); 
						endTime = new Date(endTime); 
						/* if(s > d){ 
						alert("开始时间大于结束时间"); 
						} */
						if(startTime!=""&&endTime!=""){
						 	if(endTime<startTime){
								alert("结束时间不能小于开始时间！");
								return false;
						 	}
						
						}
					});

		});//end jQuery

		//导出xml
		function exportXML() {
			if (!isBoxChecked(queryForm.objectid)) {
				bootbox.setDefaults("locale", "zh_CN");
				bootbox.dialog({
					message : "<span class='bigger-110'>请至少选择一条进行导出！</span>",
					buttons : {
						"click" : {
							"label" : "确定",
							"className" : "btn-sm btn-primary"
						}
					}
				});
				return false;
			}

			queryForm.action = "${ctx}/message/exportXml";
			queryForm.submit();
			queryForm.action = "${ctx}/message/list";

		}
		
		//删除单个
		function deleteOneMessage(id) {
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
						queryForm.action = "${ctx}/message/delete?objectid=" + id;
						//alert(queryForm.action);
						queryForm.submit();
					} else {
						// alert('点击取消按钮了');  
					}
				},
			//title: "提示信息",  
			});
	// 			bootbox.confirm("确定要删除该记录吗？", function(result) {
	// 				if (result) {//确认
	// 					queryForm.action = "${ctx}/companybook/delete?objectid="
	// 							+ id;
	// 					queryForm.submit();
	// 				}
	// 			});

		}

		//删除多个
		function deleteManyMessage() {
			var ids = "";
			$("input[type=checkbox][name=objectid]").each(function() {
		        if ($(this).is(":checked")==true) {
		             ids+=$(this).val()+",";
		        }
			});
			
			if(ids){
				bootbox.setDefaults("locale","zh_CN"); 
			    bootbox.confirm("确定要删除该记录吗？", function (result) {  
	                if(result) {//确认删除  
	                	queryForm.action = "${ctx}/message/delete";
						queryForm.submit();
	                } 
	         });  
			}else{
				bootbox.alert("还没有选择要删除的记录!", function() {});
			}
			
		}

		function updateMessage(id) {
			queryForm.action = "${ctx}/message/input";
			$("#id").val(id);
			queryForm.submit();
		}

		//操作提示信息js
		function hideBlock() {
			$("#warning-block").hide();
		}
        //时间比较
	   function validateTime(){
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			startTime = new Date(startTime); 
			endTime = new Date(endTime); 
			if(startTime!=""&&endTime!=""){
			 	if(endTime<startTime){
					bootbox.alert("开始日期不能晚于结束日期！", function() {});
					return false;
			 	}
			
			}
			
		} 
		setTimeout("javascript:hideBlock();", 2000);
	</script>

</body>
</html>
