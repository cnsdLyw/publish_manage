<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>  
<!DOCTYPE html>
<%
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>日志管理</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" 	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<%@include file="../../common/meta.html"%>
    <script src="${ctx }/resources/script/js/validate/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/js/bootbox.js" type="text/javascript"></script>
<style type="text/css">
</style>
<script type="text/javascript">
	
        
	function deleteOrganization(ids){
		bootbox.setDefaults("locale","zh_CN"); 
		bootbox.confirm("确认删除，删除后不可恢复!", function (result) {  
                if(result) {//确认删除  
                   	document.location.href="${ctx}/loginfo/deleteLogInfo/"+ids+".html";
                } 
         });  
	}
	
	//导出日志
	function exportLog() {
		var ids = '';
		$("input[type=checkbox][name=ids]").each(function() {
	        if ($(this).is(":checked")==true) {
	             ids+=$(this).val()+",";
	        }
		});
		
		if(ids){
			bootbox.setDefaults("locale","zh_CN"); 
                	ids = ids.substring(0,ids.lastIndexOf(","));
                	document.location.href="${ctx}/loginfo/exportLogInfo/"+ids+".html";
		}else{
			bootbox.alert("请至少选择一条日志进行导出！", function() {});
		}
		
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
                	document.location.href="${ctx}/loginfo/deleteLogInfo/"+ids+".html";
                } 
         });  
		}else{
			bootbox.alert("您还没有选择要删除的信息!", function() {});
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
						<li></i> <a href="#">日志管理</a></li>
						<li class="active">日志管理</li>
					</ul>
				</div>
					<div class="col-md-12">
							<form action="${ctx }/loginfo/list" method="post" id="queryForm">
								<input type="hidden" id="pageNo" name="pageNo" />
								<input type="hidden" id="operateType" name="operateType" value="${orgType }"/>
								<!-- 表单检索row  start-->
								<div class="page-header">
								<div style="float:left;">
									<button class="btn btn-sm btn-primary " type="button"  onclick="javascript:exportLog();">导出</button>&nbsp;&nbsp;&nbsp;
									<!--  <button class="btn btn-sm btn-primary " type="button" onclick="javascript:deleteAll();">删除</button>&nbsp;&nbsp;&nbsp;-->
								</div>
									<div class="row">
										<button class="btn btn-sm btn-primary "
											style="margin-bottom: 7px;float:right;margin-right:20px;" type="submit">
											<i class="ace-icon glyphicon glyphicon-search"></i> 搜 索
										</button>
										&nbsp;&nbsp;&nbsp;
										<div class="col-md-2" style="float:right;">
											<input type="text" class="form-control" id="operateUser" name="operateUser"  value="${operateUser }" placeholder="登录用户" data-rel="tooltip" title="登录用户">
										</div>
										<div class="col-md-2" style="float:right;">
											<input type="text" class="form-control"  id="operateIp" name="operateIp" value="${operateIp }" placeholder="登录ip" data-rel="tooltip" title="登录ip">
										</div>
									</div>
									
								</div>
								<!-- 表单检索row  end-->
								<!-- 列表row  start-->
								<div class="row">
									<div class="col-xs-12">
										<div>
											<div id="dynamic-table_wrapper"class="dataTables_wrapper form-inline no-footer">
												<table id="dynamic-table"
													class="table table-striped table-bordered table-hover dataTable no-footer DTTT_selectable"
													organization="grid" aria-describedby="dynamic-table_info">
													<thead>
														<tr organization="row">
															<th class="center sorting_disabled" rowspan="1" colspan="1"
																aria-label=""><label class="pos-rel"> <input
																	type="checkbox" class="ace"> <span class="lbl"></span>
															</label></th>
															<th tabindex="0">用户</th>
															<th tabindex="0">用户IP</th>
															<th tabindex="0">日志类型</th>
															<th tabindex="0">操作内容</th>
															<th tabindex="0">时间</th>
															<!--  <th>操作</th>-->
														</tr>
													</thead>
													<tbody>
														 <c:forEach  items="${pageContent.content}" var="loginfo">
															<tr organization="row" class="odd">
																<td class="center"><label class="pos-rel"> 
																	<input name="ids" type="checkbox" class="ace" value="${loginfo.id}"> <span class="lbl"></span>
																</label>
																</td>
																<td>${loginfo.operateUser }</td>
																<td>${loginfo.operateIp }</td>
																<c:if test="${loginfo.operateType=='login'}">
																	<td>登录日志</td>
																</c:if>
																<c:if test="${loginfo.operateType=='operate'}">
																	<td>操作日志</td>
																</c:if>
																
																<td>${loginfo.operateName }</td>
																<td><fmt:formatDate value="${loginfo.operateTime }" pattern="yyyy-MM-dd HH:mm:ss" /> </td>
																<!--  <td>
															 		<a class="red" href="javascript:void(0);" title="点击删除"  onclick="deleteOrganization(${loginfo.id})"> <i class="ace-icon fa fa-trash-o bigger-130"></i>
																	</a>-->
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
