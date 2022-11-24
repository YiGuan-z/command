package com.cqsd.command.service.base;

import com.cqsd.command.mapper.BaseMapper;
import com.cqsd.command.utils.Assert;

import java.util.List;
import java.util.Map;

/**
 * @author caseycheng
 * @date 2022/11/22-21:44
 **/
public class BaseServiceImpl<T,I extends BaseMapper<T>>
		implements BaseService<T,I>{
	protected final I baseMapper;
	public BaseServiceImpl(I baseMapper) {
		this.baseMapper=baseMapper;
	}
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		Assert.requireNotNull(id,"主键不能为空");
		return baseMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int updateByPrimaryKey(T record) {
		Assert.requireNotNull(record,"更新内容不能为空");
		return baseMapper.updateByPrimaryKey(record);
	}
	
	@Override
	public int insertRecord(T record) {
		Assert.requireNotNull(record,"插入内容不能为空");
		return baseMapper.insertRecord(record);
		
	}
	
	@Override
	public List<T> selectForList(Map<String, Object> consumer) {
		Assert.requireNotNullMap(consumer);
		return baseMapper.selectForList(consumer);
	}
	
	@Override
	public T selectByPrimaryKey(Long id) {
		Assert.requireNotNull(id);
		return baseMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public void deleteByPrimaryKeys(List<Long> ids) {
		Assert.requireNotNullList(ids);
		baseMapper.deleteByPrimaryKeys(ids);
	}
}
