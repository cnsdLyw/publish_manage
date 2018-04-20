package com.litc.service;

import org.springframework.data.domain.Page;

import com.litc.model.CommonQuestion;

/**
 * Function:常见问题
 *  @author  cuiyc
 *  @date    2017-7-9 上午11:15:18    
 *  @version 1.0
 */
public interface CommonQuestionService {
	/**
	 * 保存问题
	 * @param comment
	 * @return
	 */
	public CommonQuestion saveCommonQuestion(CommonQuestion comment);
	/**
	 * 分页查询问题列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<CommonQuestion> getCommentByPages(int pageNo,int pageSize,String keyWord);
	/**
	 * 根据id获取问题
	 * @param Id、
	 * @return
	 */
	public CommonQuestion getQuestion(Long id);
	/**
	 * 删除常用问题
	 * @param Id、
	 * @return
	 */
	public void deleteQuestion(Long id);
	/**
	 * 删除常用问题s
	 * @param Id、
	 * @return
	 */
	public void deleteQuestions(Long[] id);

}
