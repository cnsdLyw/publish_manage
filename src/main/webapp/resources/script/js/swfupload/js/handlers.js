/* Demo Note:  This demo uses a FileProgress class that handles the UI for displaying the file name and percent complete.
The FileProgress class is not part of SWFUpload.
*/


/* **********************
   Event Handlers
   These are my custom event handlers to make my
   web application behave the way I went when SWFUpload
   completes different tasks.  These aren't part of the SWFUpload
   package.  They are part of my application.  Without these none
   of the actions SWFUpload makes will show up in my application.
   ********************** */

function addMsg(id, msg, hide) {
	var isHide = hide || true;
	if (id) {
		var progressTarget = $('#' + id);
		progressTarget.text(msg);
		if (isHide) {
			setTimeout(function() {
						progressTarget.text('');
					}, 5000);
		}
	}
}


function fileQueued(file) {
	try {
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setStatus("等待中...");
		progress.toggleCancel(true, this);

	} catch (ex) {
		this.debug(ex);
	}

}


function fileQueueError(file, errorCode, message) {
	try {
		if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
			alert("对不起，只能上传一个文件！");
			return;
		}

		var id = this.customSettings.progressTarget;

		switch (errorCode) {
			case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT :
				addMsg(id, "对不起，您只能上传小于" + this.settings.file_size_limit
								+ "的文件！");
				this
						.debug("Error Code: File too big, File name: "
								+ file.name + ", File size: " + file.size
								+ ", Message: " + message);
				break;
			case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE :
				addMsg(id, "对不起，不能上传0字节的文件！");
				this.debug("Error Code: Zero byte file, File name: "
						+ file.name + ", File size: " + file.size
						+ ", Message: " + message);
				break;
			case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE :
				addMsg(id, "对不起，只能上传" + this.settings.file_types + "类型的文件！");
				this.debug("Error Code: Invalid File Type, File name: "
						+ file.name + ", File size: " + file.size
						+ ", Message: " + message);
				break;
			default :
				addMsg(id, "未知错误！");
				this.debug("Error Code: " + errorCode + ", File name: "
						+ file.name + ", File size: " + file.size
						+ ", Message: " + message);
				break;
		}
	} catch (ex) {
		this.debug(ex);
	}
}


function fileDialogComplete(numFilesSelected, numFilesQueued) {
	try {
		if (numFilesSelected > 0) {
			document.getElementById(this.customSettings.cancelButtonId).disabled = false;
		}
		
		/* I want auto start the upload and I can do that here */
		this.startUpload();
	} catch (ex)  {
        this.debug(ex);
	}
}

function uploadStart(file) {
	try {
		/* I don't want to do any file validation or anything,  I'll just update the UI and
		return true to indicate that the upload should start.
		It's important to update the UI here because in Linux no uploadProgress events are called. The best
		we can do is say we are uploading.
		 */
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setStatus("开始上传...");
		progress.toggleCancel(true, this);
	}
	catch (ex) {}
	
	return true;
}

function uploadProgress(file, bytesLoaded, bytesTotal) {
	try {
		var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);

		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setProgress(percent);
		progress.setStatus("正在上传...");
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadSuccess(file, serverData) {
	try {
//		var progress = new FileProgress(file, this.customSettings.progressTarget);
//		progress.setComplete();
//		progress.setStatus("上传完成.");
//		progress.toggleCancel(false);

		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setComplete();
		//progress.setStatus("上传完成.");
		progress.toggleCancel(false);
		
		eval("var serverData = " + serverData);
		//alert("serverData.success=="+serverData.success);
		if (serverData.success == 'true') {
//			alert(this.customSettings.attach_id);
			if (this.customSettings.attach_id) {
				append(this.customSettings.attach_id, serverData.attach_id,
						this.settings.button_action);
			}
			if (this.customSettings.name) {
				//alert("this.customSettings.name=="+this.customSettings.name+",serverData.name=="+serverData.name);
				append(this.customSettings.name, serverData.name,
						this.settings.button_action);
			}
			if (this.customSettings.alias) {
				append(this.customSettings.alias, serverData.alias,
						this.settings.button_action);
			}
			if (this.customSettings.ext) {
				append(this.customSettings.ext, serverData.ext,
						this.settings.button_action);
			}
			if (this.customSettings.size) {
				append(this.customSettings.size, serverData.size,
						this.settings.button_action);
			}
			if (this.customSettings.path) {
				append(this.customSettings.path, serverData.path,
						this.settings.button_action);
			}
			addMsg(this.customSettings.progressTarget, '上传完成...');//此处去除，则进度条会消失
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadError(file, errorCode, message) {
	try {
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setError();
		progress.toggleCancel(false);

		switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
			progress.setStatus("Upload Error: " + message);
			this.debug("Error Code: HTTP Error, File name: " + file.name + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
			progress.setStatus("Upload Failed.");
			this.debug("Error Code: Upload Failed, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.IO_ERROR:
			progress.setStatus("Server (IO) Error");
			this.debug("Error Code: IO Error, File name: " + file.name + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
			progress.setStatus("Security Error");
			this.debug("Error Code: Security Error, File name: " + file.name + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			progress.setStatus("Upload limit exceeded.");
			this.debug("Error Code: Upload Limit Exceeded, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
			progress.setStatus("Failed Validation.  Upload skipped.");
			this.debug("Error Code: File Validation Failed, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
			// If there aren't any files left (they were all cancelled) disable the cancel button
			if (this.getStats().files_queued === 0) {
				document.getElementById(this.customSettings.cancelButtonId).disabled = true;
			}
			progress.setStatus("Cancelled");
			progress.setCancelled();
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			progress.setStatus("Stopped");
			break;
		default:
			progress.setStatus("Unhandled Error: " + errorCode);
			this.debug("Error Code: " + errorCode + ", File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		}
	} catch (ex) {
        this.debug(ex);
    }
}

function uploadComplete(file) {
	if (this.getStats().files_queued === 0) {
		document.getElementById(this.customSettings.cancelButtonId).disabled = true;
	}
}

// This event comes from the Queue Plugin
function queueComplete(numFilesUploaded) {
	var status = document.getElementById("divStatus");
	status.innerHTML = numFilesUploaded + " 文件" + (numFilesUploaded === 1 ? "" : "s") + " 上传完成.";
}


function append(id, content, type) {

	// 单选
	if (type == SWFUpload.BUTTON_ACTION.SELECT_FILE) {
		$('#' + id).val(content);
	}

	// 多选
	if (type == SWFUpload.BUTTON_ACTION.SELECT_FILES) {
		var obj = $('#' + id);
		if (obj.val().length > 0) {
			obj.val(obj.val() + ',' + content);
		} else {
			obj.val(content);
		}

	}

}


