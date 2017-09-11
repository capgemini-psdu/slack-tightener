package com.capgemini.slack.domain

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

/**
 * Created by JZBHHX on 03/07/17.
 */
class ActiveUser(username: String, password: String, authorities: Collection<GrantedAuthority>) : User(username, password, authorities) {
    var token :String = ""

}