<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>  
<!DOCTYPE html>
<%
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>系统配置分类</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" 	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<%@include file="../../common/meta.html"%>
    <script src="${ctx }/resources/script/js/validate/jquery.validate.min.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/js/bootbox.js" type="text/javascript"></script>
    <script src="${ctx }/resources/script/bootstrap/BootstrapMenu.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
   
</script>
</head>
<body class="no-skin">
	<div class="main-container" id="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<!--所在位置 -->
				<!-- Modal -->
					<ul id="tree1"></ul>
				</div><!--end  main-content-inner -->
			</div><!-- end page-content -->

	</div><!-- end main-container -->
	<%@include file="../../common/javascript.html"%>

	<!-- page specific plugin scripts -->
		<script src="${ctx }/resources/script/bootstrap/js/fuelux/fuelux.tree.js"></script>

		<!-- ace scripts -->
		<script src="${ctx }/resources/script/bootstrap/js/ace/elements.scroller.js"></script>
		<script src="${ctx }/resources/script/bootstrap/js/ace/elements.colorpicker.js"></script>
		<script src="${ctx }/resources/script/bootstrap/js/ace/elements.fileinput.js"></script>
		<script src="${ctx }/resources/script/bootstrap/js/ace/elements.typeahead.js"></script>
		<script src="${ctx }/resources/script/bootstrap/js/ace/elements.wysiwyg.js"></script>
		<script src="${ctx }/resources/script/bootstrap/js/ace/elements.spinner.js"></script>
		<script src="${ctx }/resources/script/bootstrap/js/ace/elements.treeview.js"></script>
		<script src="${ctx }/resources/script/bootstrap/js/ace/elements.wizard.js"></script>
		<script src="${ctx }/resources/script/bootstrap/js/ace/elements.aside.js"></script>
		<script src="${ctx }/resources/script/bootstrap/js/ace/ace.js"></script>
		<script src="${ctx }/resources/script/bootstrap/js/ace/ace.ajax-content.js"></script>
		<script src="${ctx }/resources/script/bootstrap/js/ace/ace.touch-drag.js"></script>
		<script src="${ctx }/resources/script/bootstrap/js/ace/ace.sidebar.js"></script>
		<script src="${ctx }/resources/script/bootstrap/js/ace/ace.sidebar-scroll-1.js"></script>
		<script src="${ctx }/resources/script/bootstrap/js/ace/ace.submenu-hover.js"></script>
		<script src="${ctx }/resources/script/bootstrap/js/ace/ace.widget-box.js"></script>
		<script src="${ctx }/resources/script/bootstrap/js/ace/ace.settings.js"></script>
		<script src="${ctx }/resources/script/bootstrap/js/ace/ace.settings-rtl.js"></script>
		<script src="${ctx }/resources/script/bootstrap/js/ace/ace.settings-skin.js"></script>
		<script src="${ctx }/resources/script/bootstrap/js/ace/ace.widget-on-reload.js"></script>
		<script src="${ctx }/resources/script/bootstrap/js/ace/ace.searchbox-autocomplete.js"></script>
	
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
	var tree_data = {};
	var typeid = 0;
	function tree(){
		$.ajax( {  
			type : "get",  
			url : "${ctx}/sysConfigGroup/getTreeData/?typeid=${typeid}",
			dataType:"json",  
			async:false,
			success : function(data) {
				tree_data = data;
			}
		});
	}
	    $("#tree1").show(function(){
	    	tree();
	    });
	jQuery(function($){
		
	var sampleData = initiateDemoData();//see below

	$('#tree1').ace_tree({
		dataSource: sampleData['dataSource1'],
		multiSelect: false,
		folderSelect:true,//ACE
		cacheItems: true,
		'open-icon' : 'ace-icon tree-minus',
		'close-icon' : 'ace-icon tree-plus',
		'selectable' : true,
		'selected-icon' : 'ace-icon fa fa-check',
		'unselected-icon' : 'ace-icon fa fa-times',
		loadingHTML : '<div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div>'
	});
	
	//$("#b").click(function(){$("#a").trigger("click")})
	//$("#tree1").trigger("click");//就是执行#a的click事件
	/*$('#tree1').on("selected",function(){
		alert(0);
	});*/
	
	/*
	loaded.fu.tree
	updated.fu.tree
	selected.fu.tree
	deselected.fu.tree
	opened.fu.tree
	closed.fu.tree
	*/
	$('#tree1').on("selected.fu.tree",function(e,data){//e,事件。点击节点
		window.open("${ctx }/sysConfirure/list?typeid=" + data.target.id, "main_content_configure");
		//alert(data.target.text);
		//data.type="folder";
		//alert(123);
		//alert(data.target.id	);
		
	}).on("loaded.fu.tree",function(e,data){//e,事件。文件夹加载完成
		//alert(2);
	}).on("updated.fu.tree",function(e,data){//e,事件。加载完毕
		//alert(3);
	}).on("deselected.fu.tree",function(e,data){//e,事件。加载完毕
		//alert(4);
	}).on("opened.fu.tree",function(e,data){//e,事件。打开文件夹
		window.open("${ctx }/sysConfirure/list?typeid=" + data.id+"&parentName="+data.text, "main_content_configure");
		
	}).on("closed.fu.tree",function(e,data){//e,事件。关闭文件夹
		window.open("${ctx }/sysConfirure/list?typeid=0", "main_content_configure");
	});

	/*var ook = $.find(".tree-branch-name > .tree-label");
	alert(ook)
	setTimeout(function(){ook.trigger('click')}, 0);*/
						
	function initiateDemoData(){
		/*tree_data['for-sale']['additionalParameters'] = {
			'children' : {
				'appliances' : {text: 'Appliances', type: 'item'},
				'arts-crafts' : {text: 'Arts & Crafts', type: 'item'},
				'clothing' : {text: 'Clothing', type: 'item'},
				'computers' : {text: 'Computers', type: 'item'},
				'jewelry' : {text: 'Jewelry', type: 'item'},
				'office-business' : {text: 'Office & Business', type: 'item'},
				'sports-fitness' : {text: 'Sports & Fitness', type: 'item'}
			}
		}
		tree_data['vehicles']['additionalParameters'] = {
			'children' : {
				'cars' : {text: 'Cars', type: 'folder'},
				'motorcycles' : {text: 'Motorcycles', type: 'item'},
				'boats' : {text: 'Boats', type: 'item'}
			}
		}
		tree_data['vehicles']['additionalParameters']['children']['cars']['additionalParameters'] = {
			'children' : {
				'classics' : {text: 'Classics', type: 'item'},
				'convertibles' : {text: 'Convertibles', type: 'item'},
				'coupes' : {text: 'Coupes', type: 'item'},
				'hatchbacks' : {text: 'Hatchbacks', type: 'item'},
				'hybrids' : {text: 'Hybrids', type: 'item'},
				'suvs' : {text: 'SUVs', type: 'item'},
				'sedans' : {text: 'Sedans', type: 'item'},
				'trucks' : {text: 'Trucks', type: 'item'}
			}
		}

		tree_data['rentals']['additionalParameters'] = {
			'children' : {
				'apartments-rentals' : {text: 'Apartments', type: 'item'},
				'office-space-rentals' : {text: 'Office Space', type: 'item','selected':'true'},//添加selected表示选中的节点
				'vacation-rentals' : {text: 'Vacation Rentals', type: 'item'}
			}
		}
		tree_data['real-estate']['additionalParameters'] = {
			'children' : {
				'apartments' : {text: 'Apartments', type: 'item'},
				'villas' : {text: 'Villas', type: 'item'},
				'plots' : {text: 'Plots', type: 'item'}
			}
		}
		tree_data['pets']['additionalParameters'] = {
			'children' : {
				'cats' : {text: 'Cats', type: 'item'},
				'dogs' : {text: 'Dogs', type: 'item'},
				'horses' : {text: 'Horses', type: 'item'},
				'reptiles' : {text: 'Reptiles', type: 'item'}
			}
		}*/

		var dataSource1 = function(options, callback){
			
			var $data = null;
			if(!("text" in options) && !("type" in options)){
				$data = tree_data;//the root tree
				callback({ data: $data });
				return;
			}
			else if("type" in options && options.type == "folder") {
				if("additionalParameters" in options && "children" in options.additionalParameters)
					$data = options.additionalParameters.children || {};
				else $data = {}//no data
			}
			
			if($data != null)//this setTimeout is only for mimicking some random delay
				setTimeout(function(){callback({ data: $data });} , parseInt(Math.random() * 500) + 200);

			//we have used static data here
			//but you can retrieve your data dynamically from a server using ajax call
			//checkout examples/treeview.html and examples/treeview.js for more info
				}

		return {'dataSource1': dataSource1}
	}

});

