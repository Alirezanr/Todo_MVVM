package dan.nr.myapplication.model.todo

import com.google.gson.annotations.SerializedName

data class Todo(
        val id: Int,
        val category: String,
        val description: String,
        val priority: String,
        val title: String,
        @SerializedName("user_id") val userId: Int,
        @SerializedName("image_url") val imageUrl: Any,
        @SerializedName("is_done") val isDone: Int,
        @SerializedName("location_lat") val locationLat: String,
        @SerializedName("location_lang") val locationLang: String,
        @SerializedName("created_at") val createdAt: String,
        @SerializedName("updated_at") val updatedAt: String)