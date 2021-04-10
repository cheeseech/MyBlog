package cn.xmh.web.blogserver.service.impl;

import cn.xmh.web.blogserver.mapper.*;
import cn.xmh.web.blogserver.model.*;
import cn.xmh.web.blogserver.service.ArticleService;
import cn.xmh.web.blogserver.utils.IPUtil;
import cn.xmh.web.blogserver.utils.PageUtil;
import cn.xmh.web.blogserver.utils.RedisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xmh
 * @date 2020/7/29 15:44
 * 文章业务层实现类
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private TagsMapper tagsMapper;
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private DaysDataMapper daysDataMapper;
    @Resource
    private ArticleTagsMapper articleTagsMapper;

    @Override
    public PageResult getByPage(PageRequest pageRequest) {
        // 调用分页插件完成分页
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        // 设置页码以及长度
        PageHelper.startPage(pageNum, pageSize);

        // 获取所有文章
        List<Article> articles = articleMapper.listByPage();
        // 文章信息判空
        hasArticle(articles);

        return PageUtil.getPageResult(pageRequest, new PageInfo<>(insertTagsIntoArticle(articles)));
    }

    @Override
    public PageResult getByCateNameAndTitleInPage(String cateName, String title, PageRequest pageRequest) {
        // 调用分页插件完成分页
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        // 设置页码以及长度
        PageHelper.startPage(pageNum, pageSize);
        // 根据专栏名获取文章
        List<Article> articles = articleMapper.listByCateNameAndTitle(cateName, title);
        // 文章信息判空
        hasArticle(articles);

        return PageUtil.getPageResult(pageRequest, new PageInfo<>(insertTagsIntoArticle(articles)));
    }

    @Override
    public PageResult getByTagNameAndTitleInPage(String tagName, String title, PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        // 设置页码以及长度
        PageHelper.startPage(pageNum, pageSize);

        // 根据标签名获取标签ID
        Long tagId = tagsMapper.getIdByName(tagName);
        // 根据标签ID获取文章信息
        List<Article> articles = articleMapper.listByTagId(tagId, title);
        // 文章信息判空
        hasArticle(articles);

        return PageUtil.getPageResult(pageRequest, new PageInfo<>(insertTagsIntoArticle(articles)));
    }

    @Override
    public PageResult getLikeTitleInPage(String title, PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        // 设置页码以及长度
        PageHelper.startPage(pageNum, pageSize);

        // 根据标题获取文章信息
        List<Article> articles = articleMapper.listLikeByTitle(title);
        // 文章信息判空
        hasArticle(articles);

        return PageUtil.getPageResult(pageRequest, new PageInfo<>(insertTagsIntoArticle(articles)));
    }

    @Override
    public PageResult getByDelete(PageRequest pageRequest) {
        int pageSize = pageRequest.getPageSize();
        int pageNum = pageRequest.getPageNum();
        // 设置页码及长度
        PageHelper.startPage(pageNum, pageSize);

        // 获取删除状态的文章
        List<Article> articles = articleMapper.listByDelete();
        // 文章信息判空
        hasArticle(articles);

        return PageUtil.getPageResult(pageRequest, new PageInfo<>(articles));
    }

    @Override
    public PageResult getByNotDelete(PageRequest pageRequest) {
        int pageSize = pageRequest.getPageSize();
        int pageNum = pageRequest.getPageNum();
        // 设置页码及长度
        PageHelper.startPage(pageNum, pageSize);

        // 获取未删除状态的文章
        List<Article> articles = articleMapper.listByNotDelete();
        // 文章信息判空
        hasArticle(articles);

        return PageUtil.getPageResult(pageRequest, new PageInfo<>(insertTagsIntoArticle(articles)));
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void saveArticle(Article article) {
        // 检查文章标题是否重复
        List<Article> checkedTitle = articleMapper.listByTitle(article.getTitle());
        if (!checkedTitle.isEmpty()) {
            throw new IllegalArgumentException();
        }

        // 添加文章发布时间以及更新时间
        Date day = new Date();
        article.setUpdateTime(day);
        article.setPublishTime(day);

        // 初始化文章浏览量、点赞数以及评论数
        article.setViews(0);
        article.setComments(0);

        // 从security获取当前登录用户
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authenticationToken.getPrincipal();
        // 设置用户ID
        article.setUserId(user.getUserId());

        // 新建文章
        articleMapper.save(article);

        // 关联文章标签
        for (Tags tags : article.getTags()) {
            articleTagsMapper.insertArticleTags(tags.getTagId(), article.getArticleId());
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void removeById(Long articleId) {
        // 删除文章标签关联
        articleTagsMapper.deleteByArticleId(articleId);

        // 删除文章日数据关联
        daysDataMapper.removeByArticleId(articleId);

        // 删除文章
        int i = articleMapper.removeById(articleId);
        if (i != 1) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateById(Long articleId, Article article) {
        // 检查文章标题是否重复
        List<Article> checkedTitle = articleMapper.listByTitle(article.getTitle());
        if (!checkedTitle.isEmpty()) {
            // 当表单传值的id等于根据标题查找的id时表示更改了文章信息，
            // 否则表示文章标题重复
            if (!articleId.equals(checkedTitle.get(0).getArticleId())) {
                throw new IllegalArgumentException();
            }
        }

        // 设置文章更新时间
        Date day = new Date();
        article.setUpdateTime(day);

        // 更新文章
        int i = articleMapper.updateById(article);
        if (i != 1) {
            throw new IllegalArgumentException();
        }
        // 删除文章标签关联
        articleTagsMapper.deleteByArticleId(articleId);
        // 更新文章标签关联
        for (Tags tags : article.getTags()) {
            articleTagsMapper.insertArticleTags(tags.getTagId(), article.getArticleId());
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateState(Integer articleState, Long articleId) {

        // 更新文章状态
        int i = articleMapper.updateState(articleState, articleId, new Date());
        if (i != 1) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public List<Map<String, String>> listNewRecommend() {
        // 获取推荐文章
        List<Map<String, String>> maps = articleMapper.listRecommendByTop5();
        // 文章信息判空
        if (maps.isEmpty()) {
            throw new NullPointerException();
        }

        return maps;
    }

    @Override
    public List<Map<String, String>> listNew() {
        // 获取最新文章信息
        List<Map<String, String>> maps = articleMapper.listNewByTop4();
        // 文章信息判空
        if (maps.isEmpty()) {
            throw new NullPointerException();
        }

        return maps;
    }

    @Override
    public List<Map<String, Object>> countYearMonthInfo() {
        // Map中的两个键，月份、年份
        String keyYear = "years";
        String keyMonth = "months";

        // 获取年份以及文章数量
        List<Map<String, Object>> years = articleMapper.listYearsCountArticle();

        for (Map<String, Object> y : years) {
            // 根据年份获取月份以及文章数量
            String year = y.get(keyYear).toString();
            List<Map<String, Object>> months = articleMapper.listMonthsByYear(year);

            // 遍历月份集合来添加文章信息
            for (Map<String, Object> m : months) {
                // 根据年份以及月份获取文章信息
                String month = m.get(keyMonth).toString();
                List<Map<String, Object>> info = articleMapper.listInfoByMonthYear(year, month);
                m.put("info", info);
            }
            // 添加月份以及文章信息
            y.put("months", months);
        }
        return years;
    }

    @Override
    public Map<String, String> countTotalData() {
        // 获取总文章数、总浏览量、总点赞数以及总评论数
        Map<String, String> totalData = articleMapper.getTotalData();
        // 文章信息判空
        if (totalData.isEmpty()) {
            throw new NullPointerException();
        }

        return totalData;
    }

    @Override
    public Map<String, Integer> countArticleCateAndTag() {
        // 获取文章数量、专栏数量以及标签数量
        Map<String, Integer> info = new HashMap<>(3);
        Integer article = articleMapper.getCountArticle();
        Integer category = categoryMapper.getCategoryCount();
        Integer tag = tagsMapper.getTagsCount();

        info.put("article", article);
        info.put("category", category);
        info.put("tag", tag);
        return info;
    }

    @Override
    public Map<String, Object> getInfoById(Long articleId, HttpServletRequest request) {
        // 获取当前访问ip
        String ipAddress = IPUtil.getIpAddr(request);
        // 设置redis key
        String checkedKey = ipAddress + "-" + articleId;
        // 如果redis中不存在当前key则添加key并更新文章浏览数
        if (!redisUtil.hasKey(checkedKey)) {
            redisUtil.set(checkedKey, ipAddress, 86400);
            // 更新文章浏览量
            articleMapper.updateViews(articleId);
            // 更新日期数据文章浏览量
            String day = getDateToString();
            daysDataMapper.updateViewsByDaysAndArticleId(day, articleId);
        }
        // 否则不更新文章浏览数
        Map<String, Object> articleInfo = articleMapper.getInfoById(articleId);
        // 文章信息判空
        if (articleInfo.isEmpty()) {
            throw new NullPointerException();
        }

        // 添加文章下标签集合
        List<Tags> tags = articleTagsMapper.getTagsByArticleId(articleId);
        articleInfo.put("tags", tags);
        return articleInfo;
    }

    @Override
    public Map<String, Object> getMarkdownById(Long articleId) {
        // 获取文章Markdown数据
        Map<String, Object> articleInfo = articleMapper.getMarkdownById(articleId);
        // markdown数据判空
        if (articleInfo.isEmpty()) {
            throw new NullPointerException();
        }

        // 添加文章下标签信息集合
        List<Map<String, Object>> tags = tagsMapper.listByArticleIdOnAdmin(articleId);
        articleInfo.put("tags", tags);

        return articleInfo;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateCommentsById(Long articleId, int comments) {
        // 更新文章评论数
        int i = articleMapper.updateComments(articleId, comments);

        // 更新日期数据文章评论数
        String day = getDateToString();
        int j = daysDataMapper.updateCommentsByDaysAndArticleId(day, articleId);
        if (i != 1 || j != 1) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 文章信息判空
     *
     * @param articles 文章信息
     */
    private void hasArticle(List<Article> articles) {
        // 文章信息判空
        if (articles.isEmpty()) {
            throw new NullPointerException();
        }
    }

    /**
     * 为文章添加标签信息
     *
     * @param articles
     * @return
     */
    public List<Article> insertTagsIntoArticle(List<Article> articles) {
        hasArticle(articles);

        //获取文章标签集合
        for (Article article : articles) {
            article.setTags(articleTagsMapper.getTagsByArticleId(article.getArticleId()));
        }
        return articles;
    }

    /**
     * 获取当亲时间转字符串
     *
     * @return 时间字符串
     */
    private String getDateToString() {
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd");
        return df.format(new Date());
    }
}

