package com.cqsd.command.utils.builder.interfaces.constructor;

/**
 * 有参构造 这个方法只能让构造函数的接受类型限定了为Object数组
 * @param <T>
 */
@FunctionalInterface
public interface ConstructorArgs<T> extends Const<T> {
	T get(Object... args);
}