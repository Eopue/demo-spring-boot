package cn.com.demo.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import cn.com.demo.pojo.Criteria;
import cn.com.demo.pojo.UserPermissionKey;

@Repository
public interface UserPermissionMapper {
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
    int deleteByPrimaryKey(UserPermissionKey key);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(UserPermissionKey record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(UserPermissionKey record);

    /**
     * 根据条件查询记录集
     */
    List<UserPermissionKey> selectByParams(Criteria example);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") UserPermissionKey record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") UserPermissionKey record, @Param("condition") Map<String, Object> condition);
}