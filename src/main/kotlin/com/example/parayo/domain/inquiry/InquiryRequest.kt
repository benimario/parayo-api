package com.example.parayo.domain.inquiry

data class InquiryRequest(
    val type: InquiryType,
    val inquiryId: Long?,
    val productId: Long,
    val content: String
)
