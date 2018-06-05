package com.coracle.common.support.mongodb;

import com.mongodb.*;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.gridfs.GridFS;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * MongoDB工具类 Mongo实例代表了一个数据库连接池，即使在多线程的环境中，一个Mongo实例对我们来说已经足够了
 * 注意Mongo已经实现了连接池，并且是线程安全的。 设计为单例模式， 因
 * MongoDB的Java驱动是线程安全的，对于一般的应用，只要一个Mongo实例即可， Mongo有个内置的连接池（默认为10个）
 * 对于有大量写和读的环境中，为了确保在一个Session中使用同一个DB时， DB和DBCollection是绝对线程安全的
 * updated by huangbaidong
 * 20170424
 */
public class MongoDBUtil {

	public static Logger logger = LoggerFactory.getLogger(MongoDBUtil.class);

	private MongoClient mongoClient;

	private static final MongoDBUtil mongoDBUtil = new MongoDBUtil();

	public static String dbname = "positec";
	private static String uri = "";
	private static Integer connectionsPerHost = 100;
	private static Integer connectTimeout = 30000;
	private static Integer maxWaitTime = 60000;
	private static Integer socketTimeout = 0;
	private static Integer maxConnectionIdleTime = 0;
	private static Integer maxConnectionLifeTime = 0;
	private static Integer threadsAllowedToBlockForConnectionMultiplier = 10;

	private static MongoClientOptions options;

	private MongoDBUtil() {
		if (mongoClient == null) {
			mongoClient = getMongoClient();
		}
		System.out.println("=======================实例化MangodbUtil");
	}

	/**
	 * 单例模式，获取MongoDBUtil实例
	 * @return
	 */
	public static MongoDBUtil getInstance() {
		return mongoDBUtil;
	}


	// 初始化mongoDB连接池客户端
	private MongoClient getMongoClient() {
		Builder builder = getMongoClientOptionsBuilder();
		MongoClientURI connectionString = new MongoClientURI(uri, builder);
		return new MongoClient(connectionString);
	}

