package com.litc.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.dom4j.DocumentException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

import com.litc.model.data.complianceTestReport.Report;
import com.litc.model.data.complianceTestReport.TestCaseInfo;

public class CreateComplianceTestReport {

	public static void main(String[] args) throws IOException, DocumentException, JAXBException {
		// TODO Auto-generated method stub
		System.out.println();
		
	
		creatTestReport("D:/软件符合性测试结果详情(1).xml", "E:/cuiyc/开发/softwareTemplate.docx","D:/test.docx");

	}
	public static void creatTestReport( String xmlPath,String tempWordDoucment, String outputPath) throws IOException, DocumentException, JAXBException {
		
		Report report = getReport(xmlPath);
		if(report==null) return;
		
		XWPFDocument document = openDocument(tempWordDoucment);
		creatTitle(document);

		// 测试结果
		creatChapterTitle(document, "1 测试结果");
		String testDesc = "  复合出版数据传递系统于" + report.getTestResultInfo().getYear() + "年" + report.getTestResultInfo().getMonth() + "月" + report.getTestResultInfo().getDay()
				+ "日完成软件" + report.getSoftInfo().getSoftwareName() + "的测试，软件符合性测试结果：" + report.getTestResultInfo().getTestResult();
		creatRun(document, testDesc);
		
		

		// 测试内容
		creatChapterTitle(document, "2 测试内容");
		creatSectionTitle(document, "2.1 软件详情");
		creatSoftwareInfoTable(document,report);
	    
		// 测试详情
		creatSectionTitle(document, "2.2 测试详情");
		String testCaseResult = "  本轮软件测试共验证测试用例" + report.getTestResultInfo().getTestTotalNum() + "个，通过验证" +  report.getTestResultInfo().getTestSuccessNum() + "个，未通过验证"
				+ report.getTestResultInfo().getTestErrorNum()+ "，本次软件符合性测试:" + report.getTestResultInfo().getTestResult();
		creatRun(document, testCaseResult);
		creatCaseListTable(document,report);
		
		
		
		
		FileOutputStream out;

		try {
			out = new FileOutputStream(outputPath);
			document.write(out);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			
		}
		
	}

	public static XWPFDocument openDocument(String filePath) {
		XWPFDocument xdoc = null;
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);
			xdoc = new XWPFDocument(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return xdoc;
	}

	// 创建文档标题
	private static void creatTitle(XWPFDocument doc) {
		XWPFParagraph para = doc.createParagraph();
		para.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run = para.createRun();
		run.setText("软件符合性测试报告");
		run.setBold(true);
		run.setFontSize(18);
	}

	// 创建节标题
	private static void creatChapterTitle(XWPFDocument doc, String chapterTitle) {
		XWPFParagraph para = doc.createParagraph();
		XWPFRun run = para.createRun();
		run.setText(chapterTitle);
		run.setBold(true);
		run.setFontSize(16);
	}

	// 创建小节节标题
	private static void creatSectionTitle(XWPFDocument doc, String sectionTitle) {
		XWPFParagraph para = doc.createParagraph();
		XWPFRun run = para.createRun();
		run.setText(sectionTitle);
		run.setBold(true);
		run.setFontSize(14);
	}

	// 创建内容
	private static void creatRun(XWPFDocument doc, String content) {
		XWPFParagraph para = doc.createParagraph();
		para.setFirstLineIndent(20);
		XWPFRun run = para.createRun();
		run.setText(content);
		run.setFontSize(11);
	}

	// 获取软件基本信息
//	private static Object[] getSoftwareInfo(String content)
//			throws DocumentException {
//		List<String> info = new ArrayList();
//
//		SAXReader reader = new SAXReader();
//		Document document = reader.read(new File("C:/Users/杨勇/Documents/Java/Xml2Word/src/eric/office/data.xml"));
//		// org.dom4j.Document document = DocumentHelper.parseText(content);
//		Element root = document.getRootElement();
//		org.dom4j.Node node = root.selectSingleNode("softInfo/softwareName");
//		if(node!=null)
//		{
//			info.add(node.getText());
//		}
//		else
//		{
//			info.add("");
//		}
//		
//		node = root.selectSingleNode("softInfo/softwareType");
//		if(node!=null)
//		{
//			info.add(node.getText());
//		}
//		else
//		{
//			info.add("");
//		}
//		
//		node = root.selectSingleNode("softInfo/isOriginal");
//		if(node!=null)
//		{
//			info.add(node.getText());
//		}
//		else
//		{
//			info.add("");
//		}
//		
//		node = root.selectSingleNode("softInfo/developmentModel");
//		if(node!=null)
//		{
//			info.add(node.getText());
//		}
//		else
//		{
//			info.add("");
//		}
//		
//		node = root.selectSingleNode("softInfo/isPublish");
//		if(node!=null)
//		{
//			info.add(node.getText());
//		}
//		else
//		{
//			info.add("");
//		}
//		
//		node = root.selectSingleNode("softInfo/softwareSize");
//		if(node!=null)
//		{
//			info.add(node.getText());
//		}
//		else
//		{
//			info.add("");
//		}
//		
//		node = root.selectSingleNode("softInfo/hardwareEnv");
//		if(node!=null)
//		{
//			info.add(node.getText());
//		}
//		else
//		{
//			info.add("");
//		}
//		
//		node = root.selectSingleNode("softInfo/softwareEnv");
//		if(node!=null)
//		{
//			info.add(node.getText());
//		}
//		else
//		{
//			info.add("");
//		}
//		
//		node = root.selectSingleNode("softInfo/language");
//		if(node!=null)
//		{
//			info.add(node.getText());
//		}
//		else
//		{
//			info.add("");
//		}
//		
//		node = root.selectSingleNode("softInfo/desc");
//		if(node!=null)
//		{
//			info.add(node.getText());
//		}
//		else
//		{
//			info.add("");
//		}
//		
//		return info.toArray();
//
//	}
	
