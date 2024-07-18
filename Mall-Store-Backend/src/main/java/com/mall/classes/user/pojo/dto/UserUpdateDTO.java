package com.mall.classes.user.pojo.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UserUpdateDTO {
    @Id
    private String id;
    // 用户名
    private String username;
    // 性别 | 1:男，2:女，0:未设置
    private Integer sex;
    // 年龄
    private Integer age;
    // 电话
    private String tel;
    // 邮箱
    private String email;
    // 地址
    private String address;
    // 图片
    private String picture;
}
