package cn.xmh.web.blogserver.service;

import cn.xmh.web.blogserver.model.Category;
import cn.xmh.web.blogserver.model.PageRequest;
import cn.xmh.web.blogserver.model.PageResult;
import cn.xmh.web.blogserver.model.Tags;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Xmh
 * @date 2020/7/29 15:42
 */
public interface CategoryService {

    /**
     *  获取所有专栏集合
     * @return 专栏集合
     */
    List<Category> getAllCategory();

    /**
     *  根据专栏名获取专栏信息
     * @param cateName 专栏名
     * @return 专栏信息
     */
    Category getByCategoryName(String cateName);

    /**
     * 添加一个专栏
     * @param category 专栏信息
     * @throws SQLException
     */
    void insertCategory(Category category) throws SQLException;

    /**
     *  根据专栏ID删除专栏
     * @param cateId 专栏ID
     * @throws SQLException
     */
    void deleteByCategoryId(Long cateId) throws SQLException;

    /**
     * 根据专栏ID更新专栏信息
     * @param category 专栏信息
     * @throws SQLException
     */
    void updateByCategoryId(Category category) throws SQLException;

    /**
     * 获取专栏名及专栏的文章数
     * @return 专栏名及专栏的文章数
     */
    List<Map<String, Long>> getCateArticleCount();

    /**
     * 分页获取专栏名、专栏概述、创建时间、文章数、浏览量、点赞数以及评论数
     * @param pageRequest 分页
     * @return 专栏分析信息
     */
    PageResult getCateAnalysis(PageRequest pageRequest);

}
