package cn.xmh.web.blogserver.service;

import cn.xmh.web.blogserver.model.PageRequest;
import cn.xmh.web.blogserver.model.PageResult;
import java.util.Date;
import java.util.Map;

/**
 * @author Xmh
 * @date 2020/11/16 11:12
 */
public interface DaysDataService {

    /**
     * 根据日期范围分页查询日期数据
     * @param start 开始日期
     * @param end 结束日期
     * @param pageQuery 统一分页查询请求
     * @return 分页日期数据集合
     */
    PageResult getDataByRange(Date start, Date end, PageRequest pageQuery);

    /**
     * 根据日期范围查询日期数据，返回echarts需要的数据
     * @param start 开始日期
     * @param end 结束日期
     * @return 日期数据集合
     */
    Map<String,Object> getDataByRangeToEcharts(Date start,Date end);

    /**
     * 分页获取指定日期内文章标题、创建日期、浏览量、点赞数以及评论数
     * @param start 开始日期
     * @param end 结束日期
     * @param pageQuery
     * @return 文章信息
     */
    PageResult getSingleArticle(Date start,Date end,PageRequest pageQuery);


}
