package com.taotao.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.BasePojo;

public class BaseService<T extends BasePojo> {

	/**
	 * 公用的Service 
	 * 封装通用的Service层CRUD 方法代码
	 */
	
	/**
	 * 谁调用 谁实现
	 * @return
	 */
//	public abstract Mapper<T> getMapper();
	@Autowired
	public Mapper<T> mapper;
	/**
	 * 根据主键id查询单个对象
	 * @param id
	 * @return
	 */
	public T queryById(Long id){
		return this.mapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 查询所有对象   返回List集合 
	 * 默认根据更新时间倒叙排列
	 * @param clazz 返回对象的class
	 * @return
	 */
	public List<T> queryAll(Class clazz){
		Example example = new Example(clazz);
		example.setOrderByClause(" updated DESC");
		return this.mapper.selectByExample(example);
	}
	
	/**
	 * 根据条件返回单个对象
	 * 条件设置时必须保证最多只有一个结果
	 * @param record
	 * @return
	 */
	public T queryOne(T record){
		return this.mapper.selectOne(record);
	}
	
	/**
	 * 根据条件返回对象  
	 * @param record 
	 * @return
	 */
	public List<T> queryListByWhere(T record){
		
		return this.mapper.select(record);
	}
	
	/**
	 * 根据条件分页查询
	 * @param pageNum 页码
	 * @param pageSize 每页条数
	 * @param record 查询条件
	 * @return PageInfo 
	 */
	public PageInfo<T> queryPageListByWhere(Integer pageNum,Integer pageSize,T record){
		PageHelper.startPage(pageNum, pageSize);
		List<T> list = this.mapper.select(record);
		return new PageInfo<T>(list);
	}
	
	/**
	 * 新增对象
	 * @param record
	 * @return
	 */
	public Integer save(T record){
		record.setCreated(new Date());
		record.setUpdated(record.getCreated());
		return this.mapper.insertSelective(record);
	}
	
	/**
	 * 根据主键id更新单个对象
	 * @param record
	 * @return
	 */
	public Integer update(T record){
		record.setUpdated(new Date());
		return this.mapper.updateByPrimaryKeySelective(record);
	}
	/**
	 * 根据主键id删除单个对象
	 * @param id
	 * @return
	 */
	public Integer deleteById(Long id){
		return this.mapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 根据传入的属性和属性值ids执行in删除
	 * @param clazz
	 * @param property
	 * @param ids
	 * @return
	 */
	public Integer deleteByIds(Class clazz,String property,List<Object> ids){
		Example example = new Example(clazz);
		Criteria criteria = example.createCriteria();
		criteria.andIn(property, ids);
		return this.mapper.deleteByExample(example);
	}
	
	
	/**
	 * 根据record设置的属性值进行删除
	 * @param record
	 * @return
	 */
	public Integer deleteByWhere(T record){
		
		return this.mapper.delete(record);
	}
	
	
	
	
	
	
	
}
