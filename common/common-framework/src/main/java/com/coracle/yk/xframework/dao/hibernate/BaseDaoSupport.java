package com.coracle.yk.xframework.dao.hibernate;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;


public class BaseDaoSupport extends HibernateDaoSupport {
	@Resource(name = "mySessionFactory")
	// 为父类HibernateDaoSupport注入sessionFactory的值
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
		
	}
}