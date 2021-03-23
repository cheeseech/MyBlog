package cn.xmh.web.blogserver.mapper;

import cn.xmh.web.blogserver.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Xmh
 * @date 2020/7/28 21:49
 */
@Mapper
public interface UserMapper {

    /**
     * 根据用户名获取用户信息
     * @param userName 用户名
     * @return 用户信息
     */
    User getUserByUserName(@Param("userName") String userName);

    /**
     * 添加用户
     * @param user 用户信息
     * @param enabled 用户状态
     * @return 受影响的行
     */
    int insertUser(User user,int enabled);

    /**
     * 根据ID删除用户
     * @param userId 用户ID
     * @return 受影响的行
     */
    int deleteByUserId(@Param("userId") Long userId);

    /**
     * 根据ID更新用户信息
     * @param userId 用户ID
     * @param user 用户信息
     * @param enabled 用户状态
     * @return 受影响的行
     */
    int updateByUserId(@Param("userId") Long userId,User user,int enabled);

    /**
     * 更新用户状态
     * @param userState 用户状态
     * @param id 用户ID
     * @return 受影响的行
     */
    int updateUserState(@Param("userState") Integer userState,@Param("userId") Long id);

    /**
     * 获取所有用户集合
     * @return 用户集合
     */
    List<User> getAllUser();
}
