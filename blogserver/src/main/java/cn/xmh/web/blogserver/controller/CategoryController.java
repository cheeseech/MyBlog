package cn.xmh.web.blogserver.controller;

import cn.xmh.web.blogserver.model.Category;
import cn.xmh.web.blogserver.model.ResultJson;
import cn.xmh.web.blogserver.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Xmh
 * @date 2020/7/31 19:50
 * 专栏获取接口
 */
@RestController
@RequestMapping("/category")
@Api(tags = "专栏浏览")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation("获取所有专栏信息")
    public ResultJson listCategory() {
        try {
            List<Category> categoryList = categoryService.listAll();
            return new ResultJson("200", "获取成功！", categoryList);
        } catch (NullPointerException e) {
            return new ResultJson("404", "列表为空！", null);
        } catch (Exception e) {
            return new ResultJson("500", "未知错误！请联系管理员。", null);
        }
    }

    @RequestMapping(value = "/counts", method = RequestMethod.GET)
    @ApiOperation("获取专栏下文章数量")
    public ResultJson listArticleCount() {
        try {
            List<Map<String, Long>> map = categoryService.listArticleCount();
            return new ResultJson("200", "获取成功！", map);
        } catch (NullPointerException e) {
            return new ResultJson("404", "列表为空！", null);
        } catch (Exception e) {
            return new ResultJson("500", "未知错误！请联系管理员。", null);
        }
    }
}
