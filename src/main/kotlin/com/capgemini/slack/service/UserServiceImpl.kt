package com.capgemini.slack.service

import com.capgemini.slack.api.GraphApi
import com.capgemini.slack.api.SlackApi
import com.capgemini.slack.api.model.graph.GraphUser
import com.capgemini.slack.api.model.graph.GraphUserResponse
import com.capgemini.slack.api.model.SlackUser
import com.capgemini.slack.domain.ActiveUser
import com.capgemini.slack.domain.AuthenticatedUser
import com.capgemini.slack.domain.UserCache
import com.scholarsgate.SlackUsers
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import org.springframework.stereotype.*
import org.springframework.beans.factory.annotation.*


/**
 * Created by JZBHHX on 05/07/17.
 */

class UserServiceImpl : UserService {
    
    @Value("\${slack.token}")
    lateinit var token: String
    
    private val itemsPerPage = 10
    private val cacheTTL = 1000*60*60*24
    private var userCache :UserCache = UserCache(0,mutableListOf<SlackUser>())

    override fun getUnknownUsers(user: AuthenticatedUser): MutableList<SlackUser> {
        val now = System.currentTimeMillis()
        if (now - userCache.timeStamp < cacheTTL){
            return userCache.users
        }

        val activeUser = user.principal as ActiveUser
        val unknownUsers: MutableList<SlackUser> = mutableListOf<SlackUser>()
        var slackUsers = getSlackUsers()
        slackUsers = cleanse(slackUsers)
        slackUsers.forEach {
            when { (!it.deleted && it.profile.email != null) -> {
                val graphUser = getGraphUser(activeUser.token, it.profile.email)
                if (graphUser.isEmpty()) {
                    unknownUsers.add(it)
                }
                println(it.profile.email)
            }
            }
        }
        userCache.timeStamp = now
        userCache.users = unknownUsers
        return unknownUsers
    }

    private fun cleanse(slackUsers: List<SlackUser>): List<SlackUser> {
        var users: MutableList<SlackUser> = mutableListOf<SlackUser>()

        slackUsers.forEach() {
            if (!it.deleted && it.profile.email != null)
                users.add(it)
        }
        return users
    }

    override fun getSlackUsers(): List<SlackUser> {
        val slackRetrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl("http://www.slack.com/")
                .build()
        val slackApi = slackRetrofit.create(SlackApi::class.java)

        val call = slackApi.getUsers(token, 1, 1)
        val slackResponse = call.execute()
        if (slackResponse.isSuccessful && slackResponse.body()?.members != null) {
            return (slackResponse.body() as SlackUsers).members
        }
        return emptyList()
    }

    override fun getGraphUser(token: String, email: String): MutableList<GraphUser> {
        var graphUsers: MutableList<GraphUser> = mutableListOf<GraphUser>()

        val okHttpClient = OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(5, TimeUnit.MINUTES)
                .build()

        val graphRetrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl("https://graph.microsoft.com/")
                .client(okHttpClient)
                .build()

        val graphApi = graphRetrofit.create(GraphApi::class.java)
        val call = graphApi.findUserByEmail(token, "userPrincipalName eq '$email'")
        val graphResponse = call.execute()
        if (graphResponse.isSuccessful) {
            val graphUserResponse = graphResponse.body() as GraphUserResponse
            if (!graphUserResponse.value.isEmpty())
                graphUsers.add(graphUserResponse.value.first())
        }
        return graphUsers
    }

}
