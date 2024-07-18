package com.mall.classes.product.pojo.dto;

import lombok.Data;

@Data
public class ProductAddDTO {
    // 商品名
    private String name;
    // 单价
    private Double price;
    // 描述
    private String info;
    // 图片
    private String picture;
}
