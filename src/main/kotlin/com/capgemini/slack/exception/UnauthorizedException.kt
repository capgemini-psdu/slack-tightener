package com.capgemini.slack.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

/**
 * Created by JZBHHX on 30/06/17.
 */

@ResponseStatus(value= HttpStatus.UNAUTHORIZED)
class UnauthorizedException : RuntimeException()