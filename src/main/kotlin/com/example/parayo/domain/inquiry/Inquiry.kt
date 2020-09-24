package com.example.parayo.domain.inquiry

import com.example.parayo.domain.jpa.BaseEntity
import com.example.parayo.domain.product.Product
import com.example.parayo.domain.user.User
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
data class Inquiry(
    val productId: Long,
    val requestUserId: Long,
    val productOwnerId: Long,
    val question: String,
    var answer: String? = null
) : BaseEntity() {

    @ManyToOne
    @JoinColumn(
        name = "requestUserId",
        insertable = false,
        updatable = false
    )
    var requestUser: User? = null

    @ManyToOne
    @JoinColumn(
        name = "productOwnerId",
        insertable = false,
        updatable = false
    )
    var productOwner: User? = null

    @ManyToOne
    @JoinColumn(
        name = "productId",
        insertable = false,
        updatable = false
    )
    var product: Product? = null

}