	private static Report getReport(String reportXMLPath) throws JAXBException
	{
		  JAXBContext context = JAXBContext.newInstance(Report.class);  
		  Unmarshaller unmarshaller = context.createUnmarshaller(); 
		  Object object = unmarshaller.unmarshal(new File(reportXMLPath));
		  Report reportObj= (Report)object;
		 return reportObj;
	}
	
	private static void creatCaseListTable(XWPFDocument doc,Report reportDocument)
	{
		
		List<TestCaseInfo> caseList = reportDocument.getTestCases().getTestCaseInfo();
		if(caseList.size()<0) return;
		XWPFTable table = doc.createTable(caseList.size()+1, 5);
		// 表格属性
		/*CTTblPr tablePr = table.getCTTbl().addNewTblPr();
		// 表格宽度
		CTTblWidth width = tablePr.addNewTblW();
		width.setW(BigInteger.valueOf(8000));*/
		CTTblPr tblPr = table.getCTTbl().getTblPr();
        tblPr.getTblW().setType(STTblWidth.DXA);
        tblPr.getTblW().setW(BigInteger.valueOf(8000));
		
		table.getRow(0).getCell(0).setText("编号");
		table.getRow(0).getCell(1).setText("用例编号");
		table.getRow(0).getCell(2).setText("用例类型");
		table.getRow(0).getCell(3).setText("测试结果");
		table.getRow(0).getCell(4).setText("备注");
		for (int i = 0; i < caseList.size(); i++)
		{
			table.getRow(i+1).getCell(0).setText(String.valueOf(i+1));
			table.getRow(i+1).getCell(1).setText(caseList.get(i).getTestCaseId());
			table.getRow(i+1).getCell(2).setText(caseList.get(i).getTestCaseType());
			table.getRow(i+1).getCell(3).setText(caseList.get(i).getTestCaseResult());
			table.getRow(i+1).getCell(4).setText(caseList.get(i).getRemark());
			
			// 创建测试用例详情
			creatSectionTitle(doc, "2.2."+(i+1)+" 测试用例"+caseList.get(i).getTestCaseId());
			creatRun(doc, "  测试结果："+caseList.get(i).getTestCaseResult());
			creatRun(doc, "  下载用例：");
			creatRun(doc, caseList.get(i).getDownloadUrl());
			
			// 创建测试用例详情表格
			XWPFTable caseTable = doc.createTable(9, 3);
			// 表格属性
			/*CTTblPr tablePrcase = caseTable.getCTTbl().addNewTblPr();
			// 表格宽度
			CTTblWidth widthCase = tablePrcase.addNewTblW();
			widthCase.setW(BigInteger.valueOf(8000));*/
			
			CTTblPr tablePrcase = caseTable.getCTTbl().getTblPr();
			tablePrcase.getTblW().setType(STTblWidth.DXA);
			tablePrcase.getTblW().setW(BigInteger.valueOf(8000));
			
			caseTable.getRow(0).getCell(0).setText("");
			caseTable.getRow(0).getCell(1).setText("用例应解析结果");
			caseTable.getRow(0).getCell(2).setText("软件实际解析结果");
			caseTable.getRow(1).getCell(0).setText("用例正确性");
			caseTable.getRow(2).getCell(0).setText("测试功能");
			caseTable.getRow(3).getCell(0).setText("书名");
			caseTable.getRow(4).getCell(0).setText("ISBN");
			caseTable.getRow(5).getCell(0).setText("版次");
			caseTable.getRow(6).getCell(0).setText("定价");
			caseTable.getRow(7).getCell(0).setText("作者");
			caseTable.getRow(8).getCell(0).setText("出版者");
			
			caseTable.getRow(1).getCell(1).setText(caseList.get(i).getShouldResult().getTestCaseValidity());
			caseTable.getRow(2).getCell(1).setText(caseList.get(i).getShouldResult().getTestFunction());
			caseTable.getRow(3).getCell(1).setText(caseList.get(i).getShouldResult().getTitle());
			caseTable.getRow(4).getCell(1).setText(caseList.get(i).getShouldResult().getISBN());
			caseTable.getRow(5).getCell(1).setText(caseList.get(i).getShouldResult().getEditionNumber());
			caseTable.getRow(6).getCell(1).setText(caseList.get(i).getShouldResult().getPrice());
			caseTable.getRow(7).getCell(1).setText(caseList.get(i).getShouldResult().getAuthor());
			caseTable.getRow(8).getCell(1).setText(caseList.get(i).getShouldResult().getPublisher());
			
			caseTable.getRow(1).getCell(2).setText(caseList.get(i).getActualResult().getTestCaseValidity());
			caseTable.getRow(2).getCell(2).setText(caseList.get(i).getActualResult().getTestFunction());
			caseTable.getRow(3).getCell(2).setText(caseList.get(i).getActualResult().getTitle());
			caseTable.getRow(4).getCell(2).setText(caseList.get(i).getActualResult().getISBN());
			caseTable.getRow(5).getCell(2).setText(caseList.get(i).getActualResult().getEditionNumber());
			caseTable.getRow(6).getCell(2).setText(caseList.get(i).getActualResult().getPrice());
			caseTable.getRow(7).getCell(2).setText(caseList.get(i).getActualResult().getAuthor());
			caseTable.getRow(8).getCell(2).setText(caseList.get(i).getActualResult().getPublisher());
		
		}
	}

