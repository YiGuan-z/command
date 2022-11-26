package com.cqsd.command.utils.builder;

import com.cqsd.command.utils.builder.type.args.InjectComsumerArgs;
import com.cqsd.command.utils.builder.type.args.InjectsConsumer2;
import com.cqsd.command.utils.builder.type.args.InjectsConsumer3;
import com.cqsd.command.utils.builder.type.constructor.Const;
import com.cqsd.command.utils.builder.type.constructor.Constructor;
import com.cqsd.command.utils.builder.type.constructor.ConstructorArgs;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author caseycheng
 * @date 2022/11/21-15:05
 * 柯里化操作函数，让Builder更加方便，不用在实体类上添加Builder方法，只要有提供setter就可以实现Builder。
 **/
final public class Builder<T> {
	/**
	 * 存放实例 XXX::new 出来的实例好像是unsafe的AllowInstance方法
	 */
	private final Const<T> constructor;
	/**
	 * 存放操作列表
	 */
	private final List<Consumer<T>> Injects = new ArrayList<>();
	/**
	 * 存放构造函数的参数
	 */
	private final Object[] constructorArgs;
	private static final Boolean isJDK19 = Objects.equals(System.getProperty("java.version")
			.split("\\.")[0], "19");
	
	/**
	 * 提供构造器对象，附带参数
	 *
	 * @param constructor XXX::new args
	 */
	private Builder(ConstructorArgs<T> constructor, Object... args) {
		this.constructor = constructor;
		this.constructorArgs = args;
	}
	
	/**
	 * 无参构造器对象
	 *
	 * @param constructor XXX::new
	 */
	private Builder(Constructor<T> constructor) {
		this.constructor = constructor;
		this.constructorArgs = null;
	}
	
	/**
	 * 用于扩展的接口
	 *
	 * @param constructor 构造函数
	 * @param args        参数列表
	 */
	private Builder(Const<T> constructor, Object... args) {
		this.constructor = constructor;
		this.constructorArgs = args;
	}
	
	//	public static <T,P> Builder<T> builder(Constructor1<T,P>constructor,P p){
//		return new Builder<>(constructor,p);
//	}
	public static <T> Builder<T> builder(ConstructorArgs<T> construtor, Object... constructorArgs) {
		return new Builder<>(construtor, constructorArgs);
	}
	
	public static <T> Builder<T> builder(Constructor<T> constructor) {
		return new Builder<>(constructor);
	}
	
	/**
	 * 用于扩展的接口
	 *
	 * @param constructor 构造函数
	 * @param args        参数列表
	 * @param <T>         构造函数类型
	 * @return 构建器
	 */
	public static <T> Builder<T> builder(Const<T> constructor, Object... args) {
		return new Builder<>(constructor, args);
	}
	
	/**
	 * 通过参数列表获取参数
	 * 有问题，因为class会重复
	 * @param args 需要转换的参数列表
	 * @return 一个参数类型和参数值组成的map
	 */
	
	public static Map<Class<?>, Object> getParams(Object... args) {
		return Arrays.stream(args)
				.collect(Collectors.toMap(Object::getClass, obj -> obj));
	}
	
	/**
	 * 通过参数列表和参数类型来获取一个参数列表
	 * @param t 参数类型
	 * @param args 通过Builder获取的参数列表，需要在构造函数里创建Object...的构造
	 * @return 获取的参数列表
	 * @param <T> 参数类型
	 */
	public static <T> List<Object> getParams(Class<T> t, Object... args) {
		final var list = List.of(args);
         		return list.stream()
				.filter(ar -> ar.getClass() == t).toList();
	}
	
	/**
	 * 提供set方法来创建操作列表
	 * @param consumer set方法
	 * @param p 参数类型
	 * @return Builder
	 * @param <P> 参数
	 */
	
	public <P> Builder<T> with(final InjectsConsumer2<T, P> consumer,
							   final P p) {
		final Consumer<T> c = instance -> consumer.accept(instance, p);
		this.Injects.add(c);
		return this;
	}
	
	
	// 在这里转化成还需要一个实例化对象的偏函数
	public <K, V> Builder<T> with(InjectsConsumer3<T, K, V> consumer,
								  final K k,
								  final V v) {
		final Consumer<T> c = instance -> consumer.accept(instance, k, v);
		this.Injects.add(c);
		return this;
	}
	
	
	public Builder<T> with(final InjectComsumerArgs<T> comsumerArgs,
						   final Object... args) {
		final Consumer<T> c = instance -> comsumerArgs.apply(instance, args);
		this.Injects.add(c);
		return this;
	}
	//如果表达式为true就添加到list操作列表里 K V
	public <K, V> Builder<T> with(boolean express,
								  final InjectsConsumer3<T, K, V> consumer,
								  final K k,
								  final V v) {
		return express ? with(consumer, k, v) : this;
	}
	//如果表达式未true就添加到list操作列表中，多个参数
	public Builder<T> with(boolean express,
						   final InjectComsumerArgs<T> comsumerArgs,
						   final Object... args) {
		return express ? with(comsumerArgs, args) : this;
	}
	
	public T builder() {
		//在对象初始化后 instance现在才被确定
		//在这里选择构造函数对象
		T instanceObj = null;
		if (isJDK19) {
			switch (this.constructor) {
				case ConstructorArgs<T> constru -> instanceObj = constru.get(constructorArgs);
				case Constructor<T> constru -> instanceObj = constru.get();
				case null -> throw new RuntimeException("没有选择构造器");
				case default -> throw new RuntimeException("方法未实现");
			}
		} else {
			if (this.constructor instanceof ConstructorArgs<T> constructorArgs) {
				instanceObj = constructorArgs.get(this.constructorArgs);
			} else if (this.constructor instanceof Constructor<T> constructor) {
				instanceObj = constructor.get();
			} else {
				throw new RuntimeException("没有选择构造器");
			}
		}
		final T instance = instanceObj;
		Injects.forEach(inject -> inject.accept(instance));
		return instanceObj;
	}
}
