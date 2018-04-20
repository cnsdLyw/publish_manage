package com.litc.security.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.litc.common.util.ConfigurationUtil;
import com.litc.common.util.Constant;
import com.litc.common.util.tree.AdditionalParameters;
import com.litc.common.util.tree.Item;
import com.litc.fileSystem.model.JetsenFiles;
import com.litc.fileSystem.service.JetsenFilesService;
import com.litc.security.model.LogInfo;
import com.litc.security.model.Organization;
import com.litc.security.service.BaseUtil;
import com.litc.security.service.OrganizationService;
import com.litc.security.service.SysLogInfoService;
import com.litc.system.model.Classification;
import com.litc.system.service.ClassificationService;

@Controller
@RequestMapping("/organization")
public class OrganizationController extends BaseController<Organization>{

	private final static Logger logger = LoggerFactory.getLogger(OrganizationController.class);
	
	/**
	 * 每页数量
	 */
	private static final int AUTHORITYPAGESIZE = 10;
	
	/**
	 * 排序字段
	 */
	private static String ORDER_TYPE = "modifyTime";
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private SysLogInfoService sysLogInfoService;
	
	@Autowired
	private OrganizationService organizationApplyService;
	
	@Autowired
	private ClassificationService jcClassificationService;
	
	@Autowired
	private JetsenFilesService jetsenFilesFastDFService;
	
