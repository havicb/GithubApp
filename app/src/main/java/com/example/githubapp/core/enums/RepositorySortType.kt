package com.example.githubapp.core.enums

enum class RepositorySortType(
    val fullName: String
) {
    UPDATED("Updated"),
    STARS("Stars"),
    FORKS("Forks");

    companion object {
        fun urlName(type: RepositorySortType): String {
            return when (type) {
                UPDATED -> "updated"
                STARS -> "stars"
                FORKS -> "forks"
            }
        }
    }
}
