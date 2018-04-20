package com.litc.system.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.litc.system.model.Classification;
import com.litc.system.repository.ClassificationRepository;
import com.litc.system.service.ClassificationService;

@Service("classificationService")
public class ClassificationServiceImpl implements ClassificationService {

	@Autowired
	private ClassificationRepository classificationRepository;

	@Override
	public List<Classification> getClassByKey(String classKey) {
		return classificationRepository.getClassByKey(classKey);
	}

	@Override
	public void addClassification(Classification Classification) {
		classificationRepository.save(Classification);
	}

	@Override
	public List<Classification> getClassByKeyAndCode(String classKey,String parentCode) {
		return classificationRepository.getClassByKeyAndParentcode(classKey, parentCode);
	}
	
	@Override
	public List<Classification> getClassByKeyAndLevel(String classKey,Short classLevel) {
		return classificationRepository.getClassByKeyAndLevel(classKey, classLevel);
	}

	@Override
	public Classification getClassification(String classKey,
			String classCode) {
		List<Classification>  list = classificationRepository.getClassByKeyAndClassCode(classKey, classCode);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Classification getClassification(String classCode) {
		return classificationRepository.getOne(classCode);
	}

	@Override
	public int deleteClassification(String classCode) {
		classificationRepository.delete(classCode);
		return 0;
	}

	@Override
	public String getOpenListStr(String classCode) {
		StringBuilder sBuilder = new StringBuilder();
		Classification Classification = classificationRepository.getOne(classCode);
		dealOpenList(sBuilder, Classification);
		return sBuilder.toString();
	}
	
	private void dealOpenList(StringBuilder sBuilder, Classification Classification){
		sBuilder.append("["+Classification.getClassCode()+"]");
		if(StringUtils.isNotBlank(Classification.getParentCode())){
			Classification ClassificationParent = classificationRepository.getOne(Classification.getParentCode());
			dealOpenList(sBuilder, ClassificationParent);
		}
	}

	@Override
	public boolean isClassCodeUsed(String classCode) {
		int i = classificationRepository.isClassCodeExist(classCode);
		return i>0?true:false;
	}


}
