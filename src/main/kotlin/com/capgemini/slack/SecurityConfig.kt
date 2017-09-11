package com.capgemini.slack

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

/**
 * Created by JZBHHX on 28/06/17.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@EnableWebSecurity(debug = false)
class SecurityConfig : WebSecurityConfigurerAdapter(){
    private val ROLE_ADMIN = "ADMIN"

    private val adminUsername = "admin"

    private val adminPassword = "admin"

    private val LOGIN = "/login"

    private val UNPROTECTED_URLS = arrayOf("/authorize", "/public/error", LOGIN)

    @Autowired
    @Throws(Exception::class)
    fun configureGlobal(auth: AuthenticationManagerBuilder) {

        auth.inMemoryAuthentication()
                .withUser(adminUsername).password(adminPassword).roles(ROLE_ADMIN)

    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {

        http.authorizeRequests()
                .antMatchers(*UNPROTECTED_URLS).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(LOGIN)
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
    }
}