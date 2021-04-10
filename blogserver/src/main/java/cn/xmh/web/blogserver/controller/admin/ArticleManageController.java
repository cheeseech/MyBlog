package cn.xmh.web.blogserver.controller.admin;

import cn.xmh.web.blogserver.model.Article;
import cn.xmh.web.blogserver.model.PageRequest;
import cn.xmh.web.blogserver.model.PageResult;
import cn.xmh.web.blogserver.model.ResultJson;
import cn.xmh.web.blogserver.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Xmh
 * @date 2020/7/31 20:03
 * 后台文章管理接口
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "文章管理")
public class ArticleManageController {

    @Resource
    private ArticleService articleService;

    @RequestMapping(value = "/article", method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation("新建一篇文章")
    public ResultJson saveArticle(@RequestBody Article article) {
        try {
            articleService.saveArticle(article);
            return new ResultJson("201", "创建文章成功！", null);
        } catch (IllegalArgumentException e) {
            return new ResultJson("422", "创建文章失败！存在相同标题。", null);
        } catch (Exception e) {
            return new ResultJson("500", "未知错误！请联系管理员。" + e, null);
        }
    }

    @RequestMapping(value = "/article/{articleId}", method = RequestMethod.DELETE)
    @ApiOperation("删除一篇文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章ID", dataType = "Long", required = true)
    })
    public ResultJson removeArticle(@PathVariable Long articleId) {
        try {
            articleService.removeById(articleId);
            return new ResultJson("204", "删除成功！", null);
        } catch (IllegalArgumentException e) {
            return new ResultJson("400", "删除失败！请稍后再试。", null);
        } catch (Exception e) {
            return new ResultJson("500", "未知错误！请联系管理员。" + e, null);
        }
    }

    @RequestMapping(value = "/article", method = RequestMethod.POST)
    @ApiOperation("更新一篇文章")
    @ResponseBody
    public ResultJson updateArticle(@RequestBody Article article) {
        try {
            articleService.updateById(article.getArticleId(), article);
            return new ResultJson("201", "更新成功！", null);
        } catch (IllegalArgumentException e) {
            return new ResultJson("422", "更新失败！请稍后再试。", null);
        } catch (Exception e) {
            return new ResultJson("500", "未知错误！请联系管理员。" + e, null);
        }
    }

    @RequestMapping(value = "/article/{articleId}/{state}", method = RequestMethod.POST)
    @ApiOperation("更新文章状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章ID", dataType = "Long", required = true),
            @ApiImplicitParam(name = "state", value = "文章状态", dataType = "Integer", required = true)
    })
    public ResultJson updateState(@PathVariable Long articleId, @PathVariable Integer state) {
        try {
            articleService.updateState(state, articleId);
            return new ResultJson("201", "更新成功！", null);
        } catch (IllegalArgumentException e) {
            return new ResultJson("422", "更新失败！请稍后再试。", null);
        } catch (Exception e) {
            return new ResultJson("500", "未知错误！请联系管理员。", null);
        }
    }

    @RequestMapping(value = "/article/totalData", method = RequestMethod.GET)
    @ApiOperation("获取文章总数据")
    public ResultJson getTotalData() {
        try {
            Map<String, String> totalData = articleService.countTotalData();
            return new ResultJson("200", "获取成功！", totalData);
        } catch (NullPointerException e) {
            return new ResultJson("422", "获取失败！请稍后再试！", null);
        } catch (Exception e) {
            return new ResultJson("500", "未知错误！请联系管理员！", null);
        }
    }

    @RequestMapping(value = "/article/delete", method = RequestMethod.POST)
    @ApiOperation("分页获取已删除状态的文章")
    public ResultJson getDelete(PageRequest pageQuery) {
        try {
            PageResult pageResult = articleService.getByDelete(pageQuery);
            return new ResultJson("200", "获取成功！", pageResult);
        } catch (NullPointerException e) {
            return new ResultJson("404", "列表为空！", null);
        } catch (Exception e) {
            return new ResultJson("500", "未知错误！请联系管理员。", null);
        }
    }

    @RequestMapping(value = "/article/not-delete", method = RequestMethod.POST)
    @ApiOperation("分页获取未删除状态的文章")
    public ResultJson getNoDelete(PageRequest pageQuery) {
        try {
            PageResult pageResult = articleService.getByNotDelete(pageQuery);
            return new ResultJson("200", "获取成功！", pageResult);
        } catch (NullPointerException e) {
            return new ResultJson("404", "列表为空！", null);
        } catch (Exception e) {
            return new ResultJson("500", "未知错误！请联系管理员。", null);
        }
    }
}
