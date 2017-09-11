package com.capgemini.slack.api.model.activedirectory

/**
 * Created by Andy on 08/06/2017.
 */

data class ActiveDirectoryToken(
        val token_type: String,
        val expires_in: Int,
        val ext_expires_in: Int,
        val expires_on: Int,
        val not_before: Int,
        val resource: String,
        val access_token: String
)