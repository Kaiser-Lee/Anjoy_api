package com.coracle.dms.xweb.common.resolver;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class InstantiationTracingBeanPostProcessor implements
		ApplicationListener<ContextRefreshedEvent> {
	private static final Logger LOG = Logger.getLogger(InstantiationTracingBeanPostProcessor.class);
	private static boolean initialized;
	
	@Autowired
	private ManageResolver manageResolver;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            //只在初始化“根上下文”的时候执行
        	final ApplicationContext app = event.getApplicationContext();
        	if (null == app.getParent()
        		    && ("Root WebApplicationContext".equals(app.getDisplayName())
							|| app.getDisplayName().contains("AnnotationConfigEmbeddedWebApplicationContext"))
					&& "/xweb".equals(app.getApplicationName())
					) { // 当存在父子容器时，此判断很有用
        		LOG.info("*************:" + event.getSource());
            	LOG.info("*************:" + app.getDisplayName());
            	LOG.info("*************:" + app.getApplicationName());
            	LOG.info("*************:" + app.getBeanDefinitionCount());
            	LOG.info("*************:" + app.getEnvironment());
            	LOG.info("*************:" + app.getParent());
            	LOG.info("*************:" + app.getParentBeanFactory());
            	LOG.info("*************:" + app.getId());
            	LOG.info("*************:" + app.toString());
            	LOG.info("*************:" + app);
				if(!initialized && !manageResolver.IsInitialCompleted()) {
					manageResolver.initLater();
					initialized = true;
				}
            }
        } catch (Exception e) {
            LOG.error("((XmlWebApplicationContext) event.getSource()).getDisplayName() 执行失败，请检查Spring版本是否支持");
        }
    }

}