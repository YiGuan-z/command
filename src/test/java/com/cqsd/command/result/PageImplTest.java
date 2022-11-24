package com.cqsd.command.result;

import com.cqsd.command.utils.builder.Builder;
import org.junit.jupiter.api.Test;

class PageImplTest {
	@Test
	void testCreateSuccessObject(){
		System.out.println(System.getProperty("java.version").split("\\.")[0]);
		Builder.builder(PageObject::new,"你好").builder();
		final var success = Page.success();
		System.out.println(success);
	}
	
}