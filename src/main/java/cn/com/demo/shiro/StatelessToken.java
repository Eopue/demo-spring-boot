package cn.com.demo.shiro;

import org.apache.shiro.authc.AuthenticationToken;

import java.util.Map;

/**
 * The type StatelessToken.
 * <p>
 *
 * @author Xiaolu.Liu
 * @date 2018/9/25
 */
public class StatelessToken implements AuthenticationToken {

    private String username;
    private Map<String, ?> params;
    private String token;

    public StatelessToken(String username, Map<String, ?> params, String token) {
        this.username = username;
        this.params = params;
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, ?> getParams() {
        return params;
    }

    public void setParams(Map<String, ?> params) {
        this.params = params;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
