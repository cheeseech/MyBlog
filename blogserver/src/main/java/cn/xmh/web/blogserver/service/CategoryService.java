package cn.xmh.web.blogserver.service;

import cn.xmh.web.blogserver.model.Category;
import cn.xmh.web.blogserver.model.PageRequest;
import cn.xmh.web.blogserver.model.PageResult;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Xmh
 * @date 2020/7/29 15:42
 * 专栏业务层
 */
public interface CategoryService {

    /**
     * 获取所有专栏集合
     *
     * @return 专栏集合
     */
    List<Category> listAll();

    /**
     * 添加一个专栏
     *
     * @param category 专栏信息
     * @throws SQLException
     */
    void save(Category category) throws SQLException;

    /**
     * 根据专栏ID删除专栏
     *
     * @param cateId 专栏ID
     * @throws SQLException
     */
    void removeById(Long cateId) throws SQLException;

    /**
     * 根据专栏ID更新专栏信息
     *
     * @param category 专栏信息
     * @throws SQLException
     */
    void updateById(Category category) throws SQLException;

    /**
     * 获取专栏名及专栏的文章数
     *
     * @return 专栏名及专栏的文章数
     */
    List<Map<String, Long>> listArticleCount();

    /**
     * 分页获取专栏名、专栏概述、创建时间、文章数、浏览量、点赞数以及评论数
     *
     * @param pageRequest 分页
     * @return 专栏分析信息
     */
    PageResult getAnalysis(PageRequest pageRequest);

    /**
     * 分页获取专栏数据
     *
     * @param pageRequest 统一分页请求对象
     * @return 专栏数据
     */
    PageResult getByPage(PageRequest pageRequest);

}
