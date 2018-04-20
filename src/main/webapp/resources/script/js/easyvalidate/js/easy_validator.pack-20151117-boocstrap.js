/*
	Copyright (C) 2009 - 2012
	WebSite:	Http://wangking717.javaeye.com/
	Author:		wangking
	as easyTip not after input or select 
	$(obj).next("span[name='easyTip']")
	$(this).nextAll("[name='easyTip']")
	repalce
	$(obj).parent().next("span[name='easyTip']")
	$(this).parent().nextAll("[name='easyTip']")

*/
$(function(){
	var xOffset = -20; // x distance from mouse
    var yOffset = 20; // y distance from mouse  
	
	
	
	//zhongying(2014-01-add)-start
	$("[name='easyTip']").each(function() {
		$(this).addClass("onShowss");
		$(this).html('&nbsp;');
	});
	
	$("[reg]").click(function() {
		$(this).parent().nextAll("[name='easyTip']").eq(0).removeClass();
		$(this).parent().nextAll("[name='easyTip']").eq(0).addClass("onFocusss");
	});
	
	$("[rel]").click(function() {
		$(this).parent().nextAll("[name='easyTip']").eq(0).removeClass();
		$(this).parent().nextAll("[name='easyTip']").eq(0).addClass("onFocusss");
	});
	//zhongying(2014-01-add)-end
	
	
	$("[tip]").hover(
			function(e) {
				if($(this).attr('tip') != undefined){
//					var top = (e.pageY + yOffset);
//					var left = (e.pageX + xOffset);
//					$('body').append( '<p id="vtip"><img id="vtipArrow" src="/easyvalidate/img/vtip_arrow.png"/>' + $(this).attr('tip') + '</p>' );
//					$('p#vtip').css("top", top+"px").css("left", left+"px");
//					$('p#vtip').bgiframe();
				}
			},
			function() {
				if($(this).attr('tip') != undefined){
//					$("p#vtip").remove();
				}
			}
		).mousemove(
			function(e) {
				if($(this).attr('tip') != undefined){
//					var top = (e.pageY + yOffset);
//					var left = (e.pageX + xOffset);
//					$("p#vtip").css("top", top+"px").css("left", left+"px");
				}
			}
		).blur(function(){
			
			
		});
	
	
	//input action
	$("[reg],[url]:not([reg]),[tip]").blur(function(){
		//alert("blur"); 
		if($(this).attr("reg") != undefined){
			//alert("blur==reg");
			validate($(this));
		}
		
		if($(this).attr("rel") != undefined){
			//alert("blur==rel");
			var rel=$(this).attr("rel");
			eval(rel); 
		}
		
		
//		if($(this).attr("url") != undefined){
//			//执行reg验证后，然后执行自定义验证方法//zhongying(2014-02-add)-start
//			if($(this).attr("rel") != undefined){
//				var rel=$(this).attr("rel");
//				eval(rel); 
//				
//			}
//			
//			//alert("blur-1");
//			ajax_validate($(this));
//		}else if($(this).attr("reg") != undefined){
//			//alert("blur-2");
//			validate($(this));
//		}
		
	});
	
	
	$("form").submit(function(){
		
		var isSubmit = true;
		//解决jquery.js不支持submit提交和jquery.1.8.2.min.js可以正常提交问题
		//$(this).find("[reg],[rel],[url]:not([reg])").each(function(){//提交表单时候，验证每个reg
		$("form").find("input[reg],input[rel],select[reg],select[rel],textarea[reg],textarea[rel]").each(function(){//提交表单时候，验证每个reg
//			alert($(this).attr("name"));
			
			if($(this).attr("rel") != undefined){
//				alert("reg==rel");
				if(!validateRel($(this))){
//					alert("!validateRel");
					
					isSubmit = false;
				}
			}else if($(this).attr("reg") != undefined){
//				alert("reg==reg");
				if(!validate($(this))){
//					alert("!validate");
					isSubmit = false;
				}
			}else{
//				alert("else");
//				validateRel();
//				//执行reg验证后，然后执行自定义验证方法//zhongying(2014-02-add)-start
//				if($(this).attr("rel") != undefined){
//					alert("123");
//					var rel=$(this).attr("rel");
//					eval(rel); 
//					isSubmit = false;
//				}
			}
		});
		
//		alert("isSubmit=="+isSubmit);
		/***/
		if(typeof(isExtendsValidate) != "undefined"){////如果要试用扩展表单验证的话，该属性一定要申明
   			if(isSubmit && isExtendsValidate){//isSubmit==true,如果reg验证通过才验证extendsValidate();
//   				alert("isExtendsValidate==true");
				return extendsValidate();
			}else{
				
			}
   		}
   		
		
		return isSubmit;
	});
	
});


/**
 * 当表单提交时候，验证reg(blur()方法也会调用)
 */
