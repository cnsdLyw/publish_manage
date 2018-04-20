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
		if (file.name.substring(file.name.lastIndexOf('.') + 1) == 'exe') {
			addMsg(this.customSettings.progressTarget, '对不起,不允许上传exe格式的文件,请重新选择...', true);
			this.cancelUpload();
			return;
		}
		if (this.customSettings.name) {
			// 多选
			var obj = $('#' + this.customSettings.attachName);
			if (this.settings.button_action == SWFUpload.BUTTON_ACTION.SELECT_FILES) {
				if ($.trim(obj.text()).length > 0) {
					obj.text(obj.text() + ',' + file.name);
				} else {
					obj.text(file.name);
				}
			} else {
				obj.text(file.name);
			}
		}
		addMsg(this.customSettings.progressTarget, '准备上传...', false);
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
		this.startUpload();
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadStart(file) {
	try {
		addMsg(this.customSettings.progressTarget, '正在上传...', false);
	} catch (ex) {
	}
	return true;
}

function uploadProgress(file, bytesLoaded, bytesTotal) {
	try {
		var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
		addMsg(this.customSettings.progressTarget, '正在上传...' + percent + '%',
				false);
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadSuccess(file, serverData) {
	try {
		eval("var serverData = " + serverData);
		if (serverData.success == 'true') {
			if (this.customSettings.name) {
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
			addMsg(this.customSettings.progressTarget, '上传完成...');
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadError(file, errorCode, message) {
	try {
		var progress = new FileProgress(file,
				this.customSettings.progressTarget);
		progress.setError();
		progress.toggleCancel(false);

		switch (errorCode) {
			case SWFUpload.UPLOAD_ERROR.HTTP_ERROR :
				progress.setStatus("Upload Error: " + message);
				this.debug("Error Code: HTTP Error, File name: " + file.name
						+ ", Message: " + message);
				break;
			case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED :
				progress.setStatus("Upload Failed.");
				this
						.debug("Error Code: Upload Failed, File name: "
								+ file.name + ", File size: " + file.size
								+ ", Message: " + message);
				break;
			case SWFUpload.UPLOAD_ERROR.IO_ERROR :
				progress.setStatus("Server (IO) Error");
				this.debug("Error Code: IO Error, File name: " + file.name
						+ ", Message: " + message);
				break;
			case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR :
				progress.setStatus("Security Error");
				this.debug("Error Code: Security Error, File name: "
						+ file.name + ", Message: " + message);
				break;
			case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED :
				progress.setStatus("Upload limit exceeded.");
				this.debug("Error Code: Upload Limit Exceeded, File name: "
						+ file.name + ", File size: " + file.size
						+ ", Message: " + message);
				break;
			case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED :
				progress.setStatus("Failed Validation.  Upload skipped.");
				this.debug("Error Code: File Validation Failed, File name: "
						+ file.name + ", File size: " + file.size
						+ ", Message: " + message);
				break;
			case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED :
				// If there aren't any files left (they were all cancelled)
				// disable the cancel button
				if (this.getStats().files_queued === 0) {
					document.getElementById(this.customSettings.cancelButtonId).disabled = true;
				}
				progress.setStatus("Cancelled");
				progress.setCancelled();
				break;
			case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED :
				progress.setStatus("Stopped");
				break;
			default :
				progress.setStatus("Unhandled Error: " + errorCode);
				this.debug("Error Code: " + errorCode + ", File name: "
						+ file.name + ", File size: " + file.size
						+ ", Message: " + message);
				break;
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadComplete(file) {
	if (this.getStats().files_queued > 0) {
		this.startUpload();
	}
}

function queueComplete(numFilesUploaded) {
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