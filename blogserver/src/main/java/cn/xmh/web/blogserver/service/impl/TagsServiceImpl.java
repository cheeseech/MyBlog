package cn.xmh.web.blogserver.service.impl;

import cn.xmh.web.blogserver.mapper.ArticleTagsMapper;
import cn.xmh.web.blogserver.mapper.TagsMapper;
import cn.xmh.web.blogserver.model.Tags;
import cn.xmh.web.blogserver.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    public void insertTag(Tags tags) {
        int i= tagsMapper.insertTag(tags);
        if(i != 1){
            throw new IllegalArgumentException();
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateTagById(Tags tags) {
        int i=tagsMapper.updateTagById(tags);
        if(i != 1){
            throw new IllegalArgumentException();
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
}
