package cn.com.demo.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import cn.com.demo.pojo.Criteria;
import cn.com.demo.pojo.User;

/**
 * The type UserMapper.
 * <p>
 *
 * @author Xiaolu.Liu
 * @date 2018/4/10
 */
@Repository
public interface UserMapper {
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
    int deleteByPrimaryKey(Long id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(User record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(User record);

    /**
     * 根据条件查询记录集
     */
    List<User> selectByParams(Criteria example);

    /**
     * 根据主键查询记录
     */
    User selectByPrimaryKey(Long id);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByParamsSelective(@Param("record") User record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByParams(@Param("record") User record, @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(User record);
}