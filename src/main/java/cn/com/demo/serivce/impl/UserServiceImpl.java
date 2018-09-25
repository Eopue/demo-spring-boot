package cn.com.demo.serivce.impl;

import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.demo.dao.PermissionMapper;
import cn.com.demo.dao.RoleMapper;
import cn.com.demo.dao.UserMapper;
import cn.com.demo.dao.UserTokenMapper;
import cn.com.demo.helper.PasswordHelper;
import cn.com.demo.pojo.Criteria;
import cn.com.demo.pojo.Permission;
import cn.com.demo.pojo.RestResult;
import cn.com.demo.pojo.Role;
import cn.com.demo.pojo.User;
import cn.com.demo.pojo.UserToken;
import cn.com.demo.serivce.UserService;
import cn.com.demo.shiro.StatelessToken;
import cn.com.demo.shiro.auth.AuthService;
import cn.com.demo.shiro.constants.AuthConstants;
import cn.com.demo.utils.token.RequestContextUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

/**
 * The type UserServiceImpl.
 * <p>
 *
 * @author Xiaolu.Liu
 * @date 2018/4/10
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserTokenMapper userTokenMapper;

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
    public Set<String> findPermissionsByUserName(String userName) {
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

    @Override
    public RestResult login(User user) {
        // 1. 得到加密后的密码
        PasswordHelper.encryptPassword(user);

        // 2. 用户登录
        Criteria criteria = new Criteria();
        criteria.put("name", user.getName());
        criteria.put("password", user.getPassword());
        List<User> users = userMapper.selectByParams(criteria);
        boolean loginResult = !CollectionUtils.isEmpty(users);

        Map<String, Object> result = new HashMap<>(3);
        result.put("result", loginResult);

        // 登录的成功返回token
        if (loginResult) {
            UserToken userToken = authService.createJWT(user);
            result.put("token", userToken.getAccessToken());

            result.put("user", users.get(0));

            userTokenMapper.insertSelective(userToken);
        }

        return new RestResult(result);
    }

    @Override
    public void clearCookie(HttpServletRequest servletRequest, HttpServletResponse response, Subject subject) {
        if (subject != null) {
            User authUser = RequestContextUtil.getAuthUserInfo(servletRequest);
            if (subject.getPrincipal() != null) {
                RequestContextUtil.removeUserCache(subject.getPrincipal().toString());
            } else {
                RequestContextUtil.removeUserCache(authUser.getName());
            }
            // delete token in cookie
            RequestContextUtil.clearPlatformTokenCookie(servletRequest, response);

            // delete token in db
            Map<String, String[]> params = new HashMap<>(servletRequest.getParameterMap());

            StatelessToken token = new StatelessToken(authUser.getName(),
                    params,
                    RequestContextUtil.getToken(servletRequest)
            );

            final Claims claims;
            try {
                claims = Jwts.parser().setSigningKey(AuthConstants.SECRET_KEY).parseClaimsJws(token.getToken())
                        .getBody();

                userTokenMapper.deleteByParams(new Criteria("uuid", claims.getId()));
            } catch (ExpiredJwtException e) {
                logger.debug("ExpiredJwtException occurred when parsing jwt token.{}", e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
