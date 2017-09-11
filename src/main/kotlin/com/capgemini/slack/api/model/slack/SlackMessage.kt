package com.capgemini.slack.api.model.slack

import com.squareup.moshi.Json

/**
 * Created by JZBHHX on 10/07/17.
 */

data class SlackMessage(
        val status: SlackMessageStatus,
        val hits: SlackMessageHits
)

data class SlackMessageStatus(
        val rid: String,
        @Json(name = "time-ms") val time_ms: Int
)

data class SlackMessageHits(
        val found: Int,
        val start: Int,
        val hit: List<SlackMessageHit>
)

data class SlackMessageHit(
        val id: String,
        val fields: SlackMessageFields
)

data class SlackMessageFields(
        val messagetext: String,
        val userid: String,
        val ts: String,
        val date: String,
        val channel: String
)