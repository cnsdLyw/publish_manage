<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>  
<!DOCTYPE html>
<%
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>供应商</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" 	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<%@include file="../common/meta.html"%>
    <script src="${ctx }/resources/script/js/validate/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/js/bootbox.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/BootstrapMenu.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
    function getSupplier(id) {
	    $.ajax( {  
	        type : "get",  
	        url : "${ctx}/supplier/getJsonSupplier/?id="+id,  
	        dataType:"json",  
	        success : function(supplier) {
	           	var content="<p><strong class='pTitle'>公司名称</strong>："+supplier.companyName+" </p>"+
	           				"<p><strong class='pTitle'>行业分类</strong>："+(supplier.industryClassification!=null?supplier.industryClassification:"")+" </p>"+
	           				"<p><strong class='pTitle'>主管人</strong>："+(supplier.supervisor!=null?supplier.supervisor:"")+" </p>"+
	           				"<p><strong class='pTitle'>主管人电话</strong>："+(supplier.supervisorPhone!=null?supplier.supervisorPhone:"")+" </p>"+
	           				"<p><strong class='pTitle'>业务联系人</strong>："+(supplier.businessContacts!=null?supplier.businessContacts:"")+" </p>"+
	           				"<p><strong class='pTitle'>业务联系人电话</strong>："+(supplier.businessContactsPhone!=null?supplier.businessContactsPhone:"")+" </p>"+
	           				"<p><strong class='pTitle'>业务联系人邮箱</strong>："+(supplier.businessContactsEmail!=null?supplier.businessContactsEmail:"")+" </p>"+
	           				"<p><strong class='pTitle'>地区</strong>："+(supplier.province!=null?supplier.province:"")+"&nbsp;&nbsp;"+(supplier.city!=null?supplier.city:"")+"&nbsp;&nbsp;"+(supplier.county!=null?supplier.county:"")+" </p>"+
	           				"<p><strong class='pTitle'>供应商地址</strong>："+(supplier.address!=null?supplier.address:"")+" </p>"+
	           				"<p><strong class='pTitle'>邮编</strong>："+(supplier.postalCode!=null?supplier.postalCode:"")+" </p>"+
	           				"<p><strong class='pTitle'>公司网址</strong>："+(supplier.website!=null?supplier.website:"")+" </p>"+
	           				"<p><strong class='pTitle'>公司电话</strong>："+(supplier.telephone!=null?supplier.telephone:"")+" </p>"+
	           				"<p><strong class='pTitle'>公司传真</strong>："+(supplier.fax!=null?supplier.fax:"")+" </p>"+
	           				"<p><strong class='pTitle'>主管人身份证正面</strong>："+(supplier.idCardObverse!=null?"<a target='_blank' href='${ctx}"+supplier.idCardObverse+"/' title='点击预览'>"+supplier.idCardObverseName+"</a>":"")+" </p>"+
	           				"<p><strong class='pTitle'>主管人身份证反面</strong>："+(supplier.idCardReverse!=null?"<a target='_blank' href='${ctx}"+supplier.idCardReverse+"/' title='点击预览'>"+supplier.idCardReverseName+"</a>":"")+" </p>"+
	           				"<p><strong class='pTitle'>企业营业执照副本</strong>："+(supplier.businessLicenceCopy!=null?"<a target='_blank' href='${ctx}"+supplier.businessLicenceCopy+"/' title='点击预览'>"+supplier.businessLicenceCopyName+"</a>":"")+" </p>"+
	           				"<p><strong class='pTitle'>授权书</strong>："+(supplier.certificateAuthorization!=null?"<a target='_blank' href='${ctx}"+supplier.certificateAuthorization+"/' title='点击预览'>"+supplier.certificateAuthorizationName+"</a>":"")+" </p>"+
	           				"<p><strong class='pTitle'>供应商简介</strong>："+(supplier.remark!=null?supplier.remark:"")+" </p>"+
	           				"<p><strong class='pTitle'>最后修改时间</strong>："+(supplier.lastModifyTime!=null?FormatDate(supplier.lastModifyTime):"")+" </p>"
	           				;
				$("#detailContent").html(content);
	        }  
	    });  
	} 
        
	function deleteSupplier(id){
		bootbox.setDefaults("locale","zh_CN"); 
		bootbox.confirm("确认删除，删除后不可恢复!", function (result) {  
               if(result) {//确认删除  
                  	document.location.href="${ctx}/supplier/deleteSupplier/"+id+".html";
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
                	document.location.href="${ctx}/supplier/deleteSuppliers/"+ids+".html";
                } 
         });  
		}else{
			bootbox.alert("您还没有选择要删除的供应商!", function() {});
		}
	}
	
	function FormatDate (strTime) {
		if(strTime){
			var date = new Date(strTime);
			return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
		    //return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
		}
	    return '';
	}
	
	function hideBlock(){
		$("#warning-block").hide();
	}
	<c:if test="${param.message==1||param.message==0||param.message==-1}">
		setTimeout("javascript:hideBlock();", 3500)
	</c:if>
