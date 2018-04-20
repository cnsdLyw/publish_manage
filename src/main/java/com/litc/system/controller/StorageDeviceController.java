package com.litc.system.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.litc.common.util.file.FileUtil;
import com.litc.common.util.file.JetsenFtpClient;
import com.litc.security.common.page.PageParam;
import com.litc.security.controller.BaseController;
import com.litc.security.repository.IComponent;
import com.litc.system.model.StorageDevice;
import com.litc.system.service.StorageDeviceService;

@Controller
@RequestMapping("/storageDevice")
public class StorageDeviceController extends BaseController<StorageDevice> {

	private final static Logger logger = LoggerFactory
			.getLogger(StorageDeviceController.class);

	/**
	 * 每页数量
	 */
	private static final int AUTHORITYPAGESIZE = 12;

	/**
	 * 排序字段
	 */
	private static String ORDER_TYPE = "id";

	@Autowired
	private StorageDeviceService storageDeviceService;

	@RequestMapping(value = "/list")
	public ModelAndView listOfStorageDevice(HttpServletRequest request,
			String ftpName) throws Exception {
		ModelAndView modelAndView = new ModelAndView(
				"system/storageDevice/storageDevice-list");
		pageNo = this.getInt(request, "pageNo");
		// 创建查询参数
		PageParam orgNameParam = new PageParam("ftpName", ftpName,
				IComponent.SERCHTYPE_LIKE);
		page = storageDeviceService.getStorageDevicesByPages(pageNo,
				AUTHORITYPAGESIZE, SORT_TYPE_DESC, ORDER_TYPE, orgNameParam);
		modelAndView.addObject("ftpName", ftpName);
		modelAndView.addObject("pageContent", page);
		//ftp test
		
		//StorageDevice  stoDevice = storageDeviceService.getStorageDevice("FTP_D");
		//System.out.println(" "+stoDevice.getFtpName());
		//上传文件
		//StorageDeviceUtil.uploadFile("FTP_D", "/A/B/","AA.RAR", new File("E:/test/asds.rar"));
		//上传文件夹
		//StorageDeviceUtil.uploadFolder("FTP_D", "/sds/sss/12/f", new File("F:/Model/tpmo_449_green"));
		//下载文件
		//StorageDeviceUtil.downloadFile("FTP_D,mysddsd.txt", "D:/CORE/aa.txt");
		//下载文件夹
		//StorageDeviceUtil.downloadFolder("FTP_D", "/sds/sss/12/f", "D:/CORE/订饭");
		//删除文件
		//StorageDeviceUtil.deleteFile("FTP_D", "/a/b/cmdk.txt");
		//删除文件夹
		//StorageDeviceUtil.deletePath("FTP_D", "/a/b/efg");//("FTP_D", "/sds/sss/12/f", "D:/CORE/订饭");
		//拷贝文件
		//StorageDeviceUtil.copyFtpFile("FTP_D,/A/B/AA.rar", "FTP_D,/文件夹/mysddsd.rar");
		
		return modelAndView;
	}
    
