package com.coracle.dms.xweb.common.resolver;

import com.coracle.dms.xweb.common.constants.ManageConstants;
import com.coracle.yk.xframework.common.constants.RedisKeyConstants;
import com.coracle.yk.xframework.redis.RedisUtil;
import com.coracle.yk.xframework.util.BlankUtil;
import com.xiruo.medbid.util.SystemUtil;
import com.xiruo.medbid.util.Xiruo;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

/**
 * Comments : Author : jianglingfeng Create Date : 2007-10-26 Modification
 * history : Sr Date Modified By Why & What is modified
 * 
 * @version
 */
@Component("manageResolver")
public final class ManageResolver implements ApplicationContextAware {
	private static final Logger LOG = Logger.getLogger(ManageResolver.class);

	/**
	 * 单例实例
	 */
	private final static ManageResolver instance = new ManageResolver();

	private ReentrantLock lock;

	private ApplicationContext appCtx;

	private boolean initialCompleted;

	private ServletContext servletContext;

	private String contextPath = "/";

	private Map<String, String> configMap = new HashMap<String, String>();

	private ThreadPoolExecutor threadPool; // 线程池
	private ThreadPoolExecutor childThreadPool;

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Resource
	private RedisUtil redisUtil;

	/*@Resource
	private BsdCommonDictionaryService bsdCommonDictionaryService;*/

	/**
	 *
	 * @author jianglingfeng
	 * @date 2007-10-26
	 * @return
	 * @see
	 */
	public static ManageResolver getInstance() {
		return instance;
	}
	
	/**
	 *
	 * @author jianglingfeng
	 * @date 2007-10-26
	 */
	private ManageResolver() {

	}

	/**
	 * 
	 * @author jianglingfeng
	 * @date 2007-10-26
	 */
	@Override
	public void setApplicationContext(ApplicationContext appCtx)
			throws BeansException {
		this.appCtx = appCtx;
		if (appCtx instanceof WebApplicationContext) {
			this.servletContext = ((WebApplicationContext) appCtx)
					.getServletContext();
			this.contextPath = servletContext.getContextPath();
		}
	}

	/**
	 * 
	 * @param taskExecutor
	 */
	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	/**
	 * 描述
	 * @return
	 */
	public ThreadPoolTaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	/**
	 * 
	 * @author jianglingfeng
	 * @date 2007-10-26
	 * @see
	 */
	@PostConstruct
	public void init() {
		/*String path = "/WEB-INF/classes/config/log4j.properties";
		String log4jPath = getRealPath(path);
		if(new File(log4jPath).exists()) {
			PropertyConfigurator.configure(log4jPath);
		} else {
			PropertyConfigurator.configure("target/classes/config/log4j.properties");
		}*/
		lock = new ReentrantLock();
		LOG.info("管理类初始化成功...");
	}
	
	public void initLater() {
		initData();
	}

