package cn.xmh.web.blogserver.service.impl;

import cn.xmh.web.blogserver.mapper.CategoryMapper;
import cn.xmh.web.blogserver.model.Category;
import cn.xmh.web.blogserver.model.Tags;
import cn.xmh.web.blogserver.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    public void insertCategory(Category category) {
        int i= categoryMapper.insertCategory(category);
        if (i != 1) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void deleteByCategoryId(Long cateId) {
        int i= categoryMapper.deleteByCategoryId(cateId);
        if (i != 1) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateByCategoryId(Category category) {
        int i= categoryMapper.updateByCategory(category);
        if (i != 1) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public List<Map<String, Long>> getCateArticleCount() {
        List<Map<String, Long>> map=categoryMapper.getCateArticleCount();
        System.out.println(map);
        if(map.isEmpty()){
            throw  new NullPointerException();
        }
        return map;
    }
}
