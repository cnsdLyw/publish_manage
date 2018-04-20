package com.litc.security.controller.rest;

import java.awt.print.Book;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.litc.common.util.Constant;
import com.litc.common.util.file.FileUtil;
import com.litc.fileSystem.service.JetsenFilesService;
import com.litc.security.model.Organization;
import com.litc.security.model.User;
import com.litc.security.service.UserService;

@RestController
@RequestMapping("/rests")
public class JetsenRestController {

	private final static Logger logger = LoggerFactory.getLogger(JetsenRestController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private JetsenFilesService jetsenFilesFastDFService;
	
	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@RequestBody String body) {
		System.out.println("body  "+body);
		return "{\"loginName\":\"admin\",\"password\":\"1111111\"}";
	}
	
	@ResponseBody  
	@RequestMapping(value = "/upload", method = RequestMethod.POST)  
	public String upload(HttpServletRequest request,String uuid, MultipartFile file) throws Exception { 
		logger.info("开始接收编辑器数据-------- ");
	    SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMdd");
	    if(StringUtils.isBlank(uuid)){//新建图书生成UUID
	    	uuid = UUID.randomUUID().toString();
	    }
	    //uuid = "547f938e-f1ea-4a1c-aa8c-b4965760b755";
	    String home = request.getServletContext().getRealPath("/");//项目根目录
	    
	    //文件下载到临时文件夹内
	    String tempPath = Constant.SERVER_LOCAL_PATH+File.separator+sFormat.format(new Date())+File.separator+uuid;//临时文件夹路径
	    File folder = new File(tempPath); //下载的文件夹地址
	    logger.info("生成临时文件存储路径 "+folder.getPath());
	    if(folder.exists()){//如果文件夹存在则先情况文件夹内的数据
	    	logger.info("临时文件存储路径已经存在，清除已有数据。");
	    	File[] files = folder.listFiles();
	    	for(File temp:files){
				if(temp.isDirectory()){
					FileUtil.deleteFolder(temp);
				}else if(temp.isFile()){
    				temp.delete();
				}
			}
	    }else{
	    	logger.info("临时文件存储路径不存在，新建文件夹。");
	    	folder.mkdirs();
	    }
		// 下载文件到临时文件夹
		File newFile = new File(folder.getPath() + File.separator
				+ System.currentTimeMillis() + ".jonix");
		file.transferTo(newFile);
		
		if (StringUtils.isNotBlank(uuid)) {} else {// 新建图书

		}
		return "1";
	}  
	
