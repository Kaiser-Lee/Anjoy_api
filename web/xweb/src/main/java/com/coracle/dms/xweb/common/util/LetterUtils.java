package com.coracle.dms.xweb.common.util;

import com.coracle.yk.xframework.util.StringUtil;

public class LetterUtils {
	/**
	 * 判断字符串的首字母是否是英文字母
	 * @param arg
	 * @return
	 */
	public static boolean isLetter(String arg) {
		if (StringUtil.isBlank(arg)) {
			return false;
		}
		char c = arg.charAt(0);
		return (c >= 'a' && c <= 'z') || (c >='A' && c <= 'Z');
	}
}
