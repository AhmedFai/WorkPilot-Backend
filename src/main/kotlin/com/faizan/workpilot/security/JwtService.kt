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

    @Value("\${jwt.access.secret}")
    private val accessSecret: String,

    @Value("\${jwt.refresh.secret}")
    private val refreshSecret: String,

    @Value("\${jwt.access.expiration}")
    private val accessExpiration: Long,

    @Value("\${jwt.refresh.expiration}")
    private val refreshExpiration: Long

) {

    fun generateAccessToken(
        user: User
    ): String {
        return generateToken(
            user,
            accessExpiration,
            getAccessSigningKey()
        )
    }

    fun generateRefreshToken(
        user: User
    ): String {
        return generateToken(
            user,
            refreshExpiration,
            getRefreshSigningKey()
        )
    }

    private fun generateToken(
        user: User,
        expiration: Long,
        signingKey: SecretKey
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

    fun extractAccessEmail(
        token: String
    ): String {
        return extractClaim(
            token,
            getAccessSigningKey()
        ) {
            it.subject
        }
    }

    fun extractRefreshEmail(
        token: String
    ): String {
        return extractClaim(
            token,
            getRefreshSigningKey()
        ) {
            it.subject
        }
    }

    fun validateAccessToken(
        token: String,
        user: User
    ): Boolean {

        val email = extractAccessEmail(token)

        return email == user.email &&
                !isTokenExpired(
                    token,
                    getAccessSigningKey()
                )
    }

    fun validateRefreshToken(
        token: String,
        user: User
    ): Boolean {

        val email = extractRefreshEmail(token)

        return email == user.email &&
                !isTokenExpired(
                    token,
                    getRefreshSigningKey()
                )
    }

    private fun isTokenExpired(
        token: String,
        signingKey: SecretKey
    ): Boolean {

        return extractExpiration(
            token,
            signingKey
        ).before(Date())

    }

    private fun extractExpiration(
        token: String,
        signingKey: SecretKey
    ): Date {

        return extractClaim(
            token,
            signingKey
        ) {
            it.expiration
        }

    }

    private fun <T> extractClaim(
        token: String,
        signingKey: SecretKey,
        claimsResolver: (Claims) -> T
    ): T {

        val claims = extractAllClaims(
            token,
            signingKey
        )

        return claimsResolver(claims)

    }

    private fun extractAllClaims(
        token: String,
        signingKey: SecretKey
    ): Claims {

        return Jwts
            .parser()
            .verifyWith(signingKey)
            .build()
            .parseSignedClaims(token)
            .payload

    }

    private fun getAccessSigningKey(): SecretKey {

        return Keys.hmacShaKeyFor(
            accessSecret.toByteArray(StandardCharsets.UTF_8)
        )

    }

    private fun getRefreshSigningKey(): SecretKey {

        return Keys.hmacShaKeyFor(
            refreshSecret.toByteArray(StandardCharsets.UTF_8)
        )

    }

}