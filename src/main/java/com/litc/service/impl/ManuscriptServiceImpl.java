package com.litc.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.litc.common.jpa.Finder;
import com.litc.common.jpa.JpaSimpleDao;
import com.litc.model.cms.Manuscript;
import com.litc.repository.ManuscriptRepository;
import com.litc.service.ManuscriptService;

@Service("jcManuscriptService")
public class ManuscriptServiceImpl implements ManuscriptService {
	
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ManuscriptRepository manuscriptRepository;

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Page<Manuscript> getManuscripts(Map map, String queryOrderBy, String queryOrdertype, Integer pageNo, Integer pageSize) {
		Finder f = Finder.create("select  bean from Manuscript bean ");
		f.append(" where 1=1");
		if ((map != null) && (!map.isEmpty())) {
			Set s = map.entrySet();
			Iterator it = s.iterator();
			Object key = null;
			Object value = null;
			while (it.hasNext()) {
				Map.Entry m = (Map.Entry) it.next();
				key = m.getKey();
				value = m.getValue();
				 if (key.equals("query_all_like") && value != null) {
				 f.append(" and (bean.title like :title or bean.author like :author)");
				 f.setParam("title", "%" + value + "%");
				 f.setParam("author", "%" + value + "%");
				 }

				// 等号处理
				// if (key.equals("title") && value != null) {
				// f.append(" and bean.title like :title");
				// f.setParam("title", "%" + value + "%");
				// }
				 
				 if (key.equals("nodeID") && value != null) {
					 f.append(" and bean.nodeID=:nodeID");
					 f.setParam("nodeID", value);
				 }
				// if (key.equals("bookid") && value != null) {
				// f.append(" and bean.author=:author");
				// f.setParam("author", value);
				// }

				// if (key.equals("bookids") && value != null) {
				// f.append(" and bean.id in (");
				// f.append(value + "");
				// f.append(")");
				// }

			}
		}

		f.append(" order by ").append(queryOrderBy).append(" ").append(queryOrdertype).append(" ");
		return JpaSimpleDao.find(em, f, pageNo, pageSize);
	}

	@Override
	public Manuscript getManuscript(Long id) {
		return manuscriptRepository.findOne(id);
	}

	 @Override
	 @Transactional
	 public void deleteManuscript(Long id) {
	 manuscriptRepository.delete(id);
	 }

	@Override
	@Transactional
	public Manuscript updateManuscript(Manuscript faq) {
		Manuscript entity = manuscriptRepository.save(faq);
		return entity;
	}
	
	@Override
	@Transactional
	public void publishState(Integer publishstate,Date pubTime,Long[] ids){
		if(pubTime==null && ("").equals(pubTime)){
			manuscriptRepository.unpublishState(publishstate,ids);
		}else{
			manuscriptRepository.publishState(publishstate,pubTime,ids);
		}
		
	}
	@Override
	@Transactional
	public void publishSingleState(Integer publishstate,Date pubTime,Long ids){
		manuscriptRepository.publishSingleState(publishstate,pubTime,ids);
		
	}
	 
	public List<Manuscript> getManuscriptList(Long[] ids){
		return manuscriptRepository.getManuscriptList(ids);
	}
	
	@Override
	public boolean isContentNameExist(String authorityName,Long nodeId){
		int i = manuscriptRepository.isContentNameExist(authorityName,nodeId);
		return i>0?true:false;
	}
	@Override
	public boolean isContentNameExist(Long id, String authorityName,Long nodeId){
		int i = 0;
		if(id!=null){
			i = manuscriptRepository.isContentNameExist(id,authorityName,nodeId);
		}else{
			i = manuscriptRepository.isContentNameExist(authorityName,nodeId);
		}
		return i>0?true:false;
	}

	@Override
	public String getUploadTime() {
		//Date startTime = parse("2014-08-19 16:33:00");  
		return "2016-07-21 16:02:00";
		
	}
	private static Date parse(String dateStr) {  
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        try {  
            return format.parse(dateStr);  
        } catch (ParseException e) {  
            throw new RuntimeException(e);  
        }  
    }

	@Override
	public Manuscript getManuScriptByTask(Long taskId) {
		return manuscriptRepository.getManuScriptByTask(taskId);
	}

	@Override
	public List<Manuscript> getManuscriptList(Long id) {
		return manuscriptRepository.getManuscriptListById(id);
	}  
}
