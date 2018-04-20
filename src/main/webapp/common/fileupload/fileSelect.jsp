<%@page import="com.litc.common.util.ConfigurationUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="rctx" value="<%=ConfigurationUtil.resource_AccessAddress%>" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<%
	String type = request.getParameter("type");
	String filelist = request.getParameter("filelist");
	String limitSize = request.getParameter("limitSize");
	String limitType = request.getParameter("limitType");
	String s = request.getParameter("s");
	if (s == null || "".equals(s)) {
		s = "";
	} else {
		s = "S";
	}
	String parentid = request.getParameter("id");
	if (parentid == null) {
		parentid = "";
	}
	String parentname = request.getParameter("name");
	if (parentname == null) {
		parentname = "";
	}
	String parentpath = request.getParameter("path");
	if (parentpath == null) {
		parentpath = "";
	}
	String parentsize = request.getParameter("size");
	if (parentsize == null) {
		parentsize = "";
	}
%>
<title>上传图片</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${ctx}/resources/script/js/swfupload/css/default.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<script src="${ctx}/resources/script/bootstrap/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/resources/script/js/swfupload/js/swfupload.js"></script>
<script type="text/javascript" src="${ctx}/resources/script/js/swfupload/js/swfupload.queue.js"></script>
<script type="text/javascript" src="${ctx}/resources/script/js/swfupload/js/fileprogress.js"></script>
<script type="text/javascript" src="${ctx}/resources/script/js/swfupload/js/handlers.js"></script>

