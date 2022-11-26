package com.cqsd.command.utils.builder.interfaces.args;

/**
 * 用于不限制参数的方法
 *
 * @param <T>实例
 */
@FunctionalInterface
public interface InjectComsumerArgs<T> {
	void apply(T t, Object... args);
}