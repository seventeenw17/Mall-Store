package com.mall.classes.user.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("t_user")
public class User {
    @Id
    private String id;
    // 用户名
    private String username;
    // 密码
    private String password;
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
    // 状态
    private Integer status;
    // 图片
    private String picture;
    // 盐值
    private String salt;
    // 创建人
    private String createdUser;
    // 创建时间
    private Date createdTime;
    // 最后修改人
    private String modifiedUser;
    // 最后修改时间
    private Date modifiedTime;
}
