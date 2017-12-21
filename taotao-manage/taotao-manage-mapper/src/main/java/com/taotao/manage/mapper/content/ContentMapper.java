package com.taotao.manage.mapper.content;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.pojo.content.Content;

import java.util.List;

/**
 * @author 艾泽拉斯国家地理
 * @create 2017/12/21
 */
public interface ContentMapper extends Mapper<Content> {
    List<Content> queryContentByCategoryId(Long categoryId);
}
