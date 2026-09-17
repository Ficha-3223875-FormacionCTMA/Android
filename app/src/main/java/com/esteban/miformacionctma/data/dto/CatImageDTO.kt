package com.esteban.miformacionctma.data.dto

data class CatImageDTO(
    val id: String,
    val url: String,
    val width: Int = 0,
    val height: Int = 0
)