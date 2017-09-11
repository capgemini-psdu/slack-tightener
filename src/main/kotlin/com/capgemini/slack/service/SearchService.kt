package com.capgemini.slack.service

import com.capgemini.slack.api.model.slack.SlackMessage
import com.capgemini.slack.api.model.slack.SlackMessageHit

/**
 * Created by JZBHHX on 05/07/17.
 */
interface SearchService {
    fun search(query: String): List<SlackMessageHit>
}