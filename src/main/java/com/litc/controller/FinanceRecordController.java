package com.litc.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.litc.common.util.Constant;
import com.litc.common.util.file.FilePathUtil;
import com.litc.security.model.FinanceRecord;
import com.litc.security.model.LogInfo;
import com.litc.security.service.BaseUtil;
import com.litc.security.service.SysLogInfoService;
import com.litc.service.FinanceRecordService;
import com.litc.system.model.Classification;
import com.litc.system.service.ClassificationService;

/**
 * 
 * @author LITC Liyw 2018-3-30
 */

@Controller
@RequestMapping(value = "/financeRecord")
public class FinanceRecordController extends BaseController<FinanceRecord> {
	
	private final static Logger logger = LoggerFactory.getLogger(FinanceRecordController.class);
	
	@Autowired
	private FinanceRecordService financeRecordService;
	
	@Autowired
	private SysLogInfoService sysLogInfoService;
	
	@Autowired
	private ClassificationService classificationService;
	
	/**
	 * 每页数量
	 */
	private static final int PAGEPERSIZE = 15;
	
	/**
	 * 加工任务排序字段
	 */
	private static String ORDER_TYPE = "id";
	
	@RequestMapping(value = "/list")
	public ModelAndView list(HttpServletRequest request,String orderType,String sortType, String keyWord) {
		ModelAndView modelAndView = new ModelAndView("financeRecord/financeRecord-list");
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
		
		List<Classification> classList = classificationService.getClassByKey(Constant.SYS_CLASS_INDUSTRY); 
		Map<String,String> clazzMap = new HashMap<String, String>();
		for(Classification clazz:classList){
			clazzMap.put(clazz.getClassCode(), clazz.getClassName());
		}
		
		modelAndView.addObject("clazzMap",clazzMap);
		page = financeRecordService.getFinanceRecordsByPages(pageNo, PAGEPERSIZE,sortDirection,orderType,keyWord);
		modelAndView.addObject("keyWord",keyWord);
		modelAndView.addObject("pageContent",page);
		return modelAndView;
	}
	
	@RequestMapping(value = "/addFinanceRecord", method = RequestMethod.GET)
	public ModelAndView addFinanceRecord() {
		logger.info("添加财务记录  addFinanceRecord");
		ModelAndView modelAndView = new ModelAndView("financeRecord/financeRecord-add");
		modelAndView.addObject("financeRecord", new FinanceRecord());
		List<Classification> classList = classificationService.getClassByKey(Constant.SYS_CLASS_INDUSTRY); 
		modelAndView.addObject("classList",classList);//获取行业分类
		return modelAndView;
	}
	
	@RequestMapping(value = "/editFinanceRecord/{id}", method = RequestMethod.GET)
	public ModelAndView editFinanceRecord(@PathVariable Long id) {
		logger.info("修改财务记录  editFinanceRecord");
		ModelAndView modelAndView = new ModelAndView("financeRecord/financeRecord-add");
		FinanceRecord financeRecord = financeRecordService.getFinanceRecord(id);
		modelAndView.addObject("financeRecord", financeRecord);
		List<Classification> classList = classificationService.getClassByKey(Constant.SYS_CLASS_INDUSTRY); 
		modelAndView.addObject("classList",classList);//获取行业分类
		return modelAndView;
	}
	
	@RequestMapping(value = "/saveFinanceRecord", method = RequestMethod.POST)
	public ModelAndView saveFinanceRecord(HttpServletRequest request,@ModelAttribute FinanceRecord financeRecord,
						@RequestParam("attachmentFile") MultipartFile attachmentFile) {
		ModelAndView modelAndView = new ModelAndView("redirect:/financeRecord/list/");
		logger.info("保存财务记录  saveOrganization "+financeRecord.getAmount()+"  "+financeRecord.getAmountUser());
		
		try {
			String home = request.getServletContext().getRealPath("/");
			SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMdd");
			String tempPath = sFormat.format(new Date());
			String str1 = saveFile(attachmentFile, home, "financeRecordFile"+File.separator+tempPath);
			if(StringUtils.isNotBlank(str1)){
				financeRecord.setAttachment(str1);
				financeRecord.setAttachmentName(attachmentFile.getOriginalFilename());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		financeRecord.setStatus(0);
		financeRecord.setLastModifyTime(new Date());
		financeRecordService.addFinanceRecord(financeRecord);
		modelAndView.addObject("message",  "1");
		return modelAndView;
	}
	
	@RequestMapping(value="/deleteFinanceRecord/{id}", method=RequestMethod.GET)
	public ModelAndView deleteUser(@PathVariable Long id,HttpServletRequest request) {
		logger.info("删除财务记录  deleteUser "+id);
		ModelAndView modelAndView = new ModelAndView("redirect:/financeRecord/list/");
		
		//系统管理财务记录操作日志
		FinanceRecord financeRecord = financeRecordService.getFinanceRecord(id);
		String operate = "删除财务记录：" +financeRecord.getAmount()+"  "+financeRecord.getAmountUser();
		LogInfo logInfo = BaseUtil.addOperateLog(request, operate);
		if(null != logInfo){
			sysLogInfoService.addLogInfo(logInfo);
		}
		financeRecordService.deleteFinanceRecord(id);
		
		modelAndView.addObject("message",  "1");
		return modelAndView;
	}
	
	@RequestMapping(value="/deleteFinanceRecords/{ids}", method=RequestMethod.GET)
	public ModelAndView deleteUsers(@PathVariable String ids,HttpServletRequest request) {
		logger.info("删除财务记录  deleteUsers "+ids);
		ModelAndView modelAndView = new ModelAndView("redirect:/financeRecord/list/");
		Long[] id=getIdArray(ids);
		//系统管理财务记录操作日志
		for(Long Id:id){
			FinanceRecord financeRecord = financeRecordService.getFinanceRecord(Id);
			String operate = "删除财务记录：" +financeRecord.getAmount()+"  "+financeRecord.getAmountUser();
			LogInfo logInfo = BaseUtil.addOperateLog(request, operate);
			if(null != logInfo){
				sysLogInfoService.addLogInfo(logInfo);
			}
		}
		financeRecordService.deleteFinanceRecords(id);
		
		modelAndView.addObject("message",  "1");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/getJsonFinanceRecord", method=RequestMethod.GET)
	@ResponseBody
	public FinanceRecord getJsonFinanceRecord(Long id){
		logger.info("查询财务记录  getJsonFinanceRecordr "+id);
		FinanceRecord financeRecord = financeRecordService.getFinanceRecord(id);
		return financeRecord;
	}
	
	private String saveFile(MultipartFile file, String homePath, String savePath){
		String filePath = "";
		if(file!=null){
			try {
				String fileName = file.getOriginalFilename();
				if(StringUtils.isNotBlank(fileName)){
					String fileExt = FilePathUtil.getSuffix(fileName);
					filePath = File.separator + Constant.FILE_SAVE_PATH + File.separator + savePath;
					File saveFolder = new File(homePath + filePath);
					if(!saveFolder.exists()){
						saveFolder.mkdirs();
					}
					filePath = filePath +  File.separator + System.currentTimeMillis() + "." + fileExt;
					File newFile = new File(homePath + filePath);
					file.transferTo(newFile);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return filePath;
	}
}
