package cn.com.demo.pojo;

import java.io.Serializable;

public class UserPermissionKey implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long permissionSid;

    private Long userSid;

    public Long getPermissionSid() {
        return permissionSid;
    }

    public void setPermissionSid(Long permissionSid) {
        this.permissionSid = permissionSid;
    }

    public Long getUserSid() {
        return userSid;
    }

    public void setUserSid(Long userSid) {
        this.userSid = userSid;
    }
}