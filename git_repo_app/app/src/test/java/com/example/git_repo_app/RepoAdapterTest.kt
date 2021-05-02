package com.example.git_repo_app

import com.example.git_repo_app.response.Item
import com.example.git_repo_app.response.Owner
import com.example.git_repo_app.response.Repos
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class RepoAdapterTest {

    lateinit var item1: Item
    lateinit var item2: Item
    lateinit var item3: Item
    lateinit var repos: Repos
    lateinit var adapter: RepoAdapter

    @Before
    fun prepare() {
        item1 = Item(1, "first", "first item",
                Owner("masha", 156, "masha's_avatar url"),
                "masha's repo html_url", "masha's repo description")

        item2 = Item(2, "second", "second item",
                Owner("oleg", 789, "oleg's_avatar url"),
                "oleg's repo html_url", "oleg's repo description")

        item3 = Item(3, "third", "third item",
                Owner("den", 43, "dens's_avatar url"),
                "den's repo html_url", "den's repo description")
        repos = Repos(listOf(item1, item2, item3))
        adapter = RepoAdapter(repos)

    }

    @Test
    fun getItemCountTest() {
        assert(adapter.itemCount == 3)
        repos = Repos(listOf(item1, item2, item3, Item(4, "fourth", "fourth item",
                Owner("fourth owner", 77, "avatar fourth"),
                "fourth repo html", "fourth description")))
        assert(adapter.itemCount == 3)
        adapter = RepoAdapter(repos)
        assert(adapter.itemCount == 4)
        repos = Repos(listOf())
        adapter = RepoAdapter(repos)
        assert(adapter.itemCount == 0)

        var bigList = ArrayList<Item>()

        for(i in 0 until 10000) {
            bigList.add(Item(1, "a", "b", Owner("c", 2, "d"),
                    "e", "f"))
        }
        adapter = RepoAdapter(Repos(bigList))
        assert(adapter.itemCount == 10000)
    }


    /*@Test
  fun onCreateOtherViews() {

      onView(withId(R.id.edit_search)).check(matches(withHint(R.string.hint_search_string)))
      onView(withId(R.id.edit_search)).perform(typeText("star&!+?***top&**&???"))
      onView(withId(R.id.edit_search)).check(matches(withText("star&!+?***top&**&???")))
      onView(withId(R.id.btn_search)).check(matches(isClickable()))
      //onView(withId(R.id.btn_search)).perform(click())
      //onView(withId(R.id.repoList)).check(matches(isDisplayed()))
  }*/
}