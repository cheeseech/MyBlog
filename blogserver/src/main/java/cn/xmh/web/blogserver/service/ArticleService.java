package cn.xmh.web.blogserver.service;

import cn.xmh.web.blogserver.model.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Xmh
 * @date 2020/7/29 15:42
 */
public interface ArticleService {

    /**
     * 获取所有文章
     * @return 文章集合
     */
    List<Article> getAllArticle();

    /**
     * 获取已删除状态的文章
     * @return 文章集合
     */
    List<Article> getArticleByDe();

    /**
     * 获取未删除状态的文章
     * @return 文章集合
     */
    List<Article> getArticleByNoDe();

    /**
     * 根据标题模糊查询文章信息
     * @param title 标题
     * @return 文章集合
     */
    List<Article> getLikeTitleArticle(String title);

    /**
     *  插入文章信息
     * @param article 文章信息
     */
    void insertArticle(Article article);

    /**
     *  根据文章ID删除文章信息
     * @param articleId 文章ID
     */
    void deleteByArticleId(Long articleId);

    /**
     * 更新文章信息
     * @param articleId 文章ID
     * @param article 文章信息
     */
    void updateByArticleId(Long articleId,Article article);

    /**
     *  更新文章状态
     * @param articleState 文章状态
     * @param articleId 文章ID
     */
    void resetArticleState(Integer articleState,Long articleId);

    /**
     * 获取最新推荐五篇文章标题
     * @return
     */
    List<Map<String,String>> getNewRecommend();

    /**
     * 获取最新四篇文章
     * @return
     */
    List<Map<String,String>> getNewTitle();

    /**
     * 根据类型获取文章
     * @param typeName 类型名
     * @return 文章列表
     */
    List<Article> getTypeArticle(String typeName);

    /**
     * 获取某标签下的所有文章
     * @param tagId
     * @return
     */
    List<Article> getArticleByTagId(Long tagId);

    /**
     * 获取某专栏下的所有文章
     * @param cateName
     * @return
     */
    List<Article> getArticleByCateName(String cateName);

    /**
     * 获取每一年份下的文章数量，标题及发布时间信息
     * @return 每一年份下的文章数量，标题及发布时间信息
     */
    List<Map<String,Object>> getTimeLine();

    /**
     * 获取总文章数据
     * @return 总文章数据
     */
    Map<String ,String> getTotalData();
}
