package com.cqsd.command.utils.builder.interfaces.args;

/**
 * 用于一个参数的方法
 *
 * @param <T>实例
 * @param <V>value
 */
@FunctionalInterface
public interface InjectsConsumer2<T, V> {
	void accept(T t, V v);
}