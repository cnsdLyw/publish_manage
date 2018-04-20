package com.test.demo;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class TestNumber {
	public static void main(String[] args) {
		String str = "";
		System.out.println("  "+(NumberUtils.isNumber(str))+"  "+(StringUtils.isNumeric(str)));
		str = "123";
		System.out.println("  "+(NumberUtils.isNumber(str))+"  "+(StringUtils.isNumeric(str)));
		str = "123.0";
		System.out.println("  "+(NumberUtils.isNumber(str))+"  "+(StringUtils.isNumeric(str)));
		str = "0.154";
		System.out.println("  "+(NumberUtils.isNumber(str))+"  "+(StringUtils.isNumeric(str)));
		str = "1a";
		System.out.println("  "+(NumberUtils.isNumber(str))+"  "+(StringUtils.isNumeric(str)));
		str = "233.df";
		System.out.println("  "+(NumberUtils.isNumber(str))+"  "+(StringUtils.isNumeric(str)));
		str = "ff";
		System.out.println("  "+(NumberUtils.isNumber(str))+"  "+(StringUtils.isNumeric(str)));
	}
}
