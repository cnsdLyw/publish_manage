<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>  
<!DOCTYPE html>
<%
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>分类管理</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" 	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<%@include file="../../common/meta.html"%>
    <script src="${ctx }/resources/script/js/validate/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/js/bootbox.js" type="text/javascript"></script>
<style type="text/css">
</style>
<script type="text/javascript">
   
</script>
</head>
<body class="no-skin" >
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
						<li class="active">分类管理</li>
					</ul>
				</div>
				<!-- Modal -->
				<div class="modal fade" id="templatemo_modal" tabindex="-1" user="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				        <h4 class="modal-title" id="myModalLabel">查看用户详细信息</h4>
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
						<div class="row">
							<div class="col-sm-4" style="height:500px;">
								<div class="widget-box widget-color-blue2">
									<div class="widget-header">
										<h4 class="widget-title lighter smaller">选择分类:</h4>
										<select class="chosen-select" onchange="javascript:changeType(this.value);" style="width:50%;margin-left:50px;">
											<option value="">-选择类别-</option>
											<c:forEach  items="${list}" var="clazz">
												<option value="${clazz.code }">${clazz.name }</option>
											 </c:forEach>
										</select>
									</div>

									<div class="widget-body" id="test">
										<div class="widget-main padding-8">
											<iframe src="${ctx}/classification/leftTree?code=${code}" name="classTreeFrame" id="classTreeFrame" scrolling="yes" frameborder="0" height="725px" width="100%"></iframe>
										</div>
									</div>
								</div>
							</div>
							<div class="col-sm-8" style="padding-left: 0;">
								<div class="widget-box widget-color-blue2">
									<div class="widget-header">
										<h4 class="widget-title lighter smaller">添加/修改分类</h4>
									</div>

									<div class="widget-body">
										<div class="widget-main padding-8">
											<iframe src="" id="main_content_configure" name="main_content_configure" scrolling="no" frameborder="0" height="725px" width="100%"></iframe>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div><!-- end col-md-12 -->
				</div><!--end  main-content-inner -->
			</div><!-- end page-content -->

		<%@include file="../../common/footer.jsp"%>
	</div><!-- end main-container -->
	<%@include file="../../common/javascript.html"%>

	
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		jQuery(function($) {
			
			
		});
		
		function addClass(classKey){
			$("#main_content_configure").attr("src","${ctx}/classification/addClass?classKey="+classKey);
		}
		
		function changeType(classKey){
			$("#classTreeFrame").attr("src","${ctx}/classification/leftTree?classKey="+classKey);
		}
	
		function deleteClass(classCode){
			bootbox.setDefaults("locale","zh_CN"); 
			bootbox.confirm("确认删除，删除后不可恢复!", function (result) {  
               if(result) {//确认删除  
               		 $.ajax({
					      type : "get",  
					      url : "${ctx}/classification/deleteClass",  
					      dataType:"json",  
					      data:{classCode:classCode},
					      async: false,
					      success : function(obj) {
					      	if(obj.status=="1"){
			      				$("#classTreeFrame").attr("src","${ctx}/classification/leftTree?classKey="+obj.classKey+"&classCode="+obj.classCode);
					      	}else if(obj.status=="0"){
				      			showMessage("请先删除子节点！");
					      	}else if(obj.status=="-1"){
					      		showMessage("删除失败！");
					      	}
					      }  
					  });
               } 
	        }); 
		}
		
		function setTreeDate(classKey, classCode){
			$("#classTreeFrame").attr("src","${ctx}/classification/leftTree?classKey="+classKey+"&classCode="+classCode);
		}
	
		function showMessage(message){
			bootbox.setDefaults("locale", "zh_CN");
			bootbox.dialog({
				message : "<span class='bigger-110'>"+message+"</span>",
				buttons : {
					"click" : {
						"label" : "确定",
						"className" : "btn-sm btn-primary"
					}
				}
			});
		}
	</script>

</body>
</html>