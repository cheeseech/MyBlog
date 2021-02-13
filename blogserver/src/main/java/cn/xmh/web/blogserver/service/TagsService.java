package cn.xmh.web.blogserver.service;

import cn.xmh.web.blogserver.model.Tags;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Xmh
 * @date 2020/7/29 15:42
 */
public interface TagsService {

    /**
     * 获取所有标签集合
     * @return 标签集合
     */
    List<Tags> getAllTags();

    /**
     * 根据ID删除一个标签
     * @param tagId 标签ID
     */
    void deleteByTagId(Long tagId);

    /**
     * 添加一个标签
     * @param tags 标签信息
     */
    void insertTag(Tags tags) throws SQLException;

    /**
     * 更新一个标签
     * @param tags 标签信息
     */
    void updateTagById(Tags tags) throws SQLException;

    /**
     * 根据标签名称查找
     * @param tagName 标签名
     * @return 标签集合
     */
    Tags getTagsName(String tagName);

    /**
     * 根据文章ID获取标签集合
     * @param articleId 文章ID
     * @return 标签集合
     */
    List<Tags> getTagsByArticleId(Long articleId);

    /**
     * 获取标签及标签文章数
     * @return
     */
    List<Map<String,String>> getTagsAndCount();

    /**
     * 从大到小获取标签及标签文章数
     * @return 标签及标签文章数
     */
    List<Map<String, Object>> getTagsAndCountOrder();

    /**
     * 根据名称模糊查找标签及标签文章数
     * @param tagName
     * @return
     */
    List<Map<String, String>> getTagsAndCountLike(String tagName);

}
