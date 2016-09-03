package com.aws.sns.util;

public final class StringUtil {
	
	public static boolean isEmpty(String str) {
		return (str == null || str.isEmpty()) ? true : false;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	public static boolean isEmpty(String... strs) {
		for (String string : strs) {
			if(isEmpty(string)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isNotEmpty(String... strs) {
		return !isEmpty(strs);
	}
	
	public static boolean isBlank(String s) {
		if (isEmpty(s)) {
			return true;
		}

		if (isEmpty(s.trim())) {
			return true;
		}

		return false;
	}
}
