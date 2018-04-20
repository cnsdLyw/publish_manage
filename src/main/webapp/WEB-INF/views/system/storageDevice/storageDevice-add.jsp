<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>存储设备编辑</title>

<meta name="description" content="overview &amp; stats" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<%@include file="../../common/meta.html"%>
	<script src="${ctx }/resources/script/js/validate/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctx }/resources/script/js/jquery.form.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/js/validate/myValidate.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/js/bootbox.js" type="text/javascript"></script>
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
					Show("正在检测FTP!");
					var options = {//验证ftp是否可用
						//async: false, 
						contentType: "application/x-www-form-urlencoded; charset=utf-8", 
						url: "${ctx}/storageDevice/checkFtp",
						success: function(data) {
							if(data.status=="1"){
								$("#stoForm").attr("action","${ctx}/storageDevice/saveStorageDevice.html");
								stoForm.submit();
							}else if(data.status=="0"){
								Close();
								bootbox.alert(data.message, function() {});
							}
						}};
						
					$("#stoForm").ajaxSubmit(options);
				}
			});
		};
		
		function checkFTP(){
			
		}
		
		var ctxPath ="${ctx}";
</script>
<script src="${ctx }/resources/script/js/statusBarExt.js" type="text/javascript"></script>
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
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="/">首页</a>
						</li>
						<li> <a href="javascript:void(0);">系统管理</a>
						</li>
						<li class="active">存储设备编辑</li>
					</ul>
					
				</div>
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="col-md-12">
						<form:form class="form-horizontal templatemo-create-account templatemo-container validate-form" method="POST" id="stoForm" name="stoForm"
							commandName="storageDevice"><!-- action="${ctx}/storageDevice/saveStorageDevice.html" -->
							<input type="hidden" id="id" name="id" value="${storageDevice.id }"/>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> ftp名称： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									            	<input type="text" class="col-sm-10" id="ftpName" name="ftpName" value="${storageDevice.ftpName }"  stringCheck="true"
															 data-rule-required='true' data-rule-minlength='2' data-rule-maxlength='30' placeholder="ftp名称">
									         		 <font color="red">*</font>
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> ftp标识： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									            	<input type="text" class="col-sm-10" id="ftpCode" name="ftpCode" value="${storageDevice.ftpCode }"  chrnum="true"
															 data-rule-required='true' data-rule-minlength='2' data-rule-maxlength='30' placeholder="ftp标识">
									         		 <font color="red">*</font>
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> ftp文件夹地址： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									            	<input type="text" class="col-sm-10" id="localFolderPath" name="localFolderPath" value="${storageDevice.localFolderPath }"
															data-rule-minlength='2' data-rule-maxlength='100' placeholder="ftp文件夹地址">
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> ftp地址： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									            	<input type="text" class="col-sm-10" id="ftpUrl" name="ftpUrl" value="${storageDevice.ftpUrl }" isFtp="true"
															 data-rule-required='true' data-rule-minlength='2' data-rule-maxlength='100' placeholder="ftp地址">
									         		 <font color="red">*</font>
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> ftp用户： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									            	<input type="text" class="col-sm-10" id="ftpUser" name="ftpUser" value="${storageDevice.ftpUser }" chrnum="true"
															 data-rule-required='true' data-rule-minlength='2' data-rule-maxlength='15' placeholder="ftp用户">
									         		 <font color="red">*</font>
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> ftp密码： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:50px;">		          	
									            	<input type="text" class="col-sm-10" id="ftpPassword" name="ftpPassword" value="${storageDevice.ftpPassword }" 
															 data-rule-required='true' data-rule-minlength='4' data-rule-maxlength='15' placeholder="ftp密码">
									         		 <font color="red">*</font>
									        </div>	<div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 备注： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:70px;">		          	
									            <textarea class="col-sm-10" rows="3" id="remark" name="remark"
															data-rule-maxlength='50' >${storageDevice.remark }</textarea>
									        </div><div class="col-md-6">&nbsp; </div> 
										</div>
									</div>
									
									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											<button class="btn btn-info" type="submit">
												<i class="ace-icon fa fa-check bigger-110"></i>
												提交
											</button>

											&nbsp; &nbsp; &nbsp;
											<button class="btn" type="button" onclick="javascript:history.back();">
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
		</script>


</body>
</html>
