package cn.xmh.web.blogserver.mapper;

import cn.xmh.web.blogserver.model.Article;
import cn.xmh.web.blogserver.model.DaysData;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * @author Xmh
 * @date 2020/11/16 10:03
 */
@Mapper
public interface DaysDataMapper {

    /**
     * 添加新的一天的数据
     * @param daysData 数据
     * @return 条数
     */
    int newDay(DaysData daysData);

    /**
     * 根据范围查找日期数据
     * @param start 开始日期
     * @param end 结束日期
     * @return 日期数据集合
     */
    List<DaysData> getDataByRange(Date start,Date end);

    /**
     * 获取指定日期内文章标题、创建日期、浏览量、点赞数以及评论数
     * @param start 开始日期
     * @param end 结束日期
     * @return 文章信息
     */
    List<Article> getSingleArticle(Date start,Date end);
}
