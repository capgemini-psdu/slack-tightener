package com.capgemini.slack.api

import com.capgemini.slack.api.model.graph.GraphUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Created by Andy on 08/06/2017.
 */

interface GraphApi {

    @GET("v1.0/users")
    fun findUserByEmail(@Header("Authorization") bearer: String, @Query("\$filter") filter: String): Call<GraphUserResponse>

}