package com.litc.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.litc.common.jpa.Finder;
import com.litc.common.jpa.JpaSimpleDao;
import com.litc.model.DataModel;
import com.litc.repository.DataModelRepository;
import com.litc.service.DataModelService;

@Service("dataModelService")
public class DataModelServiceImpl implements DataModelService {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private DataModelRepository dataModelRepository;
	
	@Override
	public List<DataModel> getTableColumns(String tableName) {
		return dataModelRepository.getBookColumns(tableName);
	}

	@Override
	public void getTableData() {
		// 清空表
		dataModelRepository.deleteAll();
		//1 获取数据库名字
		//String dataBase = dataModelRepository.getDataBase();
		//2 获取数据库表名
		List<String> tables = dataModelRepository.getTables();
		//3 循环表，将查询出的标机构插入到DataModel表中
		for(String tableName:tables){
			System.out.println("表名：  "+tableName);

			Finder f = Finder.create("show full fields from "+tableName);
			List list = JpaSimpleDao.findBySql(em, f);
			if(list!=null&&list.size()>0){
				Object objp[] = list.toArray();
				for(Object str:objp){
					Object[] object = (Object[])str;
					System.out.println("    "+object[0]+"  "+object[1]+"  "+object[2]+"  "+object[3]+"  "+object[4]+"  "+object[5]+"  "+object[6]+"  "+object[7]+"  "+object[8]);
					DataModel model = new DataModel();
					model.setTableName(tableName);
					model.setColumnName((String)object[0]);
					model.setColumnNameType((String)object[1]);
					model.setCnCode((String)object[2]);
					model.setIsNull((String)object[3]);
					model.setColumnKey((String)object[4]);
					model.setDefaultValue((String)object[5]);
					model.setColumnExtra((String)object[6]);
					model.setColumnPrivileges((String)object[7]);
					model.setComment((String)object[8]);
					if(StringUtils.isNotBlank((String)object[6])&&("auto_increment".equals((String)object[6]))){
						model.setAutoIncrease(true);
					}else{
						model.setAutoIncrease(false);
					}
					model.setTime(new Date());
					dataModelRepository.save(model);
				}
				
			}
		}
		
	}

}
