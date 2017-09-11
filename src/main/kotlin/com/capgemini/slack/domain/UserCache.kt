package com.capgemini.slack.domain

import com.capgemini.slack.api.model.SlackUser

/**
 * Created by JZBHHX on 06/07/17.
 */
data class UserCache(
        var timeStamp: Long,
        var users: MutableList<SlackUser>
)