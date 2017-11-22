package com.taotao.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;

@Service
public class ItemService extends BaseService<Item>{

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescService itemDescService;
	
	/**
	 * 保存商品和商品描述
	 * @param item
	 * @param desc
	 * @return
	 */
	public Boolean saveItemAndDesc(Item item,String desc){
		
		item.setId(null);
		item.setStatus(1);
		//保存商品数据 
		Integer saveItemCount = super.save(item);
		if(saveItemCount.intValue() == 0){
			//数据插入失败  但是没有任何异常 
			throw new RuntimeException(" 新增商品信息失败，但是没有异常。Item ="+item);
		}
		Integer saveDescCount = 0;
		ItemDesc itemDesc = new ItemDesc();
		if(saveItemCount.intValue() == 1){
			itemDesc.setItemId(item.getId());
			itemDesc.setItemDesc(desc);
			saveDescCount = this.itemDescService.save(itemDesc);
		}
		if(saveDescCount.intValue() == 0){
			//数据插入失败  但是没有任何异常 
			throw new RuntimeException(" 新增商品描述信息失败，但是没有异常。ItemDesc ="+itemDesc);
		}
		if(saveItemCount.intValue() == 1 && saveDescCount.intValue() == 1){
			return true;
		}
		return false;
	}
	
	

}
