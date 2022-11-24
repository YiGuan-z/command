package com.cqsd.command.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Writer;

/**
 * @author caseycheng
 * @date 2022/11/4-10:37
 **/
public class JsonUtil {
	private static final ObjectMapper mapper = new ObjectMapper();
	
	public static String toJSON(Object obj) {
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	public static void writeJson(Writer writer,Object ret){
		try {
			writer.write(toJSON(ret));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
