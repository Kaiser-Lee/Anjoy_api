package com.coracle.yk.xframework.zookeeper.demo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;


public class ServerAppx {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient(DemoConstants.ZK_SERVER + ":" + DemoConstants.ZK_PORT, new ExponentialBackoffRetry(1000, 3));
//    	TestingServer server = new TestingServer();
//    	CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(),
//    			new ExponentialBackoffRetry(1000, 3));
        client.start();
        ServiceRegistrar serviceRegistrar = new ServiceRegistrar(client, DemoConstants.ZK_DEMO);
        ServiceInstance<ITest> instance1 = ServiceInstance.<ITest>builder()
                .name("service1")
                .port(DemoConstants.ZK_PORT)
                .address(DemoConstants.ZK_SERVER)   //address不写的话,会取本地ip
                .payload(new ITestImpl("Service1.Producer"))
                .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
                .build();
        ServiceInstance<ITest> instance2 = ServiceInstance.<ITest>builder()
                .name("service2")
                .port(DemoConstants.ZK_PORT)
                .address(DemoConstants.ZK_SERVER)
                .payload(new ITestImpl("Service2.Producer"))
                .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
                .build();
        serviceRegistrar.registerService(instance1);
        serviceRegistrar.registerService(instance2);
    }
}
