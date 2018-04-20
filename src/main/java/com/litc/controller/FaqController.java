package com.litc.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.litc.common.util.Constant;
import com.litc.common.util.MailUtil;
import com.litc.common.util.StringUtil;
import com.litc.common.util.WebForm;
import com.litc.model.Mail;
import com.litc.model.cms.Faq;
import com.litc.security.model.LogInfo;
import com.litc.security.service.BaseUtil;
import com.litc.security.service.SysLogInfoService;
import com.litc.service.FaqService;




/**
 * Function:留言板管理(控制层)
 * 
 * @author zhongying(281264212@qq.com)
 * @date 2015-11-11 下午3:01:53
 * @version 1.0
 */
@Controller
@RequestMapping("/faq")
public class FaqController extends BaseController<Faq> {
	@Autowired
	private FaqService faqService;
	
	@Autowired
	private SysLogInfoService sysLogInfoService;

	/**
	 * 每页数量
	 */
	private static final int USERPAGESIZE = 10;

	private static String BaseUrl;

	@ModelAttribute
	public void beforeInvokingHandlerMethod(HttpSession session) {
		if (BaseUrl == null || BaseUrl.isEmpty())
			BaseUrl = session.getServletContext().getRealPath("/") + "filetemp" + File.separator;
	}

	/**
	 * 出版书目信息保存和修改
	 */
	@RequestMapping("/save")
	public String save(Faq faqJsp, String query_all_like, String queryTitle, String queryIsbn, String queryAuthor,
			String queryOrderBy, String queryOrderType,Integer pageNo, Integer pageSize, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		
		// 参数不对，容易引起400错误
		boolean isNewBook = true;
		Long faq_id = faqJsp.getId();
		// 保存当前登录名(与权限框架结合使用)
		// book.setId(null);
		Faq faqSave = null;
		if (faq_id == null) {
			faqJsp.setPubdate(new Date());
			faqSave = faqJsp;
			
		} else {
			isNewBook = false;
			Faq bookPersistence = faqService.getFaq(faq_id);
			if(bookPersistence == faqJsp){
				faqSave = faqJsp;
			}else{
				String loginName = (String) request.getSession().getAttribute("loginname");
				String createUser = bookPersistence.getLoginname();
				faqJsp.setLoginname(createUser);
	            faqJsp.setAnswerUser(loginName);
				faqJsp.setAnswerTime(new Date());
				faqJsp.setAuditStatus("1");
				Mail mail = new Mail();
		        mail.setHost("smtp.163.com"); // 设置邮件服务器,如果不用163的,自己找找看相关的  
		        mail.setSender(Constant.EMAIL_NAME);  
		        mail.setName("复合出版数据传递平台");
		        mail.setReceiver(faqJsp.getEmail());   
		        mail.setUsername(Constant.EMAIL_NAME); 
		        mail.setPassword(Constant.EMAIL_PWD); 
		        mail.setSubject("您的留言已被回复（复合出版数据传递平台）");  
		        String htmlMessage = "<center><b>"+faqJsp.getTitle()+"</b></center><b>您的留言内容</b>：<br><span style='margin-left:50px;'>"+faqJsp.getContent()+"<span><br/><b>回复的留言内容</b>：<br/><span style='margin-left:50px;'>"+faqJsp.getAnswerContent()+"</span>";
		        mail.setMessage(htmlMessage);  
		        new MailUtil().send(mail);  
		        faqJsp.setPubdate(new Date());
				faqSave = (Faq) WebForm.form2Obj(bookPersistence, faqJsp);
				
				//留言板留言日志
				String operate = "信息发布留言板："+ faqSave.getTitle();
				LogInfo logInfo = BaseUtil.addOperateLog(request, operate);
				if(null != logInfo){
					sysLogInfoService.addLogInfo(logInfo);
				}
			}
		}
		
		faqSave = faqService.updateFaq(faqSave);
		redirectAttributes.addFlashAttribute("message", "保存成功");
		
		// " Flash attributes 在对请求的重定向生效之前被临时存储（通常是在session)中" 看到这个就感觉不好；
		addAttibuteForQuery(null, redirectAttributes, query_all_like, queryOrderBy, queryOrderType, pageNo, pageSize);
		return "redirect:/faq/list?operate=reAtt";
	}
	
