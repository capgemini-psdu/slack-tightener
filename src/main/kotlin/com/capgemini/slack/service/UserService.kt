package com.capgemini.slack.service

import com.capgemini.slack.api.model.graph.GraphUser
import com.capgemini.slack.api.model.SlackUser
import com.capgemini.slack.domain.AuthenticatedUser

/**
 * Created by JZBHHX on 05/07/17.
 */
interface UserService {
    fun getSlackUsers(): List<SlackUser>
    fun getUnknownUsers(user: AuthenticatedUser): MutableList<SlackUser>
    fun getGraphUser(token: String, email: String): MutableList<GraphUser>
}