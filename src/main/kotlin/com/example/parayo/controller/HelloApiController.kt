package com.example.parayo.controller

import com.example.parayo.common.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloApiController {

    @GetMapping("/api/v1/hello")
    fun hello() = ApiResponse.ok("world")

}