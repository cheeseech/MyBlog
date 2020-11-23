package cn.xmh.web.blogserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * Spring Security框架提供了一个基础用户接口UserDetails，该接口提供了基本的用户相关的操作，
 * 比如获取用户名/密码、用户账号是否过期和用户认证是否过期等，我们定义自己的User类时需要实现该接口。
 */

/**
 * @author Xmh
 * @date 2020/7/28 20:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    private Long userId;
    private String userFace;
    private String userName;
    private String nickName;
    private String password;
    private String email;
    private Date createTime;
    private Date updateTime;
    private List<Role> roles;
    private Boolean enabled;

    /**
     * 账号未过期
     * @return
     */
    @Override
    @JsonIgnore
    /**
     * json序列化时将java bean中的一些属性忽略掉
     */
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账号未被锁定
     * @return
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 身份认证是否有效
     * @return
     */
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账号是否激活
     * @return
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        //根据自定义逻辑返回用户权限即角色
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    public User(Boolean enabled) {
        this.enabled = enabled;
    }
}
