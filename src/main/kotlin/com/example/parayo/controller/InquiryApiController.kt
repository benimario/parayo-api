package com.example.parayo.controller

import com.example.parayo.common.ApiResponse
import com.example.parayo.common.ParayoException
import com.example.parayo.domain.auth.UserContextHolder
import com.example.parayo.domain.inquiry.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class InquiryApiController @Autowired constructor(
    private val inquiryService: InquiryService,
    private val inquirySearchService: InquirySearchService,
    private val userContextHolder: UserContextHolder
) {

    @PostMapping("/inquiries")
    fun register(@RequestBody request: InquiryRequest) =
        userContextHolder.id?.let { userId ->
            ApiResponse.ok(inquiryService.register(request, userId))
        } ?: throw ParayoException("상품 문의에 필요한 사용자 정보가 없습니다.")

    @GetMapping("/inquiries")
    fun getProductInquiries(
        @RequestParam inquiryId: Long,
        @RequestParam(required = false) productId: Long?,
        @RequestParam(required = false) requestUserId: Long?,
        @RequestParam(required = false) productOwnerId: Long?,
        @RequestParam direction: String
    ) = inquirySearchService.getProductInquiries(
        inquiryId,
        productId,
        if (requestUserId == null) null else userContextHolder.id,
        if (productOwnerId == null) null else userContextHolder.id,
        direction
    ).mapNotNull(Inquiry::toInquiryResponse)
    .let { ApiResponse.ok(it) }

}