package com.litc.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.litc.system.model.ClassificationType;
import com.litc.system.repository.ClassificationTypeRepository;
import com.litc.system.service.ClassificationTypeService;

@Service("jcClassificationTypeService")
public class ClassificationTypeServiceImpl implements
		ClassificationTypeService {
	
	@Autowired
	private ClassificationTypeRepository jcClassificationTypeRepository;

	@Override
	public void addJCClassificationType(ClassificationType jcClassificationType) {
		jcClassificationTypeRepository.save(jcClassificationType);
	}

	@Override
	public ClassificationType getJCClassificationType(Long id) {
		return jcClassificationTypeRepository.getOne(id);
	}

	@Override
	public List<ClassificationType> getAll() {
		return jcClassificationTypeRepository.findAll();
	}

}
