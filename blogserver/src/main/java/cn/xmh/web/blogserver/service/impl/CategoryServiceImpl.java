package cn.xmh.web.blogserver.service.impl;

import cn.xmh.web.blogserver.mapper.CategoryMapper;
import cn.xmh.web.blogserver.model.Category;
import cn.xmh.web.blogserver.model.Tags;
import cn.xmh.web.blogserver.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xmh
 * @date 2020/7/29 15:44
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;


    @Override
    public List<Category> getAllCategory() {
        List<Category> category= categoryMapper.getAllCategory();

        //判断集合是否为空
        if(category.isEmpty()){
            throw  new NullPointerException();
        }
        return category;
    }

    @Override
    public Category getByCategoryName(String cateName) {
        Category category= categoryMapper.getByCategoryName(cateName);
        if(category == null){
            throw  new NullPointerException();
        }
        return category;
    }

    @Override
    public Category getByCategoryId(Long cateId) {
        Category category= categoryMapper.getByCategoryId(cateId);
        if(category == null){
            throw  new NullPointerException();
        }
        return category;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void insertCategory(Category category) throws SQLException {

        //判断是否存在相同专栏名
        Category checkCategory=categoryMapper.getByCategoryName(category.getCateName());
        if(checkCategory!=null) {
            throw new IllegalArgumentException();
        }
        category.setCreateTime(new Date());
        int i= categoryMapper.insertCategory(category);
        if (i != 1) {
            throw new SQLException();
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void deleteByCategoryId(Long cateId) throws SQLException {
        int i= categoryMapper.deleteByCategoryId(cateId);
        if (i != 1) {
            throw new SQLException();
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateByCategoryId(Category category) throws SQLException {

        //判断是否存在相同专栏名
        Category checkCategory=categoryMapper.getByCategoryName(category.getCateName());
        if(checkCategory!=null){
            throw new IllegalArgumentException();
        }

        int i= categoryMapper.updateByCategory(category);
        if (i != 1) {
            throw new SQLException();
        }
    }

    @Override
    public List<Map<String, Long>> getCateArticleCount() {
        List<Map<String, Long>> map=categoryMapper.getCateArticleCount();
        if(map.isEmpty()){
            throw  new NullPointerException();
        }
        return map;
    }

    @Override
    public List<Map<String, Object>> getCateAnalysis() {
        List<Map<String, Object>> category=categoryMapper.getCateAnalysis();
        if(category.isEmpty()){
            throw new NullPointerException();
        }
        return category;
    }
}
