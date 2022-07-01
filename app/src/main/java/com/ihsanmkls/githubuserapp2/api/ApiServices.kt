package com.ihsanmkls.githubuserapp2.api

import com.ihsanmkls.githubuserapp2.BuildConfig
import com.ihsanmkls.githubuserapp2.data.retrofit2.DetailUser
import com.ihsanmkls.githubuserapp2.data.retrofit2.UserResponse
import com.ihsanmkls.githubuserapp2.data.retrofit2.Users
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    companion object {
        private const val githubApiKey: String = BuildConfig.GITHUB_KEY
    }

    @GET("search/users")
    @Headers("Authorization: token $githubApiKey")
    fun getSearchUsers (
        @Query("q") query: String
    ) : Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $githubApiKey")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $githubApiKey")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<Users>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $githubApiKey")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<Users>>

}