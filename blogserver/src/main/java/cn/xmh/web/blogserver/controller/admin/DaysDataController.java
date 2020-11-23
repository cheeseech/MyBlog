package cn.xmh.web.blogserver.controller.admin;

import cn.xmh.web.blogserver.config.ResultJson;
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
@Api(tags = "日期数据")
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
            return new ResultJson("200","获取成功！",daysData);
        }catch (NullPointerException e){
            return new ResultJson("422","数据为空！请稍后再试！",null);
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
            return new ResultJson("200","获取成功！",data);
        }catch (NullPointerException e){
            return new ResultJson("422","数据为空！请稍后再试！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员！",null);
        }
    }

    @RequestMapping(value = "/single/{start}/{end}")
    @ApiOperation("根据范围获取文章信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start", value = "开始日期", dataType = "Date", required = true),
            @ApiImplicitParam(name = "end", value = "结束日期", dataType = "Date", required = true)
    })
    public ResultJson getSingleArticle(@PathVariable Date start,@PathVariable Date end){
        try{
            List<Article> articles=daysDataService.getSingleArticle(start, end);
            return new ResultJson("200","获取成功！",articles);
        }catch (NullPointerException e){
            return new ResultJson("422","获取失败！请稍后再试！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员！",null);
        }
    }

    @RequestMapping(value = "/daysData",method = RequestMethod.PUT)
    @ApiOperation("新增日期数据")
    public ResultJson newDays(DaysData daysData){
        try{
            daysDataService.newDay(new DaysData());
            return new ResultJson("200","添加成功！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员！",null);
        }
    }
}
