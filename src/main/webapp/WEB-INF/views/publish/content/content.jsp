<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>内容管理</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<link href="${ctx}/resources/script/bootstrap/css/bootstrap-datetimepicker.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/resources/script/bootstrap/css/bootstrap-datetimepicker.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/resources/script/js/jquery.1.9.1.min.js"></script>
<script src="${ctx}/resources/script/bootstrap/js/bootstrap-datetimepicker.js" type="text/javascript"></script>
<script src="${ctx}/resources/script/bootstrap/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
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
				<form action="${ctx }/content/list/${nodeID}" method="post" id="queryForm" name="queryForm">

					<!--所在位置 -->
					<div class="breadcrumbs" id="breadcrumbs">
						<ul class="breadcrumb">
							<li><i class="ace-icon fa fa-home home-icon"></i> <a href="${ctx}/index">首页</a></li>
							<li></i> <a href="#">信息发布</a></li>
							<li class="active">
							<c:choose>
								<c:when test="${nodeID==1}">首页大图管理</c:when>
								<c:when test="${nodeID==2}">行业信息管理</c:when>
								<c:when test="${nodeID==3}">友情链接管理</c:when>
								<c:when test="${nodeID==4}">文件发布管理</c:when>
								<c:otherwise>内容管理</c:otherwise>
							</c:choose>
							</li>
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
								<c:if test="${nodeID==1}">
										<button class="btn btn-sm btn-primary " type="button" onclick="javascript:location.href='${ctx}/content/input/${nodeID}'">添加</button>
										&nbsp;&nbsp;&nbsp;
										<button class="btn btn-sm btn-primary " type="button" onclick="publishContent();">发布</button>
										&nbsp;&nbsp;&nbsp;
										<button class="btn btn-sm btn-primary " type="button" onclick="unpublishContent();">取消发布</button>
										&nbsp;&nbsp;&nbsp;
										<a id="hiddenClick" hidden=""  href="javascript:void(0);" data-toggle="modal" data-target="#templatemo_modal">hidden</a>
										<button class="btn btn-sm btn-primary " type="button" onclick="setTask(${content.id});">定时发布</button>
										&nbsp;&nbsp;&nbsp;
										<button class="btn btn-sm btn-primary " type="button" onclick="deleteManyContent();">删除</button>
										&nbsp;&nbsp;&nbsp;
								</c:if>
								<c:if test="${nodeID==2}">
										<button class="btn btn-sm btn-primary " type="button" onclick="javascript:location.href='${ctx}/content/input/${nodeID}'">添加</button>
										&nbsp;&nbsp;&nbsp;
									<security:authorize ifAnyGranted="ROLER_CONTENT_PUBLIC_NEWS_PUB">
										<button class="btn btn-sm btn-primary " type="button" onclick="publishContent();">发布</button>
										&nbsp;&nbsp;&nbsp;
										<button class="btn btn-sm btn-primary " type="button" onclick="unpublishContent();">取消发布</button>
										&nbsp;&nbsp;&nbsp;
										<a id="hiddenClick" hidden=""  href="javascript:void(0);" data-toggle="modal" data-target="#templatemo_modal">hidden</a>
										<button class="btn btn-sm btn-primary " type="button" onclick="setTask(${content.id});">定时发布</button>
										&nbsp;&nbsp;&nbsp;
									</security:authorize>
									<security:authorize ifAnyGranted="ROLER_CONTENT_PUBLIC_NEWS_DELETE">
										<button class="btn btn-sm btn-primary " type="button" onclick="deleteManyContent();">删除</button>
										&nbsp;&nbsp;&nbsp;
									</security:authorize>
								</c:if>
								<c:if test="${nodeID==3}">
									<security:authorize ifAnyGranted="ROLER_CONTENT_PUBLIC_LINK_EDIT">
										<button class="btn btn-sm btn-primary " type="button" onclick="javascript:location.href='${ctx}/content/input/${nodeID}'">添加</button>
										&nbsp;&nbsp;&nbsp;
									</security:authorize>
									<security:authorize ifAnyGranted="ROLER_CONTENT_PUBLIC_LINK_PUB">
										<button class="btn btn-sm btn-primary " type="button" onclick="publishContent();">发布</button>
										&nbsp;&nbsp;&nbsp;
										<button class="btn btn-sm btn-primary " type="button" onclick="unpublishContent();">取消发布</button>
										&nbsp;&nbsp;&nbsp;
										<a id="hiddenClick" hidden=""  href="javascript:void(0);" data-toggle="modal" data-target="#templatemo_modal">hidden</a>
										<button class="btn btn-sm btn-primary " type="button" onclick="setTask(${content.id});">定时发布</button>
										&nbsp;&nbsp;&nbsp;
									</security:authorize>
									<security:authorize ifAnyGranted="ROLER_CONTENT_PUBLIC_LINK_DELETE">
										<button class="btn btn-sm btn-primary " type="button" onclick="deleteManyContent();">删除</button>
										&nbsp;&nbsp;&nbsp;
									</security:authorize>
								</c:if>
								<c:if test="${nodeID==4}">
									<security:authorize ifAnyGranted="ROLER_CONTENT_PUBLIC_FILE_EDIT">
										<button class="btn btn-sm btn-primary " type="button" onclick="javascript:location.href='${ctx}/content/input/${nodeID}'">添加</button>
										&nbsp;&nbsp;&nbsp;
									</security:authorize>
									
									<security:authorize ifAnyGranted="ROLER_CONTENT_PUBLIC_FILE_PUB">
										<button class="btn btn-sm btn-primary " type="button" onclick="publishContent();">发布</button>
										&nbsp;&nbsp;&nbsp;
										<button class="btn btn-sm btn-primary " type="button" onclick="unpublishContent();">取消发布</button>
										&nbsp;&nbsp;&nbsp;
										<a id="hiddenClick" hidden=""  href="javascript:void(0);" data-toggle="modal" data-target="#templatemo_modal">hidden</a>
										<button class="btn btn-sm btn-primary " type="button" onclick="setTask(${content.id});">定时发布</button>
										&nbsp;&nbsp;&nbsp;
									</security:authorize>
									<security:authorize ifAnyGranted="ROLER_CONTENT_PUBLIC_FILE_DELETE">
										<button class="btn btn-sm btn-primary " type="button" onclick="deleteManyContent();">删除</button>
										&nbsp;&nbsp;&nbsp;
									</security:authorize>
								</c:if>
								<div class="col-md-4 col-sm-2 pull-right">
									<button class="btn btn-sm btn-primary  pull-right" style="margin-bottom: 7px;" type="submit">
										<i class="ace-icon glyphicon glyphicon-search"></i> 搜 索
									</button>
								<c:if test="${nodeID==1}">
									<input type="text" name="query_all_like" id="query_all_like" class="col-md-5 col-sm-2 pull-right"
										placeholder="标题" value="${query_all_like }" data-rel="tooltip" title="标题">
								</c:if>
								<c:if test="${nodeID==2}">
									<input type="text" name="query_all_like" id="query_all_like" class="col-md-5 col-sm-2 pull-right"
										placeholder="标题|作者" value="${query_all_like }" data-rel="tooltip" title="标题|作者">
								</c:if>
								<c:if test="${nodeID==3}">
									<input type="text" name="query_all_like" id="query_all_like" class="col-md-5 col-sm-2 pull-right"
										placeholder="标题" value="${query_all_like }" data-rel="tooltip" title="标题">
								</c:if>
								<c:if test="${nodeID==4}">
									<input type="text" name="query_all_like" id="query_all_like" class="col-md-5 col-sm-2 pull-right"
										placeholder="标题" value="${query_all_like }" data-rel="tooltip" title="标题">
								</c:if>
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
													<th class="sorting" tabindex="0" id="pubTime" onclick="javascript:sort(this,'queryForm','pubTime','asc');">发布时间</th>
