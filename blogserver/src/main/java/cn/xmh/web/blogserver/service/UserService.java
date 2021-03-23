package cn.xmh.web.blogserver.service;

import cn.xmh.web.blogserver.model.PageRequest;
import cn.xmh.web.blogserver.model.PageResult;
import cn.xmh.web.blogserver.model.User;

import java.sql.SQLException;

/**
 * @author Xmh
 * @date 2020/7/29 15:42
 */
public interface UserService {

    /**
     * 添加一个用户
     * @param user 用户信息
     * @throws SQLException
     */
    void insertUser(User user) throws SQLException;

    /**
     * 根据ID删除用户
     * @param userId 用户ID
     */
    void deleteByUserId(Long userId);

    /**
     * 根据ID更新用户信息
     * @param user 用户信息
     * @throws SQLException
     */
    void updateByUserId(User user) throws SQLException;

    /**
     * 更新用户状态
     * @param userState 用户状态
     * @param id 用户ID
     */
    void updateUserState(Boolean userState,Long id);

    /**
     * 分页获取用户数据集合
     * @param pageRequest 统一分页查询请求
     * @return 用户数据集合
     */
    PageResult getAllUser(PageRequest pageRequest);
}
