package com.mall.classes.user.service.impl;

import com.mall.classes.user.dao.UserDao;
import com.mall.classes.user.pojo.dto.UserUpdateDTO;
import com.mall.classes.user.pojo.dto.UserLoginDTO;
import com.mall.classes.user.pojo.dto.UserRegisterDTO;
import com.mall.classes.user.pojo.entity.User;
import com.mall.classes.user.pojo.vo.UserLoginVO;
import com.mall.classes.user.pojo.vo.UserVO;
import com.mall.classes.user.service.UserService;
import com.mall.tools.common.Constants;
import com.mall.tools.common.ServiceCode;
import com.mall.tools.common.ServiceException;
import com.mall.tools.page.PageDTO;
import com.mall.tools.page.PageVO;
import com.mall.tools.security.JWTUtils;
import com.mall.tools.security.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static cn.hutool.core.date.DateTime.now;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 获取用户列表
     * @return List<UserVO>
     */
    @Override
    public List<UserVO> getAllUserList() {
        List<UserVO> userVOList = new ArrayList<>();
        List<User> userList = userDao.findAll();
        if (userList == null || userList.isEmpty()) {
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "暂无用户！");
        }
        System.out.println("用户列表：" + userList);

        for (User user: userList) {
            UserVO userVO = new UserVO(user);
            userVOList.add(userVO);
        }
        return userVOList;
    }

    /**
     * 根据用户名查找用户
     * @param username
     * @return UserVO
     */
    @Override
    public UserVO findUserByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new ServiceException(ServiceCode.ERROR_IS_ASSOCIATED, "用户名不能为空！");
        }
        User user = userDao.getUserByUsername(username);
        if (user == null) {
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "用户不存在！");
        }
        UserVO userVO = new UserVO(user);
        return userVO;
    }

    /**
     * 用户注册
     * @param userRegisterDTO
     */
    @Override
    public void register(UserRegisterDTO userRegisterDTO) {
        // 用户名是否为空字符串
        if (userRegisterDTO.getUsername().isEmpty()) {
            throw new ServiceException(ServiceCode.ERROR_IS_ASSOCIATED, "用户名不能为空！");
        }
        // 密码是否为空字符串
        if (userRegisterDTO.getPassword().isEmpty()) {
            throw new ServiceException(ServiceCode.ERROR_IS_ASSOCIATED, "密码不能为空！");
        }
        // 确认密码是否为空字符串
        if (userRegisterDTO.getRePassword().isEmpty()) {
            throw new ServiceException(ServiceCode.ERROR_IS_ASSOCIATED, "确认密码不能为空！");
        }
        // 用户名不能和已有的用户一致
        User existedUser = userDao.getUserByUsername(userRegisterDTO.getUsername());
        if (existedUser != null) {
            throw new ServiceException(ServiceCode.ERROR_EXISTS, "该用户已存在！");
        }
        // 判断两次密码是否一样
        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getRePassword())) {
            throw new ServiceException(ServiceCode.ERROR_SAVE_FAILED, "两次密码不一致！");
        }
        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        // 密码加密
        String salt = UUID.randomUUID().toString().replace("-","");
        user.setSalt(salt);
        String password = MD5Utils.encodePassword(userRegisterDTO.getPassword(), salt,Constants.HASH_TIME);
        user.setPassword(password);
        // 没有被删除
        user.setStatus(Constants.IS_NOT_DELETED);
        user.setCreatedUser(userRegisterDTO.getUsername());
        user.setCreatedTime(now());
        User res = userDao.insert(user);
        System.out.println("注册结果" + res);
    }

    /**
     * 用户登录
     * @param userLoginDTO
     * @return UserLoginVO
     */
    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {
        System.out.println("用户登录信息：" + userLoginDTO);
        User existedUser = userDao.getUserByUsername(userLoginDTO.getUsername());
        System.out.println("存在的用户信息：" + existedUser);
        // 用户是否存在
        if (existedUser == null) {
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "用户不存在！");
        }

        // 对密码进行加密后判断
        String password = MD5Utils.encodePassword(userLoginDTO.getPassword(), existedUser.getSalt(), Constants.HASH_TIME);
        if (!password.equals(existedUser.getPassword())) {
            throw new ServiceException(ServiceCode.ERROR_FORBIDDEN, "用户名或密码错误！");
        }
        // 从User取出数据
        // 生成token
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setId(existedUser.getId());
        userLoginVO.setUsername(existedUser.getUsername());
        // 修改时间
        existedUser.setModifiedUser(existedUser.getUsername());
        existedUser.setModifiedTime(now());
        userDao.save(existedUser);

        String token = JWTUtils.generateToken(userLoginVO);
        userLoginVO.setToken(token);
        return userLoginVO;
    }

    /**
     * 修改用户信息
     * @param userUpdateDTO
     */
    @Override
    public void updateUserInfo(UserUpdateDTO userUpdateDTO, UserLoginVO curUser) {
        if (userUpdateDTO == null) {
            throw new ServiceException(ServiceCode.ERROR_FORBIDDEN, "请提交修改内容！");
        }
        if (userUpdateDTO.getUsername() == null) {
            throw new ServiceException(ServiceCode.ERROR_IS_ASSOCIATED, "用户名不能为空！");
        }
        User user = userDao.getUserByUsername(userUpdateDTO.getUsername());
        if (user == null) {
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "用户不存在！");
        }

        if (userUpdateDTO.getSex() != null) {
            user.setSex(userUpdateDTO.getSex());
        }
        if (userUpdateDTO.getAge() != null) {
            user.setAge(userUpdateDTO.getAge());
        }
        if (userUpdateDTO.getEmail() != null) {
            user.setEmail(userUpdateDTO.getEmail());
        }
        if (userUpdateDTO.getAddress() != null) {
            user.setAddress(userUpdateDTO.getAddress());
        }
        if (userUpdateDTO.getTel() != null) {
            user.setTel(userUpdateDTO.getTel());
        }
        if (userUpdateDTO.getPicture() != null) {
            user.setPicture(userUpdateDTO.getPicture());
        }
        userDao.save(user);
    }

    /**
     * 根据 ID修改用户状态
     * @param id
     * @param curUser
     */
    @Override
    public void updateStatusById(String id, UserLoginVO curUser) {
        if (id == null || id.isEmpty()) {
            throw new ServiceException(ServiceCode.ERROR_IS_ASSOCIATED, "ID不能为空！");
        }
        Optional<User> userOpt = userDao.findById(id);
        if (!userOpt.isPresent()) {
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "用户不存在！");
        }
        User user = userOpt.get();
        Integer status = user.getStatus();
        status = status ^ 1;
        user.setStatus(status);
        userDao.save(user);
    }

    /**
     * 根据ID删除用户
     * @param id
     */
    @Override
    public void deleteUserById(String id) {
        if (id == null || id.isEmpty()) {
            throw new ServiceException(ServiceCode.ERROR_IS_ASSOCIATED, "ID不能为空！");
        }
        Optional<User> userOpt= userDao.findById(id);
        if (!userOpt.isPresent()) {
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "用户不存在！");
        }
        User user = userOpt.get();
        userDao.deleteById(user.getId());
    }

    /**
     * 分页查询所有用户
     * @param pageDTO
     * @return PageVO<List<UserVO>>
     */
    @Override
    public PageVO<List<UserVO>> getAllUserListPage(PageDTO pageDTO) {
        List<UserVO> userVOList = new ArrayList<>();
        List<User> userList = userDao.findAll();
        if (userList == null || userList.isEmpty()) {
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "暂无用户！");
        }
        System.out.println("用户列表：" + userList);

        for (User user: userList) {
            UserVO userVO = new UserVO(user);
            userVOList.add(userVO);
        }
        Long cnt = userDao.count();
        PageVO<List<UserVO>> userVOListPage = new PageVO<>(
                pageDTO.getPageIndex(),
                pageDTO.getPageSize(),
                Math.toIntExact(cnt),
                userVOList
        );
        return userVOListPage;
    }

    /**
     * 获取用户数据
     * @return Long
     */
    @Override
    public Long countUser() {
        return userDao.count();
    }

    /**
     * 根据用户名模糊查询
     * @param str
     * @return List<UserVO>
     */
    @Override
    public List<UserVO> findUserByStr(String str) {
        // 模糊匹配规则
        ExampleMatcher matcher=ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        User user = new User();
        user.setUsername(str);
        Example<User> userExample = Example.of(user, matcher);
        List<User> userList = userDao.findAll(userExample);
        if (userList == null || userList.isEmpty()) {
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, "没有相应的用户！");
        }
        List<UserVO> userVOList = new ArrayList<>();
        for (User tuser: userList) {
            UserVO userVO = new UserVO(tuser);
            userVOList.add(userVO);
        }
        return userVOList;
    }
}
