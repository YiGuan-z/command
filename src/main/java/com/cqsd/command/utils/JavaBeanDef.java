package com.cqsd.command.utils;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author caseycheng
 * @date 2022/11/7-21:31
 **/
public class JavaBeanDef<T> {
	private final static Unsafe unsafe = UnsafeUtil.getUnsafe();
	private final Class<T> clazz;
	private  T value;
	private final Map<Field, Long> filedMap;
	private final Map<String, Method> methodMap;
	
	public JavaBeanDef(Class<T> clazz,boolean newInstance) {
		this.clazz = clazz;
		this.filedMap = (Arrays.stream(clazz.getDeclaredFields())
				.collect(Collectors.toMap((Field v) -> v, unsafe::objectFieldOffset)));
		this.methodMap = Arrays.stream(clazz.getDeclaredMethods())
				.collect(Collectors.toMap(Method::getName, method -> method));
		if (newInstance){
			this.value = newInstance();
		}
	}
	public JavaBeanDef<T> setValue(T value) {
		this.value = value;
		return this;
	}
	
	/**
	 * 检查一个字段是否已解析
	 *
	 * @param field 字段
	 * @return 是否解析
	 */
	
	public boolean fieldConcat(Field field) {
		return filedMap.containsKey(field);
	}
	
	
	
	public boolean methmodConcat(String methmodName) {
		return methodMap.containsKey(methmodName);
	}
	
