package cn.xmh.web.blogserver.mapper;

import cn.xmh.web.blogserver.model.Tags;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Xmh
 * @date 2020/7/29 10:29
 */
@Mapper
public interface ArticleTagsMapper {

    /**
     * 关联文章标签
     * @param tagId 标签ID
     * @param articleId 文章ID
     * @return 受影响的行
     */
    int insertArticleTags(@Param("tag_id") Long tagId,@Param("article_id") Long articleId);

    /**
     * 根据文章ID更新文章标签集合
     * @param tagId 标签ID
     * @param articleId 文章ID
     * @return 受影响的行
     */
    int updateArticleTags(@Param("tag_id") Long tagId,@Param("article_id") Long articleId);

    /**
     * 根据文章ID删除文章标签关联
     * @param articleId 文章ID
     * @return 受影响的行
     */
    int deleteByArticleId(@Param("article_id") Long articleId);

    /**
     * 根据标签ID删除文章标签关联
     * @param tagId 标签ID
     * @return 受影响的行
     */
    int deleteByTagId(@Param("tag_id") Long tagId);

    /**
     * 根据文章ID获取标签集合
     * @param articleId 文章ID
     * @return 标签集合
     */
    List<Tags> getTagsByArticleId(@Param("article_id") Long articleId);
}
