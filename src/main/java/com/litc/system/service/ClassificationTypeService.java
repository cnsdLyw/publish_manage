package com.litc.system.service;

import java.util.List;

import com.litc.system.model.ClassificationType;

public interface ClassificationTypeService {

	public void addJCClassificationType(ClassificationType jcClassificationType);
	
	public ClassificationType getJCClassificationType(Long id);

	public List<ClassificationType> getAll();


}
