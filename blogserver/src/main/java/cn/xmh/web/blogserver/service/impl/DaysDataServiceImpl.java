package cn.xmh.web.blogserver.service.impl;

import cn.xmh.web.blogserver.mapper.ArticleMapper;
import cn.xmh.web.blogserver.mapper.DaysDataMapper;
import cn.xmh.web.blogserver.model.Article;
import cn.xmh.web.blogserver.model.DaysData;
import cn.xmh.web.blogserver.service.DaysDataService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.boot.autoconfigure.session.NonUniqueSessionRepositoryException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Xmh
 * @date 2020/11/16 11:25
 */
@Service
public class DaysDataServiceImpl implements DaysDataService {

    @Resource
    private DaysDataMapper daysDataMapper;

    @Override
    public List<DaysData> getDataByRange(Date start, Date end) {
        //根据范围获取日期数据
        List<DaysData> daysData=daysDataMapper.getDataByRange(start, end);
        if(daysData.isEmpty()){
            throw new NullPointerException();
        }
        return daysData;
    }

    @Override
    public Map<String, Object> getDataByRangeToEcharts(Date start, Date end) {
        List<DaysData> daysData=daysDataMapper.getDataByRange(start, end);
        //判断所选日期内是否有数据
        if(daysData.isEmpty()){
            throw new NullPointerException();
        }

        //建立四个数组分别存储日期、浏览量、点赞数和评论数
        int size=daysData.size();
        int[] views=new int[size],likes=new int[size],comments=new int[size];
        String[] days=new String[size];
        SimpleDateFormat s=new SimpleDateFormat("YYYY-MM-dd");
        Map<String,Object> echartsData=new HashMap<>();

        //遍历日期数据进行数组的赋值
        for(int i=0;i<size;i++){
            DaysData element=daysData.get(i);
            views[i]=element.getViews();
            likes[i]=element.getLikes();
            comments[i]=element.getComments();
            days[i]=s.format(element.getDays());
        }
        //将处理好的数据放到map中
        echartsData.put("days",days);
        echartsData.put("views",views);
        echartsData.put("likes",likes);
        echartsData.put("comments",comments);

        return echartsData;
    }

    @Override
    public List<Article> getSingleArticle(Date start, Date end) {
        //获取范围内每篇文章标题、创建日期、浏览量、点赞数以及评论数
        List<Article> articles=daysDataMapper.getSingleArticle(start, end);
        if(articles.isEmpty()){
            throw new NullPointerException();
        }
        return articles;
    }
}
