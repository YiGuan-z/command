package com.cqsd.command.result;

import com.cqsd.command.utils.builder.Builder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author caseycheng
 * @date 2022/11/22-22:09
 **/
@SuppressWarnings({"unchecked", "rawtypes"})
public class PageObject
		extends HashMap {
	
	public PageObject() {
		super();
	}
	public PageObject(String message){
		super();
		System.out.println(message);
	}
	
	public PageObject(Object... objects) {
		super();
		System.out.println(Arrays.toString(objects));
	}
	
	public static PageObject of(Integer code,
								String message,
								List<?> data,
								Object record) {
		
		return Builder.builder(PageObject::new)
				.with(code != null, PageObject::put, "code", code)
				.with(message != null, PageObject::put, "message", message)
				.with(data != null, PageObject::put, "data", data)
				.with(record != null, PageObject::put, "record", record)
				.builder();
	}
	
	
}
