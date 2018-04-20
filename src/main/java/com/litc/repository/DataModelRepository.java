package com.litc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.litc.model.DataModel;

/**
 * 图书比较记录表(数据访问层)
 * @author liyw
 *
 */
public interface DataModelRepository extends JpaRepository<DataModel, Long>{
	
	String getTableSql = "show full fields from ?1";
	@Query(value=getTableSql+"12",nativeQuery=true)
	List<Object[]> test(String tableName);
	
	String getGetTableSql = "select database()";
	@Query(value=getGetTableSql,nativeQuery=true)
	String getDataBase();
	
	String getTablesSql = "show tables";
	@Query(value=getTablesSql,nativeQuery=true)
	List<String> getTables();
	
	@Query("from com.litc.model.DataModel where tableName=?1") 
	public List<DataModel> getBookColumns(String tableName);

}