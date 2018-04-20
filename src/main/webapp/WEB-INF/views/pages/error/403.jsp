<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<head>
	<title>403</title>
	<meta name="keywords" content="" />
	<meta name="description" content="" />
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<%@include file="../../common/meta.html"%>
</head>
<body class="no-skin">
	<div class="main-container" id="main-container">
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
												<i class="ace-icon fa fa-sitemap"></i>
												403
											</span>
											access Forbidden
										</h1>

										<hr />
										<h3 class="lighter smaller">客户端证书已过期或尚未生效，请重新登录……</h3>

										<div>
											<div class="space"></div>
											<h4 class="smaller">敬请期待！</h4>

										</div>

										<hr />
										<div class="space"></div>

										<%-- <div class="center">
											<a href="javascript:history.back()" class="btn btn-grey">
												<i class="ace-icon fa fa-arrow-left"></i>
												返回
											</a>

											<a href="${ctx }/index" class="btn btn-primary">
												<i class="ace-icon fa fa-tachometer"></i>
												首页
											</a>
										</div> --%>
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