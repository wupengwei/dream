package com.wpw.dream.datatable.action;

import java.util.List;

/**
 * 
 * Project Name：mmcs
 * ClassName：Pagination
 * Description：列表分页。包含具体数据list。
 * 
 * @date: 2012-6-29 下午03:00:56
 * note:
 *
 */
public class Pagination<T> extends SimplePage implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 类的构造方法
	 * 创建一个新的实例 Pagination.
	 * @param pageNo 当前页码
	 * @param pageSize 每页显示条数
	 * @param totalCount 总记录数
	*/
	public Pagination(Integer pageNo, Integer pageSize, int totalCount) {
		super(pageNo, pageSize, totalCount);
	}

	/**
	 * 
	 * 类的构造方法
	 * 创建一个新的实例 Pagination.
	 * @param pageNo 当前页码
	 * @param pageSize 每页显示条数
	 * @param totalCount 总记录数
	 * @param list 分页内容
	*/
	public Pagination(Integer pageNo, Integer pageSize, int totalCount, List<T> list) {
		super(pageNo, pageSize, totalCount);
		this.list = list;
	}

	/**
	 * 
	 * 方法描述：第一条数据位置
	 * 
	 * @date: 2012-6-29 下午03:03:21
	 * @return
	 */
	public int getFirstResult() {
		return (pageNo - 1) * pageSize;
	}

	/**
	 * 当前页的数据
	 */
	private List<T> list;

	/**
	 * 
	 * 方法描述：获得分页内容
	 * 
	 * @date: 2012-6-29 下午03:03:28
	 * @return
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * 
	 * 方法描述：设置分页内容
	 * 
	 * @date: 2012-6-29 下午03:03:34
	 * @param list
	 */
	public void setList(List<T> list) {
		this.list = list;
	}
}
