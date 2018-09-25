package cn.com.demo.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.demo.pojo.User;
import cn.com.demo.utils.token.RequestContextUtil;

/**
 * The type StatelessAuthFilter.
 * <p>
 *
 * @author Xiaolu.Liu
 * @date 2018/9/25
 */
public class StatelessAuthFilter extends AccessControlFilter {

    private static final Logger logger = LoggerFactory.getLogger(StatelessAuthFilter.class);

    private final static String AUTH_PRE = "^Bearer$";


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        String method = ((HttpServletRequest) request).getMethod();
        //解决跨域时候中的权限问题，一旦有跨域，会先发options请求来询问是否允许跨域，此时应该放这个options请求通过
        if ("options".equals(method.toLowerCase())) {
            logger.debug("it's cross-origin preflight request, must make it through");
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        logger.debug("request uri: {}", ShiroHttpServletRequest.class.cast(request).getRequestURI());
        // 客户端请求的参数列表
        Map<String, String[]> params = new HashMap<>(request.getParameterMap());

        try {
            //4、生成无状态Token
            User authUserInfo = RequestContextUtil.getAuthUserInfo(HttpServletRequest.class.cast(request));
            if (authUserInfo == null) {
                logger.warn("Unauthorized: Cannot found Authorization user info.");
                //6、登录失败
                onLoginFail(response);
                return false;
            }
            StatelessToken token = new StatelessToken(authUserInfo.getName(),
                    params,
                    getToken(HttpServletRequest.class.cast(request))
            );
            //5、委托给Realm进行登录
            getSubject(request, response).login(token);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            //6、登录失败
            onLoginFail(response);
            return false;
        }

        return true;
    }

    /**
     * 登录失败时默认返回401状态码
     */
    private void onLoginFail(ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private String getToken(HttpServletRequest httpRequest) {
        String token = null;
        final String authorizationHeader = httpRequest.getHeader("authorization");
        if (authorizationHeader == null) {
            token = RequestContextUtil.getCookieToken(httpRequest);
            if (StringUtils.isNotBlank(token)) {
                return token;
            }
            throw new AuthenticationException("Unauthorized: No Authorization header was found");
        }

        String[] parts = authorizationHeader.split(" ");
        if (parts.length != 2) {
            throw new AuthenticationException("Unauthorized: Format is Authorization: Bearer [token]");
        }

        String scheme = parts[0];
        String credentials = parts[1];

        Pattern pattern = Pattern.compile(AUTH_PRE, Pattern.CASE_INSENSITIVE);
        if (pattern.matcher(scheme).matches()) {
            token = credentials;
        }
        return token;
    }
}
