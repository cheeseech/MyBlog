package cn.xmh.web.blogserver.service;

import cn.xmh.web.blogserver.model.PageRequest;
import cn.xmh.web.blogserver.model.PageResult;
import cn.xmh.web.blogserver.model.Tags;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Xmh
 * @date 2020/7/29 15:42
 * 标签业务层
 */
public interface TagsService {

    /**
     * 获取所有标签集合
     *
     * @return 标签集合
     */
    List<Tags> listAll();

    /**
     * 分页获取标签信息
     *
     * @param pageRequest 同一分页请求对象
     * @return 标签信息
     */
    PageResult getByPage(PageRequest pageRequest);

    /**
     * 根据ID删除一个标签
     *
     * @param tagId 标签ID
     */
    void removeById(Long tagId);

    /**
     * 添加一个标签
     *
     * @param tags 标签信息
     * @throws SQLException
     */
    void save(Tags tags) throws SQLException;

    /**
     * 更新一个标签
     *
     * @param tags 标签信息
     * @throws SQLException
     */
    void updateById(Tags tags) throws SQLException;

    /**
     * 获取标签及标签文章数
     *
     * @return
     */
    List<Map<String, String>> listByArticleCount();

    /**
     * 从大到小获取标签及标签文章数
     *
     * @return 标签及标签文章数
     */
    List<Map<String, Object>> listByArticleCountAndOrder();

    /**
     * 根据名称模糊查找标签及标签文章数
     *
     * @param tagName
     * @return
     */
    List<Map<String, String>> listByArticleCountLikeName(String tagName);

}
