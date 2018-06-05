package com.coracle.dms.xweb.common.constants;


public final class ManageConstants {
	public final static String SYSTEM_CODE = "Coracle-YK";
	public final static String DEFAULT_CHARSET = "UTF-8";
	public final static int USE_FLAG_INVALID = 0;
	public final static int USE_FLAG_VALID = 1;

	public final static long ROLE_ADMIN_ID = 1L;
	public final static long ROLE_BACKADMIN_ID = 4L;
    public static final String CURRENT_MANAGE_DEALER_KEY = "currentManageDealer";
    public static final long DEFAULT_MANAGE_DEALER = 1L;

    public static String COMMONBO_HQL = "HQL";
    public static String COMMONBO_PARA = "PARA";
    public static String DAO_SQL = "dao_sql";
    public static String DAO_ORDERBY = "dao_orderby";
	
    /**
     * 线程池配置
     */
	public static final int DEFAULT_THREADPOOL_COREPOOLSIZE = 8;
	public static final int DEFAULT_THREADPOOL_MAXPOOLSIZE = 10;
	public static final long DEFAULT_THREADPOOL_KEEPALIVETIME = 5;
	public static final int DEFAULT_THREADPOOL_QUEUESIZE = 30;
    public final static String THREADPOOL_KEEPALIVETIME = "threadpool-keepalivetime";
    public final static String THREADPOOL_COREPOOLSIZE = "threadpool-corepoolsize";
    public final static String THREADPOOL_MAXPOOLSIZE = "threadpool-maxpoolsize";
    public final static String THREADPOOL_QUEUESIZE = "threadpool-queuesize";
	
    public final static String REQUIRE_LOG = "require-log";
    
    /**
     * 
     */
    public final static int ROLE_COPY_TYPE_USER = 1;
    public final static int ROLE_COPY_TYPE_RESOURCE = 2;
    public final static int ROLE_COPY_TYPE_MENU = 3;
    public final static int ROLE_COPY_TYPE_REGION = 4;
    //用户类型-系统管理员
    public final static int USER_TYPE_ADMIN = 1;
    //用户类型-普通用户
    public final static int USER_TYPE_USER = 2;

    /**
     * 验证码标识
     */
    public final static String GENERATE_CHECK_CODE_FLAG = "generateCheckCodeFlag";
    /**
     * 验证码生成时间
     */
    public final static String CHECK_CODE_GENERATE_TIME = "checkCodeGenerateTime";
    /**
     * 验证码超时时间（10分钟）单位毫秒
     */
    public final static long CHECK_CODE_TIMEOUT = 600000;
    /**
     * 验证码编码
     */
    public final static String CHECK_CODE_ENCODING = "check-code-encoding";
    public static enum checkCodeEncodings {
    	NUMBER("number"), CHARACTER("character"), MIXTURE("mixture"), GB("gb");
    	
    	private String encoding;
    	checkCodeEncodings(String encoding) {
    		this.encoding = encoding;
    	}
    	
    	public boolean equals(String encoding) {
    		if(encoding == null) {
    			return false;
    		}
    		
    		return encoding.equalsIgnoreCase(this.encoding);
    	}
    }
    
    /**
     * 上传文件大小限制，10M
     */
    public final static int UPLOAD_SIZE_LIMIT = 10240 * 1024;
    
    /**
     * 上传文件生成的缩略图的默认宽度和高度
     */
    public final static int UPLOAD_PREVIEW_WIDTH = 150;
    public final static int UPLOAD_PREVIEW_HEIGHT = 150;
    public static final String ALLOW_PREVIEW_TYPE = ",jpg,jpeg,png,bmp,gif,tif,";
    public static final String ALLOW_UPLOAD_TYPE = ",jpg,jpeg,png,bmp,gif,tif,zip,rar,xls,xlsx,doc,docx,3gp,avi,mp4,rmvb,mkv";
    public static final String NOT_ALLOW_UPLOAD_TYPE = ",jsp,html,js,php";

	/**
	 * 
	 */
	public final static String XML_ROOT_PATH = "resources/xml/";

	/**
     * 文件位置
     */
	public final static String GLOBAL_CONFIG_FILE = "/WEB-INF/classes/config/global-config.xml";
    public final static String UPLOAD_ROOT_PATH = "resources/upload/";
    public final static String HTML_ROOT_PATH = "resources/html/";
    public final static String HTML_CATEGORY_PATH = HTML_ROOT_PATH + "category/";
    public final static String HTML_REGION_PATH = HTML_ROOT_PATH + "region/";
    public final static String UPLOAD_PUBLISH_PATH = "upload-publish-path";
    public final static String HTML_SUFFIX_NAME = ".htm";
    public final static String HTML_MAINPAGE_FILE_PATH = HTML_ROOT_PATH + "index_";
    public final static String HTML_ALL_FILE_PATH =  HTML_ROOT_PATH + "all_";
    public final static String ORIGINAL_SOURCE_PATH =  "resources/original/";
    
	/**
	 * 百度应用key
	 */
	public static final String BAIDU_APP_KEY_SERVER = "vVzHYu7SsqDo5mEBWmjQz07H";
	public static final String BAIDU_APP_KEY_MOBILE = "ecfX8fa57akIC0S64YneSjH8";
	public static final String BAIDU_APP_KEY_BROWSER = "Z0jShzhQIMPZC6AFaGaCj3Tg";

    /**
     * 密码最大输错次数
     */
    public static final int PWD_RETRY_COUNT=5;

    /***
     * 扫码下载应用地址
     */
     public final static String APP_QRCODE_UPLOAD_ADDRESS = "resources/down/";

}
