package com.example.parayo.controller

import com.example.parayo.common.ApiResponse
import com.example.parayo.domain.auth.SignupRequest
import com.example.parayo.domain.auth.SignupService
import com.example.parayo.domain.auth.UserContextHolder
import com.example.parayo.domain.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class UserApiController @Autowired constructor(
    private val signupService: SignupService,
    private val userService: UserService,
    private val userContextHolder: UserContextHolder
) {

    @PostMapping("/users")
    fun signup(@RequestBody signupRequest: SignupRequest) =
        ApiResponse.ok(signupService.signup(signupRequest))

    @PutMapping("/users/fcm-token")
    fun updateFcmToken(@RequestBody fcmToken: String) =
        userContextHolder.id?.let { userId ->
            ApiResponse.ok(userService.updateFcmToken(userId, fcmToken))
        } ?: ApiResponse.error("토큰 갱신 실패")

}