var chooseFirstLevel = "-选择一级机构-";
var chooseSecondLevel = "-选择二级机构-";
var organizationInit = function(_cmbProvince, _cmbCity, _cmbArea, defaultProvince, defaultCity, defaultArea)
{
	var cmbProvince = document.getElementById(_cmbProvince);
	var cmbCity = document.getElementById(_cmbCity);
		
		function cmbSelect(cmb, str)
		{
			for(var i=0; i<cmb.options.length; i++)
			{
				if(cmb.options[i].value == str)
				{
					cmb.selectedIndex = i;
					return;
				}
			}
		}
		function cmbAddOption(cmb, str, obj)
		{
			var option = document.createElement("OPTION");
			cmb.options.add(option);
			if(str==chooseFirstLevel||str==chooseSecondLevel){
				option.value = '';
			}else{
				option.value = str;
			}
			option.innerHTML = str;
			option.obj = obj;
		}
		function cmbAddOptions(cmb, str, obj)
		{
			var option = document.createElement("OPTION");
			cmb.options.add(option);
			var List = new Array(); 
			if(str==chooseFirstLevel||str==chooseSecondLevel){
				option.value = '';
				option.innerHTML = str;
			}else{
				option.value = str;
				option.innerHTML = str;
			}
			option.obj = obj;
		}
		
		function changeCity()
		{
			var a = document.getElementById("secondOrgName");
			if(a.value!="" && a.value!=defaultArea){
				$("#secondOrgName").value=a.value;
				$("#queryForm").submit();
			}
		}
		function changeProvince()
		{
			cmbCity.options.length = 0;
			cmbCity.onchange = null;
			if(cmbProvince.selectedIndex == -1)return;
			var item = cmbProvince.options[cmbProvince.selectedIndex].obj;
			for(var i=0; i<item.cityList.length; i++)
			{
				cmbAddOptions(cmbCity, item.cityList[i], null);
			}
			cmbSelect(cmbCity, defaultCity);
			changeCity();
			cmbCity.onchange = changeCity;
		}
		
		for(var i=0; i<FirstLevelList.length; i++)
		{
			cmbAddOption(cmbProvince, FirstLevelList[i].name, FirstLevelList[i]);
		}
		cmbSelect(cmbProvince, defaultProvince);
		changeProvince();
		cmbProvince.onchange = changeProvince;
	}


var FirstLevelList = [
{name:chooseFirstLevel, cityList:[chooseSecondLevel]},
{name:'出版单位', cityList:['图书出版社','期刊社','报社','音像出版社','电子出版物出版社','互联网出版单位','出版集团','报业集团','期刊集团']},
{name:'印刷企业',cityList:['出版物印刷企业','排版、制版、装订专项印刷企业','其他印刷品印刷企业','印刷集团']},
{name:'复制企业', cityList:['音像复制企业','电子出版物复制企业']},
{name:'制作企业', cityList:['音像制作企业','电子出版物制作企业','数字化加工企业']},
{name:'发行企业', cityList:['批发企业','零售企业','物流企业','仓储企业','馆配商','其他发行企业']},
{name:'进口经营企业', cityList:['出版物进口企业']},
{name:'机构用户', cityList:['公共图书馆','高等教育图书馆','高等院校资料室及相关机构','中等教育图书馆','初等教育图书馆','特殊教育图书馆','其他教育图书馆','科研院所图书馆','政府机关图书馆','社会团体图书馆','其他机构用户']},
{name:'电信服务商', cityList:['电信运营商','其他服务商']},
];