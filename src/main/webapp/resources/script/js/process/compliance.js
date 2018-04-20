	function getTestApplication(id) {
		$("#myModalLabelExt").html("");
		$("#detailContent").html("");
		$("#myModalLabelExt").html('查看测试申请详细信息');
	    $.ajax( {  
	        type : "get",  
	        url : ctxPath+"/testApplication/getJsonTestApplication/?id="+id,  
	        dataType:"json",  
	        success : function(apply) {  
	            	var content="<p>软件名称："+(apply.softName!=null?apply.softName:"")+" </p>"+
	           				"<p>申请机构："+(apply.orgName!=null?apply.orgName:"")+" </p>"+
	           				"<p>软件类型："+(apply.softType!=null?apply.softType:"")+" </p>"+
	           				"<p>是否原创："+(apply.original!=null?apply.original==true?"是":"否":"")+" </p>"+
	           				"<p>开发方式："+(apply.developType!=null?apply.developType:"")+" </p>"+
	           				"<p>是否发表："+(apply.issue!=null?apply.issue==true?"是":"否":"")+" </p>"+
	           				"<p>软件规模："+(apply.softScale!=null?apply.softScale:"")+" </p>"+
	           				"<p>硬件环境："+(apply.hardEnvironment!=null?apply.hardEnvironment:"")+" </p>"+
	           				"<p>软件环境："+(apply.softEnvironment!=null?apply.softEnvironment:"")+" </p>"+
	           				"<p>开发语言："+(apply.developLanguage!=null?apply.developLanguage:"")+" </p>"+
	           				"<p>软件描述："+(apply.softDescription!=null?apply.softDescription:"")+" </p>"+
			            	"<p>接口描述："+(apply.interfaceDescription!=null?apply.interfaceDescription:"")+" </p>";
	            	if(apply.checkDescription){
	            		content+="<p>审核描述："+(apply.checkDescription!=null?apply.checkDescription:"")+" </p>";
	            	}
				    $("#detailContent").html(content);
	        }  
	    });  
	}
	
	function getComplianceTask(id){
		$("#myModalLabelExt").html("");
		$("#detailContent").html("");
		$("#myModalLabelExt").html('查看测试任务详细信息');
	    $.ajax( {  
	        type : "get",  
	        url : ctxPath+"/complianceTestTask/getJsonComplianceTestTask/?id="+id,  
	        dataType:"json",  
	        success : function(applyTask) {  
	            	var content=/*"<p>任务名称3："+(applyTask.taskName!=null?applyTask.taskName:"")+" </p>"+*/
	            			"<p>软件名称："+(applyTask.softName!=null?applyTask.softName:"")+" </p>"+
	           				"<p>申请机构："+(applyTask.orgName!=null?applyTask.orgName:"")+" </p>"+
	           				"<p>软件类型："+(applyTask.softType!=null?applyTask.softType:"")+" </p>"+
	           				"<p>是否原创："+(applyTask.original!=null?applyTask.original==true?"是":"否":"")+" </p>"+
	           				"<p>开发方式："+(applyTask.developType!=null?applyTask.developType:"")+" </p>"+
	           				"<p>是否发表："+(applyTask.issue!=null?applyTask.issue==true?"是":"否":"")+" </p>"+
	           				"<p>软件规模："+(applyTask.softScale!=null?applyTask.softScale:"")+" </p>"+
	           				"<p>硬件环境："+(applyTask.hardEnvironment!=null?applyTask.hardEnvironment:"")+" </p>"+
	           				"<p>软件环境："+(applyTask.softEnvironment!=null?applyTask.softEnvironment:"")+" </p>"+
	           				"<p>开发语言："+(applyTask.developLanguage!=null?applyTask.developLanguage:"")+" </p>"+
	           				"<p>软件描述："+(applyTask.softDescription!=null?applyTask.softDescription:"")+" </p>"+
			            	"<p>接口描述："+(applyTask.interfaceDescription!=null?applyTask.interfaceDescription:"")+" </p>";
	           				/*"<p>创建时间："+FormatDate(applyTask.createTime)+" </p>";*/
				    $("#detailContent").html(content);
	        }  
	    });  
	}
	
	//任务分配
    function allotTask1(){
    	var ids = '';
    	var statuses='';
		$("input[type=checkbox][name=ids]").each(function() {
	        if ($(this).is(":checked")==true) {
	             ids+=$(this).val()+",";
	             var objStatus = $("#statuses"+$(this).val()).val();
	             if(objStatus==1||objStatus==2){
	            	 statuses = '1';
	             }
	        }
		});
		if(ids){
			if(statuses=='1'){
				bootbox.alert("选择的符合性测试任务已分配!", function() {});
			}else{
				//创建图书加工任务
				$("#chooseTaskIds").val(ids);
				window.allotForm.submit();
			}
			
		}else{
			bootbox.alert("还没有选择要分配的符合性测试任务!", function() {});
		}
    }
	
    function allotTask(){
    	var values = '';
		var ids = '';
		var status='';
		$("input[type=checkbox][name=ids]").each(function() {
	        if ($(this).is(":checked")==true) {
	             values+=$(this).val()+",";
	        }
		});
		if(values){
			 values = values.substring(0,values.lastIndexOf(","));
			 var value=values.split(",");
	         for(var i=0;i<value.length;i++){        	
	        	var val = value[i].split("_");
	        	 ids+=val[0]+",";
	        	 status+=val[1]+",";
	         }
		}
		if(ids){
			if(status){
				bootbox.setDefaults("locale","zh_CN"); 
				status = status.substring(0,status.lastIndexOf(","));
				var statu = status.split(",");
				for(var i=0;i<statu.length;i++){
					var Statu = statu[i];
					if(Statu == 1){
						bootbox.alert("选择的符合性测试任务已分配!", function() {});
						return false;
					}
				}
			}
				
			//创建图书加工任务
			$("#chooseTaskIds").val(ids);
			window.allotForm.submit();

			
		}else{
			bootbox.alert("还没有选择要分配的符合性测试任务!", function() {});
		}
    }
    
    function showProcessDetail(id){
   	 $.ajax( {  
	        type : "get",  
	        url : "../complianceTestTask/getJsonComplianceTestTaskFlows/?id="+id,  
	        dataType:"json",  
	        success : function(data) {  
		        var content="";
		        var statusStr="";
		        $.each(data, function (i, item) {
		        	if(item.status==0){
			        	statusStr = "<span class=\"label label-sm label-info arrowed-in\">等待处理 </span>";
			        }else if(item.status==1){
			        	statusStr = "<span class=\"label label-sm label-warning arrowed-in arrowed-right\">正在处理</span>";
			        }else if(item.status==2){
			        	statusStr = "<span class=\"label label-sm label-success\">处理完成</span>";
			        } 
		        	//content+="<tr role=\"row\" class=\"odd\"><td>"+item.nodeName+"</td><td>"+item.step+"</td><td>"+(item.operatorName!=null?item.operatorName:"管理员")+"</td><td>"+statusStr+"</td><td>"+FormatDate(item.beginTime)+"</td><td>"+FormatDate(item.endTime)+"</td><td>"+(item.remark!=null?item.remark:"")+"</td></tr>";
		        	//content+="<tr role=\"row\" class=\"odd\"><td>"+item.nodeName+"</td><td>"+item.step+"</td><td>"+(item.operatorName!=null?item.operatorName:"管理员")+"</td><td>"+statusStr+"</td><td>"+(item.remark!=null?item.remark:"")+"</td></tr>";
		        	content+="<tr role=\"row\" class=\"odd\"><td>"+item.nodeName+"</td><td>"+item.step+"</td><td>"+(item.operatorName!=null?item.operatorName:"管理员")+"</td><td>"+statusStr+"</td></tr>";
		        });
		        
		        if(content==""){
		        	content = "<tr role=\"row\" class=\"odd\"><td colspan=\"7\">测试任务暂未分配</td><</tr>";
		        }
		        
		        $("#processdetailContent").html(content);
		         
	        }  
	    });  
   }
    
  //任务提交
    function submitTaskFlow(){
    	var ids = '';
    	var statuses='';
    	var flag = true;
		$("input[type=checkbox][name=ids]").each(function() {
	        if ($(this).is(":checked")==true) {
	        	 var flowStatus = $("#statusStr"+$(this).val()).val();
	        	 if(flowStatus==0){
	        		 flag = false;
	        	 }
	             ids+=$(this).val()+",";
	             var objStatus = $("#statuses"+$(this).val()).val();
	             if(objStatus==0||objStatus==1){
	            	 statuses = '1';
	             }
	        }
		});
		
		
		if(ids){
			if(!flag){
				bootbox.alert("只能提交节点是测试过程的任务!", function() {});
			}else{
				window.allotForm.action="../complianceTestTask/submitTaskFlow";
				$("#chooseFlowIds").val(ids);
				window.allotForm.submit();
			}
			
		}else{
			bootbox.alert("还没有选择要提交的测试任务!", function() {});
		}
    }
    
  //任务审核
    function auditTaskFlow(){
    	var ids = '';
    	var statuses='';
    	var flag = true;
		$("input[type=checkbox][name=ids]").each(function() {
	        if ($(this).is(":checked")==true) {
	        	 var flowStatus = $("#statusStr"+$(this).val()).val();
	        	 if(flowStatus!=0){
	        		 flag = false;
	        	 }
	             ids+=$(this).val()+",";
	             var objStatus = $("#statuses"+$(this).val()).val();
	             if(objStatus==0||objStatus==1){
	            	 statuses = '1';
	             }
	        }
		});
		
		
		if(ids){
			if(!flag){
				bootbox.alert("只能审核节点是审核的任务!", function() {});
			}else{
				window.allotForm.action="../complianceTestTask/goAuditTaskFlow";
				$("#chooseFlowIds").val(ids);
				window.allotForm.submit();
			}
			
			/*window.allotForm.action="../processCenter/goAuditTaskFlow";
			$("#chooseFlowIds").val(ids);
			window.allotForm.submit();*/
		}else{
			bootbox.alert("还没有选择要审核的测试任务!", function() {});
		}
    }
    
    /*选择测试用例*/
    function setTestCases(){
    	var ids = '';
		$("input[type=checkbox][name=ids]").each(function() {
	        if ($(this).is(":checked")==true) {
	             ids+=$(this).val()+",";
	        }
		});
		
		if(ids){
        	ids = ids.substring(0,ids.lastIndexOf(","));
        	window.setForm.action="../complianceTestTask/toSetTestCases";
        	$("#chooseTaskIds").val(ids);
			window.setForm.submit();
              
		}else{
			bootbox.alert("您还没有选择测试任务!", function() {});
		}
    }
    
    function getTestCases(taskId){
    	
    	$.ajax( {  
	        type : "get",  
	        url : "../complianceTestTask/getJsonComplianceTestTaskCases/?id="+taskId,  
	        dataType:"json",  
	        success : function(data) {  
		        var content="";
		        var statusStr="";
		        $.each(data, function (i, item) {
		        	//content+="<tr role=\"row\" class=\"odd\"><td>"+item.nodeName+"</td><td>"+item.step+"</td><td>"+(item.operatorName!=null?item.operatorName:"管理员")+"</td><td>"+statusStr+"</td><td>"+FormatDate(item.beginTime)+"</td><td>"+FormatDate(item.endTime)+"</td><td>"+(item.remark!=null?item.remark:"")+"</td></tr>";
		        	//content+="<tr role=\"row\" class=\"odd\"><td>"+item.nodeName+"</td><td>"+item.step+"</td><td>"+(item.operatorName!=null?item.operatorName:"管理员")+"</td><td>"+statusStr+"</td><td>"+(item.remark!=null?item.remark:"")+"</td></tr>";
		        	content+="<tr role=\"row\" class=\"odd\"><td>"+item.id+"</td><td>"+item.fileName+"</td><td>"+item.fileSize+"</td></tr>";
		        	
		        });
		        
		        if(content==""){
		        	content = "<tr role=\"row\" class=\"odd\"><td colspan=\"7\">暂未选择测试用例</td><</tr>";
		        }
		        
		        $("#taskCasesContent").html(content);
		         
	        }  
	    }); 
    }
    
    /*上传软件符合性测试报告,多个任务上传*/
    function uploadMoreComplianceTestReport(){
    	var ids = '';
    	var isUpload = true;
		$("input[type=checkbox][name=ids]").each(function() {
	        if ($(this).is(":checked")==true) {
	        	 $.ajax( {  
	     	        type : "get",  
	     	        url : ctxPath+"/complianceTestTask/getComplianceTestReportPath/?id="+$("#applicationId"+$(this).val()).val(),  
	     	        dataType:"text",
	     	        async:false,
	     	        success : function(data) {
	     	        	if(data=="1"){
	     	        		isUpload = false;
	     	        	}
	     	        }  
	     	    });  
	            ids+=$(this).val()+",";
	        }
		});
		
		if(ids){
			if(isUpload){
				ids = ids.substring(0,ids.lastIndexOf(","));
	        	window.setForm.action="../complianceTestTask/uploadReport";
	        	$("#chooseTaskIds").val(ids);
				window.setForm.submit();
			}else{
				bootbox.setDefaults("locale","zh_CN");
				bootbox.confirm("有测试任务已经存在符合性测试报告,是否提交!", function (result) {  
	                if(result) {//确认删除  
	                	window.setForm.action="../complianceTestTask/uploadReport";
	    	        	$("#chooseTaskIds").val(ids);
	    				window.setForm.submit();
	                } 
	         	});  
			}
              
		}else{
			bootbox.alert("您还没有选择测试任务!", function() {});
		}
    }
    
    /*上传软件符合性测试报告，单条任务上传*/
    function uploadComplianceTestReport(taskId,applicationId){
    	var isUpload = true;
    	
    	 $.ajax( {  
 	        type : "get",  
 	        url : ctxPath+"/complianceTestTask/getComplianceTestReportPath/?id="+applicationId,  
 	        dataType:"text",
 	        async:false,
 	        success : function(data) {
 	        	if(data=="1"){
 	        		isUpload = false;
 	        	}
 	        }  
 	    });  
		
		if(isUpload){
        	window.setForm.action="../complianceTestTask/uploadReport";
        	$("#chooseTaskIds").val(taskId);
			window.setForm.submit();
		}else{
			bootbox.setDefaults("locale","zh_CN");
			bootbox.confirm("有测试任务已经存在符合性测试报告,是否提交!", function (result) {  
                if(result) {//确认删除  
                	window.setForm.action="../complianceTestTask/uploadReport";
    	        	$("#chooseTaskIds").val(taskId);
    				window.setForm.submit();
                } 
         	});  
		}
    }
    
    function getComplianceTestReportPath(applicationId){
	    $.ajax( {  
	        type : "get",  
	        url : ctxPath+"/complianceTestTask/getComplianceTestReportPath/?id="+applicationId,  
	        dataType:"text",  
	        success : function(data) {
	        	if(data=="-1"){
	        		bootbox.alert("找不到符合性测试报告文件!", function() {});
	        	}else if(data=="0"){
	        		bootbox.alert("还没有生成符合性测试报告!", function() {});
	        	}else if(data=="1"){
	        		$("#getReport"+applicationId).click();
	        	}
	        }  
	    });  
    }
    
	function FormatDate (strTime) {
		if(strTime){
			var date = new Date(strTime);
			return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
		    //return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
		}
	    return '';
	}