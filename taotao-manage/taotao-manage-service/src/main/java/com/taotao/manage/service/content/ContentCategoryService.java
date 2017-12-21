package com.taotao.manage.service.content;

import com.taotao.manage.mapper.content.ContentCategoryMapper;
import com.taotao.manage.pojo.content.ContentCategory;
import com.taotao.manage.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 艾泽拉斯国家地理
 * @create 2017/12/20
 */
@Service
public class ContentCategoryService extends BaseService<ContentCategory> {
    @Autowired
    ContentCategoryMapper contentCategoryMapper;

    public Boolean addContentCategory(ContentCategory contentCategory) {
        contentCategory.setId(null);
        contentCategory.setIsParent(false);
        contentCategory.setSortOrder(1);
        contentCategory.setStatus(1);
        Integer param = super.save(contentCategory);
        if (param == 0) {
            throw new RuntimeException("新增内容类目失败，但是没有异常 ：ContentCategory= " + contentCategory);
        }
        ContentCategory parent = super.queryById(contentCategory.getParentId());
        if (!parent.getIsParent()) {
            parent.setIsParent(true);
            super.update(parent);
        }
        return true;
    }

    public boolean deleteContentCategory(ContentCategory contentCategory) {
        ContentCategory record = new ContentCategory();
        //先看自己有没有兄弟节点
        record.setParentId(contentCategory.getParentId());
        //兄弟节点
        List<ContentCategory> broList = super.queryListByWhere(record);
        if (broList.size() == 1 && broList != null) {
            //如果没有兄弟节点,则把父节点IsParent设置为false
            ContentCategory parent = new ContentCategory();
            parent.setId(contentCategory.getParentId());
            parent.setIsParent(false);
            super.update(parent);
            contentCategory = broList.get(0);
        } else {
            //有兄弟节点，不用修改父节点，去数据库查出自己
            contentCategory = super.queryById(contentCategory.getId());
        }
        if (contentCategory.getIsParent()) {
            ArrayList<Object> ids = new ArrayList<>();
            queryChildrenIds(contentCategory.getId(), ids);
            ids.add(contentCategory.getId());
            Integer deleteCount = super.deleteByIds(ContentCategory.class, "id", ids);
            if (deleteCount < 1) {
                throw new RuntimeException("删除商品类目失败 ： contentCategory=" + contentCategory);
            }
        } else {
            Integer deleteCount = super.deleteById(contentCategory.getId());
            if (deleteCount < 1) {
                throw new RuntimeException("删除商品类目失败 ： contentCategory=" + contentCategory);
            }
        }
        return true;
    }

    private void queryChildrenIds(Long id, ArrayList<Object> ids) {
        ContentCategory cc = new ContentCategory();
        cc.setParentId(id);
        List<ContentCategory> ccList = super.queryListByWhere(cc);
        if (!ccList.isEmpty()) {
            for (ContentCategory child : ccList) {
                ids.add(child.getId());
                if (child.getIsParent()) {
                    queryChildrenIds(child.getId(), ids);
                }
            }
        }
    }
}
