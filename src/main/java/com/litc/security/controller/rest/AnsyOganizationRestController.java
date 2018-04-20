package com.litc.security.controller.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.litc.controller.BaseController;
import com.litc.security.model.Organization;
import com.litc.security.service.OrganizationService;

@RestController
@RequestMapping("/organizationAnsyRest")
public class AnsyOganizationRestController extends BaseController<Organization>{
	
	private final static Logger logger = LoggerFactory.getLogger(AnsyOganizationRestController.class);
	@Autowired
	private OrganizationService organizationService;

	@RequestMapping(value = "/getOrganization", method = RequestMethod.POST)
	public String list() {
		List<Organization> orgList = organizationService.getOrganizations();
		String josn = JSON.toJSONString(orgList);
		logger.info("json查询机构");
		return josn;
	}
	
}