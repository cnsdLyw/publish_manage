<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>  
<!DOCTYPE html>
<%
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>配置</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" 	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<%@include file="../../common/meta.html"%>
    <script src="${ctx }/resources/script/js/validate/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/js/bootbox.js" type="text/javascript"></script>
<style type="text/css">
</style>
<script type="text/javascript">
    function getSysConfigure(id) {
	    $.ajax( {  
	        type : "get",  
	        url : "${ctx}/sysConfirure/getJsonSysConfigure/?id="+id,
	        dataType:"json",  
	        success : function(sysConfigure) {
	           	var contentInner="<p>配置名称："+sysConfigure.configureName+" </p>"+
	           				"<p>配置代码："+sysConfigure.configureCode+" </p>"+
	           				"<p>配置值："+sysConfigure.configureValue+" </p>"+
	           				"<p>备注："+sysConfigure.remark+" </p>";
				    $("#detailContent").html(contentInner);
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
                	document.location.href="${ctx}/sysConfigGroup/delete/"+ids+".html?typeid=${typeid}";
                } 
         });  
		}else{
			bootbox.alert("您还没有选择要删除的节点!", function() {});
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
	<div class="main-container" id="main-container">
		<%@include file="../../common/left.jsp"%>
		<div class="main-content">
			<div class="main-content-inner">
				<!-- Modal -->
				<div class="modal fade" id="templatemo_modal" tabindex="-1" user="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				        <h4 class="modal-title" id="myModalLabel">查看配置详细信息</h4>
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
					<form action="${ctx }/sysConfigGroup/list?typeid=${typeid}" method="post" id="queryForm">	
					 <input type="hidden" id="pageNo" name="pageNo" />
						<!-- 操作按钮start -->
						<div class="page-header">
							<div>
								<button class="btn btn-sm btn-primary " type="button"  onclick="javascript:location.href='${ctx}/sysConfigGroup/addSysConfig?typeid=${typeid}'">
									新建节点</button>&nbsp;&nbsp;&nbsp;
								<!-- 
								<button class="btn btn-sm btn-primary" data-toggle="modal">分配权限</button>&nbsp;&nbsp;&nbsp;
								 -->
								<button class="btn btn-sm btn-primary " type="button" onclick="javascript:deleteAll();">删除节点</button>&nbsp;&nbsp;&nbsp;
								<!-- 表单检索row  start-->
								<div class="col-md-4 col-2 pull-right">
									<button class="btn btn-sm btn-primary  pull-right"style="margin-bottom: 7px;" type="submit">
										<i class="ace-icon glyphicon glyphicon-search"></i> 搜 索
									</button>
									<input type="text" class="col-md-5 col-2 pull-right" id="keyWord" name="keyWord"  value="${keyWord }" placeholder="节点名称" data-rel="tooltip" title="配置名称|配置编码">
								</div><!-- 表单检索row  end-->
							</div>
						</div><!-- 操作按钮end -->
					
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
												<tr user="row">
													<th class="center sorting_disabled" rowspan="1" colspan="1"
														aria-label=""><label class="pos-rel"> <input
															type="checkbox" class="ace"> <span class="lbl"></span>
													</label></th>
													<th tabindex="0">节点名称</th>
													<th tabindex="0">父节点</th>
													<th tabindex="0">节点备注</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody>
											<c:forEach items="${pageContent.content}" var="sysConfigGroup">
													<tr user="row" class="odd">
														<td class="center"><label class="pos-rel"> 
															<input name="ids" type="checkbox" class="ace" value="${sysConfigGroup.id}"> <span class="lbl"></span>
														</label>
														</td>
														<td>${sysConfigGroup.groupName}</td>
														<td>${parentName}</td>
														<td>${sysConfigGroup.remark}</td>
														<td>
															<a class="red" href="${ctx }/sysConfigGroup/editSysConfig/${sysConfigGroup.id}?typeid=${typeid}" title="点击修改"> <i class="ace-icon fa fa-pencil bigger-130"></i>
															</a> &nbsp;&nbsp;&nbsp;
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
