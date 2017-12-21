package com.taotao.manage.service.content;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.mapper.content.ContentMapper;
import com.taotao.manage.pojo.content.Content;
import com.taotao.manage.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 艾泽拉斯国家地理
 * @create 2017/12/21
 */
@Service
public class ContentService extends BaseService<Content> {
    @Autowired
    ContentMapper contentMapper;

    public EasyUIResult queryEasyUIResultByCategoryId(Long categoryId, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<Content> list = contentMapper.queryContentByCategoryId(categoryId);
        PageInfo<Content> pageInfo = new PageInfo<>(list);
        EasyUIResult easyUIResult = new EasyUIResult();
        easyUIResult.setRows(pageInfo.getList());
        easyUIResult.setTotal(pageInfo.getTotal());
        return easyUIResult;
    }
}
