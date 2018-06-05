package com.coracle.yk.xframework.zookeeper;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.coracle.yk.xframework.zookeeper.config.IConfig;
import com.coracle.yk.xframework.zookeeper.config.spring.ZookeeperDatabaseConfigurerPusher;

public final class Starup {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = 
				new ClassPathXmlApplicationContext("classpath:config/applicationContext-xframework.xml");
//		XiruoReader xr = (XiruoReader)ctx.getBean("xiruoReader-xframwork");
		IConfig config = (IConfig)ctx.getBean("zookeeperIConfigImpl");
		//使用global.properties
//		ZookeeperPropertyPlaceholderConfigurerPusher ppc = (ZookeeperPropertyPlaceholderConfigurerPusher)ctx.getBean("zookeeperPropertyPlaceholderConfigurerPusher");
//		ppc.pushConfiguration2Zookeeper(config);
		//使用数据库表
		ZookeeperDatabaseConfigurerPusher dcp = (ZookeeperDatabaseConfigurerPusher)ctx.getBean("zookeeperDatabaseConfigurerPusher");
		dcp.pushConfiguration2Zookeeper(config);
		ctx.close();
		ctx.destroy();
	}

}
