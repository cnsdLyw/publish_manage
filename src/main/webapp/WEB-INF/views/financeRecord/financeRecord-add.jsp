<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>财务信息</title>

<meta name="description" content="overview &amp; stats" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<%@include file="../common/meta.html"%>
    <script src="${ctx }/resources/script/js/jquery.form.js" type="text/javascript"></script>
	<script src="${ctx }/resources/script/js/validate/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/js/validate/myValidate.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/js/bootbox.min.js" type="text/javascript"></script>
<style type="text/css">
.file {
    position: relative;
    display: inline-block;
    background: #46b8da;
    border: 1px solid #99D3F5;
    border-radius: 4px;
    padding: 4px 12px;
    overflow: hidden;
    color: #fff;
    text-decoration: none;
    text-indent: 0;
    line-height: 20px;
}
.file input {
    position: absolute;
    font-size: 100px;
    right: 0;
    top: 0;
    opacity: 0;
}
.file:hover {
    background: #46b8da;
    border-color: #78C3F3;
    color: #fff;
    text-decoration: none;
}
</style>
<script type="text/javascript">
		$(document).ready(function(){
			setValidateForm();
		});
		function setUrl(url){
	    	document.getElementById("imageUrl").value=url;
	    }
		
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
					//$("#amountTime").removeAttr("amountTime");
					form.submit();
				}
			});
		};
		
</script>
</head>

<body class="no-skin">
	 <%@include file="../common/top.jsp"%>
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<%@include file="../common/left.jsp"%>
		<div class="main-content">
			<div class="main-content-inner">
				<!-- #section:basics/content.breadcrumbs -->
				<div class="breadcrumbs" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="${ctx}/index">首页</a>
						</li>
						<li> <a href="javascript:void(0);">内部管理</a>
						</li>
						<li class="active">财务信息</li>
					</ul>
					
				</div>
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="col-md-12">
						<form id="mainForm" method="post" class="form-horizontal templatemo-create-account templatemo-container validate-form"
							 commandName="financeRecord" enctype="multipart/form-data"  action="${ctx}/financeRecord/saveFinanceRecord">
							<input type="hidden" id="id" name="id" value="${financeRecord.id}"/>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> 金额： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6 col-sm-10" >
									            	<input type="text" class="col-sm-10" id="amount" name="amount" value="${financeRecord.amount }" isNumber="true"
															data-rule-required='true'  data-rule-maxlength='8' placeholder="请输入金额">
									        </div>	
										</div>
									</div>
									 <div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> <span class="red">*</span> 类型： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6 col-sm-12" >		          	
							            		<select class="col-md-6 col-sm-12" id="amountType" name="amountType">
							            			<option value="+1">收入</option>
							            			<option value="-1">支出</option>
									        	</select>
									        </div>	
									    </div>
									        
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span> 经手人： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6 col-sm-10" >		          	
												<input type="text" class="col-sm-10" id="amountUser" name="amountUser" value="${financeRecord.amountUser }" isChinese="true" 
													 data-rule-required='true' data-rule-minlength='2' data-rule-maxlength='8' placeholder="请输入经手人,多个用“，”隔开">
									        </div>	
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"><span class="red">*</span>日期： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" >		          	
									            	<input type="text" class="date-picker" value="" id="amountTime" name="amountTime" value="${financeRecord.amountTime}"  data-rule-required='true'
															placeholder="请选择日期" data-date-format="yyyy-mm-dd" readonly="readonly"/>
									        </div>	
										</div>
									</div>
									
								   <!-- 相关附件  -->
								   <div class="form-group">
			                     	<label class="col-sm-2 control-label no-padding-right" for="form-field-1">附件： </label>
									<div class="col-sm-10 controls">
										<div class="col-md-6 col-sm-10" style="height:30px;margin-top:5px;">	
												<input type="file" id="attachmentFile" name="attachmentFile"/>
										</div>
									</div>
								 </div>
					
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1"> 说明： </label>
										<div class="col-sm-10 controls">
											<div class="col-md-6" style="height:150px;margin-bottom:50px;width:60%;">		          	
									            <textarea style="width:80%;" rows="8"  placeholder="请输入1-200个字符的内容" data-rule-minlength='1' data-rule-maxlength='600' id="remark" name="remark">${financeRecord.remark}</textarea>
									        	<h6 class="col-md-12" style="text-align: left;">您还可以输入<span id="word">200</span>个字符。</h6>
									        </div>
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
							</form>
					</div>
				</div>
			</div>
		</div>
		<%@include file="../common/footer.jsp"%>
	</div>
<%@include file="../common/javascript.html"%>
<script src="${ctx }/resources/script/bootstrap/js/ace/ace.js"  type="text/javascript" ></script>
<script src="${ctx }/resources/script/bootstrap/js/ace/elements.fileinput.js"  type="text/javascript" ></script>
<script src="${ctx }/resources/script/bootstrap/js/ace-elements.js"  type="text/javascript" ></script>
	<script type="text/javascript">
		$(function(){
			/*日期选择控件加载*/
			$('.date-picker').datepicker({
				autoclose : true,
				todayHighlight : true
			})
			//show datepicker when clicking on the icon
		 .next().on(ace.click_event, function() {
			$(this).prev().focus();
		}); 
		
			$('#attachmentFile').ace_file_input({
				no_file:'还未选择文件 ...',
				btn_choose:'选择',
				btn_change:'修改',
				droppable:false,
				onchange:null,
				thumbnail:false //| true | large
				//whitelist:'gif|png|jpg|jpeg'
				//blacklist:'exe|php'
				//onchange:''
				//
			});
			var textLength = 200;
		  	$("#remark").keyup(function(){
		   		var len = $(this).val().length;
		   		if(len > (textLength-1)){
		    		$(this).val($(this).val().substring(0,textLength));
		   		}
		   		var num = textLength - len;
		   		if(num>=0)
		   		$("#word").text(num);
		  	});
		 });	
		 
	</script>


</body>
</html>