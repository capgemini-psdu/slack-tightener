package com.capgemini.slack.service

import com.capgemini.slack.api.AwsApi
import com.capgemini.slack.api.model.slack.SlackMessage
import com.capgemini.slack.api.model.slack.SlackMessageHit
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by JZBHHX on 05/07/17.
 */

class SearchServiceImpl : SearchService {
    override fun search(query: String): List<SlackMessageHit> {
        val slackRetrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl("http://search-slackdomain-ayi3k5ouzcemzqahgputtcmuea.eu-west-1.cloudsearch.amazonaws.com")
                .build()
        val slackApi = slackRetrofit.create(AwsApi::class.java)

        val call = slackApi.getUsers(query)
        val slackResponse = call.execute()
        if (slackResponse.isSuccessful) {
            val hits = slackResponse.body()!!.hits.hit
            return hits.sortedByDescending { it.fields.date }
        }
        return emptyList()
    }
}