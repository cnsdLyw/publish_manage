package com.litc.security.controller;

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

import com.litc.common.util.UserCacheManager;
import com.litc.security.model.Authority;
import com.litc.security.model.LogInfo;
import com.litc.security.model.Role;
import com.litc.security.service.AuthorityService;
import com.litc.security.service.BaseUtil;
import com.litc.security.service.RoleService;
import com.litc.security.service.SysLogInfoService;

@Controller
@RequestMapping("/authority")
public class AuthorityController extends BaseController<Authority>{

	private final static Logger logger = LoggerFactory.getLogger(AuthorityController.class);
	
	/**
	 * 每页数量
	 */
	private static final int AUTHORITYPAGESIZE = 10;
	
	/**
	 * 排序字段
	 */
	private static String ORDER_TYPE = "lastModifyTime";
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private SysLogInfoService sysLogInfoService;

	@RequestMapping(value = "/list")
	public ModelAndView listOfAuthority(HttpServletRequest request,String orderType,String sortType,String keyWord) {
		ModelAndView modelAndView = new ModelAndView("security/authority/authority-list");
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
		page = authorityService.getAuthoritiesByPages(pageNo, AUTHORITYPAGESIZE,sortDirection,orderType,keyWord);
		modelAndView.addObject("keyWord",keyWord);
		modelAndView.addObject("pageContent",page);
		
		
		modelAndView.addObject("flag", this.getString(request, "flag"));
		//modelAndView.addObject("flag", 1);
		return modelAndView;
	}
	
	@RequestMapping(value="/getAuthority", method=RequestMethod.GET)
	public Authority getAuthority(Long id){
		logger.info("查询权限  getAuthority "+id);
		Authority authority = authorityService.getAuthority(id);
		return authority;
	}
	
	@RequestMapping(value="/getJsonAuthority", method=RequestMethod.GET)
	@ResponseBody
	public Authority getJsonAuthority(Long id){
		logger.info("查询权限  getJsonAuthority "+id);
		Authority authority = authorityService.getAuthority(id);
		return authority;
	}
	
	@RequestMapping(value = "/addAuthority", method = RequestMethod.GET)
	public ModelAndView addAuthorityPage() {
		logger.info("添加权限  getJsonAuthority ");
		ModelAndView modelAndView = new ModelAndView("security/authority/authority-add");
		modelAndView.addObject("authority", new Authority());
		return modelAndView;
	}
	
	
	@RequestMapping(value="/editAuthority/{id}", method=RequestMethod.GET)
	public ModelAndView editAuthorityPage(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView("security/authority/authority-add");
		Authority authority = authorityService.getAuthority(id);
		modelAndView.addObject("authority",authority);
		logger.info("编辑权限  editAuthorityPage "+authority.getAuthorityKey());
		return modelAndView;
		
	}

	@RequestMapping(value = "/saveAuthority", method = RequestMethod.POST)
	public ModelAndView saveAuthority(@ModelAttribute Authority authority,HttpServletRequest request) {
		String operate = "";
		if(authority.getId()==null){
			operate = "系统管理添加权限:";
		}else{
			operate = "系统管理编辑权限:";
		}
		
		logger.info("保存权限  saveAuthority "+authority.getAuthorityKey());
		ModelAndView modelAndView = new ModelAndView("redirect:/authority/list/");
		authorityService.addAuthority(authority);
		if(isSaveSuccess(authority.getId())){
			modelAndView.addObject("message",  "1");
			//系统管理权限操作日志
			operate = operate + authority.getAuthorityName();
			LogInfo logInfo = BaseUtil.addOperateLog(request, operate);
			if(null != logInfo){
				sysLogInfoService.addLogInfo(logInfo);
			}
		}else{
			modelAndView.addObject("message",  "0");
		}
		
		return modelAndView;
	}
	

	@RequestMapping(value="/isAuthorityUsed", method=RequestMethod.GET)
	@ResponseBody
	public Authority isAuthorityUsed(Long id){
		//Authority authority = authorityService.getAuthority(id);
		//return authority;
		return null;
	}
	

