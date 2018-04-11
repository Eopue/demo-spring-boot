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
    List<User> listUsers();

    User selectByPrimaryKey(Long userId);
}
