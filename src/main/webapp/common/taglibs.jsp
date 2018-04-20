<%@page import="com.litc.common.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="rctx" value="<%=ConfigurationUtil.resource_AccessAddress %>"/>
<c:set var="fileHomeUrl" value="<%=Constant.SERVER_FILE_HOME_URL%>"/>

 