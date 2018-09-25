package cn.com.demo.serivce;

import org.apache.shiro.subject.Subject;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.demo.pojo.Criteria;
import cn.com.demo.pojo.RestResult;
import cn.com.demo.pojo.User;

/**
 * The type UserService.
 * <p>
 *
 * @author Xiaolu.Liu
 * @date 2018/4/10
 */
public interface UserService {
    /**
     * 查询all
     *
     * @return
     */
    List<User> listUsers();

    /**
     * 根据主键查询用户
     *
     * @param userId
     * @return
     */
    User selectByPrimaryKey(Long userId);

    /**
     * 根据主键删除用户
     *
     * @param userId
     * @return
     */
    boolean deleteByPrimaryKey(Long userId);

    /**
     * 创建用户
     *
     * @param user
     * @return
     */
    boolean createUser(User user);

    /**
     * 根据用户名称查询用户角色
     *
     * @param userName
     * @return
     */
    Set<String> findRolesByUserName(String userName);

    /**
     * 根据用户名查询用户权限
     *
     * @param userName 用户名称
     *                 @return 权限
     */
    Set<String> findPermissionsByUserName(String userName);

    /**
     * 根据条件查询用户列表
     *
     * @param criteria 查询条件
     * @return 用户列表
     */
    List<User> selectByParams(Criteria criteria);

    /**
     * 用户登录
     *
     * @param user 用户实体类
     * @return restResult
     */
    RestResult login(User user);

    /**
     * 清除token
     *
     * @param servletRequest request
     * @param response response
     * @param subject subject
     */
    void clearCookie(HttpServletRequest servletRequest, HttpServletResponse response, Subject subject);
}
