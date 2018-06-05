package com.coracle.yk.xframework.zookeeper.demo;

import java.io.Closeable;
import java.util.List;
import java.util.Map;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.apache.curator.x.discovery.strategies.RandomStrategy;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ServiceDiscoverer {
    private ServiceDiscovery<ITest> serviceDiscovery;
    private Map<String, ServiceProvider<ITest>> providers = Maps.newHashMap();
    private List<Closeable> closeableList = Lists.newArrayList();
    private Object lock = new Object();


    public ServiceDiscoverer(CuratorFramework client ,String basePath) throws Exception {
        JsonInstanceSerializer<ITest> serializer = new JsonInstanceSerializer<ITest>(ITest.class);
        serviceDiscovery = ServiceDiscoveryBuilder.builder(ITest.class)
                .client(client)
                .basePath(basePath)
                .serializer(serializer)
                .build();

        serviceDiscovery.start();
    }


    public ServiceInstance<ITest> getInstanceByName(String serviceName) throws Exception {
        ServiceProvider<ITest> provider = providers.get(serviceName);
        if (provider == null) {
            synchronized (lock) {
                provider = providers.get(serviceName);
                if (provider == null) {
                    provider = serviceDiscovery.serviceProviderBuilder().
                            serviceName(serviceName).
                            providerStrategy(new RandomStrategy<ITest>())
                            .build();
                    provider.start();
                    closeableList.add(provider);
                    providers.put(serviceName, provider);
                }
            }
        }


        return provider.getInstance();
    }


    public synchronized void close(){
       for (Closeable closeable : closeableList) {
			CloseableUtils.closeQuietly(closeable);
       }
    }


}
