package cn.xmh.web.blogserver.service.impl;

import cn.xmh.web.blogserver.config.PageUtils;
import cn.xmh.web.blogserver.mapper.ArticleTagsMapper;
import cn.xmh.web.blogserver.mapper.TagsMapper;
import cn.xmh.web.blogserver.model.PageRequest;
import cn.xmh.web.blogserver.model.PageResult;
import cn.xmh.web.blogserver.model.Tags;
import cn.xmh.web.blogserver.service.TagsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xmh
 * @date 2020/7/29 15:44
 */
@Service
public class TagsServiceImpl implements TagsService {

    @Resource
    private TagsMapper tagsMapper;
    @Resource
    private ArticleTagsMapper articleTagsMapper;

    @Override
    public List<Tags> getAllTags() {

        List<Tags> tags=tagsMapper.getAllTags();
        // 标签信息判空
        if(tags.isEmpty()){
            throw new NullPointerException();
        }

        return tagsMapper.getAllTags();
    }

    @Override
    public PageResult getTagsByPage(PageRequest pageRequest) {
        int pageSize=pageRequest.getPageSize();
        int pageNum=pageRequest.getPageNum();
        // 设置页码以及长度
        PageHelper.startPage(pageNum,pageSize);

        List<Tags> tags=tagsMapper.getAllTags();
        // 标签信息判空
        if(tags.isEmpty()){
            throw new NullPointerException();
        }

        return PageUtils.getPageResult(pageRequest,new PageInfo<>(tags));
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void deleteByTagId(Long tagId) {
        //删除文章标签关联
        articleTagsMapper.deleteByTagId(tagId);

        //删除标签
        int i=tagsMapper.deleteByTagId(tagId);
        if(i != 1){
            throw new IllegalArgumentException();
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void insertTag(Tags tags) throws SQLException {

        //判断标签名是否相同
        Tags checkTag=tagsMapper.getTagsName(tags.getTagName());
        if(checkTag!=null){
            throw  new IllegalArgumentException();
        }

        // 新建标签
        int i= tagsMapper.insertTag(tags);
        if(i != 1){
            throw new SQLException();
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateTagById(Tags tags) throws SQLException {

        //判断标签名是否相同
        Tags checkTag=tagsMapper.getTagsName(tags.getTagName());
        if(checkTag!=null){
            //当表单传值的id等于根据标签名查找的id时表示更改了标签信息，
            //否则表示标签名重复
            if(!tags.getTagId().equals(checkTag.getTagId())){
                throw  new IllegalArgumentException();
            }
        }

        int i=tagsMapper.updateTagById(tags);
        if(i != 1){
            throw new SQLException();
        }
    }

    @Override
    public List<Map<String, String>> getTagsAndCount() {
        List<Map<String, String>> maps=tagsMapper.getTagsAndCount();
        // 标签及标签文章数信息判空
        if(maps.isEmpty()){
            throw new NullPointerException();
        }

        return maps;
    }

    @Override
    public List<Map<String, Object>> getTagsAndCountOrder() {
        List<Map<String, Object>> tagsCount=tagsMapper.getTagsAndCountOrder();
        //标签及标签及标签文章信息判空
        if(tagsCount.isEmpty()){
            throw new NullPointerException();
        }

        return tagsCount;
    }

    @Override
    public List<Map<String, String>> getTagsAndCountLike(String tagName) {
        List<Map<String, String>> tags=tagsMapper.getTagsAndCountLike(tagName);
        // 标签及标签文章信息判空
        if(tags.isEmpty()){
            throw new NullPointerException();
        }

        return tags;
    }
}
