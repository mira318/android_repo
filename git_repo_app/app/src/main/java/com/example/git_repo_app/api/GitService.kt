package com.example.git_repo_app.api

import com.example.git_repo_app.response.Repos
import retrofit2.Call
import retrofit2.http.GET

interface GitService {

    @GET("/repositories")
    fun takeAllRepositories(): Call<Repos>

    @GET("search/repositories?q=language:kotlin&sort=stars&order=desc&per_page=100")
    fun searchRepos(): Call<Repos>
}