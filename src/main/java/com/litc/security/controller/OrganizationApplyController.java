package com.litc.security.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.litc.common.util.Constant;
import com.litc.common.util.JetsenPasswordUtil;
import com.litc.common.util.MailUtil;
import com.litc.model.Mail;
import com.litc.security.model.LogInfo;
import com.litc.security.model.Organization;
import com.litc.security.model.OrganizationApply;
import com.litc.security.model.Role;
import com.litc.security.model.User;
import com.litc.security.service.BaseUtil;
import com.litc.security.service.OrganizationApplyService;
import com.litc.security.service.OrganizationService;
import com.litc.security.service.RoleService;
import com.litc.security.service.SysLogInfoService;
import com.litc.security.service.UserService;

@Controller
@RequestMapping("/organizationApply")
public class OrganizationApplyController extends BaseController<OrganizationApply>{

	private final static Logger logger = LoggerFactory.getLogger(OrganizationApplyController.class);
	
	/**
	 * 每页数量
	 */
	private static final int AUTHORITYPAGESIZE = 10;
	
	/**
	 * 排序字段
	 */
	private static String ORDER_TYPE = "modifyTime";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private SysLogInfoService sysLogInfoService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private OrganizationApplyService organizationApplyService;

	
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/list")
	public ModelAndView listOfOrganizationApply(HttpServletRequest request,String orderType,String sortType,Long orgType,Integer orgStatus,String keyWord) {
		ModelAndView modelAndView = new ModelAndView("security/organizationApply/organizationApply-list");
		String firstOrgName = this.getString(request, "firstOrgName");
		String secondOrgName = this.getString(request, "secondOrgName");
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
		modelAndView.addObject("firstOrgName",firstOrgName);
		modelAndView.addObject("secondOrgName",secondOrgName);
		//创建查询参数
		page = organizationApplyService.getOrganizationApplysByPages(pageNo, AUTHORITYPAGESIZE,sortDirection,orderType,orgType, orgStatus,keyWord,secondOrgName);
		modelAndView.addObject("orgType",orgType);
		modelAndView.addObject("keyWord",keyWord);
		modelAndView.addObject("orgStatus",orgStatus);
		modelAndView.addObject("pageContent",page);
		modelAndView.addObject("classes",getOrgTypes());
		modelAndView.addObject("statuses",getStatus());
		return modelAndView;
	}
	
	@RequestMapping(value="/getOrganizationApply", method=RequestMethod.GET)
	public OrganizationApply getOrganizationApply(Long id){
		logger.info("查询申请机构  getOrganizationApply "+id);
		OrganizationApply organizationApply = organizationApplyService.getOrganizationApply(id);
		return organizationApply;
	}
	
	@RequestMapping(value="/getJsonOrganizationApply", method=RequestMethod.GET)
	@ResponseBody
	public OrganizationApply getJsonOrganizationApply(Long id){
		logger.info("查询申请机构  deleteRole "+id);
		OrganizationApply organizationApply = organizationApplyService.getOrganizationApply(id);
		return organizationApply;
	}
	
	@RequestMapping(value = "/addOrganizationApply", method = RequestMethod.GET)
	public ModelAndView addOrganizationPage() {
		logger.info("添加申请机构  addOrganizationPage");
		ModelAndView modelAndView = new ModelAndView("security/organizationApply/organizationApply-add");
		modelAndView.addObject("organizationApply", new OrganizationApply());
		modelAndView.addObject("classes",getOrgTypes());
		return modelAndView;
	}
	
	
	@RequestMapping(value="/editOrganizationApply/{id}", method=RequestMethod.GET)
	public ModelAndView editOrganizationPage(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView("security/organizationApply/organizationApply-add");
		OrganizationApply organizationApply = organizationApplyService.getOrganizationApply(id);
		String orgName = organizationService.getOrgName(organizationApply.getUpperCode());
		modelAndView.addObject("organizationApply",organizationApply);
		modelAndView.addObject("classes",getOrgTypes());
		List<Map<String,String>> list = getStatus();
		Map<String,String> map = list.get(0);
		if (map!=null) {
			if(map.get("statusId").equals("0")){
				list.remove(map);
			}
		}
		modelAndView.addObject("statuses",list);
		modelAndView.addObject("orgName",orgName);
		logger.info("修改申请机构  editOrganizationPage "+organizationApply.getOrgName());
		return modelAndView;
		
	}
	
