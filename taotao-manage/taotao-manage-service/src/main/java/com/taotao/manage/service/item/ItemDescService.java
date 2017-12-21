package com.taotao.manage.service.item;

import com.taotao.manage.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.manage.mapper.item.ItemDescMapper;
import com.taotao.manage.pojo.item.ItemDesc;

@Service
public class ItemDescService extends BaseService<ItemDesc> {

	@Autowired
	private ItemDescMapper itemDescMapper;

}
