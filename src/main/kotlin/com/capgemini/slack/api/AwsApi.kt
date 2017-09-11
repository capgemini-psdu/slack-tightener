package com.capgemini.slack.api

import com.capgemini.slack.api.model.SlackUserResponse
import com.capgemini.slack.api.model.slack.SlackMessage
import com.scholarsgate.SlackUsers
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Andy on 01/06/2017.
 */

interface AwsApi {

    @GET("/2013-01-01/search")
    fun getUsers(@Query("q") text: String): Call<SlackMessage>

}