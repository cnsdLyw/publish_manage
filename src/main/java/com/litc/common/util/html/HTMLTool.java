package com.litc.common.util.html;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Configuration;
import org.w3c.tidy.Tidy;
/**
 *  Function:HTML处理工具类
 *  @author  zhongying(281264212@qq.com)
 *  @date    2015-1-19 下午3:27:27    
 *  @version 1.0
 */
public class HTMLTool {
    /**
     * 将一段HTML转换为XHTML
     * @param content String
     * @param xmlPI boolean
     * @return String
     */
    public static String convertString2XML(String content, boolean xmlPI)
    {

        if(isXML(content))
            return content;
        content = translate(content); 

        DumpStringStream stream = new DumpStringStream(content, "UTF-8");
        Tidy tidy = new Tidy();

        tidy.setXHTML(true); //产生xhtml
        tidy.setXmlPi(xmlPI); //是否输出<?xml version="1.0"?>
        tidy.setTidyMark(false); //不输出tidy的标识
        tidy.setQuiet(true); //不在标准输出窗口输出警告
        tidy.setShowWarnings(false); //不在标准输出窗口输出警告
        tidy.setWord2000(true); //清除word2000中多余的tag
        tidy.setDocType("omit"); //不输出Doctype
        //tidy.setCharEncoding(Configuration.UTF8); //输出使用UTF8//tidy使用，jtidy用不了
        tidy.setOutputEncoding("UTF8");
        tidy.setWraplen(0x7FFFFFFF); //设置成不wrap 行
        tidy.setWrapScriptlets(true);
        tidy.setWrapAttVals(false);
        tidy.setNumEntities(true); //所有实体引用转换为&#num;格式
        try
        {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            Document doc = tidy.parseDOM(stream, null);
            setCharset(doc, "UTF-8", false); //这里不强制加上字符集
            setBodyNameSpace(doc,"http://www.w3.org/1999/xhtml",true); //为body强制加上命名空间
            tidy.pprint(doc, os);
            stream.close();
            content = os.toString("UTF-8");
            content  = detranslate(content);
            return content;
        }
        catch (Exception ex){
        	
        }

        return "";
    }
    
