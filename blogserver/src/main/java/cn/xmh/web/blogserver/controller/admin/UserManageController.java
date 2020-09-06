package cn.xmh.web.blogserver.controller.admin;

import cn.xmh.web.blogserver.config.ResultJson;
import cn.xmh.web.blogserver.model.RoleUser;
import cn.xmh.web.blogserver.model.Tags;
import cn.xmh.web.blogserver.model.User;
import cn.xmh.web.blogserver.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @author Xmh
 * @date 2020/7/31 20:03
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "用户管理")
public class UserManageController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/users",method = RequestMethod.POST)
    @ApiOperation("新建一个用户")
    public ResultJson insertUser(@RequestBody User user){
        try {
            userService.insertUser(user);
            return new ResultJson("201","新建成功！",null);
        }catch (IllegalArgumentException e){
            return new ResultJson("422","新建失败！请稍后再试。",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/users/{userId}",method = RequestMethod.DELETE)
    @ApiOperation("删除一个用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户ID",dataType = "Long",required = true)
    })
    public ResultJson deleteUser(@PathVariable Long userId){
        try {
            userService.deleteByUserId(userId);
            return new ResultJson("204","删除成功！",null);
        }catch (IllegalArgumentException e){
            return new ResultJson("400","删除失败！请稍后再试。",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/users",method = RequestMethod.PUT)
    @ApiOperation("更新一个用户")
    public ResultJson updateUser(@RequestBody User user){
        try {
            userService.updateByUserId(user.getUserId(),user);
            return new ResultJson("201", "更新成功！", null);
        }catch (IllegalArgumentException e){
            return new ResultJson("422","更新失败！请稍后再试。",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/users/{userId}/state",method = RequestMethod.PUT)
    @ApiOperation("更新用户状态")
    public ResultJson updateUser(@PathVariable Long userId,@RequestBody Integer state){
        try {
            userService.updateUserState(state,userId);
            return new ResultJson("201", "更新成功！", null);
        }catch (IllegalArgumentException e){
            return new ResultJson("422","更新失败！请稍后再试。",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/users/{userId}/password",method = RequestMethod.PUT)
    @ApiOperation("更新用户密码")
    public ResultJson updateUser(@PathVariable Long userId,@RequestBody String password){
        try {
            userService.resetPassword(userId,password);
            return new ResultJson("201", "更新成功！", null);
        }catch (IllegalArgumentException e){
            return new ResultJson("422","更新失败！请稍后再试。",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/users",method = RequestMethod.GET)
    @ApiOperation("获取所有用户")
    public ResultJson getUsers(){
        try {
            List<User> users=userService.getAllUser();
            return new ResultJson("200", "查找成功！", users);
        }catch (NullPointerException e){
            return new ResultJson("400","该标签名不存在！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/users/{userName}",method = RequestMethod.GET)
    @ApiOperation("获取所有用户")
    public ResultJson getUser(@PathVariable String userName){
        try {
            User users=userService.getUserByUserName(userName);
            return new ResultJson("200", "查找成功！", users);
        }catch (NullPointerException e){
            return new ResultJson("400","该标签名不存在！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员。",null);
        }
    }

    @RequestMapping(value = "/users/role/",method = RequestMethod.POST)
    @ApiOperation("设置用户角色")
    public ResultJson setUserRole(@RequestBody RoleUser roleUser){
        try{
            userService.setUserRole(roleUser.getUserId(),roleUser.getRoleId());
            return new ResultJson("200","设置成功！",null);
        }catch (IllegalArgumentException e){
            return new ResultJson("400","设置失败！请联系管理员！",null);
        }catch (Exception e){
            return new ResultJson("500","未知错误！请联系管理员！",null);
        }
    }
}
