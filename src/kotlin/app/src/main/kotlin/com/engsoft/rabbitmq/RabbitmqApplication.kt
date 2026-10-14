package com.engsoft.rabbitmq

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RabbitmqApplication

fun main(args: Array<String>) {
    runApplication<RabbitmqApplication>(*args)
}
