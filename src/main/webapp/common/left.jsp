<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	
%>
<c:set value="admin" var="loginname"></c:set>
<html lang="zh-CN">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<!-- ace scripts -->
<script type="text/javascript">
	jQuery(function($) {
		var pathName = window.document.location.pathname;
		var channelpath = pathName.substring(pathName.indexOf('/') + 1);
		if (channelpath.indexOf("-") > 0) {
			channelpath = channelpath.substring(0, channelpath.indexOf("-"));
		}
		if (channelpath != null && channelpath != "/index"
				&& channelpath.length > 2) {
			var isHref = $("a[href='" + channelpath + "']").attr("href");
			if (undefined == isHref) {
				channelpath = "/" + channelpath;
			}
			isHref = $("a[href='" + channelpath + "']").attr("href");
			if (undefined == isHref) {
				var ctxStr = "${ctx}";
				// 				alert(ctxStr);
				channelpath = channelpath.replace(ctxStr, "");
				// 				alert(channelpath);
				//处理input和detail页面的左边菜单选中
				var currentMode = channelpath.substring(1, channelpath.indexOf(
						"/", 2));

				// 				alert(currentMode);
				$("a[lib='" + currentMode + "']").parent().addClass("active");
				$("a[lib='" + currentMode + "']").parent().parent().parent()
						.addClass("active open");
			}

			$("a[href='" + channelpath + "']").parent().addClass("active");
			$("a[href='" + channelpath + "']").parent().parent().parent()
					.addClass("active open");

		} else {
			$("ul.nav nav-list > li.active open").removeClass("active open");
			$("ul>li.active").removeClass("active");
		}
	});
</script>
</head>

