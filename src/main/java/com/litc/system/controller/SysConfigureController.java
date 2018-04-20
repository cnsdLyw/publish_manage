package com.litc.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.litc.security.controller.BaseController;
import com.litc.security.model.LogInfo;
import com.litc.security.model.User;
import com.litc.security.service.BaseUtil;
import com.litc.system.model.SysConfigure;
import com.litc.system.service.SysConfigureService;

@Controller
@RequestMapping("/sysConfirure")
public class SysConfigureController extends BaseController<SysConfigure> {

	@Autowired
	private SysConfigureService sysConfigureService;
	

	/**
	 * 每页数量
	 */
	private static final int AUTHORITYPAGESIZE = 12;

	/**
	 * 排序字段
	 */
	private static String ORDER_TYPE = "id";


	@RequestMapping(value = "/index")
	public ModelAndView indexOfStorageDevice(HttpServletRequest request,
			String ftpName,Long typeid) throws Exception {
		ModelAndView modelAndView = new ModelAndView("system/configure/configure-index");
		if(typeid==null)
			typeid=0l;
		modelAndView.addObject("typeid", typeid);
//		ModelAndView modelAndView = new ModelAndView("redirect:/sysConfigGroup/getTreeData");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/test")
	public ModelAndView test(HttpServletRequest request,
			String ftpName,Long typeid) throws Exception {
		ModelAndView modelAndView = new ModelAndView("system/configure/configure-test");
		if(typeid==null)
			typeid=0l;
		modelAndView.addObject("typeid", typeid);
//		ModelAndView modelAndView = new ModelAndView("redirect:/sysConfigGroup/getTreeData");
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/leftTree")
	public ModelAndView leftTree(HttpServletRequest request,String typeid) throws Exception {
		ModelAndView modelAndView = new ModelAndView("system/configure/leftTree");
		modelAndView.addObject("typeid", typeid);
		return modelAndView;
	}
	@RequestMapping(value = "/list")
	public ModelAndView listOfStorageDevice(HttpServletRequest request,
			String keyWord,Long typeid) throws Exception {
		ModelAndView modelAndView = new ModelAndView("system/configure/configure-list");
		Long parentId = null;
		
		if(typeid!=0){
			parentId = sysConfigureService.getParentId(typeid);
		}else{
			parentId = typeid;
		}
		
		//配置文件测试
		pageNo = this.getInt(request, "pageNo");
		/**模拟数据 start*/
//		PageParam groupParam = new PageParam("groupId", typeid);
		page = sysConfigureService.getSysConfiguresByPage(pageNo, AUTHORITYPAGESIZE, SORT_TYPE_ASC,ORDER_TYPE, typeid,keyWord);
		modelAndView.addObject("pageContent",page);
		modelAndView.addObject("typeid",typeid);
		modelAndView.addObject("keyWord",keyWord);
		modelAndView.addObject("parentId",parentId);
		/**模拟数据 end*/
		return modelAndView;
	}
	
    
	//添加新的配置信息
		@RequestMapping(value = "/addSysConfig", method = RequestMethod.GET)
		public ModelAndView addSysConfig(HttpServletRequest request,Long typeid) {
			ModelAndView modelAndView = new ModelAndView("system/configure/configure-add");
			modelAndView.addObject("SysConfigure", new SysConfigure());
			modelAndView.addObject("typeid",typeid);
			return modelAndView;
		}
		
		//编辑配置信息
		@RequestMapping(value="/editSysConfig/{id}", method=RequestMethod.GET)
		public ModelAndView editSysConfig(@PathVariable Long id,Long typeid) {
			ModelAndView modelAndView = new ModelAndView("system/configure/configure-add");
			SysConfigure sysConfigure = sysConfigureService.getSysConfigure(id);
			modelAndView.addObject("sysConfigure",sysConfigure);
			modelAndView.addObject("typeid",typeid);
			return modelAndView;
			
		}
		
		//预览配置信息
		@RequestMapping(value="/getJsonSysConfigure", method=RequestMethod.GET)
		@ResponseBody
		public SysConfigure getJsonSysConfigure(Long id){
			SysConfigure sysConfigure = sysConfigureService.getSysConfigure(id);
			return sysConfigure;
		}
		
		//保存配置
		@RequestMapping(value = "/save", method = RequestMethod.POST)
		public ModelAndView saveSysConfigure(SysConfigure sysConfigure,HttpServletRequest request,Long typeid) {
			ModelAndView modelAndView = new ModelAndView("redirect:/sysConfirure/list/");
				sysConfigure.setGroupId(typeid);
			sysConfigureService.addSysConfigure(sysConfigure);
			modelAndView.addObject("typeid",typeid);
			return modelAndView;
		}
		
		@RequestMapping(value="/isSysConfigureNameExist", method=RequestMethod.GET)
		@ResponseBody
		public boolean isSysConfigureNameExist(String configureName){
			return sysConfigureService.isSysConfigureNameExist(configureName);
		}
		
		@RequestMapping(value="/isSysConfigureNameExistWithId", method=RequestMethod.GET)
		@ResponseBody
		public boolean isSysConfigureNameExistWithId(Long id,String configureName){
			return sysConfigureService.isSysConfigureNameExist(id,configureName);
		}
		
		@RequestMapping(value="/delete/{ids}", method=RequestMethod.GET)
		public ModelAndView delete(@PathVariable String ids,HttpServletRequest request,Long typeid) {
			ModelAndView modelAndView = new ModelAndView("redirect:/sysConfirure/list/");
			Long[] Id=getIdArray(ids);
			for(Long id:Id){
				sysConfigureService.delete(id);
			}
			modelAndView.addObject("typeid",typeid);
			return modelAndView;
		}
		
}