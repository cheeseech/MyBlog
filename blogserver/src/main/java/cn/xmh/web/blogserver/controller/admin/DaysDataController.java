package cn.xmh.web.blogserver.controller.admin;

import cn.xmh.web.blogserver.model.ResultJson;
import cn.xmh.web.blogserver.model.Article;
import cn.xmh.web.blogserver.model.DaysData;
import cn.xmh.web.blogserver.service.DaysDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Xmh
 * @date 2020/11/17 8:51
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "日期数据浏览")
public class DaysDataController {

    @Resource
    private DaysDataService daysDataService;

    @RequestMapping(value = "/daysData/{start}/{end}",method = RequestMethod.GET)
    @ApiOperation("根据范围选择日期数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value = "开始日期", dataType = "Date", required = true),
            @ApiImplicitParam(name = "end", value = "结束日期", dataType = "Date", required = true)
    })
    public ResultJson getDataByRange(@PathVariable Date start,@PathVariable Date end){
        try{
            List<DaysData> daysData=daysDataService.getDataByRange(start,end);
            return new ResultJson("200","日期数据选择成功！",daysData);
        }catch (NullPointerException e){
            return new ResultJson("422","日期数据选择失败！请稍后再试。",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员！",null);
        }
    }

    @RequestMapping(value = "/daysEcharts/{start}/{end}",method = RequestMethod.GET)
    @ApiOperation("根据范围选择echarts数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value = "开始日期", dataType = "Date", required = true),
            @ApiImplicitParam(name = "end", value = "结束日期", dataType = "Date", required = true)
    })
    public ResultJson getDataForEchartsByRange(@PathVariable Date start,@PathVariable Date end){
        try{
            Map<String,Object> data = daysDataService.getDataByRangeToEcharts(start,end);
            return new ResultJson("200","日期图表选择成功！",data);
        }catch (NullPointerException e){
            return new ResultJson("422","日期图表选择失败！请稍后再试。",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员！",null);
        }
    }

    @RequestMapping(value = "/days/single/{start}/{end}")
    @ApiOperation("根据范围获取文章信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value = "开始日期", dataType = "Date", required = true),
            @ApiImplicitParam(name = "end", value = "结束日期", dataType = "Date", required = true)
    })
    public ResultJson getSingleArticle(@PathVariable Date start,@PathVariable Date end){
        try{
            List<Article> articles=daysDataService.getSingleArticle(start, end);
            return new ResultJson("200","日期文章选择成功！",articles);
        }catch (NullPointerException e){
            return new ResultJson("422","日期文章选择失败！请稍后再试。",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员！",null);
        }
    }
}
