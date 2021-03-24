package cn.xmh.web.blogserver.controller.admin;

import cn.xmh.web.blogserver.model.PageRequest;
import cn.xmh.web.blogserver.model.PageResult;
import cn.xmh.web.blogserver.model.ResultJson;
import cn.xmh.web.blogserver.model.Category;
import cn.xmh.web.blogserver.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * @author Xmh
 * @date 2020/7/31 20:03
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "专栏管理")
public class CategoryManageController {

    @Resource
    private CategoryService categoryService;

    @RequestMapping(value = "/category",method = RequestMethod.PUT)
    @ApiOperation("新建一个专栏")
    public ResultJson insertCategory(Category category){
        try {
            categoryService.insertCategory(category);
            return new ResultJson("201","专栏新增成功！",null);
        }catch (IllegalArgumentException e){
            return new ResultJson("422","专栏新增失败！已存在相同专栏名。",null);
        }catch (SQLException e){
            return new ResultJson("422","专栏新增失败！请稍后再试。",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/category",method = RequestMethod.POST)
    @ApiOperation("更新一个专栏")
    public ResultJson updateCategory(Category category){
        try {
            categoryService.updateByCategoryId(category);
            return new ResultJson("201", "专栏更新成功！", null);
        }catch (IllegalArgumentException e){
            return new ResultJson("422","专栏更新失败！已存在相同专栏名。",null);
        }catch (SQLException e){
            return new ResultJson("422","专栏更新失败！请稍后再试。",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/category/{cateId}",method = RequestMethod.DELETE)
    @ApiOperation("删除一个专栏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cateId",value = "专栏ID",dataType = "Long",required = true)
    })
    public ResultJson deleteCategory(@PathVariable Long cateId){
        try {
            categoryService.deleteByCategoryId(cateId);
            return new ResultJson("204","专栏删除成功！",null);
        }catch (IllegalArgumentException e){
            return new ResultJson("400","专栏删除失败！请稍后再试。",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/category/analysis",method = RequestMethod.POST)
    @ApiOperation("分页获取专栏数据分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageQuery",value = "分页对象",dataType = "PageRequest",required = true)
    })
    public ResultJson getCateAnalysis(PageRequest pageQuery){
        try{
            PageResult pageResult =categoryService.getCateAnalysis(pageQuery);
            return new ResultJson("200","获取成功！",pageResult);
        }catch (NullPointerException e){
            return new ResultJson("422","获取失败！请稍后再试！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员！",null);
        }
    }

    @RequestMapping(value = "/category/page",method = RequestMethod.POST)
    @ApiOperation("分页获取专栏数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageQuery",value = "分页对象",dataType = "PageRequest",required = true)
    })
    public ResultJson getAllCategories(PageRequest pageQuery){
        try{
            PageResult pageResult=categoryService.getCategoriesByPage(pageQuery);
            return new ResultJson("200","获取成功！",pageResult);
        }catch (NullPointerException e){
            return new ResultJson("400","列表为空！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }
}
