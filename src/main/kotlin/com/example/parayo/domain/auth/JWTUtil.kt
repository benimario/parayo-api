package com.example.parayo.domain.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import java.util.*

object JWTUtil {

    private const val ISSUER = "Parayo"
    private const val SUBJECT = "Auth"
    private const val EXPIRE_TIME = 60L * 60 * 2 * 1000
    private const val REFRESH_EXPIRE_TIME = 60L * 60 * 24 * 30 * 1000

    private val secret = "your-secret"
    private val algorithm: Algorithm = Algorithm.HMAC256(secret)

    private val refreshSecret = "your-refresh-secret"
    private val refreshAlgorithm: Algorithm = Algorithm.HMAC256(refreshSecret)

    fun createToken(email: String) = JWT.create()
        .withIssuer(ISSUER)
        .withSubject(SUBJECT)
        .withIssuedAt(Date())
        .withExpiresAt(Date(Date().time + EXPIRE_TIME))
        .withClaim(JWTClaims.EMAIL, email)
        .sign(algorithm)

    fun createRefreshToken(email: String) = JWT.create()
        .withIssuer(ISSUER)
        .withSubject(SUBJECT)
        .withIssuedAt(Date())
        .withExpiresAt(Date(Date().time + REFRESH_EXPIRE_TIME))
        .withClaim(JWTClaims.EMAIL, email)
        .sign(refreshAlgorithm)

    fun verify(token: String): DecodedJWT =
        JWT.require(algorithm)
            .withIssuer(ISSUER)
            .build()
            .verify(token)

    fun verifyRefresh(token: String): DecodedJWT =
        JWT.require(refreshAlgorithm)
            .withIssuer(ISSUER)
            .build()
            .verify(token)

    fun extractEmail(jwt: DecodedJWT): String =
        jwt.getClaim(JWTClaims.EMAIL).asString()

    object JWTClaims {
        const val EMAIL = "email"
    }

}
