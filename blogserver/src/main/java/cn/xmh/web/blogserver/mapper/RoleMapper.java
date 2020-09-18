package cn.xmh.web.blogserver.mapper;

import cn.xmh.web.blogserver.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Xmh
 * @date 2020/9/9 10:49
 */
@Mapper
public interface RoleMapper {

    /**
     * 添加角色信息
     * @param roleId 角色ID
     * @param roleName 角色名
     * @return 受影响的行
     */
    int insertRole(@Param("role_id") Long roleId,@Param("role_name") String roleName);

    /**
     * 根据用户ID获取角色集合
     * @param userId 用户ID
     * @return 角色集合
     */
    List<Role> getRoleByUserId(@Param("user_id") Long userId);
}
