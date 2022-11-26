package com.cqsd.command.entry.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author caseycheng
 * @date 2022/11/23-10:14
 **/
public abstract class Entry implements Serializable, Cloneable {
	@Override
	public String toString() {
		return Arrays.stream(getClass().getDeclaredFields())
				.filter(field -> {
					field.setAccessible(true);
					return true;
				}).collect(Collectors.toMap(
						Field::getName,
						(Field field) -> {
							try {
								return field.get(this);
							} catch (IllegalAccessException ignore) {
								return "null";
							}
						})
				).toString();
	}
	
	
	@Override
	public Entry clone() {
		try {
			return (Entry) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}
}
