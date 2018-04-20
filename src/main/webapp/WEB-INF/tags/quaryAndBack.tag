<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="submitButton" type="java.lang.String" required="true"%>
<%@ attribute name="submitOnclick" type="java.lang.String" required="true"%>
<%@ attribute name="backOnclick" type="java.lang.String" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	if(submitButton==null || "".equals(submitButton)){
		submitButton="submit";
	}
	if(submitOnclick==null || "".equals(submitOnclick)){
		submitOnclick="";
	}
	if(backOnclick==null || "".equals(backOnclick)){
		backOnclick="javascript:history.back();";
	}
%>
<html>
<head>
</head>
<div class="clearfix form-actions" style="background-color:#FFF;border-top:0px solid #E5E5E5">
	<div class="col-md-offset-3 col-md-9">
		<button class="btn btn-info" id="submitButton" type="<%=submitButton %>" onclick="<%=submitOnclick%>">
			<i class="ace-icon fa fa-check bigger-110"></i> 查询
		</button>

		&nbsp; &nbsp; &nbsp;
		<button class="btn" type="button"  onclick="<%=backOnclick%>">
			<i class="ace-icon fa fa-undo bigger-110"></i> 返回
		</button>
	</div>
</div>
</html>
