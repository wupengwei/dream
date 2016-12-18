package com.wpw.solr.test;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wpw.dream.solr.base.SolrUtil;

public class SolrUtilTest {
	
	private SolrClient supplyDemandSolrServer;
	
	@Before
	public void setUp() throws Exception
	{
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:META-INF/spring/application-solr.xml");
		supplyDemandSolrServer = applicationContext.getBean("supplyDemandSolrServer", SolrClient.class);
	}

	@After
	public void tearDown() throws Exception
	{

	}

	@Test
	public void testQueryMember() throws Exception
	{
		System.out.println(supplyDemandSolrServer.toString());
		SupplyDemandIndex queryById = SolrUtil.queryById("1648898", SupplyDemandIndex.class, supplyDemandSolrServer);
		System.out.println(queryById.getBreedName());
	}
	
	@Test
	public void testInsert() throws SolrServerException, IOException {
		SupplyDemandIndex student = new SupplyDemandIndex();
		student.setId("222222");
		student.setCompanyName("ceshiming");
		List<String> properties = new ArrayList<>();
		properties.add("id");
		properties.add("companyName");
		SolrUtil.addDoc(student, properties, supplyDemandSolrServer);
	}
	
	@Test
	public void queryByIdTest() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, SolrServerException, IOException {
		SupplyDemandIndex queryById = SolrUtil.queryById("1648580", SupplyDemandIndex.class, supplyDemandSolrServer);
	}
}

    