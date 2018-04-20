package com.litc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 数据表字段模型
 * 
 * @author liyw
 */
@Entity
@Table(name = "jc_data_model_column")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DataModel extends BaseId {
	private String tableName;// 表名
	private String columnName;// 列名
	private String columnNameType;// 数据类型
	private String cnCode;// 字符串类型编码
	private String isNull;// 是否允许为空
	private String columnKey;// 主外键
	private String defaultValue;// 默认值
	private String columnExtra;// 附加信息列 ，如自动递增列
	private String columnPrivileges;// 表权限
	private boolean isAutoIncrease;// 是否自动递增
	private String comment;// 注释
	private Date time;// 添加时间

	@Column(name = "table_name",length=50)
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name = "column_name",length=50)
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String colmunName) {
		this.columnName = colmunName;
	}

	@Column(name = "column_name_type",length=50)
	public String getColumnNameType() {
		return columnNameType;
	}

	public void setColumnNameType(String columnNameType) {
		this.columnNameType = columnNameType;
	}
	
	@Column(name = "cn_code",length=50)
	public String getCnCode() {
		return cnCode;
	}

	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}
	
	@Column(name = "is_null",length=50)
	public String getIsNull() {
		return isNull;
	}

	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}

	@Column(name = "column_key",length=50)
	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	@Column(name = "default_value",length=50)
	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Column(name = "column_extra")
	public String getColumnExtra() {
		return columnExtra;
	}

	public void setColumnExtra(String columnExtra) {
		this.columnExtra = columnExtra;
	}

	@Column(name = "column_privileges",length=50)
	public String getColumnPrivileges() {
		return columnPrivileges;
	}

	public void setColumnPrivileges(String columnPrivileges) {
		this.columnPrivileges = columnPrivileges;
	}

	@Column(name = "is_auto_increase",length=50)
	public boolean isAutoIncrease() {
		return isAutoIncrease;
	}

	public void setAutoIncrease(boolean isAutoIncrease) {
		this.isAutoIncrease = isAutoIncrease;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
