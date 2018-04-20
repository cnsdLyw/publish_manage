			
		$.extend(jQuery.validator.messages, {
        	required: "必填字段",
		 	remote: "请修正该字段",
		 	email: "请输入正确格式的电子邮件",
		 	url: "请输入合法的网址",
		 	date: "请输入合法的日期",
		 	dateISO: "请输入合法的日期 (ISO).",
		 	number: "请输入合法的数字",
		 	digits: "只能输入整数",
		 	creditcard: "请输入合法的信用卡号",
		 	equalTo: "密码不一致",
		 	accept: "请输入拥有合法后缀名的字符串",
		 	maxlength: jQuery.validator.format("请输入一个长度最多是{0}的字符"),
		 	minlength: jQuery.validator.format("请输入一个长度最少是{0}的字符"),
		 	rangelength: jQuery.validator.format("请输入一个长度介于 {0}和 {1}之间的字符"),
		 	range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
		 	max: jQuery.validator.format("请输入一个最大为{0} 的值"),
		 	min: jQuery.validator.format("请输入一个最小为{0} 的值")
		});
		
		//英文字母汉字加数字，不能全部是数字
		$.validator.addMethod("chrnum", function(value, element) {
			var chrEn = /^[A-Za-z0-9/_-]+$/; //	var chrnum = /^[A-Za-z0-9_\u554A-\u9C52]+$/;
			var chrNum = /^\d+$/;//只有数字
			return this.optional(element) || (chrEn.test(value)&&!chrNum.test(value));
			
		}, "只能输入数字和字母和下划线,且不能全是数字");
		//数字
		$.validator.addMethod("isNum", function(value, element) {
			var chrNum = /^\d+$/;//只有数字
			return this.optional(element) || chrNum.test(value);
			
		}, "只能输入数字");
		
		//数字，包括整数和小数
		$.validator.addMethod("isNumber", function(value, element) {
			var chrNum = /^$|^(0|[1-9]\d*)(\.\d{1,2})?$/;//只有数字
			return this.optional(element) || chrNum.test(value);
			
		}, "只能为数字，最多两位小数");
		
		//英文字母汉字加数字
		$.validator.addMethod("chrnumAndZm", function(value, element) {
			var chrEn = /^[A-Za-z0-9/]+$/; 
			return this.optional(element) || (chrEn.test(value));
			
		}, "只能输入数字和字母");
		//至少包含字母、数字、特殊字符中两种及以上组合
		$.validator.addMethod("chrpwd", function(value, element) {
			 var reg=/((?=.*[a-z])(?=.*\d)|(?=[a-z])(?=.*[#@!~%^&*_-])|(?=.*\d)(?=.*[#@!~%^&*_-]))[a-z\d#@!~%^&*_-]{8,20}/i;
			 return this.optional(element) || (reg.test(value));
			 
		}, "至少包含字母、数字、特殊字符中两种及以上组合,8-20个字符");
		
		// 判断中文字符 
		$.validator.addMethod("isChinese", function(value, element) {       
	         return this.optional(element) || /^[\u0391-\uFFE5]+$/.test(value);       
	    }, "只能包含中文字符"); 
		// 判断中文和英文
		$.validator.addMethod("isChineseOrEn", function(value, element) {       
			return this.optional(element) || /^[a-zA-Z\u4E00-\u9FA5]+$/.test(value);       
		}, "只能包含中文字符和英文字母"); 
		
		//必须以"ROLER_"开头，内容为英文字母汉字加数字，且不能全部是数字
		$.validator.addMethod("role", function(value, element) {
			var chrEn = /^(ROLER_)[A-Za-z0-9/_]+$/; //	var chrnum = /^[A-Za-z0-9_\u554A-\u9C52]+$/;
			var chrNum = /^\d+$/;//只有数字
			return this.optional(element) || (chrEn.test(value)&&!chrNum.test(value));
			
		}, "必须以'ROLER_'开头，只能输入数字和字母和下划线,且不能全是数字");
		
		// 判断中文字符 
		$.validator.addMethod("isChinese", function(value, element) {       
			return this.optional(element) || /^[\u0391-\uFFE5]+$/.test(value);       
		}, "只能包含中文字符"); 
		// 判断征订数量
		$.validator.addMethod("isPreOrderNum", function(value, element) {       
			return this.optional(element) || /^(0|[1-9]\d*)?$/.test(value);       
		}, "请输入数字，数字长度不能超过6位！"); 
		
		// 联系电话(手机/电话皆可)验证   
	    $.validator.addMethod("isPhone", function(value,element) {   
	        var length = value.length;   
	        var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;   
	        var tel = /^(\d{3,4}-?)?\d{7,9}$/g;       
	        var gdtel = /^(86-\d{2,3}-?)?\d{7,9}$/g;       
	        return this.optional(element) || tel.test(value) || (length==11 && mobile.test(value))||(length==14 &&gdtel.test(value));   
	    }, "请正确填写您的联系电话");
	    
	   // 字符验证，只能包含中文、英文、数字、下划线等字符。    
	    $.validator.addMethod("stringCheck", function(value, element) {       
	         return this.optional(element) || /^[a-zA-Z0-9\u4e00-\u9fa5-_]+$/.test(value);       
	    }, "只能包含中文、英文、数字、下划线等字符");   
	    // 字符验证，校验地址，可以包括特殊字符  
	    $.validator.addMethod("addressCheck", function(value, element) { 
	    	var chrNum = /^\d+$/;//只有数字
	    	var chrX = /^_+$/;//只有下划线
	    	var chrH = /^-+$/;//只有横线
	    	var chrT = /^#+$/;//只有#
	    	var chrY = /^()+$/;//只有()
	    	var chrU = /^（）+$/;//只有()
	    	var chrI = /^——+$/;//只有()
	    	var chrNumAndFh = /^[\d|_|()|（）|——|#|-]+$/;
	    	return this.optional(element) || /^[a-zA-Z0-9\u4e00-\u9fa5+[_|()|（）|——|#|-]+$/.test(value)
                &&!chrNum.test(value)&&!chrX.test(value)
                &&!chrH.test(value)&&!chrT.test(value)
                &&!chrY.test(value)&&!chrU.test(value)    
		    	&&!chrI.test(value)&&!chrNumAndFh.test(value);      
	    }, "只能包含中文、英文、数字、特殊符号(_、-、——、()、（）、#)，且不能全是数字和特殊符号");   
	    // 邮政编码验证    
	    $.validator.addMethod("isPostalcode", function(value, element) {    
	      var zip = /^[0-9]{6}$/;    
	      return this.optional(element) || (zip.test(value));    
	    }, "请填写格式正确的邮政编码"); 
	    // 3位数字
	    $.validator.addMethod("is3Num", function(value, element) {    
	    	var zip = /^(|[1-9][0-9][0-9])$/;    
	    	return this.optional(element) || (zip.test(value));    
	    }, "请填写3位数字，第一位必须大于0");
	    // 判断http网址
		$.validator.addMethod("isWebsit", function(value, element) {
			var urlReg =  /[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\.?/;
			return this.optional(element) || urlReg.test(value);       
		}, "请输入正确的网址"); 
		// 判断ftp地址
		$.validator.addMethod("isFtp", function(value, element) {
			var ftpUrl =  /^ftp:\/\//;
			return this.optional(element) || ftpUrl.test(value);       
		}, "请输入ftp://开头的ftp地址"); 