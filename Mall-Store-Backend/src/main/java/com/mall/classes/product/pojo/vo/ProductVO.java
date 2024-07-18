package com.mall.classes.product.pojo.vo;

import com.mall.classes.product.pojo.entity.Product;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class ProductVO {
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

    public ProductVO() {
    }

    public ProductVO(String id, String name, Double price, Integer num, Long visitCount, Date addDate, Integer status, String info, String picture) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.num = num;
        this.visitCount = visitCount;
        this.addDate = addDate;
        this.status = status;
        this.info = info;
        this.picture = picture;
    }

    public ProductVO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.num = product.getNum();
        this.visitCount = product.getVisitCount();
        this.addDate = product.getAddDate();
        this.status = product.getStatus();
        this.info = product.getInfo();
        this.picture = product.getPicture();
    }
}
