package com.ddevs.edgeify.models

import com.google.gson.annotations.SerializedName

data class ImageLinks(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("original")
    val originalLink:String,
    @SerializedName("edged")
    val edgeLink:String
)
