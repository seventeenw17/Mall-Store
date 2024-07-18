package com.mall.classes.user.service;

import com.mall.classes.user.pojo.dto.UserUpdateDTO;
import com.mall.classes.user.pojo.dto.UserLoginDTO;
import com.mall.classes.user.pojo.dto.UserRegisterDTO;
import com.mall.classes.user.pojo.vo.UserLoginVO;
import com.mall.classes.user.pojo.vo.UserVO;
import com.mall.tools.page.PageDTO;
import com.mall.tools.page.PageVO;

import java.util.List;

public interface UserService {
    /**
     * 获取用户列表
     * @return List<UserVO>
     */
    List<UserVO> getAllUserList();

    /**
     * 根据用户名查找用户
     * @param username
     * @return UserVO
     */
    UserVO findUserByUsername(String username);

    /**
     * 用户注册
     * @param userRegisterDTO
     */
    void register(UserRegisterDTO userRegisterDTO);

    /**
     * 用户登录
     * @param userLoginDTO
     * @return UserLoginVO
     */
    UserLoginVO login(UserLoginDTO userLoginDTO);

    /**
     * 修改用户信息
     * @param userUpdateDTO
     */
    void updateUserInfo(UserUpdateDTO userUpdateDTO, UserLoginVO curUser);

    /**
     * 根据ID修改用户状态
     * @param id
     * @param curUser
     */
    void updateStatusById(String id, UserLoginVO curUser);

    /**
     * 根据ID删除用户
     * @param id
     */
    void deleteUserById(String id);

    /**
     * 分页查询所有用户
     * @param pageDTO
     * @return PageVO<List<UserVO>>
     */
    PageVO<List<UserVO>> getAllUserListPage(PageDTO pageDTO);

    /**
     * 获取用户数据
     * @return Long
     */
    Long countUser();

    /**
     * 根据用户名模糊查询
     * @param str
     * @return List<UserVO>
     */
    List<UserVO> findUserByStr(String str);
}
