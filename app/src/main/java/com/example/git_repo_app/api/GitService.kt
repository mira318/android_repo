package com.example.git_repo_app.api

import com.example.git_repo_app.responce.Repos
import retrofit2.Call
import retrofit2.http.GET

interface GitService {
    @GET("/search/repositories?q=witch+language:python&sort=stars&order=desc&per_page=50")
    suspend fun searchRepos(): Call<Repos>
}