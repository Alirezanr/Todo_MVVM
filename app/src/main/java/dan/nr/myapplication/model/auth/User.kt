package dan.nr.myapplication.model.auth

import com.google.gson.annotations.SerializedName

data class User(val id: Int,
                val name: String,
                val email: String,
                @SerializedName("profile_image_url")
                val profileImageUrl: String? = null,
                @SerializedName("access_token")
                val accessToken: String)