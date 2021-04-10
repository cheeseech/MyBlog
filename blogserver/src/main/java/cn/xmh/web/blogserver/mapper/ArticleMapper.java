package cn.xmh.web.blogserver.mapper;

import cn.xmh.web.blogserver.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Xmh
 * @date 2020/7/28 22:29
 * 文章持久层
 */
@Mapper
public interface ArticleMapper {

    /**
     * 查询所有用户
     * @return 文章集合
     */
    List<Article> listByPage();

    /**
     * 获取已删除状态的文章
     * @return 文章集合
     */
    List<Article> listByDelete();

    /**
     * 获取未删除状态的文章
     * @return 文章集合
     */
    List<Article> listByNotDelete();

    /**
     * 根据标题模糊查询文章信息
     * @param title 标题
     * @return 文章集合
     */
    List<Article> listLikeByTitle(@Param("title") String title);

    /**
     * 根据标题查找文章
     * @param title
     * @return
     */
    List<Article> listByTitle(String title);

    /**
     *  插入一篇文章
     * @param article 文章信息
     * @return 受影响的行
     */
    int save(Article article);

    /**
     *  根据文章ID删除文章信息
     * @param articleId 文章ID
     * @return 受影响的行
     */
    int removeById(@Param("article_id") Long articleId);

    /**
     * 更新一篇文章
     * @param article 文章信息
     * @return 受影响的行
     */
    int updateById(Article article);

    /**
     *  更新文章状态
     * @param articleState 文章状态
     * @param articleId 文章ID
     * @param time 更新时间
     * @return 受影响的行
     */
    int updateState(@Param("article_state") Integer articleState, @Param("article_id") Long articleId, Date time);

    /**
     * 获取最新推荐五篇标题及ID
     * @return
     */
    List<Map<String,String>> listRecommendByTop5();

    /**
     * 获取最新四篇标题及ID
     * @return
     */
    List<Map<String,String>> listNewByTop4();

    /**
     * 获取某标签下的所有文章
     * @param tagId 标签ID
     * @param title 标题关键字
     * @return 文章集合
     */
    List<Article> listByTagId(@Param("tag_id") Long tagId, String title);

    /**
     * 获取某专栏下的所有文章
     * @param cateName 专栏名
     * @param title 文章标题
     * @return 文章集合
     */
    List<Article> listByCateNameAndTitle(@Param("cate_name") String cateName, String title);

    /**
     * 获取每年的文章数量
     * @return 年份+文章数量
     */
    List<Map<String,Object>> listYearsCountArticle();

    /**
     * 获取某年中月份及文章数量
     * @param year 年份
     * @return 月份及文章数量
     */
    List<Map<String,Object>> listMonthsByYear(@Param("year") String year);

    /**
     * 根据年份、月份获取文章标题即发布日期
     * @param year 年份
     * @param month 月份
     * @return 文章信息
     */
    List<Map<String, Object>> listInfoByMonthYear(@Param("year") String year, @Param("month") String month);

    /**
     * 获取总文章数、总浏览量、总点赞数以及总评论数
     * @return 文章总信息集合
     */
    Map<String,String> getTotalData();

    /**
     * 根据id获取文章信息
     * @param articleId 文章ID
     * @return 文章信息
     */
    Article getById(@Param("article_id") Long articleId);

    /**
     * 获取文章总数量
     * @return 总文章数量
     */
    Integer getCountArticle();

    /**
     * 获取所有已发布状态文章id集合
     * @return 文章ID集合
     */
    List<Long> listByPublish();

    /**
     * 删除近一个月内为已删除状态的文章
     * @param start 开始时间
     * @param end 结束时间
     * @return 删除的文章数
     */
    int recycleByDeleteInMonth(Date start, Date end);

    /**
     * 根据文章ID获取文章详细ID
     * @param articleId 文章ID
     * @return 文章详细信息
     */
    Map<String, Object> getInfoById(@Param("article_id") Long articleId);

    /**
     * 根据文章ID获取文章详细信息以及markdown数据
     * @param articleId 文章ID
     * @return 文章详细信息
     */
    Map<String, Object> getMarkdownById(@Param("article_id") Long articleId);

    /**
     * 更新文章浏览量
     * @param articleId 文章ID
     * @return 受影响的行
     */
    int updateViews(@Param("article_id") Long articleId);

    /**
     * 更新文章评论数
     * @param articleId 文章ID
     * @param commentsLen 评论数
     * @return 受影响的行
     */
    int updateComments(@Param("article_id") Long articleId, int commentsLen);
}
