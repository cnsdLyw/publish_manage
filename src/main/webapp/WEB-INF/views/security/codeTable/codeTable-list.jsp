<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>  
<!DOCTYPE html>
<%
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>代码表列表</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" 	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<%@include file="../../common/meta.html"%>
    <script src="${ctx }/resources/script/js/validate/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/js/bootbox.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/js/area/jsOrg.js" type="text/javascript"></script>
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
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="${ctx}/index">首页</a>
						</li>
						<li></i> <a href="#">系统管理</a></li>
						<li class="active">代码表列表</li>
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
				  		 	操作失败！
					</div>
				</c:if>
				<!-- Mo
				dal -->
				<div class="modal fade" id="templatemo_modal" tabindex="-1" codeTable="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				        <h4 class="modal-title" id="myModalLabel">查看机构详细信息</h4>
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
					<form action="${ctx }/codeTable/list" method="post" id="queryForm">
						<input type="hidden" id="orderType" name="orderType" value="${orderType }" />
						<input type="hidden" id="sortType" name="sortType" value="${sortType }" />
						<input type="hidden" id="pageNo" name="pageNo" />
						<!-- 操作按钮start -->
						<div class="page-header">
							<div>
								<button class="btn btn-sm btn-primary " type="button" onclick="javascript:location.href='${ctx}/codeTable/add'">添加</button>&nbsp;&nbsp;&nbsp;
								<button class="btn btn-sm btn-primary " type="button" onclick="javascript:startUse();">启用</button>&nbsp;&nbsp;&nbsp;
								<button class="btn btn-sm btn-primary " type="button" onclick="javascript:deleteAll();">删除</button>
								<!-- <button class="btn btn-sm btn-primary " type="button" onclick="javascript:cancelUse();">取消启用</button> -->
								<!-- 表单检索row  start-->
								<div class="col-md-4 col-sm-2 pull-right">
									<button class="btn btn-sm btn-primary  pull-right"style="margin-bottom: 7px;" type="submit">
										<i class="ace-icon glyphicon glyphicon-search"></i> 搜 索
									</button>
									<input type="text" class="col-md-5 col-sm-2 pull-right" placeholder="代码表版本号"  id="keyWord" name="keyWord"  value="${keyWord}" data-rel="tooltip" title="代码表版本号">
								</div><!-- 表单检索row  end-->
							</div>
						</div><!-- 操作按钮end -->
						
						<!-- 列表row  start-->
						<div class="row">
							<div class="col-xs-12">
								<div>
									<div id="dynamic-table_wrapper"class="dataTables_wrapper form-inline no-footer">
										<table id="dynamic-table"
											class="table table-striped table-bordered table-hover dataTable no-footer DTTT_selectable"
											codeTable="grid" aria-describedby="dynamic-table_info">
											<thead>
												<tr codeTable="row">
													<th class="center sorting_disabled" rowspan="1" colspan="1"
														aria-label=""><label class="pos-rel"> <input
															type="checkbox" class="ace"> <span class="lbl"></span>
													</label></th>
													<th class="sorting" id="codeVersion" tabindex="0" onclick="javascript:sort(this,'queryForm','codeVersion','asc');">代码表版本号</th>
													<th class="sorting" id="fileName" tabindex="0" onclick="javascript:sort(this,'queryForm','fileName','asc');">文件名称</th>
													<th class="sorting" id="fileSize" tabindex="0" onclick="javascript:sort(this,'queryForm','fileSize','asc');">文件大小</th>
													<th class="sorting" id="uploadTime" tabindex="0" onclick="javascript:sort(this,'queryForm','uploadTime','asc');">更新时间</th>
													<th class="sorting" id="status" tabindex="0" onclick="javascript:sort(this,'queryForm','status','asc');">状态</th>
													<th class="sorting" id="codeDescription" tabindex="0" onclick="javascript:sort(this,'queryForm','codeDescription','asc');">备注</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody>
												 <c:forEach  items="${pageContent.content}" var="codeTable">
													<tr codeTable="row" class="odd">
														<td class="center"><label class="pos-rel"> 
															<input name="ids" type="checkbox" class="ace" value="${codeTable.id}_${codeTable.status}"> <span class="lbl"></span>
														</label>
														</td>
														<td>
														${codeTable.codeVersion}
														</td>
														<td>
														${codeTable.fileName}
														</td>
														<td>
														${codeTable.fileSize}
														</td>
														<td>
														<fmt:formatDate  value='${codeTable.uploadTime}' type='both' pattern='yyyy-MM-dd HH:mm:ss' />
														</td>
														<td>
														<c:if test="${codeTable.status==1}">启用</c:if>
														<c:if test="${codeTable.status==2}">未启用</c:if>
														</td>
														<td>
														${codeTable.codeDescription}
														</td>
														<td>
															<a class="green" <a href="${ctx}/codeTable/downLoad?urls=${codeTable.fileUrl}&filename=${codeTable.fileName}" title="点击下载">  <i
																	class="ace-icon fa fa-download bigger-130"></i>
															</a> &nbsp;&nbsp;&nbsp;
															<a class="green" href="${ctx }/codeTable/edit/${codeTable.id}.html" title="点击修改"> <i
																	class="ace-icon fa fa-pencil bigger-130"></i>
															</a> &nbsp;&nbsp;&nbsp;
															<a class="red" href="javascript:deleteOne('${codeTable.id}','${codeTable.status}')"title="点击删除"><i
																	class="ace-icon fa fa-trash-o bigger-130"></i></a>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<!-- 分页 begin -->
										    <tags:page dataPage="${pageContent}" paginationSize="2"/>   
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
	//删除一条记录
	function deleteOne(id,status) {
		if(status==1){
			bootbox.alert("代码表状态已启用，不可删除!", function() {});
			return;
		}
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
					document.location.href="${ctx}/codeTable/deleteCodeTable/"+id+".html";
				} else {
					// alert('点击取消按钮了');  
				}
			},
		});
				
	}
	//删除多个
	function deleteAll() {
		var values = '';
		var status='';
		var ids = '';
		$("input[type=checkbox][name=ids]").each(function() {
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
		    //判断状态是否已启用
		    if(status){
				bootbox.setDefaults("locale","zh_CN"); 
				status = status.substring(0,status.lastIndexOf(","));
				var statu = status.split(",");
				for(var i=0;i<statu.length;i++){
					var Statu = statu[i];
					if(Statu == 1){
						bootbox.alert("代码表状态已启用，不可删除!", function() {});
						return false;
					}
				}
			}
			
			bootbox.setDefaults("locale","zh_CN"); 
		    bootbox.confirm("确认删除，删除后不可恢复!", function (result) {  
                if(result) {//确认删除  
                	ids = ids.substring(0,ids.lastIndexOf(","));
                	document.location.href="${ctx}/codeTable/deleteCodeTables/"+ids+".html";
                } 
         	});  
		}else{
			bootbox.alert("您还没有选择要删除的代码表!", function() {});
		}
	}
	function startUse(){
	
	    var values = '';
		var status='';
		var ids = '';
		$("input[type=checkbox][name=ids]").each(function() {
	        if ($(this).is(":checked")==true) {
	             values+=$(this).val()+",";
	        }
		});
		if(values){
		    values = values.substring(0,values.lastIndexOf(","));
			var valueArr = values.split(",");
			if(valueArr.length>1){
				bootbox.alert("只能存在一条状态启用的记录!", function() {});
		      	return false;
			}
			
	         for(var i=0;i<valueArr.length;i++){        	
	        	var val = valueArr[i].split("_");
	        	 ids+=val[0]+",";
	        	 status+=val[1]+",";
	         }
	         
			ids = ids.substring(0,ids.lastIndexOf(","));
			status = status.substring(0,status.lastIndexOf(","));
			if(status=="1"){
				bootbox.alert("该记录状态为启用!", function() {});
		      	return false;
			}
			//判断是否存在 启用的代码表
			$.ajax({
		      type : "get",  
		      url : "${ctx}/codeTable/isStatusUsed",  
		      dataType:"json",  
		      success : function(isExist) {
		      	if(!isExist){
		            document.location.href="${ctx}/codeTable/updateStatus/"+ids+"/"+1+".html";
		      	}else{
		      		 bootbox.setDefaults("locale","zh_CN"); 
		  			 bootbox.confirm("确认启用新版本，旧版本将被取消启用!", function (result) {  
		                 if(result) {//确认删除  
		                	document.location.href="${ctx}/codeTable/updateStatus/"+ids+"/"+1+".html";
		                 } 
	                 })
		      	}
		      }  
		 	 });
		}else{
			bootbox.alert("请选择一条记录!", function() {});
			return false;
		}
		
	
	
	}	
	function cancelUse(){
	
		var values = '';
		var status='';
		var ids = '';
		$("input[type=checkbox][name=ids]").each(function() {
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
		    //判断状态是否已启用
		    if(status){
				bootbox.setDefaults("locale","zh_CN"); 
				status = status.substring(0,status.lastIndexOf(","));
				var statu = status.split(",");
				for(var i=0;i<statu.length;i++){
					var Statu = statu[i];
					if(Statu == 2){
						bootbox.alert("存在未启用记录!", function() {});
						return false;
					}
				}
			}
			ids = ids.substring(0,ids.lastIndexOf(","));
			//取消启用状态
			document.location.href="${ctx}/codeTable/updateStatus/"+ids+"/"+2+".html";
		}else{
			bootbox.alert("请选择一条记录!", function() {});
			return false;
		}
	}	
	function hideBlock(){
		$("#warning-block").hide();
	}
	<c:if test="${param.message==1||param.message==0||param.message==-1}">
		setTimeout("javascript:hideBlock();", 3500)
	</c:if>		
	</script>

</body>
</html>
