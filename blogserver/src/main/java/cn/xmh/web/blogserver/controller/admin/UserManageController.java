package cn.xmh.web.blogserver.controller.admin;

import cn.xmh.web.blogserver.model.PageRequest;
import cn.xmh.web.blogserver.model.PageResult;
import cn.xmh.web.blogserver.model.ResultJson;
import cn.xmh.web.blogserver.model.User;
import cn.xmh.web.blogserver.service.UserService;
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
 * 用户管理接口
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "用户管理")
public class UserManageController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    @ApiOperation("新建一个用户")
    public ResultJson save(User user) {
        try {
            userService.save(user);
            return new ResultJson("201", "用户新增成功！", null);
        } catch (IllegalArgumentException e) {
            return new ResultJson("422", "用户新增失败！已存在相同用户名。", null);
        } catch (SQLException e) {
            return new ResultJson("422", "用户新增失败！请稍后再试。", null);
        } catch (Exception e) {
            return new ResultJson("500", "未知错误！请联系管理员。" + e, null);
        }
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    @ApiOperation("删除一个用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "Long", required = true)
    })
    public ResultJson remove(@PathVariable Long userId) {
        try {
            userService.removeById(userId);
            return new ResultJson("204", "用户删除成功！", null);
        } catch (IllegalArgumentException e) {
            return new ResultJson("400", "用户删除失败！请稍后再试。", null);
        } catch (Exception e) {
            return new ResultJson("500", "未知错误！请联系管理员。", null);
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ApiOperation("更新一个用户")
    public ResultJson update(User user) {
        try {
            userService.updateById(user);
            return new ResultJson("201", "用户更新成功！", null);
        } catch (IllegalArgumentException e) {
            return new ResultJson("422", "用户更新失败！已存在相同用户名。", null);
        } catch (SQLException e) {
            return new ResultJson("422", "用户更新失败！请稍后再试。", null);
        } catch (Exception e) {
            return new ResultJson("500", "未知错误！请联系管理员。" + e, null);
        }
    }

    @RequestMapping(value = "/users/{userId}/{state}", method = RequestMethod.PUT)
    @ApiOperation("更新用户状态")
    public ResultJson updateState(@PathVariable Long userId, @PathVariable Boolean state) {
        try {
            userService.updateState(state, userId);
            return new ResultJson("201", "用户状态更新成功！", null);
        } catch (IllegalArgumentException e) {
            return new ResultJson("422", "用户状态更新失败！请稍后再试。", null);
        } catch (Exception e) {
            return new ResultJson("500", "未知错误！请联系管理员。" + e, null);
        }
    }

    @RequestMapping(value = "/users/page/", method = RequestMethod.POST)
    @ApiOperation("分页获取用户数据")
    public ResultJson getByPage(PageRequest pageQuery) {
        try {
            PageResult pageResult = userService.getByPage(pageQuery);
            return new ResultJson("200", "查找成功！", pageResult);
        } catch (NullPointerException e) {
            return new ResultJson("400", "用户数据为空！", null);
        } catch (Exception e) {
            return new ResultJson("500", "未知错误！请联系管理员。", null);
        }
    }
}
