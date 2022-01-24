package com.example.githubapp.domain.entity

import com.example.githubapp.domain.usecase.TokenType

data class TokenData(
    val accessToken: String,
    val tokenType: TokenType?
)
