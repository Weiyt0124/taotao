package com.taotao.manage.pojo.item;

import java.util.List;

/**
 * @author 艾泽拉斯国家地理
 * @create 2017/12/20
 */
public class ItemCatData {
    String url;
    String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<?> getItems() {
        return items;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }

    List<?> items;

}
