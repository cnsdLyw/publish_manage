package com.litc.fileSystem.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.litc.fileSystem.model.JetsenFiles;
import com.litc.security.repository.IComponent;

public interface JetsenFilesRepository extends JpaRepository<JetsenFiles, String>, IComponent {
	@Query("from com.litc.fileSystem.model.JetsenFiles where storagePath=?1")
	public JetsenFiles findByFilePath(String filePath);
	
	@Query("from com.litc.fileSystem.model.JetsenFiles where uuid=?1")
	public JetsenFiles findByUuid(String uuid);
	
	@Query("from com.litc.fileSystem.model.JetsenFiles where storagePath like %?1%")
	public List<JetsenFiles> findByStoragePath(String storagePath);
	
	/**
	 * 分页查询
	 * @param spec
	 * @param pageable
	 * @return
	 */
	Page<JetsenFiles> findAll(Specification<JetsenFiles> spec, Pageable pageable );
}
