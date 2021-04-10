package cn.xmh.web.blogserver.utils;

import cn.xmh.web.blogserver.mapper.ArticleMapper;
import cn.xmh.web.blogserver.mapper.DaysDataMapper;
import cn.xmh.web.blogserver.model.DaysData;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Xmh
 * @date 2020/12/6 23:15
 * 定时任务类
 */

/**
 * 1.主要用于标记配置类，兼备Component的效果。
 * 2.开启定时任务
 */
@Configuration
@EnableScheduling
public class SaticScheduleTask {

    @Resource
    private DaysDataMapper daysDataMapper;
    @Resource
    private ArticleMapper articleMapper;

    /**
     * 定时任务：每天凌晨1点执行新日期数据的添加
     * 添加新日期数据,已发布状态的文章浏览量、点赞数以及评论数都为0
     */
    @Scheduled(cron = "0 0 1 * * ? ")
    public void newDay() {
        //获取当前时间
        Date day = new Date();
        //获取已发布文章ID集合
        List<Long> articleList = articleMapper.listByPublish();
        //遍历集合为添加浏览量、点赞数以及评论数
        for (Long articleId : articleList) {
            DaysData element = new DaysData();
            element.setDays(day);
            element.setViews(0);
            element.setComments(0);
            element.setLikes(0);
            element.setArticleId(articleId);
            System.out.println(day);
            //添加数据
            daysDataMapper.newDay(element);
        }
    }

    @Scheduled(cron = "0 0 0 1 * ? ")
    public void recycleArticle() {
        Calendar calendar = Calendar.getInstance();
        //获取开始时间
        Date start = new Date();

        calendar.setTime(start);
        calendar.add(Calendar.MONTH, -1);
        //获取前一个月时间
        Date end = calendar.getTime();

        //删除文章
        articleMapper.recycleByDeleteInMonth(start, end);
    }
}