	@RequestMapping(value = "/list")
	public ModelAndView list(HttpServletRequest request,String orderType,String sortType,Long orgType,String keyWord,String orgCode) {
		
		ModelAndView modelAndView = new ModelAndView("security/organization/organization-list");
		pageNo = this.getInt(request, "pageNo");
		String firstOrgName = this.getString(request, "firstOrgName");
		String secondOrgName = this.getString(request, "secondOrgName");
		//扩展根据表头字段排序
		Direction sortDirection = null;
		if(StringUtils.isNotBlank(sortType)){
			if("asc".equals(sortType.toLowerCase())){
				sortDirection = SORT_TYPE_ASC ;
			}else if("desc".equals(sortType.toLowerCase())){
				sortDirection = SORT_TYPE_DESC ;
			}
			
		}else{
			sortDirection = SORT_TYPE_DESC;
		}
		
		if(!StringUtils.isNotBlank(orderType)){
			orderType = ORDER_TYPE;
		}
		modelAndView.addObject("orderType",orderType);
		modelAndView.addObject("sortType",sortType);
		modelAndView.addObject("firstOrgName",firstOrgName);
		modelAndView.addObject("secondOrgName",secondOrgName);
		
		//创建查询参数
		page = organizationService.getOrganizationsByPages(pageNo, AUTHORITYPAGESIZE,sortDirection,orderType,orgType, secondOrgName, keyWord);
		modelAndView.addObject("orgType",orgType);
		modelAndView.addObject("keyWord",keyWord);
		modelAndView.addObject("pageContent",page);
		modelAndView.addObject("classes",getOrgTypes());
		return modelAndView;
	}
	
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request,String orderType,String sortType,Long orgType,String keyWord,String orgCode) {
		
		ModelAndView modelAndView = new ModelAndView("security/organization/organization-index");
		if(orgCode==null){
			modelAndView.addObject("orgCode", "null");
		}else{
			modelAndView.addObject("orgCode", orgCode);
		}
		return modelAndView;
		/*ModelAndView modelAndView = new ModelAndView("security/organization/organization-list");
		pageNo = this.getInt(request, "pageNo");
		//扩展根据表头字段排序
		Direction sortDirection = null;
		if(StringUtils.isNotBlank(sortType)){
			if("asc".equals(sortType.toLowerCase())){
				sortDirection = SORT_TYPE_ASC ;
			}else if("desc".equals(sortType.toLowerCase())){
				sortDirection = SORT_TYPE_DESC ;
			}
			
		}else{
			sortDirection = SORT_TYPE_DESC;
		}
		
		if(!StringUtils.isNotBlank(orderType)){
			orderType = ORDER_TYPE;
		}
		modelAndView.addObject("orderType",orderType);
		modelAndView.addObject("sortType",sortType);
		
		//创建查询参数
		page = organizationService.getOrganizationsByPages(pageNo, AUTHORITYPAGESIZE,sortDirection,orderType,orgType, keyWord);
		modelAndView.addObject("orgType",orgType);
		modelAndView.addObject("keyWord",keyWord);
		modelAndView.addObject("pageContent",page);
		modelAndView.addObject("classes",getOrgTypes());
		return modelAndView;*/
	}
	
	@RequestMapping(value="/getOrganization", method=RequestMethod.GET)
	public Organization getOrganization(String orgCode){
		logger.info("查询机构  getOrganization "+orgCode);
		Organization organization = organizationService.getOrganization(orgCode);
		return organization;
	}
	
	@RequestMapping(value="/addFtp", method=RequestMethod.GET)
	public ModelAndView addFtp(String orgCode){
		ModelAndView modelAndView = new ModelAndView("security/organization/organization-ftp");
		if(!orgCode.equals("null")){
			Organization organization = organizationService.getOrganization(orgCode);
			modelAndView.addObject("organization",organization);
			logger.info("添加机构ftp addFtp "+organization.getOrgName());
		}else{
			modelAndView=null;
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/getJsonOrganization", method=RequestMethod.GET)
	@ResponseBody
	public Organization getJsonOrganization(String orgCode){
		logger.info("查询机构  deleteRole "+orgCode);
		Organization organization = organizationService.getOrganization(orgCode);
		return organization;
	}
	@RequestMapping(value="/getJsonOrganizations", method=RequestMethod.GET)
	@ResponseBody
	public Organization getJsonOrganizations(String orgCode){
		logger.info("查询机构  deleteRole "+orgCode);
		Organization organizations = organizationService.getOrganization(orgCode);
		return organizations;
	}
	
	@RequestMapping(value = "/addOrganization", method = RequestMethod.GET)
	public ModelAndView addOrganizationPage(String orgCode) {
		logger.info("添加机构  addOrganizationPage");
		ModelAndView modelAndView = new ModelAndView("security/organization/organization-add");
		
		/*Dimension scrSize=Toolkit.getDefaultToolkit().getScreenSize();  
		//int x = (int) (scrSize.width*0.41666667);
		// modify by cuiyc on 20170519 修改显示图片分辨率大小180*150
		int x = (int) (scrSize.width*0.09375);
		int y= (int) (x/1.2);
		modelAndView.addObject("x",x);
		modelAndView.addObject("y",y);*/
		String orgName = organizationService.getOrgName(orgCode);
		Organization organization = new Organization();
		organization.setUpperCode(orgCode);
		modelAndView.addObject("organization",organization );
		modelAndView.addObject("classes",getOrgTypes());
		modelAndView.addObject("orgName",orgName);
		return modelAndView;
	}
	
	
	@RequestMapping(value="/editOrganizations", method=RequestMethod.GET)
	public ModelAndView editOrganizationPages(String orgCode) {
		ModelAndView modelAndView = new ModelAndView("security/organization/organization-edit");
		if(!orgCode.equals("null")){
			Organization organization = organizationService.getOrganization(orgCode);
			if(organization.getUpperCode()!=null && !organization.getUpperCode().equals("")){
				Organization organizations = organizationService.getOrganization(organization.getUpperCode());
				modelAndView.addObject("orgName",organizations.getOrgName());
			}
			modelAndView.addObject("organization",organization);
			modelAndView.addObject("classes",getOrgTypes());
			modelAndView.addObject("type",organization.getOrgType());
			logger.info("添加机构  editOrganizationPage "+organization.getOrgName());
		}else{
			modelAndView=null;
		}
		return modelAndView;
		
	}
	
	@RequestMapping(value="/editOrganization", method=RequestMethod.GET)
	public ModelAndView editOrganizationPage(String orgCode) {
		ModelAndView modelAndView = new ModelAndView("security/organization/organization-add");
		if(!orgCode.equals("null")){
			Organization organization = organizationService.getOrganization(orgCode);
			if(organization.getUpperCode()!=null && !organization.getUpperCode().equals("")){
				Organization organizations = organizationService.getOrganization(organization.getUpperCode());
				modelAndView.addObject("orgName",organizations.getOrgName());
			}
			modelAndView.addObject("organization",organization);
			modelAndView.addObject("classes",getOrgTypes());
			modelAndView.addObject("type",organization.getOrgType());
			logger.info("添加机构  editOrganizationPage "+organization.getOrgName());
		}else{
			modelAndView=null;
		}
		return modelAndView;
		
	}
	@RequestMapping(value = "/saveFtp", method = RequestMethod.POST)
	public ModelAndView saveFtp(HttpServletRequest request,String orgCode,String ftpAddress, String ftpName, String ftpPassWord) {
		ModelAndView modelAndView = new ModelAndView("redirect:/organization/list/");
		Organization organization = organizationService.getOrganization(orgCode);
		if(organization!=null){
			organization.setFtpAddress(ftpAddress);
			organization.setFtpName(ftpName);
			organization.setFtpPassWord(ftpPassWord);
			organizationService.addOrganization(organization);
			logger.info("保存机构ftp  saveFtp "+organization.getOrgName());
			modelAndView.addObject("message",  "1");
			return modelAndView;
		}
		modelAndView.addObject("message",  "0");
		return modelAndView;
	}
	@RequestMapping(value = "/saveOrganization", method = RequestMethod.POST)
	public ModelAndView saveOrganization(@RequestParam("file") MultipartFile file,@ModelAttribute Organization organization,String operate,HttpServletRequest request) {
		//机构管理日志
		if(("tianjia").equals(operate)){
			operate = "系统管理添加机构："+ organization.getOrgName();
		}
		if(("bianji").equals(operate)){
			operate = "系统管理编辑机构："+ organization.getOrgName();
		}
		
		LogInfo logInfo = BaseUtil.addOperateLog(request, operate);
		if(null != logInfo){
			sysLogInfoService.addLogInfo(logInfo);
		}
		
		//上传封面
		/*if(!file.isEmpty()){
			JetsenFiles jetsenFiles = jetsenFilesFastDFService.uploadFile(file, "", "", Constant.ORGANIZATION_COVER_FILE);
			organization.setUrl(jetsenFiles.getStoragePath());
		}*/
		JetsenFiles files1 = new JetsenFiles();
		if(!file.isEmpty()){
			files1 = jetsenFilesFastDFService.uploadFile(file,"", "",Constant.ORGANIZATION_COVER_FILE);
			if(files1!=null){
				organization.setUrl(files1.getStoragePath());
			}else{
				organization.setUrl(null);
			}
		}else{
			if(organization.getUrl() == null || " ".equals(organization.getUrl()) || organization.getUrl().isEmpty()){
				organization.setUrl(null);
			}
		}
		logger.info("保存机构  saveOrganization "+organization.getOrgName());
		ModelAndView modelAndView = new ModelAndView("redirect:/organization/list/");
		organization.setModifyTime(new Date());
		organizationService.addOrganization(organization);
		if(isSaveSuccess(organization.getOrgCode())){
			modelAndView.addObject("message",  "1");
		}else{
			modelAndView.addObject("message",  "0");
		}
		
		return modelAndView;
	}
	/*private Map<String,String> getReturnStr(String status,String message){
		Map<String,String> map = new HashMap<String, String>();
		map.put("status", status);
		map.put("message", message);
		return map;
	}
	@RequestMapping(value = "/saveOrganization", method = RequestMethod.POST)
	public Map<String,String> saveOrganization(@RequestParam("file") MultipartFile file,@ModelAttribute Organization organization,String operate,HttpServletRequest request) {
		//机构管理日志
		if(("tianjia").equals(operate)){
			operate = "系统管理添加机构："+ organization.getOrgName();
		}
		if(("bianji").equals(operate)){
			operate = "系统管理编辑机构："+ organization.getOrgName();
		}
		
		LogInfo logInfo = BaseUtil.addOperateLog(request, operate);
		if(null != logInfo){
			sysLogInfoService.addLogInfo(logInfo);
		}
		
		String fileUrl = "";
		
		//上传封面
		if(!file.isEmpty()){
		        String name = file.getOriginalFilename();
//				organization.setUrl(fileUrl);
//				String home = request.getServletContext().getRealPath("/");
//				String path = home + fileUrl;
//				String newPath = path.substring(0, path.length()-34);
//		    	String newName =fileUrl.substring(fileUrl.length()-34, fileUrl.length());
		        String t=Thread.currentThread().getContextClassLoader().getResource("").getPath();
				int num=t.indexOf("/WEB-INF");
				String path=(t.substring(1,num)+"\\upload").replace('/', '\\');
				path = path + File.separator + "image" + File.separator ;
				File file1 = new File(path);
				if (!file1.exists()) {
					file1.mkdirs();
				}
				File folder = new File(path,name);
				try {
					file.transferTo(folder);
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				download(Constant.SERVER_IMAGE_PATH+fileUrl, newName,newPath); 
				fileUrl = "/upload/image/"+name;
				
		}else{
			Organization organization1 = organizationService.getOrganization(organization.getOrgCode());
			fileUrl = organization1.getUrl();
		}
		
		organization.setUrl(fileUrl);
		logger.info("保存机构  saveOrganization "+organization.getOrgName());
		organization.setModifyTime(new Date());
		
		
		organization.setSecondOrgName(organization.getSecondOrgName());
		organizationService.addOrganization(organization);
		
		//修改机构广播发送机构信息
		
		return getReturnStr("1","表单提交成功");
	}*/

	
	@RequestMapping(value="/isOrganizationUsed", method=RequestMethod.GET)
	@ResponseBody
	public Organization isOrganizationUsed(Long orgCode){
		//Organization organization = organizationService.getOrganization(orgCode);
		//return organization;
		return null;
	}

	@RequestMapping(value="/isUsed", method=RequestMethod.GET)
	@ResponseBody
	public boolean isUsed(String orgCodes) {
		String[] orgCode=getOrgCodeArray(orgCodes);
		int i = organizationService.isOrganizationUsed(orgCode);
		return i>0?true:false;
	}
	
	@RequestMapping(value="/deleteOrganization", method=RequestMethod.GET)
	public ModelAndView deleteOrganization(String orgCode,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("redirect:/organization/list/");
		if(!(organizationService.isOrganizationUsed(orgCode)>0)){
			//机构管理删除日志
			Organization organization = organizationService.getOrganization(orgCode);
			String operate = "系统管理机构管理删除:"+organization.getOrgName();
			LogInfo logInfo = BaseUtil.addOperateLog(request, operate);
			if(null != logInfo){
				sysLogInfoService.addLogInfo(logInfo);
			}
			organizationService.deleteOrganization(orgCode);
			modelAndView.addObject("message",  "1");
		}else{
			modelAndView.addObject("message",  "-1");
		}
	
		return modelAndView;
	}
	
	@RequestMapping(value="/asyncOrganization", method=RequestMethod.GET)
	public ModelAndView asyncOrganization(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("redirect:/organization/list/");
		RestTemplate restTemplate = new RestTemplate();
		String url=ConfigurationUtil.center_TomcatAccessAddress+"organizationAnsyRest/getOrganization";
		String json = restTemplate.postForObject(url ,null ,String.class);
		JSONArray obj = JSON.parseArray(json);
		if(obj!=null && !obj.equals("")){
		for (Object object : obj) {
			JSONObject job = JSONObject.parseObject(object.toString());
			Organization organization = new Organization();
			organization.setOrgName((String) job.get("orgName"));
			organization.setOrgCode((String) job.get("orgCode"));
			//Integer org = (Integer)job.get("orgType");
			//Long orgType = Long.valueOf(org);
			organization.setOrgType((String)job.get("orgType"));
			organization.setPostalcode((String) job.get("postalcode"));
			organization.setTelephone((String) job.get("telephone"));
			organization.setOrgContacter((String) job.get("orgContacter"));
			organization.setOrgContactPhone((String) job.get("orgContactPhone"));
			organization.setOrgContactEmail((String) job.get("orgContactEmail"));
			organization.setOrgContent((String) job.get("orgContent"));
			organization.setProvince((String) job.get("province"));
			organization.setCity((String) job.get("city"));
			organization.setCounty((String) job.get("county"));
			organization.setOrgAddress((String) job.get("orgAddress"));
			organization.setModifyTime(new Date());
			organization.setUrl((String) job.get("url"));
			organization.setUpperCode((String) job.get("upperCode"));
			organization.setOrgWebsit((String) job.get("orgWebsit"));
			organization.setOrgEconomic((String) job.get("orgEconomic"));
			organization.setIsbn((String) job.get("isbn"));
			organization.setLoginName((String) job.get("loginName"));
			organization.setPassWord((String) job.get("passWord"));
			organization.setFtpAddress((String) job.get("ftpAddress"));
			organization.setFtpName((String) job.get("ftpName"));
			organization.setFtpPassWord((String) job.get("ftpPassWord"));
			organization.setFirstOrgName((String) job.get("firstOrgName"));
			organization.setSecondOrgName((String) job.get("secondOrgName"));
			organizationService.addOrganization(organization);
		}
			modelAndView.addObject("message",  "1");
		}else{
			modelAndView.addObject("message",  "-1");
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/deleteOrganizations", method=RequestMethod.GET)
	public ModelAndView deleteOrganizations(String orgCodes,HttpServletRequest request) {
		
		ModelAndView modelAndView = new ModelAndView("security/organization/chooseOrgType");
		/*
		 ModelAndView modelAndView = new ModelAndView("redirect:/organization/list/");
		 String[] orgCode=getOrgCodeArray(orgCodes);
		if(!(organizationService.isOrganizationUsed(orgCode)>0)){
			//机构管理删除日志
			for(String org : orgCode){
				Organization organization = organizationService.getOrganization(org);
				String operate = "系统管理机构管理删除:"+organization.getOrgName();
				LogInfo logInfo = BaseUtil.addOperateLog(request, operate);
				if(null != logInfo){
					sysLogInfoService.addLogInfo(logInfo);
				}
			}
			
			organizationService.deleteOrganizations(orgCode);
			modelAndView.addObject("message",  "1");
		}else{
			modelAndView.addObject("message",  "-1");
		}*/
		modelAndView.addObject("classes",getOrgTypes());
		return modelAndView;
	}
	
	@RequestMapping(value="/isOrgCodeExist", method=RequestMethod.GET)
	@ResponseBody
	public boolean isOrgCodeExist(String orgCode){
		return organizationService.isOrgCodeExist(orgCode)||organizationApplyService.isOrgCodeExist(orgCode);
	}
	
	/*private List<Map<String,String>> getStatus(){
		List<Map<String,String>> list = new ArrayList<>();
		Map<String,String> map1 = new HashMap<String,String>();
		map1.put("statusId", "0");
		map1.put("statusName", "待审核");
		list.add(map1);
		
		Map<String,String> map2 = new HashMap<String,String>();
		map2.put("statusId", "1");
		map2.put("statusName", "审核通过");
		list.add(map2);
		
		Map<String,String> map3 = new HashMap<String,String>();
		map3.put("statusId", "2");
		map3.put("statusName", "审核不通过");
		list.add(map3);
		
		return list;
	}*/

	@RequestMapping(value = "/leftTree")
	public ModelAndView leftTree(HttpServletRequest request,String orgCode) throws Exception {
		ModelAndView modelAndView = new ModelAndView("security/organization/organization-leftTree");
		modelAndView.addObject("orgCode", orgCode);
		return modelAndView;
	}
	
	@RequestMapping(value="/getTreeData", method=RequestMethod.GET)
	@ResponseBody
	public Object getTreeData(String classKey, String classCode){
		logger.info("getTreeData 获取分类  ，分类码  "+classKey+"，展开的节点 "+classCode);
		String openListStr = null;
		/*if(StringUtils.isNotBlank(classCode)){
			openListStr = jcClassificationService.getOpenListStr(classCode);
		}*/
		classKey = "ORG_CLASS";
		List<Classification> list = null;
		Map<String,Item> map = new HashMap<String, Item>();
		if(StringUtils.isNotBlank(classKey)){
			list = jcClassificationService.getClassByKey(classKey);
			for(Classification clazz:list){
				if(StringUtils.isBlank(clazz.getParentCode())){
					Item item = new Item();
					item.setText(clazz.getClassName());
					if(clazz.isHasLeaf()){
						item.setType("folder");
					}else{
						item.setType("item");
					}
					AdditionalParameters additionalParameters = new AdditionalParameters();
					if(StringUtils.isNotBlank(openListStr)&&openListStr.indexOf(clazz.getClassCode())!=-1){
						additionalParameters.setItemSelected(true);
					}
					additionalParameters.setId(clazz.getClassCode());
					item.setAdditionalParameters(additionalParameters);
					item.setCode(clazz.getClassCode());
					dealOrgClass(list,openListStr, clazz,item);
					map.put(clazz.getClassCode(), item);
				}
			
			}
		}
	
		JSONObject obj = (JSONObject) JSONObject.toJSON(map);
		logger.info("getTreeData   返回 "+obj.toString());
		return JSON.parse(obj.toString());
	}
	
	private void dealOrgClass(List<Classification> classList, String openListStr, Classification jcClass, Item itemParent){
		for(Classification clazz:classList){
			if(StringUtils.isNotBlank(clazz.getParentCode())&&clazz.getParentCode().equals(jcClass.getClassCode())){
				Item item = new Item();
				item.setText(clazz.getClassName());
				if(clazz.isHasLeaf()){
					item.setType("folder");
				}else{
					item.setType("item");
				}
				AdditionalParameters additionalParameters = new AdditionalParameters();
				if(StringUtils.isNotBlank(openListStr)&&openListStr.indexOf(clazz.getClassCode())!=-1){
					additionalParameters.setItemSelected(true);
				}
				additionalParameters.setId(clazz.getClassCode());
				item.setAdditionalParameters(additionalParameters);
				item.setCode(clazz.getClassCode());
				
				itemParent.getAdditionalParameters().getChildren().add(item);
				
				dealOrgClass(classList, openListStr, clazz,item);
			}
		}
	}
	/*
	@RequestMapping(value="/getTreeData", method=RequestMethod.GET)
	@ResponseBody
	public Object getTreeData(String orgCode){
		String tree_data ="{";
		String firstName = "";
		try {
			Organization orgs = organizationService.getOrganization(orgCode);
			if(orgs!=null){
				firstName = orgs.getFirstOrgName();
			}
			List<Organization> organizations = organizationRepository.getFirstOrganization();
			for(int s=0;s<organizations.size();s++){
				List<Organization> org = organizationRepository.getOrganizationBySecondNames(organizations.get(s).getFirstOrgName());
				if(org.size()>0){
					tree_data += "\""+organizations.get(s).getFirstOrgName()+"\":{\"text\":\""+organizations.get(s).getFirstOrgName()+"\",\"type\":\"folder\",\"icon-class\":\"blue\"";
					if(firstName.equals(organizations.get(s).getFirstOrgName())){
						tree_data += ",\"opened\":\"true\"";
					}
					tree_data+=",\"additionalParameters\":{\"children\":{";
					tree_data = dealOrgClass(tree_data,org,firstName,"0");
					if(s<organizations.size()-1){
						tree_data += ",";
					}else{
						tree_data += "}";
					}
				}
			}
		} catch (Exception e) {
			
		}
		if(tree_data.equals("{")){
			tree_data = "";
		}
		Object data = JSON.parse(tree_data);
		return data;
	}
	private String dealOrgClass(String tree_data,List<Organization> organizations,String firstName,String search){
		String type = "folder";
		for(int i=0;i<organizations.size();i++){
			if(search.equals("1")){
				String image = "<i class=\"ace-icon fa fa-archive green\"></i>";
				tree_data += "\""+ organizations.get(i).getOrgName()+"\":{\"text\":'"+image+organizations.get(i).getOrgName()+"',\"type\":\"item\",\"orgCode\":\""+organizations.get(i).getOrgCode()+"\"";
			}else{
				tree_data += "\""+ organizations.get(i).getSecondOrgName()+"\":{\"text\":\""+organizations.get(i).getSecondOrgName()+"\",\"type\":\""+type+"\",\"icon-class\":\"green\"";
				if(firstName.equals(organizations.get(i).getFirstOrgName())){
					tree_data += ",\"opened\":\"true\"";
				}
			}
			if(!search.equals("1")){
				List<Organization> secondChild = organizationService.getOrganizationBySecondName(organizations.get(i).getSecondOrgName());
				if(secondChild!=null){
					tree_data+=",\"additionalParameters\":{\"children\":{";
					tree_data = dealOrgClass(tree_data,secondChild, firstName,"1");
					if(i<organizations.size()-1){
						tree_data +="}}},"; 
					}else{
						tree_data +="}}}}}}";
					}
				}
			}else{
				if(i<organizations.size()-1){
					tree_data +="},"; 
				}else{
					tree_data +="}";
				}
			}
			
		}
		return tree_data;
	}*/
}