package com.sandman.blog.controller;

import com.sandman.blog.entity.common.BaseDto;
import com.sandman.blog.entity.system.User;
import com.sandman.blog.service.system.UserService;
import com.sandman.blog.utils.ShiroSecurityUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunpeikai on 2018/5/4.
 */
@RestController
@RequestMapping("/api/blog/v1/user")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserService userService;
    /**
     * POST : Create a new user.
     */
    @PostMapping("/createUser")
    public BaseDto createUser(@RequestParam("userName")String userName,
                              @RequestParam("password")String password,
                              @RequestParam("mobile")String mobile,
                              @RequestParam("email")String email,
                              @RequestParam("validateCode")String validateCode){
        //这里进行简单校验，在service里面进行复杂校验
        if (userName == null || "".equals(userName) || password == null || "".equals(password)
                || mobile == null || "".equals(mobile) || email == null || "".equals(email) || validateCode == null || "".equals(validateCode)) {
            return new BaseDto(416,"请确认输入信息!");
        }
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setMobile(mobile);
        user.setEmail(email);
        log.info("REST request to save User : {},validateCode:{}", user,validateCode);
        return userService.createUser(user,validateCode);
    }
    @GetMapping("/getCurUserInfo")
    public BaseDto getCurUserInfo(){
        boolean isPermitted = ShiroSecurityUtils.isPermitted("getCurUserInfo");
        Subject currentSubject = ShiroSecurityUtils.getCurrentSubject();
        log.info("user[{}] has permission[getCurUserInfo]:::{}",ShiroSecurityUtils.getCurrentUserName(),isPermitted);
        log.info("has role[USER]:::{};has role[ADMIN]::::{}",currentSubject.hasRole("USER"),currentSubject.hasRole("ADMIN"));
        //User user = userService.getCurUserInfo();
        User user = ShiroSecurityUtils.getCurrentUser();
        if(user!=null){
            log.info("userinfo====================={}",user);
            return new BaseDto(200,"查询成功!",user);
        }
        return new BaseDto(419,"用户未登录!");
    }
    @GetMapping("/contactExist")
    public BaseDto contactExist(String contact){
        Map<String,Integer> map = new HashMap<>();
        if(contact==null || "".equals(contact) || "null".equals(contact)){
            map.put("exist",0);
            return new BaseDto(425,"请先填写联系方式",map);
        }
        return userService.contactExist(contact);//0:未传入联系方式；1:联系方式已经被绑定；2:联系方式未被绑定
    }
    @PostMapping("/success")
    public BaseDto success(){
        log.info("login success!");
        return new BaseDto(200,"登录成功!");
    }
    @PostMapping("/error")
    public BaseDto error(){
        log.info("login error!");
        return new BaseDto(200,"登录失败!");
    }
    @PostMapping("/login")
    public BaseDto login(String username, String password, boolean rememberMe){
        log.info("login!!!!!username:{},password:{},remeberMe:{}",username,password,rememberMe);
        UsernamePasswordToken token = null;
        try{
            token = new UsernamePasswordToken(username, password);
            Subject currentUser = SecurityUtils.getSubject();
            if (!currentUser.isAuthenticated()){
                log.info("准备进行登录验证!");
                token.setRememberMe(rememberMe);
                currentUser.login(token);
            }

        } catch (IncorrectCredentialsException e) {
            return new BaseDto(411,"登录密码错误");
        } catch (UnknownAccountException e) {
            return new BaseDto(423,"用户不存在");
        }catch (LockedAccountException e){
            return new BaseDto(424,"账户已被锁定,如需解锁请联系管理员");
        }
        return new BaseDto(200,"登录成功");
    }
    @GetMapping("/logout")
    public BaseDto logout(){
        log.info("logout");
        ShiroSecurityUtils.getCurrentSubject().logout();
        return new BaseDto(200,"用户退出!");
    }
}
