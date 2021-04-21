package com.example.git_repo_app.api

import com.example.git_repo_app.responce.Repos
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepoTaker {
    private var git: GitService

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    init{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        git = retrofit.create(GitService::class.java)
    }

    suspend fun getRepos(): Repos{
        println("repository retriever get repositories")
        return git.searchRepos().execute().body()
    }
}