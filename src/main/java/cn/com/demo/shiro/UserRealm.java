package cn.com.demo.shiro;

import com.google.common.base.Strings;

import com.alibaba.druid.util.StringUtils;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;

import cn.com.demo.controller.UserController;
import cn.com.demo.serivce.UserService;
import cn.com.demo.shiro.constants.AuthConstants;
import cn.com.demo.utils.cache.JedisUtil;
import cn.com.demo.utils.database.DBUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

/**
 * The type UserRealm.
 * <p>
 *
 * @author Xiaolu.Liu
 * @date 2018/4/26
 */
@Repository
public class UserRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        StatelessToken statelessToken = (StatelessToken) token;

        if (Strings.isNullOrEmpty(statelessToken.getToken()) || "undefined".equals(statelessToken.getToken())) {
            logger.debug("user({})'s token({}) is valid.", statelessToken.getUsername(), statelessToken.getToken());
            return null;
        }

        final Claims claims = Jwts.parser().setSigningKey(AuthConstants.SECRET_KEY)
                .parseClaimsJws(statelessToken.getToken()).getBody();
        //1.从缓存或者数据库中查询token是否存在
        if (statelessToken.getToken().equals(JedisUtil.instance().get(AuthConstants.CACHE_USER_TOKEN +
                claims.getIssuer()))) {// cache hit
            return getAuthenticationInfo(statelessToken);
        } else {
            Map accessTokens = DBUtil
                    .queryMap("select access_token from sys_m_user_token where uuid='" + claims.getId() + "'");
            if (accessTokens != null && accessTokens.size() != 1) {
                logger.debug("user({})'s token({}) is valid.", statelessToken.getUsername(), statelessToken.getToken());
                return null;
            }
            JedisUtil.instance().set(AuthConstants.CACHE_USER_TOKEN + claims.getIssuer(),
                    accessTokens.get("access_token").toString()
            );
            JedisUtil.instance().expire(AuthConstants.CACHE_USER_TOKEN + claims.getIssuer(), 60 * 60);
            return getAuthenticationInfo(statelessToken);
        }
    }

    private AuthenticationInfo getAuthenticationInfo(StatelessToken statelessToken) {
        //2.如果token存在，拿出token信息验证过期时间等
        final Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(AuthConstants.SECRET_KEY).parseClaimsJws(statelessToken.getToken())
                    .getBody();
        } catch (ExpiredJwtException e) {
            logger.debug("ExpiredJwtException occurred when parsing jwt token.{}", e.getMessage());
            e.printStackTrace();
            return null;
        }
        if (claims.getExpiration().before(Calendar.getInstance().getTime())) {
            logger.debug("user({})'s token({}) expired.", statelessToken.getUsername(), statelessToken.getToken());
            return null;
        }
        return new SimpleAuthenticationInfo(statelessToken.getUsername(), statelessToken.getToken(), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 1. 获取当前登录的用户名
        String username = (String) principalCollection.fromRealm(getName()).iterator().next();
        // 2. 根据用户名来添加相应的权限和角色
        if(!StringUtils.isEmpty(username)){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

            // 3. 查询用户角色
            Set<String> roles = userService.findRolesByUserName(username);
            info.addRoles(roles);

            // 4. 查询用户权限
            Set<String> permissions = userService.findPermissionsByUserName(username);
            info.addStringPermissions(permissions);
            return info;
        }
        return null;

    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return true;
    }
}
