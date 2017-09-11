package com.capgemini.slack

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(ApplicationConfig::class)
class DemoApplication


fun main(args: Array<String>) {
    SpringApplication.run(DemoApplication::class.java, *args)
}