<body>
	<div id="sidebar" class="sidebar responsive">
		<!-- 
			<div class="sidebar-shortcuts" id="sidebar-shortcuts">
				<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
					<button class="btn btn-success">
						<i class="ace-icon fa fa-signal"></i>
					</button>

					<button class="btn btn-info">
						<i class="ace-icon fa fa-pencil"></i>
					</button>

					<button class="btn btn-warning">
						<i class="ace-icon fa fa-users"></i>
					</button>

					<button class="btn btn-danger">
						<i class="ace-icon fa fa-cogs"></i>
					</button>

				</div>

				<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
					<span class="btn btn-success"></span>

					<span class="btn btn-info"></span>

					<span class="btn btn-warning"></span>

					<span class="btn btn-danger"></span>
				</div>
			</div>
			 -->

		<ul class="nav nav-list">

			<!-- 书目中心 start -->
			<li class=""><a href="#" class="dropdown-toggle"> <i class="menu-icon fa fa-list"></i> <span
					class="menu-text"> 书目中心</span> <b class="arrow fa fa-angle-down"></b>
			</a> <b class="arrow"></b>
				<ul class="submenu">

					<security:authorize ifAnyGranted="ROLER_PUBLISH">
						<li class=""><a href="${ctx}/companybook/list" lib="companybook"> <i class="menu-icon fa fa-caret-right"></i>
								出版书目管理
						</a> <b class="arrow"></b></li>
					</security:authorize>

					<security:authorize ifAnyGranted="ROLER_LIBRARY">
						<li class=""><a href="${ctx}/librarybook/list" lib="librarybook"> <i class="menu-icon fa fa-caret-right"></i>
								图书馆书目管理
						</a> <b class="arrow"></b></li>
					</security:authorize>

					<security:authorize ifAnyGranted="ROLER_ISSUE">
						<li class=""><a href="${ctx}/issuebook/list" lib="issuebook"> <i class="menu-icon fa fa-caret-right"></i>
								发行书目管理
						</a> <b class="arrow"></b></li>
					</security:authorize>
				</ul></li>
			<security:authorize ifAnyGranted="ROLER_MESSAGE">
				<li class=""><a href="#" class="dropdown-toggle"> <i class="menu-icon fa fa-list"></i> <span
						class="menu-text"> 消息日志</span> <b class="arrow fa fa-angle-down"></b>
				</a> <b class="arrow"></b>
					<ul class="submenu">
						<li class=""><a href="${ctx}/message/list" lib="message"> <i class="menu-icon fa fa-caret-right"></i>
								消息管理
						</a> <b class="arrow"></b></li>
					</ul></li>
			</security:authorize>


			<!-- 加工中心 start -->
			<security:authorize ifAnyGranted="ROLER_CENTER">
				<li class=""><a href="#" class="dropdown-toggle"> <i class="menu-icon fa fa-list"></i> <span
						class="menu-text"> 加工中心</span> <b class="arrow fa fa-angle-down"></b>
				</a> <b class="arrow"></b>
					<ul class="submenu">
						<li class=""><a href="${ctx}/handledata/handlecenter.jsp" lib="handledata"> <i class="menu-icon fa fa-caret-right"></i> 中心加工管理
						</a> <b class="arrow"></b></li>

						<li class=""><a href="${ctx}/handledata/handleagency.jsp" lib="handledata"> <i class="menu-icon fa fa-caret-right"></i> 机构加工管理
						</a> <b class="arrow"></b></li>
						<li class=""><a href="${ctx}/handledata/handleuser.jsp" lib="handledata"> <i class="menu-icon fa fa-caret-right"></i> 用户加工管理
						</a> <b class="arrow"></b></li>
					</ul></li>
			</security:authorize>

			<!-- 信息发布管理 start -->
			<security:authorize ifAnyGranted="ROLER_CENTER">
				<li class=""><a href="#" class="dropdown-toggle"> <i class="menu-icon fa fa-list"></i> <span
						class="menu-text"> 信息发布管理</span> <b class="arrow fa fa-angle-down"></b>
				</a> <b class="arrow"></b>
					<ul class="submenu">
						<li class=""><a href="${ctx}/content/list" lib="content"> <i class="menu-icon fa fa-caret-right"></i>
								内容管理
						</a> <b class="arrow"></b></li>
						<li class=""><a href="${ctx}/publish/column"> <i class="menu-icon fa fa-caret-right"></i> 栏目管理
						</a> <b class="arrow"></b></li>
						
						<li class=""><a href="${ctx}/faq/list"> <i class="menu-icon fa fa-caret-right"></i> 留言板管理
						</a> <b class="arrow"></b></li>
						
					</ul></li>
			</security:authorize>


			<!-- 统计分析管理 start -->
			<security:authorize ifAnyGranted="ROLER_STAT">
				<li class=""><a href="#" class="dropdown-toggle"> <i class="menu-icon fa fa-list"></i> <span
						class="menu-text"> 统计中心</span> <b class="arrow fa fa-angle-down"></b>
				</a> <b class="arrow"></b>
					<ul class="submenu">
						<li class=""><a href="${ctx}/statistics/index" lib="statistics"> <i class="menu-icon fa fa-caret-right"></i>
								统计管理
						</a> <b class="arrow"></b></li>
					</ul></li>
			</security:authorize>

			<!-- 计费管理 start -->
			<security:authorize ifAnyGranted="ROLER_CENTER">
				<li class=""><a href="#" class="dropdown-toggle"> <i class="menu-icon fa fa-list"></i> <span
						class="menu-text"> 计费管理</span> <b class="arrow fa fa-angle-down"></b>
				</a> <b class="arrow"></b>
					<ul class="submenu">
						<li class=""><a href="${ctx}/charging/chargingmode"> <i class="menu-icon fa fa-caret-right"></i> 计费策略管理
						</a> <b class="arrow"></b></li>
					</ul></li>
			</security:authorize>

			<!-- 符合性测试管理 start -->
			<security:authorize ifAnyGranted="ROLER_CENTER">
				<li class=""><a href="#" class="dropdown-toggle"> <i class="menu-icon fa fa-list"></i> <span
						class="menu-text"> 符合性测试</span> <b class="arrow fa fa-angle-down"></b>
				</a> <b class="arrow"></b>
					<ul class="submenu">
						<li class=""><a href="${ctx}/testing/testapply"> <i class="menu-icon fa fa-caret-right"></i> 申请报告管理
						</a> <b class="arrow"></b></li>
						<li class=""><a href="${ctx}/testing/testtask"> <i class="menu-icon fa fa-caret-right"></i> 任务管理
						</a> <b class="arrow"></b></li>

					</ul></li>
			</security:authorize>


			<!-- 相关工具管理 start -->
			<security:authorize ifAnyGranted="ROLER_CENTER">
				<li class=""><a href="#" class="dropdown-toggle"> <i class="menu-icon fa fa-list"></i> <span
						class="menu-text"> 相关工具管理</span> <b class="arrow fa fa-angle-down"></b>
				</a> <b class="arrow"></b>
					<ul class="submenu">
						<li class=""><a href="${ctx}/tool/cnonix2cnmarcl"> <i class="menu-icon fa fa-caret-right"></i>
								CNONIX转换CNMARC
						</a> <b class="arrow"></b></li>
						<li class=""><a href="${ctx}/tool/excel2cnonix"> <i class="menu-icon fa fa-caret-right"></i>
								Excel转换CNONIX
						</a> <b class="arrow"></b></li>
					</ul></li>
			</security:authorize>
			<!-- 测试工具 start -->
			<!-- 
				<c:if test="${loginname=='centeruser' || loginname=='admin'}">
				<li class="">
					<a href="#" class="dropdown-toggle">
						<i class="menu-icon fa fa-list"></i>
						<span class="menu-text"> 系统单元测试工具</span>
						<b class="arrow fa fa-angle-down"></b>
					</a>
					<b class="arrow"></b>
					<ul class="submenu">
						<li class="">
							<a href="${ctx}/relay?url=tool/MessageExchange">
								<i class="menu-icon fa fa-caret-right"></i>
								数据交换测试
							</a>
							<b class="arrow"></b>
						</li>
					</ul>
				</li>
				</c:if>
				 -->
			<security:authorize ifAnyGranted="ROLER_SYS">
				<li class=""><a href="#" class="dropdown-toggle"> <i class="menu-icon fa fa-list"></i> <span
						class="menu-text"> 系统管理</span> <b class="arrow fa fa-angle-down"></b>
				</a> <b class="arrow"></b>
					<ul class="submenu">
						<security:authorize ifAnyGranted="ROLER_CENTER">
							<li class=""><a href="${ctx}/organization/list" lib="organization"> <i
									class="menu-icon fa fa-caret-right"></i> 机构管理
							</a> <b class="arrow"></b></li>
						</security:authorize>
						<li class=""><a href="${ctx}/user/list" lib="user"> <i class="menu-icon fa fa-caret-right"></i> 用户管理
						</a> <b class="arrow"></b></li>
						<li class=""><a href="${ctx}/role/list" lib="role"> <i class="menu-icon fa fa-caret-right"></i> 角色管理
						</a> <b class="arrow"></b></li>
						<li class=""><a href="${ctx}/authority/list" lib="authority"> <i class="menu-icon fa fa-caret-right"></i>权限管理
						</a> <b class="arrow"></b></li>
						<li class=""><a href="${ctx}/codeTable/list" lib="codeTable"> <i class="menu-icon fa fa-caret-right"></i>代码表管理
						</a> <b class="arrow"></b></li>
					</ul></li>
			</security:authorize>
			<!-- 
				<li class="">
					<a href="#" class="dropdown-toggle">
						<i class="menu-icon fa fa-desktop"></i>
						<span class="menu-text">
							UI &amp; Elements
						</span>

						<b class="arrow fa fa-angle-down"></b>
					</a>

					<b class="arrow"></b>

					<ul class="submenu">
						<li class="">
							<a href="#" class="dropdown-toggle">
								<i class="menu-icon fa fa-caret-right"></i>

								Layouts
								<b class="arrow fa fa-angle-down"></b>
							</a>

							<b class="arrow"></b>

							<ul class="submenu">
								<li class="">
									<a href="top-menu.html">
										<i class="menu-icon fa fa-caret-right"></i>
										Top Menu
									</a>

									<b class="arrow"></b>
								</li>

								<li class="">
									<a href="two-menu-1.html">
										<i class="menu-icon fa fa-caret-right"></i>
										Two Menus 1
									</a>

									<b class="arrow"></b>
								</li>

								<li class="">
									<a href="two-menu-2.html">
										<i class="menu-icon fa fa-caret-right"></i>
										Two Menus 2
									</a>

									<b class="arrow"></b>
								</li>

								<li class="">
									<a href="mobile-menu-1.html">
										<i class="menu-icon fa fa-caret-right"></i>
										Default Mobile Menu
									</a>

									<b class="arrow"></b>
								</li>

								<li class="">
									<a href="mobile-menu-2.html">
										<i class="menu-icon fa fa-caret-right"></i>
										Mobile Menu 2
									</a>

									<b class="arrow"></b>
								</li>

								<li class="">
									<a href="mobile-menu-3.html">
										<i class="menu-icon fa fa-caret-right"></i>
										Mobile Menu 3
									</a>

									<b class="arrow"></b>
								</li>
							</ul>
						</li>

						<li class="">
							<a href="typography.html">
								<i class="menu-icon fa fa-caret-right"></i>
								Typography
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="elements.html">
								<i class="menu-icon fa fa-caret-right"></i>
								Elements
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="buttons.html">
								<i class="menu-icon fa fa-caret-right"></i>
								Buttons &amp; Icons
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="content-slider.html">
								<i class="menu-icon fa fa-caret-right"></i>
								Content Sliders
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="treeview.html">
								<i class="menu-icon fa fa-caret-right"></i>
								Treeview
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="jquery-ui.html">
								<i class="menu-icon fa fa-caret-right"></i>
								jQuery UI
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="nestable-list.html">
								<i class="menu-icon fa fa-caret-right"></i>
								Nestable Lists
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="#" class="dropdown-toggle">
								<i class="menu-icon fa fa-caret-right"></i>

								Three Level Menu
								<b class="arrow fa fa-angle-down"></b>
							</a>

							<b class="arrow"></b>

							<ul class="submenu">
								<li class="">
									<a href="#">
										<i class="menu-icon fa fa-leaf green"></i>
										Item #1
									</a>

									<b class="arrow"></b>
								</li>

								<li class="">
									<a href="#" class="dropdown-toggle">
										<i class="menu-icon fa fa-pencil orange"></i>

										4th level
										<b class="arrow fa fa-angle-down"></b>
									</a>

									<b class="arrow"></b>

									<ul class="submenu">
										<li class="">
											<a href="#">
												<i class="menu-icon fa fa-plus purple"></i>
												Add Product
											</a>

											<b class="arrow"></b>
										</li>

										<li class="">
											<a href="#">
												<i class="menu-icon fa fa-eye pink"></i>
												View Products
											</a>

											<b class="arrow"></b>
										</li>
									</ul>
								</li>
							</ul>
						</li>
					</ul>
				</li>

				<li class="">
					<a href="#" class="dropdown-toggle">
						<i class="menu-icon fa fa-list"></i>
						<span class="menu-text"> Tables </span>

						<b class="arrow fa fa-angle-down"></b>
					</a>

					<b class="arrow"></b>

					<ul class="submenu">
						<li class="">
							<a href="tables.html">
								<i class="menu-icon fa fa-caret-right"></i>
								Simple &amp; Dynamic
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="jqgrid.html">
								<i class="menu-icon fa fa-caret-right"></i>
								jqGrid plugin
							</a>

							<b class="arrow"></b>
						</li>
					</ul>
				</li>

				<li class="">
					<a href="#" class="dropdown-toggle">
						<i class="menu-icon fa fa-pencil-square-o"></i>
						<span class="menu-text"> Forms </span>

						<b class="arrow fa fa-angle-down"></b>
					</a>

					<b class="arrow"></b>

					<ul class="submenu">
						<li class="">
							<a href="form-elements.html">
								<i class="menu-icon fa fa-caret-right"></i>
								Form Elements
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="form-elements-2.html">
								<i class="menu-icon fa fa-caret-right"></i>
								Form Elements 2
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="form-wizard.html">
								<i class="menu-icon fa fa-caret-right"></i>
								Wizard &amp; Validation
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="wysiwyg.html">
								<i class="menu-icon fa fa-caret-right"></i>
								Wysiwyg &amp; Markdown
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="dropzone.html">
								<i class="menu-icon fa fa-caret-right"></i>
								Dropzone File Upload
							</a>

							<b class="arrow"></b>
						</li>
					</ul>
				</li>

				<li class="">
					<a href="widgets.html">
						<i class="menu-icon fa fa-list-alt"></i>
						<span class="menu-text"> Widgets </span>
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="calendar.html">
						<i class="menu-icon fa fa-calendar"></i>

						<span class="menu-text">
							Calendar

							<span class="badge badge-transparent tooltip-error" title="2 Important Events">
								<i class="ace-icon fa fa-exclamation-triangle red bigger-130"></i>
							</span>

						</span>
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="gallery.html">
						<i class="menu-icon fa fa-picture-o"></i>
						<span class="menu-text"> Gallery </span>
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="#" class="dropdown-toggle">
						<i class="menu-icon fa fa-tag"></i>
						<span class="menu-text"> More Pages </span>

						<b class="arrow fa fa-angle-down"></b>
					</a>

					<b class="arrow"></b>

					<ul class="submenu">
						<li class="">
							<a href="profile.html">
								<i class="menu-icon fa fa-caret-right"></i>
								User Profile
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="inbox.html">
								<i class="menu-icon fa fa-caret-right"></i>
								Inbox
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="pricing.html">
								<i class="menu-icon fa fa-caret-right"></i>
								Pricing Tables
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="invoice.html">
								<i class="menu-icon fa fa-caret-right"></i>
								Invoice
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="timeline.html">
								<i class="menu-icon fa fa-caret-right"></i>
								Timeline
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="email.html">
								<i class="menu-icon fa fa-caret-right"></i>
								Email Templates
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="login.html">
								<i class="menu-icon fa fa-caret-right"></i>
								Login &amp; Register
							</a>

							<b class="arrow"></b>
						</li>
					</ul>
				</li>

				<li class="">
					<a href="#" class="dropdown-toggle">
						<i class="menu-icon fa fa-file-o"></i>

						<span class="menu-text">
							Other Pages

							<span class="badge badge-primary">5</span>

						</span>

						<b class="arrow fa fa-angle-down"></b>
					</a>

					<b class="arrow"></b>

					<ul class="submenu">
						<li class="">
							<a href="faq.html">
								<i class="menu-icon fa fa-caret-right"></i>
								FAQ
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="error-404.html">
								<i class="menu-icon fa fa-caret-right"></i>
								Error 404
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="error-500.html">
								<i class="menu-icon fa fa-caret-right"></i>
								Error 500
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="grid.html">
								<i class="menu-icon fa fa-caret-right"></i>
								Grid
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="blank.html">
								<i class="menu-icon fa fa-caret-right"></i>
								Blank Page
							</a>

							<b class="arrow"></b>
						</li>
					</ul>
				</li>
				 -->

		</ul>
		<!-- /.nav-list -->
		<!-- #section:basics/sidebar.layout.minimize -->
		<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
			<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left"
				data-icon2="ace-icon fa fa-angle-double-right"></i>
		</div>
	</div>
</body>
</html>
