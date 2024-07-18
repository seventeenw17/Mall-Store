package com.mall.classes.user.pojo.vo;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
public class UserLoginVO {
    @Id
    private String id;
    // 用户名
    private String username;
    // token
    private String token;
}
