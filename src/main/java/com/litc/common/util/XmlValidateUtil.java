package com.litc.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.util.XMLErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXParseException;
import com.litc.security.controller.SecurityController;

public class XmlValidateUtil {
	    private final static Logger logger = LoggerFactory.getLogger(SecurityController.class);
	    public static List<String> listFile = new ArrayList<String>();  
	    /** 
	     * 利用xsd文件验证xml文件的结构合法性 
	     *  
	     * @param xmlFile 
	     * @param xsdFile 
	     * @return 
	     */  
	    @SuppressWarnings("unchecked")
		public static Map<String, Object> validateXMLByXSD(String xmlFile, String xsdFile) {  
	        Boolean flag = false;
	        Map<String, Object>  dataMap=new HashMap<String, Object>();
	        List<String> list = new ArrayList<String>();
	        try {  
	        	//创建默认的XML错误处理器 
	            XMLErrorHandler errorHandler = new XMLErrorHandler(); 
                SchemaFactory factory =  SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                Schema schema = factory.newSchema(new File(xsdFile));
                Validator validator = schema.newValidator();
                validator.setErrorHandler(errorHandler);
                File file = new File(xmlFile);
                validator.validate(new StreamSource(file));
                if (errorHandler.getErrors().hasContent()) { 
	                List<Element> elist = errorHandler.getErrors().content();
	                List<Map<String, Object>> errlist  = new ArrayList<Map<String,Object>>();
	        		for (Element node : elist) {
	        			 Map<String, Object> map = new HashMap<String, Object>();
	        			 String text = node.getText();
	        			 text = text.substring(text.indexOf(":")+1);
	        			 String column = node.attributeValue("column");
	        			 String line = node.attributeValue("line");
	        			 map.put("line", line);
	        			 map.put("column", column);
	        			 map.put("text", text);
	        			 errlist.add(map);
	        		}
	        		//排序
	        		/*Collections.sort(errlist,  new Comparator<Map<String, Object>>(){  
	                     public int compare(Map<String, Object> o1, Map<String, Object> o2) {  
	                      String line1 =(String)o1.get("line");//name1是从你list里面拿出来的一个  
	                      String line2= (String)o2.get("line"); //name1是从你list里面拿出来的第二个name      
	                      return line1.compareTo(line2);    
	                    }  
	                });*/
	        		List<Map<String,Object>> tmpList=new ArrayList<Map<String,Object>>();
	                Set<String> keysSet = new HashSet<String>();
	                int n = errlist.size();
	                for (int i = 0; i < n; i++) {
	                	Map<String, Object> map = errlist.get(i); 
	                    String line = (String) map.get("line");
	                    String column = (String) map.get("column");
	                    String text = (String) map.get("text");
	                    StringBuffer lineinfo = new StringBuffer();
	                    lineinfo.append("line:").append(line).append(",column:").append(column);
	                    int beforeSize = keysSet.size();
	                    String cnt = line+column;
	                    keysSet.add(cnt);
	                    int afterSize = keysSet.size();
	                    if(afterSize == beforeSize + 1){
                        	tmpList.add(map);
                        	list.add(lineinfo.append(text).toString());
	                    }else {
	                    	String info = list.get(afterSize-1);
	                    	info= info+text;
	                    	list.set(afterSize-1, info);
						}
	                }
	        		logger.error("XML文件"+xmlFile+"校验失败！");
	            } else { 
	            	flag = true;
	            	logger.info("XML文件"+xmlFile+"校验成功！");
	            } 
	        } catch (SAXParseException ex) { 
	        	logger.error("XML文件"+xmlFile+"校验失败！"+ex.getLocalizedMessage());
	        	list.add("文件内容错误，"+ex.getLocalizedMessage());
		    }  catch (Exception ex) { 
		    	logger.error("XML文件"+xmlFile+"校验失败！"+ex.getLocalizedMessage());
		    	list.add("文件内容错误，"+ex.getLocalizedMessage());
		    	ex.printStackTrace();
		    }   
	        dataMap.put("FLAG", flag);
	        dataMap.put("ERRLIST", list);
	        return dataMap;
	    } 
	  
