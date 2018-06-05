package com.coracle.yk.xframework.zookeeper.config.spring;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;

import com.coracle.yk.xframework.zookeeper.config.ConfigConstants;
import com.coracle.yk.xframework.zookeeper.config.IConfig;
import com.xiruo.medbid.util.Xiruo;

//@Service("zookeeperDatabaseConfigurerPusher")
public class ZookeeperDatabaseConfigurerPusher {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Value("${druid.zookeeper.db}")
	private String db;
	@Value("${druid.zookeeper.table}")
	private String table;
	
	private List<Map<String, Object>> pList;
	
	@PostConstruct
	public void init() {
		if(Xiruo.nullToEmpty(db).equals("")) {
			db = ConfigConstants.ZOOKEEPER_CONFIG_DB;
		}
		if(Xiruo.nullToEmpty(table).equals("")) {
			table = ConfigConstants.ZOOKEEPER_CONFIG_TABLE;
		}
		
	}
	
	private void loadConfigurationsFromDatabase() {
		int c = jdbcTemplate.queryForObject("select count(0) from INFORMATION_SCHEMA.TABLES"
				+ " where "
				+ " lower(TABLE_SCHEMA)='ykee_comm'"
				+ " and lower(TABLE_NAME)='" + table + "'", Integer.class);
		if(c == 0) {
			System.out.println("Warning: Table '" + table + "' not exists in db '" + db + "'");
		} else {
			pList = jdbcTemplate.queryForList("select * from "+db+"."+table);
			if(pList.isEmpty()) {
				System.out.println("Warning: Cannot read any config-item from db table '" + table + "'");
			}
		}
	}
	@PreDestroy
	public void destroy() {
		
	}
	
	public void pushConfiguration2Zookeeper(IConfig zookeeperIConfigImpl) {
		try {
			loadConfigurationsFromDatabase();
			String key = "";
			String value = "";
			if(pList == null || pList.isEmpty()) {
				System.out.println("Warning: Not any config-item in Database is pushed to zookeeper server...");
			} else {
				for(Map<String, Object> map : pList) {
					key = (String)map.get("profile_key");
					value = (String)map.get("profile_value");
					zookeeperIConfigImpl.putConfig(key, value.getBytes(ConfigConstants.DEFAULT_CHARSET));
				}
				System.out.println("Put config-itesm in " + db + "." + table + " to zookeeper completed...");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
