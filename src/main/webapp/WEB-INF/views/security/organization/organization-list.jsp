<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>  
<!DOCTYPE html>
<%
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>机构列表</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" 	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<%@include file="../../common/meta.html"%>
    <script src="${ctx }/resources/script/js/validate/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/js/bootbox.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/js/area/jsOrg.js" type="text/javascript"></script>
<style type="text/css">
#detailContent{white-space:normal; width:500px; word-wrap:break-word}
</style>
<script type="text/javascript">
	
    function getOrganization(orgCode) {  
	    $.ajax( {  
	        type : "get",  
	        url : "${ctx}/organization/getJsonOrganization/?orgCode="+orgCode,  
	        dataType:"json",  
	        success : function(organization) {  
	        	var content = "";
	        	 $.ajax( {  
	     	        type : "get",  
	     	        url : "${ctx}/organization/getJsonOrganizations/?orgCode="+organization.upperCode,  
	     	        dataType:"json",
	     	        async:true,
	     	        success : function(organizations) {  
	     	        	var a = organizations.orgName;
	     	        	content+=
	     	        		/* "<p>上级机构："+(organizations.orgName!=null?organizations.orgName:"")+" </p>"+ */
	            			"<p>机构名称："+(organization.orgName!=null?organization.orgName:"")+" </p>"+
	           				"<p>机构代码："+(organization.orgCode!=null?organization.orgCode:"")+" </p>"+
	           				"<p>机构类型："+(organization.firstOrgName!=null?organization.firstOrgName:"")+" "+(organization.secondOrgName!=null?organization.secondOrgName:"")+"</p>";
	           				if(organization.isbn!=null&&organization.isbn!=""){
	           					content=content+"<p>isbn前缀码："+( organization.isbn!=null?organization.isbn:"")+" </p>";
	           				}
	           				
	           			content+="<p>地区："+(organization.province!=null?organization.province:"")+" "+(organization.city!=null?organization.city:"")+" "+(organization.county!=null?organization.county:"")+" </p>"+
	           				"<p>机构地址："+(organization.orgAddress!=null?organization.orgAddress:"")+" </p>"+
	           				//"<p>登录帐号："+(organization.loginName!=null?organization.loginName:"")+" </p>"+
	           				//"<p>ftp用户名："+(organization.ftpName!=null?organization.ftpName:"")+" </p>"+
	           				//"<p>ftp地址："+(organization.ftpAddress!=null?organization.ftpAddress:"")+" </p>"+
	           				/* "<p>联系电话："+(organization.telephone!=null?organization.telephone:"")+" </p>"+ */
	           				"<p>机构联系人："+(organization.orgContacter!=null?organization.orgContacter:"")+" </p>"+
	           				"<p>联系人电话："+(organization.orgContactPhone!=null?organization.orgContactPhone:"")+" </p>"+
	           				"<p>联系人邮箱："+(organization.orgContactEmail!=null?organization.orgContactEmail:"")+" </p>"+
	           				"<p>经济类型："+(organization.orgEconomic!=null?organization.orgEconomic:"")+" </p>"+
	           				"<p>邮编："+(organization.postalcode!=null?organization.postalcode:"")+" </p>"+
	           				"<p>企业网址："+(organization.orgWebsit!=null?organization.orgWebsit:"")+" </p>"+
	           				"<p>机构简介："+(organization.orgContent!=null?organization.orgContent:"")+" </p>";
				    $("#detailContent").html(content);
	     	       },error: function(e) { 
	     	    	  content+="<p>机构名称："+(organization.orgName!=null?organization.orgName:"")+" </p>"+
			         			"<p>机构代码："+(organization.orgCode!=null?organization.orgCode:"")+" </p>"+
			         			"<p>机构类型："+(organization.firstOrgName!=null?organization.firstOrgName:"")+" "+(organization.secondOrgName!=null?organization.secondOrgName:"")+"</p>";
		           				if(organization.isbn!=null&&organization.isbn!=""){
		           					content=content+"<p>isbn前缀码："+( organization.isbn!=null?organization.isbn:"")+" </p>";
		           				}	
			         		content+="<p>地区："+(organization.province!=null?organization.province:"")+" "+(organization.city!=null?organization.city:"")+" "+(organization.county!=null?organization.county:"")+" </p>"+
			         				"<p>机构地址："+(organization.orgAddress!=null?organization.orgAddress:"")+" </p>"+
			         				/* "<p>登录帐号："+(organization.loginName!=null?organization.loginName:"")+" </p>"+
			         				"<p>ftp用户名："+(organization.ftpName!=null?organization.ftpName:"")+" </p>"+
			         				"<p>ftp地址："+(organization.ftpAddress!=null?organization.ftpAddress:"")+" </p>"+ */
			         				/* "<p>联系电话："+(organization.telephone!=null?organization.telephone:"")+" </p>"+ */
			         				"<p>机构联系人："+(organization.orgContacter!=null?organization.orgContacter:"")+" </p>"+
			         				"<p>联系人电话："+(organization.orgContactPhone!=null?organization.orgContactPhone:"")+" </p>"+
			         				"<p>联系人邮箱："+(organization.orgContactEmail!=null?organization.orgContactEmail:"")+" </p>"+
			         				"<p>经济类型："+(organization.orgEconomic!=null?organization.orgEconomic:"")+" </p>"+
			         				"<p>邮编："+(organization.postalcode!=null?organization.postalcode:"")+" </p>"+
			         				"<p>企业网址："+(organization.orgWebsit!=null?organization.orgWebsit:"")+" </p>"+
			         				"<p>机构简介："+(organization.orgContent!=null?organization.orgContent:"")+" </p>";
	     	    	 $("#detailContent").html(content);
	        	}
	        	 })
	        }  
	    });  
	} 
    
	function deleteOrganization(orgCode){
		//判断该机构是否可以删除，是否存在用户
	    $.ajax({
		      type : "get",  
		      url : "${ctx}/organization/isUsed/?orgCodes="+orgCode,  
		      dataType:"json",  
		      success : function(isExist) {
		      	if(isExist){
		      		bootbox.alert("需删除该机构的用户！", function() {});
		      		return false;
		      	}else{
		      		bootbox.setDefaults("locale","zh_CN"); 
					bootbox.confirm("确认删除，删除后不可恢复!", function (result) {  
			                if(result) {//确认删除  
			                   	document.location.href="${ctx}/organization/deleteOrganization?orgCode="+orgCode;
			                } 
			        }); 
		      	}
		      }  
		  });
		 
	}
	
	function deleteAll(){
	    
		var orgCode = '';
		$("input[type=checkbox][name=ids]").each(function() {
	        if ($(this).is(":checked")==true) {
	             orgCode+=$(this).val()+",";
	        }
		});
		
		if(orgCode){
		    //判断该机构是否可以删除，是否存在用户
		    $.ajax({
			      type : "get",  
			      url : "${ctx}/organization/isUsed/?orgCodes="+orgCode,  
			      dataType:"json",  
			      success : function(isExist) {
			      	if(isExist){
			      		bootbox.alert("需删除该机构的用户！", function() {});
			      		return false;
			      	}else{
			      		bootbox.setDefaults("locale","zh_CN"); 
					    bootbox.confirm("确认删除，删除后不可恢复!", function (result) {  
			                if(result) {//确认删除  
			                	orgCode = orgCode.substring(0,orgCode.lastIndexOf(","));
			                	document.location.href="${ctx}/organization/deleteOrganizations?orgCodes="+orgCode;
			                } 
			        	 }); 
			      	}
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
	
	function asyncOrganization(){
		document.location.href="${ctx}/organization/asyncOrganization";
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
						<li class="active">机构列表</li>
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
				  		 	删除机构信息失败！
					</div>
				</c:if>
				<!-- Modal -->
				<div class="modal fade" id="templatemo_modal" tabindex="-1" organization="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
					<form action="${ctx }/organization/list" method="post" id="queryForm">
						<input type="hidden" id="orderType" name="orderType" value="${orderType }" />
						<input type="hidden" id="sortType" name="sortType" value="${sortType }" />
						
						<input type="hidden" id="pageNo" name="pageNo" />
						<input type="hidden" id="orgType" name="orgType" value="${orgType }"/>
						<input type="hidden" id="orgStatus" name="orgStatus" value="${orgStatus }"/>
						<!-- 操作按钮start -->
						<div class="page-header">
							<div>
								 <security:authorize ifAnyGranted="ROLER_SYS_ORGANIZATION_NEW">
									<button class="btn btn-sm btn-primary " type="button"  onclick="javascript:location.href='${ctx}/organization/addOrganization'">
										新建</button>&nbsp;&nbsp;&nbsp;
								</security:authorize> 
								<security:authorize ifAnyGranted="ROLER_SYS_ORGANIZATION_DELETE">		
									<button class="btn btn-sm btn-primary " type="button" onclick="javascript:deleteAll();">删除</button>&nbsp;&nbsp;&nbsp;
								</security:authorize>
								<%-- <select onchange="javascript:changeType(this.value);">
									<option value="">-选择机构分类-</option>
									<c:forEach  items="${classes}" var="clazz">
										<option value="${clazz.id }" <c:if test="${clazz.id==orgType }">selected</c:if>>${clazz.name }</option>
									</c:forEach>
								</select> --%>
								<!-- 表单检索row  start-->
								<div class="col-md-4 col-sm-2 pull-right">
									<button class="btn btn-sm btn-primary  pull-right"style="margin-bottom: 7px;" type="submit">
										<i class="ace-icon glyphicon glyphicon-search"></i> 搜 索
									</button>
									<input type="text" class="col-md-5 col-sm-2 pull-right" placeholder="机构名称|机构代码"  id="keyWord" name="keyWord"  value="${keyWord}" data-rel="tooltip" title="机构名称|机构代码">
								</div><!-- 表单检索row  end-->
							</div>
						</div><!-- 操作按钮end -->
					
						<div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
							<div class="row">
								<div class="col-md-2" style="width:240px;">
										<select  class="col-md-12 col-sm-8" name="firstOrgName" id="firstOrgName" data-placement="bottom">
										</select>
								</div>
								<div class="col-md-2" style="width:240px;">
										<select class="col-md-12 col-sm-8" name="secondOrgName" id="secondOrgName" data-placement="bottom">
										</select>
								</div>
								<%-- <div class="col-md-2" style="width:230px;">
									<select onchange="javascript:changeType(this.value);" class="col-md-12 col-sm-8" name="publisher" id="publisher" data-placement="bottom">
										<option value="">-选择机构分类-</option>
										<c:forEach  items="${classes}" var="clazz">
											<option value="${clazz.id }" <c:if test="${clazz.id==orgType }">selected</c:if>>${clazz.name }</option>
										</c:forEach>
									</select> 
								</div> --%>
							</div><!-- 表单检索row  end-->
							</div>
							<!-- 列表row  start-->
					
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
													<th class="sorting" id="orgName" tabindex="0" onclick="javascript:sort(this,'queryForm','orgName','asc');">机构名称</th>
													<th class="sorting" id="orgCode" tabindex="0" onclick="javascript:sort(this,'queryForm','orgCode','asc');">机构代码</th>
													<th class="sorting" id="orgContacter" tabindex="0" onclick="javascript:sort(this,'queryForm','orgContacter','asc');">联系人</th>
													<th class="sorting" id="orgContactEmail" tabindex="0" onclick="javascript:sort(this,'queryForm','orgContactEmail','asc');">联系人邮箱</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody>
												 <c:forEach  items="${pageContent.content}" var="organization">
													<tr organization="row" class="odd">
														<td class="center"><label class="pos-rel"> 
															<input name="ids" type="checkbox" class="ace" value="${organization.orgCode}"> <span class="lbl"></span>
														</label>
														</td>
														<td>${organization.orgName }</td>
														<td>${organization.orgCode }</td>
														<td>${organization.orgContacter }</td>
														<td>${organization.orgContactEmail }</td>
														<td>
															<a class="green" href="#" class="test" onclick="getOrganization('${organization.orgCode}')" data-toggle="modal" data-target="#templatemo_modal" title="查看详情">  <i
																	class="ace-icon fa fa-book bigger-130"></i>
															</a> &nbsp;&nbsp;&nbsp; 
															<security:authorize ifAnyGranted="ROLER_SYS_ORGANIZATION_EDIT">
																<a class="green" href="${ctx }/organization/editOrganization?orgCode=${organization.orgCode}" title="点击修改"> <i
																		class="ace-icon fa fa-pencil bigger-130"></i>
																</a> &nbsp;&nbsp;&nbsp;
																<c:choose>
																	  <c:when test="${organization.ftpAddress!=null&&organization.ftpName!=null&&organization.ftpPassWord!=null }">
																	  		<a class="green" href="${ctx }/organization/addFtp?orgCode=${organization.orgCode}" title="点击分配FTP"> <i
																					class="ace-icon fa fa-arrow-circle-up  bigger-130"></i>
																			</a> &nbsp;&nbsp;&nbsp;
																	  </c:when>
																	  <c:otherwise>
																	  		<a class="red" href="${ctx }/organization/addFtp?orgCode=${organization.orgCode}" title="点击分配FTP"> <i
																					class="ace-icon fa fa-arrow-circle-up  bigger-130"></i>
																			</a> &nbsp;&nbsp;&nbsp;
																	  </c:otherwise>
																</c:choose>
															</security:authorize>
															<security:authorize ifAnyGranted="ROLER_SYS_ORGANIZATION_DELETE">
																<a class="red" href="javascript:void(0);" title="点击删除"  onclick="deleteOrganization('${organization.orgCode}')"> <i
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
		
		organizationInit('firstOrgName', 'secondOrgName','name','${firstOrgName}','${secondOrgName}','${secondOrgName}');
	</script>

</body>
</html>
