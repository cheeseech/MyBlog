package cn.xmh.web.blogserver.service.impl;

import cn.xmh.web.blogserver.mapper.ArticleMapper;
import cn.xmh.web.blogserver.mapper.ArticleTagsMapper;
import cn.xmh.web.blogserver.mapper.TagsMapper;
import cn.xmh.web.blogserver.model.Article;
import cn.xmh.web.blogserver.model.Tags;
import cn.xmh.web.blogserver.service.ArticleService;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.schema.Entry;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Xmh
 * @date 2020/7/29 15:44
 */
@Service
public class ArticleServiceImpl implements ArticleService
{

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private ArticleTagsMapper articleTagsMapper;
    @Resource
    private TagsMapper tagsMapper;

    @Override
    public List<Article> getAllArticle() {
        List<Article> articles= articleMapper.getAllArticle();

        return insertTagsIntoArticle(articles);
    }

    @Override
    public List<Article> getArticleByDe() {
        List<Article> articles= articleMapper.getArticleByDe();
        if(articles.isEmpty()){
            throw new NullPointerException();
        }
        return articles;
    }

    @Override
    public List<Article> getArticleByNoDe() {
        List<Article> articles= articleMapper.getArticleByNoDe();

        return insertTagsIntoArticle(articles);
    }

    /**
     * 为文章集合添加标签信息
     * @param articles
     * @return
     */
    public List<Article> insertTagsIntoArticle(List<Article> articles){
        if(articles.isEmpty()){
            throw new NullPointerException();
        }

        //获取文章标签集合
        for(Article article:articles){
            article.setTags(tagsMapper.getTagsByArticleId(article.getArticleId()));
        }
        return articles;
    }

    @Override
    public List<Article> getLikeTitleArticle(String title) {
        List<Article> articles= articleMapper.getLikeTitleArticle(title);
        if(articles.isEmpty()){
            throw new NullPointerException();
        }

        //获取文章标签集合
        for(Article article:articles){
            article.setTags(tagsMapper.getTagsByArticleId(article.getArticleId()));
        }
        return articles;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void insertArticle(Article article) {
        //新建文章
        int i= articleMapper.insertArticle(article);
        if(i != 1){
            throw new IllegalArgumentException();
        }

        //关联文章标签
        for(Tags tags : article.getTags()){
            articleTagsMapper.insertArticleTags(tags.getTagId(),article.getArticleId());
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void deleteByArticleId(Long articleId) {
        Article article=articleMapper.getArticleById(articleId);
        //若文章是草稿箱状态则直接删除
        if(article.getArticleState() == 0){
            //删除文章标签关联
            articleTagsMapper.deleteByArticleId(articleId);

            //删除文章
            int i= articleMapper.deleteByArticleId(articleId);
            if(i != 1){
                throw new IllegalArgumentException();
            }
        }else {
            //若文章是已发布状态，则将其修改为已删除状态并且添加删除时间
            article.setArticleState(-1);
            article.setUpdateTime(new Date());
            int i=articleMapper.updateByArticleId(articleId,article);
            if(i != 1){
                throw new IllegalArgumentException();
            }
        }

    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateByArticleId(Long articleId, Article article) {
        //更新文章
        int i= articleMapper.updateByArticleId(articleId,article);
        if(i != 1){
            throw new IllegalArgumentException();
        }

        //更新文章标签
        for(Tags tags : article.getTags()){
            articleTagsMapper.insertArticleTags(tags.getTagId(),article.getArticleId());
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void resetArticleState(Integer articleState, Long articleId) {
        int i= articleMapper.resetArticleState(articleState,articleId);
        if(i != 1){
            throw new IllegalArgumentException();
        }
    }

    @Override
    public List<Map<String, String>> getNewRecommend() {
        List<Map<String, String>> maps=articleMapper.getNewRecommend();
        if(maps.isEmpty()){
            throw new NullPointerException();
        }
        return maps;
    }

    @Override
    public List<Map<String, String>> getNewTitle() {
        List<Map<String, String>> maps=articleMapper.getNewTitle();
        if(maps.isEmpty()){
            throw new NullPointerException();
        }
        return maps;
    }

    @Override
    public List<Article> getTypeArticle(String typeName) {
        List<Article> articles= articleMapper.getTypeArticle(typeName);
        if(articles.isEmpty()){
            throw new NullPointerException();
        }

        //获取文章标签集合
        for(Article article:articles){
            article.setTags(tagsMapper.getTagsByArticleId(article.getArticleId()));
        }
        return articles;
    }

    @Override
    public List<Article> getArticleByTagId(Long tagId) {
        List<Article> articles= articleMapper.getArticleByTagId(tagId);
        if(articles.isEmpty()){
            throw new NullPointerException();
        }

        //获取文章标签集合
        for(Article article:articles){
            article.setTags(tagsMapper.getTagsByArticleId(article.getArticleId()));
        }
        return articles;
    }

    @Override
    public List<Article> getArticleByCateName(String cateName) {
        List<Article> articles= articleMapper.getArticleByCateName(cateName);
        if(articles.isEmpty()){
            throw new NullPointerException();
        }

        //获取文章标签集合
        for(Article article:articles){
            article.setTags(tagsMapper.getTagsByArticleId(article.getArticleId()));
        }
        return articles;
    }

    @Override
    public List<Map<String, Object>> getTimeLine() {
        //Map中的两个键
        String keyMonth="months";
        String keyYear="years";

        List<Map<String,Object>> years=articleMapper.getYearsCountArticle();
        for(Map<String,Object> y : years){
            String year=y.get(keyYear).toString();
            List<Map<String, Object>> months = articleMapper.getMonthsByYear(year);

            for(Map<String,Object> m : months){
                String month=m.get(keyMonth).toString();
                List<Map<String, Object>> info=articleMapper.getInfoByMonthYear(year,month);
                m.put("info",info);
            }
            y.put("months", months);
        }
        return years;
    }

    @Override
    public Map<String, String> getTotalData() {
        Map<String,String> totalData=articleMapper.getTotalData();
        if(totalData.isEmpty()){
            throw new NullPointerException();
        }
        return totalData;
    }
}

