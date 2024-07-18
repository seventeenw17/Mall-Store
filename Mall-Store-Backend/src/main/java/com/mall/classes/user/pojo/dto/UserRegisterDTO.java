package com.mall.classes.user.pojo.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {
    // 用户名
    private String username;
    // 密码
    private String password;
    // 确认密码
    private String rePassword;
}
