package com.example.parayo.domain.product

import com.example.parayo.domain.jpa.BaseEntity
import javax.persistence.Entity

@Entity(name = "product_image")
class ProductImage(
    var path: String,
    var productId: Long? = null
) : BaseEntity() {
}