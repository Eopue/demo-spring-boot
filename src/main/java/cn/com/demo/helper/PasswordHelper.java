package cn.com.demo.helper;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import cn.com.demo.pojo.User;

/**
 * The type PasswordHelper.
 * <p>
 *
 * @author Xiaolu.Liu
 * @date 2018/4/26
 */
public class PasswordHelper {
    private static final int hashIterations = 2;
    private static final String algorithmName = "md5";

    public static void encryptPassword(User user) {
        String newPassword = new SimpleHash(
                algorithmName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getName()),
                hashIterations).toHex();

        user.setPassword(newPassword);
    }
}
