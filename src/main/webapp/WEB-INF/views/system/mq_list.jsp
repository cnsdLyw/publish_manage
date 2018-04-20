<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>  
<!DOCTYPE html>
<%
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>数据交换管理</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" 	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<%@include file="../common/meta.html"%>
	<script type="text/javascript" src="${ctx}/resources/script/js/jquery.1.9.1.min.js"></script>
    <script src="${ctx }/resources/script/js/validate/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/js/bootbox.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/js/statusBar.js" type="text/javascript"></script>
    <script src="${ctx}/resources/script/js/table.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
	function getHistory(name){
		queryForm.action = "${ctx}/jmxRest/getHistroyMessage?name="+name;
		queryForm.submit();
	}
	
	function deleteContent(name){
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
					queryForm.action = "${ctx}/jmxRest/delete/"+ name;
					queryForm.submit();
				} else {
					// alert('点击取消按钮了');  
				}
			},
		//title: "提示信息",  
		});
	}
    /* function getHistory(name) {
    	$("#appendTbody").val(" ");
    	 $.ajax( {  
 	        type : "get",  
 	        url : "${ctx}/jmxRest/getHistoryJson/?name="+name,  
 	        dataType:"json",  
 	        success : function(data) {
 	        	var content = "";
 	        	$("#appendTbody").html(" ");
 	        	var tbody1 = "<tr role='row' class='odd'>";
 	           	var tbody = "";
 	           	var tbody2 = "</tr>";
 	           	if(data!=null){
		        	 $.each(data,function(i,result){  
		 	        		tbody = "<td>"+result.sender.orgCode+"</td>"+
		 	        				"<td>"+result.receiver+"</td>"+
		 	        				"<td>"+result.sendtime+"</td>";
		 	        		content += tbody1+tbody+tbody2;
		        	});  
		        	$("#appendTbody").append(content);
 	           	}
 	       }  
 	    });  
	}  */
        
	
	function hideBlock(){
		$("#warning-block").hide();
	}
	<c:if test="${param.message==1||param.message==0||param.message==-1}">
		setTimeout("javascript:hideBlock();", 3500)
	</c:if>
</script>
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
						<li class="active">数据交换管理</li>
					</ul>
				</div>
				<c:if test="${param.message==1 }">
				    <div id="warning-block" class="alert alert-success" ><!--style="display: none;"  alert-success  alert-warning  <strong>无法提交！</strong>-->
				  		 <a href="#" class="close" data-dismiss="alert">&times;</a>
				  		 	操作成功！
					</div>
				</c:if>
				<c:if test="${param.message==0 || param.message==-1 }">	
					 <div id="warning-block" class="alert alert-warning" ><!--style="display: none;"  alert-success  alert-warning  <strong>无法提交！</strong>-->
				  		 <a href="#" class="close" data-dismiss="alert">&times;</a>
				  		 	操作失败！<c:if test="${param.message==-1 }">删除的用户有角色配置！</c:if>
					</div>
				</c:if>
				<!-- Modal -->
				<div class="modal fade" id="templatemo_modal" tabindex="-1" user="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content" style="width:910px;">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				        <h4 class="modal-title" id="myModalLabel">查看机构历史消息</h4>
				      </div>
				      <div class="modal-body" id="detailContent" style="width:900px;height:300px;">
				      	    <table id="dynamic-table"
											class="table table-striped table-bordered table-hover dataTable no-footer DTTT_selectable"
											user="grid" aria-describedby="dynamic-table_info">
											<thead>
												<tr role="row">
												<th class="sorting" tabindex="0" id="title" onclick="javascript:sort(this,'queryForm','title','asc');">发送者</th>
												<th class="sorting" tabindex="0" id="title" onclick="javascript:sort(this,'queryForm','title','asc');">接收者</th>
												<th class="sorting" tabindex="0" id="title" onclick="javascript:sort(this,'queryForm','title','asc');">发送时间</th>
											</tr>
											</thead>
											<tbody id="appendTbody">


											</tbody>
										</table>
										<!-- 分页 begin -->
										<!--  -->
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-info" data-dismiss="modal">关闭</button>
				      </div>
				    </div>
				  </div>
				</div>
				
					<div class="col-md-12">
					<!-- 操作按钮start -->
					<div class="page-header">
					<button class="btn btn-sm btn-primary " type="button" onclick="deleteManyMq();">删除</button>
					</div>
					<form action="${ctx }/user/list" method="post" id="queryForm">
						<input type="hidden" id="orderType" name="orderType" value="${orderType }" />
						<input type="hidden" id="sortType" name="sortType" value="${sortType }" />
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
												<th class="center sorting_disabled" rowspan="1" colspan="1" aria-label=""><label class="pos-rel">
															<input type="checkbox" class="ace"> <span class="lbl"></span>
													</label></th>
												<th>机构名称</th>
												<th>连接状态</th>
												<th>待接收消息</th>
												<th>已接收消息</th>
												<th>消息总数</th>
												<th>操作</th>
											</tr>
											</thead>
											<tbody>
							<c:forEach items="${queuesList}" var="queue">
								<tr role="row" class="odd">
								   <td class="center"><label class="pos-rel">
									 <input type="checkbox" id="objectName" name="objectName" value="${queue.name}" class="ace">
									  <span class="lbl"></span>
									</label></td>
								   <td width="30%;">${queue.name}</td>
								   <c:if test="${queue.consumerCount==0}">
								   		<td width="12%;">未连接</td>
								   </c:if>
								   <c:if test="${queue.consumerCount!=0}">
								   		<td width="12%;">已连接</td>
								   </c:if>
								   <td width="12%;">${queue.queueSize}</td>
								   <td width="12%;">${queue.dequeueCount}</td>
								   <td width="12%;">${queue.enqueueCount}</td>
								   <td style="width:10%;">
								   
								   <a class="green" href="#" class="test" onclick="getHistory('${queue.name}')"  title="查看详情">  <i
																	class="ace-icon fa fa-book bigger-130"></i>
															</a> &nbsp;&nbsp;&nbsp; 
								   <a class="red" href="javascript:deleteContent('${queue.name}')" title="点击删除"> <i
											class="ace-icon fa fa-trash-o bigger-130"></i></a>
											
											</td>
								</tr>
							</c:forEach>


						</tbody>
										</table>
										<!-- 分页 begin -->
										<!--  -->
										<!-- 分页 end -->
									</div>
								</div>
							</div>
						</div><!-- 列表row  end-->
						</form>
					</div><!-- end col-md-12 -->
				</div><!--end  main-content-inner -->
			</div><!-- end page-content -->

		<%@include file="../common/footer.jsp"%>
	</div><!-- end main-container -->
	<%@include file="../common/javascript.html"%>
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
		
		//删除多个
		function deleteManyMq() {
			if (!isBoxChecked(queryForm.objectName)) {
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
					queryForm.action = "${ctx}/jmxRest/delete/"+ null;
					queryForm.submit();
				}
			});
		}
	</script>
</body>
</html>
