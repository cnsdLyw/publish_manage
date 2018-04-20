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
				if(channelpath=="web21/loginfo/operate"){
					channelpath=channelpath.replace("operate", "list");
				}
				
				channelpath = "/" + channelpath;
			}
			isHref = $("a[href='" + channelpath + "']").attr("href");
			// 			alert(isHref+"=="+channelpath);
			if (undefined == isHref) {
				var ctxStr = "${ctx}";
				channelpath = channelpath.replace(ctxStr, "");
				var currentMode = channelpath.substring(1, channelpath.indexOf("/", 2));
				if(currentMode=="bookHistory"){//图书product记录
					var flag = "${flag}";
					if(flag=="cbs"){
						currentMode = "companybook";
					}else if(flag=="tsg"){
						currentMode = "librarybook";
					}else if(flag=="fx"){
						currentMode = "issuebook";
					}
				}
				
				$("a[lib='" + currentMode + "']").parent().addClass("active");
				$("a[lib='" + currentMode + "']").parent().parent().parent()
						.addClass("active open");
				var islibHref=$("a[lib='" + currentMode + "']");
				if (islibHref.length==0) {
					var lastMode = channelpath.substring(channelpath.lastIndexOf("/")+1);
					currentMode=currentMode+"_"+lastMode
					$("a[lib='" + currentMode + "']").parent().addClass("active");
					$("a[lib='" + currentMode + "']").parent().parent().parent()
							.addClass("active open");
				}
				
				
			} else {
				$("a[href='" + channelpath + "']").parent().addClass("active");
				$("a[href='" + channelpath + "']").parent().parent().parent()
						.addClass("active open");
			}

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
				
			<security:authorize ifAnyGranted="ROLER_MESSAGE_SEND,ROLER_MESSAGE_RECEIVE,ROLER_MESSAGE_MANAGE">
				<li class=""><a href="#" class="dropdown-toggle"> <i class="menu-icon fa fa-envelope"></i> <span
						class="menu-text"> 消息中心</span> <b class="arrow fa fa-angle-down"></b>
				</a> <b class="arrow"></b>
					<ul class="submenu">
						<!-- 中心平台不应该有发信箱 -->
						<c:if test="${login_role=='1'||login_role=='2'||login_role=='3'||login_role=='5'}">
							<security:authorize ifAnyGranted="ROLER_MESSAGE_SEND">	
								<li class=""><a href="${ctx}/message/list?columnId=21&type=1" lib="message1"> <i class="menu-icon fa fa-caret-right"></i>
									发信箱
								</a> <b class="arrow"></b></li>
							</security:authorize>
							<security:authorize ifAnyGranted="ROLER_MESSAGE_RECEIVE">	
								<li class=""><a href="${ctx}/message/list?columnId=22&type=2" lib="message2"> <i class="menu-icon fa fa-caret-right"></i>
									收信箱
								</a> <b class="arrow"></b></li>
							</security:authorize>
						</c:if>
						<c:if test="${login_role=='0'}">
							<security:authorize ifAnyGranted="ROLER_MESSAGE_MANAGE">
								<li class=""><a href="${ctx}/message/list?columnId=23&type=2" lib="message"> <i class="menu-icon fa fa-caret-right"></i>
									消息管理
								</a> <b class="arrow"></b></li>
							</security:authorize>
						</c:if>
						
					</ul></li>
			</security:authorize>
			<li class="">
					<a href="#" class="dropdown-toggle">
						<i class="menu-icon fa fa-money"></i>
						<span class="menu-text"> 交易平台</span>
						<b class="arrow fa fa-angle-down"></b>
					</a>

					<b class="arrow"></b>

					<ul class="submenu">
						<li class="">
							<a href="${ctx}/product/list" lib="product">
								<i class="menu-icon fa fa-caret-right"></i>
								产品信息
							</a>
							<b class="arrow"></b>
						</li>
						<li class="">
							<a href="${ctx}/transaction/index" lib="transaction">
								<i class="menu-icon fa fa-caret-right"></i>
								交易总揽
							</a>
							<b class="arrow"></b>
						</li>
						<li class="">
							<a href="jqgrid.html">
								<i class="menu-icon fa fa-caret-right"></i>
								资产登记
							</a>
							<b class="arrow"></b>
						</li>
						<li class="">
							<a href="jqgrid.html">
								<i class="menu-icon fa fa-caret-right"></i>
								应收账款
							</a>
							<b class="arrow"></b>
						</li>
					</ul>
				</li>
				
			<li class="">
					<a href="#" class="dropdown-toggle">
						<i class="menu-icon fa fa-desktop"></i>
						<span class="menu-text"> 供应商系统 </span>
						<b class="arrow fa fa-angle-down"></b>
					</a>

					<b class="arrow"></b>

					<ul class="submenu">
						<li class="">
							<a href="${ctx}/supplier/list" lib="supplier">
								<i class="menu-icon fa fa-caret-right"></i>
								供应商
							</a>
							<b class="arrow"></b>
						</li>
						<li class="">
							<a href="${ctx}/customer/list" lib="customer">
								<i class="menu-icon fa fa-caret-right"></i>
								客户登记
							</a>
							<b class="arrow"></b>
						</li>
					</ul>
				</li>
					
			<li class="">
					<a href="#" class="dropdown-toggle">
						<i class="menu-icon fa fa-desktop"></i>
						<span class="menu-text"> 内部管理 </span>
						<b class="arrow fa fa-angle-down"></b>
					</a>

					<b class="arrow"></b>

					<ul class="submenu">
						<li class="">
							<a href="${ctx}/financeRecord/list" lib="financeRecord">
								<i class="menu-icon fa fa-caret-right"></i>
								财务信息
							</a>
							<b class="arrow"></b>
						</li>
						<li class="">
							<a href="${ctx}/customer/list" lib="customer">
								<i class="menu-icon fa fa-caret-right"></i>
								客户登记
							</a>
							<b class="arrow"></b>
						</li>
						<li class="">
							<a href="${ctx}/asset/list" lib="asset">
								<i class="menu-icon fa fa-caret-right"></i>
								资产登记
							</a>
							<b class="arrow"></b>
						</li>
						<li class="">
							<a href="${ctx}/receivables/list" lib="receivables"> 
								<i class="menu-icon fa fa-caret-right"></i>
								应收账款
							</a>
							<b class="arrow"></b>
						</li>
					</ul>
				</li>
					
			<!-- 信息发布管理 start
			<security:authorize ifAnyGranted="ROLER_CONTENT_PUBLIC_PICTURE,ROLER_CONTENT_PUBLIC_NEWS,ROLER_CONTENT_PUBLIC_LINK,ROLER_CONTENT_PUBLIC_FILE,ROLER_CONTENT_PUBLIC_FAQ">
				<li class=""><a href="#" class="dropdown-toggle"> <i class="menu-icon fa fa-list-alt"></i> <span
						class="menu-text"> 信息发布管理</span> <b class="arrow fa fa-angle-down"></b>
				</a> <b class="arrow"></b>
					<ul class="submenu">
						<security:authorize ifAnyGranted="ROLER_CONTENT_PUBLIC_PICTURE">	
							<li class=""><a href="${ctx}/content/list/1?columnId=71" lib="content_1"> <i class="menu-icon fa fa-caret-right"></i>
									首页大图管理
							</a> <b class="arrow"></b></li>
						</security:authorize>
						<security:authorize ifAnyGranted="ROLER_CONTENT_PUBLIC_NEWS">		
							<li class=""><a href="${ctx}/content/list/2?columnId=72" lib="content_2"> <i class="menu-icon fa fa-caret-right"></i>
									行业信息管理
							</a> <b class="arrow"></b></li>
						</security:authorize>
						<security:authorize ifAnyGranted="ROLER_CONTENT_PUBLIC_LINK">		
							<li class=""><a href="${ctx}/content/list/3?columnId=73" lib="content_3"> <i class="menu-icon fa fa-caret-right"></i>
									友情链接管理
							</a> <b class="arrow"></b></li>
						</security:authorize>
						<security:authorize ifAnyGranted="ROLER_CONTENT_PUBLIC_FILE">		
							<li class=""><a href="${ctx}/content/list/4?columnId=74" lib="content_4"> <i class="menu-icon fa fa-caret-right"></i>
									文件发布管理
							</a> <b class="arrow"></b></li> 
						</security:authorize>
						<security:authorize ifAnyGranted="ROLER_CONTENT_PUBLIC_FAQ">		
							<li class=""><a href="${ctx}/faq/list?columnId=75" lib="faq"> <i class="menu-icon fa fa-caret-right"></i> 留言板管理
							</a> <b class="arrow"></b></li>
						</security:authorize>
						
						<security:authorize ifAnyGranted="ROLER_CONTENT_QUESTION">
							<li class=""><a href="${ctx}/commonQuestion/list?columnId=74" lib="commonQuestion"> <i class="menu-icon fa fa-caret-right"></i>
									常见问题管理
							</a> <b class="arrow"></b></li> 
						</security:authorize>
							<!-- 这个是注释起来的						
								<li class=""><a href="${ctx}/columns/list"> <i class="menu-icon fa fa-caret-right"></i> 栏目管理
								</a> <b class="arrow"></b></li>
	 						
					</ul></li>
			</security:authorize>
 -->

			<security:authorize ifAnyGranted="ROLER_FILE,ROLER_STORAGE_DEVICE,ROLER_CONFIGURE,ROLER_CLASS,ROLER_TASK,ROLER_ORGANIZATION,
				ROLER_ORGANIZATION_APPLY,ROLER_DATA_EXCHANGE,ROLER_STANDARD_ORGANIZATION,ROLER_SYS_ORGANIZATION,ROLER_SYS_USER,ROLER_SYS_ROLE,ROLER_SYS_AUTHORITY,ROLER_SYS_CODETABLE">
				<li class=""><a href="#" class="dropdown-toggle"><i class="menu-icon glyphicon glyphicon-cog"></i> <span
						class="menu-text"> 系统管理</span> <b class="arrow fa fa-angle-down"></b>
				</a> <b class="arrow"></b>
					<ul class="submenu">
						<security:authorize ifAnyGranted="ROLER_FILE">
							<li class="">
								<a href="${ctx}/jetsenFiles/list?columnId=91" lib="jetsenFiles">
									<i class="menu-icon fa fa-caret-right"></i>文件管理</a><b class="arrow"></b>
							</li>
						</security:authorize>
						<security:authorize ifAnyGranted="ROLER_STORAGE_DEVICE">
							<li class="">
								<a href="${ctx}/storageDevice/list?columnId=92" lib="system/storageDevice/storageDevice">
									<i class="menu-icon fa fa-caret-right"></i>存储设备管理</a><b class="arrow"></b>
							</li>
						</security:authorize>
						<security:authorize ifAnyGranted="ROLER_CONFIGURE">
							<li class="">
								<a href="${ctx}/sysConfirure/index?columnId=93" lib="sysConfigure">
									<i class="menu-icon fa fa-caret-right"></i>配置管理</a><b class="arrow"></b>
							</li>
						</security:authorize>
						<security:authorize ifAnyGranted="ROLER_CLASS">
							<li class="">
								<a href="${ctx}/classification/index?columnId=94" lib="classification">
									<i class="menu-icon fa fa-caret-right"></i>分类管理</a><b class="arrow"></b>
							</li>
						</security:authorize>
						<security:authorize ifAnyGranted="ROLER_TASK">
							<li class="">
								<a href="${ctx}/jetsenServiceTask/list?columnId=95" lib="jetsenServiceTask">
									<i class="menu-icon fa fa-caret-right"></i>任务管理</a><b class="arrow"></b>
							</li>
						</security:authorize>
						<!-- 
						<security:authorize ifAnyGranted="ROLER_ORGANIZATION">
							<li class="">
								<a href="${ctx}/organization/list?columnId=96" lib="organization"> 
									<i class="menu-icon fa fa-caret-right"></i> 机构信息</a> <b class="arrow"></b>
							</li>
						</security:authorize>
						-->
						<security:authorize ifAnyGranted="ROLER_ORGANIZATION_APPLY">
							<li class="">
								<a href="${ctx}/organizationApply/list?columnId=97" lib="organizationApply"> 
								<i class="menu-icon fa fa-caret-right"></i> 机构申请管理</a> <b class="arrow"></b>
							</li>
						</security:authorize>
						<security:authorize ifAnyGranted="ROLER_DATA_EXCHANGE">
							<li class=""><a href="${ctx}/jmxRest/list?columnId=98" lib="jmxRest"> 
								<i class="menu-icon fa fa-caret-right"></i> 数据交换管理</a> <b class="arrow"></b>
							</li>
						</security:authorize>
						<security:authorize ifAnyGranted="ROLER_STANDARD_ORGANIZATION">
							<li class=""><a href="${ctx}/organizationLibrary/list?columnId=99" lib="organizationLibrary"> <i
									class="menu-icon fa fa-caret-right"></i> 标准机构代码</a> <b class="arrow"></b>
							</li>
						</security:authorize>
							<security:authorize ifAnyGranted="ROLER_SYS_ORGANIZATION">
							<li class=""><a href="${ctx}/organization/list?columnId=910" lib="organization"> <i
									class="menu-icon fa fa-caret-right"></i> 机构管理</a> <b class="arrow"></b>
							</li>
						</security:authorize>
						<security:authorize ifAnyGranted="ROLER_SYS_USER">
							<li class=""><a href="${ctx}/user/list?columnId=911" lib="user"> 
								<i class="menu-icon fa fa-caret-right"></i>用户管理</a> <b class="arrow"></b>
							</li>
						</security:authorize>
						<security:authorize ifAnyGranted="ROLER_SYS_ROLE">
							<li class=""><a href="${ctx}/role/list?columnId=912" lib="role"> 
								<i class="menu-icon fa fa-caret-right"></i>角色管理</a> <b class="arrow"></b>
							</li>
						</security:authorize>
						<security:authorize ifAnyGranted="ROLER_SYS_AUTHORITY">
							<li class=""><a href="${ctx}/authority/list?columnId=913" lib="authority">
								<i class="menu-icon fa fa-caret-right"></i>权限管理</a> <b class="arrow"></b>
							</li>
						</security:authorize>
						<!-- 
						 <security:authorize ifAnyGranted="ROLER_LIBRARY">
						    <li class="">
						    	<a href="${ctx}/librarybook/schedule" lib="jmxRest"> 
						    		<i class="menu-icon fa fa-caret-right"></i> 自动交换管理</a> <b class="arrow"></b>
							</li>
						 </security:authorize>
						 <security:authorize ifAnyGranted="ROLER_ISSUE">
							<li class=""><a href="${ctx}/issuebook/schedule" lib="jmxRest"> 
								<i class="menu-icon fa fa-caret-right"></i> 自动交换管理</a> <b class="arrow"></b>
							</li>
						 </security:authorize> 
						 -->
					</ul></li>
			</security:authorize>
			
			<security:authorize ifAnyGranted="ROLER_SYS_LOG">
				<li class=""><a href="#" class="dropdown-toggle"><i class="menu-icon glyphicon glyphicon-list-alt"></i> <span
						class="menu-text"> 日志管理</span> <b class="arrow fa fa-angle-down"></b>
				</a> <b class="arrow"></b>
					<ul class="submenu">
						<security:authorize ifAnyGranted="ROLER_SYS_LOG">
							<li class=""><a href="${ctx}/loginfo/list?columnId=101" lib="loginfo"> <i class="menu-icon fa fa-caret-right"></i>
									日志管理
							</a> <b class="arrow"></b>
							</li>
						</security:authorize>
					</ul>
				</li>
			</security:authorize>
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
