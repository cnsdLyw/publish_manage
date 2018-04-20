package com.litc.fileSystem.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.litc.common.util.Constant;
import com.litc.controller.BaseController;
import com.litc.fileSystem.model.JetsenFiles;
import com.litc.fileSystem.service.JetsenFilesService;
@Controller
@RequestMapping("/jetsenFiles")
public class JetsenFilesController extends BaseController<JetsenFiles>{
	

	private final static Logger logger = LoggerFactory.getLogger(JetsenFilesController.class);
	
	/**
	 * 每页数量
	 */
	private static final int PAGEPERSIZE = 10;
	
	/**
	 * 默认排序字段
	 */
	private static String ORDER_TYPE = "time";
	
	@Autowired
	private JetsenFilesService jetsenFilesFastDFService;
	
	@RequestMapping(value = "/list")
	public ModelAndView listOfJetsenServiceTask(HttpServletRequest request,String orderType,String sortType) {
		logger.info("附件");
		ModelAndView modelAndView = new ModelAndView("/system/jetsenFiles/jetsenFiles-list");
		String loginOrgCode = (String)request.getSession().getAttribute("loginOrgCode");
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
		page = jetsenFilesFastDFService.getJetsenFilesByPages(pageNo, PAGEPERSIZE,sortDirection,orderType,loginOrgCode);
		modelAndView.addObject("pageContent",page);
		modelAndView.addObject("fileHomeUrl",Constant.SERVER_FILE_HOME_URL);
		return modelAndView;
	}

}
