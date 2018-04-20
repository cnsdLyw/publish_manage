package com.litc.system.service;

import java.util.List;

import com.litc.system.model.Classification;

public interface ClassificationService {
	
	public Classification getClassification(String classCode);
	
	public int deleteClassification(String classCode);

	public void addClassification(Classification Classification);
	
	public Classification getClassification(String classKey,String classCode);

	public List<Classification> getClassByKey(String classKey);

	public List<Classification> getClassByKeyAndLevel(String classKey,Short classLevel);
	
	public List<Classification> getClassByKeyAndCode(String classKey,String parentCode);
	
	public String getOpenListStr(String classCode);
	
	public boolean isClassCodeUsed(String classCode);

}
