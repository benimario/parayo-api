package com.example.parayo.domain.inquiry

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class InquirySearchService @Autowired constructor(
    private val inquiryRepository: InquiryRepository
) {

    fun getProductInquiries(
        inquiryId: Long,
        productId: Long?,
        requestUserId: Long?,
        productOwnerId: Long?,
        direction: String
    ): List<Inquiry> {
        val condition = InquirySearchCondition(
            direction,
            productId != null,
            requestUserId != null,
            productOwnerId != null
        )
        return when (condition) {
            NEXT_FOR_PRODUCT -> inquiryRepository
                .findByProductIdAndIdLessThanOrderByIdDesc(
                    productId,
                    inquiryId,
                    PageRequest.of(0, 10)
                )
            PREV_FOR_PRODUCT -> inquiryRepository
                .findByProductIdAndIdGreaterThanOrderByIdDesc(
                    productId,
                    inquiryId,
                    PageRequest.of(0, 10)
                )
            NEXT_FOR_USER -> inquiryRepository
                .findByRequestUserIdAndIdLessThanOrderByIdDesc(
                    requestUserId,
                    inquiryId,
                    PageRequest.of(0, 10)
                )
            PREV_FOR_USER -> inquiryRepository
                .findByRequestUserIdAndIdGreaterThanOrderByIdDesc(
                    requestUserId,
                    inquiryId,
                    PageRequest.of(0, 10)
                )
            NEXT_FOR_USER_PRODUCT -> inquiryRepository
                .findByProductOwnerIdAndIdLessThanOrderByIdDesc(
                    productOwnerId,
                    inquiryId,
                    PageRequest.of(0, 10)
                )
            PREV_FOR_USER_PRODUCT -> inquiryRepository
                .findByProductOwnerIdAndIdGreaterThanOrderByIdDesc(
                    productOwnerId,
                    inquiryId,
                    PageRequest.of(0, 10)
                )
            else -> throw IllegalArgumentException("문의 검색 조건 오류.")
        }
    }

    data class InquirySearchCondition(
        val direction: String,
        val hasProductId: Boolean = false,
        val hasRequestUserId: Boolean = false,
        val hasProductOwnerId: Boolean = false
    )

    companion object {
        val NEXT_FOR_PRODUCT = InquirySearchCondition("next", true)
        val PREV_FOR_PRODUCT = InquirySearchCondition("prev", true)
        val NEXT_FOR_USER =
            InquirySearchCondition("next", hasRequestUserId = true)
        val PREV_FOR_USER =
            InquirySearchCondition("prev", hasRequestUserId = true)
        val NEXT_FOR_USER_PRODUCT =
            InquirySearchCondition("next", hasProductOwnerId = true)
        val PREV_FOR_USER_PRODUCT =
            InquirySearchCondition("prev", hasProductOwnerId = true)
    }

}