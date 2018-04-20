package com.test.demo;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;

public class TestToXml {

	public static void main(String[] args) throws Exception {
		File file = new File("E:/21包文件/CNONIX图书产品信息数据元结构表.xls");
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
		List<XmlDemo> list = new ArrayList<XmlDemo>();
		Sheet sheet = workbook.getSheetAt(0);// 读取第一个sheet
		int a = 1;
		int rowCount = sheet.getPhysicalNumberOfRows(); // 获取总行数
		for (int r = 2; r < rowCount; r++) {// r从1开始，0表示第1行，
			XmlDemo demo = new XmlDemo();
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			int cellCount = row.getPhysicalNumberOfCells(); // 获取总列数
			// 遍历每一列
			for (int c = 0; c < cellCount; c++) {
				if (c != 1 && c != 2 && c != 3 && c != 8 && c != 9) {
					continue;
				}
				Cell cell = row.getCell(c);
				if (cell != null) {
					String cellValue = null;
					int cellType = cell.getCellType();
					switch (cellType) {
					case Cell.CELL_TYPE_STRING: // 文本
						cellValue = cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_NUMERIC: // 数字、日期
						if (DateUtil.isCellDateFormatted(cell)) {
							cellValue = fmt.format(cell.getDateCellValue()); // 日期型
						} else {
							cellValue = String.valueOf(cell
									.getNumericCellValue()); // 数字
							cellValue = getIntNum(cellValue);
						}
						break;
					case Cell.CELL_TYPE_BOOLEAN: // 布尔型
						cellValue = String.valueOf(cell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_BLANK: // 空白
						cellValue = cell.getStringCellValue();
						break;
					case Cell.CELL_TYPE_ERROR: // 错误
						cellValue = "错误";
						break;
					case Cell.CELL_TYPE_FORMULA: // 公式
						cellValue = "错误";
						break;
					default:
						cellValue = "错误";
					}
					cellValue = (cell != null ? cellValue.replace("<", "").replace(">", "").replace("/", "").trim() : "");
					if (StringUtils.isNotBlank(cellValue)) {
						if(c==1){//节点组编号
								demo.setTeamNum(cellValue);
						}else if (c == 2) {//节点属性值
							demo.setStrName(cellValue);
						}else if (c == 3) {//节点层级
							demo.setStep(Integer.parseInt(cellValue));
						}else if(c==8){//CNONIX代码表号
							demo.setCnonixTable(cellValue);
						}else if(c==9){//ONIX代码表号
							demo.setOnixList(cellValue);
						}
					}

				}

			}
			if (demo.getStep() != 0&& StringUtils.isNotBlank(demo.getStrName())) {
				demo.setiNum(a);
				list.add(demo);
				a++;
			}

		}

		toXml(list);
	}

	public static void toXml(List<XmlDemo> list) throws Exception {
		Document document = DocumentHelper.createDocument();
		Element onixMessage = document.addElement("ONIXMessage","http://ns.editeur.org/onix/3.0/reference").addAttribute("release", "3.0");
		document.setRootElement(onixMessage);
		for (XmlDemo demo : list) {
			demo.setParentPath(testDemo(list, demo.getiNum(), demo.getStep()));
		}

		for (XmlDemo demo : list) {
			if (StringUtils.isNotBlank(demo.getParentPath())) {
				Map<String, String> map = new HashMap<String, String>();
				String nsURI = document.getRootElement().getNamespaceURI();
				map.put("xmlns", nsURI);
				// 创建解析路径，就是在普通的解析路径前加上map里的key值
				XPath x = document.createXPath(demo.getParentPath());
				x.setNamespaceURIs(map);
				Element element = (Element) x.selectSingleNode(document);
				Element obj = element.addElement(demo.getStrName()).addAttribute("level", String.valueOf(demo.getStep()));// .addComment("这是自动生成的节点");
				
				if(StringUtils.isNotBlank(demo.getTeamNum())){
					obj.addAttribute("teamNum", demo.getTeamNum());
				}
				if(StringUtils.isNotBlank(demo.getCnonixTable())){
					obj.addAttribute("cnonixTable", demo.getCnonixTable());
				}
				if(StringUtils.isNotBlank(demo.getOnixList())){
					obj.addText(demo.getOnixList());
				}
			}
		}

		System.out.println("XML " + document.asXML());
	} 

	public static String testDemo(List<XmlDemo> list, int iNum, int step) {
		Map<Integer, String> map = new TreeMap<Integer, String>(
				new Comparator<Integer>() {
					public int compare(Integer obj1, Integer obj2) {
						// 降序排序
						return obj1.compareTo(obj2);
					}
				});
		for (int i = 1; i <= iNum - 1; i++) {
			if (list.get(i - 1).getStep() < step) {
				XmlDemo demo = list.get(i - 1);
				map.put(demo.getStep(), demo.getStrName());
			}
		}

		String strPath = "ONIXMessage/xmlns:";
		for (Integer key : map.keySet()) {
			strPath += map.get(key) + "/xmlns:";
		}

		strPath = strPath.substring(0, strPath.length() - 7);
		return strPath;
	}

	private static String getIntNum(String str) {
		if (StringUtils.isNotBlank(str) && str.indexOf(".") != -1) {
			return str.substring(0, str.indexOf("."));
		}
		return "0";
	}
	
	/*
	 * public static void toXml(List<XmlDemo> list) throws Exception{
	 * DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	 * DocumentBuilder builder = factory.newDocumentBuilder(); Document document
	 * = (Document) builder.newDocument(); Element onixMessage =
	 * document.createElement("ONIXMessage"); //if
	 * (StringUtils.isNotBlank(cellValue)) { // Element element =
	 * document.createElement(cellValue.trim()); //
	 * onixMessage.appendChild(element); //} for(XmlDemo demo:list){ //Element
	 * element = document.ge Element element =
	 * document.createElement(demo.getStrName());
	 * onixMessage.appendChild(element); }
	 * 
	 * document.appendChild(onixMessage); NodeList nodeList =
	 * document.getElementsByTagName("Sender"); Element sender =(Element)
	 * nodeList.item(0); sender.appendChild(document.createElement("AAA"));
	 * 
	 * NodeList nodeListA = document.getElementsByTagName("AAA"); Element
	 * senderA =(Element) nodeListA.item(0);
	 * senderA.appendChild(document.createElement("BBB"));
	 * 
	 * System.out.println("NODE "+document.getElementsByTagName("Sender"));
	 * System.out.println("CONTENT " + generateXMLString(document)); }
	 */

	/*
	 * public static String generateXMLString(Document document) throws
	 * TransformerException { TransformerFactory transformerFactory =
	 * TransformerFactory .newInstance(); Transformer transformer =
	 * transformerFactory.newTransformer(); Source xmlSource = new
	 * DOMSource(document); StringWriter writerStr = new StringWriter(); Result
	 * resultStr = new StreamResult(writerStr); transformer.transform(xmlSource,
	 * resultStr); String str = writerStr.getBuffer().toString(); return str; }
	 */
}
