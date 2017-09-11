package com.capgemini.slack.api.model

/**
 * Created by JZBHHX on 03/07/17.
 */

data class SlackUser(
        val id: String,
        val team_id: String,
        val name: String,
        val deleted: Boolean,
        val color: String,
        val real_name: String,
        val tz: String,
        val tz_label: String,
        val tz_offset: Int,
        val profile: SlackUserProfile,
        val is_admin: Boolean,
        val is_owner: Boolean,
        val is_primary_owner: Boolean,
        val is_restricted: Boolean,
        val is_ultra_restricted: Boolean,
        val is_bot: Boolean,
        val updated: Int,
        val has_2fa: Boolean,
        val presence: String
)

