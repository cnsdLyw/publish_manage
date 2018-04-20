package com.litc.taglib;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import org.springframework.data.domain.Sort.Order;
/**
 *  Function:模版使用分页对象  
 *  @author  zhongying(281264212@qq.com)
 *  @date    2015-11-28 下午2:21:29    
 *  @version 1.0
 */
public class PageList  {

	private int currentPage;// 当前页数
	private int totalPage;// 总页数据
	private int pageSize;// 每页条数
	private int totalCount;// 总条数
	//private Map<String, String> displayMap; // 展示的字段的DisplayName
	private List<Map<String, String>> dataList;// 不包括Id的DataList
	
	//private List<Order> orders;// 排序规则

	public List<Map<String, String>> getDataList() {
		return dataList;
	}

	public void setDataList(List<Map<String, String>> dataList) {
		this.dataList = dataList;
	}

	public int getCurrentPage() {
		if (currentPage < 1) {
			currentPage = 1;
		}
		return currentPage;
//		return currentPage;
	}

//	public Map<String, String> getDisplayMap() {
//		return displayMap;
//	}
//
//	public void setDisplayMap(Map<String, String> displayMap) {
//		this.displayMap = displayMap;
//	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getTotalPage() {
		//return totalPage;
		totalPage = (totalPage + pageSize - 1)/pageSize;
		if(totalPage==0){totalPage=1;};
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

//	public List<Order> getOrders() {
//		return orders;
//	}
//
//	public void setOrders(List<Order> orders) {
//		this.orders = orders;
//	}
	
	
	/**
	 * 首页
	 * @return
	 */
	public int getTopPageNo() {
		return 1;
	}

	/**
	 * 上一页
	 * @return
	 */
	public int getPreviousPageNo() {
		if (currentPage <= 1) {
			return 1;
		}
		return  currentPage - 1;
	}
	
	/**
	 * 下一页
	 * @return
	 */
	public int getNextPageNo() {
		if (currentPage >= getBottomPageNo()) {
			return getTotalPage()==0?1:getBottomPageNo();
		}
		return currentPage + 1;
	}
	
	/**
	 * 尾页
	 * @return
	 */
	public int getBottomPageNo() {
		return getTotalPage()==0?1:getTotalPage();
	}
	

}
