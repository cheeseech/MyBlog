package cn.xmh.web.blogserver.service.impl;

import cn.xmh.web.blogserver.config.PageUtils;
import cn.xmh.web.blogserver.mapper.CategoryMapper;
import cn.xmh.web.blogserver.model.Category;
import cn.xmh.web.blogserver.model.PageRequest;
import cn.xmh.web.blogserver.model.PageResult;
import cn.xmh.web.blogserver.model.Tags;
import cn.xmh.web.blogserver.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
        //获取所有专栏
        List<Category> category= categoryMapper.getAllCategory();
        if(category.isEmpty()){
            throw  new NullPointerException();
        }

        return category;
    }

    @Override
    public Category getByCategoryName(String cateName) {
        //根据专栏名获取专栏
        Category category= categoryMapper.getByCategoryName(cateName);
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
        //添加创建时间
        category.setCreateTime(new Date());
        int i= categoryMapper.insertCategory(category);
        if (i != 1) {
            throw new SQLException();
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void deleteByCategoryId(Long cateId) throws SQLException {
        //删除专栏
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
            //当表单ID等于根据专栏名获取的id时表示只更改了专栏概述
            //否则表示专栏名重复
            if(!category.getCateId().equals(checkCategory.getCateId())){
                throw new IllegalArgumentException();
            }
        }

        //更新专栏
        int i= categoryMapper.updateByCategory(category);
        if (i != 1) {
            throw new SQLException();
        }
    }

    @Override
    public List<Map<String, Long>> getCateArticleCount() {
        //获取专栏文章数
        List<Map<String, Long>> map=categoryMapper.getCateArticleCount();
        if(map.isEmpty()){
            throw  new NullPointerException();
        }

        return map;
    }

    @Override
    public PageResult getCateAnalysis(PageRequest pageRequest) {
        int pageNum=pageRequest.getPageNum();
        int pageSize=pageRequest.getPageSize();
        //设置页码以及长度
        PageHelper.startPage(pageNum,pageSize);

        //获取专栏名、专栏概述、创建时间、浏览量、点赞数以及评论数
        List<Map<String, Object>> category=categoryMapper.getCateAnalysis();
        //专栏数据判空
        if(category.isEmpty()){
            throw new NullPointerException();
        }

        //调用分页工具类完成分页信息的封装
        return PageUtils.getPageResult(pageRequest, new PageInfo<>(category));
    }
}
