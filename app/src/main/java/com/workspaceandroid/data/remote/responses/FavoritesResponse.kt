package com.workspaceandroid.data.remote.responses

import com.google.gson.annotations.SerializedName

data class FavoritesResponse (
    @SerializedName("created_at") val created: Int,
    @SerializedName("product_img_url") val image: String?,
    @SerializedName("product_name") val name: String?,
    @SerializedName("product_content") val content: String?,
    @SerializedName("product_code") val code: String?

)