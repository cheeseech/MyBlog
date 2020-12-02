package cn.xmh.web.blogserver.controller;

import cn.xmh.web.blogserver.model.PageRequest;
import cn.xmh.web.blogserver.model.PageResult;
import cn.xmh.web.blogserver.model.ResultJson;
import cn.xmh.web.blogserver.model.Article;
import cn.xmh.web.blogserver.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Xmh
 * @date 2020/8/1 20:06
 */
@RestController
@RequestMapping("/article")
@Api(tags = "文章")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ApiOperation("获取所有文章")
    public ResultJson getArticles(){
        try {
            List<Article> articles=articleService.getAllArticle();
            return new ResultJson("200","获取成功！",articles);
        }catch (NullPointerException e){
            return new ResultJson("404","列表为空！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/page",method = RequestMethod.POST)
    @ApiOperation("分页获取文章")
    public ResultJson getPage(PageRequest pageQuery){
        try {
            PageResult pageResult=articleService.findPage(pageQuery);
            return new ResultJson("200","获取成功！",pageResult);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员！",null);
        }
    }

    @RequestMapping(value = "/de",method = RequestMethod.GET)
    @ApiOperation("获取已删除状态的文章")
    public ResultJson getArticlesDe(){
        try {
            List<Article> articles=articleService.getArticleByDe();
            return new ResultJson("200","获取成功！",articles);
        }catch (NullPointerException e){
            return new ResultJson("404","列表为空！"+e,null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/noDe",method = RequestMethod.GET)
    @ApiOperation("获取未删除状态的文章")
    public ResultJson getArticlesNoDe(){
        try {
            List<Article> articles=articleService.getArticleByNoDe();
            return new ResultJson("200","获取成功！",articles);
        }catch (NullPointerException e){
            return new ResultJson("404","列表为空！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/{title}",method = RequestMethod.GET)
    @ApiOperation("根据标题模糊查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title",value = "标题",dataType = "string",required = true)
    })
    public ResultJson getArticleByTitle(@PathVariable String title){
        try {
            List<Article> articles=articleService.getLikeTitleArticle(title);
            return new ResultJson("200","获取成功！",articles);
        }catch (NullPointerException e){
            return new ResultJson("404","列表为空！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/new",method = RequestMethod.GET)
    @ApiOperation("获取最新四篇文章标题")
    public ResultJson getNew(){
        try {
            List<Map<String, String>> maps=articleService.getNewTitle();
            return new ResultJson("200","获取成功！",maps);
        }catch (NullPointerException e){
            return new ResultJson("404","列表为空！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/recommend",method = RequestMethod.GET)
    @ApiOperation("获取最新推荐四篇文章标题")
    public ResultJson getRecommend(){
        try {
            List<Map<String, String>> maps=articleService.getNewRecommend();
            return new ResultJson("200","获取成功！",maps);
        }catch (NullPointerException e){
            return new ResultJson("404","列表为空！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/types/{typeName}",method = RequestMethod.GET)
    @ApiOperation("根据类型获取文章")
    public ResultJson getType(@PathVariable String typeName){
        try {
            List<Article> articles=articleService.getTypeArticle(typeName);
            return new ResultJson("200","获取成功！",articles);
        }catch (NullPointerException e){
            return new ResultJson("404","列表为空！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/tags/{tagName}",method = RequestMethod.POST)
    @ApiOperation("根据标签名获取文章")
    public ResultJson getTags(@PathVariable String tagName,PageRequest pageQuery){
        try {
            PageResult pageResult=articleService.getByTagNameInRange(tagName,pageQuery);
            return new ResultJson("200","获取成功！",pageResult);
        }catch (NullPointerException e){
            return new ResultJson("404","列表为空！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/category/{cateName}",method = RequestMethod.POST)
    @ApiOperation("根据专栏获取文章")
    public ResultJson getCategory(@PathVariable String cateName,PageRequest pageQuery){
        try {
            PageResult pageResult=articleService.getByCateNameInPage(cateName,pageQuery);
            return new ResultJson("200","获取成功！",pageResult);
        }catch (NullPointerException e){
            return new ResultJson("404","列表为空！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/timeline",method = RequestMethod.GET)
    @ApiOperation("根据时间线获取文章信息")
    public ResultJson getTimeLine(){
        try {
            List<Map<String,Object>> maps=articleService.getTimeLine();
            return new ResultJson("200","获取成功！",maps);
        }catch (NullPointerException e){
            return new ResultJson("404","列表为空！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。"+e,null);
        }
    }

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @ApiOperation("获取文章、专栏以及标签数量")
    public ResultJson getInfo(){
        try {
            Map<String, Integer> info=articleService.getInfo();
            return new ResultJson("200","获取成功!",info);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员！",null);
        }
    }
}