<!-- 													<th class="sorting" tabindex="0" id="title" onclick="javascript:sort(this,'queryForm','hitcount','asc');">点击量</th> -->
													<th class="sorting" tabindex="0" id="publishstate"
														onclick="javascript:sort(this,'queryForm','publishstate','asc');">发布状态</th>
													<c:if test="${nodeID==2 }">
													<th class="sorting" tabindex="0" id="newType" onclick="javascript:sort(this,'queryForm','newType','asc');">新闻类型</th>
													</c:if>
													<c:if test="${nodeID==3 }">
													<th class="sorting" tabindex="0" id="url" onclick="javascript:sort(this,'queryForm','url','asc');">链接</th>
													</c:if>
													<c:if test="${nodeID==4 }">
													<th class="sorting" tabindex="0" id="type" onclick="javascript:sort(this,'queryForm','type','asc');">文件类型</th>
													</c:if>
													<c:if test="${nodeID!=3 && nodeID!=4}">
													<th class="sorting" tabindex="0" id="author" onclick="javascript:sort(this,'queryForm','author','asc');">作者</th>
													</c:if>
													<th>操作</th>
												</tr>
											</thead>

											<tbody>
												<c:forEach items="${pageContent.content}" var="content">
													<tr role="row" class="odd">
														<td class="center" style="width:5%;"><label class="pos-rel"> <input type="checkbox" id="objectid"
																	name="objectid" value="${content.id}_${content.publishstate}" class="ace"> <span class="lbl"></span>
														</label></td>
														<td width="42%;"><a href="${ctx}/content/detail/${nodeID}/?id=${content.id}">${content.title}</a></td>
														<td style="width:10%;"><fmt:formatDate value='${content.pubTime}' type='both' pattern='yyyy-MM-dd HH:mm:ss' /></td>
														<td style="width:9%;"><c:choose>
																<c:when test="${content.publishstate=='0'}">
																未发布
																<i class="ace-icon fa fa-info-circle bigger-115" style="color: #4b89aa;"></i>
																</c:when>
																<c:when test="${content.publishstate=='1'}">
																已发布
																<i class="ace-icon fa fa-check-circle bigger-120 " style="color: #8bad4c;"></i>
																</c:when>
																<c:when test="${content.publishstate=='2'}">
																定时发布
																<i class="ace-icon fa fa-info-circle bigger-115" style="color: #4b89aa;"></i>
																</c:when>
															</c:choose></td>
														<c:if test="${nodeID==2 }">
															<td style="width:9%;"><c:choose>
																<c:when test="${content.newType=='1'}">
																通知公告
																</c:when>
																<c:when test="${content.newType=='2'}">
																新闻动态
																</c:when>
															</c:choose></td>
														</c:if>
														<c:if test="${nodeID==3 }">
														<td style="width:20%;word-break:break-all">${content.url}</td>	
														</c:if>
														<c:if test="${nodeID!=3 && nodeID!=4}">
														<td style="width:14%;word-break:break-all">${content.author}</td>
														</c:if>
														<c:if test="${nodeID==4}">
															<c:if test="${content.newType=='1' }"><td style="width:14%;word-break:break-all">报表</td></c:if>
															<c:if test="${content.newType=='2' }"><td style="width:14%;word-break:break-all">书目</td></c:if>
															<c:if test="${content.newType=='3' }"><td style="width:14%;word-break:break-all">模板</td></c:if>
															<c:if test="${content.newType=='4' }"><td style="width:14%;word-break:break-all">相关资料</td></c:if>
														</c:if>
														
														<td style="width:10%;">
															<a class="green" href="${ctx}/content/detail/${nodeID}/?id=${content.id}" title="查看详情"> 
																<i class="ace-icon fa fa-book bigger-130"></i></a> &nbsp;&nbsp;&nbsp; 
															<c:if test="${nodeID==1}">
																	<a class="green" href="#" onclick="updateContent(${content.id},${content.publishstate});" title="点击修改"> 
																	<i class="ace-icon fa fa-pencil bigger-130"></i></a>&nbsp;&nbsp;&nbsp;
																	<a class="red" href="javascript:deleteOneContent(${content.id},${content.publishstate })" title="点击删除">
															 	<i class="ace-icon fa fa-trash-o bigger-130"></i></a>&nbsp;&nbsp;&nbsp;
															</c:if>
															<c:if test="${nodeID==2}">
																<security:authorize ifAnyGranted="ROLER_CONTENT_PUBLIC_NEWS_EDIT">
																	<a class="green" href="#" onclick="updateContent(${content.id},${content.publishstate});" title="点击修改"> 
																	<i class="ace-icon fa fa-pencil bigger-130"></i></a>&nbsp;&nbsp;&nbsp;
																</security:authorize>
																<security:authorize ifAnyGranted="ROLER_CONTENT_PUBLIC_NEWS_DELETE">
																	<a class="red" href="javascript:deleteOneContent(${content.id},${content.publishstate })" title="点击删除">
															 	<i class="ace-icon fa fa-trash-o bigger-130"></i></a>&nbsp;&nbsp;&nbsp;
																</security:authorize>
															</c:if>
															<c:if test="${nodeID==3}">
																<security:authorize ifAnyGranted="ROLER_CONTENT_PUBLIC_LINK_EDIT">
																	<a class="green" href="#" onclick="updateContent(${content.id},${content.publishstate});" title="点击修改"> 
																	<i class="ace-icon fa fa-pencil bigger-130"></i></a>&nbsp;&nbsp;&nbsp;
																</security:authorize>
																<security:authorize ifAnyGranted="ROLER_CONTENT_PUBLIC_LINK_DELETE">
																	<a class="red" href="javascript:deleteOneContent(${content.id},${content.publishstate })" title="点击删除">
															 	<i class="ace-icon fa fa-trash-o bigger-130"></i></a>&nbsp;&nbsp;&nbsp;
																</security:authorize>
															</c:if>
															<c:if test="${nodeID==4}">
																<security:authorize ifAnyGranted="ROLER_CONTENT_PUBLIC_FILE_EDIT">
																	<a class="green" href="#" onclick="updateContent(${content.id},${content.publishstate});" title="点击修改"> 
																	<i class="ace-icon fa fa-pencil bigger-130"></i></a>&nbsp;&nbsp;&nbsp;
																</security:authorize>
																<security:authorize ifAnyGranted="ROLER_CONTENT_PUBLIC_FILE_DELETE">
																	<a class="red" href="javascript:deleteOneContent(${content.id},${content.publishstate })" title="点击删除">
															 	<i class="ace-icon fa fa-trash-o bigger-130"></i></a>&nbsp;&nbsp;&nbsp;
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
					<ul>
						<!-- 隐藏的检索条件 -->
						<input type="hidden" id="pageNo" name="pageNo"/>
						<input type="hidden" id="id" name="id" />
						<input type="hidden" id="pageNo" name="pageSize" value="${pageSize }" />
						<input type="hidden" id="queryOrderBy" name="queryOrderBy" value="${queryOrderBy }" />
						<input type="hidden" id="queryOrderType" name="queryOrderType" value="${queryOrderType }" />
						<input type="hidden" id="nodeID" name="nodeID" value="${nodeID}"/>
					</ul>
					
					
						
					
					
				</form>
			</div>

