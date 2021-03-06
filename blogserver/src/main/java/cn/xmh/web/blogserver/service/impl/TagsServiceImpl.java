package cn.xmh.web.blogserver.service.impl;

import cn.xmh.web.blogserver.mapper.ArticleTagsMapper;
import cn.xmh.web.blogserver.mapper.TagsMapper;
import cn.xmh.web.blogserver.model.Tags;
import cn.xmh.web.blogserver.service.TagsService;
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
        if(tags.isEmpty()){
            throw new NullPointerException();
        }
        return tagsMapper.getAllTags();
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
            //当表单传值的id等于根据标签名查找的id时表示只进行了标签类型的更改，
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
    public Tags getTagsName(String tagName) {
        Tags tags= tagsMapper.getTagsName(tagName);
        if(tags == null){
            throw new NullPointerException();
        }
        return tags;
    }

    @Override
    public List<Tags> getTagsByArticleId(Long articleId) {
        List<Tags> tags= tagsMapper.getTagsByArticleId(articleId);
        if(tags.isEmpty()){
            throw new NullPointerException();
        }
        return tags;
    }

    @Override
    public List<Map<String, String>> getTagsAndCount() {
        List<Map<String, String>> maps=tagsMapper.getTagsAndCount();
        if(maps.isEmpty()){
            throw new NullPointerException();
        }
        return maps;
    }

    @Override
    public List<Map<String, Object>> getTagsAndCountOrder() {
        //获取标签及标签文章数
        List<Map<String, Object>> tagsCount=tagsMapper.getTagsAndCountOrder();
        if(tagsCount.isEmpty()){
            throw new NullPointerException();
        }

        return tagsCount;
    }

    @Override
    public List<Map<String, String>> getTagsAndCountLike(String tagName) {
        List<Map<String, String>> tags=tagsMapper.getTagsAndCountLike(tagName);
        if(tags.isEmpty()){
            throw new NullPointerException();
        }
        return tags;
    }
}
