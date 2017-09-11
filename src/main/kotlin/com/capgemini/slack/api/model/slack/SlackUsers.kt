package com.scholarsgate

import com.capgemini.slack.api.model.SlackUser
/**
 * Created by Andy on 01/06/2017.
 */

data class SlackUsers(
        val ok: Boolean,
        val members: List<SlackUser>
)
