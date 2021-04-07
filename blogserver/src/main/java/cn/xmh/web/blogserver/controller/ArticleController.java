package cn.xmh.web.blogserver.controller;

import cn.xmh.web.blogserver.config.IPUtil;
import cn.xmh.web.blogserver.model.PageRequest;
import cn.xmh.web.blogserver.model.PageResult;
import cn.xmh.web.blogserver.model.ResultJson;
import cn.xmh.web.blogserver.model.Article;
import cn.xmh.web.blogserver.service.ArticleService;
import com.qiniu.util.Auth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Xmh
 * @date 2020/8/1 20:06
 *
 */
@RestController
@RequestMapping("/article")
@Api(tags = "文章浏览")
public class ArticleController {

    @Resource
    private ArticleService articleService;

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

    @RequestMapping(value = "/{title}",method = RequestMethod.POST)
    @ApiOperation("根据标题分页模糊查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title",value = "标题",dataType = "string",required = true)
    })
    public ResultJson getArticleByTitle(@PathVariable String title,PageRequest pageQuery){
        try {
            PageResult articles=articleService.getLikeTitleArticleInPage(title,pageQuery);
            return new ResultJson("200","获取成功！",articles);
        }catch (NullPointerException e){
            return new ResultJson("404","列表为空！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/",method = RequestMethod.POST)
    @ApiOperation("标题为空时查询全部文章并分页")
    public ResultJson getArticleByTitle(PageRequest pageQuery){
        try {
            PageResult articles=articleService.getLikeTitleArticleInPage("",pageQuery);
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

    @RequestMapping(value = "/tags/{tagName}/{title}",method = RequestMethod.POST)
    @ApiOperation("根据标签名分页获取文章")
    public ResultJson getTags(@PathVariable String tagName,@PathVariable String title, PageRequest pageQuery){
        try {
            PageResult pageResult=articleService.getByTagNameAndTitleInPage(tagName,title,pageQuery);
            return new ResultJson("200","获取成功！",pageResult);
        }catch (NullPointerException e){
            return new ResultJson("404","列表为空！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/tags/{tagName}/",method = RequestMethod.POST)
    @ApiOperation("标题为空时根据标签名分页获取文章")
    public ResultJson getTags(@PathVariable String tagName,PageRequest pageQuery){
        try {
            PageResult pageResult=articleService.getByTagNameAndTitleInPage(tagName,"",pageQuery);
            return new ResultJson("200","获取成功！",pageResult);
        }catch (NullPointerException e){
            return new ResultJson("404","列表为空！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/category/{cateName}/{title}",method = RequestMethod.POST)
    @ApiOperation("根据专栏名和标题分页获取文章")
    public ResultJson getCategory(@PathVariable String cateName,@PathVariable String title,PageRequest pageQuery){
        try {
            PageResult pageResult=articleService.getByCateNameAndTitleInPage(cateName,title,pageQuery);
            return new ResultJson("200","获取成功！",pageResult);
        }catch (NullPointerException e){
            return new ResultJson("404","列表为空！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/category/{cateName}/",method = RequestMethod.POST)
    @ApiOperation("标题为空时根据专栏名分页获取全部文章")
    public ResultJson getCategory(@PathVariable String cateName,PageRequest pageQuery){
        try {
            PageResult pageResult=articleService.getByCateNameAndTitleInPage(cateName,"",pageQuery);
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

    @RequestMapping(value = "/info/{articleId}",method = RequestMethod.GET)
    @ApiImplicitParam(name = "articleId",value = "文章ID",dataType = "Long",required = true)
    public ResultJson getArticleInfo(@PathVariable Long articleId,HttpServletRequest request){
        try{
            Map<String, Object> articleInfo=articleService.getArticleInfoById(articleId,request);
            return new ResultJson("200","获取成功！",articleInfo);
        }catch (NullPointerException e){
            return new ResultJson("422","获取失败！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/markdown/{articleId}",method = RequestMethod.GET)
    @ApiOperation("获取文章markdown数据")
    @ApiImplicitParam(name = "articleId",value = "文章ID",dataType = "long",required = true)
    public ResultJson getMarkdownById(@PathVariable Long articleId){
        try{
            Map<String, Object> articleInfo=articleService.getMarkdownInfoById(articleId);
            return new ResultJson("200","读取文章数据成功！",articleInfo);
        }catch (NullPointerException e){
            return new ResultJson("422","获取文章数据失败！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/comments/{articleId}/{commentsLen}", method = RequestMethod.POST)
    @ApiOperation("更新文章评论数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId",value = "文章ID",dataType = "Long",required = true),
            @ApiImplicitParam(name = "commentsLen",value = "评论数",dataType = "int",required = true)
    })
    public ResultJson updateArticleComments(@PathVariable Long articleId ,@PathVariable int commentsLen){
        try {
            articleService.updateArticleComments(articleId,commentsLen);
            return new ResultJson("200","评论更新成功！",null);
        }catch (IllegalArgumentException e){
            return new ResultJson("422","更新失败！请稍后重试。",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }
}
