package cn.com.demo.dao;

import cn.com.demo.pojo.Criteria;
import cn.com.demo.pojo.UserRoleKey;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper {
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
    int deleteByPrimaryKey(UserRoleKey key);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(UserRoleKey record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(UserRoleKey record);

    /**
     * 根据条件查询记录集
     */
    List<UserRoleKey> selectByParams(Criteria example);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") UserRoleKey record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") UserRoleKey record, @Param("condition") Map<String, Object> condition);
}