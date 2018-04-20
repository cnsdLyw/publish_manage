<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<head>
	<title>供应商注册</title>
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="${ctx }/resources/script/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
	<link href="${ctx }/resources/script/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css">
	<link href="${ctx }/resources/script/bootstrap/css/templatemo_style.css" rel="stylesheet" type="text/css">
	<!-- ace styles -->
	<link rel="stylesheet" href="${ctx }/resources/script/bootstrap/css/font-awesome.css" />
	<link rel="stylesheet" href="${ctx }/resources/script/bootstrap/css/ace-fonts.css" />
	<link rel="stylesheet" href="${ctx }/resources/script/bootstrap/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />
	
	<script src="${ctx }/resources/script/js/jquery.1.9.1.min.js"  type="text/javascript" ></script>
	<script src="${ctx }/resources/script/bootstrap/js/bootstrap.min.js"  type="text/javascript" ></script>
	<script src="${ctx }/resources/script/js/validate/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/js/validate/additional-methods.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/js/bootbox.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/js/validate/myValidate.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/js/area/jsAddress.js" type="text/javascript"></script>
    <script type="text/javascript">
   		  $(document).ready(function(){
				setValidateForm();
			});
			
			// 绑定校验格式
			function setValidateForm(selector) {
				$(".validate-form").validate({
					/*onfocusout: function(element){
				        $(element).valid();
				    },*/
					errorElement : "span",
					errorClass : "help-block has-error",
					errorPlacement : function(e, t) {
						return t.parents(".controls").children(".ccontrols").last().append(e);
					},
					highlight : function(e) {
						return $(e).closest('.form-group').removeClass("has-error has-success")
								.addClass('has-error');
					},
					success : function(e) {
						return e.closest(".form-group").removeClass("has-error");
					},
					submitHandler : function(form) {
			      		$.ajax({//验证邮箱
						      type : "get",  
						   	  url : "${ctx}/supplier/isEmailExist/?email="+$("#businessContactsEmail").val(),  
						      dataType:"json",  
						      success : function(isExist) {
						      	if(!isExist){
						      		form.submit();
						      	}else{
						      		bootbox.alert("邮箱已存在，请重新输入!", function() {});
						      		return false;
						      	}
						      }  
						  });
		
						//--------------------------------										
										
             				//先验证机构代码，在验证是否同意服务条款
							/*if($("#agree").is(":checked")==false){
								bootbox.alert("您还没有同意服务条款和隐私政策!", function() {});
								//$("#warning-block").show();
								return false;
							}*/
					}
				});
			};
		function hideBlock(){
			$("#warning-block-ok").hide();
		}	
		<c:if test="${successCode==1||successCode==2}">
			setTimeout("javascript:hideBlock();", 3500)
		</c:if>	
    </script>
    <style type="text/css">
		html #body{
			//background-image:url('${ctx}/resources/images/login/6.jpg');
			background-color:#FAFAD2 ;//#696969;
		}
		.formTitle{
			font-size: 17px;
			color: #269abc;//rgb(74, 164, 180);
			font-family:microsoft yahei;
		}
	</style>
