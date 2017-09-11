package com.capgemini.slack.api

import com.capgemini.slack.api.model.activedirectory.ActiveDirectoryToken
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


/**
 * Created by Andy on 01/06/2017.
 */

interface ActiveDirectoryApi {

    @FormUrlEncoded
    @POST("/capgemini.onmicrosoft.com/oauth2/token")
    fun token(@Field("grant_type") grantType: String,
              @Field("client_id") clientId: String,
              @Field("redirect_uri") redirectUri: String,
              @Field("client_secret") clientSecret: String,
              @Field("code") code: String,
              @Field("scope")scope:String,
              @Field("resource")resource:String): Call<ActiveDirectoryToken>

}