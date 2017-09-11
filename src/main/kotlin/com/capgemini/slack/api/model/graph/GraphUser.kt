package com.capgemini.slack.api.model.graph

/**
 * Created by JZBHHX on 03/07/17.
 */
data class GraphUser(
        val id: String,
        val businessPhones: List<String>,
        val displayName: String,
        val givenName: String,
        val jobTitle: String,
        val mail: String,
        val mobilePhone: String,
        val officeLocation: String,
        val preferredLanguage: String,
        val surname: String,
        val userPrincipalName: String
)