package cn.xmh.web.blogserver.service.impl;

import cn.xmh.web.blogserver.mapper.CategoryMapper;
import cn.xmh.web.blogserver.model.Category;
import cn.xmh.web.blogserver.model.PageRequest;
import cn.xmh.web.blogserver.model.PageResult;
import cn.xmh.web.blogserver.service.CategoryService;
import cn.xmh.web.blogserver.utils.PageUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Xmh
 * @date 2020/7/29 15:44
 * 专栏业务层实现类
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;


    @Override
    public List<Category> listAll() {
        // 获取所有专栏
        List<Category> category = categoryMapper.listAll();
        // 专栏数据判空
        hasCategory(category);

        return category;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void save(Category category) throws SQLException {
        // 判断是否存在相同专栏名
        Category checkCategory = categoryMapper.getByName(category.getCateName());
        if (checkCategory != null) {
            throw new IllegalArgumentException();
        }

        // 添加创建时间
        category.setCreateTime(new Date());

        int i = categoryMapper.save(category);
        if (i != 1) {
            throw new SQLException();
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void removeById(Long cateId) throws SQLException {
        // 删除专栏
        int i = categoryMapper.removeById(cateId);
        if (i != 1) {
            throw new SQLException();
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateById(Category category) throws SQLException {
        // 判断是否存在相同专栏名
        Category checkCategory = categoryMapper.getByName(category.getCateName());
        if (checkCategory != null) {
            // 当表单ID等于根据专栏名获取的id时表示只更改了专栏概述
            // 否则表示专栏名重复
            if (!category.getCateId().equals(checkCategory.getCateId())) {
                throw new IllegalArgumentException();
            }
        }

        // 更新专栏
        int i = categoryMapper.update(category);
        if (i != 1) {
            throw new SQLException();
        }
    }

    @Override
    public List<Map<String, Long>> listArticleCount() {
        // 获取专栏文章数
        List<Map<String, Long>> map = categoryMapper.listArticleCount();
        // 专栏数据判空
        if (map.isEmpty()) {
            throw new NullPointerException();
        }

        return map;
    }

    @Override
    public PageResult getAnalysis(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        // 设置页码以及长度
        PageHelper.startPage(pageNum, pageSize);

        // 获取专栏名、专栏概述、创建时间、浏览量、点赞数以及评论数
        List<Map<String, Object>> category = categoryMapper.listAnalysis();
        // 专栏数据判空
        if (category.isEmpty()) {
            throw new NullPointerException();
        }

        return PageUtil.getPageResult(pageRequest, new PageInfo<>(category));
    }

    @Override
    public PageResult getByPage(PageRequest pageRequest) {
        int pageSize = pageRequest.getPageSize();
        int pageNum = pageRequest.getPageNum();
        // 设置页码及长度
        PageHelper.startPage(pageNum, pageSize);

        List<Category> categories = categoryMapper.listAll();
        // 专栏数据判空
        hasCategory(categories);

        return PageUtil.getPageResult(pageRequest, new PageInfo<>(categories));
    }

    /**
     * 判断专栏数据是否为空
     *
     * @param categories
     */
    private void hasCategory(List<Category> categories) {
        // 专栏数据判空
        if (categories.isEmpty()) {
            throw new NullPointerException();
        }
    }
}
