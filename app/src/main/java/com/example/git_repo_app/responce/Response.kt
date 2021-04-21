package com.example.git_repo_app.responce

data class Repos(val items: List<Item>)

data class Item(
        val id: Long?,
        val name: String?,
        val private: Boolean,
        val htmlUrl: String?,
        val description: String?,
        val creator: Creator)

data class Creator(val name: String?, val id: Long?, val avatarUrl:String?)