    public static String convertString2XHTML(String content) throws UnsupportedEncodingException{
//    	 if(isXML(content))
//             return content;
//         content = translate(content); 
        ByteArrayInputStream stream = new ByteArrayInputStream(content.getBytes("UTF-8"));

        ByteArrayOutputStream  os = new ByteArrayOutputStream();
        //实例化Tidy对象
        Tidy tidy = new Tidy();
        tidy.setXmlPi(true); //是否输出<?xml version="1.0"?>
        
        //<meta name="generator" content="HTML Tidy for Java (vers. 2009-12-01), see jtidy.sourceforge.net" />
        tidy.setTidyMark(false); //不输出tidy的标识
        
        //<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
        tidy.setDocType("omit"); //不输出Doctype
        
        //设置输入
        tidy.setInputEncoding("UTF8");
        
        //如果是true  不输出注释，警告和错误信息
        tidy.setQuiet(true);//不在标准输出窗口输出警告
        
        //设置输出
        tidy.setOutputEncoding("UTF8");
        
      //不显示警告信息
        tidy.setShowWarnings(false);//不在标准输出窗口输出警告
        
        //缩进适当的标签内容。
//        tidy.setIndentContent(true);
        
        //内容缩进
//        tidy.setSmartIndent(true);
        
        tidy.setIndentAttributes(false);
        
        //只输出body内部的内容
//        tidy.setPrintBodyOnly(true);
        
        //多长换行
//        tidy.setWraplen(1024);
        tidy.setWraplen(0x7FFFFFFF); //设置成不wrap 行
        
        tidy.setXHTML(true);//产生xhtml
        
        //去掉没用的标签
//        tidy.setMakeClean(true);
        
        //清洗word2000的内容
        tidy.setWord2000(true);//清除word2000中多余的tag
        
        //设置错误输出信息
        tidy.setErrout(new PrintWriter(System.out));
        
        tidy.setWrapScriptlets(true);
        tidy.setWrapAttVals(false);
        
        //<p>&#160;热 热</p>  会把空格&nbsp;换成&#160;，然后数据库保存也会去除&nbsp;
        tidy.setNumEntities(true); //所有实体引用转换为&#num;格式
        
//        tidy.parse(stream, os);
//        return os.toString();
        try
        {
	        Document doc = tidy.parseDOM(stream, null);
	        setCharset(doc, "UTF-8", false); //这里不强制加上字符集
	        setBodyNameSpace(doc,"http://www.w3.org/1999/xhtml",true); //为body强制加上命名空间
	        tidy.pprint(doc, os);
	        stream.close();
	        content = os.toString("UTF-8");
	        content  = detranslate(content);
	        return content;
        }
        catch (Exception ex){
        }
        return "";
  }
    
    
    public static boolean isXML(String sContent){
        boolean bResult = false;
        String BODYTAG = "<body xmlns=\"http://www.w3.org/1999/xhtml\">";
         DocumentBuilder parser;
         DocumentBuilderFactory factory = null;
         org.w3c.dom.Document ndoc = null;
         DumpStringStream ds = null;
         String sHead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+BODYTAG;
         String sTail = "</body>";
         try{
             factory = (DocumentBuilderFactory)Class.forName("org.apache.xerces.jaxp.DocumentBuilderFactoryImpl").newInstance();
             sContent = filterXml(sContent);
             if(sContent.indexOf("<?xml")>=0)
                 ds = new DumpStringStream(sContent.trim(), "UTF-8");
             else ds = new DumpStringStream(sHead+sContent.trim()+sTail, "UTF-8");
             parser = factory.newDocumentBuilder();
             ndoc = parser.parse(ds);
             bResult = true;
         }catch(Exception ex){
         }finally{
             ndoc = null;
             parser = null;
             factory = null;
             ds = null;

         }
         return bResult;
    }
    private static String filterXml(String xml){

        char t1 = 0x8;
        char t2 = 0x5;
        char t3 = 0x4;
        char t4 = 0x3;
        char t5 = 0x7;
        char t6 = 0x1;
        char t7 = 0x2;
        char t8 = 0x6;
        char t9 = 0x9;
        xml = xml.replace(t1,' ');
        xml = xml.replace(t2,' ');
        xml = xml.replace(t3,' ');
        xml = xml.replace(t4,' ');
        xml = xml.replace(t5,' ');
        xml = xml.replace(t6,' ');
        xml = xml.replace(t7,' ');
        xml = xml.replace(t8,' ');
        xml = xml.replace(t9,' ');


        xml = xml.replaceAll("&amp;","&");
        xml = xml.replaceAll("&lt;","<");
        xml = xml.replaceAll("&gt;",">");
        xml = xml.replaceAll("&quot;","\"\"");
        return xml;
    }
    private static String translate(String content){
        content = content.replaceAll("<span style=\"font-size:0pt;\"> </span>","s_st_sst_ss_sss");
        content = content.replaceAll("<span style=\"font-size:1pt;\"> </span>","s_st_sst_ss_sst");
        content = content.replaceAll("<span class=\"zwblankclass\"> </span>","s_st_sst_ss_ssk");
        content = content.replaceAll("<source>","s_st_sst_ss_source");
        content = content.replaceAll("</source>","s_st_sst_ss_sour");
        content = content.replaceAll("<images>","s_st_sst_ss_images");
        content = content.replaceAll("</images>","s_st_sst_ss_imag");
        content = content.replaceAll("<category>","s_st_sst_ss_category");
        content = content.replaceAll("</category>","s_st_sst_ss_catego");
        content = content.replaceAll("<title>","s_st_sst_ss_title");
        content = content.replaceAll("</title>","s_st_sst_ss_tit");

        content = content.replaceAll("<author>","s_st_sst_ss_author");
        content = content.replaceAll("</author>","s_st_sst_ss_auth");
        content = content.replaceAll("<pubdate>","s_st_sst_ss_pubdate");
        content = content.replaceAll("</pubdate>","s_st_sst_ss_pubda");
        content = content.replaceAll("<link>","s_st_sst_ss_link");
          content = content.replaceAll("</link>","s_st_sst_ss_li");

          content = content.replaceAll("<text>","s_st_sst_ss_text");
          content = content.replaceAll("</text>","s_st_sst_ss_te");
          content = content.replaceAll("<description>","s_st_sst_ss_description");
          content = content.replaceAll("</description>","s_st_sst_ss_descripti");
          content = content.replaceAll("<item>","s_st_sst_ss_item");
          content = content.replaceAll("</item>","s_st_sst_ss_it");
        return content;
      }
    protected static void setCharset(Document doc, String strCharset,boolean force){
    	if (doc == null)
        {
            return;
        }
        Element head = findElement(doc, "head");
        if (head == null)
        {
            if (force) //如果要求强制加上，才追加
            {
                head = doc.createElement("head");
                doc.insertBefore(head, doc.getFirstChild());
                Element meta = doc.createElement("meta");
                meta.setAttribute("http-equiv", "Content-Type");
                meta.setAttribute("content", "text/html; charset=" + strCharset);
                head.appendChild(meta);
            }
            return;
        }

        NodeList list = head.getElementsByTagName("meta");
        int len = list.getLength();
        for (int i = 0; i < len; i++)
        {
            Element meta = (Element) list.item(i);
            String name = meta.getAttribute("http-equiv");
            if ("Content-Type".equalsIgnoreCase(name))
            {
                //改变现有值
                meta.setAttribute("content", "text/html; charset=" + strCharset);
                return;
            }
        }

        //前面没有找到，如果要求强制加上，才追加
        if (force)
        {
            Element meta = doc.createElement("meta");
            meta.setAttribute("http-equiv", "Content-Type");
            meta.setAttribute("content", "text/html; charset=" + strCharset);
            head.appendChild(meta);

        }
    }
    protected static Element findElement(Document doc, String name)
    {
        if (doc == null)
        {
            return null;
        }
        Element elem = doc.getDocumentElement();
        if (elem != null)
        {
            Node node = elem.getFirstChild();
            while (node != null)
            {
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    if (node.getNodeName().equals(name))
                    {
                        return (Element) node;
                    }
                }
                node = node.getNextSibling();
            }
        }

