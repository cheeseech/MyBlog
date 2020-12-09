package cn.xmh.web.blogserver.service;

import cn.xmh.web.blogserver.model.User;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Xmh
 * @date 2020/7/29 15:42
 */
public interface UserService {

    /**
     * 根据用户名获取用户信息
     * @param userName 用户名
     * @return 用户信息
     */
    User getUserByUserName(String userName);

    /**
     * 添加用户
     * @param user 用户信息
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
     */
    void updateByUserId(User user) throws SQLException;

    /**
     * 更新用户状态
     * @param userState 用户状态
     * @param id 用户ID
     */
    void updateUserState(Boolean userState,Long id);

    /**
     * 获取所有用户集合
     * @return 用户集合
     */
    List<User> getAllUser();

    /**
     * 根据ID更新密码
     * @param userId 用户ID
     * @param password 密码
     */
    void resetPassword(Long userId,String password);
}
