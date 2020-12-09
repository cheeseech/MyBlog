package cn.xmh.web.blogserver.mapper;

import cn.xmh.web.blogserver.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.awt.event.MouseAdapter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Xmh
 * @date 2020/7/28 22:29
 */
@Mapper
public interface ArticleMapper {

    /**
     * 分页查询用户
     * @return 文章集合
     */
    List<Article> getAllPage();

    /**
     * 获取删除状态的文章
     * @return 文章集合
     */
    List<Article> getArticleByDelete();

    /**
     * 获取未删除状态的文章
     * @return 文章集合
     */
    List<Article> getArticleByNotDelete();

    /**
     * 根据标题模糊查询文章信息
     * @param title 标题
     * @return 文章集合
     */
    List<Article> getLikeTitleArticle(@Param("title") String title);

    /**
     *  插入文章信息
     * @param article 文章信息
     * @return 受影响的行
     */
    int insertArticle(Article article);

    /**
     *  根据文章ID删除文章信息
     * @param articleId 文章ID
     * @return 受影响的行
     */
    int deleteByArticleId(@Param("article_id") Long articleId);

    /**
     * 更新文章信息
     * @param articleId 文章ID
     * @param article 文章信息
     * @return 受影响的行
     */
    int updateByArticleId(@Param("article_id") Long articleId,Article article);

    /**
     *  更新文章状态
     * @param articleState 文章状态
     * @param articleId 文章ID
     * @param time 更新时间
     * @return 受影响的行
     */
    int resetArticleState(@Param("article_state") Integer articleState,@Param("article_id") Long articleId,Date time);

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
    List<Article> getTypeArticle(@Param("type_name") String typeName);

    /**
     * 获取某标签下的所有文章
     * @param tagId 标签ID
     * @return
     */
    List<Article> getArticleByTagId(@Param("tag_id") Long tagId);

    /**
     * 获取某专栏下的所有文章
     * @param cateName
     * @return
     */
    List<Article> getArticleByCateName(@Param("cate_name") String cateName);

    /**
     * 获取每年的文章数量
     * @return 年份+文章数量
     */
    List<Map<String,Object>> getYearsCountArticle();

    /**
     * 获取某年中月份及文章数量
     * @param year 年份
     * @return 月份及文章数量
     */
    List<Map<String,Object>> getMonthsByYear(@Param("year") String year);

    /**
     * 根据年份、月份获取文章标题即发布日期
     * @param year 年份
     * @param month 月份
     * @return 文章信息
     */
    List<Map<String, Object>> getInfoByMonthYear(@Param("year") String year,@Param("month") String month);

    /**
     * 获取总文章数、总浏览量、总点赞数以及总评论数
     * @return 文章总信息集合
     */
    Map<String,String> getTotalData();

    /**
     * 根据文章id获取文章信息
     * @param articleId 文章ID
     * @return 文章信息
     */
    Article getArticleById(@Param("article_id") Long articleId);

    /**
     * 获取文章数量
     * @return 文章数量
     */
    Integer getArticleNum();

    /**
     * 获取所有已发布状态文章id集合
     * @return 文章ID集合
     */
    List<Long> getAllArticleId();

    /**
     * 删除近一个月内为已删除状态的文章
     * @param start 开始时间
     * @param end 结束时间
     * @return 删除的文章数
     */
    int recycleArticle(Date start,Date end);
}
