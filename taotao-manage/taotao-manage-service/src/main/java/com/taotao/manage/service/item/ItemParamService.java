package com.taotao.manage.service.item;

import com.taotao.manage.mapper.ItemParamMapper;
import com.taotao.manage.pojo.ItemParam;
import com.taotao.manage.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 艾泽拉斯国家地理
 * @create 2017/12/14
 */
@Service
public class ItemParamService extends BaseService<ItemParam> {

    @Autowired
    private ItemParamMapper itemParamMapper;
}
