package com.capgemini.slack

import com.capgemini.slack.service.SearchService
import com.capgemini.slack.service.SearchServiceImpl
import com.capgemini.slack.service.UserService
import com.capgemini.slack.service.UserServiceImpl
import org.springframework.context.annotation.Bean

/**
 * Created by JZBHHX on 05/07/17.
 */

class ApplicationConfig {

    @Bean
    fun userService(): UserService = UserServiceImpl()

    @Bean
    fun searchService(): SearchService = SearchServiceImpl()

}