package com.mall.classes.product.dao;

import com.mall.classes.product.pojo.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductDao extends MongoRepository<Product, String> {

}
