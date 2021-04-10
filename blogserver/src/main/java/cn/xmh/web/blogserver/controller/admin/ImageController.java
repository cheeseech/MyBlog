package cn.xmh.web.blogserver.controller.admin;

import cn.xmh.web.blogserver.model.ResultJson;
import cn.xmh.web.blogserver.utils.QiniuCloudUtil;
import com.qiniu.common.QiniuException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Xmh
 * @date 2021/4/6 8:37
 * 七牛云图片上传和删除接口
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "图片上传")
public class ImageController {

    @RequestMapping(value = "/img/upload", method = RequestMethod.GET)
    @ApiOperation("获取图片上传token")
    public ResultJson getUploadToken() {
        try {
            String token = QiniuCloudUtil.getUpToken();
            return new ResultJson("200", "token获取成功！", token);
        } catch (Exception e) {
            return new ResultJson("500", "未知错误！请联系管理员。", null);
        }
    }

    @RequestMapping(value = "/img/delete/{key}", method = RequestMethod.DELETE)
    @ApiOperation("根据key删除图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "key", dataType = "String", required = true)
    })
    public ResultJson deleteByImgKey(@PathVariable String key) {
        try {
            QiniuCloudUtil qiniuCloudUtil = new QiniuCloudUtil();
            qiniuCloudUtil.deleteByImgKey(key);
            return new ResultJson("204", "图片删除成功！", null);
        } catch (QiniuException e) {
            return new ResultJson("422", "图片删除失败！请稍后再试。", null);
        } catch (Exception e) {
            return new ResultJson("500", "未知错误！请联系管理员。", null);
        }
    }
}
