package com.litc.security.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.litc.common.util.Constant;
import com.litc.common.util.UserCacheManager;
import com.litc.common.util.file.FilePathUtil;
import com.litc.security.model.LogInfo;
import com.litc.security.model.Organization;
import com.litc.security.model.Supplier;
import com.litc.security.model.User;
import com.litc.security.service.OrganizationService;
import com.litc.security.service.SysLogInfoService;
import com.litc.security.service.UserService;
import com.litc.service.SupplierService;
import com.litc.system.model.Classification;
import com.litc.system.service.ClassificationService;

/**
 * 起始请求路由
 * @author Administrator
 *
 */

@Controller
public class LinkController extends BaseController{
	
	private final static Logger logger = LoggerFactory.getLogger(LinkController.class);
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private SysLogInfoService sysInfoService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private ClassificationService classificationService;
	
	//加工使用的变量
	protected static final Direction SORT_TYPE_DESC = Sort.Direction.DESC;
	protected static final Direction SORT_TYPE_ASC = Sort.Direction.ASC;
	private static String ORDER_TYPE = "id";
	
	@RequestMapping(value="/")
	public ModelAndView mainPage() {
		return new ModelAndView("redirect:/security/login/");
	}
	
	@RequestMapping(value="/jg")
	public ModelAndView jgPage() {
	return new ModelAndView("redirect:/security/login_jg/");
}
	@RequestMapping(value="/index")
	public ModelAndView indexPage(HttpServletRequest request, ModelMap model) {
		Object obj = SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
		if(obj instanceof UserDetails){
			UserDetails userDetails = (UserDetails)obj ;
			if(!(userDetails.getAuthorities()!=null&&userDetails.getAuthorities().size()>0)){
	    		return new ModelAndView("redirect:/security/login?errorCode=2");
	    	}
	    	request.getSession().setAttribute("loginname", userDetails.getUsername());
	    	User user = userService.loadUser(userDetails.getUsername());
    		if(user.getOrganization()!=null){
    			String organization_code = user.getOrganization().getOrgCode();
	   			Organization organization=organizationService.getOrganizationByCode(organization_code);
	    		if(organization!=null){
	    			//登录之后设置机构类别
		  			request.getSession().setAttribute("loginRole",String.valueOf(organization.getOrgType()));
	    			request.getSession().setAttribute("loginName",userDetails.getUsername());
					request.getSession().setAttribute("loginOrgName", organization.getOrgName());
					request.getSession().setAttribute("loginOrgCode", organization_code);
					String sessionId = request.getRequestedSessionId();
					request.getSession().setAttribute("sessionId", sessionId);
					UserCacheManager.addUser(userDetails.getUsername(), sessionId);
					String login_role=String.valueOf(organization.getOrgType());
					//此处加载首页的业务数据
					
					//添加日志
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
		  			LogInfo logInfo = new LogInfo();
		  			logInfo.setOperateIp(ip);
		  			logInfo.setOperateName("后台登录成功");
		  			logInfo.setOperateTime(new Date());
		  			logInfo.setOperateType("login");
		  			logInfo.setOrgCode(organization_code);
		  			logInfo.setOperateUser(userDetails.getUsername());
		  			sysInfoService.addLogInfo(logInfo);
		  			//后期改为访问同一个页面
		  			return new ModelAndView("/index");
					/*if("1".equals(login_role)){
						return new ModelAndView("/index_cbs");
					}else if("2".equals(login_role)){
						return new ModelAndView("/index_tsg");
					}else if("3".equals(login_role)){
						return new ModelAndView("/index_fx");
					}else if("4".equals(login_role)){
						return new ModelAndView("/index_zx");
					}else{
						return new ModelAndView("/index");
					}*/
				}
	    		
    		}else{
    			return new ModelAndView("redirect:/security/login?errorCode=3");
    		}
		}
		
		return new ModelAndView("redirect:/security/login/");
	}
	
