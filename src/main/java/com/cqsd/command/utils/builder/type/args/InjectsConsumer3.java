package com.cqsd.command.utils.builder.type.args;
/**
 * 用于两个参数的方法
 *
 * @param <T>实例
 * @param <K>key
 * @param <V>value
 */
@FunctionalInterface
public interface InjectsConsumer3<T, K, V> {
	void accept(T t, K k, V v);
}