	/**
	 * 上传数据接口，并返回文件访问路径
	 * @param request
	 * @param uuid
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@ResponseBody  
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)  
	public String uploadFile(HttpServletRequest request,@RequestParam("file") MultipartFile file,@RequestParam("loginName") String loginName,@RequestParam("password") String password){ 
		logger.info("开始上传数据-------- ");
		org.dom4j.Document document = DocumentHelper.createDocument(); 
		org.dom4j.Element message = document.addElement("message");
		try {
			User user = userService.loadUser(loginName);
			if(user != null){
				if (password.equals(user.getPassWord())) {
					Organization organization = user.getOrganization();
					if (organization != null) {
						String fileUrl = jetsenFilesFastDFService.uploadFile(file, "");
						if(StringUtils.isNotBlank(fileUrl)){						
							JetsenClientUtil.getReturnStr(message,true,Constant.SERVER_FILE_HOME_URL+fileUrl);
						}else{
							JetsenClientUtil.getReturnStr(message,false,"上传附件发送错误");
						}		
					}else{
						JetsenClientUtil.getReturnStr(message,false,"用户机构信息不正确");
					}
				}else{
					JetsenClientUtil.getReturnStr(message,false,"密码不正确");
				}
			}else{
				JetsenClientUtil.getReturnStr(message,false,"用户不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JetsenClientUtil.getReturnStr(message,false,"调用接口时发生错误");
		}
		return document.asXML();
	}  
	
	private List<String> getList(String str){
		List<String> list = new ArrayList<String>();
		if(StringUtils.isNotBlank(str)){
			list = Arrays.asList(str.trim().split("&"));
		}
		return list;
	}
	
	
	@RequestMapping(value = "/getPath", method = RequestMethod.POST)
	public String getPath(@RequestBody String body) {
		
		System.out.println("body     "+body);
		String returnStr="<Root State=\"true\" Message=\"\">"+
				"<Item type=\"FTP\">"+
						"<meta name=\"host\">127.0.0.1</ meta>"+
			         	"<meta name=\"port\">21</ meta>"+
			         	"<meta name=\"path\">/data/2015/03/17/</ meta>"+
			         	"<meta name=\"userName\">root2</ meta>"+
			         	"<meta name=\"password\">1</ meta>"+
					"</Item>"+
				"</Root>";
		
		

		return returnStr;
	}
	
	@RequestMapping(value = "/getFile", method = RequestMethod.POST)
	public String getFile(MultipartFile file) {
		String name = file.getOriginalFilename();
		
		String t=Thread.currentThread().getContextClassLoader().getResource("").getPath();
		int num=t.indexOf("/WEB-INF");
		String path=(t.substring(1,num)+"\\upload").replace('/', '\\');
		path = path + File.separator + "image" + File.separator ;
		File file1 = new File(path);
		if (!file1.exists()) {
			file1.mkdirs();
		}
		File folder = new File(path,name);
		try {
			file.transferTo(folder);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String fileUrl = "/upload/image/"+name;
		
		return fileUrl;
	}
	
	//notifyImportData
	//getBookInfo
	//getIssueInfo
	//returnSyncResult
	//sendPrintBook
	//uploadBook
	//uploadIssueInfo
	//uploadBorrowInfo
	
	
	
	@ResponseBody  
	@RequestMapping(value = "/setBookStatus", method = RequestMethod.POST)  
	public String setBookStatus(HttpServletRequest request,@RequestBody String messageData) throws Exception { 
		logger.info("更新图书任务状态 setBookStatus "+messageData);
		//所有客户端推送：
		//Comet comet = new Comet();
		//comet.setMessageData(messageData);
		//new CometUtil().pushToAll(comet);

		CometEngine engine = CometContext.getInstance().getEngine();
		// 推送到所有客户端
		engine.sendToAll(Constant.CHANNEL_BOOK_ONIX_CHECK,messageData);
		

		//精准推送给某个客户端
		/*Comet comet = new Comet();
		comet.setUserId("1");//前端到session中的用户id
		comet.setMsgCount(String.valueOf(msgCount));
		comet.setMsgData(resultList);
		new CometUtil()..pushTo(comet);
		*/
		return null;
	}
	
	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		/*String url="http://127.0.0.1:8080/web21/rests/getPath";
		String jsonStr = "<Root>"+
					"<Params type=\"Validate\">"+
						"<param name=\"SysCode\"></param>"+
						"<param name=\"Password\"></param>"+
					"</Params>"+
					"<Params type=\"Content\">"+
					     " <param name=” type”>文件后缀名</param>"+
					"</ Params >"+
					"</Root>";
		String returnStr = restTemplate.postForObject(url, jsonStr, String.class);
		System.out.println("returnStr "+returnStr);
		*/
		
		/*String url="http://127.0.0.1:8080/web21/rests/add";
		String jsonStr = "{\"loginName\":\"admins实打实的\",\"password\":\"1\"}";
		String json = restTemplate.postForObject(url, jsonStr, String.class);
		System.out.println("JSON "+json);*/
		
		//编辑器提交修改数据接口
		/*String url="http://127.0.0.1:8080/web21//rests/uploadFile" ;
		RestTemplate rest = new RestTemplate();
		String filePath = "D:/temp/20170609/6f978635e5dde71115c63721a0efce1b9f1661c2.jpg";  
		//spring-mvc.xml文件中配置可上传文件大小限制
	    FileSystemResource resource = new FileSystemResource(new File(filePath));  
	    MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();  
	    param.add("file", resource);  
	    String string = rest.postForObject(url, param, String.class); 
	    System.out.println("string   "+string);*/
		String url="http://127.0.0.1:8080/web21/rests/setBookStatus";
		String jsonStr = "{\"messageData\":\"admin\"}";
		String json = restTemplate.postForObject(url, jsonStr, String.class);
		System.out.println("JSON "+json);
	}
	

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@RequestParam(value = "login_error", required = false) boolean error, Model model,String email,String loginName,String password) {
        logger.debug("Received request to show login page, error "+error);
        /*if (error) {
            model.addAttribute("error", "输入的用户名或密码错误!");
        }*/
        /*
        UserPasswordEncoder encoder = new UserPasswordEncoder();
    	password = encoder.encode(password.toString());
    	System.out.println("ddddddddddddddddddddddddddd "+password+"  "+encoder.encode(password));
    	*/
    	Md5PasswordEncoder md5 = new Md5PasswordEncoder(); 
    	password = md5.encodePassword(password, null);
    	
    	User user = new User();
		user.setLoginName(loginName);
		user.setPassWord(password);
		userService.addUser(user);
        
		System.out.println("增加用户");
        return "user/register";
    }
    
	/**
	 * create timestamp
	 */
	public String creatTimestamp() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		String str = sdf.format(date);
		return str;
	}

}