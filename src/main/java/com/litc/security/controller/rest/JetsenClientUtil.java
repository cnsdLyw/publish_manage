package com.litc.security.controller.rest;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class JetsenClientUtil {
	
	public static void getReturnStr(Element message,boolean flag,String str){
		message.addAttribute("status",String.valueOf(flag));
		Element text = message.addElement("text");
		text.setText(str);
	}
	
	public static void writeXmlDocument(Element element,Object obj, String Encode) { 
        try {   
            String rootname = obj.getClass().getSimpleName();//获得类名   
            Field[] properties = obj.getClass().getDeclaredFields();//获得实体类的所有属性   
            Element secondRoot;
            if(rootname.equals("ClientBookView")){
            	secondRoot = element.addElement("book");
            	for (int i = 0; i < properties.length; i++) {                      
            		Method meth = obj.getClass().getMethod("get"+ properties[i].getName().substring(0, 1).toUpperCase() + properties[i].getName().substring(1));   
            		if(meth.invoke(obj)!=null && !properties[i].getName().equals("orgCode")){
            			if(properties[i].getName().equals("price")){
            				DecimalFormat  df  = new DecimalFormat("######0.00"); 
            				df.format(meth.invoke(obj));
            				secondRoot.addElement(properties[i].getName()).setText(df.format(meth.invoke(obj)));   
            			}else{
            				secondRoot.addElement(properties[i].getName()).setText(meth.invoke(obj).toString());  
            			}
            		}else if(meth.invoke(obj) == null && properties[i].getName().equals("isSend")){
            				secondRoot.addElement(properties[i].getName()).setText(String.valueOf(-1)); 
            		}
            	}   
            }else{
            	secondRoot = element.addElement(rootname);            //二级节点   
            	for (int i = 0; i < properties.length; i++) {                      
            		Method meth = obj.getClass().getMethod("get"+ properties[i].getName().substring(0, 1).toUpperCase() + properties[i].getName().substring(1));   
            		if(meth.invoke(obj)!=null){
            			secondRoot.addElement(properties[i].getName()).setText( meth.invoke(obj).toString());   
            		}
            	}   

            }
        } catch (Exception e) {
        	e.printStackTrace();
        }   
	}  

	public static String setDoucment(){
		return null;
	}
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
    
    public static Date stringToDate(String str){
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        Date date = null;  
        try {  
            // Fri Feb 24 00:00:00 CST 2012  
            date = format.parse(str);   
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        // 2012-02-24  
        date = java.sql.Date.valueOf(str);  
                                              
        return date; 
    }
    
    public static Object classReflect(NodeList childNodeList,org.w3c.dom.Element e,Object obj){
    	try {
    		if(childNodeList != null && !childNodeList.equals("")){
    			for (int j = 0; j < childNodeList.getLength(); j++) {
    				Node nod = childNodeList.item(j);
    				if(nod.getNodeType() == org.w3c.dom.Element.ELEMENT_NODE){
    					org.w3c.dom.Element ele = (org.w3c.dom.Element) nod;
    					String nodeName = ele.getTagName();
    					String nodeValue = e.getElementsByTagName(nodeName).item(0).getFirstChild().getNodeValue(); 
    					Class<?> obj1 = obj.getClass();
    					Field[] properties = obj1.getClass().getDeclaredFields();
    					for (int k = 0; k < properties.length; k++) {
    						String name  = properties[k].getName();
    						Method method= obj1.getMethod("set"+properties[k].getName().substring(0, 1).toUpperCase() + properties[k].getName().substring(1), properties[k].getType());
    						if(method != null){
    							Object Info = obj1.newInstance();
    							obj = method.invoke(Info,nodeValue);
    						}
    					}
    				}
    			}
    			return obj;
    		}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
    	return null;
    }
}
