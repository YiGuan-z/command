package com.cqsd.command.service.base;

import com.cqsd.command.mapper.BaseMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author caseycheng
 * @date 2022/11/22-21:43
 **/
public interface BaseService<T,I extends BaseMapper<T>> {
	int deleteByPrimaryKey(Long id);
	int updateByPrimaryKey(T record);
	int insertRecord(T record);
	List<T> selectForList(Map<String,Object> consumer);
	T selectByPrimaryKey(Long id);
	
	void deleteByPrimaryKeys(List<Long> ids);
}
