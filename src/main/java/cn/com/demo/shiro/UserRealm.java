package cn.com.demo.shiro;

import com.alibaba.druid.util.StringUtils;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import cn.com.demo.pojo.Criteria;
import cn.com.demo.pojo.User;
import cn.com.demo.serivce.UserService;

/**
 * The type UserRealm.
 * <p>
 *
 * @author Xiaolu.Liu
 * @date 2018/4/26
 */
@Repository
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        Criteria criteria = new Criteria("name", usernamePasswordToken.getUsername());
        criteria.put("password", String.valueOf(usernamePasswordToken.getPassword()));
        List<User> user = userService.selectByParams(criteria);
        if (CollectionUtils.isEmpty(user)) {
            //没找到帐号
            throw new UnknownAccountException();
        }
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以在此判断或自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                //用户名
                user.get(0).getName(),
                //密码
                user.get(0).getPassword(),
                //salt=username+salt
                ByteSource.Util.bytes(user.get(0).getName()),
                //realm name
                getName()
        );
        return authenticationInfo;
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
            Set<String> permissions = userService.findPermissionaByUserName(username);
            info.addStringPermissions(permissions);
            return info;
        }
        return null;

    }
}
