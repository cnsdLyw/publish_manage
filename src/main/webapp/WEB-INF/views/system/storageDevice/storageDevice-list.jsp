<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>  
<!DOCTYPE html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>存储设备列表</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" 	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<%@include file="../../common/meta.html"%>
<script src="${ctx }/resources/script/bootstrap/js/bootbox.js" type="text/javascript"></script>
 
<style type="text/css">
</style>
<script type="text/javascript">
	
    function getStorageDevice(id) {  
	    $.ajax( {  
	        type : "get",  
	        url : "${ctx}/storageDevice/getJsonStorageDevice/?id="+id,  
	        dataType:"json",  
	        success : function(storageDevice) {  
	           	var content="<p>ftp名称："+storageDevice.ftpName+" </p>"+
				           	"<p>ftp标识："+storageDevice.ftpCode+" </p>"+
				           	"<p>ftp文件夹地址："+storageDevice.localFolderPath+" </p>"+
				           	"<p>ftp地址："+storageDevice.ftpUrl+" </p>"+
				      		"<p>ftp用户："+storageDevice.ftpUser+"</p>"+
				      		"<p>备注："+storageDevice.remark+"</p>";
				$("#detailContent").html(content);
	        }  
	    });  
	} 
        
	function deleteStorageDevice(id){
		bootbox.setDefaults("locale","zh_CN"); 
		bootbox.confirm("确认删除，删除后不可恢复!", function (result) {  
                if(result) {//确认删除  
                   	document.location.href="${ctx}/storageDevice/deleteStorageDevice/"+id+".html";
                } 
         });  
	}
	
	function deleteAll(){
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
                	document.location.href="${ctx}/storageDevice/deleteStorageDevices/"+ids+".html";
                } 
         });  
		}else{
			bootbox.alert("您还没有选择要删除的权限!", function() {});
		}
	}
	
	function hideBlock(){
		$("#warning-block").hide();
	}
	<c:if test="${param.message==1||param.message==0||param.message==-1}">
		setTimeout("javascript:hideBlock();", 3500)
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
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="/">首页</a>
						</li>
						<li></i> <a href="#">系统管理</a></li>
						<li class="active">存储设备管理</li>
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
				  		 	操作失败！<c:if test="${param.message==-1 }">删除的FTP正在使用！</c:if>
					</div>
				</c:if>
				<!-- Modal -->
				<div class="modal fade" id="templatemo_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				        <h4 class="modal-title" id="myModalLabel">查看FTP详细信息</h4>
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
						<!-- 操作按钮start -->
						<div class="page-header">
							<div>
								<button class="btn btn-sm btn-primary " type="button"  onclick="javascript:location.href='${ctx}/storageDevice/addStorageDevice'">
									新建</button>&nbsp;&nbsp;&nbsp;
								<!-- 
								<button class="btn btn-sm btn-primary" data-toggle="modal">分配权限</button>&nbsp;&nbsp;&nbsp;
								 -->
								<button class="btn btn-sm btn-primary " type="button" onclick="javascript:deleteAll();">删除</button>&nbsp;&nbsp;&nbsp;
							</div>
						</div><!-- 操作按钮end -->
						
						<form action="${ctx }/storageDevice/list" method="post" id="queryForm">
							 <input type="hidden" id="pageNo" name="pageNo" />
						<!-- 表单检索row  start-->
						<div class="row">
							<div class="col-md-2">
								<input type="text" class="form-control" placeholder="ftp名称"  id="ftpName" name="ftpName"  value="${ftpName}" data-rel="tooltip" title="ftp名称">
							</div>
							<!-- 
							<div class="col-md-2">
								<input type="text" class="form-control" placeholder="权限值" id="authKey" name="authKey" value="${authKey }" data-rel="tooltip" title="权限值">
							</div>
							 -->
							&nbsp;&nbsp;&nbsp;
							<button class="btn btn-sm btn-primary "
								style="margin-bottom: 7px;" type="submit">
								<i class="ace-icon glyphicon glyphicon-search"></i> 搜 索
							</button>
						</div><!-- 表单检索row  end-->
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
													<th tabindex="0">FTP名称</th>
													<th tabindex="0">FTP标识</th>
													<th tabindex="0">FTP地址</th>
													<th tabindex="0">备注</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="storageDevice" items="${pageContent.content}">
													<tr role="row" class="odd">
														<td class="center"><label class="pos-rel"> 
														<input name="ids" type="checkbox" class="ace" value="${storageDevice.id}"> <span class="lbl"></span>
														</label></td>
														<td>
															${storageDevice.ftpName }
														</td>
														<td>
															${storageDevice.ftpCode }
														</td>
														<td>
															${storageDevice.ftpUrl }
														</td>
														<td>
															${storageDevice.remark }
														</td>
														<td>
														<a class="red" href="#" class="test" onclick="getStorageDevice(${storageDevice.id})" data-toggle="modal" data-target="#templatemo_modal" title="查看详情"> <i
																class="ace-icon fa fa-book bigger-130"></i>
														</a> &nbsp;&nbsp;&nbsp; 
														<a class="green" href="${ctx }/storageDevice/editStorageDevice/${storageDevice.id}.html" title="点击修改"> <i
																class="ace-icon fa fa-pencil bigger-130"></i>
														</a> &nbsp;&nbsp;&nbsp;
														 <a class="red" href="javascript:void(0);" class="test" onclick="deleteStorageDevice(${storageDevice.id})" title="点击删除"> <i
																class="ace-icon fa fa-trash-o bigger-130"></i>
														</a>
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
