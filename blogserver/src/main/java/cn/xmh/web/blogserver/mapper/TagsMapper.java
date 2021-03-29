package cn.xmh.web.blogserver.mapper;

import cn.xmh.web.blogserver.model.Tags;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Xmh
 * @date 2020/7/28 22:29
 */
@Mapper
public interface TagsMapper {

    /**
     * 获取所有标签集合
     * @return 标签集合
     */
    List<Tags> getAllTags();

    /**
     * 根据ID删除一个标签
     * @param tagId 标签ID
     * @return 受影响的行
     */
    int deleteByTagId(@Param("tag_id") Long tagId);

    /**
     * 添加一个标签
     * @param tags 标签信息
     * @return 受影响的行
     */
    int insertTag(Tags tags);

    /**
     * 根据ID更新一个标签
     * @param tags 标签信息
     * @return 受影响的行
     */
    int updateTagById(Tags tags);

    /**
     * 根据标签名称模糊查找
     * @param tagName 标签名
     * @return 标签集合
     */
    Tags getTagsName(@Param("tag_name") String tagName);

    /**
     * 获取标签及标签文章数
     * @return 标签及标签文章数
     */
    List<Map<String,String>> getTagsAndCount();

    /**
     * 根据文章ID获取后台需要的标签集合
     * @param articleId 文章ID
     * @return 标签集合
     */
    List<Map<String, Object>> getTagsByArticleIdBuAdmin(@Param("article_id") Long articleId);

    /**
     * 从大到小获取标签及标签文章数
     * @return 标签及标签文章数
     */
    List<Map<String, Object>> getTagsAndCountOrder();

    /**
     * 根据标签名模糊获取标签及标签文章数
     * @param tagName
     * @return
     */
    List<Map<String, String>> getTagsAndCountLike(@Param("tag_name") String tagName);

    /**
     * 根据名称模糊搜索标签
     * @param input 名称
     * @return 标签集合
     */
    List<Tags> getLikeTags(String input);

    /**
     * 获取标签数量
     * @return 标签数量
     */
    Integer getTagsNum();

    /**
     * 根据标签名获取标签ID
     * @param tagName 标签名
     * @return
     */
    Long getIdByName(@Param("tag_name") String tagName);

}
