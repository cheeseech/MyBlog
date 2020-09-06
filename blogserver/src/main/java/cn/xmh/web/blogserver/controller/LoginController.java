package cn.xmh.web.blogserver.controller;

import cn.xmh.web.blogserver.config.ResultJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Xmh
 * @date 2020/8/31 10:54
 */
@RestController
@Api(tags = "登录")
public class LoginController {

    @RequestMapping(value = "/login_page",method = RequestMethod.GET)
    @ApiOperation("登录")
    public ResultJson login(){
        System.out.println("尚未登录！");
        return new ResultJson("401","尚未登录，请登录",null);
    }
}
