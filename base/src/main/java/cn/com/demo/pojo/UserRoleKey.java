package cn.com.demo.pojo;

import java.io.Serializable;

public class UserRoleKey implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色SID
     */
    private Long roleSid;

    /**
     * 用户SID
     */
    private Long userSid;

    /**
     * @return 角色SID
     */
    public Long getRoleSid() {
        return roleSid;
    }

    /**
     * @param roleSid 
	 *            角色SID
     */
    public void setRoleSid(Long roleSid) {
        this.roleSid = roleSid;
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
}