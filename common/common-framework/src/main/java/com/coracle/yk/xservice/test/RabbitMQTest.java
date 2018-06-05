package com.coracle.yk.xservice.test;

import java.io.FileInputStream;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;


@SuppressWarnings("unused")
public class RabbitMQTest {
	private ClassPathXmlApplicationContext ctx;
	
	public static void main(String[] args) throws Exception {
		RabbitMQTest instance = new RabbitMQTest();
		try {
			instance.setUp();
			instance.test();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			instance.tearDown();
			instance = null;
		}
	}
	
	public void setUp() throws Exception {
		PropertyConfigurator.configure("src/main/resources/config/log4j.properties");
		ctx = new ClassPathXmlApplicationContext(
				new String[] {
						"classpath:config/applicationContext-rabbitMQ-test.xml"
				});
	}

	public void tearDown() throws Exception {
		if(null != ctx) {
			ctx.close();
			ctx.destroy();
			ctx = null;
		}
	}

	public void test() throws Exception {
		AmqpTemplate ykMqTemplate = (AmqpTemplate)ctx.getBean("ykMqTemplate");
//		File file = new File("F:\\tools\\open source\\xmlto-0.0.26.tar.gz");
		FileInputStream fis = null;
		try {
//			fis = new FileInputStream(file);
//			byte[] bs = new byte[(int)file.length()];
//			fis.read(bs);
//			Message message = MessageBuilder.withBody(bs).setHeader("ContentType", "application/octet-stream").build();
//			YkRegion region = new YkRegion();
//			region.setId(1);
//			region.setName("希偌哦");
//			region.setParentId(0);
//			//region.setRemark("remark");
//			region.setOrderField("xxxxx");
//			region.setOrderString("Desc");
//			ykMqTemplate.convertAndSend("yk_exchange", "yk_queue", region);
			byte[] b = new byte[0];
			synchronized(b) {
				System.out.println("Message sended, wait...");
//				b.wait();
				Thread.sleep(3000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fis != null) {
				fis.close();
				fis = null;
			}
		}
	}

}
