package cn.xmh.web.blogserver.controller;

import cn.xmh.web.blogserver.config.ResultJson;
import cn.xmh.web.blogserver.model.Tags;
import cn.xmh.web.blogserver.service.TagsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Xmh
 * @date 2020/7/31 9:50
 */
@RestController
@RequestMapping("/tags")
@Api(tags = "标签")
public class TagsController {

    @Resource
    private TagsService tagsService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ApiOperation("获取所有标签")
    public ResultJson getTags(){

            try {
            List<Tags> tags=tagsService.getAllTags();
            return new ResultJson("200","获取成功!",tags);
        }catch (NullPointerException e){
            return new ResultJson("400","列表为空！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/article/{articleId}",method = RequestMethod.GET)
    @ApiOperation("根据文章ID获取标签集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId",value = "文章ID",dataType = "Long",required = true)
    })
    public ResultJson getTagsByArticle(@PathVariable Long articleId){
        try {
            List<Tags> tags = tagsService.getTagsByArticleId(articleId);
            return new ResultJson("200", "查找成功！", tags);
        }catch (NullPointerException e){
            return new ResultJson("400","该文章标签不存在！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/counts",method = RequestMethod.GET)
    @ApiOperation("获取标签集合及文章数")
    public ResultJson getTagsAndCount(){
        try {
            List<Map<String,String>> maps=tagsService.getTagsAndCount();
            return new ResultJson("200", "查找成功！", maps);
        }catch (NullPointerException e){
            return new ResultJson("400","该文章标签不存在！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }
}
