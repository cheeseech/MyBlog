package cn.xmh.web.blogserver.service.impl;

import cn.xmh.web.blogserver.config.PageUtils;
import cn.xmh.web.blogserver.mapper.ArticleMapper;
import cn.xmh.web.blogserver.mapper.ArticleTagsMapper;
import cn.xmh.web.blogserver.mapper.CategoryMapper;
import cn.xmh.web.blogserver.mapper.TagsMapper;
import cn.xmh.web.blogserver.model.Article;
import cn.xmh.web.blogserver.model.PageRequest;
import cn.xmh.web.blogserver.model.PageResult;
import cn.xmh.web.blogserver.model.Tags;
import cn.xmh.web.blogserver.service.ArticleService;
import cn.xmh.web.blogserver.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private ArticleTagsMapper articleTagsMapper;
    @Resource
    private TagsMapper tagsMapper;
    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        //调用分页插件完成分页
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        //设置页码以及长度
        PageHelper.startPage(pageNum, pageSize);
        //获取所有文章
        List<Article> articles = articleMapper.getAllPage();
        if(articles.isEmpty()){
            throw new NullPointerException();
        }

        //调用分页工具类完成分页信息的封装
        return PageUtils.getPageResult(pageRequest,new PageInfo<Article>(insertTagsIntoArticle(articles)));
    }

    @Override
    public PageResult getByCateNameAndTitleInPage(String cateName,String title,PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        //设置页码以及长度
        PageHelper.startPage(pageNum, pageSize);
        //根据专栏名获取文章
        List<Article> articles= articleMapper.getArticleByCateNameAndTitle(cateName,title);
        if(articles.isEmpty()){
            throw new NullPointerException();
        }

        //调用分页工具类完成分页信息的封装
        return PageUtils.getPageResult(pageRequest,new PageInfo<Article>(insertTagsIntoArticle(articles)));
    }

    @Override
    public PageResult getByTagNameAndTitleInPage(String tagName, String title,PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        //设置页码以及长度
        PageHelper.startPage(pageNum, pageSize);

        //根据标签名获取标签ID
        Long tagId=tagsMapper.getIdByName(tagName);
        //根据标签ID获取文章信息
        List<Article> articles= articleMapper.getArticleByTagId(tagId,title);
        if(articles.isEmpty()){
            throw new NullPointerException();
        }

        //调用分页工具类完成分页信息的封装
        return PageUtils.getPageResult(pageRequest,new PageInfo<Article>(insertTagsIntoArticle(articles)));
    }

    @Override
    public PageResult getLikeTitleArticleInPage(String title,PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        //设置页码以及长度
        PageHelper.startPage(pageNum, pageSize);

        List<Article> articles= articleMapper.getLikeTitleArticle(title);
        if(articles.isEmpty()){
            throw new NullPointerException();
        }

        //为文章添加标签信息
        articles=insertTagsIntoArticle(articles);

        //调用分页工具类完成分页信息的封装
        return PageUtils.getPageResult(pageRequest,new PageInfo<Article>(insertTagsIntoArticle(articles)));
    }

    /**
     * 为文章添加标签信息
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
    public List<Article> getArticleByDelete() {
        //获取删除状态的文章
        List<Article> articles= articleMapper.getArticleByDelete();
        if(articles.isEmpty()){
            throw new NullPointerException();
        }

        return articles;
    }

    @Override
    public List<Article> getArticleByNotDelete() {
        //获取未删除状态的文章
        List<Article> articles= articleMapper.getArticleByNotDelete();
        if(articles.isEmpty()){
            throw new NullPointerException();
        }

        return insertTagsIntoArticle(articles);
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void insertArticle(Article article) {
        //添加文章发布时间以及更新时间
        Date day=new Date();
        article.setUpdateTime(day);
        article.setPublishTime(day);
        //初始化文章浏览量、点赞数以及评论数
        article.setViews(0);
        article.setLikes(0);
        article.setComments(0);
        //设置文章概述
        String content=article.getMdContent();
        if(content.length() > 100){
            content=content.substring(0,101);
        }
        article.setSummary(content);
        //设置用户ID
        article.setUserId(1L);
        //新建文章
        articleMapper.insertArticle(article);

        //关联文章标签
        for(Tags tags : article.getTags()){
            articleTagsMapper.insertArticleTags(tags.getTagId(),article.getArticleId());
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void deleteByArticleId(Long articleId) {

        //删除文章标签关联
        articleTagsMapper.deleteByArticleId(articleId);

        //删除文章
        int i= articleMapper.deleteByArticleId(articleId);
        if(i != 1){
            throw new IllegalArgumentException();
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateByArticleId(Long articleId, Article article) {
        //设置文章更新时间
        Date day=new Date();
        article.setUpdateTime(day);
        //设置文章概述
        String content=article.getMdContent();
        if(content.length() > 100){
            content=content.substring(0,101);
        }
        article.setSummary(content);
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
        Date day=new Date();

        //更新文章状态
        int i= articleMapper.resetArticleState(articleState,articleId,day);
        if(i != 1){
            throw new IllegalArgumentException();
        }
    }

    @Override
    public List<Map<String, String>> getNewRecommend() {
        //获取推荐文章
        List<Map<String, String>> maps=articleMapper.getNewRecommend();
        if(maps.isEmpty()){
            throw new NullPointerException();
        }

        return maps;
    }

    @Override
    public List<Map<String, String>> getNewTitle() {
        //获取最新文章
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
    public List<Map<String, Object>> getTimeLine() {
        //Map中的两个键
        String keyMonth="months";
        String keyYear="years";

        //获取年份以及文章数量
        List<Map<String,Object>> years=articleMapper.getYearsCountArticle();
        for(Map<String,Object> y : years){
            //根据年份获取月份以及文章数量
            String year=y.get(keyYear).toString();
            List<Map<String, Object>> months = articleMapper.getMonthsByYear(year);

            //遍历月份集合来添加文章信息
            for(Map<String,Object> m : months){
                //根据年份以及月份获取文章信息
                String month=m.get(keyMonth).toString();
                List<Map<String, Object>> info=articleMapper.getInfoByMonthYear(year,month);
                m.put("info",info);
            }
            //添加月份以及文章信息
            y.put("months", months);
        }
        return years;
    }

    @Override
    public Map<String, String> getTotalData() {
        //获取总文章数、总浏览量、总点赞数以及总评论数
        Map<String,String> totalData=articleMapper.getTotalData();
        if(totalData.isEmpty()){
            throw new NullPointerException();
        }

        return totalData;
    }

    @Override
    public Map<String, Integer> getInfo() {
        //获取文章数量、专栏数量以及标签数量
        Map<String, Integer> info=new HashMap<>();
        Integer article=articleMapper.getArticleNum();
        Integer category=categoryMapper.getCategoryNum();
        Integer tag=tagsMapper.getTagsNum();

        info.put("article",article);
        info.put("category",category);
        info.put("tag",tag);
        return info;
    }

    @Override
    public Map<String, Object> getArticleInfoById(Long articleId) {
        Map<String, Object> articleInfo=articleMapper.getArticleInfoById(articleId);
        if(articleInfo.isEmpty()){
            throw new NullPointerException();
        }
        List<Tags> tags=tagsMapper.getTagsByArticleId(articleId);
        articleInfo.put("tags",tags);
        return articleInfo;
    }

    @Override
    public Map<String, Object> getMarkdownInfoById(Long articleId) {
        Map<String, Object> articleInfo=articleMapper.getMarkdownInfoById(articleId);
        if(articleInfo.isEmpty()){
            throw new NullPointerException();
        }
        List<Tags> tags=tagsMapper.getTagsByArticleId(articleId);
        articleInfo.put("tags",tags);

        return articleInfo;
    }
}

