package cn.com.demo.shiro.auth;

import cn.com.demo.pojo.User;
import cn.com.demo.token.RequestContextUtil;

/**
 * The type AuthUtil.
 * <p>
 *
 * @author Xiaolu.Liu
 * @date 2018/9/25
 */
public class AuthUtil {
    public static User getAuthUserInfo() {
        return RequestContextUtil.getAuthUserInfo();
    }
}
