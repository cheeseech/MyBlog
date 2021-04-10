package cn.xmh.web.blogserver.mapper;

import cn.xmh.web.blogserver.model.Article;
import cn.xmh.web.blogserver.model.DaysData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

/**
 * @author Xmh
 * @date 2020/11/16 10:03
 * 日期数据持久层
 */
@Mapper
public interface DaysDataMapper {

    /**
     * 添加新的一天的数据
     *
     * @param daysData 数据
     * @return 条数
     */
    int newDay(DaysData daysData);

    /**
     * 根据范围查找日期数据
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 日期数据集合
     */
    List<DaysData> listByRange(Date start, Date end);

    /**
     * 获取指定日期内文章标题、创建日期、浏览量、点赞数以及评论数
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return 文章信息
     */
    List<Article> listSingleArticle(Date start, Date end);

    /**
     * 根据文章ID删除日数据
     *
     * @param articleId 文章ID
     * @return 受影响的行
     */
    int removeByArticleId(@Param("article_id") Long articleId);

    /**
     * 根据日期及文章ID更行浏览量加一
     * @param day 日期
     * @param articleId 文章ID
     * @return 受影响的行
     */
    int updateViewsByDaysAndArticleId(String day, @Param("article_id") Long articleId);

    /**
     * 根据日期及文章ID更行评论数加一
     * @param day 日期
     * @param articleId 文章ID
     * @return 受影响的行
     */
    int updateCommentsByDaysAndArticleId(String day, @Param("article_id") Long articleId);
}