	@RequestMapping(value="/auditOrganizationApply", method=RequestMethod.GET)
	@ResponseBody
	public String auditOrganizationPage(Long id,Integer orgStatus) {
		logger.info("审核申请机构  editOrganizationPage "+id);
		OrganizationApply organizationApply = organizationApplyService.getOrganizationApply(id);
		organizationApply.setOrgStatus(orgStatus);
		organizationApplyService.addOrganizationApply(organizationApply);
		return "1";
	}

	@RequestMapping(value = "/saveOrganizationApply", method = RequestMethod.POST)
	@Transactional
	public ModelAndView saveOrganization(@ModelAttribute OrganizationApply organizationApply,HttpServletRequest request) {
		logger.info("保存申请机构  saveOrganization "+organizationApply.getOrgName());
		ModelAndView modelAndView = new ModelAndView("redirect:/organizationApply/list/");
		organizationApply.setModifyTime(new Date());
		organizationApplyService.addOrganizationApply(organizationApply);
		if(isSaveSuccess(organizationApply.getId())){//
			Mail mail = new Mail();
	        mail.setHost("smtp.163.com"); // 设置邮件服务器,如果不用163的,自己找找看相关的  &&StringUtils.isNotBlank(organizationApply.getLoginName())
	        mail.setSender(Constant.EMAIL_NAME);  
	        mail.setName("复合出版数据传递平台");
	        mail.setReceiver(organizationApply.getOrgContactEmail()); 
	        mail.setUsername(Constant.EMAIL_NAME); 
	        mail.setPassword(Constant.EMAIL_PWD); 
	        mail.setSubject("您的机构申请审核完毕（复合出版数据传递平台）");  
	        String htmlMessage = "";
	        String code = "";
	        if(StringUtils.isNotBlank(organizationApply.getOrgCode())){
	        	code = organizationApply.getOrgCode();
	        }else{
	        	code = organizationApply.getBeforeCode();
	        }
			if(StringUtils.isNotBlank(code)&&organizationApply.getOrgStatus()==1){//审核通过
				if(StringUtils.isBlank(organizationApply.getOrgCode())){
					organizationApply.setOrgCode(organizationApply.getBeforeCode());
				}
				organizationApplyService.addOrganizationApply(organizationApply);
				
				Organization organization = new Organization();
				organization.setOrgName(organizationApply.getOrgName());
				organization.setOrgCode(organizationApply.getOrgCode());
				organization.setOrgType(organizationApply.getOrgType());
				organization.setPostalcode(organizationApply.getPostalcode());
				organization.setTelephone(organizationApply.getTelephone());
				organization.setOrgContacter(organizationApply.getOrgContacter());
				organization.setOrgContactPhone(organizationApply.getOrgContactPhone());
				organization.setOrgContactEmail(organizationApply.getOrgContactEmail());
				organization.setOrgContent(organizationApply.getOrgContent());
				organization.setProvince(organizationApply.getProvince());
				organization.setCity(organizationApply.getCity());
				organization.setCounty(organizationApply.getCounty());
				organization.setOrgAddress(organizationApply.getOrgAddress());
				organization.setModifyTime(new Date());
				organization.setUrl(organizationApply.getUrl());
				organization.setUpperCode(organizationApply.getUpperCode());
				organization.setOrgWebsit(organizationApply.getOrgWebsit());
				organization.setOrgEconomic(organizationApply.getOrgEconomic());
				organization.setIsbn(organizationApply.getIsbn());
				organization.setLoginName(organizationApply.getLoginName());
				organization.setPassWord(organizationApply.getPassWord());
				organization.setFtpAddress(organizationApply.getFtpAddress());
				organization.setFtpName(organizationApply.getFtpName());
				organization.setFtpPassWord(organizationApply.getFtpPassWord());
				organization.setFirstOrgName(organizationApply.getFirstOrgName());
				organization.setSecondOrgName(organizationApply.getSecondOrgName());
				organizationService.addOrganization(organization);
				
				
				//机构申请审核日志
				String operate = "系统管理审核机构申请："+ organizationApply.getOrgName();
				LogInfo logInfo = BaseUtil.addOperateLog(request, operate);
				if(null != logInfo){
					sysLogInfoService.addLogInfo(logInfo);
				}
				
				User user = new User();//申请机构登录用户
				user.setLoginName(organizationApply.getLoginName());
				//密码是登录名和机构代码前三位
				int a = (int)(Math.random()*(999999-100000+1))+100000;//随机四位数
				//2017.9.11   登录名最少2位 ，整个密码最少8位，所以取6位随机数 
				//String password = organizationApply.getLoginName()+organizationApply.getOrgCode().substring(0, 4);
				String password = organizationApply.getLoginName()+a;
				user.setPassWord(JetsenPasswordUtil.getMD5String(password));
				user.setName(organizationApply.getOrgContacter());
				user.setEmail(organizationApply.getOrgContactEmail());
				
				//平台管理员角色ID
				List<Role> roleList = new ArrayList<Role>();
				if("1".equals(organization.getOrgType())){//出版  19
					roleList.add(roleService.getRole(19L));
				}else if("2".equals(organization.getOrgType())){//图书馆 20
					roleList.add(roleService.getRole(20L));
				}else if("3".equals(organization.getOrgType())||"5".equals(organization.getOrgType())){//发行、零售 23
					roleList.add(roleService.getRole(23L));
				}else if("4".equals(organization.getOrgType())){//加工 27
					roleList.add(roleService.getRole(27L));
				}
				//if(roleList!=null&&roleList.size()>0){
					user.setOrganization(organization);
				    user.setRoleList(roleList);
					userService.addUser(user);
					//发送邮件
			        htmlMessage = "<b>尊敬的"+organization.getOrgName()+"用户:</b><br/><b>&nbsp;&nbsp;您的机构申请已通过审核,登录账户："+user.getLoginName()+",密码："+password+"。" ;
			        		//",ftp用户名："+organization.getFtpName()+",ftp密码："+organization.getFtpPassWord()+",ftp地址："+organization.getFtpAddress()+"。</b>";
			        mail.setMessage(htmlMessage);  
			        new MailUtil().send(mail); 
				//}
			}
			if(organizationApply.getOrgStatus()==2){
		        htmlMessage = "<b>尊敬的"+organizationApply.getOrgName()+"用户:</b><br/><b>&nbsp;&nbsp;您的机构申请未通过审核。";
		        mail.setMessage(htmlMessage);  
		        new MailUtil().send(mail); 
			}
			modelAndView.addObject("message",  "1");
		}else{
			modelAndView.addObject("message",  "0");
		}
		
		return modelAndView;
	}
	

