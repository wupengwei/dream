package com.wpw.dream.solr.base;

import java.util.List;

public class Page<T> {
	
	private Integer pageNo;
	
	private Integer pageSize;
	
	private Integer start;
	
	private Long totalRecord;
	
	private List<T> results;

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(Long totalRecord) {
		this.totalRecord = totalRecord;
	}

	public List getResults() {
		return results;
	}

	public void setResults(List results) {
		this.results = results;
	}
	
	

}
