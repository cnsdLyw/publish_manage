<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>  
<!DOCTYPE html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<link rel="shortcut icon" href="${ctx }/resources/images/link.ico">
<title>角色列表</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" 	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<%@include file="../../common/meta.html"%>
 <script src="${ctx }/resources/script/bootstrap/js/bootbox.js" type="text/javascript"></script>
 
<style type="text/css">
</style>
<script type="text/javascript">
	
    function getRole(id) {  
	    $.ajax( {  
	        type : "get",  
	        url : "${ctx}/role/getJsonRole/?id="+id,  
	        dataType:"json",  
	        success : function(role) {  
	           		var content = '';
       				for(var i =0;i<role.authorityList.length;i++){
       					content+=role.authorityList[i].authorityName+"&nbsp;&nbsp;&nbsp;&nbsp;";
       					if((i+1)%3==0&&(i+1)<role.authorityList.length){
       						content+="<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
       					}
       				}
           	    	var contentInner="<p>角色名称："+role.roleName+" </p>"+
			           	"<p>权限："+content+" </p>"+
           				"<p>备注："+role.remark+" </p>";
				    $("#detailContent").html(contentInner);
	        }  
	    });  
	} 
        
	function deleteRole(id){
		bootbox.setDefaults("locale","zh_CN"); 
		bootbox.confirm("确认删除，删除后不可恢复!", function (result) {  
                if(result) {//确认删除  
                   	document.location.href="${ctx}/role/deleteRole/"+id+".html";
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
                	document.location.href="${ctx}/role/deleteRoles/"+ids+".html";
                } 
         });  
		}else{
			bootbox.alert("您还没有选择要删除的角色!", function() {});
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
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="${ctx}/index">首页</a>
						</li>
						<li></i> <a href="#">系统管理</a></li>
						<li class="active">角色列表</li>
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
				  		 	操作失败！<c:if test="${param.message==-1 }">删除的角色正在使用！</c:if>
					</div>
				</c:if>
				<!-- Modal -->
				<div class="modal fade" id="templatemo_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				        <h4 class="modal-title" id="myModalLabel">查看角色详细信息</h4>
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
					<form action="${ctx }/role/list" method="post" id="queryForm">
						<input type="hidden" id="orderType" name="orderType" value="${orderType }" />
						<input type="hidden" id="sortType" name="sortType" value="${sortType }" />
						<!-- 操作按钮start -->
						<div class="page-header">
							<div>
								<security:authorize ifAnyGranted="ROLER_SYS_ROLE_NEW">
									<button class="btn btn-sm btn-primary " type="button"  onclick="javascript:location.href='${ctx}/role/addRole'">
										新建</button>&nbsp;&nbsp;&nbsp;
								</security:authorize>
								<security:authorize ifAnyGranted="ROLER_SYS_ROLE_DELETE">
									<button class="btn btn-sm btn-primary " type="button" onclick="javascript:deleteAll();">删除</button>&nbsp;&nbsp;&nbsp;
								</security:authorize>
								<security:authorize ifAnyGranted="ROLER_SYS_ROLE_REFRESH">
									<button class="btn btn-sm btn-primary " type="button" onclick="javascript:refreshAuthority();">权限刷新</button>&nbsp;&nbsp;&nbsp;
								</security:authorize>
								<!-- 表单检索row  start-->
								<div class="col-md-4 col-sm-2 pull-right">
									<button  class="btn btn-sm btn-primary  pull-right" style="margin-bottom: 7px;" type="submit">
										<i class="ace-icon glyphicon glyphicon-search"></i> 搜 索
									</button>
									<input type="text" class="col-md-5 col-sm-2 pull-right" placeholder="角色名称"  id="keyWord" name="keyWord"  value="${keyWord}" data-rel="tooltip" title="权限名称">
								</div><!-- 表单检索row  end-->
								
							</div>
						</div><!-- 操作按钮end -->
						
							 <input type="hidden" id="pageNo" name="pageNo" />
						
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
													<th class="sorting" id="roleName" tabindex="0" onclick="javascript:sort(this,'queryForm','roleName','asc');">角色名称</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="role" items="${pageContent.content}">
													<tr role="row" class="odd">
														<td class="center"><label class="pos-rel"> 
															<input name="ids" type="checkbox" class="ace" value="${role.id}"> <span class="lbl"></span>
														</label></td>
														<td>
															${role.roleName }
														</td>
														<td>
														<a class="green" href="#" class="test" onclick="getRole(${role.id})" data-toggle="modal" data-target="#templatemo_modal" title="查看详情"> <i
																class="ace-icon fa fa-book bigger-130"></i>
														</a> &nbsp;&nbsp;&nbsp; 
															<a class="green" href="${ctx }/role/editRole/${role.id}.html" title="点击修改"> <i
																	class="ace-icon fa fa-pencil bigger-130"></i>
															</a> &nbsp;&nbsp;&nbsp;
														<security:authorize ifAnyGranted="ROLER_SYS_ROLE_DELETE">
															<a class="red" href="javascript:void(0);" class="test" onclick="deleteRole(${role.id})" title="点击删除"> <i
																	class="ace-icon fa fa-trash-o bigger-130"></i>
															</a>
														</security:authorize>
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
		
		//刷新权限
		function refreshAuthority(){
			bootbox.setDefaults("locale","zh_CN"); 
			bootbox.confirm("确认刷新!", function (result) {  
				Show("刷新用户权限");
				$.ajax({
			        type : "get",
			        url : "${ctx}/authority/refreshAuthority",
			        dataType:"json",
				    success : function(flag) {
				    	setTimeout("Close();",1000);
				    	Show("刷新完成");
				    	setTimeout("Close();",1000);
				    	window.location.reload();
				    }
				});
			}); 
		}
		var ctxPath ="${ctx}";
	</script>
	<script src="${ctx }/resources/script/js/statusBarExt.js" type="text/javascript"></script>
</body>
</html>
