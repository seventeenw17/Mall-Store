package com.mall.classes.product.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("t_product")
public class Product {
    @Id
    private String id;
    // 商品名
    private String name;
    // 单价
    private Double price;
    // 库存
    private Integer num;
    // 访问量
    private Long visitCount;
    // 添加时间
    private Date addDate;
    // 状态 | 1:上架，0:下架
    private Integer status;
    // 描述
    private String info;
    // 图片
    private String picture;
    // 创建人
    private String createdUser;
    // 创建时间
    private Date createdTime;
    // 最近修改人
    private String modifiedUser;
    // 最近修改时间
    private Date modifiedTime;
}
