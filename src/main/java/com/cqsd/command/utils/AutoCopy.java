//package com.cqsd.command.utils;
//
//import org.springframework.beans.BeanUtils;
//
//import java.io.Serializable;
//
///**
// * @author caseycheng
// * @date 2022/11/5-10:25
// **/
//abstract public class AutoCopy {
//
//	public static <T extends Serializable> T of(Object resource, Class<T> clazz) {
//		try {
//			final var instance = JavaBeanDef.newInstance(clazz);
//			BeanUtils.copyProperties(resource, instance);
//			return instance;
//		} catch (Exception ignore) {
//			return null;
//		}
//	}
//}
