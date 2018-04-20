var bookJson = {'产品形式':'form','产品形式细节':'form_detail','套书名':'set_title','副书名':'subtitle','并列书名':'parallel_title','引进版原书名':'introduce_title','书名':'title','ISBN':'isbn','定价':'price','作者':'author','出版日期':'publish_date','版次':'version'};
function getSearchModel1(key){
 var path = $("#path").val();
 var xmlHtml = "";
 var q = "<form  action="+path+"  id='formModel' name='formModel' method='post' class='form-horizontal'>";
 var z = "";
 var h = "<div class='form-actions'><button type='submit' class='btn btn-primary'>搜索</button> <button class='btn'>取消</button></div></form>";
            
           
         
 if(key=1){
	for(i in bookJson){
		    if(i=="出版日期"){
		    	/*z += "<div class='control-group'><label class='control-label'>"+i+"</label><div class='controls'><input style='width:270px;height:34px;z-index:9999999;'  class='form-control date-picker' id="+bookJson[i]+" name="+bookJson[i]+" "+
					" type='text' "+
					"data-date-format='yyyy-mm-dd' data-original-title='请选择日期！' tip='请选择日期！'data-placement='bottom'></div></div>"*/
		    	//z+= "<div class='input-append date form_datetime' data-date='2013-02-21T15:25:00Z'><input size='24' type='text' value='' readonly>"
		    }else if(i=="产品形式" || i=="产品形式细节"){
		    	z +="<div class='control-group'><label class='control-label' for="+bookJson[i]+">"+i+"</label><div class='controls'><select  style='width:270px;height:34px;'    name="+bookJson[i]+" id="+bookJson[i]+"  "+
					" data-placement='bottom' ' ></select></div></div>";
		    }else if(i=="定价"){
		    	z += "<div class='control-group'><label class='control-label' for='"+bookJson[i]+"'>"+i+"</label><div class='controls'><input style='width:270px;height:34px;'  type='text' name="+bookJson[i]+" id="+bookJson[i]+" "+
					"rel='validateMeasureUnit('price');'  data-original-title='只能为数字，最多两位小数,长度不能超过5！' reg='^$|^(0|[1-9]\d*)(\.\d{1,2})?$' tip='只能为数字，最多两位小数,长度不能超过9！' "+
						"data-placement='bottom'></div></div>"
		    }else{
		    	z += "<div class='control-group'><label class='control-label' for='input01'>"+i+"</label>"+
		    	"<div class='controls'><input type='text' class='input-xlarge' id="+bookJson[i]+" name="+bookJson[i]+">"+
		    	"</div></div>" 
		    }
			
	}
	xmlHtml = q+z+h;
	//$("#detailContent").html(xmlHtml);
 }	
 
 
 /*日期选择控件加载*/
	$('.date-picker').datepicker({
		autoclose : true,
		todayHighlight : true
	})
	
	//加载xml(出版状态,使用xml读取list64.xml)
	laodXml("publish_status", "List64.xml", "CodelistServlet");
	$("#publish_status").val("${book.publish_status}");
	
	//中图法分类
	laodXml("library_type", "CLC.xml", "CodelistServlet");
	$("#library_type").val("${book.library_type}");
	
	//营销分类 sale_type
	laodXml("sale_type", "salesCategory.xml", "CodelistServlet");
	$("#sale_type").val("${book.sale_type}");
	
	//高度单位
	laodXml("height_unit", "List50.xml", "CodelistServlet");
	$("#height_unit").val("${book.height_unit}");
	
	//宽度单位
	laodXml("width_unit", "List50.xml", "CodelistServlet");
	$("#width_unit").val("${book.width_unit}");
	
	//厚度单位
	laodXml("thick_unit", "List50.xml", "CodelistServlet");
	$("#thick_unit").val("${book.thick_unit}");
	
	//重量单位
	laodXml("weigth_unit", "List50.xml", "CodelistServlet");
	$("#weigth_unit").val("${book.weigth_unit}");
	
	//价格单位
	laodXml("price_unit", "currency.xml", "CodeXMLServlet");
	$("#price_unit").val("${book.price_unit}");
	
	//读者对象  list154.xml
	laodXml("reader", "List154.xml", "CodelistServlet");
	$("#reader").val("${book.reader}");

	// 出版国  GBT2659.xml
	laodXml("publish_country", "GBT2659.xml", "CodeXMLServlet");
	$("#publish_country").val("${book.publish_country}");
	
	//正文语种：GBt4880.2.xml
	laodXml("language", "GBt4880.2.xml", "CodeXMLServlet");
	$("#language").val("${book.language}");

	//加载xml(产品形式)
	laodXml("form", "List150.xml", "CodelistServlet");
	$("#form").val("${book.form}");

	//加载xml(产品形式细节)
	laodXml("form_detail", "List175.xml", "CodelistServlet");
	$("#form_detail").val("${book.form_detail}");
	
	//加载xml(内容类型)
	laodXml("content_type", "List81.xml", "CodelistServlet");
	$("#content_type").val("${book.content_type}");

	//加载xml(文件单位)
	laodXml("size_unit", "List24.xml", "CodelistServlet");
	$("#size_unit").val("${book.size_unit}");
	
	//加载xml(操作系统)
	laodXml("system", "List176.xml", "CodelistServlet");
	$("#system").val("${book.system}");
	
	
	//加载xml(保护技术)
	laodXml("protection", "List144.xml", "CodelistServlet");
	$("#protection").val("${book.protection}");
		
}
$(".form_datetime").datetimepicker({
    format: "yyyy-mm-dd hh:ii",
    autoclose: true,
    todayBtn: true,
    startDate: "2016-07-02 10:00",
    minuteStep: 10
});    
