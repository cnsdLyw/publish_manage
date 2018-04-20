<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>  
<!DOCTYPE html>
<%
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>标准机构代码列表</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" 	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<%@include file="../../common/meta.html"%>
    <script src="${ctx }/resources/script/js/validate/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/js/bootbox.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/js/statusBar.js" type="text/javascript"></script>
<style type="text/css">
</style>
<script type="text/javascript">
	
    function importOrganizationLibrary(){
    	window.document.location ="${ctx}/organizationLibrary/uploadFile?organizationType="+$("input[name='typeRadio']:checked").val();;
    }
    
	function deleteOrganizationLibrary(orgCode){
		bootbox.setDefaults("locale","zh_CN"); 
		bootbox.confirm("确认删除，删除后不可恢复!", function (result) {  
	        if(result) {
	           	document.location.href="${ctx}/organizationLibrary/deleteOrganizationLibrary?orgCode="+orgCode;
	        } 
        });  
	}
	
	function deleteAll(){
		bootbox.setDefaults("locale","zh_CN"); 
		bootbox.confirm("确认全部删除，删除后不可恢复!", function (result) {  
	        if(result) {
	        	Show("正在删除!");
	           	document.location.href="${ctx}/organizationLibrary/deleteAll";
	        } 
        });  
	}
	function deleteOrganizationLibrarys(){
		var orgCode = '';
		$("input[type=checkbox][name=ids]").each(function() {
	        if ($(this).is(":checked")==true) {
	             orgCode+=$(this).val()+",";
	        }
		});
		
		if(orgCode){
			bootbox.setDefaults("locale","zh_CN"); 
		    bootbox.confirm("确认删除，删除后不可恢复!", function (result) {  
                if(result) {
                	orgCode = orgCode.substring(0,orgCode.lastIndexOf(","));
                	document.location.href="${ctx}/organizationLibrary/deleteOrganizationLibrarys?orgCodes="+orgCode;
                } 
         });  
		}else{
			bootbox.alert("您还没有选择要删除的机构!", function() {});
		}
	}
	
	function changeType(type){
		$("#orgType").val(type);
		$("#queryForm").submit();
	}
	
	function changeStatus(type){
		$("#orgStatus").val(type);
		$("#queryForm").submit();
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
						<li class="active">标准机构代码</li>
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
				<!-- Modal -->
				<div class="modal fade" id="orgType_modal" tabindex="-1" organization="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				        <h4 class="modal-title" id="myModalLabel">选择要导入的机构类别</h4>
				      </div>
				      <div class="modal-body" id="detailContent">
								<c:forEach  items="${classes}" var="clazz">
									<div class="radio">
										<label>
											<input name="typeRadio" type="radio" class="ace" <c:if test="${clazz.classCode=='100' }">checked</c:if> value="${clazz.classCode }"/>
											<span class="lbl"> ${clazz.className }</span>
										</label>
									</div>
								</c:forEach>
							</select>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-info"onclick="javascript:importOrganizationLibrary()">确定</button>
				      </div>
				    </div>
				  </div>
				</div>
				
					<div class="col-md-12">
					<form action="${ctx }/organizationLibrary/list" method="post" id="queryForm">
						<input type="hidden" id="orderType" name="orderType" value="${orderType }" />
						<input type="hidden" id="sortType" name="sortType" value="${sortType }" />
						
						<input type="hidden" id="pageNo" name="pageNo" />
						<input type="hidden" id="orgType" name="orgType" value="${orgType }"/>
						<input type="hidden" id="orgStatus" name="orgStatus" value="${orgStatus }"/>
						<!-- 操作按钮start -->
						<div class="page-header">
							<div>
								<security:authorize ifAnyGranted="ROLER_STANDARD_ORGANIZATION_IMPORT">
									<button class="btn btn-sm btn-primary " type="button"  data-toggle="modal" data-target="#orgType_modal">
										导入</button>&nbsp;&nbsp;&nbsp;
								</security:authorize>
								<security:authorize ifAnyGranted="ROLER_STANDARD_ORGANIZATION_DELETE">		
									<button class="btn btn-sm btn-primary " type="button" onclick="javascript:deleteOrganizationLibrarys();" title="删除选中的记录">删除</button>&nbsp;&nbsp;&nbsp;
									<button class="btn btn-sm btn-primary " type="button" onclick="javascript:deleteAll();"title="清空全部记录">全部删除</button>&nbsp;&nbsp;&nbsp;
								</security:authorize>
								<!-- 
								<button class="btn btn-sm btn-primary " type="button"  onclick="javascript:location.href='${ctx}/organizationLibrary/addOrganizationLibrary/'">
									新建</button>&nbsp;&nbsp;&nbsp;
								<button class="btn btn-sm btn-primary " type="button" onclick="javascript:deleteAll();">删除</button>&nbsp;&nbsp;&nbsp;
								 -->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<select onchange="javascript:changeType(this.value);">
									<option value="">-选择机构分类-</option>
									<c:forEach  items="${classes}" var="clazz">
										<option value="${clazz.classCode }" <c:if test="${clazz.classCode==orgType }">selected</c:if>>${clazz.className }</option>
									</c:forEach>
								</select>
								<!-- 表单检索row  start-->
								<div class="col-md-4 col-sm-2 pull-right">
									<button class="btn btn-sm btn-primary  pull-right"style="margin-bottom: 7px;" type="submit">
										<i class="ace-icon glyphicon glyphicon-search"></i> 搜 索
									</button>
									<input type="text" class="col-md-5 col-sm-2 pull-right" placeholder="机构名称|机构代码"  id="keyWord" name="keyWord"  value="${keyWord}" data-rel="tooltip" title="名称|代码">
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
											organization="grid" aria-describedby="dynamic-table_info">
											<thead>
												<tr organization="row">
													<th class="center sorting_disabled" rowspan="1" colspan="1"
														aria-label=""><label class="pos-rel"> <input
															type="checkbox" class="ace"> <span class="lbl"></span>
													</label></th>
													<!-- 
														<th class="sorting" id="number" tabindex="0" onclick="javascript:sort(this,'queryForm','number','asc');">序号</th>
													 -->
													<th class="sorting" id="orgName" tabindex="0" onclick="javascript:sort(this,'queryForm','orgName','asc');">名称</th>
													<th class="sorting" id="orgCode" tabindex="0" onclick="javascript:sort(this,'queryForm','orgCode','asc');">代码</th>
													<th class="sorting" id="orgTypeName" tabindex="0" onclick="javascript:sort(this,'queryForm','orgTypeName','asc');">类别</th>
													<th class="sorting" id="province" tabindex="0" onclick="javascript:sort(this,'queryForm','province','asc');">地区</th>
													<!-- 	
														<th class="sorting" id="modifyTime" tabindex="0" onclick="javascript:sort(this,'queryForm','modifyTime','asc');">日期</th>
													-->
												</tr>
											</thead>
											<tbody>
												 <c:forEach  items="${pageContent.content}" var="organization">
													<tr organization="row" class="odd">
														<td class="center"><label class="pos-rel"> 
															<input name="ids" type="checkbox" class="ace" value="${organization.orgCode}"> <span class="lbl"></span>
														</label>
														</td>
														<!-- 
															<td>${organization.number }</td>
														-->
														<td>${organization.orgName }</td>
														<td>${organization.orgCode }</td>
														<td>${organization.orgTypeName }</td>
														<td>${organization.province }</td>
														<!-- 
															<td><fmt:formatDate value="${organization.modifyTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
														-->
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
	</script>

</body>
</html>
