package com.example.git_repo_app.response

data class Repos(val items: List<Item>)

data class Item(
        val id: Long?,
        val name: String?,
        val fullName: String?,
        val owner: Owner,
        val html_url: String?,
        val description: String?)

data class Owner(val login: String?, val id: Long?, val avatar_url:String?)