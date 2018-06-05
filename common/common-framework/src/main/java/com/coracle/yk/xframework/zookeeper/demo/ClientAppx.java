package com.coracle.yk.xframework.zookeeper.demo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.x.discovery.ServiceInstance;

public class ClientAppx {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient(DemoConstants.ZK_SERVER + ":" + DemoConstants.ZK_PORT, new ExponentialBackoffRetry(1000, 3));
//    	TestingServer server = new TestingServer();
//    	CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(),
//    			new ExponentialBackoffRetry(1000, 3));
        client.start();
        ServiceDiscoverer serviceDiscoverer = new ServiceDiscoverer(client, DemoConstants.ZK_DEMO);
      
        ServiceInstance<ITest> instance1 = null;

        while(instance1 == null) {
        	instance1 = serviceDiscoverer.getInstanceByName("service1");
        	System.out.println("instance1 is null...");
        	Thread.sleep(300);
        }

        ITest instance = instance1.getPayload();
        System.out.println(instance1.buildUriSpec());
        System.out.println(instance);
        System.out.println(instance.test("Xiruo"));

        ServiceInstance<ITest> instance2 = serviceDiscoverer.getInstanceByName("service2");

        instance = instance2.getPayload();
        System.out.println(instance2.buildUriSpec());
        System.out.println(instance);
        System.out.println(instance.test("JiangLingFeng"));

        serviceDiscoverer.close();
        CloseableUtils.closeQuietly(client);
    }
}