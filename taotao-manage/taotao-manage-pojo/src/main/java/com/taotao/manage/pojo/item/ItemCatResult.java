package com.taotao.manage.pojo.item;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 艾泽拉斯国家地理
 * @create 2017/12/20
 */
public class ItemCatResult {

    private List<ItemCatData> itemCats = new ArrayList<ItemCatData>();

    public List<ItemCatData> getItemCats() {
        return itemCats;
    }

    public void setItemCats(List<ItemCatData> itemCats) {
        this.itemCats = itemCats;
    }
}