</head>
<body id="body" class="templatemo-bg-gray">
	<div id="warning-block" class="alert alert-warning" style="display: none;"><!--  alert-success  alert-warning -->
  		 <a href="#" class="close" data-dismiss="alert">&times;</a>
  		 <strong>无法提交！</strong>您还没有同意服务条款和隐私政策！
	</div>
	<c:if test="${successCode==1 }">
		<div id="warning-block-ok" class="alert alert-success"><!--  alert-success  alert-warning -->
	  		 <a href="#" class="close" data-dismiss="alert">&times;</a>
	  		 <strong>注册成功,请等待审核！</strong> 
		</div>
	</c:if>
	<h1 class="margin-bottom-15">供应商注册</h1> <!-- <input type="button" value="SSS" onclick="test()">  -->
	<div class="page-content" style="padding-top: 0px;padding-bottom: 0px;">
		<div class="col-md-12">			
			<form:form class="form-horizontal templatemo-create-account templatemo-container validate-form" id="validate-form" commandName="supplier"
				action=" ${pageContext.request.contextPath}/saveSupplier" method="post" enctype="multipart/form-data">
				<div class="form-inner">
					 <div class='form-group controls'>
                        <label class='control-label col-md-3 no-padding-right formTitle'><strong><font color="red">*</font>&nbsp;&nbsp;公司名称</strong></label>
                        <div class='col-md-8' style="height:50px;">
                          <input class='form-control' id="companyName" name="companyName" data-rule-required='true' stringCheck="true" data-rule-minlength='6' 
                          	data-rule-maxlength='30' placeholder='公司名称' type='text'>
                        </div>
                        <div class='col-md-3'></div>
                        <div class='col-md-4 ccontrols'></div>
                     </div>
                     <!-- 
                     <div class='form-group controls'>
                        <label class='control-label col-md-3 no-padding-right formTitle'><strong><font color="red">*</font>&nbsp;&nbsp;主管人</strong></label>
                        <div class='col-md-8' style="height:50px;">
                          <input class='form-control' id="supervisor" name="supervisor" data-rule-required='true' isChinese="true" 
                          		data-rule-minlength='2'  data-rule-maxlength='20' placeholder='主管人' type='text'>
                        </div>
                        <div class='col-md-3'></div>
                        <div class='col-md-4 ccontrols'></div>
                     </div>
                     
                     <div class='form-group controls'>
                        <label class='control-label col-md-3 no-padding-right formTitle'><strong><font color="red">*</font>&nbsp;&nbsp;主管人电话</strong></label>
                        <div class='col-md-8' style="height:50px;">
                          <input class='form-control' id="supervisorPhone" name="supervisorPhone" data-rule-required='true' isPhone="true" 
                          	data-rule-minlength='6'  data-rule-maxlength='30' placeholder='主管人电话' type='text'>
                        </div>
                     	<div class='col-md-3'></div>
                        <div class='col-md-4 ccontrols'></div>
                     </div>
                     
                     <div class='form-group controls'>
                        <label class='control-label col-md-3 no-padding-right formTitle'><strong><font color="red">*</font>&nbsp;&nbsp;业务联系人</strong></label>
                        <div class='col-md-8' style="height:50px;">
                          <input class='form-control' id="businessContacts" name="businessContacts" data-rule-required='true' isChinese="true" 
                          		data-rule-minlength='2'  data-rule-maxlength='20' placeholder='业务联系人' type='text'>
                        </div>
                        <div class='col-md-3'></div>
                        <div class='col-md-4 ccontrols'></div>
                     </div>
                     
                     <div class='form-group controls'>
                        <label class='control-label col-md-3 no-padding-right formTitle'><strong><font color="red">*</font>&nbsp;&nbsp;业务联系人电话</strong></label>
                        <div class='col-md-8' style="height:50px;">
                          <input class='form-control' id="businessContactsPhone" name="businessContactsPhone" data-rule-required='true' isPhone="true" 
                          	data-rule-minlength='6'  data-rule-maxlength='30' placeholder='业务联系人电话' type='text'>
                        </div>
                        <div class='col-md-3'></div>
                        <div class='col-md-4 ccontrols'></div>
                     </div>
                     
                     <div class='form-group controls'>
                        <label class='control-label col-md-3 no-padding-right formTitle'><strong><font color="red">*</font>&nbsp;&nbsp;业务联系人邮箱</strong></label>
                        <div class='col-md-8' style="height:50px;">
                          <input class='form-control' id="businessContactsEmail" name="businessContactsEmail" data-rule-required='true' data-rule-email='true' 
                          		data-rule-minlength='6'  data-rule-maxlength='30' placeholder='联系人邮箱' type='text'>
                        </div>
                        <div class='col-md-3'></div>
                        <div class='col-md-4 ccontrols'></div>
                     </div>
                      -->
					<div class="form-group">
						<label class='control-label col-md-3 no-padding-right formTitle'><strong>行业分类</strong></label>
						<div class='col-md-8' style="height:50px;">
			            		<select class="form-control col-xs-5 col-md-5" id="industryClassification" name="industryClassification" onchange="checkClass(this)">
			            			<option value="">-选择行业分类-</option>
				            		<c:forEach var="clzss" items="${classList}">
				            			<option value="${clzss.classCode}">${clzss.className }</option>
						        	</c:forEach>
						        	<option value="-1">其他分类</option>
					        	</select>
					        	<div id="otherClassDiv" style="display: none;">
					        		<input id="otherClass" name="otherClass" class='form-control' placeholder='添加其他分类，不超过16个字' type='text' data-rule-maxlength='16'>
								</div>
						</div>
                        <div class='col-md-4 ccontrols'>
                        </div>
					</div>
					<div class="form-group">
						<label class='control-label col-md-3 no-padding-right formTitle'><strong>地区</strong></label> 
						<div class="col-md-3" style="height:50px;">		          	
			            	<select class="form-control col-md-12" id="province" name="province">
						    </select>
				        </div>
				        <div class="col-md-3" style="height:50px;">		          	
							<select class="form-control col-md-12" id="city" name="city">
						    </select>
				        </div>
				        <div class="col-md-3" style="height:50px;">		          	
							<select class="form-control col-md-12" id="county" name="county">
				    		</select>
				        </div>
					</div>
					 <div class='form-group controls'>
                        <label class='control-label col-md-3 no-padding-right formTitle'><strong><font color="red">*</font>&nbsp;&nbsp;供应商地址</strong></label>
                        <div class='col-md-8' style="height:50px;">
                          <input class='form-control' id="address" name="address" data-rule-required='true' stringCheck="true"
                          	data-rule-minlength='6'  data-rule-maxlength='30' placeholder='供应商地址' type='text'>
                        </div>
                        <div class='col-md-3'></div>
                        <div class='col-md-4 ccontrols'></div>
                     </div>
                     
                     <div class='form-group controls'>
                        <label class='control-label col-md-3 no-padding-right formTitle'><strong><font color="red">*</font>&nbsp;&nbsp;邮编</strong></label>
                        <div class='col-md-8' style="height:50px;">
                          <input class='form-control' id="postalCode" name="postalCode" data-rule-required='true' isPostalcode="true"
                          	data-rule-minlength='6'  data-rule-maxlength='30' placeholder='邮编' type='text'>
                        </div>
                        <div class='col-md-3'></div>
                        <div class='col-md-4 ccontrols'></div>
                     </div>
                     
                     <div class='form-group controls'>
                        <label class='control-label col-md-3 no-padding-right formTitle'><strong>公司网址</strong></label>
                        <div class='col-md-8' style="height:50px;">
                          <input class='form-control' id="website" name="website" isWebsit="true"
                          	data-rule-minlength='6'  data-rule-maxlength='30' placeholder='例：www.xxx.com' type='text'>
                        </div>
                        <div class='col-md-3'></div>
                        <div class='col-md-4 ccontrols'></div>
                     </div>
                     
                      <div class='form-group controls'>
                        <label class='control-label col-md-3 no-padding-right formTitle'><strong>公司电话</strong></label>
                        <div class='col-md-8' style="height:50px;">
                          <input class='form-control' id="telephone" name="telephone" isPhone="true" 
                          	data-rule-minlength='6'  data-rule-maxlength='30' placeholder='公司电话' type='text'>
                        </div>
                        <div class='col-md-3'></div>
                        <div class='col-md-4 ccontrols'></div>
                     </div>
                     
                     <div class='form-group controls'>
                        <label class='control-label col-md-3 no-padding-right formTitle'><strong>公司传真</strong></label>
                        <div class='col-md-8' style="height:50px;">
                          <input class='form-control' id="fax" name="fax" isPhone="true" 
                          	data-rule-minlength='6'  data-rule-maxlength='30' placeholder='公司电话' type='text'>
                        </div>
                        <div class='col-md-3'></div>
                        <div class='col-md-4 ccontrols'></div>
                     </div>
                     <!-- 
                     <div class='form-group controls'>
                        <label class='control-label col-md-3 no-padding-right formTitle'><strong>登录帐号</strong></label>
                        <div class='col-md-8' style="height:50px;">
                          <input class='form-control' id="loginName" name="loginName" data-rule-required='true' chrnum="true"
                          		data-rule-minlength='6'  data-rule-maxlength='30' placeholder='用户登录名' type='text'>
                        </div>
                        <div class='col-md-3'></div>
                        <div class='col-md-4 ccontrols'></div>
                     </div>
                      -->
                     <!-- 相关附件上传 -->
                     <div class="form-group">
                     	<div class="control-label col-md-3 no-padding-right formTitle"><strong>主管人身份证正面</strong></div>
						<div class="col-md-8">
							<!-- #section:custom/file-input -->
							<input type="file" id="idCardObverseFile" name="idCardObverseFile"/>
						</div>
					 </div>
					 <div class="form-group">
                     	<div class="control-label col-md-3 no-padding-right formTitle"><strong>主管人身份证反面</strong></div>
						<div class="col-md-8">
							<!-- #section:custom/file-input -->
							<input type="file" id="idCardReverseFile" name="idCardReverseFile"/>
						</div>
					 </div>
					  <div class="form-group">
                     	<div class="control-label col-md-3 no-padding-right formTitle"><strong>企业营业执照副本</strong></div>
						<div class="col-md-8">
							<!-- #section:custom/file-input -->
							<input type="file" id="businessLicenceCopyFile" name="businessLicenceCopyFile"/>
						</div>
					 </div>
					  <div class="form-group">
                     	<div class="control-label col-md-3 no-padding-right formTitle"><strong>授权书</strong></div>
						<div class="col-md-8">
							<!-- #section:custom/file-input -->
							<input type="file" id="certificateAuthorizationFile" name="certificateAuthorizationFile"/>
						</div>
					 </div>
					 
					 <div class='form-group controls'>
                        <label class='control-label col-md-3 no-padding-right formTitle'><strong><font color="red">*</font>&nbsp;&nbsp;供应商简介</strong></label>
                        <div class='col-md-8' style="height:200px;">
                           <textarea style="width:100%;" rows="8"  placeholder="请输入1-200个字符的内容" data-rule-minlength='1' data-rule-maxlength='600' id="remark" name="remark">${supplier.remark}</textarea>
				        	<h6 class="col-md-12" style="text-align: left;">您还可以输入<span id="word">200</span>个字符。</h6>
                        </div>
                        <div class='col-md-3'></div>
                        <div class='col-md-4 ccontrols'></div>
                     </div>
            
			        <div class="form-group">
		        	   <div class="col-md-2">
		        	   </div>
			           <div class="col-md-6">
			            <label>
			            	<input type="checkbox" id="agree" name="agree" value="1">我同意 <a href="javascript:;" data-toggle="modal" data-target="#templatemo_modal">服务条款</a> 
			            	和<a href="javascript:;" data-toggle="modal" data-target="#PrivacyPolicy_modal">隐私政策。</a>
			            </label>
			           </div>
			            <div class="col-md-4">
			            </div>
			        </div>
			        <div class="form-group">
		        	  <div class="col-md-2">
	        	      </div>
			          <div class="col-md-6">
			            <input type="submit" value="Create account" class="btn btn-info">
			            <a href="${ctx }" class="pull-right">Main</a>
			          </div>
			          <div class="col-md-4">
			          </div>
			        </div>	
				</div>				    	
		      </form:form>		      
		</div>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="templatemo_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title" id="myModalLabel"><strong>服务条款</strong></h4>
	      </div>
	      <div class="modal-body">
	      	<p>This form is provided by <a rel="nofollow" href="#">Free HTML5 Templates</a> that can be used for your websites. Cras mattis consectetur purus sit amet fermentum. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Morbi leo risus, porta ac consectetur ac, vestibulum at eros.</p>
	        <p>Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Vivamus sagittis lacus vel augue laoreet rutrum faucibus dolor auctor. Aenean lacinia bibendum nulla sed consectetur. Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Donec sed odio dui. Donec ullamcorper nulla non metus auctor fringilla. Cras mattis consectetur purus sit amet fermentum. Cras justo odio, dapibus ac facilisis in, egestas eget quam.</p>
	        <p>Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Vivamus sagittis lacus vel augue laoreet rutrum faucibus dolor auctor.</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="PrivacyPolicy_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title" id="myModalLabel">隐私政策</h4>
	      </div>
	      <div class="modal-body">
	      	<p>This form is provided by <a rel="nofollow" href="#">Free HTML5 Templates</a> that can be used for your websites. Cras mattis consectetur purus sit amet fermentum. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Morbi leo risus, porta ac consectetur ac, vestibulum at eros.</p>
	        <p>Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Vivamus sagittis lacus vel augue laoreet rutrum faucibus dolor auctor. Aenean lacinia bibendum nulla sed consectetur. Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Donec sed odio dui. Donec ullamcorper nulla non metus auctor fringilla. Cras mattis consectetur purus sit amet fermentum. Cras justo odio, dapibus ac facilisis in, egestas eget quam.</p>
	        <p>Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Vivamus sagittis lacus vel augue laoreet rutrum faucibus dolor auctor.</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	      </div>
	    </div>
	  </div>
	</div>
