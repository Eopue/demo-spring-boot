package cn.com.demo.pojo;

import java.io.Serializable;
import java.util.Date;

public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Long roleSid;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDesc;

    /**
     * 角色状态 (0:禁用，1:有效，9:锁定)
     */
    private String status;

    /**
     * 角色类型(01:前台角色; 02:后台角色)
     */
    private String roleType;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdDt;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private Date updatedDt;

    /**
     * 版本号
     */
    private Long version;

    /**
     * @return 角色ID
     */
    public Long getRoleSid() {
        return roleSid;
    }

    /**
     * @param roleSid 
	 *            角色ID
     */
    public void setRoleSid(Long roleSid) {
        this.roleSid = roleSid;
    }

    /**
     * @return 角色名称
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName 
	 *            角色名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * @return 角色描述
     */
    public String getRoleDesc() {
        return roleDesc;
    }

    /**
     * @param roleDesc 
	 *            角色描述
     */
    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    /**
     * @return 角色状态 (0:禁用，1:有效，9:锁定)
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status 
	 *            角色状态 (0:禁用，1:有效，9:锁定)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return 角色类型(01:前台角色; 02:后台角色)
     */
    public String getRoleType() {
        return roleType;
    }

    /**
     * @param roleType 
	 *            角色类型(01:前台角色; 02:后台角色)
     */
    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    /**
     * @return 创建人
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy 
	 *            创建人
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return 创建时间
     */
    public Date getCreatedDt() {
        return createdDt;
    }

    /**
     * @param createdDt 
	 *            创建时间
     */
    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    /**
     * @return 更新人
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy 
	 *            更新人
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return 更新时间
     */
    public Date getUpdatedDt() {
        return updatedDt;
    }

    /**
     * @param updatedDt 
	 *            更新时间
     */
    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    /**
     * @return 版本号
     */
    public Long getVersion() {
        return version;
    }

    /**
     * @param version 
	 *            版本号
     */
    public void setVersion(Long version) {
        this.version = version;
    }
}