	/**
	 * 直接创建一个对象，绕过构造函数和类型检查
	 *
	 * @return 一个空的实例对象
	 */
	public T newInstance() {
		return newInstance(clazz);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<? extends T> clazz) {
		try {
			return (T) unsafe.allocateInstance(clazz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 源对象 偏移量 值对象
	 *
	 * @param resource 源对象
	 * @param field    字段
	 * @param value    值对象
	 */
	private void setFieldValue(Object resource, Field field, Object value) throws DangerousoperationException {
		hit(field);
		unsafe.putObject(resource, filedMap.get(field), value);
	}
	
	/**
	 * 获取字段值
	 *
	 * @param resource 源对象
	 * @param field    字段
	 * @return 源对象的字段值
	 * @throws DangerousoperationException 未解析字段异常
	 */
	private Object getFieldValue(Object resource, Field field) throws DangerousoperationException {
		hit(field);
		return unsafe.getObject(resource, filedMap.get(field));
	}
	
	/**
	 * 设置一个字段值
	 *
	 * @param field 字段
	 * @param value 值
	 * @throws DangerousoperationException 未解析字段异常
	 */
	public void setFieldValue(Field field, Object value) throws DangerousoperationException {
		setFieldValue(this.value, field, value);
	}
	
	/**
	 * 获取一个字段值
	 *
	 * @param field 字段
	 * @return 该字段的值
	 * @throws DangerousoperationException 未解析字段异常
	 */
	public Object getFieldValue(Field field) throws DangerousoperationException {
		return getFieldValue(this.value, field);
	}
	
	/**
	 * 原子性操作，给对象的int设置一个值
	 * 包装类型请使用getAndSetObject
	 *
	 * @param field    字段
	 * @param newValue 该字段的值
	 * @return 更新前的字段值
	 * @throws DangerousoperationException 未解析字段异常
	 */
	public int getAndSetInt(Field field, int newValue) throws DangerousoperationException {
		return getAndSetInt(this.value, field, newValue);
	}
	
	/**
	 * 原子性操作，给对象的Long设置一个值
	 *
	 * @param field    字段
	 * @param newValue 该字段的值
	 * @return 更新前的字段值
	 * @throws DangerousoperationException 未解析字段异常
	 */
	public long getAndSetLong(Field field, long newValue) throws DangerousoperationException {
		return getAndSetLong(this.value, field, newValue);
	}
	
	/**
	 * 原子性操作，给对象的int增加一个值
	 *
	 * @param field 字段
	 * @param delta 该字段的值
	 * @return 更新前的字段值
	 * @throws DangerousoperationException 未解析字段异常
	 */
	public int getAndAddInt(Field field, int delta) throws DangerousoperationException {
		return getAndAddInt(this.value, field, delta);
	}
	
	/**
	 * 原子性操作，给对象的long增加一个值
	 *
	 * @param field 字段
	 * @param delta 加上的值
	 * @return 更新前的值
	 * @throws DangerousoperationException 未解析字段异常
	 */
	public long getAndAddLong(Field field, long delta) throws DangerousoperationException {
		return getAndAddLong(this.value, field, delta);
	}
	
	/**
	 * 原子性操作，给字段设置并更新一个对象
	 *
	 * @param field 字段
	 * @param delta 新对象
	 * @return 更新前的对象
	 * @throws DangerousoperationException 未解析字段异常
	 */
	public Object getAndSetObject(Field field, Object delta) throws DangerousoperationException {
		return getAndSetObject(this.value, field, delta);
	}
	
	//————————————————————————————————————Methmods————————————————————————————————————————————//
	
	/**
	 * 通过methmodName获取一个方法
	 *
	 * @param methodName 方法名
	 * @return 方法
	 * @throws DangerousoperationException 未命中方法
	 */
	private Method getMethod(String methodName) throws DangerousoperationException {
		assertsTrue(methmodConcat(methodName), "未命中目标方法");
		return methodMap.get(methodName);
	}
	
	/**
	 * 执行一个方法
	 *
	 * @param methodName 方法名
	 * @param args       方法参数名
	 * @return 方法执行结果
	 * @throws DangerousoperationException 危险的内存操作
	 * @throws InvocationTargetException   调用目标异常
	 * @throws IllegalAccessException      非法访问异常
	 */
	private Object invoke(String methodName, Object... args) throws DangerousoperationException, InvocationTargetException, IllegalAccessException {
		final var method = getMethod(methodName);
		method.setAccessible(true);
		return method.invoke(this.value, args);
	}
	
	private int getAndSetInt(Object resource, Field field, int newValue) throws DangerousoperationException {
		hit(field);
		assertsTrue(field.getType() != int.class, "包装类型请使用getAndSetObject");
		return unsafe.getAndSetInt(resource, filedMap.get(field), newValue);
	}
	
	private long getAndSetLong(Object resource, Field field, long newValue) throws DangerousoperationException {
		hit(field);
		assertsTrue(field.getType() != long.class, "包装类型请使用getAndSetObject");
		return unsafe.getAndSetLong(resource, filedMap.get(field), newValue);
	}
	
	private long getAndAddLong(Object resource, Field field, long delta) throws DangerousoperationException {
		hit(field);
		assertsTrue(field.getType() != long.class, "包装类型请使用getAndSetObject");
		return unsafe.getAndAddLong(resource, filedMap.get(field), delta);
	}
	
	private int getAndAddInt(Object resource, Field field, int delta) throws DangerousoperationException {
		hit(field);
		assertsTrue(field.getType() != int.class, "包装类型请使用getAndSetObject");
		return unsafe.getAndAddInt(resource, filedMap.get(field), delta);
	}
	
	private Object getAndSetObject(Object resource, Field field, Object newValue) throws DangerousoperationException {
		hit(field);
		return unsafe.getAndSetObject(resource, filedMap.get(field), newValue);
	}
	
	private void hit(Field field) throws DangerousoperationException {
		assertsTrue(fieldConcat(field), "未命中目标偏移量，无法继续");
	}
	
	private static void assertsTrue(boolean expression, String message) throws DangerousoperationException {
		if (!expression) {
			throw new DangerousoperationException(message);
		}
	}
	
	public Object getValue() {
		return value;
	}
	
	public static long allocateMemory(long bytes) {
		return unsafe.allocateMemory(bytes);
	}
	
	public static class DangerousoperationException extends Exception {
		/**
		 * Constructs a new exception with the specified detail message.  The
		 * cause is not initialized, and may subsequently be initialized by
		 * a call to {@link #initCause}.
		 *
		 * @param message the detail message. The detail message is saved for
		 *                later retrieval by the {@link #getMessage()} method.
		 */
		public DangerousoperationException(String message) {
			super(message);
		}
		
		/**
		 * Constructs a new exception with the specified detail message and
		 * cause.  <p>Note that the detail message associated with
		 * {@code cause} is <i>not</i> automatically incorporated in
		 * this exception's detail message.
		 *
		 * @param message the detail message (which is saved for later retrieval
		 *                by the {@link #getMessage()} method).
		 * @param cause   the cause (which is saved for later retrieval by the
		 *                {@link #getCause()} method).  (A {@code null} value is
		 *                permitted, and indicates that the cause is nonexistent or
		 *                unknown.)
		 * @since 1.4
		 */
		public DangerousoperationException(String message, Throwable cause) {
			super(message, cause);
		}
	}
	
}
