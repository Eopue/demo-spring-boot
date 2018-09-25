package cn.com.demo.pojo;

import java.io.Serializable;

/**
 * The type UserToken.
 * <p>
 *
 * @author Xiaolu.Liu
 * @date 2018/9/25
 */
public class UserToken implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * uuid
     */
    private String uuid;

    /**
     * 用户SID
     */
    private Long userSid;

    /**
     * 访问TOKEN
     */
    private String accessToken;

    /**
     * 过期时间
     */
    private Long accessExpire;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid
     *            uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * @return 用户SID
     */
    public Long getUserSid() {
        return userSid;
    }

    /**
     * @param userSid
     *            用户SID
     */
    public void setUserSid(Long userSid) {
        this.userSid = userSid;
    }

    /**
     * @return 访问TOKEN
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessToken
     *            访问TOKEN
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return 过期时间
     */
    public Long getAccessExpire() {
        return accessExpire;
    }

    /**
     * @param accessExpire
     *            过期时间
     */
    public void setAccessExpire(Long accessExpire) {
        this.accessExpire = accessExpire;
    }
}