	private static Builder getMongoClientOptionsBuilder() {
		loadConfig();
		Builder builder = new Builder();
		builder.connectionsPerHost(connectionsPerHost);// 连接池设置为300个连接,默认为100
		builder.connectTimeout(connectTimeout);// 连接超时，推荐>3000毫秒
		builder.maxWaitTime(maxWaitTime); // 最大等待可用连接的时间
		builder.socketTimeout(socketTimeout);// 套接字超时时间，0无限制
		builder.maxConnectionIdleTime(maxConnectionIdleTime);// 连接的最大闲置时间，0：表示无限制。
		builder.maxConnectionLifeTime(maxConnectionLifeTime);// 连接的最大生存时间，0：表示无限制。
		// 线程队列数，如果连接线程排满了队列就会抛出“Out of semaphores to get db”错误。
		builder.threadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlockForConnectionMultiplier);
		return builder;
	}

	private static void loadConfig() {
		Properties properties = new Properties();
		InputStream in = ClassLoader.class.getResourceAsStream("/config/mongodb.properties");
		if (in == null) {
			in = MongoDBUtil.class.getClassLoader().getResourceAsStream("/config/mongodb.properties");
		}
		if (in == null) {
			throw new RuntimeException("读取配置文件config/mongodb.properties失败");
		}
		try {
			properties.load(in);
			in.close();
			Object tUri = properties.get("mongodb.server.uri");
			Object tdbname = properties.get("mongodb.db.dbname");
			Object tConnectionsPerHost = properties.get("mongodb.pool.connectionsPerHost");
			Object tConnectTimeout = properties.get("mongodb.pool.connectTimeout");
			Object tMaxWaitTime = properties.get("mongodb.pool.maxWaitTime");
			Object tSocketTimeout = properties.get("mongodb.pool.socketTimeout");
			Object tMaxConnectionIdleTime = properties.get("mongodb.pool.maxConnectionIdleTime");
			Object tMaxConnectionLifeTime = properties.get("mongodb.pool.maxConnectionLifeTime");
			Object tThreadsAllowedToBlockForConnectionMultiplier = properties.get("mongodb.pool.threadsAllowedToBlockForConnectionMultiplier");
			if(tUri == null) {
				System.out.println("mongodb uri 配置参数为空，请检查mongodb.properties配置文件");
				return;
			}
			if(tdbname == null) {
				System.out.println("tdbname 配置参数为空，请检查mongodb.properties配置文件");
				return;
			}
			uri = tUri.toString();
			dbname = tdbname.toString();
			connectionsPerHost = Integer.parseInt((String) tConnectionsPerHost);
			connectTimeout = Integer.parseInt((String)tConnectTimeout);
			maxWaitTime = Integer.parseInt((String)tMaxWaitTime);
			socketTimeout = Integer.parseInt((String)tSocketTimeout);
			maxConnectionIdleTime = Integer.parseInt((String)tMaxConnectionIdleTime);
			maxConnectionLifeTime = Integer.parseInt((String)tMaxConnectionLifeTime);
			threadsAllowedToBlockForConnectionMultiplier = Integer.parseInt((String)tThreadsAllowedToBlockForConnectionMultiplier);
			System.out.println("uri:"+uri);
			System.out.println("dbname:"+dbname);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	// ------------------------------------共用方法---------------------------------------------------
	/**
	 * 获取DB实例 - 指定DB
	 *
	 * @param dbName
	 * @return
	 */
	public MongoDatabase getDatabase(String dbName) {
		if (dbName != null && !"".equals(dbName)) {
			MongoDatabase database = mongoClient.getDatabase(dbName);
			return database;
		}
		return null;
	}

	/**
	 * 获取DB实例 - 指定DB
	 *
	 * @param dbName
	 * @return
	 */
	public DB getDB(String dbName) {
		if (dbName != null && !"".equals(dbName)) {
			DB db = mongoClient.getDB(dbName);
			return db;
		}
		return null;
	}

	/**
	 * 获取GridFS实例
	 *
	 * @return
	 */
	public GridFS getGridFS(String dbName, String collectionName) {
		return new GridFS(mongoClient.getDB(dbName), collectionName);
	}

	/**
	 * 获取GridFS实例
	 *
	 * @return
	 */
	public GridFS getGridFS(String dbName) {
		return new GridFS(mongoClient.getDB(dbName));
	}


	/**
	 * 获取collection对象 - 指定Collection
	 * @param dbName
	 * @param collectionName
	 * @return
	 */
	public MongoCollection<Document> getCollection(String dbName, String collectionName) {
		if (null == collectionName || "".equals(collectionName)) {
			return null;
		}
		if (null == dbName || "".equals(dbName)) {
			return null;
		}
		return getDatabase(dbName).getCollection(collectionName);
	}

	/**
	 * 获取collection对象 - 指定Collection
	 * @return
	 */
	public MongoCollection<Document> getDBCollection(String dbName, String collectionName) {
		if (null == dbName || "".equals(dbName)) {
			return null;
		}
		if (null == collectionName || "".equals(collectionName)) {
			return null;
		}
		return this.getDatabase(dbName).getCollection(collectionName);
	}

	/**
	 * 由ID获取数据
	 *
	 * @param dbName
	 * @param collectionName
	 * @param id
	 * @return
	 */
	public Document findById(String dbName, String collectionName, String id) {
		BsonDocument document = new BsonDocument();
		document.put("_id", new BsonString(id));
		return this.getDBCollection(dbName, collectionName).findOneAndDelete(document);
	}

	/** 总条数 */
	public int getCount(MongoCollection<Document> coll) {
		int count = (int) coll.count();
		return count;
	}

	/** 条件查询 - 不分页 */
	public MongoCursor<Document> find(MongoCollection<Document> coll, Bson filter) {
		return coll.find(filter).iterator();
	}

	/** 条件查询 - 分页总条数 */
	public int getCount(MongoCollection<Document> coll, Map<String, Object> filterMap) {
		Bson filter = new BasicDBObject(filterMap);
		return (int) coll.count(filter);
	}

	/**
	 * 分页查询集合下匹配的数据
	 *
	 * @param coll
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public MongoCursor<Document> findByPage(MongoCollection<Document> coll, Map<String, Object> filterMap, Map<String, Object> orderMap, int pageNo,
											int pageSize) {
		Bson filter = new BasicDBObject(filterMap);
		Bson orderBy = new BasicDBObject(orderMap);
		return coll.find(filter).sort(orderBy).skip((pageNo - 1) * pageSize).limit(pageSize).iterator();
	}

	/**
	 * 获取所有数据库名称列表
	 */
	public MongoIterable<String> getAllDBNames() {
		MongoIterable<String> s = mongoClient.listDatabaseNames();
		return s;
	}

	/**
	 * 查询DB下的所有表名
	 *
	 * @param dbName
	 * @return
	 */
	public List<String> getAllCollections(String dbName) {
		MongoIterable<String> colls = getDatabase(dbName).listCollectionNames();
		List<String> _list = new ArrayList<String>();
		for (String s : colls) {
			_list.add(s);
		}
		return _list;
	}

	/**
	 * 删除一个数据库
	 */
	public void dropDB(String dbName) {
		getDatabase(dbName).drop();
	}

	/**
	 * 删除一个集合
	 *
	 * @param dbName
	 * @param collName
	 */
	public void dropCollection(String dbName, String collName) {
		getDatabase(dbName).getCollection(collName).drop();
	}

	/**
	 * MongoClient的close方法会关闭底层连接，MongoClient的实例将变得不可用，我们应该根据程序的需要，适当的调用该方法，释放资源
	 * 。
	 */
	public void close() {
		if (mongoClient != null) {
			mongoClient.close();
			mongoClient = null;
		}
	}
}
