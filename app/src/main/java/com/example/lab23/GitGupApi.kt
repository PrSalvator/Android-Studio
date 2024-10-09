package com.example.lab23
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/repositories")
    fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10
    ): Call<RepositoryResponse>
}

data class RepositoryResponse(
    val total_count: Int,
    val items: List<Repository>
)