package cn.xmh.web.blogserver.config;


import cn.xmh.web.blogserver.model.ResultJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.PrintWriter;


/**
 * Security权限管理
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            //开启登录配置
            .authorizeRequests()
                //表示访问/admin接口需要登录.hasRole("admin")
                .antMatchers("/admin/**").authenticated()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                //剩余接口放行
                .anyRequest().permitAll()
                .and()
            //定义表单登录，身份认证方式
            .formLogin()
                .loginProcessingUrl("/login")
                .permitAll()
                //登录成功处理
                .successHandler((request, response, authentication) -> {
                    response.setContentType("application/json; charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    ResultJson resultJson=new ResultJson("200","恭喜，登录成功！",null);
                    out.write(new ObjectMapper().writeValueAsString(resultJson));
                    out.flush();
                    out.close();
                })
                //登录失败处理
                .failureHandler((request, response, e) -> {
                    response.setContentType("application/json; charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    ResultJson resultJson = new ResultJson();

                    resultJson.setStatus("401.1");
                    if (e instanceof LockedException) {
                        resultJson.setMsg("账户被锁定，请联系管理员!");
                    } else if (e instanceof CredentialsExpiredException) {
                        resultJson.setMsg("密码过期，请联系管理员!");
                    } else if (e instanceof AccountExpiredException) {
                        resultJson.setMsg("账户过期，请联系管理员!");
                    } else if (e instanceof DisabledException) {
                        resultJson.setMsg("账户被禁用，请联系管理员!");
                    } else if (e instanceof BadCredentialsException) {
                        resultJson.setMsg("用户名或者密码输入错误，请重新输入!");
                    }
                    out.write(new ObjectMapper().writeValueAsString(resultJson));
                    out.flush();
                    out.close();
                })
                .and()
                //提供注销支持
                .logout()
                //我们指定一个自定义logoutSuccessHandle,如果指定了此项，则忽略logoutSuccessUrl()
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setContentType("application/json; charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    out.write("\"status\":200,\"msg\":\"登出成功！\"");
                }).
                permitAll();

        http.csrf().disable();//关闭跨站攻击

        http.exceptionHandling()
                //未认证处理
                .authenticationEntryPoint((request, response, authException) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    PrintWriter out = response.getWriter();
                    out.write("{\"status\":401,\"msg\":\"尚未登录，请登录！\"}");
                    out.flush();
                    out.close();
                })
                //权限不足处理
                .accessDeniedHandler((request, response, ex) -> {
                    response.setContentType("application/json; charset=UTF-8");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    PrintWriter out = response.getWriter();
                    out.write("\"status\":403,\"msg\":\"权限不足！请联系管理员！\"");
                    out.flush();
                    out.close();
                });
    }

    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
    }
}