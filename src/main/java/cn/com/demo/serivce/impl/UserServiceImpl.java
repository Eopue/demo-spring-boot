package cn.com.demo.serivce.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cn.com.demo.dao.PermissionMapper;
import cn.com.demo.dao.RoleMapper;
import cn.com.demo.dao.UserMapper;
import cn.com.demo.helper.PasswordHelper;
import cn.com.demo.pojo.Criteria;
import cn.com.demo.pojo.Permission;
import cn.com.demo.pojo.Role;
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

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<User> listUsers() {
        return userMapper.selectByParams(new Criteria());
    }

    @Override
    public User selectByPrimaryKey(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public boolean deleteByPrimaryKey(Long userId) {
        return userMapper.deleteByPrimaryKey(userId) > 0;
    }

    @Override
    public boolean createUser(User user) {
        // 用户密码加密
        PasswordHelper.encryptPassword(user);

        return userMapper.insertSelective(user) > 0;
    }

    @Override
    public Set<String> findRolesByUserName(String userName) {
        List<Role> roles = roleMapper.findRolesByParams(new Criteria("userName", userName));

        if (!CollectionUtils.isEmpty(roles)) {
            return roles.stream().map(Role::getRoleName).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    @Override
    public Set<String> findPermissionaByUserName(String userName) {
        List<Permission> permissions = permissionMapper.findPermissionsByParams(new Criteria("userName", userName));

        if (!CollectionUtils.isEmpty(permissions)) {
            return permissions.stream().map(Permission::getPermissionCode).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    @Override
    public List<User> selectByParams(Criteria criteria) {
        return userMapper.selectByParams(criteria);
    }
}
