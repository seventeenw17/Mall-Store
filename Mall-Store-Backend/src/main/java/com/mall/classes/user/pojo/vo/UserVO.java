package com.mall.classes.user.pojo.vo;

import com.mall.classes.user.pojo.entity.User;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Date;


@Data
public class UserVO {
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
    // 状态
    private Integer status;
    // 图片
    private String picture;
    // 创建人
    private String createdUser;
    // 创建时间
    private Date createdTime;
    // 最后修改人
    private String modifiedUser;
    // 最后修改时间
    private Date modifiedTime;

    public UserVO() {

    }

    public UserVO(String id, Integer sex, String username, Integer age, String tel, String email, String address, Integer status, String picture, String createdUser, Date createdTime, String modifiedUser, Date modifiedTime) {
        this.id = id;
        this.sex = sex;
        this.username = username;
        this.age = age;
        this.tel = tel;
        this.email = email;
        this.address = address;
        this.status = status;
        this.picture = picture;
        this.createdUser = createdUser;
        this.createdTime = createdTime;
        this.modifiedUser = modifiedUser;
        this.modifiedTime = modifiedTime;
    }

    public UserVO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.age = user.getAge();
        this.sex = user.getSex();
        this.tel = user.getTel();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.status = user.getStatus();
        this.picture = user.getPicture();
        this.createdUser = user.getCreatedUser();
        this.createdTime = user.getCreatedTime();
        this.modifiedUser = user.getModifiedUser();
        this.modifiedTime = user.getModifiedTime();
    }
}