	/**
	 * 验证FTP test
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/checkFtpStr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> checkFtpStr(@ModelAttribute StorageDevice storageDevice) throws Exception {
		return getReturnStr("1", "Ftp连接失败！");
	}
	/**
	 * 验证FTP
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/checkFtp", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> checkFtp(@ModelAttribute StorageDevice storageDevice) throws Exception {
		logger.info("测试ftp  checkFtp " + storageDevice.getFtpName());
		String flag = "";
		JetsenFtpClient  ftpUtil = null;
		String host = JetsenFtpClient.getHost(storageDevice.getFtpUrl());
		int port = JetsenFtpClient.getFtpPort(storageDevice.getFtpUrl());
		String relPath = JetsenFtpClient.getRelPath(storageDevice.getFtpUrl());
		//测试FTP能否联通
		try {
			Thread.sleep(2000);
			ftpUtil = new JetsenFtpClient(host, port, storageDevice.getFtpUser(), storageDevice.getFtpPassword());
		} catch (Exception e) {
			e.printStackTrace();
			flag = "FTP无法连接";
		}
		//测试FTP能否上传文件
		if(ftpUtil!=null){
			String text = "Myftp";
			String testFileName = System.currentTimeMillis() + ".txt";
			try {
				ftpUtil.uploadFile(new ByteArrayInputStream(text.getBytes("UTF-8")),relPath + "/test/" + testFileName);
			} catch (Exception e) {
				e.printStackTrace();
				flag = "FTP无法上传文件";
			}
			
			if(StringUtils.isNotBlank(storageDevice.getLocalFolderPath())){
				String tempFilePath = storageDevice.getLocalFolderPath() + "\\test\\"+ testFileName;
				File file = new File(tempFilePath);
				if (file.exists()) {
					FileUtil.deleteFile(tempFilePath);
					if (file.exists()) {
						ftpUtil.deleteFile("/test/" + testFileName);
						flag = "共享路径无法读写文件";
					} else {
						flag = "success";
					}
				} else {
					flag = "共享路径配置不正确，无法读取文件!";
				}
			}else{
				//删除临时文件
				ftpUtil.deleteFile("/test/" + testFileName);
				flag = "success";
			}
		}
		
		if(ftpUtil!=null){
			ftpUtil.closeFtp();
		}
		if(flag.equals("success")){
			return getReturnStr("1", "FTP连接成功");
		}else{
			return getReturnStr("0", flag);
		}
		
		//return storageDevice;
	}
	
	private Map<String,String> getReturnStr(String status,String message){
		Map<String,String> map = new HashMap<String, String>();
		map.put("status", status);
		map.put("message", message);
		return map;
	}
	
	@RequestMapping(value = "/getStorageDevice", method = RequestMethod.GET)
	public StorageDevice getStorageDevice(Long id) {
		logger.info("查询存储设备  getStorageDevice " + id);
		StorageDevice storageDevice = storageDeviceService.getStorageDevice(id);
		return storageDevice;
	}

	@RequestMapping(value = "/getJsonStorageDevice", method = RequestMethod.GET)
	@ResponseBody
	public StorageDevice getJsonStorageDevice(Long id) {
		logger.info("查询存储设备  deleteRole " + id);
		StorageDevice storageDevice = storageDeviceService.getStorageDevice(id);
		return storageDevice;
	}

	@RequestMapping(value = "/addStorageDevice", method = RequestMethod.GET)
	public ModelAndView addStorageDevicePage() {
		logger.info("添加存储设备  addStorageDevicePage");
		ModelAndView modelAndView = new ModelAndView(
				"system/storageDevice/storageDevice-add");
		modelAndView.addObject("storageDevice", new StorageDevice());
		return modelAndView;
	}

	@RequestMapping(value = "/editStorageDevice/{id}", method = RequestMethod.GET)
	public ModelAndView editStorageDevicePage(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView(
				"system/storageDevice/storageDevice-add");
		StorageDevice storageDevice = storageDeviceService.getStorageDevice(id);
		modelAndView.addObject("storageDevice", storageDevice);
		modelAndView.addObject("classes", getOrgTypes());
		logger.info("添加存储设备  editStorageDevicePage "
				+ storageDevice.getFtpName());
		return modelAndView;

	}

	@RequestMapping(value = "/saveStorageDevice", method = RequestMethod.POST)
	public ModelAndView saveStorageDevice(
			@ModelAttribute StorageDevice storageDevice) {
		logger.info("保存存储设备  saveStorageDevice " + storageDevice.getFtpName());
		ModelAndView modelAndView = new ModelAndView(
				"redirect:/storageDevice/list/");
		storageDeviceService.addStorageDevice(storageDevice);
		if (isSaveSuccess(storageDevice.getId())) {
			modelAndView.addObject("message", "1");
		} else {
			modelAndView.addObject("message", "0");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/isStorageDeviceUsed", method = RequestMethod.GET)
	@ResponseBody
	public StorageDevice isStorageDeviceUsed(Long id) {
		// StorageDevice storageDevice =
		// storageDeviceService.getStorageDevice(id);
		// return storageDevice;
		return null;
	}

	@RequestMapping(value = "/deleteStorageDevice/{id}", method = RequestMethod.GET)
	public ModelAndView deleteStorageDevice(@PathVariable Long id) {
		ModelAndView modelAndView = new ModelAndView(
				"redirect:/storageDevice/list/");
		if (!(storageDeviceService.isStorageDeviceUsed(id) > 0)) {
			storageDeviceService.deleteStorageDevice(id);
			modelAndView.addObject("message", "1");
		} else {
			modelAndView.addObject("message", "-1");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/deleteStorageDevices/{ids}", method = RequestMethod.GET)
	public ModelAndView deleteStorageDevices(@PathVariable String ids) {
		ModelAndView modelAndView = new ModelAndView(
				"redirect:/storageDevice/list/");
		Long[] id = getIdArray(ids);
		if (!(storageDeviceService.isStorageDeviceUsed(id) > 0)) {
			storageDeviceService.deleteStorageDevices(id);
			modelAndView.addObject("message", "1");
		} else {
			modelAndView.addObject("message", "-1");
		}
		return modelAndView;
	}

}