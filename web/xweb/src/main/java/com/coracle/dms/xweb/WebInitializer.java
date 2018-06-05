package com.coracle.dms.xweb;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.web.SpringBootServletInitializer;

public class WebInitializer extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer {
	 @Override  
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {  
	        return builder.sources(XwebApplication.class);
	    }  
	 
	 @Override  
	    public void customize(ConfigurableEmbeddedServletContainer container) {  
	        container.setPort(8081);  
	    }  
}