</body>

<script src="${ctx }/resources/script/bootstrap/js/ace/ace.js"  type="text/javascript" ></script>
<script src="${ctx }/resources/script/bootstrap/js/ace/elements.fileinput.js"  type="text/javascript" ></script>
<script src="${ctx }/resources/script/bootstrap/js/ace-elements.js"  type="text/javascript" ></script>

<script type="text/javascript">
	addressInit('province', 'city', 'county','${supplier.province}','${supplier.city}','${supplier.county}');
	jQuery(function($) {
		$('#idCardObverseFile,#idCardReverseFile,#businessLicenceCopyFile,#certificateAuthorizationFile').ace_file_input({
			no_file:'还未选择文件 ...',
			btn_choose:'选择',
			btn_change:'修改',
			droppable:false,
			onchange:null,
			thumbnail:false //| true | large
			//whitelist:'gif|png|jpg|jpeg'
			//blacklist:'exe|php'
			//onchange:''
			//
		});
		
		var textLength = 200;
	  	$("#remark").keyup(function(){
	   		var len = $(this).val().length;
	   		if(len > (textLength-1)){
	    		$(this).val($(this).val().substring(0,textLength));
	   		}
	   		var num = textLength - len;
	   		if(num>=0)
	   		$("#word").text(num);
	  	});
	});
	
	function checkClass(obj){
		if('-1'==obj.value){
			$("#otherClassDiv").css("display","block");
		}else{
			$("#otherClassDiv").css("display","none");
			$("#otherClass").val("");
		}
	}
	
</script>	
</html>