	/**
	 * 
	 * @author jianglingfeng
	 * @date 2007-11-9
	 * @see
	 */
	public void initData() {
		try {
			if(!initialCompleted) {
				Runnable task = new Runnable() {
					@Override
					public void run() {
						lock.lock();
						if(!initialCompleted) {
							try {
								initConfig();// initial global configuration
								//loadDictionary();//数据字典缓存放到微服务模块加载了
								LOG.info("系统配置初始化成功...");
								initialCompleted = true;
								lock.unlock();
							} catch (Exception e) {
								e.printStackTrace(System.err);
							}
						}
					}
				};
				taskExecutor.execute(task);
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	/**
	 * 将数据字典加载到redis缓存
	 */
	/*private void loadDictionary() {
		LOG.error("--------->加载数据字典");
		List<BsdCommonDictionary> dictionaries = bsdCommonDictionaryService.selectForRedisCache(null);
		if(BlankUtil.isNotEmpty(dictionaries)) {
			for(BsdCommonDictionary dictionary : dictionaries) {
				redisUtil.putInMap(RedisKeyConstants.DICT_REDIS_KEY, dictionary.getId().toString(), dictionary);
			}
		}
	}*/



	/**
	 * 
	 * @author jianglingfeng
	 * @date 2007-10-26
	 * @see
	 */
	@PreDestroy
	public void destroy() {
		// destroy operation
		if (configMap != null) {
			configMap.clear();
			configMap = null;
		}
	}


	/**
	 * 
	 * @author Administrator
	 * @date 2009-1-6
	 * @return
	 * @see
	 */
	public boolean IsInitialCompleted() {
		return initialCompleted;
	}

	/**
	 * 
	 * @author jianglingfeng
	 * @date 2007-10-26
	 * @return
	 * @see
	 */
	public ApplicationContext getApplicationContext() {
		return appCtx;
	}



	/**
	 * 
	 * @author jianglingfeng
	 * @date 2007-11-16
	 * @param servletContext
	 * @see
	 */
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		try {
			contextPath = servletContext.getResource("/").getPath();
			final String reg = "^\\/[^\\/]+(\\/.*)";
			contextPath = contextPath.replaceAll(reg, "$1");
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	/**
	 * 
	 * @author jianglingfeng
	 * @date 2007-11-16
	 * @return
	 * @see
	 */
	public ServletContext getServletContext() {
		return servletContext;
	}


	/**
	 * 
	 * @author Administrator
	 * @date 2008-5-21
	 * @return
	 * @see
	 */
	public String getContextPath() {
		return contextPath;
	}

	/**
	 * 
	 * @author jianglingfeng
	 * @date 2007-11-16
	 * @return
	 * @see
	 */
	public String getRealPath(String path) {
		if (Xiruo.nullToEmpty(path).equals("")) {
			return "";
		}
		if (servletContext == null) {
			return path;
		}

		String p = getServletContext().getRealPath(path);
		File file = new File(p);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		return p;
	}



	/**
	 * 
	 * @author jianglingfeng
	 * @date 2007-11-2
	 * @param key
	 * @param value
	 * @see
	 */
	public void setConfigMap(String key, String value) {
		configMap.put(key, value);
	}

	/**
	 * 
	 * @author jianglingfeng
	 * @date 2007-11-2
	 * @param key
	 * @return
	 * @see
	 */
	public String getConfig(String key) {
		return configMap.get(key);
	}

	/**
	 * 
	 * @author jianglingfeng
	 * @date 2007-11-14
	 * @throws Exception
	 * @see
	 */
	private void initConfig() {
		try {
			// Document document =
			// saxReader.read(getServletContext().getResourceAsStream(PublicConstant.GLOBAL_CONFIG_FILE));
			final SAXReader saxReader = new SAXReader();
			String path = getRealPath(
					ManageConstants.GLOBAL_CONFIG_FILE);
			if(!new File(path).exists()) {
				path = "web/xweb/target/classes/config/global-config.xml";
			}
			Document document = saxReader.read(path);
			List configList = document.getRootElement().elements();
			Element e = null;
			for (Object obj : configList) {
				e = (Element) obj;
				setConfigMap(e.getName(), e.getText().trim());
			}
		} catch (Exception e) {
			LOG.error("初始化global-config.xml文件失败", e);
		}
	}


	/**
	 * 使用线程池来处理任务
	 * 
	 * @param job
	 */
	public void executeJob(Runnable job) {
		if (threadPool == null) {
			int threadPool_corePoolSize = Xiruo
					.nullToInt(getConfig(ManageConstants.THREADPOOL_COREPOOLSIZE));
			if (threadPool_corePoolSize == 0) {
				threadPool_corePoolSize = ManageConstants.DEFAULT_THREADPOOL_COREPOOLSIZE;
			}

			int threadPool_maxPoolSize = Xiruo
					.nullToInt(getConfig(ManageConstants.THREADPOOL_MAXPOOLSIZE));
			if (threadPool_maxPoolSize == 0) {
				threadPool_maxPoolSize = ManageConstants.DEFAULT_THREADPOOL_MAXPOOLSIZE;
			}

			long threadPool_keepAliveTime = Xiruo
					.nullToInt(getConfig(ManageConstants.THREADPOOL_KEEPALIVETIME));
			if (threadPool_keepAliveTime == 0) {
				threadPool_keepAliveTime = ManageConstants.DEFAULT_THREADPOOL_KEEPALIVETIME;
			}

			int threadpool_queuesize = Xiruo
					.nullToInt(getConfig(ManageConstants.THREADPOOL_QUEUESIZE));
			if (threadpool_queuesize == 0) {
				threadpool_queuesize = ManageConstants.DEFAULT_THREADPOOL_QUEUESIZE;
			}

			threadPool = new ThreadPoolExecutor(threadPool_corePoolSize,
					threadPool_maxPoolSize, threadPool_keepAliveTime,
					TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(
							threadpool_queuesize),
					new ThreadPoolExecutor.CallerRunsPolicy());
		}

		threadPool.execute(job);
	}

	public ThreadPoolExecutor getThreadPoolExecutor() {
		return threadPool;
	}

	public void executeChildJob(Runnable job) {
		if (childThreadPool == null) {
			int threadPool_corePoolSize = Xiruo
					.nullToInt(getConfig(ManageConstants.THREADPOOL_COREPOOLSIZE));
			if (threadPool_corePoolSize == 0) {
				threadPool_corePoolSize = ManageConstants.DEFAULT_THREADPOOL_COREPOOLSIZE;
			}

			int threadPool_maxPoolSize = Xiruo
					.nullToInt(getConfig(ManageConstants.THREADPOOL_MAXPOOLSIZE));
			if (threadPool_maxPoolSize == 0) {
				threadPool_maxPoolSize = ManageConstants.DEFAULT_THREADPOOL_MAXPOOLSIZE;
			}

			long threadPool_keepAliveTime = Xiruo
					.nullToInt(getConfig(ManageConstants.THREADPOOL_KEEPALIVETIME));
			if (threadPool_keepAliveTime == 0) {
				threadPool_keepAliveTime = ManageConstants.DEFAULT_THREADPOOL_KEEPALIVETIME;
			}

			int threadpool_queuesize = Xiruo
					.nullToInt(getConfig(ManageConstants.THREADPOOL_QUEUESIZE));
			if (threadpool_queuesize == 0) {
				threadpool_queuesize = ManageConstants.DEFAULT_THREADPOOL_QUEUESIZE;
			}

			childThreadPool = new ThreadPoolExecutor(threadPool_corePoolSize,
					threadPool_maxPoolSize, threadPool_keepAliveTime,
					TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(
							threadpool_queuesize),
					new ThreadPoolExecutor.CallerRunsPolicy());
		}

		childThreadPool.execute(job);
	}

	public ThreadPoolExecutor getChildThreadPoolExecutor() {
		return childThreadPool;
	}

	/**
	 * 
	 * @author xjiang
	 * @date 2010-8-19
	 * @param relativePath
	 * @return
	 * @see
	 */
	public String getVirtualPath(String relativePath) {
		if (!relativePath.endsWith("/") || !relativePath.endsWith("\\")) {
			relativePath += "/";
		}
		return getContextPath() + relativePath;
	}

	/**
	 * 
	 * @author jianglingfeng
	 * @date 2008-5-14
	 * @param msg
	 * @see
	 */
	public void writeDebugMessage(final String msg) {
		PrintWriter out = null;
		try {
			final String debugFilePath = getRealPath("debug.txt");
			out = new PrintWriter(debugFilePath);
			out.println(msg);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		} finally {
			try {
				if (out != null) {
					out.close();
					out = null;
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}
	}

	/**
	 * 
	 * @author jianglingfeng
	 * @date 2008-5-21
	 * @param source
	 * @return
	 * @see
	 */
	public String revisePath(String source) {
		if (Xiruo.nullToEmpty(source).equals("")) {
			return source;
		}

		final String contextPath = getContextPath();
		String target = source;
		boolean flag = false;
		String reg = "(src[\\s]*\\=[\\s]*[\"']?)(?!http\\:\\/\\/)(?!\\/)(?!\\.)([^\"'>\\s]+)";
		Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE
				| Pattern.UNICODE_CASE | Pattern.DOTALL);
		flag = pattern.matcher(target).find();
		int i = 0;
		while (flag && i < 10) {
			target = pattern.matcher(target).replaceAll(
					"$1" + contextPath + "$2");
			flag = pattern.matcher(target).find();
			i++;
		}

		reg = "(href[\\s]*\\=[\\s]*[\"']?)(?!http\\:\\/\\/)(?!\\/)(?!\\.)([^\"'>\\s]+)";
		pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE
				| Pattern.UNICODE_CASE | Pattern.DOTALL);
		flag = pattern.matcher(target).find();
		i = 0;
		while (flag && i < 10) {
			target = pattern.matcher(target).replaceAll(
					"$1" + contextPath + "$2");
			flag = pattern.matcher(target).find();
			i++;
		}
		return target;
	}

	/**
	 * 生成缩略图
	 * 
	 * @author jianglingfeng
	 * @date 2008-7-24
	 * @param imgPath
	 * @return
	 * @throws Exception
	 * @see
	 */
	public String createPreviewImage(final String imgPath) throws Exception {
		int index = imgPath.lastIndexOf("/");
		if (index == -1) {
			index = imgPath.lastIndexOf("\\");
		}

		if (index == -1 || index >= imgPath.length() - 1) {
			return "";
		}

		final String imgName = imgPath.substring(index + 1);
		final String folderPath = imgPath.substring(0, index + 1);
		final String previewFolderPath = folderPath.replaceAll("forum",
				"preview");
		final String folderAbsPath = getRealPath(folderPath) + "/";
		final String previewFolderAbsPath = getRealPath(previewFolderPath)
				+ "/";
		final String previewName = SystemUtil.createPreviewImage(imgName,
				folderAbsPath, previewFolderAbsPath);
		if (previewName.equals("")) {
			return "";
		} else {
			return previewFolderPath + previewName;
		}
	}

	public String getUploadRootPath() {
		String uploadPath = ManageConstants.UPLOAD_ROOT_PATH;
		if (!Xiruo.nullToEmpty(getConfig("upload-rootpath")).equals("")) {
			uploadPath = getConfig("upload-rootpath");
		}

		uploadPath += "/files/";
		return uploadPath;
	}

	public String getUploadPath() {
		return getUploadRootPath() + SystemUtil.getMonthFolder();
	}
	
	public String getUploadPathWithView() {
		return "/files/" + SystemUtil.getMonthFolder();
	}

	public String getPreviewRootPath() {
		String uploadPath = ManageConstants.UPLOAD_ROOT_PATH;
		if (!Xiruo.nullToEmpty(getConfig("upload-rootpath")).equals("")) {
			uploadPath = getConfig("upload-rootpath");
		}

		uploadPath += "/preview/";
		return uploadPath;
	}
	public String getCompressRootPath() {
		String uploadPath = ManageConstants.UPLOAD_ROOT_PATH;
		if (!Xiruo.nullToEmpty(getConfig("upload-rootpath")).equals("")) {
			uploadPath = getConfig("upload-rootpath");
		}

		uploadPath += "/compress/";
		return uploadPath;
	}

	public String getUploadPreviewPath() {
		return getPreviewRootPath() + SystemUtil.getMonthFolder();
	}

	public String getUploadCompressPath(){
		return getCompressRootPath()+ SystemUtil.getMonthFolder();
	}
	public String getPreviewPathWithView() {
		return "/preview/" + SystemUtil.getMonthFolder();
	}
}
