package com.capgemini.slack.api.model

/**
 * Created by JZBHHX on 03/07/17.
 */

data class SlackUserProfile(
        val first_name: String,
        val last_name: String,
        val avatar_hash: String,
        val real_name: String,
        val real_name_normalized: String,
        val email: String?,
        val image_24: String,
        val image_32: String,
        val image_48: String,
        val image_72: String,
        val image_192: String,
        val image_512: String
)

