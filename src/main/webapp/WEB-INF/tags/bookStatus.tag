<%@tag import="com.litc.common.util.StatusConstant"%>
<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="status" type="java.lang.Integer" required="true"%>
<%
	String str = "";
	if(status!=null){
		if((status&StatusConstant.BOOK_CHECK_ONIX_COMPLETE)==StatusConstant.BOOK_CHECK_ONIX_COMPLETE){
			if((status&StatusConstant.BOOK_CHECK_ONIX)==StatusConstant.BOOK_CHECK_ONIX){
				str = "<i class='ace-icon fa fa-check green bigger-130'></i>";		
			}else{
				str = "<i class='ace-icon glyphicon glyphicon-remove red bigger-130'></i>";		
			}
		}else{
			str = "<i class='ace-icon fa fa-spinner fa-spin blue bigger-130'></i>";
		}
	}
	//pageContext.setAttribute("str",str);
	out.print(str);
%>
