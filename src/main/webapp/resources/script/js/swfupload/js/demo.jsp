<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${ctx }/js/swfupload/css/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/js/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${ctx}/js/swfupload/js/swfupload.queue.js"></script>
<script type="text/javascript" src="${ctx}/js/swfupload/js/fileprogress.js"></script>
<script type="text/javascript" src="${ctx}/js/swfupload/js/handlers.js"></script>
		
		
	<script type="text/javascript">
		var videoupload_swfu;
		window.onload = function() {
			var videoupload_settings = {
				flash_url : "${ctx}/js/swfupload/swfupload.swf",
				// 上传路径
				upload_url : "${ctx}/UploadServlet?type=videoupload",
				// 上传大小
				file_size_limit : "2048 MB",
				post_params: {"PHPSESSID" : ""},
				file_types : "*.*",
				file_types_description : "All Files",
				file_upload_limit : 100,
				file_queue_limit : 0,
				custom_settings : {
					progressTarget : "videoupload_uploadProgress",
					attachName : 'videoupload_attachName',
					name : 'videoupload_name',
					path : 'videoupload_path',
					cancelButtonId : "btnCancel"
				},
				debug: false,


				// 上传动作;SELECT_FILE(单个);SELECT_FILES(多个);
				button_action : SWFUpload.BUTTON_ACTION.SELECT_FILE,
				// 移入小手
				button_cursor : SWFUpload.CURSOR.HAND,
				// 上传图片
				button_image_url : "${ctx}/images/btn_upload.gif",
				// 图片宽度
				button_width : "120",
				// 图片高度
				button_height : "42",
				// 替换按钮的DOM
				
				button_placeholder_id: "videoupload_uploadButton",
				button_text_style: ".theFont { font-size: 16; }",
				button_text_left_padding: 12,
				button_text_top_padding: 3,
				
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

			videoupload_swfu = new SWFUpload(videoupload_settings);
	     };
	</script>
		
</head>
<body>

<body>
	<div id="content">
		
		<div class="fieldset flash" id="videoupload_uploadProgress">
			<span class="legend">上传队列</span>
		</div>
		
		<div id="divStatus"></div>
		<div>
			<span id="videoupload_uploadButton"></span>
			<input type="hidden" id="videoupload_name" name="videoupload_name" />
			<input type="hidden" id="videoupload_path" name="orimediapath" value="" />
			<input id="btnCancel" type="button" value="取消上传" onclick="videoupload_swfu.cancelQueue();" disabled="disabled" style="margin-left: 2px; font-size: 8pt; height: 29px;" />
		</div>
	</div>

</body>
</body>
</html>