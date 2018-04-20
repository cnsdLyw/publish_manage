//交换节点   
	function getProcessTask(id) {
	    $.ajax( {  
	        type : "get",  
	        url : ctxPath+"/processTask/getJsonProcessTask/?id="+id,  
	        dataType:"json",  
	        success : function(processTask) {  
	           	var content="<p>任务名称："+processTask.taskName+" </p>"+
				           	//"<p>图书UUID："+processTask.bookUUID+" </p>"+
				           	"<p>图书ISBN："+processTask.bookISBN+" </p>"+
				           	"<p>图书标题："+processTask.bookTitle+" </p>"+
				           	//"<p>创建机构代码："+processTask.creatorOrgCode+" </p>"+
				           	"<p>创建机构名称："+processTask.creatorOrgName+" </p>"+
				           	//"<p>加工机构代码："+processTask.processorOrgCode+" </p>"+
				           	"<p>加工机构名称："+processTask.processorOrgName+" </p>"+
				           	//"<p>任务开始时间："+(processTask.beginTime!=null?FormatDate(processTask.beginTime):'')+" </p>"+
				            //"<p>任务结束时间："+(processTask.endTime!=null?FormatDate(processTask.endTime):'')+" </p>"+
							           	"<p>创建时间：" + FormatDate(processTask.createTime)
							+ " </p>";
					if (processTask.status == 0) {
						content += "<p>状态：<span class=\"label label-sm label-warning arrowed-in arrowed-right\">正在加工</span> </p>";
					} else if (processTask.status == 1) {
						content += "<p>状态：<span class=\"label label-sm label-success\">完成加工</span> </p>";
					}
				           	
			    content+="<p>加工说明："+(processTask.processExplain==null?"":processTask.processExplain)+"</p>";
			    $("#detailContent").html(content);
	        }  
	    });  
	}
	
	function getBook(id) {
	    $.ajax({  
	        type : "get",
	        url : "../statistics/getJsonBook/?id="+id,
	        dataType:"json",
		    success : function(book) {
		    	var content="<p>图书名称："+book.title+" </p>"+
		    				"<p>副书名："+(book.subtitle==null?"":book.subtitle)+" </p>"+
		    				"<p>ISBN："+(book.isbn==null?"":book.isbn)+"</p>"+
		    				"<p>版次："+(book.version==null?"":book.version)+" </p>"+
		    				"<p>关键词："+(book.keywords==null?"":book.keywords)+" </p>"+
		    				"<p>作者："+(book.author==null?"":book.author)+" </p>"+
		    				"<p>出版日期："+FormatDate(book.publish_date)+" </p>"+
		    				//"<p>正文语种："+(book.language==null?"":book.language)+" </p>"+
		    				"<p>页数："+(book.pages==null?"":book.pages)+" </p>"+
		    				"<p>字数："+(book.words==null?"":book.words)+" </p>";
				$("#detailContent").html(content);
		 	 }  
	    });  
	}
    
	function createTask(){
		var ids = '';
		var flag = false;
		$("input[type=checkbox][name=ids]").each(function() {
	        if ($(this).is(":checked")==true) {
	        	$.ajax({//验证是否有图书加工任务
				      type : "get",  
				      url : "../processTask/hasProcessTask/?bookUUID="+$(this).val(),  
				      dataType:"json",  
				      async: false,
				      success : function(isExist) {
				      	if(isExist){
				      		flag = true;
				      	}
				      }  
				  });
	        	
	        	ids+=$(this).val()+",";
	        }
		});
		
		if(flag){
			bootbox.alert("选择的图书有正加工的图书加工任务!", function() {});
		}else{
			if(ids){
				$("#chooseBookIds").val(ids);
				window.creatForm.submit();
			}else{
				bootbox.alert("还没有选择要加工的图书!", function() {});
			}
		}
	}
	
	
	
