package com.litc.security.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.litc.common.util.Constant;
import com.litc.security.model.LogInfo;
import com.litc.security.model.Organization;
import com.litc.security.model.Role;
import com.litc.security.model.User;
import com.litc.security.service.BaseUtil;
import com.litc.security.service.OrganizationApplyService;
import com.litc.security.service.OrganizationService;
import com.litc.security.service.RoleService;
import com.litc.security.service.SysLogInfoService;
import com.litc.security.service.UserService;


/**
 * 用户操作
 * @author liyw
 *
 */
@Controller
@RequestMapping(value="/user")
public class UserController extends BaseController<User>{
	
	private final static Logger logger = LoggerFactory.getLogger(UserController.class);
	
	/**
	 * 每页数量
	 */
	private static final int USERPAGESIZE = 10;
	
	/**
	 * 排序字段
	 */
	private static String ORDER_TYPE = "lastModifyTime";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SysLogInfoService sysLogInfoService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private OrganizationApplyService organizationApplyService;

	@RequestMapping(value="/getUser", method=RequestMethod.GET)
	public User getUser(Long id){
		logger.info("查询用户  getUser "+id);
		User user = userService.getUser(id);
		return user;
	}
	
	@RequestMapping(value="/getJsonUser", method=RequestMethod.GET)
	@ResponseBody
	public User getJsonUser(Long id){
		logger.info("查询用户  getJsonUser "+id);
		User user = userService.getUser(id);
		return user;
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public ModelAndView addUserPage(HttpServletRequest request) {
		logger.info("添加用户  addUser ");
		ModelAndView modelAndView = new ModelAndView("security/user/user-add");
		String loginRole = (String)request.getSession().getAttribute("loginRole");
		modelAndView.addObject("user", new User());
		//获取角色
		List<Role> roles = null;
		//修改用户权限时出版/图书馆/发行/加工平台只展示自己的角色，否则全部展示
		if(StringUtils.isNotBlank(loginRole)&&(loginRole.equals("1")||loginRole.equals("2")||loginRole.equals("3")
				||loginRole.equals("4")||loginRole.equals("5"))){
			loginRole = "3";
			roles = roleService.getRoles(loginRole);
		}else{
			roles = roleService.getRoles();
		}
		//List<Role> roles = roleService.getRoles();
		modelAndView.addObject("roles",roles);
		//获取所有机构
		List<Organization> organizations = organizationService.getOrganizations();
		modelAndView.addObject("organizations",organizations);
		modelAndView.addObject("roleStr",loginRole);
		return modelAndView;
	}
	
	
	@RequestMapping(value="/editUser/{id}", method=RequestMethod.GET)
	public ModelAndView editUserPage(@PathVariable Long id, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("security/user/user-add");
		String loginRole = (String)request.getSession().getAttribute("loginRole");
		User user = userService.getUser(id);
		modelAndView.addObject("user",user);
		//获取所有角色
		//List<Role> roles = roleService.getRoles();
		//获取角色
		List<Role> roles = null;
		//修改用户权限时出版/图书馆/发行/加工平台只展示自己的角色，否则全部展示
		if(StringUtils.isNotBlank(loginRole)&&(loginRole.equals("1")||loginRole.equals("2")||loginRole.equals("3")
				||loginRole.equals("4"))){
			roles = roleService.getRoles(loginRole);
		}else{
			roles = roleService.getRoles();
		}
		
		List<Role> useRoles = user.getRoleList();
		/*for(Role role:roles){
			if(useRoles.contains(role)){
				role.setStatus(2);
			}
		}*/
		modelAndView.addObject("roles",roles);
		modelAndView.addObject("useRoles",useRoles);		
		//获取所有机构
		List<Organization> organizations = organizationService.getOrganizations();
		modelAndView.addObject("organizations",organizations);
		modelAndView.addObject("roleStr",loginRole);
		logger.info("编辑用户  editUser "+user.getLoginName());
		return modelAndView;
		
	}
	
	@RequestMapping(value="/editPassword/{id}", method=RequestMethod.GET)
	public ModelAndView editPassword(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView("security/user/user-editPassword");
		User user = userService.getUser(id);
		modelAndView.addObject("user",user);
		logger.info("修改用户密码  editPassword "+user.getLoginName());
		return modelAndView;
		
	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public ModelAndView saveUser(@ModelAttribute User user,HttpServletRequest request,String orgCode) {
		String operate = "";
		logger.info("保存用户  "+user.getLoginName());
		ModelAndView modelAndView = new ModelAndView("redirect:/user/list/");
		if(user.getId()==null){
			//默认密码是登录名
			//Md5PasswordEncoder md5 = new Md5PasswordEncoder(); 
			//user.setPassWord(md5.encodePassword(user.getLoginName(), null));
			operate = "系统管理添加用户：";
			Md5PasswordEncoder md5 = new Md5PasswordEncoder(); 
			String passWord = md5.encodePassword(user.getPassWord(), null);
			user.setPassWord(passWord);
		}else{
			//密码
			operate = "系统管理编辑用户：";
			User temp = userService.getUser(user.getId());
			user.setPassWord(temp.getPassWord());
		}
		//角色
		String[] roleIds = request.getParameterValues("roleIds");
		List<Role> roleList = new ArrayList<Role>();
		if(roleIds!=null){
			for(String id:roleIds){
				roleList.add(roleService.getRole(Long.parseLong(id)));
			}
		}
		user.setRoleList(roleList);
		//机构
		if(StringUtils.isNotBlank(orgCode)){
			Organization organization = organizationService.getOrganization(orgCode);
			user.setOrganization(organization);
		}
		
		
		userService.addUser(user);
		if(isSaveSuccess(user.getId())){
			modelAndView.addObject("message",  "1");
			//系统管理用户操作日志
			operate = operate + user.getName();
			LogInfo logInfo = BaseUtil.addOperateLog(request, operate);
			if(null != logInfo){
				sysLogInfoService.addLogInfo(logInfo);
			}
		}else{
			modelAndView.addObject("message",  "0");
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/saveUserPassword", method = RequestMethod.POST)
	public ModelAndView saveUserPassword(Long id,HttpServletRequest request) {
		User user = userService.getUser(id);
		String passWord = request.getParameter("passWord");
		logger.info("修改保存用户密码  "+user.getLoginName());
		ModelAndView modelAndView = new ModelAndView("redirect:/user/list/");
		Md5PasswordEncoder md5 = new Md5PasswordEncoder(); 
		user.setPassWord(md5.encodePassword(passWord, null));
		userService.addUser(user);
		if(isSaveSuccess(user.getId())){
			modelAndView.addObject("message",  "1");
			//系统管理用户操作日志
			String operate = "系统管理修改密码:" + user.getName();
			LogInfo logInfo = BaseUtil.addOperateLog(request, operate);
			if(null != logInfo){
				sysLogInfoService.addLogInfo(logInfo);
			}
		}else{
			modelAndView.addObject("message",  "0");
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value="/checkPassword", method=RequestMethod.GET)
	@ResponseBody
	public boolean checkPassword(Long id,String oldPassWord){
		boolean flag = false;
		User user = userService.getUser(id);
		logger.info("验证密码  checkPassword "+user.getLoginName());
		if(StringUtils.isNotBlank(oldPassWord)){
			if(user.getPassWord().equals(oldPassWord)){
				flag = true;
			}
		}
		
		return flag;
	}

	@RequestMapping(value="/isUserUsed", method=RequestMethod.GET)
	@ResponseBody
	public User isUserUsed(Long id){
		//User user = userService.getUser(id);
		//return user;
		return null;
	}
	

	@RequestMapping(value="/deleteUser/{id}", method=RequestMethod.GET)
	public ModelAndView deleteUser(@PathVariable Long id,HttpServletRequest request) {
		logger.info("删除用户  deleteUser "+id);
		ModelAndView modelAndView = new ModelAndView("redirect:/user/list/");
		
		//系统管理用户操作日志
		User user = userService.getUser(id);
		String operate = "系统管理删除用户：" + user.getName();
		LogInfo logInfo = BaseUtil.addOperateLog(request, operate);
		if(null != logInfo){
			sysLogInfoService.addLogInfo(logInfo);
		}
		/*if(!(userService.isUserHasRole(id)>0)){
			userService.deleteUser(id);
			modelAndView.addObject("message",  "1");
		}else{
			modelAndView.addObject("message",  "-1");
		}*/
		userService.deleteUser(id);
		return modelAndView;
	}
	
	@RequestMapping(value="/deleteUsers/{ids}", method=RequestMethod.GET)
	public ModelAndView deleteUsers(@PathVariable String ids,HttpServletRequest request) {
		logger.info("删除用户  deleteUsers "+ids);
		ModelAndView modelAndView = new ModelAndView("redirect:/user/list/");
		Long[] id=getIdArray(ids);
		//系统管理用户操作日志
		for(Long Id:id){
			User user = userService.getUser(Id);
			String operate = "系统管理删除用户：" + user.getName();
			LogInfo logInfo = BaseUtil.addOperateLog(request, operate);
			if(null != logInfo){
				sysLogInfoService.addLogInfo(logInfo);
			}
		}
		
		/*
		//>0 表示角色和用户有关联                                             >0表示角色和权限有关联 
		if(!(userService.isUserHasRole(id)>0)){
			userService.deleteUsers(id);
			modelAndView.addObject("message",  "1");
		}else{
			modelAndView.addObject("message",  "-1");
		}
		*/
		
		userService.deleteUsers(id);
		return modelAndView;
	}
	
	@RequestMapping(value="/list")
	public ModelAndView listOfUsers(HttpServletRequest request,String orderType,String sortType, String keyWord) {
		ModelAndView modelAndView = new ModelAndView("security/user/user-list");
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
		
		if(StringUtils.isBlank(orderType)){
			orderType = ORDER_TYPE;
		}
		modelAndView.addObject("orderType",orderType);
		modelAndView.addObject("sortType",sortType);
		
		//创建查询参数
		String orgCode = (String)request.getSession().getAttribute("loginOrgCode");
		page = userService.getUsersByPages(pageNo, USERPAGESIZE, sortDirection,orderType,orgCode, keyWord);
		modelAndView.addObject("pageContent",page);
		modelAndView.addObject("keyWord",keyWord);
		modelAndView.addObject("orderType",orderType);
		modelAndView.addObject("sortType",sortType);
		return modelAndView;
		
	}

	@RequestMapping(value="/isLoginNameExist", method=RequestMethod.GET)
	@ResponseBody
	public boolean isLoginNameExist(String loginName){
		return userService.isLoginNameExist(loginName)||organizationApplyService.isLoginNameExist(loginName);
	}
	
	@RequestMapping(value="/isEmailExist", method=RequestMethod.GET)
	@ResponseBody
	public boolean isEmailExist(String email){
		return userService.isEmailExist(email)||organizationApplyService.isEmailExist(email);
	}
	
	@RequestMapping(value="/isEmailExistWithId", method=RequestMethod.GET)
	@ResponseBody
	public boolean isEmailExist(Long id,String email){
		return userService.isEmailExist(id,email);
	}
	
}
