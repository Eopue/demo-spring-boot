package cn.com.demo.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * The type Record.
 * <p>
 *
 * @author Xiaolu.Liu
 * @date 2018/4/10
 */
public class Record implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String operate;

    private Date createdDt;

    private String createdBy;

    private Date updatedDt;

    private String updatedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}