<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>权限编辑</title>

<meta name="description" content="overview &amp; stats" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<%@include file="../../common/meta.html"%>
	<link rel="stylesheet" href="${ctx }/resources/script/bootstrap/css/chosen.css" />
	<script src="${ctx }/resources/script/js/validate/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/js/validate/myValidate.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/js/bootbox.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/js/column_authority.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/js/chosen.jquery.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
		$(document).ready(function(){
				setValidateForm();
		});
		
		// 绑定校验格式
		function setValidateForm(selector) {
			$(".validate-form").validate({
				errorElement : "span",
				errorClass : "help-block has-error",
				errorPlacement : function(e, t) {
					return t.parents(".controls").first().append(e);
				},
				highlight : function(e) {
					return $(e).closest('.form-group').removeClass("has-error has-success")
							.addClass('has-error');
				},
				success : function(e) {
					return e.closest(".form-group").removeClass("has-error");
				},
				submitHandler : function(form) {
					if($("#id").val()){
						 $.ajax({//验证登录名
						      type : "POST",  
						      url : "${ctx}/authority/isAuthorityNameExistWithId",  
						      dataType:"json",  
						      data:{id:$("#id").val(),authorityName:$("#authorityName").val()},
						      success : function(isExist) {
						      	if(!isExist){
						      		//form.submit();
						      		$.ajax({//验证邮箱
									      type : "get",  
									      url : "${ctx}/authority/isAuthorityKeyExistWithId/?id="+$("#id").val()+"&authorityKey="+$("#authorityKey").val(),  
									      dataType:"json",  
									      success : function(isExist) {
									      	if(!isExist){
									      		form.submit();
									      	}else{
									      		bootbox.alert("权限值已存在，请重新输入!", function() {});
									      		return false;
									      	}
									      }  
									  });
						      		//
						      	}else{
						      		bootbox.alert("权限名已存在，请重新输入!", function() {});
						      		return false;
						      	}
						      }  
						  });
					}else{
						$.ajax({//验证登录名
						      type : "POST",  
						      url : "${ctx}/authority/isAuthorityNameExist",  
						      dataType:"json",  
						      data:{authorityName:$("#authorityName").val()},
						      success : function(isExist) {
						      	if(!isExist){
						      		//form.submit();
						      		$.ajax({//验证登录名
									      type : "get",  
									      url : "${ctx}/authority/isAuthorityKeyExist/?authorityKey="+$("#authorityKey").val(),  
									      dataType:"json",  
									      success : function(isExist) {
									      	if(!isExist){
									      		form.submit();
									      	}else{
									      		bootbox.alert("权限值已存在，请重新输入!", function() {});
									      		return false;
									      	}
									      }  
									  });
						      	}else{
						      		bootbox.alert("权限名已存在，请重新输入!", function() {});
						      		return false;
						      	}
						      }  
						  });
					}
					/*
					if($("#id").val()){
						 
					}else{
						
					}
					*/
				}
			});
		};
</script>
</head>

<body class="no-skin">
	<%@include file="../../common/top.jsp"%>
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<%@include file="../../common/left.jsp"%>
		<div class="main-content">
			<div class="main-content-inner">
				<!-- #section:basics/content.breadcrumbs -->
				<div class="breadcrumbs" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="${ctx}/index">首页</a>
						</li>
						<li> <a href="javascript:void(0);">系统管理</a>
						</li>
						<li class="active">权限编辑</li>
					</ul>
					
				</div>
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="col-md-12">
						<form:form class="form-horizontal templatemo-create-account templatemo-container validate-form" method="POST"
							commandName="authority" action="${ctx}/authority/saveAuthority.html">
							<input type="hidden" id="id" name="id" value="${authority.id }"/>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 权限名： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									            	<input type="text" class="col-sm-10" id="authorityName" name="authorityName" value="${authority.authorityName }" isChinese="true"
															 data-rule-required='true' data-rule-minlength='2' data-rule-maxlength='30' placeholder="请输入字符长度为2-30的权限名称">
									         		 <font color="red">*</font>
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 权限值： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									            	<input type="text" class="col-sm-10" id="authorityKey" name="authorityKey" value="${authority.authorityKey }" role="true"
															data-rule-required='true' data-rule-minlength='2' data-rule-maxlength='50' placeholder="请输入字符长度为2-30的权限值，以ROLER_开头">
									         		 <font color="red">*</font>
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 所属栏目： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									        	<!-- 
										            <select class="col-xs-5 col-md-5 chosen-select" id="columnId" name="columnId" multiple="" data-placeholder="选择权限...">
										        	</select>
									        	 -->
									        	 <select class="col-xs-5 col-md-5" id="columnId" name="columnId" data-placeholder="选择权限...">
									        	 </select>
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 权限类别： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									           <input name="type" type="radio" class="ace" value="1" <c:if test="${authority.type==1 }">checked="checked"</c:if>/><span class="lbl"> 栏目权限</span>&nbsp;&nbsp;
									           <input name="type" type="radio" class="ace" value="2" <c:if test="${authority.type==2 }">checked="checked"</c:if>/><span class="lbl"> 操作权限</span>&nbsp;&nbsp;
									           <input name="type" type="radio" class="ace" value="3" <c:if test="${authority.type==3 }">checked="checked"</c:if>/><span class="lbl"> 流程操作权限</span>&nbsp;&nbsp;
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									 
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 权限状态： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									            	<select class="col-sm-4" id="status" name="status" title="权限状态" >
														<option value="1" <c:if test="${authority.status==1 }"> selected</c:if> >启用</option>
														<option value="0" <c:if test="${authority.status==0 }"> selected</c:if> >不启用</option>
													</select>
									        </div><div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									 
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 备注： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:70px;">		          	
									            <textarea class="col-sm-10" rows="3" id="remark" name="remark"
															data-rule-maxlength='200' >${authority.remark }</textarea>
															<h6 class="col-sm-10" style="text-align: left;">您还可以输入<span id="contentWord">200</span>个字符。</h6>
									        </div><div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="clearfix form-actions" style="margin-top:50px;">
										<div class="col-md-offset-3 col-md-9">
											<button class="btn btn-info" type="submit">
												<i class="ace-icon fa fa-check bigger-110"></i>
												提交
											</button>

											&nbsp; &nbsp; &nbsp;
											<button class="btn" type="button" onclick="javascript:location.href='${ctx}/authority/list'">
												<i class="ace-icon fa fa-undo bigger-110"></i>
												返回
											</button>
										</div>
									</div>
									
								</form:form>
					</div>
				</div>
			</div>
		</div>
		<%@include file="../../common/footer.jsp"%>
	</div>
