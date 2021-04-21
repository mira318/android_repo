package com.example.git_repo_app.response

data class Repos(val items: List<Item>)

data class Item(
        val id: Long?,
        val name: String?,
        val fullName: String?,
        val owner: Owner,
        val private: Boolean,
        val description: String?,
        val htm_url: String?)

data class Owner(val login: String?, val id: Long?, val avatar_url:String?)