package com.capgemini.slack.api.model.graph

import com.squareup.moshi.Json

/**
 * Created by JZBHHX on 03/07/17.
 */
data class GraphUserResponse(
        @Json(name = "@odata.context") val odataContext: String,
        val value: List<GraphUser>
)
