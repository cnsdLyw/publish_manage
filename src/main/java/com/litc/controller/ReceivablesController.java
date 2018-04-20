package com.litc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.litc.model.DataModel;

/**
 * 
 * @author LITC Liyw 2018-3-29
 */

@Controller
@RequestMapping(value = "/receivables")
public class ReceivablesController extends BaseController<DataModel> {
	@RequestMapping(value = "/list")
	public ModelAndView index() {
		// dataModelService.getTableData();
		return new ModelAndView("receivables/receivables-list");
	}
}