/*
	var menu1 = new BootstrapMenu(".tree-item", {
	  actions: [{
		name: "jQuery特效",
		iconClass: "fa-plus blue",
		onClick: function(name) {
			alert(name);
		  //toastr.info("'jQuery特效 <i class='ace-icon fa fa-pencil bigger-130'></i>");
		}
	  }, {
		name: '站长素材',
		iconClass: 'fa-edit',
		onClick: function() {
		  //toastr.info("'站长素材");
		}
	  }, {
		name: '站长之家',
		iconClass: 'fa-trash',
		onClick: function() {
		  //toastr.info("'站长之家");
		}
	  }]
	});*/
	/*var menu2 = new BootstrapMenu(".tree-branch-header", {
	  actions: [{
		name: "jQuery特效  <i class='ace-icon fa fa-pencil bigger-130'></i>",
		iconClass: "fa-plus",
		onClick: function() {
		 // toastr.info("'jQuery特效 <i class='ace-icon fa fa-pencil bigger-130'></i>");
		}
	  }, {
		name: '站长素材',
		iconClass: 'fa-edit',
		onClick: function() {
		  //toastr.info("'站长素材");
		}
	  }, {
		name: '站长之家',
		iconClass: 'fa-trash',
		onClick: function() {
		 // toastr.info("'站长之家");
		}
	  }]
	});*/
	</script>

</body>
</html>