	public boolean send(Mail mail) {  
        // 发送email  
        HtmlEmail email = new HtmlEmail();  
        try {  
            // 这里是SMTP发送服务器的名字：163的如下："smtp.163.com"  
            email.setHostName(mail.getHost());  
            // 字符编码集的设置  
            email.setCharset(Mail.ENCODEING);  
            // 收件人的邮箱  
            email.addTo(mail.getReceiver());  
            // 发送人的邮箱  
            email.setFrom(mail.getSender(), mail.getName());  
            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码  
            email.setAuthentication(mail.getUsername(), mail.getPassword());  
            // 要发送的邮件主题  
            email.setSubject(mail.getSubject());  
            // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签  
            email.setMsg(mail.getMessage());  
            // 发送  
            email.send(); 
            return true;  
        } catch (Exception e) {
            return false;  
        }  
    }  
	@RequestMapping("/delete")
	public String delete(String query_all_like, String queryTitle, String queryIsbn, String queryAuthor,
			String queryOrderBy, String queryOrderType, Integer pageNo, Integer pageSize, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		String[] arr = request.getParameterValues("objectid");
		for (int i = 0; i < arr.length; i++) {
			String str = arr[i]; //
			long thelong = Long.valueOf(str);//
			// FaqTxt contentTxt =contentTxtService.get(thelong);
			// if(contentTxt!=null){
			// contentTxtService.delete(thelong);//先删除关联的内容表
			// }
			//留言板删除日志
			Faq faq = faqService.getFaq(thelong);
			String operate = "信息发布留言板删除："+ faq.getTitle();
			LogInfo logInfo = BaseUtil.addOperateLog(request, operate);
			if(null != logInfo){
				sysLogInfoService.addLogInfo(logInfo);
			}
			
			faqService.deleteFaq(thelong);
		}
		redirectAttributes.addFlashAttribute("message", "删除成功");
		addAttibuteForQuery(null, redirectAttributes, query_all_like, queryOrderBy, queryOrderType, pageNo, pageSize);

		return "redirect:/faq/list?operate=reAtt";
	}

	@RequestMapping("/detail")
	public String detail(Faq faq, Long id,String message,String queryTitle, String queryIsbn, String queryAuthor,
			Integer queryOrderBy, Integer pageNo, HttpServletRequest request, ModelMap model) {
		faq=faqService.getFaq(id);
		model.addAttribute("faq",faq);
		model.addAttribute("message",message);
		return "/publish/faq/faq-detail";
	}


	@RequestMapping("/input")
	public String input(Long id, Faq faq, ModelMap model, String query_all_like, String queryTitle, String queryIsbn,
			String queryAuthor, String queryOrderBy, String queryOrderType, Integer pageNo, Integer pageSize) {
		addAttibuteForQuery(model, null, query_all_like, queryOrderBy, queryOrderType, pageNo, pageSize);
		faq=faqService.getFaq(id);
		model.addAttribute("faq",faq);
		return "/publish/faq/faq-input";
	}

	@RequestMapping("/list")
	public String list(String query_all_like, String queryOrderBy, String queryOrderType, Integer pageNo,
			Integer pageSize, HttpServletRequest request, ModelMap model, String message) {
		// long time = System.currentTimeMillis();
		// 处理保存和修改时候的参数回显
		String operate = request.getParameter("operate");
		if (operate != null && !"".equals(operate)) {
			query_all_like = StringUtil.getString(model.get("query_all_like"));
			queryOrderBy = StringUtil.getString(model.get("queryOrderBy"));
			queryOrderType = StringUtil.getString(model.get("queryOrderType"));
			pageNo = StringUtil.getInt(model.get("pageNo"));
			pageSize = StringUtil.getInt(model.get("pageSize"));
		}
		if (pageNo == null) {
			pageNo = 0;
		}

		if (pageSize == null || pageSize == 0) {
			pageSize = USERPAGESIZE;
		}

		if (StringUtils.isBlank(queryOrderBy)) {
			queryOrderBy = "id";
		}
		if (StringUtils.isBlank(queryOrderType)) {
			queryOrderType = "desc";
		}

		Map map = new HashMap();
		map.put("query_all_like", query_all_like);
		page = faqService.getFaqs(map, queryOrderBy, queryOrderType, pageNo, pageSize);
		model.addAttribute("pageContent", page);
		// 数据回显
		addAttibuteForQuery(model, null, query_all_like, queryOrderBy, queryOrderType, pageNo, pageSize);
		// time = System.currentTimeMillis() - time;
		return "/publish/faq/faq";
	}

	

	private void addAttibuteForQuery(ModelMap model, RedirectAttributes redirectAttributes, String query_all_like,
			String queryOrderBy, String queryOrderType, Integer pageNo, Integer pageSize) {
		if (!StringUtils.isBlank(query_all_like)) {
			if (model != null) {
				model.addAttribute("query_all_like", query_all_like);
			} else {
				redirectAttributes.addFlashAttribute("query_all_like", query_all_like);
			}

		}

		if (!StringUtils.isBlank(queryOrderBy)) {
			if (model != null) {
				model.addAttribute("queryOrderBy", queryOrderBy);
			} else {
				redirectAttributes.addFlashAttribute("queryOrderBy", queryOrderBy);
			}
		}
		if (!StringUtils.isBlank(queryOrderType)) {
			if (model != null) {
				model.addAttribute("queryOrderType", queryOrderType);
			} else {
				redirectAttributes.addFlashAttribute("queryOrderType", queryOrderType);
			}
		}

		if (pageNo != null) {
			if (model != null) {
				model.addAttribute("pageNo", pageNo);
			} else {
				redirectAttributes.addFlashAttribute("pageNo", pageNo);
			}
		}
		if (pageSize != null) {
			if (model != null) {
				model.addAttribute("pageSize", pageSize);
			} else {
				redirectAttributes.addFlashAttribute("pageSize", pageSize);
			}
		}
	}

}
