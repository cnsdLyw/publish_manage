<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="dataPage" type="org.springframework.data.domain.Page" required="true"%>
<%@ attribute name="paginationSize" type="java.lang.Integer" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%
	int current =  dataPage.getNumber() + 1;
	int begin = Math.max(1, current - paginationSize/2);
	int end = Math.min(begin + (paginationSize - 1), dataPage.getTotalPages());
	long totalCount = dataPage.getTotalElements();
	request.setAttribute("totalCount", totalCount);
	request.setAttribute("current", current);
	request.setAttribute("begin", begin);
	request.setAttribute("end", end);
	request.setAttribute("total", dataPage.getTotalPages());
	
%>
<html>
<head>
<script type="text/javascript">
	function getPage(v){
		var page = parseInt(v) - 1;
		//alert(document.getElementById("twoPageNo").value);
	    document.getElementById("pageNo").value=page;
		document.getElementById("queryForm").submit();
	}
	//监听回车事件
	document.onkeydown=function() {

		if(event.keyCode==13) {
			getValue();
			
		}
	}
	function getValue(){
		var flag = $("#flag").val();
		var foo= $("input[class='total']"); 
		var foo2=$("input[class='val']"); 
		var total = $(".total").val();
		var va = $(".val").val();
		$(foo).each(function(i,val) {
			if(i==0 && flag=="1"){
				total = val.value;
			}
			if(i==1 && flag=="2"){
				total = val.value;
			}
		})
		$(foo2).each(function(i,val) {
			if(i==0 && flag=="1"){
				va = val.value;
			}
			if(i==1 && flag=="2"){
				va = val.value;
				
			}
		})
		var value = va-1;
		//alert("total:"+total+"val:"+va+"value:"+value);
		if(value<0){
			value = 0;
			//alert("\\b"+value);
		}
		if(value>=total){
			value = total-1;
			//alert("\\a"+value);
		}
		if(value<0){
			value = 0;
			//alert("\\b"+value);
		}
		//alert("flag:"+flag+"value:"+value);
		if(flag=="1"){
			$(".pageNo").val(value);
			$("#pageNo").val(value);
			$("#flag").val(1);
		}else if(flag=="2"){
			$(".pageNo").val(value);
			$("#pageNo").val(value);
			$(".flag").val(2);
		}else{
			$("#pageNo").val(value);
		}
		
		document.getElementById("queryForm").submit();
	}
</script>
</head>
	<div class="row" style="background-color:#FFF;border-top:0px solid #E5E5E5;border-bottom:0px solid #e0e0e0">
		<div class="col-xs-12">
			<div class="dataTables_paginate paging_simple_numbers" id="dynamic-table_paginate" >
				<ul class="pagination">
				  &nbsp;&nbsp;&nbsp;<span>到第<input type="text" class="val" id="val"  value="${current }" style="width:40px; height:25px;"/>页&nbsp;&nbsp;&nbsp;<a href="#" style="text-decoration: none;"  id="qdBtn" onclick="getValue()">确定</a></span>
					<% if(dataPage.hasPrevious()) {%>
					    <span style="line-height:28px;">&nbsp;&nbsp;找到  ${totalCount} 条记录，共${total}页</span>
					    <input type="hidden" class="total" id="total" value="${total }"/>
						<li class="paginate_button previous" aria-controls="dynamic-table" tabindex="0"id="dynamic-table_previous">
						    
							<a href="javascript:void(0);" onclick="getPage(1)">首页</a>
						</li>
						<li class="paginate_button previous" aria-controls="dynamic-table" tabindex="0"id="dynamic-table_previous">
							<a href="javascript:void(0);" onclick="getPage(${current-1})">上一页</a>
						</li>
					<%} else { %>
					    <span style="line-height:28px;">&nbsp;&nbsp;找到  ${totalCount} 条记录，共${total}页</span>
					    <input type="hidden" class="total" id="total"  value="${total }"/>
						<li class="paginate_button previous disabled" aria-controls="dynamic-table" tabindex="0"id="dynamic-table_previous">
							<a href="javascript:void(0);">首页</a>
						</li>
						<li class="paginate_button previous disabled" aria-controls="dynamic-table" tabindex="0"id="dynamic-table_previous">
							<a href="javascript:void(0);">上一页</a>
						</li>
					<%} %>
					
					<c:forEach var="i" begin="${begin }" end="${end }" step="1">
						<c:choose>
            					<c:when test="${i == current}">
								<li class="paginate_button active" aria-controls="dynamic-table" tabindex="0">
									<a href="javascript:void(0);">${i}</a>
								</li>
							</c:when>
              					<c:otherwise>
								<li class="paginate_button " aria-controls="dynamic-table" tabindex="0">
									<a href="javascript:void(0);" onclick="getPage(${i})">${i}</a>
								</li>
								</c:otherwise>
						</c:choose>
					</c:forEach>
					
					 <% if (dataPage.hasNext()){%>
						<li class="paginate_button next" aria-controls="dynamic-table" tabindex="0" id="dynamic-table_next">
							<a href="javascript:void(0);" onclick="getPage(${current+1})">下一页</a>
						</li>
						<li class="paginate_button next" aria-controls="dynamic-table" tabindex="0" id="dynamic-table_next">
							<a href="javascript:void(0);" onclick="getPage(${total})">尾页</a>
						</li>
					    <%} else {%>
						<li class="paginate_button next disabled" aria-controls="dynamic-table" tabindex="0" id="dynamic-table_next">
							<a href="javascript:void(0);">下一页</a>
						</li>
						<li class="paginate_button next disabled" aria-controls="dynamic-table" tabindex="0" id="dynamic-table_next">
							<a href="javascript:void(0);">尾页</a>
						</li>
					 <%} %>
					  
				</ul>
			</div>
		</div>
	</div>
</html>
