package cn.xmh.web.blogserver.service.impl;

import cn.xmh.web.blogserver.mapper.DaysDataMapper;
import cn.xmh.web.blogserver.model.Article;
import cn.xmh.web.blogserver.model.DaysData;
import cn.xmh.web.blogserver.model.PageRequest;
import cn.xmh.web.blogserver.model.PageResult;
import cn.xmh.web.blogserver.service.DaysDataService;
import cn.xmh.web.blogserver.utils.PageUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xmh
 * @date 2020/11/16 11:25
 * 日期数据业务层实现类
 */
@Service
public class DaysDataServiceImpl implements DaysDataService {

    @Resource
    private DaysDataMapper daysDataMapper;

    @Override
    public PageResult getByRange(Date start, Date end, PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        // 设置页码以及长度
        PageHelper.startPage(pageNum, pageSize);

        // 根据范围获取日期数据
        List<DaysData> daysData = daysDataMapper.listByRange(start, end);
        // 日期数据判空
        if (daysData.isEmpty()) {
            throw new NullPointerException();
        }

        return PageUtil.getPageResult(pageRequest, new PageInfo<>(daysData));
    }

    @Override
    public Map<String, Object> getByRangeOnEcharts(Date start, Date end) {
        List<DaysData> daysData = daysDataMapper.listByRange(start, end);
        // 判断所选日期内是否有数据
        if (daysData.isEmpty()) {
            throw new NullPointerException();
        }

        // 建立三个数组分别存储日期、浏览量、和评论数
        int size = daysData.size();
        int[] views = new int[size], comments = new int[size];
        String[] days = new String[size];

        // 日期数据格式
        SimpleDateFormat s = new SimpleDateFormat("YYYY-MM-dd");
        // 集合存储处理好的数据
        Map<String, Object> echartsData = new HashMap<>();

        // 遍历日期集合进行数组元素的赋值
        for (int i = 0; i < size; i++) {
            DaysData element = daysData.get(i);
            views[i] = element.getViews();
            comments[i] = element.getComments();
            days[i] = s.format(element.getDays());
        }

        // 将处理好的数据放到map中
        echartsData.put("days", days);
        echartsData.put("views", views);
        echartsData.put("comments", comments);

        return echartsData;
    }

    @Override
    public PageResult getSingleArticle(Date start, Date end, PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        // 设置页码以及长度
        PageHelper.startPage(pageNum, pageSize);

        // 获取时间范围内每篇文章标题、创建日期、浏览量、点赞数以及评论数
        List<Article> articles = daysDataMapper.listSingleArticle(start, end);
        // 文章数据判空
        if (articles.isEmpty()) {
            throw new NullPointerException();
        }

        // 调用分页工具类完成分页信息的封装
        return PageUtil.getPageResult(pageRequest, new PageInfo<>(articles));
    }
}
