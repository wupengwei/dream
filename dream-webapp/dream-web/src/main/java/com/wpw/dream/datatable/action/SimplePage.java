package com.wpw.dream.datatable.action;

/**
 * 
 * @Package com.xdja.platform.datacenter.database.page
 * @ClassName: SimplePage
 * @Description: 简单分页类
 * 
 * @date 2013-10-24 上午10:20:54
 */
public class SimplePage {
	
	public static final int PAGE_SIZE = 20;

	public SimplePage(){
	    
	}
	
	/**
	 * 
	 * <p>创建一个新的实例 SimplePage.</p>
	 * @param pageNo
	 * @param pageSize
	 * @param totalCount
	 */
	public SimplePage(Integer pageNo, Integer pageSize, int totalCount) {
		setTotalCount(totalCount);
		setPageSize(pageSize);
		setPageNo(pageNo);
		adjustPageNo();
	}

	/**
	 * 
	 * @Title: adjustPageNo
	 * @Description: 调整页码，使不超过最大页数
	 */
	public void adjustPageNo() {
		if (pageNo == 1) {
			return;
		}
		int tp = getTotalPage();
		if (pageNo > tp) {
			pageNo = tp;
		}
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getTotalPage() {
		int totalPage = totalCount / pageSize;
		if (totalPage == 0 || totalCount % pageSize != 0) {
			totalPage++;
		}
		return totalPage;
	}

	public boolean isFirstPage() {
		return pageNo <= 1;
	}

	public boolean isLastPage() {
		return pageNo >= getTotalPage();
	}

	public int getNextPage() {
		if (isLastPage()) {
			return pageNo;
		} else {
			return pageNo + 1;
		}
	}

	public int getPrePage() {
		if (isFirstPage()) {
			return pageNo;
		} else {
			return pageNo - 1;
		}
	}

	protected int totalCount = 0;
	protected int pageSize = PAGE_SIZE;
	protected int pageNo = 1;

	/**
	 * 
	 * 方法描述：if totalCount<0 then totalCount=0
	 * @author: 任瑞修
	 * @date: 2012-6-29 下午03:00:05
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		if (totalCount < 0) {
			this.totalCount = 0;
		} else {
			this.totalCount = totalCount;
		}
	}

	/**
	 * 
	 * 方法描述：if pageSize< 1 then pageSize=DEF_COUNT
	 * @author: 任瑞修
	 * @date: 2012-6-29 下午03:00:15
	 * @param pageSize
	 */
	public void setPageSize(Integer pageSize) {
		if (pageSize == null || pageSize < 1) {
			this.pageSize = PAGE_SIZE;
		} else {
			this.pageSize = pageSize;
		}
	}

	/**
	 * 
	 * 方法描述：if pageNo < 1 then pageNo=1
	 * @author: 任瑞修
	 * @date: 2012-6-29 下午03:00:23
	 * @param pageNo
	 */
	public void setPageNo(Integer pageNo) {
		if (pageNo == null || pageNo < 1) {
			this.pageNo = 1;
		} else {
			this.pageNo = pageNo;
		}
	}
}
