<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="backOnclick" type="java.lang.String" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
if(backOnclick==null || "".equals(backOnclick)){
	backOnclick="javascript:history.back();";
}
%>
<html>
<head>
</head>
<div class="clearfix form-actions" style="background-color:#FFF;border-top:0px solid #E5E5E5" id="pageDiv" name="pageDiv">
	<div class="col-md-offset-3 col-md-9">
		<button class="btn btn-info" type="button"
			onclick="<%=backOnclick%>">
			<i class="ace-icon fa fa-undo bigger-110"></i> 返回
		</button>

	</div>
</div>

<script type="text/javascript">
	<c:if test="${backParam=='3'}">
		$("#pageDiv").hide();
	</c:if>
</script>
</html>
