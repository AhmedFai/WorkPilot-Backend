package com.faizan.workpilot.security

import com.faizan.workpilot.entity.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.util.Date
import javax.crypto.SecretKey

@Service
class JwtService(
    @Value("\${jwt.secret}")
    private val secretKey: String,

    @Value("\${jwt.expiration}")
    private val expiration: Long
) {

    fun generateToken(
        user: User
    ): String {

        return Jwts
            .builder()
            .subject(user.email)
            .issuedAt(Date())
            .expiration(
                Date(
                    System.currentTimeMillis() + expiration
                )
            )
            .signWith(signingKey)
            .compact()

    }

    fun extractEmail(
        token: String
    ): String {
        return extractClaim(token) {
            it.subject
        }
    }

    fun extractExpiration(
        token: String
    ): Date {

        return extractClaim(token) {
            it.expiration
        }
    }

    fun isTokenExpired(
        token: String
    ): Boolean {

        return extractExpiration(token).before(Date())

    }

    fun validateToken(
        token: String,
        user: User
    ): Boolean {

        val email = extractEmail(token)

        return email == user.email &&
                !isTokenExpired(token)

    }

    private fun <T> extractClaim(
        token: String,
        claimsResolver: (Claims) -> T
    ): T {

        val claims = extractAllClaims(token)

        return claimsResolver(claims)
    }

    private fun extractAllClaims(
        token: String
    ): Claims {
        return Jwts
            .parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    private val signingKey: SecretKey by lazy {
        Keys.hmacShaKeyFor(
            secretKey.toByteArray(StandardCharsets.UTF_8)
        )
    }

}