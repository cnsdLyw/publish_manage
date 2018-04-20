<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../../common/taglibs.jsp"%>
<%
	String tempPath = String.valueOf(System.currentTimeMillis()).substring(3);
	pageContext.setAttribute("tempPath", tempPath);
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<link href="${ctx}/resources/images/favicon.ico" rel="icon" type="image/x-icon" />
<meta charset="utf-8" />
<title>文件上传</title>
<!-- dropzone.css 必须首先引用并且必须在ace.css样式的前面 -->
<link rel="stylesheet" href="${ctx}/resources/script/bootstrap/css/dropzone.css" />
<%@include file="../../common/meta.html"%>
<script src="${ctx }/resources/script/js/statusBar.js" type="text/javascript"></script>
</head>
<body class="no-skin">
	<%@include file="../../common/top.jsp"%>
	<div class="main-container" id="main-container">
		<%@include file="../../common/left.jsp"%>
		<div class="main-content">
			<div class="main-content-inner">
				<div class="breadcrumbs" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="javascript:void(0);">首页</a></li>
						<li><i class="ace-icon fa"></i> <a href="javascript:void(0);">系统管理</a></li>
						<li><i class="ace-icon fa"></i> <a href="${ctx}/organizationLibrary/list">标准机构代码</a></li>
						<li class="active">导入excel</li>
					</ul>
				</div>

				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<div>
								<form action="${ctx }/file/uploadMagFile" method="post" class="dropzone" id="dropzone"
									enctype="multipart/form-data">
									<input type="hidden" id="tempPath" name="tempPath" value="orgLibrary/${tempPath}" />
									<input type="hidden" id="fileclass" name="fileclass" value="" />
									<input type="hidden" id="isCover" name="isCover" value="0" />
									<div class="fallback">
										<input name="file" id="file" type="file" multiple="" />
									</div>
								</form>
							</div>
							<!-- PAGE CONTENT ENDS -->
							<div class="clearfix form-actions">
								<div class="col-md-offset-3 col-md-9">
									<button class="btn btn-info" type="button" id="savefileinfo">
										<i class="ace-icon fa fa-check bigger-110"></i> 提交
									</button>

									&nbsp; &nbsp; &nbsp;
									<button class="btn" type="button" onclick="javascript:history.back();">
										<i class="ace-icon fa fa-undo bigger-110"></i> 返回
									</button>
								</div>
							</div>
						</div>
						<!-- /.row -->
					</div>
				</div>
			</div>
		</div>
		<%@include file="../../common/footer.jsp"%>
	</div>

	<%@include file="../../common/javascript.html"%>

	<script src="${ctx }/resources/script/bootstrap/js/dropzone.js"></script>
	<script src="${ctx }/resources/script/bootstrap/js/jquery.nestable.js"></script>

	<script type="text/javascript">
		jQuery(function($){
			Dropzone.autoDiscover = false;
			try {
			  var myDropzone = new Dropzone("#dropzone" , {
				  	maxFiles:1,
					acceptedFiles: ".xlsx",  //doc,.docx,.pdf,.rar,.zip,.jpg
				    paramName: "file", // The name that will be used to transfer the file
				   	//maxFilesize: 0.5, // MB
					addRemoveLinks : true,
					dictCancelUpload: "取消上传",
					dictRemoveFile:"移除文件",
					dictCancelUploadConfirmation: "确定取消上传该文件?",
					dictMaxFilesExceeded: "只能上传{{maxFiles}}个文件！",
					dictFileTooBig: "文件太大，({{filesize}}MB). 最大允许上传: {{maxFilesize}}MB.",
					dictDefaultMessage :
					"<span class=\"bigger-150 bolder\"><i class=\"ace-icon fa fa-caret-right red\"></i> 拖拽</span> 上传\
					<span class=\"smaller-80 grey\">(或点击)</span> <br /> \
					<i class=\"upload-icon ace-icon fa fa-cloud-upload blue fa-3x\"></i>",
					dictResponseError: "上传文件时发生错误!",
					previewTemplate: "<div class=\"dz-preview dz-file-preview\">\n  <div class=\"dz-details\">\n  <div class=\"dz-filename\"><span data-dz-name></span></div>\n    <div class=\"dz-size\" data-dz-size></div>\n    <img data-dz-thumbnail />\n  </div>\n  <div class=\"progress progress-small progress-striped active\"><div class=\"progress-bar progress-bar-success\" data-dz-uploadprogress></div></div>\n  <div class=\"dz-success-mark\"><span></span></div>\n  <div class=\"dz-error-mark\"><span></span></div>\n  <div class=\"dz-error-message\"><span data-dz-errormessage></span></div>\n</div>",
					removedfile: function(file) {
			        var _ref;
			        if (file.previewElement) {
			          if ((_ref = file.previewElement) != null) {
			            _ref.parentNode.removeChild(file.previewElement);
			          }
			        }
			        var clss=$('#fileclass').val();
			        
			        //从临时文件夹删除
			        $.ajax({
						type: "POST",
						url: "${ctx }/file/deleteTempFile",
						contentType: "application/x-www-form-urlencoded;charset=utf-8",
						data: {tempPath:"2/${tempPath}",fileName:file.name , fileclass:clss},
						dataType:"text",
						success: function(data){
							bootbox.alert("移除成功", function() {});
						}
					}); 
			        return this._updateMaxFilesReachedClass();
			      }
			  
			  });
			} catch(e) {
			  alert('Dropzone.js 不支持老版本的浏览器!');
			}
			
			$('.dd').nestable();
			
			$('.dd2-content').click(function(){
				$("li >div.btn-info").removeClass("btn-info");
				$(this).addClass("btn-info");
				var codetype=$(this).parent().attr("codetype");
				$("#fileclass").val(codetype);
			});
			
			$('#savefileinfo').click(function(){
				/*if($("#isCover").val()=='1'){
					alert(1);
				}*/
				var dz_details=$(".dz-details").val();
				if(undefined==dz_details){
					bootbox.setDefaults("locale", "zh_CN");
					bootbox.dialog({
						message : "<span class='bigger-110'>请先上传附件！</span>",
						buttons : {
							"click" : {
								"label" : "确定",
								"className" : "btn-sm btn-primary",
								"callback" : function() {
									
								}
							}
						}
					});
					return false;
				}
				
				Show("正在上传机构信息!");
				//下拉框等取消disabled属性
	    		qxDisabled();
				
				var clss=$('#fileclass').val();
				$.ajax({
					type: "POST",
					url: "${ctx }/organizationLibrary/importOrganizationLibrary",
					contentType: "application/x-www-form-urlencoded;charset=UTF-8",
					data: {tempPath:"orgLibrary/${tempPath}", organizationType:${organizationType}},
					dataType:"text",
					async: true,
					success: function(data){
						if(data!=""){
							if(data=="1"){
								Close();
								bootbox.alert("导入失败！", function() {});
							}else{
								Close();
								bootbox.alert(data, function() {});
							}
							
						}else{
							Close();
							bootbox.setDefaults("locale", "zh_CN");
							bootbox.dialog({
								message : "<span class='bigger-110'>导入成功！</span>",
								buttons : {
									"click" : {
										"label" : "确定",
										"className" : "btn-sm btn-primary",
										"callback" : function() {
											window.location.href="${ctx}/organizationLibrary/list"; 
										}
									}
								}
							});
						}
					}
				})
				
			});
		});
		</script>
</body>
</html>
