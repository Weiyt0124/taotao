package com.taotao.manage.service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService extends BaseService<Item> {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescService itemDescService;

    /**
     * 保存商品和商品描述
     *
     * @param item
     * @param desc
     * @return
     */
    public Boolean saveItemAndDesc(Item item, String desc) {

        item.setId(null);
        item.setStatus(1);
        //保存商品数据
        Integer saveItemCount = super.save(item);
        if (saveItemCount.intValue() == 0) {
            //数据插入失败  但是没有任何异常
            throw new RuntimeException(" 新增商品信息失败，但是没有异常。Item =" + item);
        }
        Integer saveDescCount = 0;
        ItemDesc itemDesc = new ItemDesc();
        if (saveItemCount.intValue() == 1) {
            itemDesc.setItemId(item.getId());
            itemDesc.setItemDesc(desc);
            saveDescCount = this.itemDescService.save(itemDesc);
        }
        if (saveDescCount.intValue() == 0) {
            //数据插入失败  但是没有任何异常
            throw new RuntimeException(" 新增商品描述信息失败，但是没有异常。ItemDesc =" + itemDesc);
        }
        if (saveItemCount.intValue() == 1 && saveDescCount.intValue() == 1) {
            return true;
        }
        return false;
    }


    public EasyUIResult queryPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(Item.class);
        example.setOrderByClause("updated desc");
        List<Item> items = itemMapper.selectByExample(example);
        PageInfo<Item> page = new PageInfo<>(items);
        return new EasyUIResult(page.getTotal(), items);
    }

    public boolean updateItem(Item item, String desc) {
        item.setStatus(null);
        Integer num = super.update(item);
        if (num != 1) {
            throw new RuntimeException("修改商品失败,没发生异常:" + item + "---" + desc);
        }
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        Integer descNum = itemDescService.update(itemDesc);
        if (descNum != 1) {
            throw new RuntimeException("修改商品失败,没发生异常:" + itemDesc);
        }
        return true;
    }
}
