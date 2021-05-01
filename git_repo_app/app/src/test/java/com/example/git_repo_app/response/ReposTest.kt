package com.example.git_repo_app.response

import org.junit.Test

import org.junit.Assert.*

class ReposTest {

    @Test
    fun getItemsTest() {
        var item1 = Item(1, "first", "first item",
                Owner("masha", 156, "masha's_avatar url"),
                "masha's repo html_url", "masha's repo description")

        var item2 = Item(2, "second", "second item",
                Owner("oleg", 789, "oleg's_avatar url"),
                "oleg's repo html_url", "oleg's repo description")

        var item3 = Item(3, "third", "third item",
                Owner("den", 43, "dens's_avatar url"),
                "den's repo html_url", "den's repo description")

        var repos: Repos
        repos = Repos(listOf(item1, item2, item3))
        assert(repos.items == listOf(item1, item2, item3))
        assert(repos.items[0]== item1)
        assert(repos.items[1].owner.id == 789.toLong())
        assert(repos.items[2].description == "den's repo description")
        assert(repos.items.get(1).description == "oleg's repo description")
    }
}