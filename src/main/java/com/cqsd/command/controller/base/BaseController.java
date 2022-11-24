package com.cqsd.command.controller.base;

import com.cqsd.command.mapper.BaseMapper;
import com.cqsd.command.result.Page;
import com.cqsd.command.result.PageObject;
import com.cqsd.command.service.base.BaseService;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author caseycheng
 * @date 2022/11/22-21:43
 **/
abstract public class BaseController<T, I extends BaseService<T, BaseMapper<T>>>
		extends Page {
	protected final I service;
	
	public BaseController(I service) {
		this.service = service;
	}
	
	public PageObject listData(HashMap<String, Object> params) {
		final var list = service.selectForList(params);
		return success(list);
	}
	
	public PageObject deleteByIds(ArrayList<Long> ids) {
		service.deleteByPrimaryKeys(ids);
		return success();
	}
	
	public PageObject updateItem(T record) {
		service.updateByPrimaryKey(record);
		return success(record);
	}
	public PageObject saveReord(T record){
		service.insertRecord(record);
		return success(record);
	}
	
	
}
