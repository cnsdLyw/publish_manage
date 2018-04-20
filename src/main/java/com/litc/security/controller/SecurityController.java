package com.litc.security.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.litc.common.util.UserCacheManager;
import com.litc.security.model.LogInfo;
import com.litc.security.model.Organization;
import com.litc.security.model.OrganizationApply;
import com.litc.security.model.Role;
import com.litc.security.model.User;
import com.litc.security.service.OrganizationApplyService;
import com.litc.security.service.OrganizationService;
import com.litc.security.service.RoleService;
import com.litc.security.service.SysLogInfoService;
import com.litc.security.service.UserService;

@Controller
@RequestMapping("/security")
public class SecurityController extends BaseController{

	private final static Logger logger = LoggerFactory
			.getLogger(SecurityController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private SysLogInfoService sysInfoService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private OrganizationApplyService organizationApplyService;

	@RequestMapping(value = "/setOrgType", method = RequestMethod.GET)
	public ModelAndView setOrgType(String loginName){
		logger.info("设置SSO用户所属机构类别  regOrganizationPage loginName = "+loginName);
		ModelAndView modelAndView = new ModelAndView("security/chooseOrgType");
		modelAndView.addObject("loginName", loginName);
		modelAndView.addObject("classes",getOrgTypes());
		return modelAndView;
	}
	
	@RequestMapping(value = "/saveOrgType", method = RequestMethod.GET)
	public ModelAndView saveOrgType(String orgType, String loginName){
		logger.info("保存SSO用户所属机构类别信息和设置用户权限  regOrganizationPage  orgType = "+orgType+"，loginName="+loginName);
		ModelAndView modelAndView = new ModelAndView("/index_error_alert");
		User user =  userService.loadUser(loginName);
		String orgCode = user.getOrganization().getOrgCode();
		Organization organization = organizationService.getOrganizationByCode(orgCode);
		organization.setOrgType(orgType);
		organizationService.addOrganization(organization);
		//平台管理员角色ID
		List<Role> roleList = new ArrayList<Role>();
		if("1".equals(organization.getOrgType())){//出版  19
			roleList.add(roleService.getRole(19L));
		}else if("2".equals(organization.getOrgType())){//图书馆 20
			roleList.add(roleService.getRole(20L));
		}else if("3".equals(organization.getOrgType())||"5".equals(organization.getOrgType())){//发行、零售 23
			roleList.add(roleService.getRole(23L));
		}else if("4".equals(organization.getOrgType())||"6".equals(organization.getOrgType())){//加工 27
			roleList.add(roleService.getRole(27L));
		}	
		user.setRoleList(roleList);
		userService.addUser(user);
		modelAndView.addObject("errorMessage", "退出重新登录");
		//刷新权限
		UserCacheManager.loadUserAuthorityCache();
		return modelAndView;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
			@RequestParam(value = "errorCode", required = false) Integer errorCode,
			@RequestParam(value = "successCode", required = false) Integer successCode,
			Model model,HttpServletRequest request,HttpServletResponse response) {
	    logger.debug("Received request to show login page, error " + errorCode);
		ModelAndView modelAndView = new ModelAndView("security/login");
		if(errorCode!=null){
			String name = "";
			Cookie[] cookies = (Cookie[]) request.getCookies();
            for(Cookie c :cookies ){
                if(c.getName().equals("names")){
                	name = c.getValue();
                	c.setMaxAge(-1);
                	response.addCookie(c);
                }
            }
			
				//日志
				 String ip = request.getHeader("x-forwarded-for"); 
			       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			           ip = request.getHeader("Proxy-Client-IP"); 
			       } 
			       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			           ip = request.getHeader("WL-Proxy-Client-IP"); 
			       } 
			       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			           ip = request.getRemoteAddr(); 
			       }
			       User user = userService.loadUser(name);
			       if(user!=null){
				       String organization_code = user.getOrganization().getOrgCode();
				       
						LogInfo logInfo = new LogInfo();
						logInfo.setOperateIp(ip);
						logInfo.setOperateName("后台登录失败");
						logInfo.setOperateTime(new Date());
						logInfo.setOperateType("login");
						logInfo.setOperateUser(name);
						logInfo.setOrgCode(organization_code);
						sysInfoService.addLogInfo(logInfo);
			       }
				modelAndView.addObject("errorCode",errorCode);
			}
		modelAndView.addObject("successCode",successCode);
		return modelAndView;
	}

	@RequestMapping(value = "/login_jg", method = RequestMethod.GET)
	public ModelAndView jg(
			@RequestParam(value = "errorCode", required = false) Integer errorCode,
			@RequestParam(value = "successCode", required = false) Integer successCode,
			Model model) {
	    logger.debug("Received request to show login page, error " + errorCode);
		ModelAndView modelAndView = new ModelAndView("security/login_jg");
		modelAndView.addObject("errorCode",errorCode);
		modelAndView.addObject("successCode",successCode);
		return modelAndView;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		logger.info("注册用户  regOrganizationPage");
		ModelAndView modelAndView = new ModelAndView("security/register");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public ModelAndView addUser(String email,String loginName,String passWord) {
		ModelAndView modelAndView = new ModelAndView("redirect:/security/login/");
		User user = new User();
		user.setEmail(email);
		user.setLoginName(loginName);
		Md5PasswordEncoder md5 = new Md5PasswordEncoder(); 
		passWord = md5.encodePassword(passWord, null);
		user.setPassWord(passWord);
		userService.addUser(user);
		modelAndView.addObject("successCode", 1);
		logger.info("保存用户  addUser "+user.getLoginName());
		return modelAndView;
	}
	//@ModelAttribute Organization organization
	@RequestMapping(value = "/addOrganizationApply", method = RequestMethod.POST)
	public ModelAndView saveOrganization(@ModelAttribute OrganizationApply organizationApply) {
		organizationApply.setOrgStatus(0);
		organizationApply.setModifyTime(new Date());
		organizationApplyService.addOrganizationApply(organizationApply);
		ModelAndView modelAndView = new ModelAndView("redirect:/security/regOrganization");
		if(isSaveSuccess(organizationApply.getId())){
			modelAndView.addObject("successCode", 1);
		}
		logger.info("保存机构  saveOrganization "+organizationApply.getOrgName());
		return modelAndView;
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("security/index");
		// 获取当前登录用户UserDetails信息
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		request.getSession().setAttribute("loginName",userDetails.getUsername());
		
		//添加机构码session
		/*User user = userService.loadUser(userDetails.getUsername());
	    String organization_code = user.getOrganization().getOrgCode();
		request.getSession().setAttribute("organization_code",organization_code);
		*/
		return modelAndView;
	}
	
	@RequestMapping(value = "/to404", method = RequestMethod.GET)
	public ModelAndView to404() {
	    logger.debug("找不到页面……");
		ModelAndView modelAndView = new ModelAndView("/pages/error/404");
		return modelAndView;
	}
	@RequestMapping(value = "/to403", method = RequestMethod.GET)
	public ModelAndView to403() {
		logger.debug("拒绝访问……");
		ModelAndView modelAndView = new ModelAndView("/pages/error/403");
		return modelAndView;
	}
	
	public boolean isSaveSuccess(Long id){
		if(id!=null&&!id.equals("0")){
			return true ;
		}
		return false ;
	}
}
