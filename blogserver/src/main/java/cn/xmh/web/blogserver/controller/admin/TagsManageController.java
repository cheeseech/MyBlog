package cn.xmh.web.blogserver.controller.admin;

import cn.xmh.web.blogserver.model.PageRequest;
import cn.xmh.web.blogserver.model.PageResult;
import cn.xmh.web.blogserver.model.ResultJson;
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
import java.sql.SQLException;

/**
 * @author Xmh
 * @date 2020/7/31 20:03
 * 标签管理接口
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "标签管理")
public class TagsManageController {

    @Resource
    private TagsService tagsService;

    @RequestMapping(value = "/tags/page/", method = RequestMethod.POST)
    @ApiOperation("分页获取标签信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageQuery", value = "分页对象", dataType = "PageRequest", required = true)
    })
    public ResultJson getByPage(PageRequest pageQuery) {
        try {
            PageResult pageResult = tagsService.getByPage(pageQuery);
            return new ResultJson("200", "获取成功！", pageResult);
        } catch (NullPointerException e) {
            return new ResultJson("400", "获取失败！列表为空。", null);
        } catch (Exception e) {
            return new ResultJson("500", "未知错误！请联系管理员。", null);
        }
    }

    @RequestMapping(value = "/tags/{tagId}", method = RequestMethod.DELETE)
    @ApiOperation("删除一个标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tagId", value = "标签ID", dataType = "Long", required = true)
    })
    public ResultJson remove(@PathVariable Long tagId) {
        try {
            tagsService.removeById(tagId);
            return new ResultJson("204", "标签删除成功！", null);
        } catch (IllegalArgumentException e) {
            return new ResultJson("400", "标签删除失败！请稍后再试。", null);
        } catch (Exception e) {
            return new ResultJson("500", "未知错误！请联系管理员。", null);
        }
    }

    @RequestMapping(value = "/tags", method = RequestMethod.PUT)
    @ApiOperation("新建一个标签")
    public ResultJson save(Tags tags) {
        try {
            tagsService.save(tags);
            return new ResultJson("201", "标签新增成功！", null);
        } catch (IllegalArgumentException e) {
            return new ResultJson("422", "标签新增失败！已存在相同标签名。", null);
        } catch (SQLException e) {
            return new ResultJson("422", "标签新增失败！请稍后再试。", null);
        } catch (Exception e) {
            return new ResultJson("500", "未知错误！请联系管理员。", null);
        }
    }

    @RequestMapping(value = "/tags", method = RequestMethod.POST)
    @ApiOperation("更新一个标签")
    public ResultJson update(Tags tags) {
        try {
            tagsService.updateById(tags);
            return new ResultJson("201", "标签更新成功！", null);
        } catch (IllegalArgumentException e) {
            return new ResultJson("422", "标签更新失败！已存在相同标签名。", null);
        } catch (SQLException e) {
            return new ResultJson("422", "标签更新失败！请稍后再试。", null);
        } catch (Exception e) {
            return new ResultJson("500", "未知错误！请联系管理员。", null);
        }
    }
}
