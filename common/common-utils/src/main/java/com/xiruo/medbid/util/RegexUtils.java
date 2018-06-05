package com.xiruo.medbid.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class RegexUtils {
	public RegexUtils() {
    }

    public static String findMatchContent(String regx, String source) {
        if(StringUtils.isNotEmpty(regx) && StringUtils.isNotEmpty(source)) {
            Matcher m = Pattern.compile(regx).matcher(source);
            if(m.find()) {
                return m.group();
            }
        }

        return null;
    }

    public static List<String> findMatchContents(String regx, String source) {
        if(StringUtils.isNotEmpty(regx) && StringUtils.isNotEmpty(source)) {
            ArrayList lst = new ArrayList();
            Matcher m = Pattern.compile(regx).matcher(source);

            while(m.find()) {
                lst.add(m.group());
            }

            return lst.isEmpty()?null:lst;
        } else {
            return null;
        }
    }

}
