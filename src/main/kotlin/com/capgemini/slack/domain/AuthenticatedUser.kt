package com.capgemini.slack.domain

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

/**
 * Created by JZBHHX on 03/07/17.
 */

class AuthenticatedUser : AbstractAuthenticationToken {

    private val principal: Any

    constructor(aPrincipal: Any, authorities: Collection<GrantedAuthority>) : super(authorities) {
        this.principal = aPrincipal
        super.setAuthenticated(true)
    }

    constructor(aPrincipal: User) : super(aPrincipal.authorities) {
        this.principal = aPrincipal
        super.setAuthenticated(true)
    }

    override fun getCredentials(): Any {
        return ""
    }

    override fun getPrincipal(): Any {
        return principal
    }
}
