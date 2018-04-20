package com.litc.service.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.litc.common.jpa.Finder;
import com.litc.common.jpa.JpaSimpleDao;
import com.litc.model.cms.Faq;
import com.litc.repository.FaqRepository;
import com.litc.service.FaqService;

@Service("faqService")
public class FaqServiceImpl implements FaqService {
	private static final Logger log = LoggerFactory.getLogger(FaqServiceImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private FaqRepository faqRepository;

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Page<Faq> getFaqs(Map map, String queryOrderBy, String queryOrdertype, Integer pageNo, Integer pageSize) {
		Finder f = Finder.create("select  bean from Faq bean ");
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
				 f.append(" and (bean.anonymous like :anonymous or bean.email like :email or bean.phone like :phone)");
				 f.setParam("anonymous", "%" + value + "%");
				 f.setParam("email", "%" + value + "%");
				 f.setParam("phone", "%" + value + "%");
				 }

				// 等号处理
				// if (key.equals("title") && value != null) {
				// f.append(" and bean.title like :title");
				// f.setParam("title", "%" + value + "%");
				// }
				// if (key.equals("isbn") && value != null) {
				// f.append(" and bean.isbn=:isbn");
				// f.setParam("isbn", value);
				// }
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
	public Faq getFaq(Long id) {
		return faqRepository.findOne(id);
	}

	 @Override
	 @Transactional
	 public void deleteFaq(Long id) {
	 faqRepository.delete(id);
	 }

	@Override
	@Transactional
	public Faq updateFaq(Faq faq) {
		Faq entity = faqRepository.save(faq);
		return entity;
	}

}