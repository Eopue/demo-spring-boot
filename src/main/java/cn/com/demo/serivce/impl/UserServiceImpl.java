package cn.com.demo.serivce.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import cn.com.demo.dao.UserMapper;
import cn.com.demo.pojo.Criteria;
import cn.com.demo.pojo.User;
import cn.com.demo.serivce.UserService;

/**
 * The type UserServiceImpl.
 * <p>
 *
 * @author Xiaolu.Liu
 * @date 2018/4/10
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> listUsers() {
        return userMapper.selectByParams(new Criteria());
    }

    @Override
    public User selectByPrimaryKey(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }
}
