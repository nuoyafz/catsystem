package com.catfeeding.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.catfeeding.common.Result;
import com.catfeeding.entity.User;
import com.catfeeding.service.UserService;
import com.catfeeding.utils.JwtUtils;
import com.catfeeding.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器
 * @author 方舟
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        User dbUser = userService.getOne(wrapper);

        if (dbUser == null) {
            return Result.error("用户名不存在");
        }

        if (!PasswordUtils.verify(user.getPassword(), dbUser.getPassword())) {
            return Result.error("密码错误");
        }

        if (dbUser.getStatus() == 0) {
            return Result.error("账号已被禁用");
        }

        String token = jwtUtils.generateToken(dbUser.getId(), dbUser.getUsername(), dbUser.getRole());
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", dbUser);

        return Result.success(data);
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<Void> register(@RequestBody User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        if (userService.getOne(wrapper) != null) {
            return Result.error("用户名已存在");
        }

        user.setPassword(PasswordUtils.encrypt(user.getPassword()));
        user.setRole("user");
        user.setStatus(1);
        userService.save(user);

        return Result.success();
    }

    /**
     * 获取所有用户列表（管理员用）
     */
    @GetMapping("/list")
    public Result<java.util.List<User>> getAllUsers() {
        java.util.List<User> users = userService.list();
        // 隐藏密码
        users.forEach(u -> u.setPassword(null));
        return Result.success(users);
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/info/{id}")
    public Result<User> getUserInfo(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user != null) {
            user.setPassword(null);
        }
        return Result.success(user);
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/update")
    public Result<Void> updateUser(@RequestBody User user) {
        user.setPassword(null);
        userService.updateById(user);
        return Result.success();
    }

    /**
     * 修改密码
     */
    @PutMapping("/password")
    public Result<Void> changePassword(@RequestBody Map<String, String> params) {
        Long userId = Long.parseLong(params.get("userId"));
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");

        User user = userService.getById(userId);
        if (!PasswordUtils.verify(oldPassword, user.getPassword())) {
            return Result.error("原密码错误");
        }

        user.setPassword(PasswordUtils.encrypt(newPassword));
        userService.updateById(user);

        return Result.success();
    }
}
