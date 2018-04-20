<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>  
<!DOCTYPE html>
<%
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>客户登记</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" 	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<%@include file="../common/meta.html"%>
    <script src="${ctx }/resources/script/js/validate/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/js/bootbox.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/BootstrapMenu.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
    function getUser(id) {
	    $.ajax( {  
	        type : "get",  
	        url : "${ctx}/user/getJsonUser/?id="+id,  
	        dataType:"json",  
	        success : function(user) {
	       		var content = '';
   				for(var i =0;i<user.roleList.length;i++){
   					content+=user.roleList[i].roleName+"&nbsp;&nbsp;&nbsp;&nbsp;";
   					if((i+1)%3==0&&(i+1)<user.roleList.length){
   						content+="<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
   					}
   				}
   				
   				var orgName='';
   				if(user.organization){  
   					orgName=user.organization.orgName;
   				}
	           	var contentInner="<p>登录名："+user.loginName+" </p>"+
	           				/* "<p>所属机构："+orgName+" </p>"+ */
	           				"<p>角色："+content+" </p>"+
	           				"<p>姓名："+user.name+" </p>"+
	           				"<p>性别："+(user.sex!=null?user.sex:"")+" </p>"+
	           				"<p>邮箱："+user.email+" </p>"+
	           				"<p>联系电话："+(user.phone!=null?user.phone:"")+" </p>"+
	           				"<p>地址："+(user.address!=null?user.address:"")+" </p>";
				    $("#detailContent").html(contentInner);
	        }  
	    });  
	} 
        
	function deleteUser(id){
		bootbox.setDefaults("locale","zh_CN"); 
		bootbox.confirm("确认删除，删除后不可恢复!", function (result) {  
               if(result) {//确认删除  
                  	document.location.href="${ctx}/user/deleteUser/"+id+".html";
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
                	document.location.href="${ctx}/user/deleteUsers/"+ids+".html";
                } 
         });  
		}else{
			bootbox.alert("您还没有选择要删除的用户!", function() {});
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
						<li></i> <a href="#">交易平台</a></li>
						<li class="active">客户登记</li>
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
				  		 	操作失败！<c:if test="${param.message==-1 }">删除的用户有角色配置！</c:if>
					</div>
				</c:if>
				<!-- Modal -->
				<div class="modal fade" id="templatemo_modal" tabindex="-1" user="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
				        <h4 class="modal-title" id="myModalLabel">查看产品详细信息</h4>
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
					<form action="${ctx }/user/list" method="post" id="queryForm">
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
									<input type="text" class="col-md-5 col-sm-2 pull-right" id="keyWord" name="keyWord"  value="${keyWord }" placeholder="产品名称|代码" data-rel="tooltip" title="登录名|姓名">
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
													<th class="sorting" id="loginName" tabindex="0" onclick="javascript:sort(this,'queryForm','loginName','asc');">姓名</th>
													<th class="sorting" id="name" tabindex="0" onclick="javascript:sort(this,'queryForm','name','asc');">身份证号</th>
													<th class="sorting" id="name" tabindex="0" onclick="javascript:sort(this,'queryForm','name','asc');">性别</th>
													<th class="sorting" id="name" tabindex="0" onclick="javascript:sort(this,'queryForm','name','asc');">联系电话</th>
													<th class="sorting" id="name" tabindex="0" onclick="javascript:sort(this,'queryForm','name','asc');">投资情况</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody>
													<tr user="row">
														<td class="center"><label class="pos-rel"> 
															<input name="ids" type="checkbox" class="ace" value="1}"> <span class="lbl"></span>
														</label>
														</td>
														<td>张三</td>
														<td>111111111111111111</td>
														<td>男</td>
														<td>11111111111</td>
														<td><a href="javascript:void(0);">明细</a></td>
														<td>
															<a class="green" href="#" class="test" onclick="getUser(${user.id})" data-toggle="modal" data-target="#templatemo_modal" title="查看详情">  <i
																	class="ace-icon fa fa-book bigger-130"></i>
															</a> &nbsp;&nbsp;&nbsp; 
															<security:authorize ifAnyGranted="ROLER_SYS_USER_EDIT">	
																<a class="green" href="void(0)" title="点击修改"> <i
																		class="ace-icon fa fa-pencil bigger-130"></i>
																</a> &nbsp;&nbsp;&nbsp;
															</security:authorize>
														</td>
													</tr>
											</tbody>
										</table>
										<!-- 分页 begin -->
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
		
	//刷新用户权限
	function refreshAuthority(){
		bootbox.setDefaults("locale","zh_CN"); 
		bootbox.confirm("确认刷新!", function (result) {  
			Show("刷新系统权限");
			$.ajax({
		        type : "get",
		        url : "${ctx}/authority/refreshAuthority?orgCode=${sessionScope.loginOrgCode}",
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
	
	<script>
	/*
var menu = new BootstrapMenu("#demo1Box", {
  actions: [{
	name: "jQuery特效  <i class='ace-icon fa fa-pencil bigger-130'></i>",
	iconClass: "fa-plus",
	onClick: function() {
	  toastr.info("'jQuery特效 <i class='ace-icon fa fa-pencil bigger-130'></i>");
	}
  }, {
	name: '站长素材',
	iconClass: 'fa-edit',
	onClick: function() {
	  toastr.info("'站长素材");
	}
  }, {
	name: '站长之家',
	iconClass: 'fa-trash',
	onClick: function() {
	  toastr.info("'站长之家");
	}
  }]
});

	
	$("#dynamic-table").dataTable({
		initComplete: function () {
		    $("#dynamic-table tbody tr").removeClass("odd");
		    $("#dynamic-table tbody tr").removeClass("even");
		    $("#dynamic-table tbody tr").addClass("DynamicAdd");
		}
	});
	*/
</script>
</body>
</html>
