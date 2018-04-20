package com.litc.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.litc.model.DataModel;
import com.litc.service.DataModelService;

/**
 * 数据字典Controller
 * @author liyw
 *
 */

@Controller
@RequestMapping(value = "/data")
public class DataController extends BaseController<DataModel> {

	private final static Logger logger = LoggerFactory.getLogger(DataController.class);
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private DataModelService dataModelService;
	
	
	@RequestMapping(value="/index")
	public ModelAndView index() {
		//dataModelService.getTableData();
		return new ModelAndView("data/data_index");
	}
	
	@RequestMapping(value = "/getTable")
	@ResponseBody
	public List<DataModel> getTable(String tableName) {
		logger.info("获取表结构，表名 "+tableName);
		List<DataModel> list = dataModelService.getTableColumns(tableName);
		return list;
	}
	
	@RequestMapping(value="/publishPress", method=RequestMethod.GET)
	@ResponseBody
	public Integer publishPress(int flag) throws InterruptedException{
		logger.info("查询发布热门出版社  publishPress "+flag);
		Thread.sleep(1800);
		Thread.sleep(1800);
		return 0;
	}
	
	
}
