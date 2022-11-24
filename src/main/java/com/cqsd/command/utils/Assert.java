package com.cqsd.command.utils;

import java.util.List;
import java.util.Map;

/**
 * @author caseycheng
 * @date 2022/11/13-09:28
 **/
abstract public class Assert {
	public static void requireNotNull(Object obj, Throwable throwable) {
		if (obj == null) throw new RuntimeException(throwable);
	}
	
	public static void requireNotNull(Object obj, String message) {
		if (obj == null) throw new RuntimeException(message);
	}
	
	public static void requireNotNull(Object obj) {
		if (obj == null) throw new RuntimeException();
	}
	
	public static void assertFalse(boolean express, String message) {
		if (express) throw new RuntimeException(message);
	}
	
	public static void requireNotNullMap(Map<?, ?> params) {
		Assert.requireNotNull(params);
		Assert.assertFalse(params.size() == 0, "该map为空");
	}
	
	public static void requireNotNullList(List<?> list) {
		Assert.requireNotNull(list);
		Assert.assertFalse(list.size() == 0, "该list为空");
	}
}
