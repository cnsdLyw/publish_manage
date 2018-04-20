var chooseFirstLevel = "-选择一级机构-";
var chooseSecondLevel = "-选择二级机构-";
var organizationInit = function(_cmbProvince, _cmbCity, defaultProvince, defaultCity)
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
	function cmbAddOptions(cmb, strs, obj)
	{
		var option = document.createElement("OPTION");
		cmb.options.add(option);
		var List = new Array(); 
		
		if(strs==chooseFirstLevel||strs==chooseSecondLevel){
			option.value = '';
			option.innerHTML = strs;
		}else{
			List = strs.split(",");
			var str = List[1];
			option.value = strs;
			option.innerHTML = str;
		}
		option.obj = obj;
	}
	
	function changeProvince()
	{
		cmbCity.options.length = 0;
		if(cmbProvince.selectedIndex == -1)return;
		var item = cmbProvince.options[cmbProvince.selectedIndex].obj;
		for(var i=0; i<item.cityList.length; i++)
		{
			cmbAddOptions(cmbCity, item.cityList[i], null);
		}
		cmbSelect(cmbCity, defaultCity);
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
{name:'出版单位', cityList:['101,图书出版社','102,期刊社','103,报社','104,音像出版社','105,电子出版物出版社','106,互联网出版单位','191,出版集团','192,报业集团','193,期刊集团']},
{name:'印刷企业',cityList:['201,出版物印刷企业','203,排版、制版、装订专项印刷企业','205,其他印刷品印刷企业','291,印刷集团']},
{name:'复制企业', cityList:['301,音像复制企业','302,电子出版物复制企业']},
{name:'制作企业', cityList:['401,音像制作企业','404,电子出版物制作企业','421,数字化加工企业']},
{name:'发行企业', cityList:['503,批发企业','504,零售企业','521,物流企业','522,仓储企业','523,馆配商','599,其他发行企业']},
{name:'进口经营企业', cityList:['600,出版物进口企业']},
{name:'机构用户', cityList:['801,公共图书馆','811,高等教育图书馆','812,高等院校资料室及相关机构','813,中等教育图书馆','814,初等教育图书馆','815,特殊教育图书馆','819,其他教育图书馆','821,科研院所图书馆','831,政府机关图书馆','841,社会团体图书馆','899,其他机构用户']},
{name:'电信服务商', cityList:['901,电信运营商','999,其他服务商']},
];