	/*@RequestMapping(value="/index")
	public ModelAndView indexPage(HttpServletRequest request, ModelMap model) {
		Object obj = SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
		if(obj instanceof UserDetails){
			UserDetails userDetails = (UserDetails)obj ;
			if(!(userDetails.getAuthorities()!=null&&userDetails.getAuthorities().size()>0)){
				ModelAndView errorModel = new ModelAndView("/index_error_alert");
				errorModel.addObject("flag", "1");
				errorModel.addObject("loginName", userDetails.getUsername());
				errorModel.addObject("errorMessage", "用户权限信息不正确");
	    		return errorModel;
	    	}
	    	request.getSession().setAttribute("loginname", userDetails.getUsername());
	    	User user = userService.loadUser(userDetails.getUsername());
	    	//判断SAAS服务是否关闭
	    	ModelAndView errorModel = new ModelAndView("/index_error_alert");
	    	String errorMessage = null;
	    	if(user==null){
	    		errorModel.addObject("errorMessage", "用户不存在");
	    		return errorModel;
	    	}
	    	if (CacheManager.getContent("serviceStatus")!=null) {
	    		String serviceStatus  = (String)CacheManager.getContent("serviceStatus").getValue();
	    		if (serviceStatus.equals("1")) {
		    		//判断用户所属机构的服务是否关闭
		    		if(user.getOrganization()!=null&&StringUtils.isNotBlank(user.getOrganization().getOrgID())){
		    			if (user.getOrganization().getOrgServiceStatus()!= null) {
		    				String orgStatus = user.getOrganization().getOrgServiceStatus();
		    				if (orgStatus.equals("1")) {
		    					//判断用户的服务是否关闭
	    						if (user.getUserServiceStatus()!= null) {
	    							String userStatus = user.getUserServiceStatus();
	    							if (!userStatus.equals("1")) {
	    								logger.info("用户" + user.getUserID() + ",当前用户被禁用！");
	    								errorMessage = "当前用户被禁用！";
	    							}
	    						}else{
	    							logger.info("用户" + user.getUserID() + ",当前用户被禁用！");
									errorMessage = "当前用户被禁用！";
	    						}
		    				}else{
		    					logger.info("Saas服务机构被停止使用：orgId = "+user.getOrganization().getOrgID());
		    					errorMessage = "当前用户所属机构被禁用！";
		    				}
		    			}else{
		    				logger.info("Saas服务机构被停止使用：orgId = "+user.getOrganization().getOrgID());
	    					errorMessage = "当前用户所属机构被禁用！";
		    			}
		    		}else{
		    			logger.info("当前用户的机构配置有错误！");
						errorMessage = "当前用户的机构配置有错误！";
		    		}

				} else {
					logger.info("Saas服务未启动！");
					errorMessage = "Saas服务未启动！";
				}
			}else{
				logger.info("Saas服务未启动！");
				errorMessage = "Saas服务未启动！";
			}
	    	
	    	if(StringUtils.isNotBlank(errorMessage)){
	    		errorModel.addObject("errorMessage", errorMessage);
	    		return errorModel;
	    	}
	    	
	    	
    		if(user.getOrganization()!=null){
    			String organization_code = user.getOrganization().getOrgCode();
	   			Organization organization=organizationService.getOrganizationByCode(organization_code);
	    		if(organization!=null){
	    			//登录之后设置机构类别
		  			request.getSession().setAttribute("loginRole",String.valueOf(organization.getOrgType()));
	    			request.getSession().setAttribute("loginName",userDetails.getUsername());
					request.getSession().setAttribute("loginOrgName", organization.getOrgName());
					request.getSession().setAttribute("loginOrgCode", organization_code);
					String sessionId = request.getRequestedSessionId();
					request.getSession().setAttribute("sessionId", sessionId);
					UserCacheManager.addUser(userDetails.getUsername(), sessionId);
					String login_role=String.valueOf(organization.getOrgType());
					//加工机构不用查看图书和消息
					if(!login_role.equals("4")){
						
						//查询消息列表
						Page<Message> messageList = messageService.getMessages(null, "sendtime", "desc", 0, 10, organization_code,2);
						model.addAttribute("messageList", messageList);
						
						//查询图书列表
						Direction sortDirection = Sort.Direction.DESC;
						Page<BookOrg> bookList = bookViewService.getBookOrgs( 0, 10, sortDirection, "lastModifyTime",organization_code,null);
						model.addAttribute("bookList", bookList);
					}
					
					//查询加工任务列表 只有出版和加工平台才能查看任务
					if(login_role.equals("1")||login_role.equals("4")){
						Page<ProcessTask> processTaskList = processTaskService.getProcessTasksByPages(0, 10,SORT_TYPE_DESC,ORDER_TYPE,login_role.equals("1")?"creatorOrgCode":"processorOrgCode",organization_code,login_role.equals("1")?1:2,"");
						model.addAttribute("processTaskList", processTaskList);
					}
				
					//添加日志
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
		  			LogInfo logInfo = new LogInfo();
		  			logInfo.setOperateIp(ip);
		  			logInfo.setOperateName("后台登录成功");
		  			logInfo.setOperateTime(new Date());
		  			logInfo.setOperateType("login");
		  			logInfo.setOrgCode(organization_code);
		  			logInfo.setOperateUser(userDetails.getUsername());
		  			sysInfoService.addLogInfo(logInfo);
		  			//后期改为访问同一个页面
		  			return new ModelAndView("/index_main");
				}
	    		
    		}else{
    			return new ModelAndView("redirect:/security/login?errorCode=3");
    		}
		}
		
		return new ModelAndView("redirect:/security/login/");
	}*/
	
	@RequestMapping(value = "/regSupplier", method = RequestMethod.GET)
	public ModelAndView regSupplier(@RequestParam(value = "successCode", required = false) Integer successCode) {
		logger.info("注册机构  regOrganizationPage");
		ModelAndView modelAndView = new ModelAndView("security/supplier-register");
		modelAndView.addObject("supplier", new Supplier());
		List<Classification> classList = classificationService.getClassByKey(Constant.SYS_CLASS_INDUSTRY); 
		modelAndView.addObject("classList",classList);//获取行业分类
		return modelAndView;
	}
	
