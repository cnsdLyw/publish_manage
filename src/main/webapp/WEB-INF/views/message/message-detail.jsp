<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>消息详情</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />


<%@include file="../common/meta.html"%>
<style type="text/css">

/*状态为已接收 未接收的图标控制*/
.cir {
	width: 18px;
	height: 16px;
	overflow: hidden;
	cursor: pointer;
}

.Ru {
	background: url(/images/mail2757b6.png) -48px 0 no-repeat;
}

.Rr {
	background: url(/images/mail2757b6.png) -48px -16px no-repeat;
}
</style>
</head>

<body class="no-skin">
	<%@include file="../common/top.jsp"%>

	<div class="main-container" id="main-container">
		<%@include file="../common/left.jsp"%>
		<div class="main-content">
			<div class="main-content-inner">
				<!--所在位置 -->
				<div class="breadcrumbs" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="${ctx}/index">首页</a></li>
						<li></i> <a href="#">消息中心</a></li>
						<li class="active">消息详情</li>
					</ul>
				</div>
				
				</br>

				<form action="#" method="post" class="form-horizontal">
					<div class="col-md-12">
						<div class="form-group">
							<label class="col-sm-1 control-label no-padding-right" for="form-field-1"> 发送者： </label>
							<div class="col-sm-11">
								<label class="col-sm-12" style="padding-top: 7px;"> ${message.sender.orgName} </label>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-1 control-label no-padding-right" for="form-field-1">接收者： </label>
							<div class="col-sm-11">
								<label class="col-sm-12" style="padding-top: 7px;"> ${org_names} </label>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-1 control-label no-padding-right" for="form-field-1">消息类型： </label>
							<div class="col-sm-10">
								<label class="col-sm-12" style="padding-top: 7px;">  <c:if test="${message.type==1}">发送</c:if> <c:if
										test="${message.type==2}">接收</c:if>
								</label>
							</div>
						</div>
						 <div class="form-group">
							<label class="col-sm-1 control-label no-padding-right" for="form-field-1">消息文本： </label>
							<div class="col-sm-11">
								<label class="col-sm-12 " style="padding-top: 7px;"> <a href="${ctx}/message/pre?id=${message.id}" target="_blank">预览</a>
								</label>
							</div>
						</div>
						<%--<div class="form-group">
							<label class="col-sm-1 control-label no-padding-right" for="form-field-1">备注： </label>
							<div class="col-sm-11">
								<label class="col-sm-12 " style="padding-top: 7px;">
									${message.memo }
								</label>
							</div>
						</div> --%>
						
						<div class="form-group">
							<label class="col-sm-1 control-label no-padding-right" for="form-field-1">发送时间： </label>
							<div class="col-sm-10">
								<label class="col-sm-12" style="padding-top: 7px;"> <fmt:formatDate value='${message.sendtime}' type='both'
										pattern='yyyy-MM-dd HH:mm:ss' />
								</label>
							</div>
						</div>

						<%-- <div class="form-group">
							<label class="col-sm-1 control-label no-padding-right" for="form-field-1">消息附件： </label>
							<div class="col-sm-10">
								<label class="col-sm-12" style="padding-top: 7px;" id="otherList"> 
									<c:forEach items="${other_attachList }" var="otherAttach">
										<a href="${fileHomeUrl}/${otherAttach.storagePath}" title="点击预览" target="_blank"
											title="${otherAttach.name}">${otherAttach.name}</a>&nbsp;&nbsp;&nbsp;
									 </c:forEach>
								</label>
							</div>
						</div> --%>
						<c:if test="${type==null }">
							<tags:back backOnclick="" />

						</c:if>
						<div class="vspace-6-sm"></div>
					</div>
					<!-- end col-md-12-->
				</form>

			</div>
			<!--end  main-content-inner -->
		</div>
		<!-- end main-content -->

		<%@include file="../common/footer.jsp"%>

	</div>
	<!-- end main-container -->


	</div>
	<!-- end main-container -->


	<%@include file="../common/javascript.html"%>

	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		jQuery(function($) {

			/*表格全选操作加载*/
			var active_class = 'active';
			$('#dynamic-table > thead > tr > th input[type=checkbox]')
					.eq(0)
					.on(
							'click',
							function() {
								var th_checked = this.checked;//checkbox inside "TH" table header

								$(this)
										.closest('table')
										.find('tbody > tr')
										.each(
												function() {
													var row = this;
													if (th_checked)
														$(row)
																.addClass(
																		active_class)
																.find(
																		'input[type=checkbox]')
																.eq(0)
																.prop(
																		'checked',
																		true);
													else
														$(row)
																.removeClass(
																		active_class)
																.find(
																		'input[type=checkbox]')
																.eq(0)
																.prop(
																		'checked',
																		false);
												});
							});

			$('#dynamic-table').on('click', 'td input[type=checkbox]',
					function() {
						var $row = $(this).closest('tr');
						if (this.checked)
							$row.addClass(active_class);
						else
							$row.removeClass(active_class);
					});

		});//end jQuery
	</script>

</body>
</html>
