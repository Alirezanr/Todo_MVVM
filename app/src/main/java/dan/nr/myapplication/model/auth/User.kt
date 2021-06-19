package dan.nr.myapplication.model.auth

data class User(val id: Int,
                val name: String,
                val email: String,
                val profileImageUrl: String? = null,
                val accessToken: String)