	@RequestMapping(value="/deleteOrganizationApply/{id}", method=RequestMethod.GET)
	public ModelAndView deleteOrganization(@PathVariable Long id,HttpServletRequest request) {
		logger.info("删除申请机构  deleteOrganization "+id);
		//软件符合性测试日志
		OrganizationApply organizationApply = organizationApplyService.getOrganizationApply(id);
		String operate = "系统管理删除机构申请："+ organizationApply.getOrgName();
		LogInfo logInfo = BaseUtil.addOperateLog(request, operate);
		if(null != logInfo){
			sysLogInfoService.addLogInfo(logInfo);
		}
		
		ModelAndView modelAndView = new ModelAndView("redirect:/organizationApply/list/");
		organizationApplyService.deleteOrganizationApply(id);
		modelAndView.addObject("message",  "1");
		return modelAndView;
	}
	
	@RequestMapping(value="/deleteOrganizationApplys/{idsStr}", method=RequestMethod.GET)
	public ModelAndView deleteOrganizations(@PathVariable String idsStr,HttpServletRequest request) {
		logger.info("删除申请机构共  deleteOrganizations "+idsStr);
		ModelAndView modelAndView = new ModelAndView("redirect:/organizationApply/list/");
		Long[] ids = getIdArray(idsStr);
		for(Long id : ids){
			OrganizationApply organizationApply = organizationApplyService.getOrganizationApply(id);
			String operate = "系统管理删除机构申请："+ organizationApply.getOrgName();
			LogInfo logInfo = BaseUtil.addOperateLog(request, operate);
			if(null != logInfo){
				sysLogInfoService.addLogInfo(logInfo);
			}
		}
		organizationApplyService.deleteOrganizationApplys(ids);
		modelAndView.addObject("message",  "1");
		return modelAndView;
	}
	
	
	/*private List<Map<String,String>> getStatus(){
		List<Map<String,String>> list = new ArrayList<>();
		/*
		Map<String,String> map1 = new HashMap<String,String>();
		map1.put("statusId", "0");
		map1.put("statusName", "待审核");
		list.add(map1);
		*/
	/*	Map<String,String> map2 = new HashMap<String,String>();
		map2.put("statusId", "1");
		map2.put("statusName", "审核通过");
		list.add(map2);
		
		Map<String,String> map3 = new HashMap<String,String>();
		map3.put("statusId", "2");
		map3.put("statusName", "审核不通过");
		list.add(map3);
		
		return list;
	}*/

}