package com.mall.classes.product.service.impl;

import com.mall.classes.product.dao.ProductDao;
import com.mall.classes.product.pojo.dto.ProductAddDTO;
import com.mall.classes.product.pojo.entity.Product;
import com.mall.classes.product.pojo.vo.ProductVO;
import com.mall.classes.product.service.ProductService;
import com.mall.classes.user.pojo.vo.UserLoginVO;
import com.mall.tools.common.ServiceCode;
import com.mall.tools.common.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static cn.hutool.core.date.DateTime.now;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    /**
     * 新增商品
     * @param productAddDTO
     * @param curUser
     */
    @Override
    public void insertProduct(ProductAddDTO productAddDTO, UserLoginVO curUser) {
        if (productAddDTO.getName() == null || productAddDTO.getName().isEmpty()) {
            throw new ServiceException(ServiceCode.ERROR_IS_ASSOCIATED, "商品名不能为空！");
        }
        if (productAddDTO.getPrice() == null) {
            throw new ServiceException(ServiceCode.ERROR_IS_ASSOCIATED, "商品价格不能为空！");
        }
        Product product = new Product();
        product.setName(productAddDTO.getName());
        product.setPrice(productAddDTO.getPrice());
        product.setInfo(productAddDTO.getInfo());
        product.setPicture(productAddDTO.getPicture());
        product.setAddDate(now());
        product.setNum(0);
        product.setVisitCount(0L);
        product.setStatus(0);
        product.setCreatedUser(curUser.getUsername());
        product.setCreatedTime(now());
        productDao.insert(product);
    }

    /**
     * 获取所有商品列表
     * @return List<ProductVO>
     */
    @Override
    public List<ProductVO> getAllProductList() {
        List<Product> productList = productDao.findAll();
        List<ProductVO> productVOList = new ArrayList<>();
        for (Product product : productList) {
            ProductVO productVO = new ProductVO(product);
            productVOList.add(productVO);
        }
        return productVOList;
    }
}
