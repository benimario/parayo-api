package com.example.parayo.domain.inquiry

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface InquiryRepository : JpaRepository<Inquiry, Long> {

    fun findByProductIdAndIdLessThanOrderByIdDesc(
        productId: Long?,
        inquiryId: Long,
        pageagle: Pageable
    ) : List<Inquiry>

    fun findByProductIdAndIdGreaterThanOrderByIdDesc(
        productId: Long?,
        inquiryId: Long,
        pageable: Pageable
    ) : List<Inquiry>

    fun findByRequestUserIdAndIdLessThanOrderByIdDesc(
        requestUserId: Long?,
        inquiryId: Long,
        pageagle: Pageable
    ) : List<Inquiry>

    fun findByRequestUserIdAndIdGreaterThanOrderByIdDesc(
        requestUserId: Long?,
        inquiryId: Long,
        pageable: Pageable
    ) : List<Inquiry>

    fun findByProductOwnerIdAndIdLessThanOrderByIdDesc(
        productOwnerId: Long?,
        inquiryId: Long,
        pageagle: Pageable
    ) : List<Inquiry>

    fun findByProductOwnerIdAndIdGreaterThanOrderByIdDesc(
        productOwnerId: Long?,
        inquiryId: Long,
        pageable: Pageable
    ) : List<Inquiry>

}