	public static void getXmls(String zipPath,List<String> filePathList ) {
		File dir = new File(zipPath);
	    File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                	getXmls(files[i].getAbsolutePath(),filePathList); // 获取文件绝对路径
                } else if (fileName.endsWith("xml")||fileName.endsWith("XML")) { // 判断文件名是否以.xml结尾
                    String strFileName = files[i].getAbsolutePath();
                    filePathList.add(strFileName);
                } else {
                    continue;
                }
            }

        }
		
	}
	public static int validateXML(String zipPath) {
		File dir = new File(zipPath);
		File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getName();
				Map<String, String> map =new HashMap<>();
				if (files[i].isDirectory()) { // 判断是文件还是文件夹
					validateXML(files[i].getAbsolutePath()); // 获取文件绝对路径
				} else if (fileName.endsWith("xml")||fileName.endsWith("XML")) { // 判断文件名是否以.xml结尾
				} else {
					return -1;
				}
			}
			
			return 0;
		}else {
			return -1;
		}
		
	}
	
    //参数string为你的文件名
	private static String readFileContent(File file) throws IOException {

		BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

		String content = "";
		StringBuilder sb = new StringBuilder();

		while (content != null) {
			content = bf.readLine();

			if (content == null) {
				break;
			}

			sb.append(content.trim());
		}

		bf.close();
		return sb.toString();
	}
   @SuppressWarnings({"unchecked" })
   public static Map<String, Object>  updateDocument(String filepath) {
	   Map<String, Object>  dataMap=new HashMap<String, Object>();
	   String type="";
	   String rtnCode="0";
	   try {
		 //读取XML文件，获得document对象
		 SAXReader reader = new SAXReader();
		 Document document = reader.read(new File(filepath));
		 //获得某个节点的属性对象
		 Element rootElem = document.getRootElement();
		 List<Namespace> declareNamespaces = rootElem.declaredNamespaces();  //获取XML document已经声明的namespace  
		 for (Namespace ns: declareNamespaces){  
		     System.out.println("namespace prefix:"+ ns.getPrefix()+", namespace URI:"+ns.getURI());  
		 }  
		 if (declareNamespaces.size()<=0) {
			
			 Namespace namespace =new Namespace("", "http://ns.editeur.org/onix/3.0/reference");
			 QName a = new QName("ONIXMessage", namespace);
			 rootElem.setQName(a);
			 
			 getNodes(rootElem);// 从根节点开始遍历所有节点  
		 }
		//获取测试用例类型
		List<Element> childElements = rootElem.elements();
        for (Element child : childElements) {
        	 type = child.elementText("NotificationType");
        }
	    saveDocument(document, filepath);
	   } catch (DocumentException e) {
		    rtnCode="-1";
			e.printStackTrace();
	   } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
	   dataMap.put("type", type);
	   dataMap.put("rtnCode", rtnCode);
	   return dataMap;
    }

   /** 
    * 从指定节点Element node开始,递归遍历其所有子节点 
    */  
   @SuppressWarnings("unchecked")
   public static void getNodes(final Element node) {  
 
       // 当前节点的名称、文本内容和属性  
       String nodeName = node.getName();
       Namespace namespace =new Namespace("", "http://ns.editeur.org/onix/3.0/reference");
       QName name = new QName(nodeName, namespace);
	   node.setQName(name);
       /*final List<Attribute> listAttr = node.attributes();// 当前节点的所有属性  
       for (final Attribute attr : listAttr) {// 遍历当前节点的所有属性  
           final String name = attr.getName();// 属性名称  
           final String value = attr.getValue();// 属性的值  
           System.out.println("属性名称：" + name + "---->属性值：" + value);  
       }  */
 
       // 递归遍历当前节点所有的子节点  
       final List<Element> listElement = node.elements();// 所有一级子节点的list  
       for (final Element e : listElement) {// 遍历所有一级子节点  
           getNodes(e);// 递归  
       }  
   }    
   
   /** 
    * 平台导入xml文件时校验xml
    * 利用xsd文件验证xml文件的结构合法性
    * @param xmlFile 
    * @param xsdFile 
    * @return 
    */  
   @SuppressWarnings("unchecked")
	public static boolean validateXMLByXSD(File file,String xsdFile) {
		Boolean flag = false;
		List<String> list = new ArrayList<String>();
		try {
			// 创建默认的XML错误处理器
			XMLErrorHandler errorHandler = new XMLErrorHandler();
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new File(xsdFile));
			Validator validator = schema.newValidator();
			validator.setErrorHandler(errorHandler);
			validator.validate(new StreamSource(file));
			if (errorHandler.getErrors().hasContent()) {
				List<Element> elist = errorHandler.getErrors().content();
        		for (Element node : elist) {
        			 String text = node.getText();
        			 text = text.substring(text.indexOf(":")+1);
        			 String column = node.attributeValue("column");
        			 String line = node.attributeValue("line");
        			 logger.info("XML文件" + file.getName() +" ----  "+line+"   "+column+"   "+text);
        		}
			} else {
				flag = true;
				logger.info("XML文件" + file.getName() + "校验成功！");
			}
		} catch (SAXParseException ex) {
			logger.error("XML文件" + file.getName() + "校验失败！" + ex.getLocalizedMessage());
			list.add(ex.getLocalizedMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	} 
   
    /** 
	* 把改变的domcument对象保存到指定的xml文件中 
	* @author chenleixing 
	* @throws IOException  
	*/  
	public static void saveDocument(Document document,String xmlFile) throws IOException{ 
		 OutputFormat format = OutputFormat.createPrettyPrint();
         Writer xmlwriter = new OutputStreamWriter(new FileOutputStream(xmlFile), "UTF-8");
         format.setEncoding("UTF-8");
         XMLWriter writer = new XMLWriter(xmlwriter,format);
         writer.write(document);
         writer.close();
         xmlwriter.close();
	}
	  public static void main(String[] args) {
		  try {
			  List<String> filePathList = new ArrayList<String>();
			  System.out.println(filePathList.size());
			  //http:\180.0.0.205\group1\M00\00\6E\tAAAzVlsz3qAOH0JAABMbmPTHrw294.xml
					 //getXmls("http:\\180.0.0.205\\group1\\M00\\00\\6E\\tAAAzVlsz3qAOH0JAABMbmPTHrw294.xml",filePathList);
			  // updateDocument("E:/cuiyc/11.xml");
			  validateXMLByXSD("D:/Documents/Downloads/互联网 医疗健康_eedbfac3-b561-4750-9ae6-aed2317c9740.xml","D:/program/apache-tomcat-7.0.56-windows-x64/apache-tomcat-7.0.56/webapps/web21/cnonix/ONIX_BookProduct_XSD_schema+codes_Issue_30/ONIX_BookProduct_3.0_reference.xsd");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	 }
}
