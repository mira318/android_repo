package com.example.git_repo_app

import com.example.git_repo_app.response.Item
import com.example.git_repo_app.response.Owner
import com.example.git_repo_app.response.Repos
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class RepoAdapterTest {

    var item1 = Item(1, "first", "first item",
            Owner("masha", 156, "masha's_avatar url"),
            "masha's repo html_url", "masha's repo description")

    var item2 = Item(2, "second", "second item",
            Owner("oleg", 789, "oleg's_avatar url"),
            "oleg's repo html_url", "oleg's repo description")

    var item3 = Item(3, "third", "third item",
            Owner("den", 43, "dens's_avatar url"),
            "den's repo html_url", "den's repo description")
    var repos = Repos(listOf(item1, item2, item3))
    var adapter = RepoAdapter(repos)


    @Test
    fun getItemCountTest() {
        assert(adapter.itemCount == 3)
        repos = Repos(listOf(item1, item2, item3, Item(4, "fourth", "fourth item",
                Owner("fourht owner", 77, "avatar fourth"),
                "forth repo html", "fourth description")))
        assert(adapter.itemCount == 3)
        adapter = RepoAdapter(repos)
        assert(adapter.itemCount == 4)

    }

}