package com.cqsd.command.result;

import java.util.List;

public abstract class Page {
	public static final String SUCCESS_MSG = "success";
	public static final Integer SUCCESS_CODE = 200;
	public static final String FAILED_MSG = "failed";
	public static final Integer FAILED_CODE = 400;
	
	protected static PageObject success(String message, List<?> data) {
		return PageObject.of(SUCCESS_CODE, message, data, null);
	}
	
	protected static PageObject success(List<?> data) {
		return PageObject.of(SUCCESS_CODE, SUCCESS_MSG, data, null);
	}
	
	protected static <T> PageObject success(T data) {
		return PageObject.of(SUCCESS_CODE, SUCCESS_MSG, null, data);
	}
	
	protected static PageObject success() {
		return PageObject.of(SUCCESS_CODE, SUCCESS_MSG, null, null);
	}
	
	protected static PageObject failed(Integer code, String message) {
		return PageObject.of(code, message, null, null);
	}
	
	protected static PageObject failed(String message) {
		return PageObject.of(FAILED_CODE, message, null, null);
	}
	
	protected static PageObject failed() {
		return PageObject.of(FAILED_CODE, FAILED_MSG, null, null);
	}
}
