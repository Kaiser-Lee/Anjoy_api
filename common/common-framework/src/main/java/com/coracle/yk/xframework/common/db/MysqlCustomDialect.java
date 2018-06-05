package com.coracle.yk.xframework.common.db;

import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.DateType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;

public class MysqlCustomDialect extends MySQLDialect {
	public MysqlCustomDialect() {
		super();
		registerFunction("convert", new SQLFunctionTemplate(StringType.INSTANCE, "convert(?1 using ?2)"));
		registerFunction("convert_gbk", new SQLFunctionTemplate(StringType.INSTANCE, "convert(?1 using gbk)"));
		registerFunction("date_add", new SQLFunctionTemplate(DateType.INSTANCE, "date_add(?1, INTERVAL ?2 ?3)"));
		registerFunction("adddate", new SQLFunctionTemplate(DateType.INSTANCE, "ADDDATE(?1, INTERVAL ?2 ?3)"));
		registerFunction("addtime", new SQLFunctionTemplate(TimestampType.INSTANCE, "ADDTIME(?1,?2)"));
		registerFunction("date_sub", new SQLFunctionTemplate(DateType.INSTANCE, "DATE_SUB(?1, INTERVAL ?2 ?3)"));
		registerFunction("group_concat", new SQLFunctionTemplate(StringType.INSTANCE, "group_concat(?1)"));
		registerFunction("lpad", new SQLFunctionTemplate(StringType.INSTANCE, "LPAD(?1, ?2, ?3)"));
		registerFunction("replace", new SQLFunctionTemplate(StringType.INSTANCE, "REPLACE(?1, ?2, ?3)"));
		registerFunction("variance", new StandardSQLFunction("variance", DoubleType.INSTANCE) ); 
		registerFunction("stddev", new StandardSQLFunction("stddev", DoubleType.INSTANCE) );  
	}
}