function validate(obj){
//	alert("validate()调用了 ");
	//var reg = new RegExp(obj.attr("reg"));
	//var objValue = obj.attr("value");
	var regStr=$(obj).attr("reg");
	var reg = new RegExp(regStr);
	var objValue =$(obj).val();
	
//	alert("reg== "+reg+",objValue=="+objValue);
	
	
	tipObj = $(obj).parent().nextAll("[name='easyTip']").eq(0);//zhongying(2014-01-add)-start
	tipObj.removeClass();//zhongying(2014-01-add)-start
	
	
	if(!reg.test(objValue)){
		tipObj.addClass("onErrorss");//zhongying(2014-01-add)-start
		if (objValue == "") {//zhongying(2014-01-add)-start
			tipObj.html($(obj).attr('emp_txt'));//zhongying(2014-01-add)-start
		} else {
			tipObj.html($(obj).attr('err_txt'));//zhongying(2014-01-add)-start
		}
		
		change_error_style($(obj),"add");
		change_tip($(obj),null,"remove");
		
		
		//reg也给于tip提示信息
		var ctip=$(obj).attr("tip");
		$(obj).validate_callback(ctip,"failed");
		return false;
	}else{
		if($(obj).attr("url") == undefined){
			change_error_style($(obj),"remove");
			change_tip($(obj),null,"remove");
			return true;
		}else{
			return ajax_validate($(obj));
		}
	}
}


/**
 * 当表单提交时候，验证rel
 */
function validateRel(obj){
	var rel=$(obj).attr("rel");
	
	//rel不能写为validate(this)，否则识别不了
	var aa=eval(rel); 
	
//	alert("aa=="+aa);
	return aa;
//	alert($(obj).attr("id"));
//	var rel=$(this).attr("rel");
//	alert("rel=="+rel);
//	var aa=eval(rel); 
//	alert("aa=="+aa);
}



function ajax_validate(obj){
	
	var url_str = obj.attr("url");
	if(url_str.indexOf("?") != -1){
		url_str = url_str+"&"+obj.attr("name")+"="+obj.attr("value");
	}else{
		url_str = url_str+"?"+obj.attr("name")+"="+obj.attr("value");
	}
	var feed_back = $.ajax({url: url_str,cache: false,async: false}).responseText;
	feed_back = feed_back.replace(/(^\s*)|(\s*$)/g, "");
	if(feed_back == 'success'){
		change_error_style(obj,"remove");
		change_tip(obj,null,"remove");
		return true;
	}else{
		change_error_style(obj,"add");
		change_tip(obj,feed_back,"add");
		return false;
	}
}

function change_tip(obj,msg,action_type){
	
	if(obj.attr("tip") == undefined){//初始化判断TIP是否为空
		//去除在文本库下面出现tip错误提示$('#loginName').validate_callback("对不起您的账号含有敏感字请重新输入","failed");
		//obj.attr("is_tip_null","yes");
	}
	if(action_type == "add"){
		$(obj).parent().next("span[name='easyTip']").html(msg);//zhongying(2014-01-add)-start
		
		//去除在文本库下面出现tip错误提示$('#loginName').validate_callback("对不起您的账号含有敏感字请重新输入","failed");
		/**
		if(obj.attr("is_tip_null") == "yes"){
			obj.attr("tip",msg);
		}else{
			if(msg != null){
				if(obj.attr("tip_bak") == undefined){
					obj.attr("tip_bak",obj.attr("tip"));
				}
				obj.attr("tip",msg);
			}
		}
		*/
		
	}else{
		$(obj).parent().next("span[name='easyTip']").html("&nbsp");//zhongying(2014-01-add)-start
		
		/**
		if(obj.attr("is_tip_null") == "yes"){
			obj.removeAttr("tip");
			obj.removeAttr("tip_bak");
		}else{
			obj.attr("tip",obj.attr("tip_bak"));
			obj.removeAttr("tip_bak");
		}
		 */
		
	}
}

function change_error_style(obj,action_type){
	//alert("action_type=="+action_type);
	if(action_type == "add"){
		$(obj).addClass("input_validation-failed");
		//tipObj.addClass("onErrorss");//zhongying(2014-01-add)-start
		
		$(obj).parent().next("span[name='easyTip']").removeClass();
		$(obj).parent().next("span[name='easyTip']").addClass("onErrorss");//zhongying(2014-02-add)-start
	}else{
		$(obj).removeClass("input_validation-failed");
		
		$(obj).parent().next("span[name='easyTip']").removeClass();
		$(obj).parent().next("span[name='easyTip']").addClass("onCorrectss");//zhongying(2014-01-add)-start
		//tipObj.html("&nbsp");//zhongying(2014-01-add)-start
		$(obj).parent().next("span[name='easyTip']").html("");//zhongying(2014-01-add)-start
	}
}

$.fn.validate_callback = function(msg,action_type,options){
	this.each(function(){
		if(action_type == "failed"){
			//alert("validate_callback===failed");
			change_error_style($(this),"add");
			change_tip($(this),msg,"add");
		}else{
			change_error_style($(this),"remove");
			change_tip($(this),null,"remove");
		}
	});
};




