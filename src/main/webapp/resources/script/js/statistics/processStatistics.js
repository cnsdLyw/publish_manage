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
	var strJG;
	var strJGWC;
	$.ajax( {  
        type : "post",
        async: false,
        url : "../processCenter/trend/",
        data:{year:$("#chooseYear").val()},
        dataType:"json",  
        success : function(data) {
       		strMonth = data.strMonth.split(",");
       		strJG = eval(data.strJG);
       		strJGWC = eval(data.strJGWC);
        }  
    });
	
	 // 使用
    require([
        'echarts',
        'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
        'echarts/chart/bar'
    ],
    function (ec) {
        // 基于准备好的dom，初始化echarts图表
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
		    		'加工完成' : true,
		            '正在加工' : true
		            
		        },
		        data:['加工完成','正在加工']
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
		    series : [
	              {
			            name:'加工完成',
			            type:'line',
			            data:strJGWC//[11, 11, 15, 13, 12, 13, 10, 11, 15, 13, 12, 13],
			            /*markPoint : {
			                data : [
			                    {type : 'max', name: '最大值'},
			                    {type : 'min', name: '最小值'}
			                ]
			            },
			            markLine : {
			                data : [
			                    {type : 'average', name: '平均值'}
			                ]
			            }*/
				 },
		        {
		            name:'正在加工',
		            type:'line',
		            data:strJG//[11, 11, 15, 13, 12, 13, 10, 11, 15, 13, 12, 13],
		            /*markPoint : {
		                data : [
		                    {type : 'max', name: '最大值'},
		                    {type : 'min', name: '最小值'}
		                ]
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name: '平均值'}
		                ]
		            }*/
		        }
		       
		    ]
		};
        // 为echarts对象加载数据 
        myChart.setOption(option); 
    }
  );
}
//特征分析
function statFeatures(featFlag){
	$("#statText").html('来源分析');
	$("#statFlag").val(2);
	
	$("#main").css({height:"450px"});
	var maxValue;
	var names;
	var data;
	$.ajax( {  
        type : "post",
        async: false,
        url : "../processCenter/features",
        data:{featFlag:featFlag,year:$("#chooseYear").val()}, 
        dataType:"json",  
        success : function(dataStr) {
        	maxValue = dataStr.maxValue;
        	names = eval(dataStr.names);
       		data = eval(dataStr.jsonNum);
        }  
    });  
	// 使用
    require([
        'echarts',
        'echarts/chart/pie',
        'echarts/chart/funnel'
    ],
    function (ec) {
        // 基于准备好的dom，初始化echarts图表 ,(dom,主题)
        var myChart = ec.init(document.getElementById('main'),'macarons');
        var option = {
		    title : {
		        text: '按来源分析统计',
		        subtext: '来源分析',
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
		                type: ['pie', 'funnel'],
		                option: {
		                    funnel: {
		                        x: '25%',
		                        width: '80%',
		                        funnelAlign: 'left',
		                        max: maxValue
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
	$.ajax( {  
        type : "post",
        async: false,
        url : "../statistics/region",
        data:{id:$("#chooseId").val(),year:$("#chooseYear").val()}, 
        dataType:"json",  
        success : function(dataStr) {
       		dataDG = eval(dataStr.list1);
			dataXL = eval(dataStr.list2);
			dataKC = eval(dataStr.list3);
			dataJY = eval(dataStr.list4);
        }  
    });  
	 // 使用
    require(
    [
        'echarts',
        'echarts/chart/map' // 使用柱状图就加载bar模块，按需加载
    ],
    function (ec) {
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
		            '订购' : true,
		            '销量' : false,
		            '库存' : false,
		            '借阅' : false,
		        },
		        orient: 'vertical',
		        x:'left',
		        data:['订购','销量','库存','借阅']
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
		    series : [
		        {
		            name: '订购',
		            type: 'map',
		            //mapValueCalculation:average,
		            mapType: 'china',
		            roam: true,
		            itemStyle:{
		                normal:{label:{show:true}},
		                emphasis:{label:{show:true}}
		            },
		            data:dataDG
		        },
		        {
		            name: '销量',
		            type: 'map',
		            mapType: 'china',
		            itemStyle:{
		                normal:{label:{show:true}},
		                emphasis:{label:{show:true}}
		            },
		            data:dataXL
		        },
		        {
		            name: '库存',
		            type: 'map',
		            mapType: 'china',
		            itemStyle:{
		                normal:{label:{show:true}},
		                emphasis:{label:{show:true}}
		            },
		            data:dataKC
		        },
		        {
		            name: '借阅',
		            type: 'map',
		            mapType: 'china',
		            itemStyle:{
		                normal:{label:{show:true}},
		                emphasis:{label:{show:true}}
		            },
		            data:dataJY
		        }
		    ]
		};
        // 为echarts对象加载数据 
        myChart.setOption(option); 
    }
  );
}