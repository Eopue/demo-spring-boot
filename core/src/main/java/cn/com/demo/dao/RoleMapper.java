package cn.com.demo.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import cn.com.demo.pojo.Criteria;
import cn.com.demo.pojo.Role;

@Repository
public interface RoleMapper {
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
    int deleteByPrimaryKey(Long roleSid);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Role record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(Role record);

    /**
     * 根据条件查询记录集
     */
    List<Role> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    Role selectByPrimaryKey(Long roleSid);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") Role record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") Role record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Role record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(Role record);

    /**
     * 根据条件查询用户角色
     *
     * @param criteria
     * @return
     */
    List<Role> findRolesByParams(Criteria criteria);
}