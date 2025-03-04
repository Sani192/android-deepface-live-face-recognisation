package com.trident.ui.gallery.models

data class MoveImagesRequest(
    val directoryName: String,
    val imageNames: List<String>
)
