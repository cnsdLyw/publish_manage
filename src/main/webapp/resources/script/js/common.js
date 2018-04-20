
function path() {
	var curWwwPath = window.document.location.href;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos);
	var pathName = window.document.location.pathname;
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
}

function projectName() {
	var pathName = window.document.location.pathname;
	var projectName = pathName.substring(pathName.substr(1).indexOf('/') + 2);
	return projectName;
}

/**
 * 
 * @param url
 * @param type
 * @param datatype
 * @param param
 * @param callback
 */
function loadContent(url, type, datatype, param,callback) {
	jQuery.ajax({
        type: type,
        url: url,
        data: param,
        dataType: datatype,
        async: false,
        success: callback,
		error:function(XMLHttpResponse){
			alert("系统繁忙，请稍后再试！");
			return false;
		}
    });
}

function formatDate(longTime) {
	var time = parseFloat(longTime);
	if (time != null && time != "") {
		time = Digit.round(time, 2)
		if (time < 60) {
			var s = time;
			time = "00:" + s;
		} else if (time > 60 && time < 3600) {
			var m = parseInt(time / 60);
			var s = parseInt(time % 60);
			time = m + ":" + s;
		} else if (time >= 3600 && time < 86400) {
			var h = parseInt(time / 3600);
			var m = parseInt(time % 3600 / 60);
			var s = parseInt(time % 3600 % 60 % 60);
			time = h + ":" + m + ":" + s;
		}
	}
	return time;
}

var Digit = {};
Digit.round = function(digit, length) {
	length = length ? parseInt(length) : 0;
	if (length <= 0)
		return Math.round(digit);
	digit = Math.round(digit * Math.pow(10, length))
			/ Math.pow(10, length);
	return digit;
};
var resList="";




/**
 * 获取checkbox选中的值
 */
function getcheckboxVal(name){
	var resVal="";
	var checkVal=document.getElementsByName(name)
	for(var i = 0 ; i < checkVal.length ; i++ ){
		if(checkVal[i].checked == true){
			resVal+=checkVal[i].value+",";
		  }
	}
	if(resVal.length>0){
		resVal=resVal.substr(0,resVal.length-1)
	}
	return resVal;
}









