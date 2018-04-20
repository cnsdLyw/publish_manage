function sort(obj,currentForm,orderType, sortType) {//orderType  sortType
		if ($("#orderType").val() == orderType) {
			if ($("#sortType").val() == "") {
				$("#sortType").val(sortType);
			}
			else if ($("#sortType").val() == "desc") {
				$("#sortType").val("asc");
			}
			else if ($("#sortType").val() == "asc") {
				$("#sortType").val("desc");
			}
		}
		else {
			$("#orderType").val(orderType);
			$("#sortType").val(sortType);
		}
	
		$("#"+currentForm).submit();
	}