	// 创建表格
	private static void creatSoftwareInfoTable(XWPFDocument doc,Report report)
			throws DocumentException {

		XWPFTable table = doc.createTable(10, 2);
		/*table.setColBandSize(4000);
		table.setCellMargins(0, 300, 0, 0);
		table.setWidth(100);*/
		// 表格属性
		//CTTblPr tablePr = table.getCTTbl().addNewTblPr();
		// 表格宽度
		//CTTblWidth width = tablePr.addNewTblW();
		//width.setW(BigInteger.valueOf(8000));
		
		CTTblPr tblPr = table.getCTTbl().getTblPr();
        tblPr.getTblW().setType(STTblWidth.DXA);
        tblPr.getTblW().setW(new BigInteger("8000"));
 
        //设置单元格宽度
        CTTcPr cellPr = table.getRow(0).getCell(0).getCTTc().addNewTcPr();
        CTTblWidth cellWidth = cellPr.addNewTcW();
        cellWidth.setType(STTblWidth.DXA);
        cellWidth.setW(BigInteger.valueOf(4000));
        
        CTTcPr cellPr2 = table.getRow(0).getCell(1).getCTTc().addNewTcPr();
        CTTblWidth cellWidth2 = cellPr2.addNewTcW();
        cellWidth2.setType(STTblWidth.DXA);
        cellWidth2.setW(BigInteger.valueOf(4000));
        
		table.getRow(0).getCell(0).setText("软件名称");
		table.getRow(1).getCell(0).setText("软件类型");
		table.getRow(2).getCell(0).setText("是否原创");
		table.getRow(3).getCell(0).setText("开发方式");
		table.getRow(4).getCell(0).setText("是否发表");
		table.getRow(5).getCell(0).setText("软件规模");
		table.getRow(6).getCell(0).setText("硬件环境");
		table.getRow(7).getCell(0).setText("软件环境");
		table.getRow(8).getCell(0).setText("开发语言");
		table.getRow(9).getCell(0).setText("软件描述");

		// 填充内容
		//Object[] info = getSoftwareInfo("");
		table.getRow(0).getCell(1).setText(report.getSoftInfo().getSoftwareName());
		table.getRow(1).getCell(1).setText(report.getSoftInfo().getSoftwareType());
		table.getRow(2).getCell(1).setText(report.getSoftInfo().getIsOriginal());
		table.getRow(3).getCell(1).setText(report.getSoftInfo().getDevelopmentModel());
		table.getRow(4).getCell(1).setText(report.getSoftInfo().getIsPublish());
		table.getRow(5).getCell(1).setText(report.getSoftInfo().getSoftwareSize());
		table.getRow(6).getCell(1).setText(report.getSoftInfo().getHardwareEnv());
		table.getRow(7).getCell(1).setText(report.getSoftInfo().getSoftwareEnv());
		table.getRow(8).getCell(1).setText(report.getSoftInfo().getLanguage());
		table.getRow(9).getCell(1).setText(report.getSoftInfo().getDesc());

	}


}
