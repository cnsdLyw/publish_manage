package com.litc.service;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.litc.model.cms.Faq;

/**
 * Function:留言接口
 * 
 * @author zhongying(281264212@qq.com)
 * @date 2015-11-19 上午9:57:44
 * @version 1.0
 */
public interface FaqService {

	/**
	 * 获取书目列表
	 */
	public Page<Faq> getFaqs(Map map, String queryOrderBy, String queryOrdertype, Integer pageNo, Integer pageSize);


	/**
	 * 更新书目
	 */
	public Faq updateFaq(Faq faq);

	/**
	 * 获取一本书目
	 */
	public Faq getFaq(Long id);

	/**
	 * 删除一本书目
	 */
	 public void deleteFaq(Long id);
}