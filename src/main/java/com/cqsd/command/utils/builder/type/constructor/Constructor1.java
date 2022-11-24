package com.cqsd.command.utils.builder.type.constructor;
/**
 * 单参构造
 * @param <T> 实体
 * @param <P> 参数
 */
@FunctionalInterface
public interface Constructor1<T,P> extends Const<T>{
	T get(P p);
}