<%@include file="../../common/javascript.html"%>
	<script type="text/javascript">
			jQuery(function($) {
		//And for the first simple table, which doesn't have TableTools or dataTables
				//select/deselect all rows according to table header checkbox
			var active_class = 'active';
			$('#dynamic-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
				var th_checked = this.checked;//checkbox inside "TH" table header
				
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
				});
			});
			
			//select/deselect a row when the checkbox is checked/unchecked
			$('#dynamic-table').on('click', 'td input[type=checkbox]' , function(){
				var $row = $(this).closest('tr');
				if(this.checked) $row.addClass(active_class);
				else $row.removeClass(active_class);
			});
			


			});
			$(function(){
				//content 内容简介
				var contentLength =200;
			  	$("#remark").keyup(function(){
			   		var len = $(this).val().length;
			   		if(len > (contentLength-1)){
			    		$(this).val($(this).val().substring(0,contentLength));
			   		}
			   		var num = contentLength - len;
			   		if(num>=0)
			   		$("#contentWord").text(num);
			  	});
			  	
			  	//修改初始化
			  	//内容简介
			  	var contentLen = $("#remark").val().length;
		  		if(contentLen > (contentLength-1)){
		    		$(this).val($(this).val().substring(0,contentLength));
		   		}
		   		var num = contentLength - contentLen;
		   		if(num>=0)
		   		$("#contentWord").text(num);
			});
		</script>
	<script type="text/javascript">
		$(function(){
			var columnId = "${authority.columnId}";
			var content = "<option value=''>-选择所属栏目-</option>";
			for (i in columnList){
				if(columnList[i].parentId==""){
					content+="<option disabled value='"+columnList[i].id+"' style='font-weight:bold'>"+columnList[i].columnName+"</option>";
				}else{
					content+="<option value='"+columnList[i].id+"'";
					if(columnId&&columnId.indexOf(",")!=-1){
						var temp = columnId.split(",");
						for (j in temp){
							if(columnList[i].id==temp[j]){
								content +=	"selected=\"selected\"";
							}
						}
					}
					if(columnList[i].id==columnId){
						content +=	"selected=\"selected\"";
					}
					content +=">&nbsp;&nbsp;&nbsp;&nbsp;"+columnList[i].columnName+"</option>";
				}
				
				
			}
			$("#columnId").html(content);
			
			
		});
		
		$(function($) {
	 		if(!ace.vars['touch']) {
					$('.chosen-select').chosen({allow_single_deselect:true}); 
					//resize the chosen on window resize
			
					$(window)
					.off('resize.chosen')
					.on('resize.chosen', function() {
						$('.chosen-select').each(function() {
							 var $this = $(this);
							 $this.next().css({'width': $this.parent().width()});
						})
					}).trigger('resize.chosen');
					//resize chosen on sidebar collapse/expand
					$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
						if(event_name != 'sidebar_collapsed') return;
						$('.chosen-select').each(function() {
							 var $this = $(this);
							 $this.next().css({'width': $this.parent().width()});
						})
					});
				}
	 	});
		</script>

</body>
</html>