	@RequestMapping(value="/deleteAuthority/{id}", method=RequestMethod.GET)
	public ModelAndView deleteAuthority(@PathVariable Long id,HttpServletRequest request) {
		logger.info("删除权限  deleteAuthority "+id);
		ModelAndView modelAndView = new ModelAndView("redirect:/authority/list/");
		if(!(authorityService.isAuthorityUsed(id)>0)){
			//系统管理权限操作日志
			Authority authority = authorityService.getAuthority(id);
			String operate = "系统管理删除权限：" + authority.getAuthorityName();
			LogInfo logInfo = BaseUtil.addOperateLog(request, operate);
			if(null != logInfo){
				sysLogInfoService.addLogInfo(logInfo);
			}
			
			authorityService.deleteAuthority(id);
			modelAndView.addObject("message",  "1");
		}else{
			modelAndView.addObject("message",  "-1");
		}
	
		return modelAndView;
	}
	
	@RequestMapping(value="/deleteAuthoritys/{ids}", method=RequestMethod.GET)
	public ModelAndView deleteAuthoritys(@PathVariable String ids,HttpServletRequest request) {
		logger.info("删除权限  deleteAuthoritys "+ids);
		ModelAndView modelAndView = new ModelAndView("redirect:/authority/list/");
		Long[] id=getIdArray(ids);
		if(!(authorityService.isAuthorityUsed(id)>0)){
			for(Long Id : id){
				//系统管理权限操作日志
				Authority authority = authorityService.getAuthority(Id);
				String operate = "系统管理删除权限：" + authority.getAuthorityName();
				LogInfo logInfo = BaseUtil.addOperateLog(request, operate);
				if(null != logInfo){
					sysLogInfoService.addLogInfo(logInfo);
				}
			}
			authorityService.deleteAuthoritys(id);
			modelAndView.addObject("message",  "1");
		}else{
			modelAndView.addObject("message",  "-1");
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/isAuthorityKeyExist", method=RequestMethod.GET)
	@ResponseBody
	public boolean isAuthorityKeyExist(String authorityKey){
		return authorityService.isAuthorityKeyExist(authorityKey);
	}
	@RequestMapping(value="/isAuthorityKeyExistWithId", method=RequestMethod.GET)
	@ResponseBody
	public boolean isAuthorityKeyExist(Long id,String authorityKey){
		return authorityService.isAuthorityKeyExist(id,authorityKey);
	}
	
	@RequestMapping(value="/isAuthorityNameExist", method=RequestMethod.POST)
	@ResponseBody
	public boolean isAuthorityNameExist(String authorityName){
		return authorityService.isAuthorityNameExist(authorityName);
	}
	
	@RequestMapping(value="/isAuthorityNameExistWithId", method=RequestMethod.POST)
	@ResponseBody
	public boolean isAuthorityNameExist(Long id,String authorityName){
		return authorityService.isAuthorityNameExist(id,authorityName);
	}
	
	@RequestMapping(value="/getAuthoritysByType", method=RequestMethod.GET)
	@ResponseBody
	public List<Authority> getAuthoritysByType(Long roleId, int type){
		logger.info("查询权限  getAuthoritys roleId="+roleId+", type="+type);
		List<Authority> list =  authorityService.getAuthoritysByType(type);
		if(roleId!=null&&roleId!=0){
			Role role = roleService.getRole(roleId);
			if(role!=null){
				//获取所有启用角色
				List<Authority> useAuthorities = role.getAuthorityList();
				for(Authority authority:list){
					if(useAuthorities.contains(authority)){
						authority.setStatus(2);
					}
				}
			}
		}
		
		return list;
		
	}
	
	@RequestMapping(value="/refreshAuthority", method=RequestMethod.GET)
	@ResponseBody
	public Integer refreshAuthority(String orgCode) throws InterruptedException{
		logger.info("刷新系统权限  refreshAuthority orgCode = "+orgCode);
		Thread.sleep(1800);
		if(StringUtils.isNotBlank(orgCode)){
			UserCacheManager.loadUserAuthorityCache(orgCode);
		}else{
			UserCacheManager.loadUserAuthorityCache();
		}
		
		Thread.sleep(1800);
		return 1;
	}
	
	/*
	@RequestMapping(value = "/list")
	public ModelAndView listOfAuthority(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("security/authority/authority-list");
		pageNo = this.getInt(request, "pageNo");
		page = authorityService.getUserByPages(pageNo,AUTHORITYPAGESIZE);
		modelAndView.addObject("pageContent",page);
		
		return modelAndView;
	}
	*/
	
}