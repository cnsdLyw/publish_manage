package com.litc.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.litc.common.util.tree.AdditionalParameters;
import com.litc.common.util.tree.Item;
import com.litc.system.model.Classification;
import com.litc.system.model.ClassificationType;
import com.litc.system.service.ClassificationService;
import com.litc.system.service.ClassificationTypeService;

@Controller
@RequestMapping("/classification")
public class ClassificationController {
	
	private final static Logger logger = LoggerFactory.getLogger(ClassificationController.class);
	
	@Autowired
	private ClassificationService classificationService;
	
	@Autowired
	private ClassificationTypeService classificationTypeService;
	
	@RequestMapping(value = "/index")
	public ModelAndView index() throws Exception {
		ModelAndView modelAndView = new ModelAndView("system/classification/classification-index");
		List<ClassificationType> list = classificationTypeService.getAll();
		modelAndView.addObject("list",list);
		return modelAndView;
	}

	@RequestMapping(value = "/leftTree")
	public ModelAndView leftTree(HttpServletRequest request,String classKey, String classCode) throws Exception {
		ModelAndView modelAndView = new ModelAndView("system/classification/classification-leftTree");
		modelAndView.addObject("classKey", classKey);
		modelAndView.addObject("classCode", classCode);
		return modelAndView;
	}
	
	/**
	 * 
	 * @param classKey
	 * @param parentCode
	 * @param classCode 默认要展开的节点
	 * @return
	 */
	@RequestMapping(value="/getTreeData", method=RequestMethod.GET)
	@ResponseBody
	public Object getTreeData(String classKey,String parentCode){
		logger.info("getTreeData 获取分类   分类码  "+classKey+" ,父节点 "+parentCode);
		List<Classification> list = null;
		if(StringUtils.isNotBlank(parentCode)){
			list = classificationService.getClassByKeyAndCode(classKey, parentCode);
		}else{
			list = classificationService.getClassByKeyAndLevel(classKey, (short)1);
		}
		
		Map<String,Item> map = new HashMap<String, Item>();
		for(Classification clazz:list){
			Item item = new Item();
			item.setText(clazz.getClassName());
			if(clazz.isHasLeaf()){
				item.setType("folder");
			}else{
				item.setType("item");
			}
			
			AdditionalParameters additionalParameters = new AdditionalParameters();
			additionalParameters.setId(clazz.getClassCode());
			item.setAdditionalParameters(additionalParameters);
			item.setCode(clazz.getClassCode());
			map.put(clazz.getClassCode(), item);
		}
		JSONObject obj = (JSONObject) JSONObject.toJSON(map);
		logger.info("getTreeData   返回 "+obj.toString());
		return JSON.parse(obj.toString());
	}
	
	@RequestMapping(value="/getAllTreeData", method=RequestMethod.GET)
	@ResponseBody
	public Object getAllTreeData(String classKey, String classCode){
		logger.info("getAllTreeData 获取所有分类  ，分类码  "+classKey+"，展开的节点 "+classCode);
		String openListStr = null;
		if(StringUtils.isNotBlank(classCode)){
			openListStr = classificationService.getOpenListStr(classCode);
		}
		List<Classification> list = null;
		Map<String,Item> map = new HashMap<String, Item>();
		if(StringUtils.isNotBlank(classKey)){
			list = classificationService.getClassByKey(classKey);
			for(Classification clazz:list){
				if(StringUtils.isBlank(clazz.getParentCode())){
					Item item = new Item();
					item.setText(clazz.getClassName());
					if(clazz.isHasLeaf()){
						item.setType("folder");
					}else{
						item.setType("item");
					}
					AdditionalParameters additionalParameters = new AdditionalParameters();
					if(StringUtils.isNotBlank(openListStr)&&openListStr.indexOf(clazz.getClassCode())!=-1){
						additionalParameters.setItemSelected(true);
					}
					additionalParameters.setId(clazz.getClassCode());
					item.setAdditionalParameters(additionalParameters);
					item.setCode(clazz.getClassCode());
					dealOrgClass(list,openListStr, clazz,item);
					map.put(clazz.getClassCode(), item);
				}
			
			}
		}
	
		JSONObject obj = (JSONObject) JSONObject.toJSON(map);
		logger.info("getTreeData   返回 "+obj.toString());
		return JSON.parse(obj.toString());
	}
	
