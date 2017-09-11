package com.capgemini.slack.controller

import com.capgemini.slack.domain.ActiveUser
import com.capgemini.slack.domain.AuthenticatedUser
import com.capgemini.slack.api.ActiveDirectoryApi
import com.capgemini.slack.exception.UnauthorizedException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.ArrayList
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.stereotype.*
import org.springframework.beans.factory.annotation.*

/**
 * Created by JZBHHX on 30/06/17.
 */

@Controller
class AuthorisationController {

    @Value("\${active-directory.application.application-id}")
    lateinit var clientId: String

    @Value("\${active-directory.application.client-secret-key}")
    lateinit var clientSecret: String

    @Value("\${redirect.uri}")
    lateinit var redirectUri: String
    
    val scope = "https://graph.microsoft.com/User.ReadBasic.All"
    val resource = "https://graph.microsoft.com"
    val grantType = "authorization_code"
    val ROLE = "ROLE_"

    @RequestMapping(method = arrayOf(RequestMethod.GET), value = "/authorize")
    fun authorize(@RequestParam("code") code: String, @RequestParam("session_state") state: String, request: HttpServletRequest, response: HttpServletResponse): String {
        val ctx = SecurityContextHolder.getContext()

        val retrofit4ActiveDirectory = Retrofit.Builder()
                .baseUrl("https://login.microsoftonline.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        val activeDirectoryService = retrofit4ActiveDirectory.create(ActiveDirectoryApi::class.java);

        val tokenCall = activeDirectoryService.token(grantType, clientId, redirectUri, clientSecret, code, scope, resource)

        val tokenResponse = tokenCall.execute()

        if (!tokenResponse.isSuccessful)
            throw UnauthorizedException()

        ctx.authentication = provisionUser(tokenResponse.body()!!.access_token)

        return "redirect:/search"
    }

    fun provisionUser(access_token: String): AuthenticatedUser {
        val roles = ArrayList<SimpleGrantedAuthority>()
        roles.add(SimpleGrantedAuthority(ROLE + "user"))
        val activeUser = ActiveUser("user", "******", roles)
        activeUser.token = access_token
        val user = AuthenticatedUser(activeUser)
        return user
    }

}


