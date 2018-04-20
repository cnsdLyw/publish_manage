package com.litc.security.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.litc.security.common.page.PageParam;
import com.litc.security.model.Authority;
import com.litc.security.model.LogInfo;
import com.litc.security.model.Role;
import com.litc.security.service.AuthorityService;
import com.litc.security.service.BaseUtil;
import com.litc.security.service.RoleService;
import com.litc.security.service.SysLogInfoService;

@Controller
@RequestMapping(value = "/role")
public class RoleController extends BaseController<Role>{
	
	private final static Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	/**
	 * 每页数量
	 */
	private static final int ROLEPAGESIZE = 10;
	
	/**
	 * 排序字段
	 */
	private static String ORDER_TYPE = "lastModifyTime";
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private SysLogInfoService sysLogInfoService;
	
	@Autowired
	private AuthorityService authorityService;

	@RequestMapping(value="/getRole", method=RequestMethod.GET)
	public Role getRole(Long id){
		logger.info("查询角色  getRole "+id);
		Role role = roleService.getRole(id);
		return role;
	}
	
	@RequestMapping(value="/getJsonRole", method=RequestMethod.GET)
	@ResponseBody
	public Role getJsonRole(Long id){
		logger.info("查询角色  getJsonRole "+id);
		Role role = roleService.getRole(id);
		return role;
	}
	
	@RequestMapping(value = "/addRole", method = RequestMethod.GET)
	public ModelAndView addRolePage() {
		logger.info("添加角色  getJsonRole ");
		ModelAndView modelAndView = new ModelAndView("security/role/role-add");
		modelAndView.addObject("role", new Role());
		//获取所有启用角色
		List<Authority> authorities = authorityService.getAuthoritysByType(3);
		modelAndView.addObject("authorities",authorities);
		modelAndView.addObject("orgTypes",getOrgTypes());
		return modelAndView;
	}
	
	
	@RequestMapping(value="/editRole/{id}", method=RequestMethod.GET)
	public ModelAndView editRolePage(@PathVariable Long id, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("security/role/role-add");
		Role role = roleService.getRole(id);
		//获取所有启用角色
		List<Authority> authorities = authorityService.getAuthoritysByType(3);
		List<Authority> useAuthorities = role.getAuthorityList();
		for(Authority authority:authorities){
			if(useAuthorities.contains(authority)){
				authority.setStatus(2);
			}
		}
		modelAndView.addObject("authorities",authorities);
		modelAndView.addObject("role",role);
		modelAndView.addObject("orgTypes",getOrgTypes());
		logger.info("查询角色  getJsonRole "+role.getRoleName());
		return modelAndView;
		
	}

	@RequestMapping(value = "/saveRole", method = RequestMethod.POST)
	public ModelAndView saveRole(@ModelAttribute Role role,HttpServletRequest request) {
		String operate = "";
		if(role.getId()==null){
			operate = "系统管理添加角色:";
		}else{
			operate = "系统管理编辑角色:";
		}
		
		logger.info("查询角色  saveRole "+role.getRoleName());
		ModelAndView modelAndView = new ModelAndView("redirect:/role/list/");
		String[] authorityIds = request.getParameterValues("authorityIds");
		List<Authority> authorityList = new ArrayList<Authority>();
		if(authorityIds!=null){
			for(String id:authorityIds){
				authorityList.add(authorityService.getAuthority(Long.parseLong(id)));
			}
		}
		
		//流程操作权限
		String[] flowAuthorityIds = request.getParameterValues("flow_authorityIds");
		if(flowAuthorityIds!=null){
			for(String id:flowAuthorityIds){
				authorityList.add(authorityService.getAuthority(Long.parseLong(id)));
			}
		}
		
		role.setAuthorityList(authorityList);
		roleService.addRole(role);
		if(isSaveSuccess(role.getId())){
			modelAndView.addObject("message",  "1");
			//系统管理角色操作日志
			operate = operate + role.getRoleName();
			LogInfo logInfo = BaseUtil.addOperateLog(request, operate);
			if(null != logInfo){
				sysLogInfoService.addLogInfo(logInfo);
			}
		}else{
			modelAndView.addObject("message",  "0");
		}
		
		return modelAndView;
	}
	
	@RequestMapping(value="/deleteRole/{id}", method=RequestMethod.GET)
	public ModelAndView deleteRole(@PathVariable Long id,HttpServletRequest request) {
		logger.info("删除角色  deleteRole "+id);
		ModelAndView modelAndView = new ModelAndView("redirect:/role/list/");
		//if(!(roleService.isRoleUsedByUser(id)>0||roleService.isRoleUsed(id)>0)){
		if(!(roleService.isRoleUsedByUser(id)>0)){
			//系统管理角色操作日志
			Role role = roleService.getRole(id);
			String operate = "系统管理删除角色：" + role.getRoleName();
			LogInfo logInfo = BaseUtil.addOperateLog(request, operate);
			if(null != logInfo){
				sysLogInfoService.addLogInfo(logInfo);
			}
			
			roleService.deleteRole(id);
			modelAndView.addObject("message",  "1");
		}else{
			modelAndView.addObject("message",  "-1");
		}
	
		return modelAndView;
	}
	
	@RequestMapping(value="/deleteRoles/{ids}", method=RequestMethod.GET)
	public ModelAndView deleteRoles(@PathVariable String ids,HttpServletRequest request) {
		logger.info("删除角色  deleteRole "+ids);
		ModelAndView modelAndView = new ModelAndView("redirect:/role/list/");
		Long[] id=getIdArray(ids);
		//>0 表示角色和用户有关联                                             >0表示角色和权限有关联 
		if(!(roleService.isRoleUsedByUser(id)>0)){
			//系统管理角色操作日志
			for(Long Id:id){
				Role role = roleService.getRole(Id);
				String operate = "系统管理删除角色：" + role.getRoleName();
				LogInfo logInfo = BaseUtil.addOperateLog(request, operate);
				if(null != logInfo){
					sysLogInfoService.addLogInfo(logInfo);
				}
			}
			
			
			int returnI = roleService.deleteRoles(id);
			modelAndView.addObject("message",  "1");
		}else{
			modelAndView.addObject("message",  "-1");
		}
		return modelAndView;
	}
	@RequestMapping(value = "/list")
	public ModelAndView listOfRoles(HttpServletRequest request,String orderType,String sortType,String keyWord) {
		ModelAndView modelAndView = new ModelAndView("security/role/role-list");
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
		page = roleService.getRolesByPages(pageNo, ROLEPAGESIZE,sortDirection,orderType, keyWord);
		modelAndView.addObject("keyWord",keyWord);
		modelAndView.addObject("pageContent",page);
		return modelAndView;
	}
	
	@RequestMapping(value="/isRoleExist", method=RequestMethod.GET)
	@ResponseBody
	public boolean isRoleExist(String roleName){
		return roleService.isRoleExist(roleName);
	}
	
	@RequestMapping(value="/isRoleExistWithId", method=RequestMethod.GET)
	@ResponseBody
	public boolean isRoleExist(Long id,String roleName){
		return roleService.isRoleExist(id, roleName);
	}

}
