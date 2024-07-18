package com.mall.classes.product.service;

import com.mall.classes.product.pojo.dto.ProductAddDTO;
import com.mall.classes.product.pojo.vo.ProductVO;
import com.mall.classes.user.pojo.vo.UserLoginVO;

import java.util.List;

public interface ProductService {
    /**
     * 新增商品
     * @param productAddDTO
     * @param curUser
     */
    void insertProduct(ProductAddDTO productAddDTO, UserLoginVO curUser);

    /**
     * 获取所有商品列表
     * @return List<ProductVO>
     */
    List<ProductVO> getAllProductList();
}
