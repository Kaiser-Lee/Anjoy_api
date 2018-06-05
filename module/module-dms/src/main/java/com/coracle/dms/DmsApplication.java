package com.coracle.dms;

import java.net.URL;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DmsApplication {

	public static void main(String[] args) {

		if(args.length > 0) {
			String command = args[0];
			if("stop".equals(command.toLowerCase())) {

			}
		} else {

			URL url = DmsApplication.class.getResource("/config/log4j.properties");
			if(url != null) {
				PropertyConfigurator.configure(url);
			} else {
				PropertyConfigurator.configure("src/main/resources/config/log4j.properties");
			}

			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] {
							"classpath:config/applicationContext-positec.xml",
							"classpath:config/applicationContext-positec-dubbo.xml",
							"classpath:config/applicationContext-positec-jedis.xml"
					});

			try {

				System.out.println("dms MicroService provided, Main thread wait...");
				byte[] b = new byte[0];
				synchronized(b) {
					b.wait();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(null != ctx) {
					ctx.close();
					ctx.destroy();
				}
			}
			
		}
		
	}
	
}