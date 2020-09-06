package cn.xmh.web.blogserver.mapper;

import cn.xmh.web.blogserver.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.awt.event.MouseAdapter;
import java.util.List;
import java.util.Map;

/**
 * @author Xmh
 * @date 2020/7/28 22:29
 */
@Mapper
public interface ArticleMapper {

    /**
     * 获取所有文章
     * @return 文章集合
     */
    List<Article> getAllArticle();

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
    int deleteByArticleId(@Param("articleId") Long articleId);

    /**
     * 更新文章信息
     * @param articleId 文章ID
     * @param article 文章信息
     * @return 受影响的行
     */
    int updateByArticleId(@Param("articleId") Long articleId,Article article);

    /**
     *  更新文章状态
     * @param articleState 文章状态
     * @param articleId 文章ID
     * @return 受影响的行
     */
    int resetArticleState(@Param("articleState") Integer articleState,@Param("articleId") Long articleId);

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
    List<Article> getTypeArticle(@Param("typeName") String typeName);

    /**
     * 获取某标签下的所有文章
     * @param tagId
     * @return
     */
    List<Article> getArticleByTagId(@Param("tagId") Long tagId);

    /**
     * 获取某专栏下的所有文章
     * @param cateName
     * @return
     */
    List<Article> getArticleByCateName(@Param("cateName") String cateName);

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
}
