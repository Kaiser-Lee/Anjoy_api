/**
 * 
 */
package com.coracle.yk.xframework.zookeeper.demo;

import java.io.Closeable;
import java.io.IOException;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;


/**
 * @author lenovo
 * 
 */
public class ZookeeperProducer implements Closeable {
	private final ServiceDiscovery<ITest> serviceDiscovery;
	private final ServiceInstance<ITest> thisInstance;

	public ZookeeperProducer(CuratorFramework client, String path, String serviceName) throws Exception {
		UriSpec uriSpec = new UriSpec("http://" + DemoConstants.ZK_SERVER + ":" + DemoConstants.ZK_PORT);
        thisInstance = ServiceInstance.<ITest>builder().name(serviceName).payload(new ITestImpl())
                .port(2345) // in a real application,
                                                        // you'd use a common
                                                        // port
                .uriSpec(uriSpec).build();
        // if you mark your payload class with @JsonRootName the provided
        // JsonInstanceSerializer will work
        JsonInstanceSerializer<ITest> serializer = new JsonInstanceSerializer<ITest>(ITest.class);
        serviceDiscovery = ServiceDiscoveryBuilder.builder(ITest.class).client(client).basePath(path).serializer(serializer)
                .thisInstance(thisInstance).build();
	}
	
    public ServiceInstance<ITest> getThisInstance() {
        return thisInstance;
    }

    public void start() throws Exception {
        serviceDiscovery.start();
    }

    @Override
    public void close() throws IOException {
        CloseableUtils.closeQuietly(serviceDiscovery);
    }
}
