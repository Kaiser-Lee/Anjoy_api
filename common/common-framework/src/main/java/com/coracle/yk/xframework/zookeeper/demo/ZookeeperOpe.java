/**
 * 
 */
package com.coracle.yk.xframework.zookeeper.demo;

import java.util.Arrays;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;


/**
 * @author lenovo
 *
 */
public class ZookeeperOpe {
	public void connectZookeeper() {
		zkCacheAction();
	}

	public void zkCacheAction() {
		TestingServer server = null;
		CuratorFramework client = null;
		PathChildrenCache cache = null;
		try {
			server = new TestingServer();
			client = CuratorFrameworkFactory.newClient(server
					.getConnectString(), new ExponentialBackoffRetry(1000, 3));
			client.start();
			// in this example we will cache data. Notice that this is optional.
			cache = new PathChildrenCache(client, DemoConstants.ZK_ROOT, true);
			cache.start();
			for (ChildData data : cache.getCurrentData()) {
				System.out.println(data.getPath() + " = "
						+ new String(data.getData()));
			}
//			processCommands(client, cache);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseableUtils.closeQuietly(cache);
			CloseableUtils.closeQuietly(client);
			CloseableUtils.closeQuietly(server);
		}
	}

	public void zkCreateNode() {
		CuratorFramework client = null;
		PathChildrenCache cache = null;
		try {
			client = CuratorFrameworkFactory.builder()
					.connectString(DemoConstants.ZK_SERVER + ":" + DemoConstants.ZK_PORT)
					.retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000))
					.connectionTimeoutMs(5000).build();
			client.start();
			cache = new PathChildrenCache(client, DemoConstants.ZK_SERVER + ":" + DemoConstants.ZK_PORT, true);
			cache.start();
			client.create().creatingParentsIfNeeded()
					.withMode(CreateMode.PERSISTENT)
					.forPath(DemoConstants.ZK_ROOT, "hello, Xiruo.".getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.close();
				client = null;
			}
		}
	}

	public void zkRemoveNode() {
		CuratorFramework client = null;
		try {
			client = CuratorFrameworkFactory.builder()
					.connectString(DemoConstants.ZK_SERVER + ":" + DemoConstants.ZK_PORT)
					.retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000))
					.connectionTimeoutMs(5000).build();
			client.start();
			client.delete().guaranteed().deletingChildrenIfNeeded()
					.withVersion(-1).forPath(DemoConstants.ZK_ROOT);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.close();
				client = null;
			}
		}
	}

	public void zkModifyNode() {
		CuratorFramework client = null;
		try {
			client = CuratorFrameworkFactory.builder()
					.connectString(DemoConstants.ZK_SERVER + ":" + DemoConstants.ZK_PORT)
					.retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000))
					.connectionTimeoutMs(5000).build();
			client.start();
			client.setData().withVersion(-1)
					.forPath(DemoConstants.ZK_ROOT, "Hello, World!".getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.close();
				client = null;
			}
		}
	}

	public void zkReadNode() {
		CuratorFramework client = null;
		try {
			client = CuratorFrameworkFactory.builder()
					.connectString(DemoConstants.ZK_SERVER + ":" + DemoConstants.ZK_PORT)
					.retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000))
					.connectionTimeoutMs(5000).build();
			client.start();
			Stat stat = new Stat();
			byte[] nodeData = client.getData().storingStatIn(stat)
					.forPath(DemoConstants.ZK_ROOT);
			System.out.println("NodeData: " + new String(nodeData));
			System.out.println("Stat: " + stat);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.close();
				client = null;
			}
		}
	}

	public void zkListNode() {
		CuratorFramework client = null;
		try {
			client = CuratorFrameworkFactory.builder()
					.connectString(DemoConstants.ZK_SERVER + ":" + DemoConstants.ZK_PORT)
					.retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000))
					.connectionTimeoutMs(5000).build();
			client.start();
			List<String> children = client.getChildren().forPath(DemoConstants.ZK_ROOT);
			System.out.println("Children: " + children);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.close();
				client = null;
			}
		}
	}

	public void zkClientCuratorInvoker() {
		CuratorFramework client = null;
		try {
			client = CuratorFrameworkFactory.builder()
					.connectString(DemoConstants.ZK_SERVER + ":" + DemoConstants.ZK_PORT)
					.retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000))
					.connectionTimeoutMs(5000).build();
			// client = CuratorFrameworkFactory.newClient(DemoConstants.ZK_SERVER + ":" + DemoConstants.ZK_PORT,
			// // 服务器列表
			// 5000, // 会话超时时间，单位毫秒
			// 3000, // 连接创建超时时间，单位毫秒
			// new ExponentialBackoffRetry(1000, 3) // 重试策略
			// );
			System.out.println("aaaaaaaaaa");
			// 启动 上面的namespace会作为一个最根的节点在使用时自动创建
			client.start();

			// 创建一个节点
			client.create().forPath("/head", new byte[0]);

			// 异步地删除一个节点
			client.delete().inBackground().forPath("/head");

			// 创建一个临时节点
			client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
					.forPath("/head/child", new byte[0]);

			// 取数据
			client.getData().watched().inBackground().forPath("/test");

			// 检查路径是否存在
			client.checkExists().forPath(DemoConstants.ZK_ROOT);

			// 异步删除
			client.delete().inBackground().forPath("/head");

			// 注册观察者，当节点变动时触发
			client.getData().usingWatcher(new Watcher() {
				@Override
				public void process(WatchedEvent event) {
					System.out.println("node is changed");
				}
			}).inBackground().forPath("/test");
			System.out.println(client.getChildren().forPath("/").size());
			for (String path : client.getChildren().forPath("/")) {
				System.out.println(path);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.close();
				client = null;
			}
		}
	}

	public void zkOriginalInvoker() {
		try {
			// 创建一个与服务器的连接
			ZooKeeper zk = new ZooKeeper(DemoConstants.ZK_SERVER + ":" + DemoConstants.ZK_PORT, 30000,
					new Watcher() {
						// 监控所有被触发的事件
						public void process(WatchedEvent event) {
							System.out.println("状态:" + event.getState() + ":"
									+ event.getType() + ":"
									+ event.getWrapper() + ":"
									+ event.getPath());
						}
					});
			// 创建一个总的目录ktv，并不控制权限，这里需要用持久化节点，不然下面的节点创建容易出错
			zk.create(DemoConstants.ZK_ROOT, "DemoConstants.ZK_ROOT-ktv".getBytes(),
					ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

			// 然后杭州开一个KTV , PERSISTENT_SEQUENTIAL 类型会自动加上 0000000000 自增的后缀
			zk.create(DemoConstants.ZK_ROOT + "/杭州KTV", "杭州KTV".getBytes(),
					ZooDefs.Ids.OPEN_ACL_UNSAFE,
					CreateMode.PERSISTENT_SEQUENTIAL);

			// 也可以在北京开一个, EPHEMERAL session 过期了就会自动删除
			zk.create(DemoConstants.ZK_ROOT + "/北京KTV", "北京KTV".getBytes(),
					ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

			// 同理，我可以在北京开多个，EPHEMERAL_SEQUENTIAL session 过期自动删除，也会加数字的后缀
			zk.create(DemoConstants.ZK_ROOT + "/北京KTV-分店", "北京KTV-分店".getBytes(),
					ZooDefs.Ids.OPEN_ACL_UNSAFE,
					CreateMode.EPHEMERAL_SEQUENTIAL);

			// 我们也可以 来看看 一共监视了多少家的ktv
			List<String> ktvs = zk.getChildren(DemoConstants.ZK_ROOT, true);
			System.out.println(Arrays.toString(ktvs.toArray()));
			for (String node : ktvs) {
				// 删除节点
				zk.delete(DemoConstants.ZK_ROOT + "/" + node, -1);
			}
			// 根目录得最后删除的
			zk.delete(DemoConstants.ZK_ROOT, -1);
			zk.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
