// JavaScript Document
/*
var basePath = '';
var sc=document.getElementsByTagName('script'); 
var lesc = sc.length;
for(var sl=0; sl<lesc; sl++){
	var basePath = sc[sl].src;
	var pos = basePath.indexOf("basePath=");
	if(pos>0){
		basePath = basePath.substring(pos+9);   
		pos = basePath.indexOf("&");
		if(pos > 0){
			basePath = basePath.substring(0,pos);
		}
		break;
	}
}


// 路径配置
   require.config({
       paths: {
           echarts: basePath + "/resources/build/dist"
       }
   });
 */
// 路径配置
   require.config({
       paths: {
           echarts: '../resources/build/dist'
       }
   });
   
//趋势分析
function statTrend(){
	$("#statText").html('趋势分析');
	$("#statFlag").val(1);
	$("#main").css({height:"450px"});
	var strMonth;
	var strDG;
	var strKC;
	var strXS;
	var strJY;
	var flag;
	$.ajax( {  
        type : "post",
        async: false,
        url : "../statistics/trend/",
        data:{id:$("#chooseId").val(),year:$("#chooseYear").val(),month:$("#chooseMonth").val(),chooseStatType:$("#chooseStatType").val()},
        dataType:"json",  
        success : function(data) {
       		strMonth = data.strMonth.split(",");
       		strDG = eval(data.strDG);
       		strKC = eval(data.strKC);
       		strXS = eval(data.strXS);
       		strJY = eval(data.strJY);
       		flag = data.flag;
        }  
    });
//	function(){
//		if(flag == "four"){
//        	data:['订购','销量','库存','借阅']
//        }else if(flag == "three"){
//        	data:['订购','销量','库存']
//        }else if(flag = "one"){
//        	data:['借阅']
//        }
//	}
	 // 使用
    require([
        'echarts',
        'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
        'echarts/chart/bar'
    ],
    function (ec) {
    	var dg = "";
    	var xl = "";
    	var kc = "";
    	var jy = "";
    	var dgfl = true;
        var jyfl = false;
        var jyStr = "";
        var kcStr = {
	            name:'库存',
	            type:'line',
	            data:strKC
	        };
        if(strJY==null || strJY=="undified"){
        	jyStr = {
        			name:'',
        			type:'line',
        			data:''
        	};
        }else{
        	jyStr = {
            		name:'借阅',
    	            type:'line',
    	            data:strJY
            };
        }
    	// 基于准备好的dom，初始化echarts图表
		if(flag == "four"){
	        dg = "订购";
		    xl = "销量";
		    kc = "库存";
		    jy = "借阅";
		}else if(flag == "three"){
			dg = "订购";
		    xl = "销量";
		    kc = "库存";
	    }else if(flag = "one"){
		    jy = "借阅";
		    dgfl = false;
		    jyfl = true;
	    }
        var myChart = ec.init(document.getElementById('main'),'macarons');
        var option = {
		    title : {
		        text: '按趋势分析统计',
		        subtext: '趋势分析'
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		    	selectedMode:"multiple",
		    	selected:{
		            '订购' : dgfl,
		            '销量' : false,
		            '库存' : false,
		            '借阅' : jyfl,
		        },
		        data:[dg,xl,kc,jy]
		        
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: false},
		            dataView : {show: true, readOnly: false},
		            magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            boundaryGap : false,
		           	data : strMonth
		            //data:[strMonth]
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            axisLabel : {
		                formatter: '{value}'
		            }
		        }
		    ],
		    series :
		    	function(){
		        var serie_=[];
		        if(strDG!=null && strDG!="undified"){
		        	var itemDG={
		 		        	name:'订购',
		 		        	type:'line',
		 		        	data:strDG,
			           }
		        	serie_.push(itemDG);
		        }
		        if(strKC!=null && strKC!="undified"){
		         var itemKC={
	 		        	name:'库存',
	 		        	type:'line',
	 		        	data:strKC,
		           }
		         serie_.push(itemKC);
		        }
		        
		         if(strXS!=null && strXS!="undified"){
		         var itemXS={
	 		        	name:'销量',
	 		        	type:'line',
	 		        	data:strXS,
		           }
		         serie_.push(itemXS);
		         }
		         
		        if(strJY!=null && strJY!="undified"){
		        	var itemJY={
		 		        	name:'借阅',
		 		        	type:'line',
		 		        	data:strJY,
			           }
		        	serie_.push(itemJY);
		        }
		         
		         
		         
		        
		      return serie_;
		   }()
//		    	[
//		        {
//		            name:'订购',
//		            type:'line',
//		            data:strDG//[11, 11, 15, 13, 12, 13, 10, 11, 15, 13, 12, 13],
//		            /*markPoint : {
//		                data : [
//		                    {type : 'max', name: '最大值'},
//		                    {type : 'min', name: '最小值'}
//		                ]
//		            },
//		            markLine : {
//		                data : [
//		                    {type : 'average', name: '平均值'}
//		                ]
//		            }*/
//		        },
//		        {
//		            name:'销量',
//		            type:'line',
//		            data:strXS//[1, 12, 2, 5, 3, 2, 0, 35, 2, 5, 3, 2],
//		            /*markPoint : {
//		                data : [
//		                    {name : '年最低', value : -2, xAxis: 1, yAxis: -1.5}
//		                ]
//		            },
//		            markLine : {
//		                data : [
//		                    {type : 'average', name : '平均值'}
//		                ]
//		            }*/
//		        }
//		        ,
//		        {
//		        	name:'库存',
//		        	type:'line',
//		        	data:strKC,
//		        },
//		        {
//		        	name:'借阅',
//		        	type:'line',
//		        	data:strKC,
//		        }
//	
//		    ]
		};
        // 为echarts对象加载数据 
        myChart.setOption(option); 
    }
  );
}
//特征分析
function statFeatures(featFlag){
	$("#statText").html('特征分析');
	$("#statFlag").val(2);
	
	$("#main").css({height:"450px"});
	var maxValue;
	var names;
	var data;
	var flag;
	$.ajax( {  
        type : "post",
        async: false,
        url : "../statistics/features",
        data:{featFlag:featFlag,id:$("#chooseId").val(),year:$("#chooseYear").val(),month:$("#chooseMonth").val(),chooseStatType:$("#chooseStatType").val()},
        dataType:"json",  
        success : function(dataStr) {
        	maxValue = dataStr.maxValue;
        	names = eval(dataStr.names);
       		data = eval(dataStr.jsonSale);
       		flag = dataStr.flag;
        }  
    });  
	// 使用
    require([
        'echarts',
        'echarts/chart/pie',
        'echarts/chart/funnel'
    ],
    function (ec) {
    	if(flag == "four"){
		}else if(flag == "three"){
			$("#jy").hide();
	    }else if(flag = "one"){
		    $("#dg").hide();
		    $("#xl").hide();
		    $("#kc").hide();
		    $("#jy_check").attr("checked",true);
	    }
        // 基于准备好的dom，初始化echarts图表 ,(dom,主题)
        var myChart = ec.init(document.getElementById('main'),'macarons');
        var option = {
		    title : {
		        text: '按特征分析统计',
		        subtext: '特征分析',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient : 'vertical',
		        x : 'left',
		        data:names
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            mark : {show: false},
		            dataView : {show: true, readOnly: false},
		            magicType : {
		                show: true, 
		                type: ['pie'/*, 'funnel'*/],
		                option: {
		                    funnel: {
		                        x: '25%',
		                        width: '50%',
		                        funnelAlign: 'left',
		                        //max: maxValue
		                        max:10000
		                    }
		                }
		            },
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    calculable : true,
		    series : [
		        {
		            name:'数量',
		            type:'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:data
		        }
		    ]
		};
        // 为echarts对象加载数据 
        myChart.setOption(option); 
    }
  );
}
//地域分析
function statRegion(){
	$("#statText").html('地域分析');
	$("#statFlag").val(3);
	$("#main").css({height:"450px"});
	var dataDG;
	var dataXL;
	var dataKC;
	var dataJY;
	var flag;
	$.ajax( {  
        type : "post",
        async: false,
        url : "../statistics/region",
        data:{id:$("#chooseId").val(),year:$("#chooseYear").val(),month:$("#chooseMonth").val(),chooseStatType:$("#chooseStatType").val()},
        dataType:"json",  
        success : function(dataStr) {
       		dataDG = eval(dataStr.list1);
			dataXL = eval(dataStr.list2);
			dataKC = eval(dataStr.list3);
			dataJY = eval(dataStr.list4);
            flag = dataStr.flag;
        }  
    });  
	 // 使用
    require(
    [
        'echarts',
        'echarts/chart/map' // 使用柱状图就加载bar模块，按需加载
    ],
    function (ec) {
    	var dg = "";
    	var xl = "";
    	var kc = "";
    	var jy = "";
    	var dgfl = true;
        var jyfl = false;
    	// 基于准备好的dom，初始化echarts图表
		if(flag == "four"){
	        dg = "订购";
		    xl = "销量";
		    kc = "库存";
		    jy = "借阅";
		}else if(flag == "three"){
			dg = "订购";
		    xl = "销量";
		    kc = "库存";
	    }else if(flag = "one"){
		    jy = "借阅";
		    dgfl = false;
		    jyfl = true;
	    }
        // 基于准备好的dom，初始化echarts图表
        var myChart = ec.init(document.getElementById('main'),'macarons');
        
        var option = {
		    title : {
		        text: '按地域分析统计',
		        subtext: '地域分析',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item'
		        /*formatter:function(params){
		        	  var res = params.name+':<br/>';
		              var myseries = option.series;
		              for (var i = 0; i < myseries.length; i++) {
		                  for(var j=0;j<myseries[i].data.length;j++){
		                	  if(myseries[i].data[j].name==params.name){
		                		  res+=myseries[i].name;
		                		  res+=":"+myseries[i].data[j].value+"<br>";
		                	  }
		                  }
		              }
		              return res;
		        }*/
		    },
		    legend: {
		    	show:true,
		    	selectedMode:"single",
		    	selected:{
		            '订购' : dgfl,
		            '销量' : false,
		            '库存' : false,
		            '借阅' : jyfl,
		        },
		        orient: 'vertical',
		        x:'left',
		        data:[dg,xl,kc,jy]
		    },
		    dataRange: {
		        min: 0,
		        max: 10000,
		        x: 'left',
		        y: 'bottom',
		        text:""['高','低'],           // 文本，默认为数值文本
		        //selectedMode:"singal",
		        calculable : true
		    },
		    toolbox: {
		        show: true,
		        orient : 'vertical',
		        x: 'right',
		        y: 'center',
		        feature : {
		            mark : {show: false},
		            dataView : {show: true, readOnly: false},
		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    roamController: {
		        show: true,
		        x: 'right',
		        mapTypeControl: {
		            'china': true
		        }
		    },
		    series :
		    	function(){
		        var serie=[];
		        if(dataDG!=null && dataDG!="undified"){
		        	var itemDG={
		 		        	name:'订购',
		 		        	type:'map',
		 		        	data:dataDG,
			           }
			        serie.push(itemDG);
		        }
		        if(dataKC!=null && dataKC!="undified"){
		         var itemKC={
	 		        	name:'库存',
	 		        	type:'map',
	 		        	data:dataKC,
		           }
		         serie.push(itemKC);
		        }
		         
		         if(dataXL!=null && dataXL!="undified"){
		         var itemXL={
	 		        	name:'销量',
	 		        	type:'map',
	 		        	data:dataXL,
		           }
		         serie.push(itemXL);
		         }
		         
		        if(dataJY!=null && dataJY!="undified"){
		        	var itemJY={
		 		        	name:'借阅',
		 		        	type:'map',
		 		        	data:dataJY,
			           }
		        	 serie.push(itemJY);
		        }
		         
		         
		         
		        
		      return serie;
		   }()
//		    series : [
//		        {
//		            name: '订购',
//		            type: 'map',
//		            //mapValueCalculation:average,
//		            mapType: 'china',
//		            roam: true,
//		            itemStyle:{
//		                normal:{label:{show:true}},
//		                emphasis:{label:{show:true}}
//		            },
//		            data:dataDG
//		        },
//		        {
//		            name: '销量',
//		            type: 'map',
//		            mapType: 'china',
//		            itemStyle:{
//		                normal:{label:{show:true}},
//		                emphasis:{label:{show:true}}
//		            },
//		            data:dataXL
//		        },
//		        {
//		            name: '库存',
//		            type: 'map',
//		            mapType: 'china',
//		            itemStyle:{
//		                normal:{label:{show:true}},
//		                emphasis:{label:{show:true}}
//		            },
//		            data:dataKC
//		        },
//		        {
//		            name: '借阅',
//		            type: 'map',
//		            mapType: 'china',
//		            itemStyle:{
//		                normal:{label:{show:true}},
//		                emphasis:{label:{show:true}}
//		            },
//		            data:dataJY
//		        }
//		    ]
		};
        // 为echarts对象加载数据 
        myChart.setOption(option); 
    }
  );
}