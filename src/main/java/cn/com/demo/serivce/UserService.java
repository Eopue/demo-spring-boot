package cn.com.demo.serivce;

import java.util.List;

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
}
