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
		
	
		creatTestReport("D:/��������Բ��Խ������(1).xml", "E:/cuiyc/����/softwareTemplate.docx","D:/test.docx");

	}
	public static void creatTestReport( String xmlPath,String tempWordDoucment, String outputPath) throws IOException, DocumentException, JAXBException {
		
		Report report = getReport(xmlPath);
		if(report==null) return;
		
		XWPFDocument document = openDocument(tempWordDoucment);
		creatTitle(document);

		// ���Խ��
		creatChapterTitle(document, "1 ���Խ��");
		String testDesc = "  ���ϳ������ݴ���ϵͳ��" + report.getTestResultInfo().getYear() + "��" + report.getTestResultInfo().getMonth() + "��" + report.getTestResultInfo().getDay()
				+ "��������" + report.getSoftInfo().getSoftwareName() + "�Ĳ��ԣ���������Բ��Խ����" + report.getTestResultInfo().getTestResult();
		creatRun(document, testDesc);
		
		

		// ��������
		creatChapterTitle(document, "2 ��������");
		creatSectionTitle(document, "2.1 �������");
		creatSoftwareInfoTable(document,report);
	    
		// ��������
		creatSectionTitle(document, "2.2 ��������");
		String testCaseResult = "  ����������Թ���֤��������" + report.getTestResultInfo().getTestTotalNum() + "����ͨ����֤" +  report.getTestResultInfo().getTestSuccessNum() + "����δͨ����֤"
				+ report.getTestResultInfo().getTestErrorNum()+ "��������������Բ���:" + report.getTestResultInfo().getTestResult();
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

	// �����ĵ�����
	private static void creatTitle(XWPFDocument doc) {
		XWPFParagraph para = doc.createParagraph();
		para.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run = para.createRun();
		run.setText("��������Բ��Ա���");
		run.setBold(true);
		run.setFontSize(18);
	}

	// �����ڱ���
	private static void creatChapterTitle(XWPFDocument doc, String chapterTitle) {
		XWPFParagraph para = doc.createParagraph();
		XWPFRun run = para.createRun();
		run.setText(chapterTitle);
		run.setBold(true);
		run.setFontSize(16);
	}

	// ����С�ڽڱ���
	private static void creatSectionTitle(XWPFDocument doc, String sectionTitle) {
		XWPFParagraph para = doc.createParagraph();
		XWPFRun run = para.createRun();
		run.setText(sectionTitle);
		run.setBold(true);
		run.setFontSize(14);
	}

	// ��������
	private static void creatRun(XWPFDocument doc, String content) {
		XWPFParagraph para = doc.createParagraph();
		para.setFirstLineIndent(20);
		XWPFRun run = para.createRun();
		run.setText(content);
		run.setFontSize(11);
	}

	// ��ȡ���������Ϣ
//	private static Object[] getSoftwareInfo(String content)
//			throws DocumentException {
//		List<String> info = new ArrayList();
//
//		SAXReader reader = new SAXReader();
//		Document document = reader.read(new File("C:/Users/����/Documents/Java/Xml2Word/src/eric/office/data.xml"));
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
		// �������
		/*CTTblPr tablePr = table.getCTTbl().addNewTblPr();
		// �����
		CTTblWidth width = tablePr.addNewTblW();
		width.setW(BigInteger.valueOf(8000));*/
		CTTblPr tblPr = table.getCTTbl().getTblPr();
        tblPr.getTblW().setType(STTblWidth.DXA);
        tblPr.getTblW().setW(BigInteger.valueOf(8000));
		
		table.getRow(0).getCell(0).setText("���");
		table.getRow(0).getCell(1).setText("�������");
		table.getRow(0).getCell(2).setText("��������");
		table.getRow(0).getCell(3).setText("���Խ��");
		table.getRow(0).getCell(4).setText("��ע");
		for (int i = 0; i < caseList.size(); i++)
		{
			table.getRow(i+1).getCell(0).setText(String.valueOf(i+1));
			table.getRow(i+1).getCell(1).setText(caseList.get(i).getTestCaseId());
			table.getRow(i+1).getCell(2).setText(caseList.get(i).getTestCaseType());
			table.getRow(i+1).getCell(3).setText(caseList.get(i).getTestCaseResult());
			table.getRow(i+1).getCell(4).setText(caseList.get(i).getRemark());
			
			// ����������������
			creatSectionTitle(doc, "2.2."+(i+1)+" ��������"+caseList.get(i).getTestCaseId());
			creatRun(doc, "  ���Խ����"+caseList.get(i).getTestCaseResult());
			creatRun(doc, "  ����������");
			creatRun(doc, caseList.get(i).getDownloadUrl());
			
			// ������������������
			XWPFTable caseTable = doc.createTable(9, 3);
			// �������
			/*CTTblPr tablePrcase = caseTable.getCTTbl().addNewTblPr();
			// �����
			CTTblWidth widthCase = tablePrcase.addNewTblW();
			widthCase.setW(BigInteger.valueOf(8000));*/
			
			CTTblPr tablePrcase = caseTable.getCTTbl().getTblPr();
			tablePrcase.getTblW().setType(STTblWidth.DXA);
			tablePrcase.getTblW().setW(BigInteger.valueOf(8000));
			
			caseTable.getRow(0).getCell(0).setText("");
			caseTable.getRow(0).getCell(1).setText("����Ӧ�������");
			caseTable.getRow(0).getCell(2).setText("���ʵ�ʽ������");
			caseTable.getRow(1).getCell(0).setText("������ȷ��");
			caseTable.getRow(2).getCell(0).setText("���Թ���");
			caseTable.getRow(3).getCell(0).setText("����");
			caseTable.getRow(4).getCell(0).setText("ISBN");
			caseTable.getRow(5).getCell(0).setText("���");
			caseTable.getRow(6).getCell(0).setText("����");
			caseTable.getRow(7).getCell(0).setText("����");
			caseTable.getRow(8).getCell(0).setText("������");
			
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

	// �������
	private static void creatSoftwareInfoTable(XWPFDocument doc,Report report)
			throws DocumentException {

		XWPFTable table = doc.createTable(10, 2);
		/*table.setColBandSize(4000);
		table.setCellMargins(0, 300, 0, 0);
		table.setWidth(100);*/
		// �������
		//CTTblPr tablePr = table.getCTTbl().addNewTblPr();
		// �����
		//CTTblWidth width = tablePr.addNewTblW();
		//width.setW(BigInteger.valueOf(8000));
		
		CTTblPr tblPr = table.getCTTbl().getTblPr();
        tblPr.getTblW().setType(STTblWidth.DXA);
        tblPr.getTblW().setW(new BigInteger("8000"));
 
        //���õ�Ԫ����
        CTTcPr cellPr = table.getRow(0).getCell(0).getCTTc().addNewTcPr();
        CTTblWidth cellWidth = cellPr.addNewTcW();
        cellWidth.setType(STTblWidth.DXA);
        cellWidth.setW(BigInteger.valueOf(4000));
        
        CTTcPr cellPr2 = table.getRow(0).getCell(1).getCTTc().addNewTcPr();
        CTTblWidth cellWidth2 = cellPr2.addNewTcW();
        cellWidth2.setType(STTblWidth.DXA);
        cellWidth2.setW(BigInteger.valueOf(4000));
        
		table.getRow(0).getCell(0).setText("�������");
		table.getRow(1).getCell(0).setText("�������");
		table.getRow(2).getCell(0).setText("�Ƿ�ԭ��");
		table.getRow(3).getCell(0).setText("������ʽ");
		table.getRow(4).getCell(0).setText("�Ƿ񷢱�");
		table.getRow(5).getCell(0).setText("�����ģ");
		table.getRow(6).getCell(0).setText("Ӳ������");
		table.getRow(7).getCell(0).setText("�������");
		table.getRow(8).getCell(0).setText("��������");
		table.getRow(9).getCell(0).setText("�������");

		// �������
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
