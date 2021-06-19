package dan.nr.myapplication.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthEntity(
        @SerializedName("id")
        @Expose
        var id: Int,

        @SerializedName("name")
        @Expose
        var name: String,

        @SerializedName("email")
        @Expose
        var email: String,

        @SerializedName("profile_image_url")
        @Expose
        var profileImageUrl: String? = null,

        @SerializedName("access_token")
        @Expose
        var access_token: String)