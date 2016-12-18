package com.wpw.dream.solr.base;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.cloud.SolrZkClient;

public class SolrUtil {
	
	
	/**
	 * 为多个文档对象的，某些属性建立索引
	 *
	 * @date 2015-8-30 下午5:33:29
	 * @param list
	 * @param properties
	 * @param solrClient
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	public static <T> void addDocs(List<T> list, List<String> properties, SolrClient solrClient) throws SolrServerException, IOException {
		if(null == list || list.size() == 0 ) {
			return;
		}
		List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		List<Field> fields = ReflectUtil.getAllFields(list.get(0).getClass());
		for (T obj : list) {
			SolrInputDocument doc = new SolrInputDocument();
			for (Field field : fields) {
				for (String property : properties) {
					if(property.equals(field.getName())) {
						doc.addField(property, ReflectUtil.invokeGetterMethod(obj, property));
					}
				}
			}
			docs.add(doc);
		}
		solrClient.add(docs);
		solrClient.commit();
	}
	
	/**
	 * 建立单个索引
	 *
	 * @param <T>
	 * @date 2015-8-30 下午5:33:58
	 * @param student
	 * @param properties
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	public static <T> void addDoc(T obj, List<String> properties, SolrClient solrClient) throws SolrServerException, IOException {
		List<T> list = new ArrayList<T>();
		list.add(obj);
		addDocs(list, properties, solrClient);
	}
	
	/**
	 * 将整个对象都添加到索引
	 * @author wuyw
	 * @param <T>
	 * @date 2015-8-30 下午5:35:34
	 * @param student
	 * @throws SolrServerException 
	 * @throws IOException 
	 */
	public static <T> void addBean(T obj, SolrClient solrClient) throws IOException, SolrServerException {
		solrClient.addBean(obj);
		solrClient.commit();
	}
	
	/**
	 * 添加多个索引对象
	 *
	 * @param <T>
	 * @date 2015-8-30 下午5:36:37
	 * @param students
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	public static <T> void addBeans(List<T> list, SolrClient solrClient) throws SolrServerException, IOException {
		solrClient.addBeans(list);
		solrClient.commit();
	}
	
	/**
	 * 根据id删除某条索引
	 * 
	 * @date 2015-8-30 下午5:37:46
	 * @param id
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	public static void deleteById(String id, SolrClient solrClient) throws SolrServerException, IOException {
    	solrClient.deleteById(id);
    	solrClient.commit();
    }
	
	/**
	 * 根据查询语句删除索引
	 *
	 * @date 2015-8-30 下午5:38:46
	 * @param query
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	public static void deleteByQuery(String query, SolrClient solrClient) throws SolrServerException, IOException {
    	solrClient.deleteByQuery(query);
    	solrClient.commit();
    }
	
	@SuppressWarnings("unchecked")
	public static <T> T queryById(String id, Class<?> entityClass, SolrClient solrClient) throws InstantiationException, SolrServerException, IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		T obj = null;
		try {
			obj = (T) entityClass.newInstance();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
			throw e1;
		}
		SolrQuery query = new SolrQuery();
		query.setQuery("id:" + id);
		QueryResponse response = null;
		try {
			response = solrClient.query(query);
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
			throw e;
		}
		SolrDocumentList docs = response.getResults();
		if(null == docs || docs.size() == 0) {
			return null;
		}
		SolrDocument doc = docs.get(0);
		List<Field> fields = ReflectUtil.getAllFields(obj.getClass());
		for (Field field : fields) {
			String propertyName = field.getName();
			Object fieldValue = doc.getFieldValue(propertyName);
			if (fieldValue == null) {
				continue;
			}
			String propertyValue = fieldValue.toString();
			Class<?> propertyClass = field.getType();
			ReflectUtil.setFieldValue(obj, propertyClass, propertyName, propertyValue);
		}
		
		return obj;
	}
	
	/**
	 * solr获取的分页对象
	 * 
	 * @param <T>
	 * @date 2015-8-30 下午5:39:36
	 * @param page
	 * @param solrQuery 里面封装了查询对象的条件
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	@SuppressWarnings("unchecked")
	public static <T> Page<T> getPage(Page<T> page, SolrQuery solrQuery, SolrClient solrClient, Class<?> entityClass) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		solrQuery.setStart(page.getStart());//开始索引
		solrQuery.setRows(page.getPageSize());//分页的数量
		QueryResponse queryResponse = null;
		try {
			queryResponse = solrClient.query(solrQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}
		SolrDocumentList docs = queryResponse.getResults();
		List<T> list = new ArrayList<T>(0);
		
		for(SolrDocument doc : docs){
			T obj = null;
			try {
				obj =  (T) entityClass.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			List<Field> fields = ReflectUtil.getAllFields(obj.getClass());
			for (Field field : fields) {
				String propertyName = field.getName();
				String propertyValue = (String) doc.getFieldValue(propertyName);
				Class<?> propertyClass = field.getType();
				if(propertyClass.equals(Integer.class)) {
					Integer value = Integer.valueOf(propertyValue);
					ReflectUtil.setFieldValue(obj, propertyClass, propertyName, value);
				} else {
					ReflectUtil.setFieldValue(obj, propertyClass, propertyName, propertyValue);
				}
			}
			list.add(obj);
		}
		page.setTotalRecord(docs.getNumFound());
		page.setResults(list);
		return page;
	}
	
	/**
	 * 优化solr索引
	 *
	 * @date 2015-8-31 上午12:02:49
	 * @param solrClient
	 */
	public static void optimize(String collection, SolrZkClient solrClient) {
		try {
			//solrClient.optimize(collection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
