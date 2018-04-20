<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>角色编辑</title>

<meta name="description" content="overview &amp; stats" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<%@include file="../../common/meta.html"%>
	<script src="${ctx }/resources/script/js/validate/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/js/validate/myValidate.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/js/column_authority.js" type="text/javascript"></script>
<style type="text/css">
.columnName{
	font-weight:bold;
	font-size:13px;
}

.operatorName{
	font-weight:none;
	font-size:12px;
}
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
						 $.ajax({//验证邮箱
						      type : "get",  
						      url : "${ctx}/role/isRoleExistWithId/?id="+$("#id").val()+"&roleName="+$("#roleName").val(),  
						      dataType:"json",  
						      success : function(isExist) {
						      	if(!isExist){
						      		form.submit();
						      	}else{
						      		bootbox.alert("角色已存在，请重新输入!", function() {});
						      		return false;
						      	}
						      }  
						  });
					}else{
						$.ajax({//验证登录名
							      type : "get",  
							      url : "${ctx}/role/isRoleExist/?roleName="+$("#roleName").val(),  
							      dataType:"json",  
							      success : function(isExist) {
							      	if(!isExist){
							      		form.submit();
							      	}else{
							      		bootbox.alert("角色已存在，请重新输入!", function() {});
							      		return false;
							      	}
							      }  
							  });
					}
					
					//form.submit();
				}
			});
		};
		
		function checkAll(name){
			$("input[type=checkbox][name="+name+"]").each(function() {
		        $(this).prop("checked", true);
			});
		}
		
		function unCheckAll(name){
			$("input[type=checkbox][name="+name+"]").each(function() {
		        $(this).prop("checked", false);
			});
		}
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
						<li class="active">角色编辑</li>
					</ul>
					
				</div>
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="col-md-12">
						<form:form class="form-horizontal templatemo-create-account templatemo-container validate-form" method="POST"
							commandName="role" action="${ctx}/role/saveRole.html">
							<input type="hidden" id="id" name="id" value="${role.id }"/>
									<div class="form-group">
										<label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 角色名： </label>
										<div class="col-sm-11 controls">
											<div class="col-md-6" style="height:50px;">		          	
									            	<input type="text" class="col-sm-10" id="roleName" name="roleName" value="${role.roleName }" isChinese="true"
															 data-rule-required='true' data-rule-minlength='2' data-rule-maxlength='30' placeholder="请输入字符长度为2-30的角色名称">
									         		 <font color="red">*</font>
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 设置权限： </label>
										<div class="col-sm-11 controls">
											<div class="col-md-6">
													<input name="checkAllc" type="button" onClick="javascript:checkAll('authorityIds')" value="全选"/>&nbsp;&nbsp;
													<input name="unCheckAlcl" type="button" onClick="javascript:unCheckAll('authorityIds')" value="取消全选"/>
												<br> <br>  	
									        </div>	<div class="col-md-6">&nbsp; </div> 
					                        <table class="table table-bordered">
					                            <thead>
					                                <tr>
					                                	<th class="columnName" width="12%">一级栏目</th>
					                                    <th class="columnName" width="20%">栏目权限</th>
					                                    <th class="columnName">操作权限</th>
					                                </tr>
					                            </thead>
					                            <tbody id="colunmTable">
					                               	<!-- 
						                                <tr>
						                                	<td class="columnName">书目中心</td>
						                                    <td class="columnName"><input name="form-field-checkbox" type="checkbox"/>&nbsp;出版社书目管理</td>
						                                    <td class="operatorName">
						                                    	<label class='col-md-3 col-sm-4 col-xs-6'><input name="form-field-checkbox" type="checkbox"/>&nbsp;数据采集权限</label>
						                                    	<label class='col-md-3 col-sm-4 col-xs-6'><input name="form-field-checkbox" type="checkbox"/>&nbsp;数据交换权限</label>
						                                    	<label class='col-md-3 col-sm-4 col-xs-6'><input name="form-field-checkbox" type="checkbox"/>&nbsp;数据采集权限</label>
						                                    	<label class='col-md-3 col-sm-4 col-xs-6'><input name="form-field-checkbox" type="checkbox"/>&nbsp;数据交换权限</label>
						                                    </td>
						                                </tr>
					                                 -->
					                            </tbody>
					                        </table>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 流程操作： </label>
										<div class="col-sm-11 controls">
											<div class="col-md-8" style="padding-top: 10px;">
									        	<c:forEach var="authority" items="${authorities}">
									        		<label class="pos-rel col-lg-3 col-md-6 col-sm-6">
					                                	<input name="flow_authorityIds" type="checkbox" value="${authority.id}" title="${authority.remark }" 
					                                		<c:if test="${authority.status==2 }">checked="checked"</c:if>>
					                            		 <font class='operatorName tooltip-info' data-rel='tooltip' data-placement='top' title="${authority.remark }">${authority.authorityName }</font> &nbsp;&nbsp;
					                              	</label>
									        	</c:forEach>
									        </div>	<div class="col-md-4">&nbsp; </div> 
										</div>
									</div>
									
									<div class="clearfix height"></div>
									
									<div class="form-group">
										<label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 平台： </label>
										<div class="col-sm-11 ">
											<div class="col-md-6" style="padding-top: 8px;">
											<label class="pos-rel col-lg-12 col-md-8 col-sm-6"> 
									           <!-- 
									           <input type="radio" name="organizationFlag"  value="1" <c:if test="${role.organizationFlag=='1'}">checked="checked"</c:if>/>出版   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								        	   <input type="radio" name="organizationFlag"  value="2" <c:if test="${role.organizationFlag=='2'}">checked="checked"</c:if>/>图书馆&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								        	   <input type="radio" name="organizationFlag"  value="3" <c:if test="${role.organizationFlag=='3'}">checked="checked"</c:if>/>发行 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								        	   <input type="radio" name="organizationFlag"  value="4" <c:if test="${role.organizationFlag=='4'}">checked="checked"</c:if>/>加工&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								        	    -->
								        	    <c:forEach var="orgType" items="${orgTypes}">
							            			<input type="radio" name="organizationFlag"  value="${orgType.id}" <c:if test="${role.organizationFlag==orgType.id}">checked="checked"</c:if>/>${orgType.name }
							            			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									        	</c:forEach>
								        	 
								        	 </label>  
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 备注： </label>
										<div class="col-sm-11 controls">
											<div class="col-md-6" style="height:70px;">		          	
									           <textarea type="text" class="col-sm-10" rows="3" id="remark" name="remark"
															data-rule-maxlength='50' >${role.remark }</textarea>
												<h6 class="col-sm-10" style="text-align: left;">您还可以输入<span id="contentWord">50</span>个字符。</h6>
									        </div>
									        <div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="clearfix form-actions" style="margin-top:50px;">
										<div class="col-md-offset-3 col-md-9">
											<button class="btn btn-info" type="submit">
												<i class="ace-icon fa fa-check bigger-110"></i>
												提交
											</button>

											&nbsp; &nbsp; &nbsp;
											<button class="btn" type="button" onclick="javascript:location.href='${ctx}/role/list'">
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
				var contentLength =50;
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
		var flag ='${flag}';
		$(function(){
			var content = "";
			var firstColumnNames = "";
			var firstColumnName = "";
			for (i in columnList){
				if(columnList[i].parentId==""){
					continue;
				}
				
				//不同的机构类别显示不同的栏目
				//if(!(flag&&flag==1)){
					//if(columnList[i].loginRoleS&&columnList[i].loginRoleS.indexOf(${login_role})==-1){
					//	continue ;
					//}
				//}
				
				firstColumnName = columnList[i].firstColumnName;
				if(firstColumnNames.indexOf(firstColumnName)==-1){
					firstColumnNames += firstColumnName +",";
				}else{
					firstColumnName = "";
				}
				
				content+="<tr>"+
						"<td class='columnName'>"+firstColumnName+"</td>"+
					    //"<td class='columnName' id='column"+columnList[i].id+"'><input name='authorityIds111' id='authorityId"+columnList[i].id+"' type='checkbox' value=''/>&nbsp;"+columnList[i].columnName+"</td>"+
					    "<td class='columnName' id='column"+columnList[i].id+"' >&nbsp;</td>"+
					    "<td class='operatorName' id='operator"+columnList[i].id+"'>"+
					    "</td>"+
					"</tr>";
				
			}
			$("#colunmTable").html(content);
			
			//获取栏目权限
	   	 	$.ajax( {  
		        type : "get",  
		        url : "${ctx}/authority/getAuthoritysByType/?roleId=${role.id }&type=1",  
		        dataType:"json",  
		        success : function(data) {  
			        var content="";
			        var statusStr="";
			        $.each(data, function (i, item) {
			        	var content = "<input name='authorityIds' id='authorityId"+item.id+"' type='checkbox' value='"+item.id+"'/>"+
			        		"&nbsp;<font class='tooltip-info' data-rel='tooltip' data-placement='right' title="+item.remark+">"+item.authorityName+"</font>";
			        	$("#column"+item.columnId).html(content);
			        	if(item.status==2){
			        		$("#authorityId"+item.id).attr("checked",true);	
			        	}	
			        });
			         
			        $('[data-rel=tooltip]').tooltip();
	           }  
	    	});
			//获取栏目操作权限,一个栏目可以有多个操作权限
	   	 	$.ajax( {  
		        type : "get",  
		        url : "${ctx}/authority/getAuthoritysByType/?roleId=${role.id }&type=2",  
		        dataType:"json",  
		        success : function(data) {  
			        $.each(data, function (i, item) {
			        	var content = "<label class='col-md-3 col-sm-4 col-xs-6'>"+
			        			"<input name='authorityIds' id='authorityId"+item.id+"' type='checkbox' value='"+item.id+"'/>&nbsp;"+
			        			"<font class='operatorName tooltip-info' data-rel='tooltip' data-placement='top' title='"+item.remark+"'>"+item.authorityName+"</font></label>";
			        	$("#operator"+item.columnId).append(content);
			        	if(item.status==2){
			        		$("#authorityId"+item.id).attr("checked",true);	
			        	}	
			        });
			        $('[data-rel=tooltip]').tooltip();
	           }  
	    	});
			
		});
		</script>
		
</body>
</html>
