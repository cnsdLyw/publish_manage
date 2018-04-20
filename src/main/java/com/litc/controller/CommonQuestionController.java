package com.litc.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.litc.common.util.StringUtil;
import com.litc.model.CommonQuestion;
import com.litc.security.controller.BaseController;
import com.litc.service.CommonQuestionService;


/**
 * 常见问题
 * @author cuiyc
 *
 */
@Controller
@RequestMapping(value="/commonQuestion")
public class CommonQuestionController extends BaseController<CommonQuestion>{
	
	private final static Logger logger = LoggerFactory.getLogger(CommonQuestionController.class);
	
	@Autowired
	private CommonQuestionService commonQuestionService;
	
	
	/**
	 * 每页数量
	 */
	private static final int PAGEPERSIZE = 10;
	
	@RequestMapping(value = "/list")
	public String listOfTestApplication(HttpServletRequest request, String keyWord,Integer pageNo, Integer pageSize,ModelMap model) {
		
		if (pageNo==null) {
			pageNo = this.getInt(request, "pageNo");
		}
		Page<CommonQuestion> page = commonQuestionService.getCommentByPages(pageNo, PAGEPERSIZE,keyWord);
		model.addAttribute("pageContent", page);
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("keyWord",keyWord);
		model.addAttribute("pageNo",pageNo);
		
		
		return "/publish/commonQuestion/commonQuestion";
	}
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable String id,Integer pageNo, Integer pageSize, HttpServletRequest request) {
		logger.info("删除常用问题  deleteQuesttion "+id);
		commonQuestionService.deleteQuestion(Long.valueOf(id));
			
		return "redirect:/commonQuestion/list";
	}
	@RequestMapping(value="/deletes")
	public ModelAndView deleteQuesttions(HttpServletRequest request,String keyWord, Integer pageNo, Integer pageSize) {
		String[] arr = request.getParameterValues("objectid");
		logger.info("删除常用问题  deleteQuesttions "+arr);
		int lenght = arr.length;
		Long[] ids = new Long[lenght];
		for(int i=0;i<lenght;i++){
			ids[i]=Long.parseLong(arr[i]);
		}
		commonQuestionService.deleteQuestions(ids);
		
		ModelAndView modelAndView = new ModelAndView("redirect:/commonQuestion/list/");
		
		return modelAndView;
	}
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView addCommonQuestion(HttpServletRequest request) {
		
		logger.info("添加常见问题  addCommonQuestion");
		ModelAndView modelAndView = new ModelAndView("/publish/commonQuestion/commonQuestion-input");
		modelAndView.addObject("commonQuestion", new CommonQuestion());
		return modelAndView;
	}
	@RequestMapping(value = "/edit/{id}&{type}", method = RequestMethod.GET)
	public ModelAndView editCommonQuestion(@PathVariable String id,@PathVariable String type,HttpServletRequest request) {
		logger.info("修改常见问题  editCommonQuestion"+id);
		CommonQuestion commonQuestion=commonQuestionService.getQuestion(Long.valueOf(id));
		ModelAndView modelAndView = new ModelAndView("/publish/commonQuestion/commonQuestion-detail");
		modelAndView.addObject("commonQuestion",commonQuestion);
		//type=0,代表修改 ，type=1，代表查看
		modelAndView.addObject("type",type);
		return modelAndView;
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveCommonQuestion(@ModelAttribute CommonQuestion commonQuestion,HttpServletRequest request) {
		logger.info("保存常见问题  saveCommonQuestion ");
		ModelAndView modelAndView = new ModelAndView("redirect:/commonQuestion/list");
		
		String loginname=StringUtil.getUserName(request);
		commonQuestion.setTime(new Date());
		commonQuestion.setUser(loginname);
		commonQuestionService.saveCommonQuestion(commonQuestion);
		
		return modelAndView;
	}

}
