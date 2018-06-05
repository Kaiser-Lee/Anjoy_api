package com.coracle.dms.schedule;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.URL;

public class ScheduleApplication {

	public static void main(String[] args) {

		if(args.length > 0) {
			String command = args[0];
			if("stop".equals(command.toLowerCase())) {

			}
		} else {

			URL url = ScheduleApplication.class.getResource("/log4j.properties");
			if(url != null) {
				PropertyConfigurator.configure(url);
			} else {
				PropertyConfigurator.configure("src/main/resources/config/log4j.properties");
			}

			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] {
							"classpath:config/applicationContext-schedule.xml",
							"classpath:config/applicationContext-jedis.xml",
							"classpath:config/applicationContext-schedule-dubbo.xml"
					});

			try {
				System.out.println("schedule MicroService provided, Main thread wait...");
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