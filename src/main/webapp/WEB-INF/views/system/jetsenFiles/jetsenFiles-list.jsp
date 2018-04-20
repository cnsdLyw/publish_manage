<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>  
<!DOCTYPE html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>文件管理</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" 	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<%@include file="../../common/meta.html"%>
<script type="text/javascript" src="${ctx }/resources/script/bootstrap/js/toastr.js"></script>
<link rel="stylesheet" href="${ctx}/resources/script/bootstrap/css/toastr.css" />
<style type="text/css">
</style>
<script type="text/javascript">
	function FormatDate (strTime) {
		if(strTime){
			var date = new Date(strTime);
			//return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
		    return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
		}
	    return '';
	}
	
	function hideBlock(){
		$("#warning-block").hide();
	}
	<c:if test="${param.message==1||param.message==0||param.message==-1}">
		setTimeout("javascript:hideBlock();", 3500);
	</c:if>
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
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="${ctx }/index">首页</a>
						</li>
						<li></i> <a href="#">系统管理</a></li>
						<li class="active">文件管理</li>
					</ul>
				</div>
				<c:if test="${param.message==1 }">
				    <div id="warning-block" class="alert alert-success" ><!--style="display: none;"  alert-success  alert-warning  <strong>无法提交！</strong>-->
				  		 <a href="#" class="close" data-dismiss="alert">&times;</a>
				  		 	操作成功！
					</div>
				</c:if>
				<!-- Modal -->
				<div class="modal fade" id="templatemo_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				        <h4 class="modal-title" id="myModalLabel">查看测试用例详细信息</h4>
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
					<form action="${ctx }/jetsenFiles/list" method="post" id="queryForm">
						<input type="hidden" id="orderType" name="orderType" value="${orderType }" />
						<input type="hidden" id="sortType" name="sortType" value="${sortType }" />
					    <input type="hidden" id="pageNo" name="pageNo" />
						<!-- 操作按钮start -->
						<div class="page-header">
							<div>
								<!-- 
								<security:authorize ifAnyGranted="ROLER_TASK_RESET">		
									<button class="btn btn-sm btn-primary " type="button" onclick="javascript:resetTasks();">重置</button>&nbsp;&nbsp;&nbsp;
								</security:authorize>
								
								<security:authorize ifAnyGranted="ROLER_TASK_DELETE">	
									<button class="btn btn-sm btn-primary " type="button" onclick="javascript:deleteJetsenServiceTasks();">删除</button>&nbsp;&nbsp;&nbsp;
								</security:authorize>
								 -->
							</div>
						</div><!-- 操作按钮end -->
						
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
													<th class="sorting" id="name" tabindex="0" onclick="javascript:sort(this,'queryForm','name','asc');">文件名称</th>
													<th class="sorting" id="fileType" tabindex="0" onclick="javascript:sort(this,'queryForm','fileType','asc');">文件类型</th>
													<th class="sorting" id="fileSize" tabindex="0" onclick="javascript:sort(this,'queryForm','fileSize','asc');">文件大小</th>
													<th class="sorting" id="time" tabindex="0" onclick="javascript:sort(this,'queryForm','time','asc');">上传时间</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="jetsneFiles" items="${pageContent.content}">
													<tr role="row" class="odd">
														<td class="center"><label class="pos-rel"> 
														<input name="ids" type="checkbox" class="ace" value="${jetsneFiles.uuid}"> <span class="lbl"></span>
														</label></td>
														<td>
															${jetsneFiles.name }
														</td>
														<td>
															${jetsneFiles.fileType }
														</td>
														<td>
															<c:if test="${jetsneFiles.fileSize!=null&&(jetsneFiles.fileSize>1024&&jetsneFiles.fileSize<1048576) }">
																<fmt:formatNumber value="${jetsneFiles.fileSize/1024 }" pattern="0.00"/> <font color="blue">KB</font>
															</c:if>
															<c:if test="${jetsneFiles.fileSize!=null&&jetsneFiles.fileSize>1048576}">
																<fmt:formatNumber value="${jetsneFiles.fileSize/1048576 }" pattern="0.00"/> <font color="blue">MB</font>
															</c:if>
														</td>
														<td>
															<fmt:formatDate value="${jetsneFiles.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
														</td>
														<td>
															<a class="green" href="${fileHomeUrl }/${jetsneFiles.storagePath}" target="_blank" title="文件下载">  <i
																	class="ace-icon fa fa-cloud-download bigger-130"></i>
															</a> &nbsp;&nbsp;&nbsp; 
													</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<!-- 分页 begin -->
										    <tags:page dataPage="${pageContent}" paginationSize="2"/>   
										<!--  -->
									</div>
								</div>
							</div>
						</div><!-- 列表row  end-->
						</form>
					</div><!-- end col-md-12 -->
				</div><!--end  main-content-inner -->
			</div><!-- end page-content -->

		<%@include file="../../common/footer.jsp"%>
	</div><!-- end main-container -->
	<%@include file="../../common/javascript.html"%>
	<script src="${ctx}/resources/script/js/mysort.js"></script>
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
		
		function setClass(taskCode){
			window.document.location = "${ctx}/jetsneFiles/list?taskCode="+taskCode+"&taskStatus=${taskStatus}";			
		}
		
		function setStatus(taskStatus){
			window.document.location = "${ctx}/jetsneFiles/list?taskCode=${taskCode}&taskStatus="+taskStatus;			
		}
		
		function deleteJetsenServiceTask(id){
			bootbox.setDefaults("locale","zh_CN"); 
			bootbox.confirm("确认删除，删除后不可恢复!", function (result) {  
	               if(result) {//确认删除  
	                  	document.location.href="${ctx}/jetsneFiles/deleteJetsenServiceTask/"+id;
	               } 
	        });  
		}
		
		function deleteJetsenServiceTasks(){
			var ids = '';
			$("input[type=checkbox][name=ids]").each(function() {
		        if ($(this).is(":checked")==true) {
		             ids+=$(this).val()+",";
		        }
			});
			
			if(ids){
				bootbox.setDefaults("locale","zh_CN"); 
			    bootbox.confirm("确认删除，删除后不可恢复!", function (result) {  
	                if(result) {//确认删除  
	                	ids = ids.substring(0,ids.lastIndexOf(","));
	                	document.location.href="${ctx}/jetsneFiles/deleteJetsenServiceTasks/"+ids;
	                } 
	         });  
			}else{
				bootbox.alert("您还没有选择要删除的任务!", function() {});
			}
		}
	
		function resetTask(id){
			bootbox.setDefaults("locale","zh_CN"); 
			bootbox.confirm("确认重置吗？", function (result) {  
               if(result) {//确认删除  
               		 $.ajax({
					      type : "get",  
					      url : "${ctx}/jetsneFiles/resetTask",  
					      dataType:"json",  
					      data:{id:id},
					      async: false,
					      success : function(data) {
					      	if(data=="1"){
					      	  	setTimeout("toastr.success(\"<font size='4px'>重置任务成功</font>\");", 500);
			      				//bootbox.alert("重置任务成功。");
			      				$("#"+id+"status").html("<span class='label label-sm label-info arrowed-in'>等待处理</span>");
					      	}
					      }  
					  });
               } 
	        }); 
		}
		var ctxPath = "${ctx}";
	</script>
	<script src="${ctx }/resources/script/bootstrap/js/bootbox.js" type="text/javascript"></script>
	<script src="${ctx }/resources/script/js/process/process.js" type="text/javascript"></script>
	<script type="text/javascript" src="${ctx}/resources/script/js/comet4j/comet4j.js"></script>
	<script type="text/javascript">
		var count = 0;
		window.onload = function(){
	    // 建立连接，conn 即web.xml中 CometServlet的<url-pattern>
	    JS.Engine.start('${ctx}/conn');
	    // 监听后台某个频道
	    JS.Engine.on({ 
	            // 对应服务端 “频道1” 的值 msgCount
	            messageData : function(messageData){
	            	var dataArray = messageData.split(",");
	                if(dataArray&&dataArray.length==4){
	                	if(dataArray[1]=="-1"){
	                		$("#"+dataArray[0]+"status").html("<span class='label label-sm label-danger'>处理失败</span>");
	                	}else if(dataArray[1]=="2"){
	                		$("#"+dataArray[0]+"status").html("<span class='label label-sm label-success'>处理完成</span>");
	                	}
	                }
	                //alert(dataArray[2]);
	                //$("#msgCount").html(msgCount);
	            }//,
	            // 对应服务端 “频道2” 的值 msgData
	            /*msgData : function(msgData){
	                alert(msgData);
	                //$("#msgData").html(msgData);
	            },*/
	        }
	    );
	}
		
	</script>
	
</body>
</html>
