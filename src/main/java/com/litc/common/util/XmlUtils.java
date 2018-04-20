package com.litc.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlUtils {
	/**
	 * 获得DOM对象
	 * 
	 * @param xmlFile
	 * @param encoding
	 *            解码时用的编码方式
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Document getDocument(File xmlFile, String encoding)
			throws IOException, DocumentException {
		Document document = null;
		if ("UTF-8".equalsIgnoreCase(encoding)) {
			InputSource is = new InputSource();
			is.setEncoding(encoding);
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(xmlFile);
				is.setByteStream(fis);
				document = (new SAXReader()).read(is);
			} finally {
				try {
					if (fis != null) {
						fis.close();
					}
				} catch (Exception e) {

				}
			}
		} else {
			String xmlText = readTextFile(xmlFile, encoding);
			document = org.dom4j.DocumentHelper.parseText(xmlText);
		}

		return document;
	}

	/**
	 * 根据输入流读取DOM对象
	 * 
	 * @param in
	 * @param encoding
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Document getDocument(InputStream in, String encoding)
			throws IOException, DocumentException {
		Document document = null;
		if ("UTF-8".equalsIgnoreCase(encoding)) {
			InputSource is = new InputSource();
			is.setEncoding(encoding);
			is.setByteStream(in);
			document = (new SAXReader()).read(is);
		} else {
			String xmlText = readTextFile(in, encoding);
			document = org.dom4j.DocumentHelper.parseText(xmlText);
		}

		return document;
	}

	/**
	 * 读取文本文件
	 * 
	 * @param xmlFile
	 * @param encoding
	 *            解码时用的编码方式
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String readTextFile(File xmlFile, String encoding)
			throws IOException {
		StringWriter writer;
		if (xmlFile == null)
			throw new NullPointerException("pathname");
		if (encoding == null)
			throw new NullPointerException("encoding");
		writer = new StringWriter();
		FileInputStream fis = null;
		String str = null;
		try {
			fis = new FileInputStream(xmlFile);
			IOUtils.copy(fis, writer, encoding);
			str = writer.getBuffer().toString();
		} finally {
			if (fis != null) {
				fis.close();
			}
			if (writer != null) {
				writer.close();
			}
		}

		return str;
	}

	/**
	 * 从输入流中读取内容
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param in
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String readTextFile(InputStream in, String encoding)
			throws IOException {
		StringWriter writer;
		if (in == null)
			throw new NullPointerException("pathname");
		if (encoding == null)
			throw new NullPointerException("encoding");
		writer = new StringWriter();
		String str = null;
		try {
			IOUtils.copy(in, writer, encoding);
			str = writer.getBuffer().toString();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}

		return str;
	}

	/**
	 * DOM对象转换
	 * 
	 * @param document
	 *            dom对象
	 * @param stylesheet
	 *            样式表相对于class的路径
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @return
	 */
	public static Document styleDocument(Document document, String stylesheet) {

		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = null;
		InputStream in = null;
		try {
			in = XmlUtils.class.getResourceAsStream(stylesheet);
			transformer = factory.newTransformer(new StreamSource(in));
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		DocumentSource source = new DocumentSource(document);
		DocumentResult result = new DocumentResult();
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {

			}
		}
		// return the transformed document
		Document transformedDoc = result.getDocument();
		return transformedDoc;
	}

	public static Document styleDocument(Document document, File styleFile) {

		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = null;
		InputStream in = null;
		try {
			in = new FileInputStream(styleFile.getAbsolutePath());
			transformer = factory.newTransformer(new StreamSource(in));
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		DocumentSource source = new DocumentSource(document);
		DocumentResult result = new DocumentResult();
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {

			}
		}
		// return the transformed document
		Document transformedDoc = result.getDocument();
		return transformedDoc;
	}

	/**
	 * 提取节点的文本值
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param root
	 *            xpath参考的根节点
	 * 
	 * 
	 * 
	 * 
	 * @param xPath
	 * @return
	 */
	public static String getNodeText(Node root, String xPath) {
		assert (root != null);
		Node subNode = root.selectSingleNode(xPath);
		if (subNode == null)
			return "";
		return subNode.getText();
	}

	/**
	 * 写入文件
	 * 
	 * @param doc
	 */
	public static void write(Document doc, File file, String encode) {
		XMLWriter writer = null;
		try {
			// 美化格式
			OutputFormat format = OutputFormat.createPrettyPrint();

			// 需要加这句话否则XML内容中的两个空格会被缩成一个
			format.setTrimText(false);

			format.setEncoding(encode);
			writer = new XMLWriter(new FileOutputStream(file), format);
			writer.write(doc);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			colse(writer);
		}
	}

	/**
	 * 写入文件
	 * 
	 * @param doc
	 */
	public static void write(Document doc, String file, String encode) {
		// 指定文件
		XMLWriter writer = null;
		try {
			// 美化格式
			OutputFormat format = OutputFormat.createPrettyPrint();

			// 需要加这句话否则XML内容中的两个空格会被缩成一个
			format.setTrimText(false);

			format.setEncoding(encode);
			writer = new XMLWriter(new FileOutputStream(file), format);
			writer.write(doc);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			colse(writer);
		}
	}

	public static void colse(XMLWriter writer) {
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 利用schema验证xml是否有效
	 * 
	 * @param xmlFile
	 *            xml文件
	 * @param schemaLocation
	 *            schema文件
	 * @return true验证通过 false验证不通过
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	public static boolean verifyXml(File xmlFile, File schemaLocation)
			throws SAXException, ParserConfigurationException, IOException {
		SchemaFactory factory = SchemaFactory
				.newInstance("http://www.w3.org/2001/XMLSchema");
		try {
			Schema schema = factory.newSchema(schemaLocation);
			Validator validator = schema.newValidator();
			DocumentBuilderFactory domFactory = DocumentBuilderFactory
					.newInstance();
			domFactory.setNamespaceAware(true); // 感知命名空间
			// domFactory.setXIncludeAware(true); // 感知XInclude

			DocumentBuilder builder = domFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = builder.parse(xmlFile);
			DOMSource source = new DOMSource(doc);
			// 校验
			validator.validate(source);
		} catch (SAXException ex) {
			String msg = "没有验证通过，因为" + ex.getMessage();
			throw new SAXException(msg);
		} catch (IOException e) {
			String msg = "没有验证通过，因为" + e.getMessage();
			throw new IOException(msg);
		} catch (ParserConfigurationException e) {
			String msg = "没有验证通过，因为" + e.getMessage();
			throw new ParserConfigurationException(msg);
		}
		return true;
	}

	/**
	 * 利用schema验证xml是否有效
	 * 
	 * @param xmlFile
	 *            xml文件
	 * @param schemaLocation
	 *            schema文件
	 * @return true验证通过 false验证不通过
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static boolean verifyXml(InputStream xmlFile,
			InputStream schemaStream) throws SAXException, IOException,
			ParserConfigurationException {
		SchemaFactory factory = SchemaFactory
				.newInstance("http://www.w3.org/2001/XMLSchema");
		StreamSource schemaSource = new StreamSource(schemaStream);
		try {
			Schema schema = factory.newSchema(schemaSource);
			Validator validator = schema.newValidator();
			DocumentBuilderFactory domFactory = DocumentBuilderFactory
					.newInstance();
			domFactory.setNamespaceAware(true); // 感知命名空间
			// domFactory.setXIncludeAware(true); // 感知XInclude
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = builder.parse(xmlFile);
			DOMSource source = new DOMSource(doc);

			validator.validate(source);
		} catch (SAXException ex) {
			String msg = "没有验证通过，因为" + ex.getMessage();
			throw new SAXException(msg);
		} catch (IOException e) {
			String msg = "没有验证通过，因为" + e.getMessage();
			throw new IOException(msg);
		} catch (ParserConfigurationException e) {
			String msg = "没有验证通过，因为" + e.getMessage();
			throw new ParserConfigurationException(msg);
		}
		return true;
	}

	/**
	 * 利用schema验证xml是否有效
	 * 
	 * @param xmlfile
	 *            xml文件名
	 * 
	 * 
	 * 
	 * 
	 * @param schemaFile
	 *            schema文件名
	 * 
	 * 
	 * 
	 * 
	 * @return true验证通过 false验证不通过
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static boolean verifyXml(String xmlfile, String schemaFile)
			throws SAXException, IOException, ParserConfigurationException {
		File schemaLocation = new File(schemaFile);
		File xmlFile = new File(xmlfile);
		if (!verifyXml(xmlFile, schemaLocation)) {
			return false;
		}
		return true;
	}

	/**
	 * 对xml不支持的字符进行转义
	 * 
	 * @param value
	 * @return
	 */
	public static String transSpecialChar(String value) {
		String temp = value;
		temp = temp.replaceAll("<", "&lt;");
		temp = temp.replaceAll(">", "&gt;");
		temp = temp.replaceAll("&", "&amp;");
		temp = temp.replaceAll("'", "&apos;");
		temp = temp.replaceAll("\"", "&quot;");
		return temp;
	}

	/**
	 * 去掉xml不支持的字符
	 * 
	 * @param value
	 * @return
	 */
	public static String removeSpecialChar(String value) {
		String temp = value;
		temp = temp.replaceAll("<", "");
		temp = temp.replaceAll(">", "");
		temp = temp.replaceAll("&", "");
		temp = temp.replaceAll("'", "");
		temp = temp.replaceAll("\"", "");
		return temp;
	}

	/**
	 * 获取紧缩格式的xml字符串，去掉了标签间的空格，换行符等空白字符
	 * 
	 * @param rootEle
	 * @return
	 * @throws E5Exception
	 */
	public static String getUnformatXmlString(Element rootEle) {
		removeNullElement(rootEle, "span");
		// 格式化xml输出到字符串
		StringWriter strWriter = new StringWriter();
		OutputFormat format = OutputFormat.createCompactFormat();
		format.setEncoding("UTF-8");
		format.setSuppressDeclaration(true);
		XMLWriter xmlWriter = new XMLWriter(strWriter, format);
		try {
			xmlWriter.write(rootEle);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return replaceBlankInContent(strWriter.toString());
	}

	/**
	 * 获取紧缩格式的xml字符串，去掉了标签间的空格，换行符等空白字符
	 * 
	 * @param rootEle
	 * @return
	 * @throws E5Exception
	 * @throws DocumentException
	 */
	public static String getUnformatXmlString(String xmlString) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xmlString);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return XmlUtils.getUnformatXmlString(doc.getRootElement());
	}

	/**
	 * 删除节点名为eleName的空节点
	 * 
	 * @param contentElement
	 */
	@SuppressWarnings("unchecked")
	public static void removeNullElement(Element contentElement, String eleName) {
		List eleList = contentElement.selectNodes("//" + eleName
				+ "[@class!='parabegin']");
		if (eleList != null) {
			for (Object obj : eleList) {
				Element ele = (Element) obj;

				if (ele.getTextTrim().equals("")
						&& ele.elementIterator() != null
						&& !ele.elementIterator().hasNext()) {
					Element parent = ele.getParent();
					parent.remove(ele);
				}
			}
		}
	}

	public static String replaceBlankInContent(String content) {
		Pattern p = Pattern
				.compile("[a-zA-Z,\\.\\)”]([ ])[\\(“a-zA-Z][ ]*|[a-zA-Z,\\.\\)]([ ]+)<u|</u>([ ]+)[a-zA-Z,\\.]");
		Matcher m = p.matcher(content);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			if (m.group(1) != null)
				m.appendReplacement(sb, m.group().replaceAll(" ", " "));
			else if (m.group(2) != null)
				m.appendReplacement(sb, m.group().replaceAll(" ", " "));
			else if (m.group(3) != null)
				m.appendReplacement(sb, m.group().replaceAll(" ", " "));
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	/**
	 * 获取当前系统的“机构编码”
	 *  @return
	 */  
//	@Test
	public  String getActiveMQQueueDestination() {
		// 创建一个SAXReader对象
		SAXReader reader = new SAXReader();
		Document document;
		String destination="";
		try {
			document = reader.read(this.getClass().getResourceAsStream("/ActiveMQ.xml"));
			List<Element> codes = document.selectNodes("/beans/jms:listener-container/jms:listener");
			for (Element code : codes) {
				String ref = code.attributeValue("ref");//
				if("queueReceiver".equals(ref)){
					destination=code.attributeValue("destination");
					 break;
				}
				if("processQueueReceiver".equals(ref)){
					destination=code.attributeValue("destination");
					 break;
				}
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return destination;
	}
	public String getActiveMQQueueDestinationProcess()
	  {
	    SAXReader reader = new SAXReader();

	    String destination = "";
	    try {
	      org.dom4j.Document document = reader.read(getClass().getResourceAsStream("/ActiveMQ.xml"));
	  	  List<Element> codes = document.selectNodes("/beans/jms:listener-container/jms:listener");
	      for (Element code : codes) {
	        String ref = code.attributeValue("ref");
	        if ("processQueueReceiver".equals(ref)) {
	          destination = code.attributeValue("destination");
	          break;
	        }
	      }
	    }
	    catch (DocumentException e) {
	      e.printStackTrace();
	    }
	    return destination;
	  }
}

