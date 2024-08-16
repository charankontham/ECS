package com.charan.ecs.entity;

import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.units.qual.C;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column(name = "product_category_id")
    private int productCategoryId;

    @Column(name ="product_brand_id")
    private int productBrandId;

    @Column(name = "product_name")
    private String productName;

    @Column(name ="product_description")
    private String productDescription;

    @Column(name = "product_price")
    private float productPrice;

    @Column(name ="product_quantity")
    private int productQuantity;

    @Column(name = "product_color")
    private String productColor;

    @Column(name = "product_weight")
    private float productWeight;

    @Column(name = "product_dimensions")
    private String productDimensions;

    @Column(name = "product_condition")
    private String productCondition;

}
