<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" codeTable="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>代码表编辑</title>

<meta name="description" codeTable="overview &amp; stats" />
<meta name="viewport" codeTable="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<%@include file="../../common/meta.html"%>
<link rel="stylesheet" href="${ctx }/resources/script/bootstrap/css/chosen.css" />
	<script src="${ctx }/resources/script/js/validate/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/js/validate/myValidate.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/js/bootbox.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/js/chosen.jquery.js"></script>
<style type="text/css">
 .file {
    position: relative;
   display: block;
    background: #EEEEEE;
    border: 1px solid #cccccc;
    border-radius: 4px;
    padding: 10px 12px;
    overflow: hidden;
    color: #000000;
    text-decoration: none;
    text-indent: 0;
    line-height: 10px;
}
.file input {
    position: absolute;
    font-size: 100px;
    right: 0;
    top: 0;
    opacity: 0;
}
.file:hover {
    border-color: #cccccc;
    color: #000000;
    text-decoration: none;
}
textarea::-webkit-input-placeholder{color:#999;}
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
					save();
				}
					
			}); 
					 
		};
		function save(){
			 formData = new FormData($( "#mainForm" )[0]); 
	  		 //上传至后台
             $.ajax({
				url : "${ctx}/codeTable/save",  
				data : formData,
				type : 'post',
				dataType : 'text',
				async : false,
				cache : false,
				contentType : false,
				processData : false,
				success : function(data) {
					if(data==-1){
						alert("zip文件下支持xml文件");
					}else if(data==-2){
						alert("zip格式错误");
					}else if(data==-3){
						alert("文件上传失败");
					}else{
						alert("操作成功！");
						window.location="${ctx}/codeTable/list";
					}
				},
				error : function(e) {
					alert("文件上传失败！");
					window.location="${ctx}/codeTable/list";
				}
		}); 
		
		}
		function checkCodeVersion(){
		 //判断版本号是否唯一
		 
            $.ajax({
		      type : "get",  
		      url : "${ctx}/codeTable/checkCodeVersion",  
		      data: {codeVersion:$("#codeVersion").val()}, 
		      dataType:"json",  
		      success : function(isExist) {
		      	if(!isExist){
		      	    //不存在，继续
		      	}else{
		      		bootbox.alert("版本号重复!", function() {});
		      		$("#codeVersion").val("");
		      		return false;
		      	}
		      }  
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
				<!-- #section:basics/codeTable.breadcrumbs -->
				<div class="breadcrumbs" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="${ctx}/index">首页</a></li>
						<li><a href="javascript:void(0);">系统管理</a></li>
						<li class="active">代码表编辑</li>
					</ul>

				</div>
				<!-- /section:basics/codeTable.breadcrumbs -->
				<div class="page-content">
					<div class="col-md-12">
						<form id="mainForm" class="form-horizontal templatemo-create-account templatemo-container validate-form" method="post" action="${ctx}/codeTable/save" enctype="multipart/form-data">
							<input type="hidden" value="${codeTable.id}" name="id" id="id">
							<div class="form-group"  >
								<label class="col-md-2 control-label no-padding-right" for="form-field-1"> <font color="red">*</font>代码表版本号： </label>
								<div class="col-sm-10 controls">
									<div class="col-md-6" >		          	
											<c:if test="${codeTable.id==null||codeTable.id==''}">
		   								   		 <input type="text" class="col-sm-10" id="codeVersion" name="codeVersion" value="${codeTable.codeVersion }" isNum="true"
													 data-rule-required='true'  data-rule-maxlength='10'  onchange="checkCodeVersion()"placeholder="请输入数字">
										    </c:if>
											<c:if test="${codeTable.id!=null&&codeTable.id!=''}">
		   								   		 <input type="text" class="col-sm-10" id="codeVersion" name="codeVersion" disabled="disabled" value="${codeTable.codeVersion }" chrnumAndZm="true"
													 data-rule-required='true'  data-rule-maxlength='10' placeholder="请输入数字或英文字母">
										    </c:if>
										    
							        </div>	<div class="col-md-6">&nbsp; </div> 
								</div>
							</div>
							<div class="form-group" id="zipfile" >
								<label class="col-md-2 col-sm-1 control-label no-padding-right"><font color="red">*</font>代码表文件： </label>
								<div class="col-sm-10 controls">
									<div class="col-md-6" >
										<label style="margin-top: 8px;"> 
	   								    
									    <input  type="text" id="imageUrl" name="fileUrl" class="col-sm-10"  readonly="readonly"  placeholder="请选择zip格式文件" data-rule-required='true' value="${codeTable.fileUrl}"style="width:417px;" >
										<div>
										
											<c:if test="${codeTable.id==null||codeTable.id==''}">
			   								    <a href="#" class="file" style="float:left;height:27px;" id="btn">
												 <input type="file" name="file" id="file"  onchange="changeFile()" >选择文件</a>
											</c:if>
										</div>
										<%-- <c:if test="${codeTable.id!=null&&codeTable.id!=''}">
		   								    <input id="imageUrl" name="fileUrl"  disabled="disabled" data-rel="tooltip"data-placement="bottom" placeholder="请选择zip格式文件" data-rule-required='true' value="${codeTable.fileUrl}" style="float:left;width:340px;height:30px;">
										</c:if> --%>
	                            	 </div>
	                            	 <div class="col-md-6">&nbsp; </div> 
                            	 </div>
							</div>
							<%-- <div class="form-group">
								<label class="col-md-2 control-label no-padding-right" for="form-field-1"> <font color="red">*</font>状态： </label>
								<div class="col-md-6">
								    <select class="col-sm-4" id="status" name="status" title="状态" >
										<option value="2"<c:if test="${codeTable.status==2 }"> selected</c:if> >不启用</option>
										<option value="1"<c:if test="${codeTable.status==1 }"> selected</c:if> >启用</option>
									</select>
									
								</div> 
								<div class="col-md-6">&nbsp; </div> 
							</div> --%>


							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right" for="form-field-1"> 备注： </label>
								<div class="col-md-10 controls"  >
									<div class="col-md-6">
										<textarea rows="5"  class="col-md-12" id="codeDescription" name="codeDescription" data-rule-maxlength='200' placeholder="请输入200字符以内的备注" >${codeTable.codeDescription }</textarea>
									</div>
									<div class="col-md-6">&nbsp; </div> 
								</div>
							</div>

							<tags:submitAndBack submitButton="submit" submitOnclick="" backOnclick="javascript:backList();" />

						</form>
					</div>
				</div>
			</div>
		</div>
		<%@include file="../../common/footer.jsp"%>
	</div>
	<%@include file="../../common/javascript.html"%>
	<script type="text/javascript">

		jQuery(document).ready(function() {
			//启用tooltip提示信息
			$('[data-rel=tooltip]').tooltip();
			var url = document.getElementById("imageUrl").value;
			if(url!=''){
				document.getElementById("imageUrl").style.display="";
				//document.getElementById("titles").style.display="";
			}
			
			
		});

        function changeFile(){
         	var url = $('#file').val();
            
            if(url==""||url==null){
              return;
            }else{
	            var fileType = url.substring(url.lastIndexOf('.') + 1);
	            fileType =fileType.toLowerCase();
				if (fileType != "zip" && fileType != "ZIP") {
					alert("上传zip格式文件！");
					//清空input 标签
					event.target.value='';
					return ;
				}
				$("#imageUrl").val(url);
				$("#imageUrl").blur();
				$("#zipfile").removeClass("has-error");
				
            }
        
        }
		
		
		function backList() {
			//history.back();
			/* $("#mainForm").attr("action", "${ctx}/codeTable/list");
			$("#mainForm").submit(); */
			window.location.href= "${ctx}/codeTable/list.html";
		}
		
	</script>


</body>
</html>