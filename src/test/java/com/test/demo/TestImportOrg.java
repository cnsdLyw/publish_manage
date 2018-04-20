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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;

public class TestImportOrg {

	public static void main(String[] args) throws Exception {
		File file = new File("E:/21包文件/机构代码表/出版机构代码表.xls");
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
		List<XmlDemo> list = new ArrayList<XmlDemo>();
		Sheet sheet = workbook.getSheetAt(0);// 读取第一个sheet
		int a = 1;
		int rowCount = sheet.getPhysicalNumberOfRows(); // 获取总行数
		for (int r = 1; r < rowCount; r++) {// r从1开始，0表示第1行，
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			int cellCount = row.getPhysicalNumberOfCells(); // 获取总列数
			// 遍历每一列
			for (int c = 0; c < cellCount; c++) {
				Cell cell = row.getCell(c);
				if (cell != null) {
					String cellValue = null;
					int cellType = cell.getCellType();
					switch (cellType) {
					case Cell.CELL_TYPE_STRING: // 文本
						cellValue = cell.getStringCellValue();
						break;
					default:
						cellValue = "错误";
					}
					cellValue = (cell != null ? cellValue.replace("<", "").replace(">", "").replace("/", "").trim() : "");
					
					System.out.print("  "+cellValue);
				}
			}
			
			System.out.println();
		}

		//toXml(list);
	}



	private static String getIntNum(String str) {
		if (StringUtils.isNotBlank(str) && str.indexOf(".") != -1) {
			return str.substring(0, str.indexOf("."));
		}
		return "0";
	}
}
