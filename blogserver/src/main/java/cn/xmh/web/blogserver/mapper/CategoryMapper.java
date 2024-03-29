package cn.xmh.web.blogserver.mapper;

import cn.xmh.web.blogserver.model.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Xmh
 * @date 2020/7/28 22:29
 * 专栏持久层
 */
@Mapper
public interface CategoryMapper {

    /**
     * 获取所有专栏集合
     *
     * @return 专栏集合
     */
    List<Category> listAll();

    /**
     * 根据专栏名获取专栏信息
     *
     * @param cateName 专栏名
     * @return 专栏信息
     */
    Category getByName(@Param("cate_name") String cateName);

    /**
     * 插入专栏信息
     *
     * @param category 专栏信息
     * @return 受影响的行
     */
    int save(Category category);

    /**
     * 根据专栏ID删除专栏
     *
     * @param cateId 专栏ID
     * @return 受影响的行
     */
    int removeById(@Param("cate_id") Long cateId);

    /**
     * 更新专栏信息
     *
     * @param category 专栏信息
     * @return 受影响的行
     */
    int update(Category category);

    /**
     * 获取专栏名及专栏的文章数
     *
     * @return 专栏名及专栏的文章数
     */
    List<Map<String, Long>> listArticleCount();

    /**
     * 获取专栏名、专栏概述、创建时间、浏览量、点赞数以及评论数
     *
     * @return 专栏分析信息
     */
    List<Map<String, Object>> listAnalysis();

    /**
     * 获取专栏数量
     *
     * @return 专栏数量
     */
    Integer getCategoryCount();
}