<!-- Modal -->
						<div class="modal fade" id="templatemo_modal" tabindex="-1" user="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						  <div class="modal-dialog">
						    <div class="modal-content">
						      <div class="modal-header">
						        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
						        <h4 class="modal-title" id="myModalLabel">定时发布</h4>
						      </div>
						      <div class="modal-body" id="detailContent" style="width:400px;height:70px;">
						      	<div class="input-append date form_datetime" style="margin: auto 50px;">
    <span style="float:left;margin:5px auto;"><font style="font-size:15px;">时间：</font></span><input style="float:left;" size="24" type="text" value="" id="taskTime" name="taskTime" readonly>
    <span class="add-on"><i class="icon-remove"></i></span>
    <span class="add-on"><i class="icon-calendar"></i></span>
</div>
<script type="text/javascript">
    $(".form_datetime").datetimepicker({
        format: "yyyy-mm-dd hh:ii:00",
        autoclose: true,
        todayBtn: true,
        minuteStep: 10
    });
    //$('#datetimepicker').datetimepicker('setStartDate', "2016-07-02 11:00");
</script>
<div style="float: left;">&nbsp;&nbsp;&nbsp;</div>
    
						      </div>
						      <div class="modal-footer">
						        <button type="button" onclick="submitTask()" class="btn btn-info" data-dismiss="modal">提交</button>
						        <button type="button" class="btn btn-info" data-dismiss="modal">关闭</button>
						      </div>
						    </div>
						  </div>
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
			$("#taskTime").val("");
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
		function deleteOneContent(id,status) {
			bootbox.setDefaults("locale", "zh_CN");
			//bootbox.js控制确认和取消的位置
			// options = mergeDialogOptions("confirm", ["confirm","cancel"], ["message", "callback"], arguments);
			//button.className = "btn-default";
			//button.className = "btn-primary";	
			if(status=='1'){
				bootbox.alert("数据已发布，不可删除！");
			}else{
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
							queryForm.action = "${ctx}/content/delete/"+ id;
							queryForm.submit();
						} else {
							// alert('点击取消按钮了');  
						}
					},
				//title: "提示信息",  
				});
			}
			
			// 			bootbox.confirm("确定要删除该记录吗？", function(result) {
			// 				if (result) {//确认
			// 					queryForm.action = "${ctx}/content/delete?objectid="
			// 							+ id;
			// 					queryForm.submit();
			// 				}
			// 			});

		}
		/*日期选择控件加载*/
		$('.date-picker').datepicker({
			autoclose : true,
			todayHighlight : true
		})
		function GetCurrentDate(){
		     return new Date().format("yyyy-MM-dd hh:mm:ss"); 
		} 
		//定时任务执行方法
		function submitTask(){
			var values='';
			var ids = '';
			var status='';
			var taskTime = $("#taskTime").val();
			/* var arr = taskTime.split('-');
			var myDate = new Date();
			var today = new Date();
			myDate.setFullYear(arr[0],arr[1]-1,arr[2]);
			if(myDate > today){alert(">");}
			else {alert("<=");}
			  }  */
			  var today =  new Date().format("yyyy-MM-dd hh:mm:ss"); 
			  
			  //alert(taskTime +"=="+ today);
			  //today.toLocaleString();
			  //alert(today);
			  if(taskTime < today){
				  bootbox.alert("选择的时间不能小于当前时间!", function() {});
					return false;
			  }
			$("input[type=checkbox][name=objectid]").each(function() {
		        if ($(this).is(":checked")==true) {
		             values+=$(this).val()+",";
		        }
			});
			if(values){
				 values = values.substring(0,values.lastIndexOf(","));
				 var value=values.split(",");
		         for(var i=0;i<value.length;i++){        	
		        	var val = value[i].split("_");
		        	 ids+=val[0]+",";
		        	 status+=val[1]+",";
		         }
			}
				if(taskTime==null || taskTime=="" || taskTime==undefined){
					bootbox.alert("请选择定时任务的时间!", function() {});
					return false;
				}
			if(ids){
				  if(status){
					bootbox.setDefaults("locale","zh_CN"); 
					status = status.substring(0,status.lastIndexOf(","));
					var statu = status.split(",");
					for(var i=0;i<statu.length;i++){
						var Statu = statu[i];
						if(Statu==1){
							bootbox.alert("选中项包含已定时发布的数据!", function() {});
							return false;
						}
					}
				}
			bootbox.setDefaults("locale", "zh_CN");
			
					ids = ids.substring(0,ids.lastIndexOf(","));
					queryForm.action = "${ctx}/content/task/"+ids+"?taskTime="+taskTime;
					queryForm.submit();
		}
		}
		//删除多个
		function deleteManyContent() {
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
			
			var values='';
			var ids = '';
			var status='';
			$("input[type=checkbox][name=objectid]").each(function() {
		        if ($(this).is(":checked")==true) {
		             values+=$(this).val()+",";
		        }
			});
			if(values){
				 values = values.substring(0,values.lastIndexOf(","));
				 var value=values.split(",");
		         for(var i=0;i<value.length;i++){        	
		        	var val = value[i].split("_");
		        	 ids+=val[0]+",";
		        	 status+=val[1]+",";
		         }
			}
			if(ids){
				  if(status){
					bootbox.setDefaults("locale","zh_CN"); 
					status = status.substring(0,status.lastIndexOf(","));
					var statu = status.split(",");
					for(var i=0;i<statu.length;i++){
						var Statu = statu[i];
						if(Statu==1){
							bootbox.alert("选中项包含已经应用的策略!", function() {});
							return false;
						}
					}
				}
			bootbox.setDefaults("locale", "zh_CN");
			bootbox.confirm("确定要删除该记录吗？", function(result) {
				if (result) {//确认
					ids = ids.substring(0,ids.lastIndexOf(","));
					queryForm.action = "${ctx}/content/delete/"+ids;
					queryForm.submit();
				}
			});
		}
		}
		//通用代码
		function useful(message){
			var values='';
			var ids = '';
			var status='';
			$("input[type=checkbox][name=objectid]").each(function() {
		        if ($(this).is(":checked")==true) {
		             values+=$(this).val()+",";
		        }
			});
			if(values){
				 values = values.substring(0,values.lastIndexOf(","));
				 var value=values.split(",");
		         for(var i=0;i<value.length;i++){        	
		        	var val = value[i].split("_");
		        	 ids+=val[0]+",";
		        	 status+=val[1]+",";
		         }
			}
			if(ids){
				  if(status){
					bootbox.setDefaults("locale","zh_CN"); 
					status = status.substring(0,status.lastIndexOf(","));
					var statu = status.split(",");
					for(var i=0;i<statu.length;i++){
						var Statu = statu[i];
						if(message==0){
							if(Statu==0){
								bootbox.alert("选中项包含未发布的数据!", function() {});
								return false;
							}
						}
						if(message==1){
							if(Statu==1){
							bootbox.alert("选中项包含已发布的数据!", function() {});
							return false;
						}
						}
						
					}
				}
				  if(message==1){
					  bootbox.setDefaults("locale", "zh_CN");
						bootbox.confirm("确定要发布该记录吗？", function(result) {
							if (result) {//确认
								ids = ids.substring(0,ids.lastIndexOf(","));
								queryForm.action = "${ctx}/content/publish/"+ids;
								queryForm.submit();
							}
						});
				  }
				  if(message==0){
					  bootbox.setDefaults("locale", "zh_CN");
						bootbox.confirm("确定要取消发布该记录吗？", function(result) {
							if (result) {//确认
								ids = ids.substring(0,ids.lastIndexOf(","));
								queryForm.action = "${ctx}/content/unpublish/"+ids;
								queryForm.submit();
							}
						});
				  }
			}
		}
		//发布新闻
		function publishContent() {
			if (!isBoxChecked(queryForm.objectid)) {
				bootbox.setDefaults("locale", "zh_CN");
				bootbox.dialog({
					message : "<span class='bigger-110'>请至少选择一条进行发布！</span>",
					buttons : {
						"click" : {
							"label" : "确定",
							"className" : "btn-sm btn-primary"
						}
					}
				});
				return false;
			}
			
			useful(1);
		}
		//设定定时任务
		function setTask() {
			if (!isBoxChecked(queryForm.objectid)) {
				bootbox.setDefaults("locale", "zh_CN");
				bootbox.dialog({
					message : "<span class='bigger-110'>请至少选择一条进行定时发布！</span>",
					buttons : {
						"click" : {
							"label" : "确定",
							"className" : "btn-sm btn-primary"
						}
					}
				});
				$("#templatemo_modal").hide();
				return false;
				
			}
			$("#taskTime").val("");
			$("#hiddenClick").click();
			
			
		}
		
		//发布新闻
		function unpublishContent() {
			if (!isBoxChecked(queryForm.objectid)) {
				bootbox.setDefaults("locale", "zh_CN");
				bootbox.dialog({
					message : "<span class='bigger-110'>请至少选择一条进行取消发布！</span>",
					buttons : {
						"click" : {
							"label" : "确定",
							"className" : "btn-sm btn-primary"
						}
					}
				});
				return false;
			}
			useful(0);
		}
		
		
		function updateContent(id,status) {
			if(status==1){
				bootbox.alert("数据已发布，不可编辑！");
			}else{
			queryForm.action = "${ctx}/content/input/${nodeID}";
			 			//alert(id);
			$("#id").val(id);
			queryForm.submit();
			}
		}

		//操作提示信息js
		function hideBlock() {
			$("#warning-block").hide();
		}

		setTimeout("javascript:hideBlock();", 2000);
	</script>
</body>
</html>