//交换中心
	function getCenterProcessTask(id) {
		//ie浏览器兼容，相同请求参数，只请求后台第一次，加时间戳
		$("#myModalLabel").html('查看加工任务详细信息');
	    $.ajax( {  
	        type : "get",  
	        url : "../processTask/getJsonProcessTask/?id="+id+"&date ="+new Date(),  
	        dataType:"json",  
	        success : function(processTask) {  
	           	var content="<p>任务名称："+processTask.taskName+" </p>"+
				           	//"<p>图书UUID："+processTask.bookUUID+" </p>"+
				           	"<p>图书ISBN："+processTask.bookISBN+" </p>"+
				           	"<p>图书标题："+processTask.bookTitle+" </p>"+
				           //"<p>创建机构代码："+processTask.creatorOrgCode+" </p>"+
				           	"<p>创建机构名称："+processTask.creatorOrgName+" </p>"+
				           	//"<p>加工机构代码："+processTask.processorOrgCode+" </p>"+
				           	"<p>加工机构名称："+processTask.processorOrgName+" </p>"+
				            //"<p>任务开始时间："+(processTask.beginTime!=null?FormatDate(processTask.beginTime):'')+" </p>"+
				            //"<p>任务结束时间："+(processTask.endTime!=null?FormatDate(processTask.endTime):'')+" </p>"+
				           	"<p>创建时间："+FormatDate(processTask.createTime)+" </p>";
				if(processTask.status==0){
					content+="<p>状态：<span class=\"label label-sm label-info arrowed-in\">等待分配</span> </p>";
				}else if(processTask.status==1){
					content+="<p>状态：<span class=\"label label-sm label-warning arrowed-in arrowed-right\">正在加工</span> </p>";
				}else if(processTask.status==2){
					content+="<p>状态：<span class=\"label label-sm label-success\">完成加工</span> </p>";
				}
				
				if(processTask.processExplain){
					 content+="<p>加工说明："+(processTask.processExplain==null?"":processTask.processExplain)+"</p>";
				} 
				if(processTask.remark){
					 content+="<p>审核意见："+(processTask.remark==null?"":processTask.remark)+"</p>";
				}
				
			    $("#detailContent").html(content);
			    //$("#bookDetailFrame").attr("src","../processCenter/detail?id="+processTask.bookUUID+"&type=3");
	        }  
	    });  
	}
	
    function showProcessDetail(id){
    	var processExplain = "";
    	var remark = "";
    	 $.ajax( {  
	        type : "get",  
	        url : "../processCenter/getJsonProcessTaskFlows/?id="+id+"&date ="+new Date(),  
	        dataType:"json",  
	        success : function(data) {  
		        var content="";
		        var statusStr="";
		        $.each(data, function (i, item) {
		        	if(i==0){
		        		if(item.processTask.processExplain!=null){
		        			processExplain = item.processTask.processExplain;
		        		}
		        		if(item.processTask.remark!=null){
		        			remark = item.processTask.remark;
		        		}
		        	}
		        	if(item.status==0){
			        	statusStr = "<span class=\"label label-sm label-info arrowed-in\">等待处理 </span>";
			        }else if(item.status==1){
			        	statusStr = "<span class=\"label label-sm label-warning arrowed-in arrowed-right\">正在处理</span>";
			        }else if(item.status==2){
			        	statusStr = "<span class=\"label label-sm label-success\">处理完成</span>";
			        } 
		        	//content+="<tr role=\"row\" class=\"odd\"><td>"+item.nodeName+"</td><td>"+item.step+"</td><td>"+(item.operatorName!=null?item.operatorName:"管理员")+"</td><td>"+statusStr+"</td><td>"+FormatDate(item.beginTime)+"</td><td>"+FormatDate(item.endTime)+"</td><td>"+(item.remark!=null?item.remark:"")+"</td><</tr>";
		        	//content+="<tr role=\"row\" class=\"odd\"><td>"+item.nodeName+"</td><td>"+item.step+"</td><td>"+(item.operatorName!=null?item.operatorName:"管理员")+"</td><td>"+statusStr+"</td><td>"+(item.remark!=null?item.remark:"")+"</td><</tr>";
		        	content+="<tr role=\"row\" class=\"odd\"><td>"+item.nodeName+"</td><td>"+item.step+"</td><td>"+(item.operatorName!=null?item.operatorName:"管理员")+"</td><td>"+statusStr+"</td></tr>";
		        });
		        
		        if(content==""){
		        	content = "<tr role=\"row\" class=\"odd\"><td colspan=\"7\">加工任务暂未分配</td><</tr>";
		        }
		        
		        $("#processdetailContent").html(content);
		        var textPContent = "";
		        if(processExplain){
		        	textPContent = "<BR/>加工说明："+processExplain;
		        }
		        if(remark){
	        	    textPContent+="<BR/>审核意见："+remark;
		        }
		     
		        $("#textP").html(textPContent);
		         
	        }  
	    });  
    }
    function toProcessing(taskId,taskFlowId){
    	$("#taskId").val(taskId);
    	$("#taskFlowId").val(taskFlowId);
		window.processForm.submit();
    }
    
    //加工任务分配
    function allotTask(){
    	/*var ids = '';
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
				bootbox.alert("选择的图书加工任务已分配!", function() {});
			}else{
				//创建图书加工任务
				$("#chooseTaskIds").val(ids);
				window.allotForm.submit();
			}
			
		}else{
			bootbox.alert("还没有选择要分配的图书加工任务!", function() {});
		}*/
		//----------------------------------------------------------------------
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
					if(Statu == "1"||Statu == "2"){
						bootbox.alert("选择的图书加工任务已分配", function() {});
						return false;
					}
				}
			}
	
    	//创建图书加工任务
		$("#chooseTaskIds").val(ids);
		window.allotForm.submit();
  
		}else{
			bootbox.alert("还没有选择要分配的图书加工任务!", function() {});
		}
		
    }
    
    //加工任务提交
    function submitTask(){
    	var ids = '';
    	var statuses='';
		$("input[type=checkbox][name=ids]").each(function() {
	        if ($(this).is(":checked")==true) {
	             ids+=$(this).val()+",";
	             var objStatus = $("#statuses"+$(this).val()).val();
	             if(objStatus==0||objStatus==1){
	            	 statuses = '1';
	             }
	        }
		});
		if(ids){
			if(statuses=='1'){
				bootbox.alert("选择的图书加工任务还未完成!", function() {});
			}else{
				//创建图书加工任务
				window.allotForm.action="../processCenter/submitTask";
				$("#chooseTaskIds").val(ids);
				window.allotForm.submit();
			}
			
		}else{
			bootbox.alert("还没有选择要提交的图书加工任务!", function() {});
		}
    }
    
    //加工节点 加工任务提交
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
				bootbox.alert("只能提交节点是加工过程的图书!", function() {});
			}else{
				window.allotForm.action="../processCenter/submitTaskFlow";
				$("#chooseFlowIds").val(ids);
				window.allotForm.submit();
			}
			
		}else{
			bootbox.alert("还没有选择要提交的图书加工任务!", function() {});
		}
    }
    
  //加工节点 加工任务审核
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
				bootbox.alert("只能审核节点是审核的图书!", function() {});
			}else{
				window.allotForm.action="../processCenter/goAuditTaskFlow";
				$("#chooseFlowIds").val(ids);
				window.allotForm.submit();
			}
			
			/*window.allotForm.action="../processCenter/goAuditTaskFlow";
			$("#chooseFlowIds").val(ids);
			window.allotForm.submit();*/
		}else{
			bootbox.alert("还没有选择要审核的图书加工任务!", function() {});
		}
    }
    
    
    function editCNONIX(id) {
    	/*var id;
		$("input[type=checkbox][name=objectid]").each(function() {
			if ($(this).is(":checked") == true) {
				id = $(this).val();
			}
		});*/
    	//ie浏览器兼容，相同请求参数，只请求后台第一次，加时间戳
		$.ajax({
			type : "get",
			url : ctxPath+"/processCenter/getCNONIXBookXml/?id=" + id+"&date ="+new Date(),
			//url : "../companybook/getCNONIXBookXml/?id=" + id,
			dataType : "json",
			success : function(onixPath) {
				if(onixPath==''){
					bootbox.setDefaults("locale", "zh_CN");
					bootbox.alert("选择的图书还没有生成CNONIX片段!", function() {
					});
				}else{
					window.location.href = "CNONIXEdit:///" + onixPath;
				}
				
			}
		});

	}
    
    
    function getBookDetail(bookId,type){
    	$.ajax({//验证是否有图书加工任务
		      type : "get",  
		      url : ctxPath+"/processCenter/checkBook/?bookId="+bookId,  
		      dataType:"json",  
		      async: false,
		      success : function(isExist) {
		      	if(isExist){
		      		if(type==1){
		      			window.document.location = ctxPath+"/processTask/detail?id="+bookId+"&type="+type;
		      		}else if(type=2){
		      			window.document.location = ctxPath+"/processCenter/detail?id="+bookId+"&type="+type;
		      		}
		      	}else{
		      		bootbox.setDefaults("locale", "zh_CN");
		      		bootbox.alert("图书不存在!", function() {});
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