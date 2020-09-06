package cn.xmh.web.blogserver.service;

import cn.xmh.web.blogserver.model.Category;
import cn.xmh.web.blogserver.model.Tags;
import org.apache.ibatis.annotations.Param;

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
     *  根据专栏ID获取专栏信息
     * @param cateId 专栏ID
     * @return 专栏信息
     */
    Category getByCategoryId(Long cateId);

    /**
     * 插入专栏信息
     * @param category 专栏信息
     */
    void insertCategory(Category category);

    /**
     *  根据专栏ID删除专栏
     * @param cateId 专栏ID
     */
    void deleteByCategoryId(Long cateId);

    /**
     * 根据专栏ID更新专栏信息
     * @param category 专栏信息
     */
    void updateByCategoryId(Category category);

    /**
     * 获取专栏名及专栏的文章数
     * @return 专栏名及专栏的文章数
     */
    List<Map<String, Long>> getCateArticleCount();

}
