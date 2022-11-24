package com.cqsd.command.mapper;

import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {
	int deleteByPrimaryKey(Long id);
	int updateByPrimaryKey(T record);
	int insertRecord(T record);
	List<T> selectForList(Map<String,Object> params);
	T selectByPrimaryKey(Long id);
	
	void deleteByPrimaryKeys(List<Long> ids);
}