        return null;

    }
    /**
     * 为body加上命名空间
     * 2005-9-23  14:54:19
     * @param doc 源文件
     * @param strNameSpace 指定的命名空间
     * @param force 如果没有body或者body没有xmlns属性 是否强行加上
     */
    protected static void setBodyNameSpace(Document doc, String strNameSpace,
            boolean force)
    {
        if (doc == null)
        {
            return;
        }
        Element body = findElement(doc, "body");
        if (body == null)
        {
            if (force) //如果要求强制加上，才追加
            {

                body = doc.createElement("body");
                body.setAttribute("xmlns",strNameSpace);
                try{
                    if (doc.getFirstChild() == null)
                        doc.appendChild((Node) body);
                    else
                        doc.insertBefore(body, doc.getFirstChild());
                }catch(Exception ex){
                }
            }
            return;
        }

        String namespace = body.getAttribute("xmlns");
        if(namespace == null || namespace.length() <= 0)
        {
            body.setAttribute("xmlns",strNameSpace);
        }
        else if(force)
        {
            body.setAttribute("xmlns",strNameSpace);
        }
    }

private static String detranslate(String content){
    content = content.replaceAll("s_st_sst_ss_sss","<span style=\"font-size:0pt;\"> </span>");
    content = content.replaceAll("s_st_sst_ss_sst","<span style=\"font-size:1pt;\"> </span>");
    content = content.replaceAll("s_st_sst_ss_ssk","<span class=\"zwblankclass\"> </span>");
    content = content.replaceAll("s_st_sst_ss_source","<source>");
    content = content.replaceAll("s_st_sst_ss_sour","</source>");
    content = content.replaceAll("s_st_sst_ss_images","<images>");
    content = content.replaceAll("s_st_sst_ss_imag","</images>");
    content = content.replaceAll("s_st_sst_ss_category","<category>");
    content = content.replaceAll("s_st_sst_ss_catego","</category>");
    content = content.replaceAll("s_st_sst_ss_title","<title>");
    content = content.replaceAll("s_st_sst_ss_tit","</title>");

    content = content.replaceAll("s_st_sst_ss_author","<author>");
    content = content.replaceAll("s_st_sst_ss_auth","</author>");
    content = content.replaceAll("s_st_sst_ss_pubdate","<pubdate>");
    content = content.replaceAll("s_st_sst_ss_pubda","</pubdate>");
    content = content.replaceAll("s_st_sst_ss_link","<link>");
      content = content.replaceAll("s_st_sst_ss_li","</link>");

      content = content.replaceAll("s_st_sst_ss_text","<text>");
      content = content.replaceAll("s_st_sst_ss_te","</text>");
      content = content.replaceAll("s_st_sst_ss_description","<description>");
      content = content.replaceAll("s_st_sst_ss_descripti","</description>");
      content = content.replaceAll("s_st_sst_ss_item","<item>");
      content = content.replaceAll("s_st_sst_ss_it","</item>");
    return content;

  }
public static String getLocalHostIP()
{
    String ip = "";
    try
    {
        InetAddress add = InetAddress.getLocalHost();
        ip = add.getHostAddress();
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
    return ip;
}
}