<script type="text/javascript" language="javascript">
		var pubcourseware_swfu;//课件上传
		$(document).ready(function() {
			var pubcourseware_settings = {
					flash_url : "${ctx}/resources/script/js/swfupload/swfupload.swf",
					// 上传路径
					upload_url : "${ctx}/UploadServlet?type=fileUpload&uplaodType=<%=type%>&limitSize=<%=limitSize%>",
					// 上传大小
					file_size_limit : "<%=limitSize%> MB",
					post_params: {"PHPSESSID" : ""},
					file_types : "<%=limitType%>",
					file_types_description : "All Files",
					file_upload_limit : <%=limitSize%>,
					file_queue_limit : 0,
					//下面的参数和上传所在位置参数保持一致，否则出现等待中问题
					custom_settings : {
						progressTarget : "pubcourseware_uploadProgress",
						attachName : 'pubcourseware_attachName',
						attach_id : 'attach_id',
						name : 'name',
						path : 'path',
						ext : 'ext',
						size : 'size',
						cancelButtonId : "pubcourseware_btnCancel"
					},
					debug: false,


					// 上传动作;SELECT_FILE(单个);SELECT_FILES(多个);
					button_action : SWFUpload.BUTTON_ACTION.SELECT_FILE<%=s%>,
					// 移入小手
					button_cursor : SWFUpload.CURSOR.HAND,
					
					// 上传图片
					button_image_url : "${ctx}/resources/images/TestImageNoText_65x29.png",
					button_width: "90",
					button_height: "29",
					
					button_placeholder_id: "pubcourseware_uploadButton",
					
					button_text: '<span class="theFont">上传文件</span>',
					button_text_style: ".theFont { font-size: 13; }",
					button_text_left_padding: 2,
					button_text_top_padding: 4,
					
					// The event handler functions are defined in handlers.js
					file_queued_handler : fileQueued,
					file_queue_error_handler : fileQueueError,
					file_dialog_complete_handler : fileDialogComplete,
					upload_start_handler : uploadStart,
					upload_progress_handler : uploadProgress,
					upload_error_handler : uploadError,
					upload_success_handler : uploadSuccess,
					upload_complete_handler : uploadComplete,
					queue_complete_handler : queueComplete	// Queue plugin event
				};

				pubcourseware_swfu = new SWFUpload(pubcourseware_settings);
				
		});
		
		
		function queueComplete(numFilesUploaded) {
			//var status = document.getElementById("divStatus");
			//status.innerHTML = numFilesUploaded + " 文件" + (numFilesUploaded === 1 ? "" : "s") + " 上传完成.";
		}

		
		//一个文件上传周期完成时触发（不管是否上传成功还是失败，都会触发） 
		function uploadComplete(file) {
			//alert("上传成功！");
			if (this.getStats().files_queued === 0) {
				document.getElementById(this.customSettings.cancelButtonId).disabled = true;
			}
			var filelist="<%=filelist%>";
			
			var parentid="<%=parentid%>";
			var parentname="<%=parentname%>";
			var parentpath="<%=parentpath%>";
			var parentsize="<%=parentsize%>";
			var s="<%=s%>";

		var attach_id = $("#attach_id").val();
		var path = $("#path").val();
		var name = $("#name").val();
		var size = $("#size").val();
		var ext = $("#ext").val();

		if (parentid != "") {
			var parentidValue = parent.document.getElementById(parentid).value;
			if (s != '' && parentidValue != '') {
				parent.document.getElementById(parentid).value = parentidValue
						+ "," + attach_id;
			} else {
				//parent.document.getElementById(parentid).value=attach_id;
				//加上后，则单文件上传，修改附件时候，产生新的记录
			}
		}

		if (parentname != "") {
			var parentnameValue = parent.document.getElementById(parentname).value;
			if (s != '' && parentnameValue != '') {
				parent.document.getElementById(parentname).value = parentnameValue
						+ "," + name;
			} else {
				parent.document.getElementById(parentname).value = name;
			}

		}

		if (parentpath != "") {
			// alert("path=="+path);
			var subpath = path.substr(0, 1);
			if (subpath != "/") {
				path = "/" + path;
				// alert("path=="+path);

			}
			var parentpathValue = parent.document.getElementById(parentpath).value;
			if (s != '' && parentpathValue != '') {
				parent.document.getElementById(parentpath).value = parentpathValue
						+ "," + path;
			} else {
				parent.document.getElementById(parentpath).value = path;
			}
		}

		if (parentsize != "") {
			var parentsizeValue = parent.document.getElementById(parentsize).value;
			if (s != "" && parentsizeValue != '') {
				parent.document.getElementById(parentsize).value = parentsizeValue
						+ "," + size;
			} else {
				parent.document.getElementById(parentsize).value = size;
			}
		}

		//保存路径 D:\founder\files\
		var names = parent.document.getElementById(parentname).value.split(",");
		var paths = parent.document.getElementById(parentpath).value.split(",");

		var temp = "";
		
		for ( var i = 0; i < names.length; i++) {
			temp += "<span class='col-md-12 col-sm-12'>";
			temp += "&nbsp;&nbsp;<a href='${rctx}/"
					+ paths[i]
					+ "' target='_blank' title='"
					+ names[i]
					+ "'>"
					+ names[i] + "</a>";
			// var iclick="onclick='deleteAttachs('other_id','other_name','other_path','other_size','"+paths[i]+"',this)'";
			var iclick = "onclick='deleteAttachs(\"other_id\",\"other_name\",\"other_path\",\"other_size\",\""
					+ paths[i] + "\",this)'";

			if (s != '') {
				temp += "(<i class='ace-icon glyphicon glyphicon-remove red' "+iclick+" title='点击删除该附件'></i>)";
				temp += "&nbsp;&nbsp;&nbsp;";
			}

			//alert(list);
			temp += "</span>";
		}
		

		parent.document.getElementById(filelist).innerHTML = temp;
		
		//清空选择数据
		$("#attach_id").val("");
		$("#path").val("");
		$("#name").val("");
		$("#size").val("");
		$("#ext").val("");
		parent.document.getElementById(filelist+"Remove").style.display="block";
		
	}
</script>
</head>
<body>
	<div id="pubcourseware_uploadProgress"></div>
	<div id="pubcourseware_attachName"></div>
	<div id="pubcourseware_uploadButton"></div>
	<input id="pubcourseware_btnCancel" type="hidden" value="取消上传" onclick="pubcourseware_swfu.cancelQueue();"
		disabled="disabled" style="margin-left: 2px; font-size: 8pt; height: 29px;" />
	<input type="hidden" id="attach_id" name="attach_id" />
	<input type="hidden" id="name" name="name" />
	<input type="hidden" id="path" name="path" />
	<input type="hidden" id="ext" name="ext" />
	<input type="hidden" id="size" name="size" />

</body>
</html>
