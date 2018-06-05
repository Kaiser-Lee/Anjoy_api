package com.coracle.yk.xframework.zookeeper.demo;

import java.io.IOException;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;


public class ServiceRegistrar {

    private ServiceDiscovery<ITest> serviceDiscovery;
    @SuppressWarnings("unused")
	private final CuratorFramework client;


    public ServiceRegistrar(CuratorFramework client, String basePath) throws Exception {
        this.client = client;
        JsonInstanceSerializer<ITest> serializer = new JsonInstanceSerializer<ITest>(ITest.class);
        serviceDiscovery = ServiceDiscoveryBuilder.builder(ITest.class)
                .client(client)
                .serializer(serializer)
                .basePath(basePath)
                .build();
        serviceDiscovery.start();
    }

    public void registerService(ServiceInstance<ITest> serviceInstance) throws Exception {
        serviceDiscovery.registerService(serviceInstance);
    }

    public void unregisterService(ServiceInstance<ITest> serviceInstance) throws Exception {
        serviceDiscovery.unregisterService(serviceInstance);

    }

    public void Service(ServiceInstance<ITest> serviceInstance) throws Exception {
//        serviceDiscovery.Service(serviceInstance);
    }

    public void close() throws IOException {
        serviceDiscovery.close();
    }
}