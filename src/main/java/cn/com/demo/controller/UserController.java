package cn.com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
@EnableAutoConfiguration
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long userId) {
        User user = userService.selectByPrimaryKey(userId);
        return user;
    }

    @GetMapping
    public List<User> listUsers() {
        List<User> users = userService.listUsers();

        return users;
    }
}
