package com.litc.controller;

import java.awt.print.Book;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.alibaba.fastjson.JSON;
import com.litc.common.bean.SpringBeanUtil;
import com.litc.common.util.StringUtil;
import com.litc.fileSystem.model.JetsenFiles;
import com.litc.fileSystem.service.JetsenFilesService;
import com.litc.model.book.Handle;
import com.litc.security.model.Classification;
import com.litc.security.model.Organization;
import com.litc.security.model.User;
import com.litc.security.service.OrganizationService;
import com.litc.security.service.UserService;

public class BaseController<T> {

	protected static final Direction SORT_TYPE_DESC = Sort.Direction.DESC;

	protected static final Direction SORT_TYPE_ASC = Sort.Direction.ASC;

	protected String loginname = null;
	
	protected List<Classification> getOrgTypes(){
		List<Classification> list = new ArrayList<Classification>();
		Classification class1 = new Classification("1", "通知公告");
		Classification class2 = new Classification("2", "新闻动态");
		list.add(class1);
		list.add(class2);
		return list;
		
	}
	

	/**
	 * 获取用户名
	 * 
	 * @param request
	 */
	@ModelAttribute
	public String beforeInvokingHandlerMethod(HttpServletRequest request) {
		if (loginname == null || loginname.isEmpty()) {
			loginname = StringUtil.getUserName(request);
		}
		// loginname=null;
		// if(loginname==null || loginname.isEmpty()){
		// //跳转到登录页面
		// return "redirect:/security/login";
		//
		// }
		return loginname;
	}

	/**
	 * 当前页码,从0开始
	 */
	protected int pageNo;

	protected Page<T> page;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public Page<T> getPage() {
		return page;
	}

	public void setPage(Page<T> page) {
		this.page = page;
	}

	/**
	 * @param request
	 * @param paramName
	 *            参数名称
	 * @return 从request获取参数对应字符串值
	 */
	public static String getString(HttpServletRequest request, String paramName) {
		String value = request.getParameter(paramName);
		return value != null ? value : "";
	}

	/**
	 * 获取当前登录用户信息
	 * 
	 * @param request
	 * @return
	 */
	public static User getCurrentUser(String loginame) {
		UserService userService = (UserService) SpringBeanUtil.getBean("userService");
		User user = userService.loadUser(loginame);
		return user;
	}

	/**
	 * 附件内容添加到附件列表中
	 * 
	 * @param attache
	 *            提交的附件内容
	 * @return void
	 */
	public Map<String, List<JetsenFiles>> addAttachement(List<JetsenFiles> attachementlist, boolean isNewBook, String bookid) {
		Map<String, List<JetsenFiles>> map = new HashMap<String, List<JetsenFiles>>();
		List<JetsenFiles> attachementUpload = new ArrayList<JetsenFiles>();
		List<JetsenFiles> attachementCreate = new ArrayList<JetsenFiles>();
		List<JetsenFiles> attachementUpdate = new ArrayList<JetsenFiles>();
		if (null != attachementlist && attachementlist.size() > 0) {
			boolean isNewAttachement = true;
			for (JetsenFiles att : attachementlist) {
				isNewAttachement = true;
				if (isNewBook) {
					// companybookService.addBookAttachment(att);
					attachementCreate.add(att);
				} else {
					String attathTemp = att.getUuid();
					if (null != attathTemp) {
						isNewAttachement = false;
						// 更新附件，则update下
						// companybookService.updateBookAttachment(att);
						attachementUpdate.add(att);
					} else {
						// companybookService.addBookAttachment(att);
						attachementCreate.add(att);
					}
				}
				att.setNewAttachement(isNewAttachement);
				attachementUpload.add(att);
			}
		}

		map.put("attachementUpload", attachementUpload);
		map.put("attachementCreate", attachementCreate);
		map.put("attachementUpdate", attachementUpdate);
		return map;
	}

