package cn.xmh.web.blogserver.service.impl;

import cn.xmh.web.blogserver.mapper.UserMapper;
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
import java.sql.SQLException;
import java.util.Date;
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

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        logger.info("登录用户名:{}",userName);
        User user=userMapper.getUserByUserName(userName);
        if(user == null){
            //避免返回null，这里返回一个不含有任何值的User对象，在后期的密码比对过程中一样会验证失败
            return new User(true);
        }
        String passWord=passwordEncoder.encode(user.getPassword());
        logger.info("登录密码:{}",passWord);
        user.setPassword(passWord);

        return user;
    }

    @Override
    public User getUserByUserName(String userName) {
        //根据用户名获取用户
        User user=userMapper.getUserByUserName(userName);
        if(user == null){
            throw new NullPointerException();
        }

        return user;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void insertUser(User user) throws SQLException {
        //检查用户名是否重复
        User checkUserName=userMapper.getUserByUserName(user.getUsername());
        if(checkUserName != null){
            throw new IllegalArgumentException();
        }

        //创建时间、更新时间
        Date date=new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);

        //用户状态
        int enabled=user.getEnabled()?1:0;

        //新增用户
        int i=userMapper.insertUser(user,enabled);
        if(i != 1){
            throw new SQLException();
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void deleteByUserId(Long userId) {
        //根据ID删除用户
        int i=userMapper.deleteByUserId(userId);
        if(i != 1){
            throw new IllegalArgumentException();
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateByUserId(User user) throws SQLException {
        //检查用户名是否重复
        User checkUserName=userMapper.getUserByUserName(user.getUsername());
        if(checkUserName != null){
            //ID相同时表示未修改用户名，否则用户名重复
            if(!checkUserName.getUserId().equals(user.getUserId())){
                throw new IllegalArgumentException();
            }
        }
        //设置更新时间
        user.setUpdateTime(new Date());
        //用户状态
        int enabled=user.getEnabled()?1:0;
        //更新用户
        int i= userMapper.updateByUserId(user.getUserId(),user,enabled);
        if(i != 1){
            throw new SQLException();
        }
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Error.class})
    public void updateUserState(Boolean userState, Long id) {
        //设置用户状态
        int i= userMapper.updateUserState(userState ? 1 : 0,id);
        if(i != 1){
            throw new IllegalArgumentException();
        }
    }

    @Override
    public List<User> getAllUser() {
        //获取所有用户信息
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
}
