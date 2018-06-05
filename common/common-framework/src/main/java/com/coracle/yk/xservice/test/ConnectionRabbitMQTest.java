package com.coracle.yk.xservice.test;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;

import com.rabbitmq.client.Channel;



public class ConnectionRabbitMQTest {
  
    public static void main(String[] argv) {
    	Connection connection = null;
    	Channel channel = null;
    	try {
	        /** 
	         * 创建连接连接到MabbitMQ 
	         */  
    		CachingConnectionFactory factory = new CachingConnectionFactory();  
	        //设置MabbitMQ所在主机ip或者主机名  
	        factory.setHost("192.168.8.165");
	        factory.setPort(5672);
	        factory.setUsername("root");
	        factory.setPassword("yk2015");
	        
	        factory.setVirtualHost("/");
	        //创建一个连接  
	        connection = factory.createConnection();  
	        //创建一个频道  
	        channel = connection.createChannel(true);  
	        //指定一个队列  
	        channel.queueDeclare("yk_queue", false, false, false, null);  
	        //发送的消息  
	        String message = "hello world!";  
	        //往队列中发出一条消息  
	        channel.basicPublish("", "yk_queue", null, message.getBytes());  
	        System.out.println(" [x] Sent '" + message + "'");  
    	} catch(Exception e) {
    		e.printStackTrace();
    	} finally {
    		try {
	    		//关闭频道和连接 
    			if(channel != null) {
    				channel.close();
    			}
    			if(null != connection) {
    				connection.close();
    			}
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
     }  
}
