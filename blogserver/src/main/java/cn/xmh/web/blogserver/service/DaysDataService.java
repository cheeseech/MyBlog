package cn.xmh.web.blogserver.service;

import cn.xmh.web.blogserver.model.Article;
import cn.xmh.web.blogserver.model.DaysData;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Xmh
 * @date 2020/11/16 11:12
 */
public interface DaysDataService {

    /**
     * 根据日期范围查询日期数据
     * @param start 开始日期
     * @param end 结束日期
     * @return 日期数据集合
     */
    List<DaysData> getDataByRange(Date start,Date end) throws ParseException;

    /**
     * 根据日期范围查询日期数据，返回echarts需要的数据
     * @param start 开始日期
     * @param end 结束日期
     * @return 日期数据集合
     */
    Map<String,Object> getDataByRangeToEcharts(Date start,Date end);

    /**
     * 添加新日期数据
     * @param daysData
     */
    void newDay(DaysData daysData) throws ParseException;

    /**
     * 获取指定日期内文章标题、创建日期、浏览量、点赞数以及评论数
     * @param start 开始日期
     * @param end 结束日期
     * @return 文章信息
     */
    List<Article> getSingleArticle(Date start,Date end);
}
