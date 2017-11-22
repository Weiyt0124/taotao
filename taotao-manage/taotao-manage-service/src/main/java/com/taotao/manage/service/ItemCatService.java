package com.taotao.manage.service;

import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.pojo.ItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 艾泽拉斯国家地理
 * @create 2017/11/22
 */
@Service
public class ItemCatService {

    @Autowired
    ItemCatMapper itemCatMapper;

    public List<ItemCat> getItemCatById(Long id) {
        ItemCat itemCat = new ItemCat();
        itemCat.setParentId(id);
        List<ItemCat> result = itemCatMapper.select(itemCat);
        return result;
    }
}
