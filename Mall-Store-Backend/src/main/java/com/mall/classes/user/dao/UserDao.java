package com.mall.classes.user.dao;

import com.mall.classes.user.pojo.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDao extends MongoRepository<User, String> {

    /**
     * 根据用户名查找用户
     * @param username
     * @return User
     */
    User getUserByUsername(String username);
}
