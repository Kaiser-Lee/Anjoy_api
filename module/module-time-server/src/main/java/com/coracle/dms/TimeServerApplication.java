package com.coracle.dms;

import com.coracle.dms.time.TimeServer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TimeServerApplication {

	public static void main(String[] args) {
		Properties properties = new Properties();
		InputStream in = ClassLoader.class.getResourceAsStream("/config/application-time.properties");
		try {
			properties.load(in);
			in.close();
			int port = 9999;
			try {
				port = Integer.parseInt(properties.getProperty("server.port"));
			} catch (Exception e) {
				port = 9999;
			}
			//启动时间服务器
			new TimeServer().start(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}