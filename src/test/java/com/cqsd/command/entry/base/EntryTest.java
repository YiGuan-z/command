package com.cqsd.command.entry.base;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntryTest {
	@Test
	void testPrint(){
		final var student = new Student();
		final var millis = System.currentTimeMillis();
		System.out.println(student);
		final var timeMillis = System.currentTimeMillis();
		System.out.println(timeMillis-millis+"ms");
	}
	static class Student extends Entry{
		private String name="nood";
		private Integer age=32;
		private List<String> da=List.of("list","java","undefined");
	}
}