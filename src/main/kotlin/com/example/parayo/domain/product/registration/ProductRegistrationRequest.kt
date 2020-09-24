package com.example.parayo.domain.product.registration

data class ProductRegistrationRequest(
    val name: String,
    val description: String,
    val price: Int,
    val categoryId: Int,
    val imageIds: List<Long?>
)
