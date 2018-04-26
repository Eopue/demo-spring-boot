package cn.com.demo.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
public class UserRealm extends AuthenticatingRealm {

    @Autowired
    private UserService userService;

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(userService.findRolesByUserName(username));
        authorizationInfo.setStringPermissions(userService.findPermissionaByUserName(username));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();
        List<User> user = userService.selectByParams(new Criteria("name", username));
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
}
