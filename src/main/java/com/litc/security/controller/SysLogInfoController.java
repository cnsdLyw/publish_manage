package com.litc.security.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.litc.common.util.file.ZipFileDownloadUtil;
import com.litc.security.common.page.PageParam;
import com.litc.security.model.LogInfo;
import com.litc.security.repository.IComponent;
import com.litc.security.service.BaseUtil;
import com.litc.security.service.SysLogInfoService;


/**
 * 日志信息
 * @author makang
 *
 */
@Controller
@RequestMapping(value="/loginfo")
public class SysLogInfoController extends BaseController<LogInfo>{
	
	/**
	 * 每页数量
	 */
	private static final int AUTHORITYPAGESIZE = 10;
	
	/**
	 * 排序字段
	 */
	private static String ORDER_TYPE = "id";
	
	@Autowired
	private SysLogInfoService sysLogInfoService;
	
	@RequestMapping(value = "/list")
	public ModelAndView listOfOrganization(HttpServletRequest request,String operateUser,String operateIp) {
		ModelAndView modelAndView = new ModelAndView("security/loginloginfo/loginloginfo");
		pageNo = this.getInt(request, "pageNo");
	    String loginOrgCode = (String)request.getSession().getAttribute("loginOrgCode");
		//创建查询参数
		PageParam operateOrgCodeParam = new PageParam("orgCode", loginOrgCode,IComponent.SERCHTYPE_SAME);
		PageParam operateUserParam = new PageParam("operateUser", operateUser,IComponent.SERCHTYPE_LIKE);
		PageParam operateIpParam = new PageParam("operateIp", operateIp,IComponent.SERCHTYPE_LIKE);
		page = sysLogInfoService.getOrganizationsByPages(pageNo, AUTHORITYPAGESIZE,SORT_TYPE_DESC,ORDER_TYPE,operateOrgCodeParam, operateUserParam,operateIpParam);
		modelAndView.addObject("operateUser",operateUser);
		modelAndView.addObject("operateIp",operateIp);
		modelAndView.addObject("pageContent",page);
		return modelAndView;
	}
	
	/*@RequestMapping(value = "/operate")
	public ModelAndView operate(HttpServletRequest request,String operateUser,String operateIp) {
		ModelAndView modelAndView = new ModelAndView("security/loginloginfo/loginloginfoOperate");
		pageNo = this.getInt(request, "pageNo");
		//创建查询参数
		PageParam operateTypeParam = new PageParam("operateType", "operate",IComponent.SERCHTYPE_LIKE);
		PageParam operateUserParam = new PageParam("operateUser", operateUser,IComponent.SERCHTYPE_LIKE);
		PageParam operateIpParam = new PageParam("operateIp", operateIp,IComponent.SERCHTYPE_LIKE);
		page = sysLogInfoService.getOrganizationsByPages(pageNo, AUTHORITYPAGESIZE,SORT_TYPE_DESC,ORDER_TYPE, operateTypeParam,operateUserParam,operateIpParam);
		modelAndView.addObject("operateUser",operateUser);
		modelAndView.addObject("operateIp",operateIp);
		modelAndView.addObject("pageContent",page);
		return modelAndView;
	}*/
	
	@RequestMapping(value = "/deleteLogInfo/{ids}")
	public ModelAndView deleteLogInfo(@PathVariable String ids) {
		ModelAndView modelAndView = new ModelAndView("redirect:/loginfo/list/");
		Long[] id=getIdArray(ids);
		sysLogInfoService.deleteLogInfo(id);
		return modelAndView;
	}
	
	@RequestMapping(value = "/exportLogInfo/{ids}")
	public ModelAndView exportLogInfo(@PathVariable String ids,HttpServletResponse response,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("redirect:/loginfo/list/");
		
		String type="";
		Date time = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(time);
		File folder = new File("C:/test");
		if(!folder.exists()){
			folder.mkdirs();
		}
		String path =folder + "/logg.txt";
		File f = new File(path);
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		FileWriter writer;
        try {
            writer = new FileWriter(f);
            writer.write("日志导出时间： "+dateString+"\r\n\r\n");
            Long[] Ids=getIdArray(ids);
    		for(Long id:Ids){
    			LogInfo log = sysLogInfoService.getLogInfo(id);
    			if(log.getOperateType().equals("login")){
    				type = "登陆用户";
    			}else{
    				type = "操作用户";
    			}
    			String operateTime = formatter.format(log.getOperateTime());
    			String str =operateTime+" "+type+" "+log.getOperateUser()+":"+log.getOperateName()+"(ip："+log.getOperateIp()+")\r\n";
    			writer.write(str);
    			
    			//操作日志
    	        String operate = "导出日志："+log.getOperateName();
    	        LogInfo logInfo = BaseUtil.addOperateLog(request, operate);
    			if(null != logInfo){
    				sysLogInfoService.addLogInfo(logInfo);
    			}
    		}
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		String tempPath = String.valueOf(System.currentTimeMillis())
				.substring(3);
		String zipName =sdf.format(new Date())+"-"+tempPath;
        ZipFileDownloadUtil.downloadTxt(f, response, zipName);// 下载txt文件
        
		return modelAndView;
	}
	
}
