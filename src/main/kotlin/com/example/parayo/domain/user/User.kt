package com.example.parayo.domain.user

import com.example.parayo.domain.jpa.BaseEntity
import javax.persistence.Entity

@Entity(name = "user")
class User(
    var email: String,
    var password: String,
    var name: String,
    var fcmToken: String?
) : BaseEntity()
