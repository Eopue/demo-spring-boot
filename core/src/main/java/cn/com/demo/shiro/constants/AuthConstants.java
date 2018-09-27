package cn.com.demo.shiro.constants;

import java.util.Base64;

/**
 * The type AuthConstants.
 * <p>
 *
 * @author Xiaolu.Liu
 * @date 2018/9/25
 */
public interface AuthConstants {
    String PHONE = "phone";
    String NAME = "name";
    String PASSWORD = "password";
    String FIELD_RES_SPEC_PARAM_ = "resSpecParam";
    String CLAIMS_KEY = "claims";
    String TOKEN_PREFIX = "^Bearer$";
    int PERIED_TIME = 60 * 60;
    String CACHE_KEY_USER_PREFIX = "portal:user:";
    String SECRET_KEY = Base64.getEncoder().encodeToString("secretKey".getBytes());
    String PLATFORM_USER_SQL = "select id, name, password, phone, created_dt, created_by, updated_dt, updated_by from sys_m_user where name = ?";
    long TTL_MILLIS = 86400000;
    String CACHE_USER_TOKEN = "user:token:";
}
