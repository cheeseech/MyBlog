package cn.xmh.web.blogserver.controller.admin;

import cn.xmh.web.blogserver.config.ResultJson;
import cn.xmh.web.blogserver.model.Article;
import cn.xmh.web.blogserver.model.Tags;
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
 * @date 2020/7/31 20:03
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "博客管理")
public class ArticleManageController {

    @Resource
    private ArticleService articleService;

    @RequestMapping(value = "/article",method = RequestMethod.POST)
    @ApiOperation("新建一篇文章")
    public ResultJson insertArticle(@RequestBody Article article){
        try {
            articleService.insertArticle(article);
            return new ResultJson("201","新建成功！",null);
        }catch (IllegalArgumentException e){
            return new ResultJson("422","新建失败！请稍后再试。",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/article/{articleId}",method = RequestMethod.DELETE)
    @ApiOperation("删除一篇文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId",value = "文章ID",dataType = "Long",required = true)
    })
    public ResultJson deleteArticle(@PathVariable Long articleId){
        try {
            articleService.deleteByArticleId(articleId);
            return new ResultJson("204","删除成功！",null);
        }catch (IllegalArgumentException e){
            return new ResultJson("400","删除失败！请稍后再试。",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/article",method = RequestMethod.PUT)
    @ApiOperation("更新一篇文章")
    public ResultJson updateArticle(@RequestBody Article article){
        try {
            articleService.updateByArticleId(article.getArticleId(),article);
            return new ResultJson("201", "更新成功！", null);
        }catch (IllegalArgumentException e){
            return new ResultJson("422","更新失败！请稍后再试。",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/article/{articleId}/state",method = RequestMethod.PUT)
    @ApiOperation("更新文章状态")
    public ResultJson updateArticle(@PathVariable Long articleId,@RequestBody Integer articleState){
        try {
            articleService.resetArticleState(articleState,articleId);
            return new ResultJson("201", "更新成功！", null);
        }catch (IllegalArgumentException e){
            return new ResultJson("422","更新失败！请稍后再试。",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/article/totalData",method = RequestMethod.GET)
    @ApiOperation("获取文章总数据")
    public ResultJson getTotalData(){
        try{
            Map<String,String> totalData=articleService.getTotalData();
            return new ResultJson("200","获取成功！",totalData);
        }catch (NullPointerException e){
            return new ResultJson("422","获取失败！请稍后再试！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员！",null);
        }
    }
}