	/**
	 * @param request
	 * @param paramName
	 *            参数名称
	 * @return 从request获取参数对应整型值
	 */
	protected int getInt(HttpServletRequest request, String paramName) {
		String value = request.getParameter(paramName);
		if (value == null || value.length() == 0)
			return 0;
		else {
			try {
				return Integer.parseInt(value);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
	}

	/**
	 * @param request
	 * @param paramName
	 *            参数名称
	 * @return 从request获取参数对应整型值
	 */
	protected long getLong(HttpServletRequest request, String paramName) {
		String value = request.getParameter(paramName);
		if (value == null || value.length() == 0)
			return 0;
		else {
			try {
				return Long.parseLong(value);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
	}

	private List<Organization> findTopTree() {
		List<Organization> result = new ArrayList<Organization>();
		Organization p1 = new Organization();
		p1.setOrgName("出版");
		p1.setOrgCode("1");

		p1.setOrgName("发行");
		p1.setOrgCode("2");

		p1.setOrgName("图书馆");
		p1.setOrgCode("3");
		result.add(p1);

		return result;
	}

	/**
	 * 构建JSON
	 * 
	 * @param categories
	 * @param json
	 */
	public void buildJSON(List<Organization> allList, List<Organization> list, StringBuffer json, Boolean closed) {

		if (allList != null && allList.size() > 0) {

			for (int i = 0; i < allList.size(); i++) {
				Organization obj = allList.get(i);
				json.append("{");
				// id
				json.append("id");
				json.append(":");
				json.append("\"");
				json.append(obj.getOrgCode());
				json.append("\"");
				json.append(",");

				// pId
				json.append("pId");
				json.append(":");
				json.append(obj.getOrgType());
				json.append(",");

				// orgCode
				json.append("orgCode");
				json.append(":");
				json.append("\"");
				json.append(obj.getOrgCode());
				json.append("\"");
				json.append(",");

				// name
				json.append("name");
				json.append(":");
				json.append("\"");
				json.append(obj.getOrgName());
				json.append("\"");

				// List<Organization> children = faqclassService
				// .findByCondition(Integer.parseInt(faqclass.getId() +
				// ""),allFaqclasses);
				List<Organization> children = null;
				if (children != null && children.size() > 0) {

					if (closed) {
						json.append(",");

						json.append("\"open\"");
						json.append(":");
						json.append("\"false\"");
					}

					json.append(",");

					// json.append("\"children\"");
					// json.append(":");

					buildJSON(allList, children, json, closed);

				}

				json.append("}");

				if (i != allList.size() - 1) {
					json.append(",");
				}

			}

		}
		json.append("]");
	}

	/**
	 * 获取组织机构json
	 * 
	 * @param model
	 * @return
	 */
	public void getOrganizationJson(ModelMap model) {
		// 组织机构
		OrganizationService organizationService = (OrganizationService) SpringBeanUtil.getBean("organizationService");
		List<Organization> orgList = organizationService.getOrganizations();
		Map<String, Object> map = new HashMap<String, Object>();
		for (Organization org : orgList) {
			map.put(org.getOrgCode(), org.getOrgName());
		}
		String orgMap = JSON.toJSONString(map);
		model.addAttribute("orgMap", orgMap);
	}

	/**
	 * 获取组织机构map
	 * 
	 * @param model
	 * @return
	 */
	public void getOrganizationMap(ModelMap model) {
		// 组织机构
		OrganizationService organizationService = (OrganizationService) SpringBeanUtil.getBean("organizationService");
		List<Organization> orgList = organizationService.getOrganizations();
		Map<String, String> map = new HashMap<String, String>();
		for (Organization org : orgList) {
			map.put(org.getOrgCode(), org.getOrgName());
		}
		model.addAttribute("orgMap", map);
	}

	public void printHtmlMsg(HttpServletResponse resp, String msg) {
		resp.setContentType("text/html;charset=GB2312");// 指定以中文字符集显示返回信息
		PrintWriter out = null;
		try {
			out = resp.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}// 输出返回信息
		out.println("<html>");
		out.println("<body>");
		out.println("<center>");
		out.println("<br><br><br>提示信息：" + "<font color=red>" + msg + "</font><br><br>");
		out.println("<input type='button'  value='返  回' onclick='javascript:history.go(-1);' />&nbsp;&nbsp;&nbsp;");
		// out.println("<input type='button'  value='关闭' onclick='javascript:window.close();' /><br>");
		out.println("</center>");
	}

	public void printAlertMsg(HttpServletResponse resp, String msg) {
		resp.setContentType("text/html;charset=GB2312");// 指定以中文字符集显示返回信息
		PrintWriter out = null;
		try {
			out = resp.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}// 输出返回信息

		out.print("<script type='text/javascript'>alert('" + msg + "');history.go(-1);</script>");
	}

	/**
	 * 将ID数组由String类型转换成Long型
	 * @param ids
	 * @return
	 */
	protected Long[] getIdArray(String ids){
		String[] str1 = ids.split(",");
		Long[] str2 = new Long[str1.length];
		for (int i = 0; i < str1.length; i++) {
			str2[i] = Long.valueOf(str1[i]);
		}
		return str2;
	}
}
