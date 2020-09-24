package com.example.parayo.domain.product

data class ProductListItemResponse(
    val id: Long,
    val name: String,
    val description: String,
    val price: Int,
    val status: String,
    val sellerId: Long,
    val imagePaths: List<String>
)

fun Product.toProductListItemResponse() = id?.let {
    ProductListItemResponse(
        it,
        name,
        description,
        price,
        status.name,
        userId,
        images.map { it.toThumbs() }
    )
}

fun ProductImage.toThumbs(): String {
    val ext = path.takeLastWhile { it != '.' }
    val fileName = path.takeWhile { it != '.' }
    val thumbnailPath = "$fileName-thumb.$ext"

    return if (ext == "jpg") thumbnailPath else "$thumbnailPath.jpg"
}