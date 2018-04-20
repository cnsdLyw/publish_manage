
function comboSelect(rootUrl, xmlName, className){
	$.ajax({
		type : "POST",
		url : rootUrl + className,
		dataType : 'json',
		async : false,
		data : {
			"xmlName" : xmlName
		},
		success : function(handles) {
			var tag_data = handles; 
			switch(xmlName)
			{
			case "GBT2659.xml":
				$('#comboSelect1').bComboSelect({
					showField : 'name',
					keyField : 'id',
					data : tag_data
				});
				break;
			case "CLC.xml":
				$('#comboSelect2').bComboSelect({
					showField : 'name',
					keyField : 'id',
					data : tag_data,
					//格式化显示项目，提供源数据进行使用
					formatItem : function(data){
						return data.id + '(' + data.name + ')';
					}
				});
				break;
			case "salesCategory.xml":
				$('#comboSelect3').bComboSelect({
					showField : 'name',
					keyField : 'id',
					data : tag_data
				});
				break;
			case "GBt4880.2.xml":
				$('#comboSelect4').bComboSelect({
					showField : 'name',
					keyField : 'id',
					data : tag_data
				});
				break;
			}
		}
	});
}	
