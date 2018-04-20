<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>  
<!DOCTYPE html>
<%
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>财务信息</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" 	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<%@include file="../common/meta.html"%>
    <script src="${ctx }/resources/script/js/validate/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/js/bootbox.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/BootstrapMenu.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
    function getFinanceRecord(id) {
	    $.ajax( {  
	        type : "get",  
	        url : "${ctx}/financeRecord/getJsonFinanceRecord/?id="+id,  
	        dataType:"json",  
	        success : function(financeRecord) {
	           	var content="<p><strong class='pTitle'>金额</strong>："+financeRecord.amount+" </p>"+
	           				"<p><strong class='pTitle'>类别</strong>：";
	           				if(financeRecord.amountType!=null){
	           					if(financeRecord.amountType=="-1"){
	           						 content+="<span class='label label-sm label-success'>支出</span>";
	           					}else if(financeRecord.amountType=="+1"){
			           				 content+="<span class='label label-sm label-danger'>收入</span>";
	           						
	           					}
	           				}
	           	    content+=" </p>"+
	           				"<p><strong class='pTitle'>状态</strong>：";
	           				
	           				if(financeRecord.status!=null){
	           					if(financeRecord.status=="0"){
	           						content+="<span class='label label-sm label-info arrowed-in'>待审核 </span>";
	           					}else if(financeRecord.status=="1"){
			           				content+="<span class='label label-sm label-success'>审核通过</span>";
	           					}else if(financeRecord.status=="2"){
	           						content+="<span class='label label-sm label-warning arrowed-in arrowed-right'>审核不通过</span>";
	           					}
	           				}
	           	   content+=" </p>"+
	           	   			"<p><strong class='pTitle'>经手人</strong>："+(financeRecord.amountUser!=null?financeRecord.amountUser:"")+" </p>"+
	           				"<p><strong class='pTitle'>时间</strong>："+(financeRecord.amountTime!=null?FormatDate(financeRecord.amountTime):"")+" </p>"+
	           				"<p><strong class='pTitle'>说明</strong>："+(financeRecord.remark!=null?financeRecord.remark:"")+" </p>"+
	           				"<p><strong class='pTitle'>录入人员</strong>："+(financeRecord.operator!=null?financeRecord.operator:"")+" </p>"+
	           				"<p><strong class='pTitle'>审核人员</strong>："+(financeRecord.auditor!=null?financeRecord.auditor:"")+" </p>"+
	           				"<p><strong class='pTitle'>审核意见</strong>："+(financeRecord.auditOpinion!=null?financeRecord.auditOpinion:"")+" </p>"+
	           				"<p><strong class='pTitle'>审核时间</strong>："+(financeRecord.auditTime!=null?FormatDate(financeRecord.auditTime):"")+" </p>"+
	           				"<p><strong class='pTitle'>相关附件</strong>："+(financeRecord.attachment!=null?"<a target='_blank' href='${ctx}"+financeRecord.attachment+"/' title='点击预览'>"+financeRecord.attachmentName+"</a>":"")+" </p>"+
	           				"<p><strong class='pTitle'>供应商简介</strong>："+(financeRecord.remark!=null?financeRecord.remark:"")+" </p>"+
	           				"<p><strong class='pTitle'>录入时间</strong>："+(financeRecord.lastModifyTime!=null?FormatDate(financeRecord.lastModifyTime):"")+" </p>"
	           				;
				$("#detailContent").html(content);
	        }  
	    });  
	} 
        
	function deleteFinanceRecord(id){
		bootbox.setDefaults("locale","zh_CN"); 
		bootbox.confirm("确认删除，删除后不可恢复!", function (result) {  
               if(result) {//确认删除  
                  	document.location.href="${ctx}/financeRecord/deleteFinanceRecord/"+id+".html";
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
                	document.location.href="${ctx}/financeRecord/deleteFinanceRecords/"+ids+".html";
                } 
         });  
		}else{
			bootbox.alert("您还没有选择要删除的财务信息!", function() {});
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
						<li></i> <a href="#">内部管理</a></li>
						<li class="active">财务信息</li>
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
				<div class="modal fade" id="templatemo_modal" tabindex="-1" financeRecord="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				        <h4 class="modal-title" id="myModalLabel">查看财务信息详细信息</h4>
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
					<form action="${ctx }/financeRecord/list" method="post" id="queryForm">
						<input type="hidden" id="orderType" name="orderType" value="${orderType }" />
						<input type="hidden" id="sortType" name="sortType" value="${sortType }" />
					    <input type="hidden" id="pageNo" name="pageNo" />
						<!-- 操作按钮start -->
						<div class="page-header">
							<div>
								<button class="btn btn-sm btn-primary " type="button"  onclick="javascript:location.href='${ctx}/financeRecord/addFinanceRecord'" id="demo1Box">
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
											financeRecord="grid" aria-describedby="dynamic-table_info">
											<thead>
												<tr financeRecord="row">
													<th class="center sorting_disabled" rowspan="1" colspan="1"
														aria-label=""><label class="pos-rel"> <input
															type="checkbox" class="ace"> <span class="lbl"></span>
													</label></th>
													<th class="sorting" id="amount" tabindex="0" onclick="javascript:sort(this,'queryForm','amount','asc');">金额</th>
													<th class="sorting" id="amountType" tabindex="0" onclick="javascript:sort(this,'queryForm','amountType','asc');">类别</th>
													<th class="sorting" id="amountUser" tabindex="0" onclick="javascript:sort(this,'queryForm','amountUser','asc');">经手人</th>
													<th class="sorting" id="amountTime" tabindex="0" onclick="javascript:sort(this,'queryForm','amountTime','asc');">时间</th>
													<th class="sorting" id="status" tabindex="0" onclick="javascript:sort(this,'queryForm','status','asc');">状态</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach  items="${pageContent.content}" var="financeRecord">
													<tr user="row">
														<td class="center"><label class="pos-rel"> 
															<input name="ids" type="checkbox" class="ace" value="${financeRecord.id }"> <span class="lbl"></span>
														</label>
														</td>
														<td>${financeRecord.amount }</td>
														<td>
															<c:if test="${financeRecord.amountType=='-1' }">
																<span class="label label-sm label-success">支出</span>
															</c:if>
															<c:if test="${financeRecord.amountType=='+1' }">
																<span class="label label-sm label-danger">收入</span>
															</c:if>
														
														</td>
														<td>${financeRecord.amountUser }</td>
														<td>
															<fmt:formatDate  value='${financeRecord.amountTime }' type='both' pattern='yyyy-MM-dd' />
														</td>
														<td>
															<c:if test="${financeRecord.status==0 }"><span class="label label-sm label-info arrowed-in">待审核 </span></c:if>
												        	<c:if test="${financeRecord.status==1 }"><span class="label label-sm label-success">审核通过</span></c:if>
												        	<c:if test="${financeRecord.status==2 }"><span class="label label-sm label-warning arrowed-in arrowed-right">审核不通过</span></c:if>
															
														</td>
														<td>
															<a class="green" href="#" class="test" onclick="getFinanceRecord(${financeRecord.id})" data-toggle="modal" data-target="#templatemo_modal" title="查看详情">  <i
																	class="ace-icon fa fa-book bigger-130"></i>
															</a> &nbsp;&nbsp;&nbsp; 
															<a class="green" href="${ctx }/financeRecord/editFinanceRecord/${financeRecord.id}" title="点击修改"> <i class="ace-icon fa fa-check bigger-130"></i>
															</a> &nbsp;&nbsp;&nbsp;
															<a class="red" href="javascript:void(0);" title="点击删除"  onclick="deleteFinanceRecord(${financeRecord.id})"> <i
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
