package com.example.git_repo_app.api

import com.example.git_repo_app.response.Repos
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
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
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
        git = retrofit.create(GitService::class.java)
    }

    fun getRepos(callback: Callback<Repos>) {
        println("repository retriever get repositories")
        val call = git.searchRepos()
        return call.enqueue(callback)
    }
}