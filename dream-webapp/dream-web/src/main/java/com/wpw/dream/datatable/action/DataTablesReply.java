package com.wpw.dream.datatable.action;


import java.util.ArrayList;
import java.util.List;

/**
 * @author:马征
 * 
 * Project Name：cmac-web
 * ClassName：DataTablesReply
 * Description：dataTable ajax请求数据 返回   参照mdmp
 * 
 * @date: 2015-1-27 上午9:49:17
 * note:
 *
 */
public class DataTablesReply<T> {

	/**
	 * 实际的行数
	 */
	private int recordsTotal;
	
	/**
	 * 过滤之后，实际的行数。
	 */
	private int recordsFiltered;
	
	/**
	 * 来自客户端 sEcho 的没有变化的复制品，
	 */
	private int draw;
	
	/**
	 * 数组的数组，表格中的实际数据(注意，T对象不能再包含其他对象)。
	 */
	private List<T> data;

	public DataTablesReply() {
		super();
	}

	/**
	 * 此方法需要另外设置draw，建议使用 DataTablesReply(Pagination<T> page, int draw)
	 * @param page 分页数据
	 */
	public DataTablesReply(Pagination<T> page) {
		this.setData(page.getList());
		this.setRecordsFiltered(page.getTotalCount());
		this.setRecordsTotal(page.getTotalCount());
	}
	
	/**
	 * 
	 * @param page 分页数据
	 * @param draw 从datatables前台传来的draw原封传回，由页码进行计数处理
	 */
	public DataTablesReply(Pagination<T> page, int draw) {
		this.setData(page.getList());
		this.setRecordsFiltered(page.getTotalCount());
		this.setRecordsTotal(page.getTotalCount());
		this.draw = draw;
	}
	
	public int getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public List<T> getData() {
		if(null == data){
			data = new ArrayList<T>();
		}
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
}
