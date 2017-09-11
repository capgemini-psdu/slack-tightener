package com.capgemini.slack.controller

import com.capgemini.slack.api.model.SlackUser
import com.capgemini.slack.domain.AuthenticatedUser
import com.capgemini.slack.service.SearchService
import com.capgemini.slack.service.UserService
import com.jcabi.log.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.stereotype.*
import org.springframework.beans.factory.annotation.*

/**
 * Created by JZBHHX on 30/06/17.
 */

@Controller
class HomeController {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var searchService: SearchService

    @Value("\${active-directory.application.application-id}")
    lateinit var clientId: String

    @Value("\${redirect.uri}")
    lateinit var redirectUri: String

    @RequestMapping(method = arrayOf(RequestMethod.GET), value = "/login")
    fun login(model: Model): String {
        var activeDirectoryLoginUri = "https://login.microsoftonline.com/capgemini.onmicrosoft.com/oauth2/authorize" +
            "?client_id=" + clientId +
            "&response_type=code" +
            "&response_mode=query" +
            "&redirect_uri=" + redirectUri +
            "&scope=https://graph.microsoft.com/User.ReadBasic.All"
        model.addAttribute("activeDirectoryLoginUri", activeDirectoryLoginUri)
        return "login"
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET), value = "/users")
    fun users(@RequestParam("filter", required = false) filter: String?, model: Model, @AuthenticationPrincipal user: AuthenticatedUser?): String {


        val start = System.currentTimeMillis()
        if (user != null) {

            val unknownUsers = userService.getUnknownUsers(user)
            when {
                (filter != null) -> {
                    var users: List<SlackUser>
                    if (filter.startsWith("!")) {
                        val regex: Regex = Regex(filter.substring(1))
                        users = unknownUsers.filterNot { it.profile.email!!.contains(regex) }
                    } else
                        users = unknownUsers.filter { it.profile.email!!.contains(filter, true) }
                    model.addAttribute("count", users.size)
                    model.addAttribute("users", users)

                }
                (filter == null) -> {
                    model.addAttribute("count", unknownUsers.size)
                    model.addAttribute("users", unknownUsers)
                }
            }

        }
        val finish = System.currentTimeMillis()
        val elapsed = (finish - start) / 1000
        Logger.info(this, "/home finished in $elapsed seconds")
        return "users"
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET), value = "/home")
    fun home(model: Model, @AuthenticationPrincipal user: AuthenticatedUser?): String {

        return "search"
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET), value = "/search")
    fun search(@RequestParam("q", required = false) query: String?, model: Model, @AuthenticationPrincipal user: AuthenticatedUser?): String {

        if (query != null) {
            model.addAttribute("query", query)
            model.addAttribute("hits", searchService.search(query))
        }
        return "search"
    }
}