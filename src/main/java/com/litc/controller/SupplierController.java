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
import com.litc.security.model.LogInfo;
import com.litc.security.model.Supplier;
import com.litc.security.service.BaseUtil;
import com.litc.security.service.SysLogInfoService;
import com.litc.service.SupplierService;
import com.litc.system.model.Classification;
import com.litc.system.service.ClassificationService;

/**
 * 
 * @author LITC Liyw 2018-3-30
 */

@Controller
@RequestMapping(value = "/supplier")
public class SupplierController extends BaseController<Supplier> {
	
	private final static Logger logger = LoggerFactory.getLogger(SupplierController.class);
	
	@Autowired
	private SupplierService supplierService;
	
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
		ModelAndView modelAndView = new ModelAndView("supplier/supplier-list");
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
		page = supplierService.getSuppliersByPages(pageNo, PAGEPERSIZE,sortDirection,orderType,keyWord);
		modelAndView.addObject("keyWord",keyWord);
		modelAndView.addObject("pageContent",page);
		return modelAndView;
	}
	
	@RequestMapping(value = "/addSupplier", method = RequestMethod.GET)
	public ModelAndView addSupplier() {
		logger.info("添加供应商  addSupplier");
		ModelAndView modelAndView = new ModelAndView("supplier/supplier-add");
		modelAndView.addObject("supplier", new Supplier());
		List<Classification> classList = classificationService.getClassByKey(Constant.SYS_CLASS_INDUSTRY); 
		modelAndView.addObject("classList",classList);//获取行业分类
		return modelAndView;
	}
	
	@RequestMapping(value = "/editSupplier/{id}", method = RequestMethod.GET)
	public ModelAndView editSupplier(@PathVariable Long id) {
		logger.info("修改供应商  editSupplier");
		ModelAndView modelAndView = new ModelAndView("supplier/supplier-add");
		Supplier supplier = supplierService.getSupplier(id);
		modelAndView.addObject("supplier", supplier);
		List<Classification> classList = classificationService.getClassByKey(Constant.SYS_CLASS_INDUSTRY); 
		modelAndView.addObject("classList",classList);//获取行业分类
		return modelAndView;
	}
	
	@RequestMapping(value = "/saveSupplier", method = RequestMethod.POST)
	public ModelAndView saveSupplier(HttpServletRequest request,@ModelAttribute Supplier supplier,
						@RequestParam("idCardObverseFile") MultipartFile idCardObverseFile,
						@RequestParam("idCardReverseFile") MultipartFile idCardReverseFile,
						@RequestParam("businessLicenceCopyFile") MultipartFile businessLicenceCopyFile,
						@RequestParam("certificateAuthorizationFile") MultipartFile certificateAuthorizationFile
							) {
		ModelAndView modelAndView = new ModelAndView("redirect:/supplier/list/");
		logger.info("保存供应商  saveOrganization "+supplier.getCompanyName());
		
		try {
			String home = request.getServletContext().getRealPath("/");
			SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMdd");
			String tempPath = sFormat.format(new Date());
			String str1 = saveFile(idCardObverseFile, home, "idCardObverseFile"+File.separator+tempPath);
			if(StringUtils.isNotBlank(str1)){
				supplier.setIdCardObverse(str1);
				supplier.setIdCardObverseName(idCardObverseFile.getOriginalFilename());
			}
			String str2 = saveFile(idCardReverseFile,  home, "idCardReverseFile"+File.separator+tempPath);
			if(StringUtils.isNotBlank(str2)){
				supplier.setIdCardReverse(str2);
				supplier.setIdCardReverseName(idCardReverseFile.getOriginalFilename());
			}
			String str3 = saveFile(businessLicenceCopyFile,  home,"businessLicenceCopyFile"+File.separator+tempPath);
			if(StringUtils.isNotBlank(str3)){
				supplier.setBusinessLicenceCopy(str3);
				supplier.setBusinessLicenceCopyName(businessLicenceCopyFile.getOriginalFilename());
			}
			String str4 = saveFile(certificateAuthorizationFile,  home, "certificateAuthorizationFile"+File.separator+tempPath);
			if(StringUtils.isNotBlank(str4)){
				supplier.setCertificateAuthorization(str4);
				supplier.setCertificateAuthorizationName(certificateAuthorizationFile.getOriginalFilename());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		supplier.setStatus(0);
		supplier.setLastModifyTime(new Date());
		supplierService.addSupplier(supplier);
		modelAndView.addObject("message",  "1");
		return modelAndView;
	}
	
	@RequestMapping(value="/deleteSupplier/{id}", method=RequestMethod.GET)
	public ModelAndView deleteUser(@PathVariable Long id,HttpServletRequest request) {
		logger.info("删除供应商  deleteUser "+id);
		ModelAndView modelAndView = new ModelAndView("redirect:/supplier/list/");
		
		//系统管理供应商操作日志
		Supplier supplierr = supplierService.getSupplier(id);
		String operate = "删除供应商：" + supplierr.getCompanyName();
		LogInfo logInfo = BaseUtil.addOperateLog(request, operate);
		if(null != logInfo){
			sysLogInfoService.addLogInfo(logInfo);
		}
		supplierService.deleteSupplier(id);
		
		modelAndView.addObject("message",  "1");
		return modelAndView;
	}
	
	@RequestMapping(value="/deleteSuppliers/{ids}", method=RequestMethod.GET)
	public ModelAndView deleteUsers(@PathVariable String ids,HttpServletRequest request) {
		logger.info("删除供应商  deleteUsers "+ids);
		ModelAndView modelAndView = new ModelAndView("redirect:/supplier/list/");
		Long[] id=getIdArray(ids);
		//系统管理供应商操作日志
		for(Long Id:id){
			Supplier supplierr = supplierService.getSupplier(Id);
			String operate = "删除供应商：" + supplierr.getCompanyName();
			LogInfo logInfo = BaseUtil.addOperateLog(request, operate);
			if(null != logInfo){
				sysLogInfoService.addLogInfo(logInfo);
			}
		}
		supplierService.deleteSuppliers(id);
		
		modelAndView.addObject("message",  "1");
		return modelAndView;
	}
	
	@RequestMapping(value="/isEmailExist", method=RequestMethod.GET)
	@ResponseBody
	public boolean isEmailExist(String email){
		return supplierService.isEmailExist(email);
	}
	
	@RequestMapping(value="/isEmailExistWithId", method=RequestMethod.GET)
	@ResponseBody
	public boolean isEmailExist(Long id,String email){
		return supplierService.isEmailExist(id,email);
	}
	
	@RequestMapping(value="/getJsonSupplier", method=RequestMethod.GET)
	@ResponseBody
	public Supplier getJsonSupplier(Long id){
		logger.info("查询供应商  getJsonSupplierr "+id);
		Supplier supplier = supplierService.getSupplier(id);
		return supplier;
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
