package com.test.demo;

import java.io.File;

import org.apache.commons.lang.StringUtils;

import com.litc.common.util.UUID;


public class TestString {
	public static void main(String[] args) {
		//System.out.println(StringUtils.isNumeric("12.5"));
		//System.out.println("asdss".length());
		//System.out.println("山东省".length());
		
		/*String str= "^[0-9a-zA-Z]{0,20}$";
		System.out.println("123".matches(str));
		System.out.println("ewe".matches(str));
		System.out.println("DSD12".matches(str));
		System.out.println("DDSD".matches(str));
		System.out.println("12123sdsd".matches(str));*/
		/*File file = new File("D:\\program\\apache-tomcat-7.0.56-windows-x64\\apache-tomcat-7.0.56\\webapps\\web21\\attachement\\complianceTestReport\\20160909\\201609081541-3320513490.pdf");
		System.out.println(file.getName());*/
		
		//System.out.println("Living Planet: Understanding and Preserving Biodiversity".length());
		//Integer str = null;
		//System.out.println((str>7));
		//数字引用类型是null的话，比较大小时会报错。
		
		/*String strF13 = "^\\d{3}-\\d-\\d{3}-\\d{5}-\\d$";//ISBN-13:31351
		String strF10 = "^\\d-\\d{3}-\\d{5}-\\d$";//ISBN-10:1351
		String str = "978-7-550-01855-6";
		String str10 = "7-550-01855-6";
		String str1 = "9787550015516";
		String str2 = "7550018556";
		String strNum = "^\\d{10}||\\d{13}$";
		
		System.out.println(str.matches(strF13));
		System.out.println(str10.matches(strF10));
		
		System.out.println(str1.matches(strNum));
		System.out.println(str2.matches(strNum));*/
		// ISBN正则表达式
		/*String strString ="";
		if(StringUtils.isNotBlank(str1)&&str1.matches(strNum)){
			char[] c = str1.toCharArray();
			System.out.println("-- "+c[0]);
			if(str1.length()==10){
				strString = strString+c[0]+"-"+c[1]+c[2]+c[3]+"-"+c[4]+c[5]+c[6]+c[7]+c[8]+"-"+c[9];
			}else if(str1.length()==13){
				strString = strString+c[0]+c[1]+c[2]+"-"+ c[3]+"-"+c[4]+c[5]+c[6]+"-"+c[7]+c[8]+c[9]+c[10]+c[11]+"-"+c[12];
			}
		}
		
		System.out.println("strString  "+strString);
		
		byte a = 123;
		Integer i = (int) a;*/
		//System.out.println(String.valueOf(null));
		
		/*String str = "0cd29996-1e61-484b-a4e6-39da85c63ea3";
		System.out.println(str.length());*/
		
		System.out.println(java.util.UUID.randomUUID().toString());
	}
	
	
}