	@RequestMapping(value = "/saveSupplier", method = RequestMethod.POST)
	public ModelAndView saveSupplier(HttpServletRequest request,@ModelAttribute Supplier supplier,
						@RequestParam("idCardObverseFile") MultipartFile idCardObverseFile,
						@RequestParam("idCardReverseFile") MultipartFile idCardReverseFile,
						@RequestParam("businessLicenceCopyFile") MultipartFile businessLicenceCopyFile,
						@RequestParam("certificateAuthorizationFile") MultipartFile certificateAuthorizationFile,
						String otherClass) {
		//先保存附件到Tomcat目录下
		
		ModelAndView modelAndView = new ModelAndView("/pub/index.jsp");
		if(isSaveSuccess(supplier.getId())){
			modelAndView.addObject("success_msg", "保存成功!!!");
		}
		
		logger.info("保存供应商  saveOrganization "+supplier.getCompanyName());
		
		try {
			String home = request.getServletContext().getRealPath("/");
			SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMdd");
			String tempPath = sFormat.format(new Date());
			String str1 = saveFile(idCardObverseFile, home, "idCardObverseFile"+File.separator+tempPath);
			if(StringUtils.isNotBlank(str1)){
				supplier.setIdCardObverse(str1);
				supplier.setIdCardObverseName(idCardObverseFile.getOriginalFilename());
			}
			String str2 = saveFile(idCardReverseFile,  home, "idCardReverseFile"+File.separator+tempPath);
			if(StringUtils.isNotBlank(str2)){
				supplier.setIdCardReverse(str2);
				supplier.setIdCardReverseName(idCardReverseFile.getOriginalFilename());
			}
			String str3 = saveFile(businessLicenceCopyFile,  home,"businessLicenceCopyFile"+File.separator+tempPath);
			if(StringUtils.isNotBlank(str3)){
				supplier.setBusinessLicenceCopy(str3);
				supplier.setBusinessLicenceCopyName(businessLicenceCopyFile.getOriginalFilename());
			}
			String str4 = saveFile(certificateAuthorizationFile,  home, "certificateAuthorizationFile"+File.separator+tempPath);
			if(StringUtils.isNotBlank(str4)){
				supplier.setCertificateAuthorization(str4);
				supplier.setCertificateAuthorizationName(certificateAuthorizationFile.getOriginalFilename());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//处理分类信息
		if(supplier!=null){
			if(StringUtils.isNotBlank(supplier.getIndustryClassification())){
				if(supplier.getIndustryClassification().equals("-1")){
					supplier.setIndustryClassification("");
					if(StringUtils.isNotBlank(otherClass)){
						//1 先查询有没有重名的行也分类
						boolean flag = false;
						List<Classification> classList = classificationService.getClassByKey(Constant.SYS_CLASS_INDUSTRY); 
						for(Classification clazz:classList){
							if(clazz.getClassName().equals(otherClass)){
								supplier.setIndustryClassification(clazz.getClassCode());
								flag = true;
							}
						}
						//2 如果没有重名分类则创建新的分类
						if(!flag){
							Classification newClazz = new Classification();
							newClazz.setClassCode(java.util.UUID.randomUUID().toString());
							newClazz.setClassName(otherClass);
							newClazz.setClassLevel((short)1);
							newClazz.setClassKey(Constant.SYS_CLASS_INDUSTRY);
							newClazz.setHasLeaf(false);
							newClazz.setParentCode("");
							newClazz.setRemark("");
							classificationService.addClassification(newClazz);
							supplier.setIndustryClassification(newClazz.getClassCode());
						}
					}
					
				}
			}
		}
		supplier.setStatus(0);
		supplier.setLastModifyTime(new Date());
		supplierService.addSupplier(supplier);

		return new ModelAndView("redirect:/security/login/");
	}
	
	private String saveFile(MultipartFile file, String homePath, String savePath){
		String filePath = "";
		if(file!=null){
			try {
				String fileName = file.getOriginalFilename();
				if(StringUtils.isNotBlank(fileName)){
					String fileExt = FilePathUtil.getSuffix(fileName);
					filePath = File.separator + Constant.FILE_SAVE_PATH + File.separator + savePath;
					File saveFolder = new File(homePath + filePath);
					if(!saveFolder.exists()){
						saveFolder.mkdirs();
					}
					filePath = filePath +  File.separator + System.currentTimeMillis() + "." + fileExt;
					File newFile = new File(homePath + filePath);
					file.transferTo(newFile);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return filePath;
	}
	
	@RequestMapping(value="/404")
	public ModelAndView missingPage(HttpServletRequest request) {
		return new ModelAndView("redirect:/security/to404/");
	}
	
	@RequestMapping(value="/403")
	public ModelAndView accessForbiddenPage(HttpServletRequest request) {
		return new ModelAndView("redirect:/security/to403/");
	}
	
	/**
	 * 趋势分析
	 * @return
	 */
	@RequestMapping(value = "isSessionValid", method = RequestMethod.GET)
	@ResponseBody
	public boolean isSessionValid(HttpServletRequest request) {
		logger.info("验证session有效性 ");
		if(request.isRequestedSessionIdValid()){
			return true;
		}
		return false;
	}
}
