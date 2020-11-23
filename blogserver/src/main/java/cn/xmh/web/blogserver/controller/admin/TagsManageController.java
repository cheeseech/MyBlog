package cn.xmh.web.blogserver.controller.admin;

import cn.xmh.web.blogserver.config.ResultJson;
import cn.xmh.web.blogserver.model.Tags;
import cn.xmh.web.blogserver.service.TagsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Xmh
 * @date 2020/7/31 20:03
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "标签管理")
public class TagsManageController {

    @Resource
    private TagsService tagsService;

    @RequestMapping(value = "/tags/{tagName}",method = RequestMethod.GET)
    @ApiOperation("根据标签名查找标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tagName",value = "标签名",dataType = "String",required = true)
    })
    public ResultJson getTag(@PathVariable String tagName){
        try {
            Tags tags = tagsService.getTagsName(tagName);
            return new ResultJson("200", "查找成功！", tags);
        }catch (NullPointerException e){
            return new ResultJson("400","该标签名不存在！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/tags/{tagId}",method = RequestMethod.DELETE)
    @ApiOperation("删除一个标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tagId",value = "标签ID",dataType = "Long",required = true)
    })
    public ResultJson deleteTag(@PathVariable Long tagId){
        try {
            tagsService.deleteByTagId(tagId);
            return new ResultJson("204","删除成功！",null);
        }catch (IllegalArgumentException e){
            return new ResultJson("400","删除失败！请稍后再试。",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/tags",method = RequestMethod.PUT)
    @ApiOperation("新建一个标签")
    public ResultJson insertTag(Tags tags){
        try {
            tagsService.insertTag(tags);
            return new ResultJson("201","新增成功！",null);
        }catch (IllegalArgumentException e){
            return new ResultJson("422","新增失败！已存在相同标签名。",null);
        }catch (SQLException e){
            return new ResultJson("422","新增失败！请稍后再试。",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/tags",method = RequestMethod.POST)
    @ApiOperation("更新一个标签")
    public ResultJson updateTag(Tags tags){
        try {
            tagsService.updateTagById(tags);
            return new ResultJson("201", "更新成功！", null);
        }catch (IllegalArgumentException e){
            return new ResultJson("422","更新失败！已存在相同标签名。",null);
        }catch (SQLException e){
            return new ResultJson("422","更新失败！请稍后再试。",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }
}
