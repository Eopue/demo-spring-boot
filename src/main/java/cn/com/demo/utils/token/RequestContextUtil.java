package cn.com.demo.utils.token;

import com.google.common.base.CaseFormat;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.demo.pojo.User;
import cn.com.demo.shiro.constants.AuthConstants;
import cn.com.demo.utils.cache.JedisUtil;
import cn.com.demo.utils.common.JsonUtil;
import cn.com.demo.utils.database.DBUtil;
import cn.com.demo.utils.string.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;


/**
 * The type RequestContextUtil.
 * <p>
 *
 * @author Xiaolu.Liu
 * @date 2018/9/25
 */
public class RequestContextUtil {

    private static final Logger logger = LoggerFactory.getLogger(RequestContextUtil.class);

    /**
     * 创建基础的服务类参数.
     *
     * @param <T>              the type parameter
     * @param req              HttpRequest
     * @param resBaseInfoClazz 资源层共通参数类
     * @return the t
     */
    public static <T> T buildResBaseInfo(HttpServletRequest req, Class<T> resBaseInfoClazz) {
        return buildResBaseInfo(req, resBaseInfoClazz, false);
    }

    /**
     * 创建基础的服务类参数.
     *
     * @param <T>              the type parameter
     * @param req              HttpServletRequest
     * @param resBaseInfoClazz 资源层共通参数类
     * @param withJsonParam    是否设置JSON配置项<br/>
     *                         true -> 从request的body中取得参数，直接设置到对象的字段上，但是有前提是REST接口上面，
     *                         没有使用自动映射参数
     * @return the t
     * @throws IllegalStateException 如果 {@code withJsonParam} 为true，发生异常由于 {@link HttpServletRequest#getInputStream} 或者 {@link HttpServletRequest#getReader()}
     *                               在这个Request上已经被调用过
     */
    public static <T> T buildResBaseInfo(HttpServletRequest req, Class<T> resBaseInfoClazz, boolean withJsonParam) {
        User authUser = getAuthUserInfo(req);
        T result = transUserInfo2ResBase(authUser, resBaseInfoClazz);
        // 将request的body（JSON）设置到对象上
        if (withJsonParam) {
            String body = null;
            try {
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = req.getReader();
                while ((body = reader.readLine()) != null) {
                    sb.append(body);
                }
                // resSpecParam
                Field resSpecParam = ReflectionUtils.findField(resBaseInfoClazz, AuthConstants.FIELD_RES_SPEC_PARAM_);
                ReflectionUtils.makeAccessible(resSpecParam);
                ReflectionUtils.setField(resSpecParam, result, sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 获得授权用户
     *
     * @return the auth user info
     */
    public static User getAuthUserInfo() {
        return getAuthUserInfo(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
    }

    /**
     * 获得授权用户
     *
     * @param req the req
     * @return the auth user info
     */
    public static User getAuthUserInfo(HttpServletRequest req) {
        Claims claims = (Claims) req.getAttribute(AuthConstants.CLAIMS_KEY);
        if (claims == null) {
            String token = getToken(req);
            if (token == null) {
                return null;
            }
            try {
                claims = Jwts.parser().setSigningKey(AuthConstants.SECRET_KEY).parseClaimsJws(token).getBody();
            } catch (ExpiredJwtException | MalformedJwtException e) {
                throw e;
            } catch (Exception e) {
                logger.error(Throwables.getStackTraceAsString(e));
                return null;
            }
            if (claims == null) {
                return null;
            }
        }
        String userAccount = claims.getIssuer();

        return getAuthUserInfo(userAccount);
    }

    /**
     * 获取授权用户信息
     *
     * @param userAccount 用户account
     * @return the auth user
     */
    private static User getAuthUserInfo(String userAccount) {
        User authUser;
        // Redis中
        Map<String, String> userInfo = JedisUtil.instance().hgetall(AuthConstants.CACHE_KEY_USER_PREFIX + userAccount);
        if (userInfo != null && userInfo.size() > 0) {
            authUser = JsonUtil.fromJson(JsonUtil.toJson(userInfo), User.class);
        } else {
            // From DB
            Map dbUserMap = DBUtil.queryMap(AuthConstants.PLATFORM_USER_SQL, userAccount);
            Map<String, String> userMap = new HashMap<>(16);
            for (Object column : dbUserMap.keySet()) {
                String key = column.toString();
                String camelKey = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key);
                userMap.put(camelKey, StringUtil.nullToEmpty(dbUserMap.get(key)));
            }
            authUser = JsonUtil.fromJson(JsonUtil.toJson(userMap), User.class);
            JedisUtil.instance()
                    .hmset(AuthConstants.CACHE_KEY_USER_PREFIX + userAccount, userMap, AuthConstants.PERIED_TIME);
        }
        return authUser;
    }

    /**
     * 创建基础的服务类参数.
     *
     * @param <T>              the type parameter
     * @param authUser         授权用户
     * @param resBaseInfoClazz 资源层参数类
     * @return the t
     */
    public static <T> T transUserInfo2ResBase(User authUser, Class<T> resBaseInfoClazz) {
        T result;
        try {
            result = resBaseInfoClazz.newInstance();
            // phone
            Field phone = ReflectionUtils.findField(resBaseInfoClazz, AuthConstants.PHONE);
            ReflectionUtils.makeAccessible(phone);
            ReflectionUtils.setField(phone, result, authUser.getPhone());
            // userAccount
            Field userName = ReflectionUtils.findField(resBaseInfoClazz, AuthConstants.NAME);
            ReflectionUtils.makeAccessible(userName);
            ReflectionUtils.setField(userName, result, authUser.getName());
            // userPass
            Field userPassword = ReflectionUtils.findField(resBaseInfoClazz, AuthConstants.PASSWORD);
            ReflectionUtils.makeAccessible(userPassword);
            ReflectionUtils.setField(userPassword, result, authUser.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }

    /**
     * 获得 token.
     *
     * @param httpRequest the http request
     * @return the token
     */
    public static String getToken(HttpServletRequest httpRequest) {
        String token = null;
        final String authorizationHeader = httpRequest.getHeader("authorization");
        if (authorizationHeader == null) {
            return getCookieToken(httpRequest);
        }

        String[] parts = authorizationHeader.split(" ");
        if (parts.length != 2) {
            return null;
        }

        String scheme = parts[0];
        String credentials = parts[1];

        Pattern pattern = Pattern.compile(AuthConstants.TOKEN_PREFIX, Pattern.CASE_INSENSITIVE);
        if (pattern.matcher(scheme).matches()) {
            token = credentials;
        }
        return token;
    }

    public static String getCookieToken(HttpServletRequest httpRequest) {
        Cookie[] cookies = httpRequest.getCookies();
        for (Cookie cookie:cookies) {
            if (cookie.getName().equals("PLATFORM_IDC_TOKEN")) {
                return cookie.getValue();
            }
        }

        return null;
    }

    public static boolean removeUserCache(String userAccount) {
        JedisUtil.instance().del(AuthConstants.CACHE_KEY_USER_PREFIX + userAccount);
        JedisUtil.instance().del(AuthConstants.CACHE_USER_TOKEN + userAccount);
        return true;
    }

    /**
     * clear cookie for platform's token
     * @param request
     * @param response
     */
    public static void clearPlatformTokenCookie(HttpServletRequest request, HttpServletResponse response) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("PLATFORM_IDC_TOKEN".equalsIgnoreCase(cookie.getName())) {
                    Cookie nilCookie = new Cookie(cookie.getName(), null);
                    nilCookie.setValue(null);
                    if (!Strings.isNullOrEmpty(cookie.getDomain())) {
                        nilCookie.setDomain(cookie.getDomain());
                    }
                    nilCookie.setMaxAge(0);
                    nilCookie.setPath("/");
                    response.addCookie(nilCookie);
                }
            }
        }
    }

}
