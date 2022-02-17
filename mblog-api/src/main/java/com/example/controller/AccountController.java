package com.example.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.lang.dto.LoginDto;
import com.example.common.lang.Result;
import com.example.entity.User;
import com.example.service.UserService;
import com.example.util.JwtUtils;
import io.jsonwebtoken.lang.Assert;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author giaming
 * @date 2022/2/12 - 15:46
 * @detail
 */
@RestController
public class AccountController {
    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    /**
     * 登录请求处理
     * @param loginDto
     * @param response
     * @return
     */
    @CrossOrigin
    @RequestMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response){
        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        // 断言拦截
        Assert.notNull(user, "用户不存在");
        // 判断账号密码是否错误，因为是MD5加密所以这里md5判断
        if(!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))){
            // 密码不则抛出异常
            return Result.fail("密码不正确");
        }
        if(user.getStatus()==0){
            return Result.fail("账户已被禁用");
        }
        String jwt = jwtUtils.generateToken(user.getId(), user.getUsername());

        // 将token放在header中
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        return Result.succ(MapUtil.builder()
                        .put("id", user.getId())
                        .put("username", user.getUsername())
                        .put("avatar", user.getAvatar())
                        .put("email", user.getEmail())
                        .put("role", user.getRole()).map());
    }


    // 需要认证权限才能退出登录
    @RequiresAuthentication
    @RequestMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.succ("注销成功");
    }
}