</script>
    <style type="text/css">
		.pTitle{
			font-size: 16px;
			color: #269abc;//rgb(74, 164, 180);
			font-family:microsoft yahei;
		}
	</style>
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
						<li></i> <a href="#">供应商系统</a></li>
						<li class="active">供应商</li>
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
				<div class="modal fade" id="templatemo_modal" tabindex="-1" supplier="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				        <h4 class="modal-title" id="myModalLabel">查看供应商详细信息</h4>
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
					<form action="${ctx }/supplier/list" method="post" id="queryForm">
						<input type="hidden" id="orderType" name="orderType" value="${orderType }" />
						<input type="hidden" id="sortType" name="sortType" value="${sortType }" />
					    <input type="hidden" id="pageNo" name="pageNo" />
						<!-- 操作按钮start -->
						<div class="page-header">
							<div>
								<button class="btn btn-sm btn-primary " type="button"  onclick="javascript:location.href='${ctx}/supplier/addSupplier'" id="demo1Box">
									新建</button>&nbsp;&nbsp;&nbsp;
								<button class="btn btn-sm btn-primary " type="button" onclick="javascript:deleteAll();">删除</button>&nbsp;&nbsp;&nbsp;
								<!-- 
									<button class="btn btn-sm btn-primary " type="button" onclick="javascript:refreshAuthority();">用户权限刷新</button>&nbsp;&nbsp;&nbsp;
								 -->
								<!-- 表单检索row  start-->
								<div class="col-md-4 col-sm-2 pull-right">
									<button class="btn btn-sm btn-primary  pull-right" style="margin-bottom: 7px;" type="submit">
										<i class="ace-icon glyphicon glyphicon-search"></i> 搜 索
									</button>
									<input type="text" class="col-md-5 col-sm-2 pull-right" id="keyWord" name="keyWord"  value="${keyWord }" placeholder="公司名称|联系人" data-rel="tooltip" title="登录名|姓名">
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
											supplier="grid" aria-describedby="dynamic-table_info">
											<thead>
												<tr supplier="row">
													<th class="center sorting_disabled" rowspan="1" colspan="1"
														aria-label=""><label class="pos-rel"> <input
															type="checkbox" class="ace"> <span class="lbl"></span>
													</label></th>
													<th class="sorting" id="companyName" tabindex="0" onclick="javascript:sort(this,'queryForm','companyName','asc');">公司名称</th>
													<th class="sorting" id="industryClassification" tabindex="0" onclick="javascript:sort(this,'queryForm','industryClassification','asc');">行业分类</th>
													<th class="sorting" id="supervisor" tabindex="0" onclick="javascript:sort(this,'queryForm','supervisor','asc');">主管人</th>
													<th class="sorting hidden-1024" id="supervisorPhone" tabindex="0" onclick="javascript:sort(this,'queryForm','supervisorPhone','asc');">主管人电话</th>
													<th class="sorting" id="businessContacts" tabindex="0" onclick="javascript:sort(this,'queryForm','businessContacts','asc');">业务联系人</th>
													<th class="sorting hidden-1024" id="businessContactsPhone" tabindex="0" onclick="javascript:sort(this,'queryForm','businessContactsPhone','asc');">业务联系人电话</th>
													<th class="sorting" id="businessContactsEmail" tabindex="0" onclick="javascript:sort(this,'queryForm','businessContactsEmail','asc');">业务联系人邮箱</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach  items="${pageContent.content}" var="supplier">
													<tr user="row">
														<td class="center"><label class="pos-rel"> 
															<input name="ids" type="checkbox" class="ace" value="${supplier.id }"> <span class="lbl"></span>
														</label>
														</td>
														<td>${supplier.companyName }</td>
														<td>
															<c:if test="${supplier.industryClassification!=null&&supplier.industryClassification!='' }">
																${clazzMap[supplier.industryClassification] } 
															</c:if>
														</td>
														<td>${supplier.supervisor }</td>
														<td class="hidden-1024">${supplier.supervisorPhone }</td>
														<td>${supplier.businessContacts }</td>
														<td class="hidden-1024">${supplier.businessContactsPhone }</td>
														<td>${supplier.businessContactsEmail }</td>
														<td>
															<a class="green" href="#" class="test" onclick="getSupplier(${supplier.id})" data-toggle="modal" data-target="#templatemo_modal" title="查看详情">  <i
																	class="ace-icon fa fa-book bigger-130"></i>
															</a> &nbsp;&nbsp;&nbsp; 
															<a class="green" href="${ctx }/supplier/editSupplier/${supplier.id}" title="点击修改"> <i class="ace-icon fa fa-pencil bigger-130"></i>
															</a> &nbsp;&nbsp;&nbsp;
															<a class="red" href="javascript:void(0);" title="点击删除"  onclick="deleteSupplier(${supplier.id})"> <i
																		class="ace-icon fa fa-trash-o bigger-130"></i>
															</a>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<!-- 分页 begin -->
										<tags:page dataPage="${pageContent}" paginationSize="2"/>   
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
	</script>
</body>
</html>
