package com.litc.model.book;
/**
<?xml version="1.0" encoding="UTF-8"?>
<list>
	<code id="01">
		<name>专用</name>
		<explane>交换双方约定的代码方案</explane>
	</code>
	<code id="02">
		<name>ISBN-10</name>
		<explane>2007年以前使用的10位国际标准书号</explane>
	</code>
</list>
*/

public class Handle {
	private String id;
	private String name;
	private String explane;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExplane() {
		return explane;
	}
	public void setExplane(String explane) {
		this.explane = explane;
	}
	
}
