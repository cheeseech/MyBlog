package cn.xmh.web.blogserver.service;

import cn.xmh.web.blogserver.model.Article;
import cn.xmh.web.blogserver.model.PageRequest;
import cn.xmh.web.blogserver.model.PageResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Xmh
 * @date 2020/7/29 15:42
 * 文章业务层
 */
public interface ArticleService {

    /**
     * 分页查询文章接口
     * 这里统一封装了分页请求和结果，避免直接引入具体框架的分页对象, 如MyBatis或JPA的分页对象
     * 从而避免因为替换ORM框架而导致服务层、控制层的分页接口也需要变动的情况，替换ORM框架也不会
     * 影响服务层以上的分页接口，起到了解耦的作用
     *
     * @param pageRequest 自定义，统一分页查询请求
     * @return PageResult 自定义，统一分页查询结果
     */
    PageResult getByPage(PageRequest pageRequest);

    /**
     * 根据专栏名和标题分页模糊查询文章信息
     *
     * @param pageRequest 统一分页查询请求
     * @param cateName    专栏名
     * @param title       文章标题
     * @return 统一分页查询结果
     */
    PageResult getByCateNameAndTitleInPage(String cateName, String title, PageRequest pageRequest);

    /**
     * 根据标签名和标题分页模糊查询文章信息
     *
     * @param pageRequest 统一分页查询请求
     * @param tagName     标签名
     * @param title       文章标题
     * @return 统一分页查询结果
     */
    PageResult getByTagNameAndTitleInPage(String tagName, String title, PageRequest pageRequest);

    /**
     * 根据标题分页模糊查询文章信息
     *
     * @param pageRequest 统一分页查询请求
     * @param title       文章标题
     * @return 统一分页查询结果
     */
    PageResult getLikeTitleInPage(String title, PageRequest pageRequest);

    /**
     * 分页获取已删除状态的文章
     *
     * @param pageRequest 统一分页请求
     * @return 统一分页查询结果
     */
    PageResult getByDelete(PageRequest pageRequest);

    /**
     * 分页获取未删除状态的文章
     *
     * @param pageRequest 统一分页请求
     * @return 统一分页查询结果
     */
    PageResult getByNotDelete(PageRequest pageRequest);

    /**
     * 添加一篇文章
     *
     * @param article 文章信息
     */
    void saveArticle(Article article);

    /**
     * 根据文章ID删除一篇文章
     *
     * @param articleId 文章ID
     */
    void removeById(Long articleId);

    /**
     * 更新一篇文章
     *
     * @param articleId 文章ID
     * @param article   文章信息
     */
    void updateById(Long articleId, Article article);

    /**
     * 更新文章状态
     *
     * @param articleState 文章状态
     * @param articleId    文章ID
     */
    void updateState(Integer articleState, Long articleId);

    /**
     * 获取最新推荐五篇文章标题
     *
     * @return
     */
    List<Map<String, String>> listNewRecommend();

    /**
     * 获取最新四篇文章ID及文章标题
     *
     * @return 文章信息集合
     */
    List<Map<String, String>> listNew();

    /**
     * 获取每一年份月份下的文章数量，标题及发布时间信息
     *
     * @return 每一年份下的文章数量，标题及发布时间信息
     */
    List<Map<String, Object>> countYearMonthInfo();

    /**
     * 获取总文章、浏览量、点赞数以及评论数数据
     *
     * @return 总文章数据
     */
    Map<String, String> countTotalData();


    /**
     * 获取文章、专栏以及标签数量
     *
     * @return 集合
     */
    Map<String, Integer> countArticleCateAndTag();

    /**
     * 根据文章ID获取文章详细信息
     *
     * @param articleId 文章ID
     * @param request   响应
     * @return 文章详细信息
     */
    Map<String, Object> getInfoById(Long articleId, HttpServletRequest request);

    /**
     * 根据文章ID获取文章详细信息以及markdown数据
     *
     * @param articleId 文章ID
     * @return 文章详细信息
     */
    Map<String, Object> getMarkdownById(Long articleId);

    /**
     * 根据文章ID更新文章评论数
     *
     * @param articleId 文章ID
     * @param comments  评论数
     */
    void updateCommentsById(Long articleId, int comments);
}
