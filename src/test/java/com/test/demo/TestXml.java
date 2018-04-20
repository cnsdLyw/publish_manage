package com.test.demo;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TestXml {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String msg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><messageIndex flag=\"1\"><task><BookId>258133499892642054</BookId><CreatorOrgCode>101-1101-026-3</CreatorOrgCode><ProcessorOrgCode>101-1101-026-3</ProcessorOrgCode></task></messageIndex>";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document msgDocument = null;
		try {
			builder = factory.newDocumentBuilder();
			try {
				StringReader msgStr = new StringReader(msg);
				InputSource source = new InputSource(msgStr);
				msgDocument = builder.parse(source);
				Element messageIndex = msgDocument.getDocumentElement();
				String flag = null;
				if(StringUtils.isNotBlank(messageIndex.getAttribute("flag"))){
					flag = messageIndex.getAttribute("flag");
				}
				
				System.out.println("FLAG = "+flag);
				//flag==1 表示提交加工任务
				if(flag!=null&&flag.equals("1")){
					NodeList taskList = messageIndex.getElementsByTagName("task");
					
					for (int i = 0; i < taskList.getLength(); i++) {
						Element task = (Element) taskList.item(i);
						Element BookId = (Element)task.getElementsByTagName("BookId").item(0);
						Element CreatorOrgCode = (Element)task.getElementsByTagName("CreatorOrgCode").item(0);
						Element ProcessorOrgCode = (Element)task.getElementsByTagName("ProcessorOrgCode").item(0);
						System.out.println("OTO  "+BookId.getTextContent() + " "+CreatorOrgCode.getTextContent()+ " "+ProcessorOrgCode);
					}
				}	
			} catch (SAXException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		} catch (ParserConfigurationException e1) {

			e1.printStackTrace();
		}
		
		
		/*
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			Source xmlSource = new DOMSource(msgDocument);
			StringWriter writerStr = new StringWriter();
			Result resultStr = new StreamResult(writerStr);
			transformer.transform(xmlSource, resultStr);
			String str = writerStr.getBuffer().toString();
			System.out.println("STR "+str);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
	
		
		
		
	}

}
