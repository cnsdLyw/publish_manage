<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.litc.common.util.Constant"%>
<c:set var="login_role" value="${sessionScope.loginRole }" />

<%
String loginame=(String) session.getAttribute("loginname"); 
%>
<!DOCTYPE html>
<html lang="zh-CN"> 
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>XXXXXXXX系统</title>
		<%@include file="./common/meta.html"%>
	</head>

	<body class="no-skin">

		<%@include file="./common/top.jsp"%>
		<!-- /section:basics/navbar.layout -->
		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>
				
				
				<%@ include file="./common/left.jsp"%>
				
			
			<div class="main-content">
			<div class="main-content-inner">
				<!-- #section:basics/content.breadcrumbs -->
				<div class="breadcrumbs" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">首页</a>
						</li>
					</ul>
					<!-- /.breadcrumb -->
					<!-- #section:basics/content.searchbox -->
					
					<!-- 
					<div class="nav-search" id="nav-search">
						<form class="form-search">
							<span class="input-icon"> <input type="text"
								placeholder="Search ..." class="nav-search-input"
								id="nav-search-input" autocomplete="off" /> <i
								class="ace-icon fa fa-search nav-search-icon"></i> </span>
						</form>
					</div>
					 -->
					<!-- /.nav-search -->

					<!-- /section:basics/content.searchbox -->
				</div>
				
				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					<!-- #section:settings.box -->
					<%@include file="./common/setColor.html"  %>
					<div class="row text-center">
						<h1>欢迎登录</h1>
					</div>
						<c:if test="${'4'!=login_role}">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<div class="row">
									<div class="col-xs-12 col-sm-6 widget-container-col">
										<div class="widget-box">
											<div class="widget-header">
												<h4 class="widget-title"><i class="menu-icon fa fa-envelope"></i> 消息</h4>
												<!-- #section:custom/widget-box.toolbar -->
												<div class="widget-toolbar">
													<a href="#" data-action="fullscreen" class="orange2" title="全屏">
														<i class="ace-icon fa fa-expand"></i>
													</a>
													<a href="${ctx}/message/list?type=2" title="点击查看更多">
														<i class="ace-icon fa fa-plus"></i>
													</a>
													<a href="#" data-action="collapse" title="隐藏">
														<i class="ace-icon fa fa-chevron-up"></i>
													</a>
													<a href="#" data-action="close" title="关闭">
														<i class="ace-icon fa fa-times"></i>
													</a>
												</div>

												<!-- /section:custom/widget-box.toolbar -->
											</div>

											<div class="widget-body">
												<div class="widget-main no-padding">
													<table class="table table-bordered table-striped">
														<thead class="thin-border-bottom">
															<tr>
																<th><i class="ace-icon fa fa-caret-right blue"></i>发送者</th>
																<th><i class="ace-icon fa fa-caret-right blue"></i>消息类型</th>
																<th class="hidden-480"><i class="ace-icon fa fa-caret-right blue"></i>发送时间</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach items="${messageList.content}" var="message">
															<tr class="mainIndexTr">
																<td><a href="${ctx}/message/detail?id=${message.id}">${message.sender.orgName}</td>
																<td>
																		<c:if test="${message.type==1}">发送</c:if>
																		<c:if test="${message.type==2}">接收</c:if>
																</td>
																<td class="hidden-480">
																	<fmt:formatDate  value='${message.sendtime}' type='both' pattern='yyyy-MM-dd HH:mm:ss' />
																</td>
															</tr>
															</c:forEach>
															
														</tbody>
													</table>
												</div>
											</div>
										</div>

										<!-- /section:custom/widget-box -->
									</div>

									<div class="col-xs-12 col-sm-6 widget-container-col">
												<!-- #section:custom/widget-box -->
												<div class="widget-box mainIndexDiv">
													<div class="widget-header">
														<h4 class="widget-title"><i class="menu-icon fa fa-book fa-4"></i> 通知</h4>
														<!-- #section:custom/widget-box.toolbar -->
														<div class="widget-toolbar">
															<a href="#" data-action="fullscreen" class="orange2" title="全屏">
																<i class="ace-icon fa fa-expand"></i>
															</a>
															<a href="${ctx}/companybook/list" title="点击查看更多">
																<i class="ace-icon fa fa-plus"></i>
															</a>
															<a href="#" data-action="collapse" title="隐藏">
																<i class="ace-icon fa fa-chevron-up"></i>
															</a>
															<a href="#" data-action="close" title="关闭">
																<i class="ace-icon fa fa-times"></i>
															</a>
														</div>
													</div>
													<div class="widget-body">
														<div class="widget-main no-padding">
															<table class="table table-bordered table-striped">
																<thead class="thin-border-bottom">
																	<tr>
																		<th><i class="ace-icon fa fa-caret-right blue"></i>书名</th>
																		<th><i class="ace-icon fa fa-caret-right blue"></i>ISBN</th>
																		<th class="hidden-480"><i class="ace-icon fa fa-caret-right blue"></i>作者</th>
																	</tr>
																</thead>
		
																<tbody>
																	<c:forEach items="${bookList.content}" var="book">
																	<tr class="mainIndexTr">
																		<td title="${book.title}"><a href="${ctx}/companybook/detail?id=${book.id}"><div style="width: 280px;" class="overFlowDiv">${book.title}</div></a></td>
																		<td>${book.isbn}</td>
																		<td title="${book.author}">
																			<div style="width: 120px;" class="overFlowDiv">
																				${book.author}
																			</div>
																		</td>
																	</tr>
																	</c:forEach>
																</tbody>
															</table>
														</div>
													</div>
												</div>
												<!-- /section:custom/widget-box -->
									</div><!-- /.span -->
								</div><!-- /.row -->
							</div><!-- /.col -->
							</c:if>
							<c:if test="${'1'==login_role||'4'==login_role}">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<div class="row">
									<div class="col-xs-12 col-sm-6 widget-container-col">
										<!-- #section:custom/widget-box -->
												<div class="widget-box mainIndexDiv">
													<div class="widget-header">
														<h4 class="widget-title"><i class="menu-icon fa fa-pencil-square-o"></i> 加工任务</h4>
														<!-- #section:custom/widget-box.toolbar -->
														<div class="widget-toolbar">
															<a href="#" data-action="fullscreen" class="orange2" title="全屏">
																<i class="ace-icon fa fa-expand"></i>
															</a>
															<a href="${ctx}/processTask/list" title="点击查看更多">
																<i class="ace-icon fa fa-plus"></i>
															</a>
															<a href="#" data-action="collapse" title="隐藏">
																<i class="ace-icon fa fa-chevron-up"></i>
															</a>
															<a href="#" data-action="close" title="关闭">
																<i class="ace-icon fa fa-times"></i>
															</a>
														</div>
													</div>
													<div class="widget-body">
														<div class="widget-main no-padding">
															<table class="table table-bordered table-striped">
																<thead class="thin-border-bottom">
																	<tr>
																		<th><i class="ace-icon fa fa-caret-right blue"></i>任务名称</th>
																		<th><i class="ace-icon fa fa-caret-right blue"></i>图书名称</th>
																		<th class="hidden-480"><i class="ace-icon fa fa-caret-right blue"></i>状态</th>
																	</tr>
																</thead>
		
																<tbody>
																	<c:forEach items="${processTaskList.content}" var="processTask">
																	<tr>
																		<td title="${processTask.taskName }">
																			<div style="width: 230px;" class="overFlowDiv col-sm-6">
																				${processTask.taskName }
																			</div>
																		</td>
																		<td title="${processTask.bookTitle }">
																			<div style="width: 200px;" class="overFlowDiv col-sm-6">
																				${processTask.bookTitle }
																			</div>
																		</td>
																		<td class="hidden-480">
																			<c:if test="${'1'==login_role}">
																				<c:if test="${processTask.status==0 }"><span class="label label-sm label-warning arrowed-in arrowed-right">正在加工</span></c:if>
																				<c:if test="${processTask.status==1 }"><span class="label label-sm label-success">加工完成</span></c:if>
																			</c:if>
																			<c:if test="${'4'==login_role}">
																				<c:if test="${processTask.status==0 }"><span class="label label-sm label-info arrowed-in">等待分配</span></c:if>
																				<c:if test="${processTask.status==1 }"><span class="label label-sm label-warning arrowed-in arrowed-right">正在加工</span></c:if>
																				<c:if test="${processTask.status==2 }"><span class="label label-sm label-success">加工完成</span></c:if>
																			</c:if>
																		</td>
																	</tr>
																	</c:forEach>
																	
																</tbody>
															</table>
														</div>
													</div>
												</div>
										
										
									</div><!-- /.span -->
									<div class="col-xs-12 col-sm-6 widget-container-col">
									</div>
								</div>
							</div>
							</c:if>
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
		<%@include file="./common/footer.jsp"  %>
			
		</div><!-- /.main-container -->

	<%@include file="./common/javascript.html"  %>
	</body>
	<script type="text/javascript">
			jQuery(function($) {
			
				$('#simple-colorpicker-1').ace_colorpicker({pull_right:true}).on('change', function(){
					var color_class = $(this).find('option:selected').data('class');
					var new_class = 'widget-box';
					if(color_class != 'default')  new_class += ' widget-color-'+color_class;
					$(this).closest('.widget-box').attr('class', new_class);
				});
			
			
				// scrollables
				$('.scrollable').each(function () {
					var $this = $(this);
					$(this).ace_scroll({
						size: $this.attr('data-size') || 100,
						//styleClass: 'scroll-left scroll-margin scroll-thin scroll-dark scroll-light no-track scroll-visible'
					});
				});
				$('.scrollable-horizontal').each(function () {
					var $this = $(this);
					$(this).ace_scroll(
					  {
						horizontal: true,
						styleClass: 'scroll-top',//show the scrollbars on top(default is bottom)
						size: $this.attr('data-size') || 500,
						mouseWheelLock: true
					  }
					).css({'padding-top': 12});
				});
				
				$(window).on('resize.scroll_reset', function() {
					$('.scrollable-horizontal').ace_scroll('reset');
				});
			
				
				$('#id-checkbox-vertical').prop('checked', false).on('click', function() {
					$('#widget-toolbox-1').toggleClass('toolbox-vertical')
					.find('.btn-group').toggleClass('btn-group-vertical')
					.filter(':first').toggleClass('hidden')
					.parent().toggleClass('btn-toolbar')
				});
			
				/**
				//or use slimScroll plugin
				$('.slim-scrollable').each(function () {
					var $this = $(this);
					$this.slimScroll({
						height: $this.data('height') || 100,
						railVisible:true
					});
				});
				*/
				
			
				/**$('.widget-box').on('setting.ace.widget' , function(e) {
					e.preventDefault();
				});*/
			
				/**
				$('.widget-box').on('show.ace.widget', function(e) {
					//e.preventDefault();
					//this = the widget-box
				});
				$('.widget-box').on('reload.ace.widget', function(e) {
					//this = the widget-box
				});
				*/
			
				//$('#my-widget-box').widget_box('hide');
			
				
			
				// widget boxes
				// widget box drag & drop example
			    $('.widget-container-col').sortable({
			        connectWith: '.widget-container-col',
					items:'> .widget-box',
					handle: ace.vars['touch'] ? '.widget-header' : false,
					cancel: '.fullscreen',
					opacity:0.8,
					revert:true,
					forceHelperSize:true,
					placeholder: 'widget-placeholder',
					forcePlaceholderSize:true,
					tolerance:'pointer',
					start: function(event, ui) {
						//when an element is moved, it's parent becomes empty with almost zero height.
						//we set a min-height for it to be large enough so that later we can easily drop elements back onto it
						ui.item.parent().css({'min-height':ui.item.height()})
						//ui.sender.css({'min-height':ui.item.height() , 'background-color' : '#F5F5F5'})
					},
					update: function(event, ui) {
						ui.item.parent({'min-height':''})
						//p.style.removeProperty('background-color');
					}
			    });
			});
			jQuery(function($) {
				if('${errorMessage}'!=''){
					bootbox.setDefaults("locale", "zh_CN");
					bootbox.dialog({
					message : "<span class='bigger-110'>${errorMessage}</span>",
					closeButton: false,
					buttons : {
						"click" : {
							"label" : "确定",
							"className" : "btn-sm btn-primary",
							"callback" : function() {
								// nothing to do
							}
						}
					}
				  });
				}
				
			
			});
		</script>
</html>
