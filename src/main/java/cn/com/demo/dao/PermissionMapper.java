package cn.com.demo.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

import cn.com.demo.pojo.Criteria;
import cn.com.demo.pojo.Permission;

public interface PermissionMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByParams(Criteria example);

    /**
     * 根据条件删除记录
     */
    int deleteByParams(Criteria example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long permissionSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Permission record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Permission record);

    /**
     * 根据条件查询记录集
     */
    List<Permission> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    Permission selectByPrimaryKey(Long permissionSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") Permission record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") Permission record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Permission record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Permission record);

    /**
     * 根据条件查询用户权限
     */
    List<Permission> findPermissionsByParams(Criteria criteria);
}