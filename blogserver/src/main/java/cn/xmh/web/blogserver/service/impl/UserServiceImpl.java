package cn.xmh.web.blogserver.service.impl;

import cn.xmh.web.blogserver.mapper.RoleMapper;
import cn.xmh.web.blogserver.mapper.UserMapper;
import cn.xmh.web.blogserver.model.Role;
import cn.xmh.web.blogserver.model.User;
import cn.xmh.web.blogserver.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Xmh
 * @date 2020/7/29 15:44
 */
@Service
public class UserServiceImpl implements UserService , UserDetailsService {

    Logger logger=LoggerFactory.getLogger(getClass());

    @Resource
    private UserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        logger.info("登录用户名:{}",userName);
        User user=userMapper.getUserByUserName(userName);

        if(user == null){
            //避免返回null，这里返回一个不含有任何值的User对象，在后期的密码比对过程中一样会验证失败
            return new User();
        }
        String passWord=passwordEncoder.encode(user.getPassword());
        logger.info("登录密码:{}",passWord);
        user.setPassword(passWord);

        //添加用户的角色信息
        List<Role> roles=roleMapper.getRoleByUserId(user.getUserId());
        user.setRoles(roles);
        return user;
    }

    @Override
    public User getUserByUserName(String userName) {
        User user=userMapper.getUserByUserName(userName);
        if(user == null){
            throw new NullPointerException();
        }
        return user;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void insertUser(User user) {
        int i=userMapper.insertUser(user);
        if(i != 1){
            throw new IllegalArgumentException();
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void deleteByUserId(Long userId) {
        int i=userMapper.deleteByUserId(userId);
        if(i != 1){
            throw new IllegalArgumentException();
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateByUserId(Long userId, User user) {
        int i= userMapper.updateByUserId(userId,user);
        if(i != 1){
            throw new IllegalArgumentException();
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateUserState(Integer userState, Long id) {
        int i= userMapper.updateUserState(userState,id);
        if(i != 1){
            throw new IllegalArgumentException();
        }
    }

    @Override
    public List<User> getAllUser() {
        List<User> users= userMapper.getAllUser();
        if(users.isEmpty()){
            throw new NullPointerException();
        }
        return users;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void resetPassword(Long userId, String password) {
        int i= userMapper.resetPassword(userId,password);
        if(i != 1){
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void setUserRole(Long userId, Long roleId) {
        int i=userMapper.setUserRole(userId,roleId);
        if(i != 1){
            throw new IllegalArgumentException();
        }
    }
}
