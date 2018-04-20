<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.io.StringWriter"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	Exception ex = (Exception)request.getAttribute("ex");
%>
<!DOCTYPE html>
<head>
	<title>404</title>
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<%@include file="../../common/meta.html"%>
	<script type="text/javascript">
		function showErrorDiv(){
			$("#errorDiv").show();	
		}
		function hideErrorDiv(){
			$("#errorDiv").hide();
		}
	</script>
	
</head>
<body class="no-skin">
	<%@include file="../../common/top.jsp"%>
	<div class="main-container" id="main-container">
		<%@include file="../../common/left.jsp"%>
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
	
							<!-- #section:pages/error -->
							<div class="error-container">
								<div class="well">
									<h1 class="grey lighter smaller">
										<span class="blue bigger-125">
											<i class="ace-icon fa fa-random"></i>
											500
										</span>
										Something Went Wrong
									</h1>
	
									<hr />
									<h3 class="lighter smaller">
										后台报错了，请联系管理员！
										<i class="ace-icon fa fa-wrench icon-animated-wrench bigger-125"></i>
									</h3>
	
									<div class="space"></div>
	
									<div>
										<h4 class="lighter smaller">查看错误详情:</h4>
	
										<ul class="list-unstyled spaced inline bigger-110 margin-15">
											<li>
												<i class="ace-icon fa fa-hand-o-right blue"></i>
												异常: <%=ex.getClass().getName() %>
											</li>
	
											<li>
												<i class="ace-icon fa fa-hand-o-right blue"></i>
												异常信息: ${pageContext.exception}
											</li>
											<li>
												<i class="ace-icon fa fa-hand-o-right blue"></i>
												异常发生页面：${pageContext.errorData.requestURI}
											</li>
											<li>
												<i class="ace-icon fa fa-hand-o-right blue"></i>
												异常栈日志:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<button class="btn btn-sm btn-primary " type="button"  onclick="javascript:showErrorDiv()">展示</button>
												<button class="btn btn-sm btn-primary " type="button"  onclick="javascript:hideErrorDiv()">隐藏</button>
												<div class="col-md-12">
												<div class="col-md-1">&nbsp;</div>
												<div class="col-md-11" id="errorDiv" name="errorDiv" style="display:none;">
												<%
											         StringWriter sw=new StringWriter();
											         PrintWriter pw=new PrintWriter(sw);
											         ex.printStackTrace(pw);
											         out.println(sw.toString().replaceAll("\n", "<BR/>")); 
										         %>
										         </div>
										         </div>
											</li>
										</ul>
									</div>
	
									<hr />
									<div class="space"></div>
	
									<div class="center">
										<a href="javascript:history.back()" class="btn btn-grey">
											<i class="ace-icon fa fa-arrow-left"></i>
											返回
										</a>

										<a href="${ctx }/index" class="btn btn-primary">
											<i class="ace-icon fa fa-tachometer"></i>
											首页
										</a>
									</div>
								</div>
							</div>
	
							<!-- /section:pages/error -->
	
							<!-- PAGE CONTENT ENDS -->
						</div><!-- /.col -->
					</div><!-- /.row -->
				</div>
			</div>
		</div><iframe id="wordframe" style="display:none"></iframe>
	<%@include file="../../common/footer.jsp"%>
	</div>
	<%@include file="../../common/javascript.html"%>
</body>
</html>