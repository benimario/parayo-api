package com.example.parayo.domain.fcm

import com.example.parayo.domain.user.User
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class NotificationService {

    private val firebaseApp by lazy {
        val inputStream = ClassPathResource(
            "parayo-fcdad-firebase-adminsdk-7esu4-accd6f8d17.json"
        ).inputStream

        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(inputStream))
            .build()

        FirebaseApp.initializeApp(options)
    }

    fun sendToUser(user: User, title: String, content: String) =
        user.fcmToken?.let { token ->
            val message = Message.builder()
                .setToken(token)
                .putData("title", title)
                .putData("content", content)
                .build()

            FirebaseMessaging.getInstance(firebaseApp).send(message)
        }

}