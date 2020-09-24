package com.example.parayo.domain.inquiry

import com.example.parayo.common.ParayoException
import com.example.parayo.domain.fcm.NotificationService
import com.example.parayo.domain.product.Product
import com.example.parayo.domain.product.ProductService
import com.example.parayo.domain.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class InquiryService @Autowired constructor(
    private val inquiryRepository: InquiryRepository,
    private val productService: ProductService,
    private val userService: UserService,
    private val notificationService: NotificationService
) {

    fun register(request: InquiryRequest, userId: Long) {
        val product = productService.get(request.productId)
            ?: throw ParayoException("상품 정보를 찾을 수 없습니다.")

        val inquiry = saveInquiry(request, userId, product)
        sendNotification(request, inquiry)
    }

    private fun saveInquiry(
        request: InquiryRequest,
        userId: Long,
        product: Product
    ) = if (request.type == InquiryType.QUESTION) {
        if (userId == product.userId) {
            throw ParayoException("자신의 상품에는 질문할 수 없습니다.")
        }
        val inquiry = Inquiry(
            request.productId,
            userId,
            product.userId,
            request.content
        )
        inquiryRepository.save(inquiry)
    } else {
        request.inquiryId
            ?.let(inquiryRepository::findByIdOrNull)
            ?.apply {
                require(productId == request.productId) { "답변 데이터 오류." }
                if (productOwnerId != userId) {
                    throw ParayoException("자신의 상품에만 답변할 수 있습니다.")
                }
                answer = request.content
                inquiryRepository.save(this)
            } ?: throw ParayoException("질문 데이터를 찾을 수 없습니다.")
    }

    private fun sendNotification(
        request: InquiryRequest,
        inquiry: Inquiry
        ) {
        val targetUser = if (request.type == InquiryType.QUESTION) {
            userService.find(inquiry.productOwnerId)
        } else {
            userService.find(inquiry.requestUserId)
        }

        targetUser?.run {
            notificationService.sendToUser(this, "상품문의", request.content)
        }
    }

}