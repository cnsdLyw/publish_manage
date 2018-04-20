
Date.prototype.format = function(format) {
   var date = {
          "M+": this.getMonth() + 1,
          "d+": this.getDate(),
          "h+": this.getHours(),
          "m+": this.getMinutes(),
          "s+": this.getSeconds(),
          "q+": Math.floor((this.getMonth() + 3) / 3),
          "S+": this.getMilliseconds()
   };
   if (/(y+)/i.test(format)) {
          format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
   }
   for (var k in date) {
          if (new RegExp("(" + k + ")").test(format)) {
                 format = format.replace(RegExp.$1, RegExp.$1.length == 1
                        ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
          }
   }
   return format;
};

var temp;

function sort(obj,currentForm,queryOrderBy, queryOrderType) {
	if ($("#queryOrderBy").val() == queryOrderBy) {
		if ($("#queryOrderType").val() == "") {
			$("#queryOrderType").val(queryOrderType);
			//$(obj).attr("class","sorting_"+queryOrderType);
		}
		else if ($("#queryOrderType").val() == "desc") {
			$("#queryOrderType").val("asc");
			//$(obj).attr("class","sorting_asc");
		}
		else if ($("#queryOrderType").val() == "asc") {
			$("#queryOrderType").val("desc");
			//$(obj).attr("class","sorting_desc");
		}
	}
	else {
		$("#queryOrderBy").val(queryOrderBy);
		$("#queryOrderType").val(queryOrderType);
	}

	$("#"+currentForm).submit();
}



/**
 * 判断checkbox是否有选中
 * 只要有被选中即返回true,不管选中多少个
 * 也可以用户radio的校验
 * @param checkIdObj checkbox对象
 */
function isBoxChecked(checkIdObj)
{
	//对象是否为空
	if(checkIdObj == null)
	{
		return false;
	}
	//checkbox列表对象有多个
	if(checkIdObj.length)
	{
		for(i = 0;i < checkIdObj.length;i++)
		{
			//checkbox有选中项
			if(checkIdObj[i].checked  &&  checkIdObj[i].disabled == false)
			{
				return true;
			}
		}
	}
	//checkbox列表对象只有一个
	else if(checkIdObj.checked  &&  checkIdObj.disabled == false)
	{
		return true;
	}	
}

/**
 * 判断checkbox是否有选中且只能选中一个
 * 该功能可以被isRadioChecked代替
 * 若是被选中多个则也返回false
 * @param checkIdObj checkbox对象
 */
function isBoxCheckedForOne(checkIdObj)
{
	var nNum = 0;
	//对象是否为空
	if(checkIdObj == null)
	{
		return false;
	}
	//checkbox列表长度多
	if(checkIdObj.length)
	{
		for(i = 0;i < checkIdObj.length;i++)
		{
			//有被选中的项
			if(checkIdObj[i].checked  &&  checkIdObj[i].disabled == false)
			{
				temp=checkIdObj[i].value;
				nNum = nNum + 1;
			}
		}
		//只有一个被选中
		if(nNum==1)
		{
			return true;
		}
		//多个被选中
		if(nNum>1)
		{
			return false;
		}
	}
	//checkbox列表长度为1且被选中
	else if(checkIdObj.checked  &&  checkIdObj.disabled == false)
	{
		temp=checkIdObj.value;
		return true;
	}	
}
 function isBoxCheckedAll(v,formObj)
{
	  	var f = document.forms[formObj];
		  for (i=0;i<f.elements.length;i++)
		   if (f.elements[i].name=="objectid") f.elements[i].checked = v;
		  document.forms[formObj].elements["clickall"].checked = v;
	
}
 

 
 
