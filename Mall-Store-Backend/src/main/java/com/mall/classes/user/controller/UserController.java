package com.mall.classes.user.controller;

import com.mall.classes.user.pojo.dto.UserUpdateDTO;
import com.mall.classes.user.pojo.dto.UserLoginDTO;
import com.mall.classes.user.pojo.vo.UserLoginVO;
import com.mall.tools.common.Result;
import com.mall.classes.user.pojo.dto.UserRegisterDTO;
import com.mall.classes.user.pojo.vo.UserVO;
import com.mall.classes.user.service.UserService;
import com.mall.tools.page.PageDTO;
import com.mall.tools.page.PageVO;
import com.mall.tools.security.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户注册接口
     * @return Result<Void>
     */
    @PostMapping("/register")
    public Result<Void> register(@RequestBody @Validated UserRegisterDTO userRegisterDTO) {
        userService.register(userRegisterDTO);
        return Result.ok();
    }

    /**
     * 用户登录
     * @param userLoginDTO
     * @return Result<UserLoginVO>
     */
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody @Validated UserLoginDTO userLoginDTO) {
        System.out.println("用户登录信息：" + userLoginDTO);
        UserLoginVO userLoginVO = userService.login(userLoginDTO);
        return Result.ok(userLoginVO);
    }

    /**
     * 获取用户列表接口
     * @return Result<List<UserVO>>
     */
    @GetMapping("/get/all")
    public Result<List<UserVO>> getAllUserList(HttpServletRequest req) {
        String token = req.getHeader("authorization");
        UserLoginVO curUser = JWTUtils.getUserInfo(token);
        List<UserVO> userVOList = userService.getAllUserList();
        return Result.ok(userVOList);
    }

    /**
     * 根据用户名查找用户接口
     * @param username
     * @return Result<UserVO>
     */
    @GetMapping("/find/{username}")
    public Result<UserVO> findUserByUsername(@PathVariable String username, HttpServletRequest req) {
        String token = req.getHeader("authorization");
        UserLoginVO curUser = JWTUtils.getUserInfo(token);
        UserVO userVO = userService.findUserByUsername(username);
        return Result.ok(userVO);
    }

    /**
     * 更新用户信息
     * @param userInfoUpdateDTO
     * @param req
     * @return Result<Void>
     */
    @PutMapping("/update")
    public Result<Void> updateUser(@RequestBody @Validated UserUpdateDTO userInfoUpdateDTO,
                                       HttpServletRequest req) {
        String token = req.getHeader("authorization");
        UserLoginVO curUser = JWTUtils.getUserInfo(token);
        userService.updateUserInfo(userInfoUpdateDTO, curUser);
        return Result.ok();
    }

    /**
     * 修改用户状态接口
     * @param id
     * @param req
     * @return Result<Void>
     */
    @PutMapping("/updateStatus/{id}")
    public Result<Void> updateStatusById(@PathVariable String id, HttpServletRequest req) {
        String token = req.getHeader("authorization");
        UserLoginVO curUser = JWTUtils.getUserInfo(token);
        userService.updateStatusById(id, curUser);
        return Result.ok();
    }

    /**
     * 根据ID删除用户接口
     * @param id
     * @param req
     * @return Result<Void>
     */
    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteUser(@PathVariable String id, HttpServletRequest req) {
        String token = req.getHeader("authorization");
        UserLoginVO curUser = JWTUtils.getUserInfo(token);
        userService.deleteUserById(id);
        return Result.ok();
    }

    /**
     * 分页查询用户接口
     * @param pageDTO
     * @param req
     * @return Result<PageVO<List<UserVO>>>
     */
    @GetMapping("/page/all")
    public Result<PageVO<List<UserVO>>> getAllUserListPage(@RequestBody @Validated PageDTO pageDTO,
                                                           HttpServletRequest req) {
        String token = req.getHeader("authorization");
        UserLoginVO curUser = JWTUtils.getUserInfo(token);
        PageVO<List<UserVO>> userVOListPageVO = userService.getAllUserListPage(pageDTO);
        return Result.ok(userVOListPageVO);
    }

    /**
     * 获取用户数量接口
     * @param req
     * @return Result<Long>
     */
    @GetMapping("/count")
    public Result<Long> getUserCount(HttpServletRequest req) {
        String token = req.getHeader("authorization");
        UserLoginVO curUser = JWTUtils.getUserInfo(token);
        Long cnt = userService.countUser();
        return Result.ok(cnt);
    }

    /**
     * 根据用户名模糊查询接口
     * @param str
     * @param req
     * @return Result<List<UserVO>>
     */
    @GetMapping("/finds/{str}")
    public Result<List<UserVO>> findUserByStr(@PathVariable String str, HttpServletRequest req) {
        String token = req.getHeader("authorization");
        UserLoginVO curUser = JWTUtils.getUserInfo(token);
        List<UserVO> userVOList = userService.findUserByStr(str);
        return Result.ok(userVOList);
    }
}
