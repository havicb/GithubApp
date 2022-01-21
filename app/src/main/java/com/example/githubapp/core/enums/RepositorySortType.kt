package com.example.githubapp.core.enums

enum class RepositorySortType(
    val fullName: String
) {
    UPDATED("Updated"),
    STARS("Stars"),
    FORKS("Forks");

    companion object {
        fun urlName(type: RepositorySortType?): String {
            if(type == null)
                return ""
            return when (type) {
                UPDATED -> "updated"
                STARS -> "stars"
                FORKS -> "forks"
            }
        }
    }
}
