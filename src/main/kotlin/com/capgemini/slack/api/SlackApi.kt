package com.capgemini.slack.api

import com.capgemini.slack.api.model.SlackUserResponse
import com.scholarsgate.SlackUsers
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Andy on 01/06/2017.
 */

interface SlackApi {

    @GET("/api/users.list")
    fun getUsers(@Query("token") token: String, @Query("presence") presence: Int, @Query("pretty") pretty: Int): Call<SlackUsers>

    @GET("/api/users.info")
    fun getUser(@Query("token") token: String, @Query("user") user: String): Call<SlackUserResponse>

}