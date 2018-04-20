<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>文件发布管理</title>

<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<%@include file="../../common/meta.html"%>
<link href="${ctx}/resources/script/js/easyvalidate/css/validate_platform.css" rel="stylesheet" type="text/css" />
<style type="text/css">
 .file {
    position: relative;
   display: block;
    background: #EEEEEE;
    border: 1px solid #cccccc;
    border-radius: 4px;
    padding: 10px 12px;
    overflow: hidden;
    color: #000000;
    text-decoration: none;
    text-indent: 0;
    line-height: 10px;
}
.file input {
    position: absolute;
    font-size: 100px;
    right: 0;
    top: 0;
    opacity: 0;
}
.file:hover {
    border-color: #cccccc;
    color: #000000;
    text-decoration: none;
}
</style>

<script>
	function change(input){
		var fileName = input.value;
	}
</script>
</head>

<body class="no-skin">
	<%@include file="../../common/top.jsp"%>
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<%@include file="../../common/left.jsp"%>
		<div class="main-content">
			<div class="main-content-inner">
				<!-- #section:basics/content.breadcrumbs -->
				<div class="breadcrumbs" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="${ctx}/index">首页</a></li>
						<li><a href="javascript:void(0);">信息发布管理</a></li>
						<li class="active">文件发布管理</li>
					</ul>

				</div>
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<div class="col-md-12">
						<form id="mainForm" class="form-horizontal" method="POST" action="${ctx}/content/save" enctype="multipart/form-data">
							<input type="hidden" id="id" name="id" value="${manuscript.id }" />
							<input type="hidden" id="nodeID" name="nodeID" value="${nodeID }" />
							<input type="hidden" id="masterID" name="masterID" value="${nodeID}" />
							
							<!-- 列表页面检索条件 -->
							<input type="hidden" id="pageNo" name="pageNo" value="${pageNo }" />
							<input type="hidden" id="pageSize" name="pageSize" value="${pageSize }" />
							<input type="hidden" id="queryOrderBy" name="queryOrderBy" value="${queryOrderBy }" />
							<input type="hidden" id="queryOrderType" name="queryOrderType" value="${queryOrderType }" />
							<input type="hidden" id="query_all_like" name="query_all_like" value="${query_all_like }" />
					
							
							<div class="form-group">
								<label class="col-md-2 control-label no-padding-right" for="form-field-1"><font color="red">*</font>&nbsp;标题：
								</label>
								<div class="col-md-6 ">
									<input type="text" class="col-md-12" id="title" name="title" value="${manuscript.title}" placeholder="请输入字符为2-60的标题"
										reg="^.{2,60}$" data-rel="tooltip" data-original-title="请输入字符为2-60的标题名！" tip="请输入字符为2-60的标题名！" data-placement="bottom">
								</div>
								<span name="easyTip"></span>
							</div>

							<div class="form-group">
							
								<label class="col-md-2 control-label no-padding-right" for="form-field-1"><font color="red">*</font>&nbsp;类型：
								</label>
								<div class="col-md-3">
								<select class="col-md-12 col-sm-8" name="newType" id="newType" data-placement="bottom">
								    <option value="1">报表</option>
								    <option value="2">书目</option>
								    <option value="3">模板</option>
								    <option value="4">相关资料</option>
								</select>
							</div>
							</div>
							<div class="form-group">
								<label class="col-md-2 col-sm-1 control-label no-padding-right"><font color="red">*</font>&nbsp; 文件： </label>
								<div class="col-md-3">
									<label style="margin-top: 8px;"> 
   										<input id="imageUrl" reg="." readonly="readonly" data-rel="tooltip" data-original-title="请选择文件，必选项！" tip="请选择文件！" data-placement="bottom" value="${manuscript.posterpic}"  style="float:left;width:400px;">
   										 <a href="#" class="file" style="float:left;height:27px; "> 
   										<input type="file" name="file" id="file" onchange="changeFile()">选择文件</a>
   										
									<!-- <a href="javascript:;" class="file">选择文件
									</a> -->
									
									<!-- <iframe id="file" name="file" height="45px"
											frameborder="0" scrolling="no" width="365px" type="file"
											src="${ctx}/common/fileupload/fileSelect.jsp?type=101&filelist=coverList&limitType=*.*&limitSize=1024&path=posterpic&name=cover_name&size=cover_size">
									</iframe> -->
									<input type="hidden" id="cover_id" name="cover_id" value="${cover_id}"> <input type="hidden"
											id="cover_name" name="cover_name" value="${fileName}"> <input type="hidden" id="posterpic"
											name="posterpic" value="${manuscript.posterpic}"> <input type="hidden" id="cover_size" name="cover_size"
											value="${cover_size}"> 
									</label>
								</div>
							</div>
								
								
							
								<!--  <div class="col-md-7">
									<label class="col-md-1 col-sm-2" style="margin-top: 8px;">
										<button class="btn btn-xs btn-danger" type="button"
											onClick="deleteAttach('cover_id','cover_name','posterpic','cover_size','coverList')">
											<i class="ace-icon fa fa-times"></i> <span class="bigger-110">移除</span>
										</button>
									</label> <label class="col-md-10 col-sm-10" id="coverList" style="margin-top: 8px;"> <c:if
											test="${not empty manuscript.posterpic}">
														&nbsp;&nbsp;<a href="${rctx}/${manuscript.posterpic}" target="_blank">预览</a>
											<%--
														<button type="button" class="btn spinbox-down btn-md btn-danger">
															<i class="icon-only  ace-icon ace-icon fa fa-minus bigger-80"></i>
														</button>
														 --%>
										</c:if>
									</label>
								</div>  -->

							</div>

							

							<tags:submitAndBack submitButton="submit" submitOnclick="" backOnclick="javascript:backList();" />

						</form>
					</div>
				</div>
			</div>
		</div>
		<%@include file="../../common/footer.jsp"%>
	</div>
	<%@include file="../../common/javascript.html"%>
	<script type="text/javascript" src="${ctx}/resources/script/js/easyvalidate/js/easy_validator.pack_platform.js"></script>
	<script type="text/javascript" src="${ctx}/resources/script/js/easyvalidate/js/jquery.bgiframe.min.js"></script>

	<script src="${ctx }/resources/script/ueditor/ueditor.config.js" type="text/javascript"></script>
	<script src="${ctx }/resources/script/ueditor/ueditor.all.js" type="text/javascript"></script>
	<script src="${ctx }/resources/script/ueditor/lang/zh-cn/zh-cn.js" type="text/javascript"></script>
	<script type="text/javascript">
	var returnValue=true;
	var isExtendsValidate = true; //如果要试用扩展表单验证的话，该属性一定要申明
	function extendsValidate() { //函数名称，固定写法
		if($("#id").val()){
			 $.ajax({//验证登录名
			      type : "POST",  
			      url : "${ctx}/content/isContentNameExistWithId?nodeId=${nodeID}",  
			      dataType:"json",  
			      data:{id:$("#id").val(),title:$("#title").val()},
			      async: false,
			      success : function(isExist) {
			      	if(isExist){
			      		bootbox.setDefaults("locale", "zh_CN");
						bootbox.dialog({
							message : "<span class='bigger-110'>链接新闻标题已存在！</span>",
							buttons : {
								"click" : {
									"label" : "确定",
									"className" : "btn-sm btn-primary"
								}
							}
						});
						if(isExist){
							returnValue=isExist;
						}
			      	}
			      	if(!isExist){
			      		returnValue = isExist;
			      	}
			      }  
			  });
			 if(returnValue){
				 return false;
			 }else{
				 return true;
			 }
		}else{
			$.ajax({//验证登录名
			      type : "POST",  
			      url : "${ctx}/content/isContentNameExist?nodeId=${nodeID}",  
			      dataType:"json",  
			      data:{title:$("#title").val()},
			      async: false,
			      success : function(isExist) {
			      	if(isExist){
			      		bootbox.setDefaults("locale", "zh_CN");
						bootbox.dialog({
							message : "<span class='bigger-110'>链接标题已存在！</span>",
							buttons : {
								"click" : {
									"label" : "确定",
									"className" : "btn-sm btn-primary"
								}
							}
						});
						if(isExist){
							returnValue=isExist;
						}
			      	}
			      	if(!isExist){
			      		returnValue = isExist;
			      	}
			      }  
			  });
			 if(returnValue){
				 return false;
			 }else{
				 return true;
			 }
		}
	}

		jQuery(document).ready(function() {
			//启用tooltip提示信息
			$('[data-rel=tooltip]').tooltip();
			var url = document.getElementById("imageUrl").value;
			if(url!=''){
				document.getElementById("imageUrl").style.display="";
				document.getElementById("titles").style.display="";
			}
		});


			//删除附件
		function deleteAttach(id, name, path, size, attlist) {
			var pathStr = $("#" + path).val();
			if (pathStr != '') {
				bootbox.setDefaults("locale", "zh_CN");
				bootbox.confirm("确定要删除附件吗？", function(result) {
					if (result) {//确认
						document.getElementById(attlist).innerHTML = '';
						document.getElementById(id).value = '';
						document.getElementById(name).value = '';
						document.getElementById(path).value = '';
						document.getElementById(size).value = '';
					}
				});
			}
		}

		 function changeFile(){
         	var url = $('#file').val();
            
            if(url==""||url==null){
              return;
            }else{
				$("#imageUrl").val(url);
            }
        
        }
		
		function backList() {
			//方式1实现返回
// 			var path="${ctx}/content/list/${nodeID}";
// 			var serializeHidden = $("#mainForm").find(":hidden").serializeArray();
// 			var param ="1=1";
// 			$.each(serializeHidden, function() {
// 				param=param+"&"+this.name+"="+this.value;
				
// 			});
// 			window.location.href=path+"?"+param;

			//方式2实现返回
			$("form").find("input[reg],input[rel],select[reg],select[rel],textarea[reg],textarea[rel]").each(function(){
				$(this).removeAttr("reg");
				$(this).removeAttr("rel");
			});
			
			/* $("#mainForm").attr("action", "${ctx}/content/list/${nodeID}");
			$("#mainForm").submit(); */
			
			
			window.location.href= "${ctx}/content/list/${nodeID}";
			//history.back();

		}
		
	</script>


</body>
</html>