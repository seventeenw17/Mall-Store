package com.mall.classes.product.controller;

import com.mall.classes.product.pojo.dto.ProductAddDTO;
import com.mall.classes.product.pojo.vo.ProductVO;
import com.mall.classes.product.service.ProductService;
import com.mall.classes.user.pojo.vo.UserLoginVO;
import com.mall.tools.common.Result;
import com.mall.tools.security.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * 新增商品
     *
     * @param productAddDTO
     * @param req
     * @return Result<Void>
     */
    @PostMapping("/add")
    public Result<Void> addProduct(@RequestBody @Validated ProductAddDTO productAddDTO,
                                   HttpServletRequest req) {
        String token = req.getHeader("authorization");
        UserLoginVO curUser = JWTUtils.getUserInfo(token);
        productService.insertProduct(productAddDTO, curUser);
        return Result.ok();
    }

    @GetMapping("/get/all")
    public Result<List<ProductVO>> getAllProduct(HttpServletRequest req) {
        String token = req.getHeader("authorization");
        UserLoginVO curUser = JWTUtils.getUserInfo(token);
        List<ProductVO> productVOList = productService.getAllProductList();
        return Result.ok(productVOList);
    }
}