	private void dealOrgClass(List<Classification> classList, String openListStr, Classification Class, Item itemParent){
		for(Classification clazz:classList){
			if(StringUtils.isNotBlank(clazz.getParentCode())&&clazz.getParentCode().equals(Class.getClassCode())){
				Item item = new Item();
				item.setText(clazz.getClassName());
				if(clazz.isHasLeaf()){
					item.setType("folder");
				}else{
					item.setType("item");
				}
				AdditionalParameters additionalParameters = new AdditionalParameters();
				if(StringUtils.isNotBlank(openListStr)&&openListStr.indexOf(clazz.getClassCode())!=-1){
					additionalParameters.setItemSelected(true);
				}
				additionalParameters.setId(clazz.getClassCode());
				item.setAdditionalParameters(additionalParameters);
				item.setCode(clazz.getClassCode());
				itemParent.getAdditionalParameters().getChildren().add(item);
				dealOrgClass(classList, openListStr, clazz,item);
			}
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/isClassCodeExist", method=RequestMethod.GET)
	public boolean isClassCodeExist(String classCode){
		logger.info("addClass  查询分类码是否已经存在  "+classCode);
		return classificationService.isClassCodeUsed(classCode);
	}
	
	@RequestMapping(value = "/saveClass", method = RequestMethod.POST)
	public ModelAndView saveClass(@ModelAttribute Classification classification,HttpServletRequest request) {
		logger.info("saveClass  保存分类");
		ModelAndView modelAndView = new ModelAndView("redirect:/classification/updateClass");
		
		if(StringUtils.isNotBlank(classification.getParentCode())){
			Classification parentClass = classificationService.getClassification(classification.getParentCode());
			if(!parentClass.isHasLeaf()){
				parentClass.setHasLeaf(true);
				classificationService.addClassification(parentClass);
			}
			classification.setClassLevel((short)(parentClass.getClassLevel()+1));
		}else{
			classification.setClassLevel((short)1);
		}
		
		//查询子节点，设置hasLeaf属性
		List<Classification> list = classificationService.getClassByKeyAndCode(classification.getClassKey(), classification.getClassCode());
		if(list!=null&&list.size()>0){
			classification.setHasLeaf(true);
		}
		
		
		
		classificationService.addClassification(classification);
		modelAndView.addObject("classCode",classification.getClassCode());
		modelAndView.addObject("message","1");
		return modelAndView;
	}
			
	@RequestMapping(value = "/addClass")
	public ModelAndView addClass(String classKey,String parentCode) throws Exception {
		logger.info("addClass  添加分类  "+classKey+"   "+parentCode);
		ModelAndView modelAndView = new ModelAndView("system/classification/classification-add");
		Classification classification = new Classification();
		if(StringUtils.isNotBlank(parentCode)){
			classification.setParentCode(parentCode);
		}
		classification.setClassKey(classKey);
		modelAndView.addObject("classification",classification);
		modelAndView.addObject("check",1);
		return modelAndView;
	}
	
	@RequestMapping(value = "/updateClass")
	public ModelAndView updateClass(String classCode) throws Exception {
		logger.info("updateClass  修改分类  "+classCode);
		ModelAndView modelAndView = new ModelAndView("system/classification/classification-add");
		Classification classification = classificationService.getClassification(classCode);
		modelAndView.addObject("classCode",classCode);
		modelAndView.addObject("classification",classification);
		modelAndView.addObject("check",0);
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteClass", method=RequestMethod.GET)
	public Object deleteClass(String classCode){
		logger.info("deleteClass  删除分类  "+classCode);
		Map<String,String> returnMap = new HashMap<String,String>();
		try {
			Classification Classification = classificationService.getClassification(classCode);
			List<Classification> listC = classificationService.getClassByKeyAndCode(Classification.getClassKey(), Classification.getClassCode());
			if(listC!=null&&listC.size()>0){
				returnMap.put("status", "0");
			}else{
				classificationService.deleteClassification(classCode);
				String classKey = Classification.getClassKey();
				String returnClassCode = "";
				if(StringUtils.isNotBlank(Classification.getParentCode())){
					Classification parentClass = classificationService.getClassification(Classification.getParentCode());
					//查询子节点，设置hasLeaf属性
					List<Classification> list = classificationService.getClassByKeyAndCode(parentClass.getClassKey(), parentClass.getClassCode());
					if(list!=null&&list.size()>0){
						parentClass.setHasLeaf(true);
					}else{
						parentClass.setHasLeaf(false);
					}
					returnClassCode = parentClass.getClassCode();
					classificationService.addClassification(parentClass);
				}
				returnMap.put("status", "1");
				returnMap.put("classKey", classKey);
				returnMap.put("classCode", returnClassCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("status", "-1");
		}
	
		JSONObject obj = (JSONObject) JSONObject.toJSON(returnMap);
		return JSON.parse(obj.toString());
	}
}
