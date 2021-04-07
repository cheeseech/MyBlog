package cn.xmh.web.blogserver.config;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.sun.org.apache.bcel.internal.generic.RET;

import java.lang.annotation.Retention;

/**
 * @author Xmh
 * @date 2021/4/5 22:11
 */
public class QiniuCloudUtil {

    /**
     * 简单上传凭证，AccessKey、SecretKey、Bucket
     */
    private static final String ACCESS_KEY="xYLhfS1tiVu4NKzCPiHgibewxghVz4ZSGAVj0yaF";
    private static final String SECRET_KEY="DhH0VZhAeOT_793brBB6oinsQA7HCiMU5wN5ftUE";
    private static final String BUCKET_NAME="cheeseblog";
    /**
     * 生成密钥
     */
    private static final Auth auth=Auth.create(ACCESS_KEY,SECRET_KEY);

    /**
     * 生成七牛云上传凭证
     * @return token
     */
    public static String getUpToken(){
        StringMap putPolicy=new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        long expireSeconds = 3600;
        return auth.uploadToken(BUCKET_NAME, null, expireSeconds, putPolicy);
    }

    /**
     * 根据key删除七牛云图片
     * @param key
     * @throws QiniuException
     */
    public void deleteByImgKey(String key) throws QiniuException{
        Configuration cfg=new Configuration(Region.region1());
        BucketManager bucketManager=new BucketManager(auth, cfg);
        bucketManager.delete(BUCKET_NAME,key);
    }
}
