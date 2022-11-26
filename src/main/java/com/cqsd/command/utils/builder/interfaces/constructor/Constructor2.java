package com.cqsd.command.utils.builder.interfaces.constructor;

/**
 * 双参构造
 * @param <T> 实体
 * @param <P> 参数
 * @param <P1> 参数
 */
@FunctionalInterface
public interface Constructor2<T,P,P1> extends Const<T>{
	T get(P p,P1 p1);
}