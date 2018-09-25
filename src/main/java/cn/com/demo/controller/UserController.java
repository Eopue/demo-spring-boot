package cn.com.demo.controller;

import com.google.common.collect.ImmutableMap;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.demo.pojo.RestResult;
import cn.com.demo.pojo.User;
import cn.com.demo.serivce.UserService;

/**
 * The type TestBootController.
 * <p>
 *
 * @author Xiaolu.Liu
 * @date 2018/4/10
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public RestResult getUser(@PathVariable("id") Long userId) {
        User user = userService.selectByPrimaryKey(userId);

        return new RestResult(user);
    }

    @GetMapping
    public RestResult listUsers() {
        List<User> users = userService.listUsers();

        return new RestResult(users);
    }

    @DeleteMapping("/{id}")
    public RestResult deleteUser(@PathVariable("id") Long userId) {
        boolean result = userService.deleteByPrimaryKey(userId);

        return new RestResult(result);
    }

    @PostMapping
    public RestResult createUser(@RequestBody User user) {
        boolean result = userService.createUser(user);

        return new RestResult(result);
    }

    @PostMapping("/login")
    public RestResult login(@RequestBody User user) {
        RestResult restResult = userService.login(user);

        return new RestResult(restResult);
    }

    @PostMapping("/logout")
    public RestResult logout(HttpServletRequest servletRequest, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();

        userService.clearCookie(servletRequest, response, subject);

        logger.info("退出成功，时间[{}]", Calendar.getInstance().getTime().toInstant().atZone(ZoneId.systemDefault()));
        return new RestResult(ImmutableMap.of("result", true));
    }
}
